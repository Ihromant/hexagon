package ua.ihromant.deserializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSMapLike;
import org.teavm.jso.core.JSString;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectField;
import org.teavm.metaprogramming.reflect.ReflectMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@CompileTime
public class DeserializerGenerator {
    private static final Map<String, Value<Deserializer>> definedDeserializers = new HashMap<>();

    static {
        definedDeserializers.put(int.class.getName(), Metaprogramming.lazy(() -> Deserializer.INT));
        definedDeserializers.put(Integer.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.INT)));
        definedDeserializers.put(boolean.class.getName(), Metaprogramming.lazy(() -> Deserializer.BOOLEAN));
        definedDeserializers.put(Boolean.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.BOOLEAN)));
        definedDeserializers.put(double.class.getName(), Metaprogramming.lazy(() -> Deserializer.DOUBLE));
        definedDeserializers.put(Double.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.DOUBLE)));
        definedDeserializers.put(String.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.STRING)));
    }

    public static Value<Deserializer> getDeserializer(Class<?> cls) {
        Value<Deserializer> result = definedDeserializers.get(cls.getName());
        if (result != null) {
            return result;
        }
        Value<Deserializer> generated = Metaprogramming.lazyFragment(() -> {
            Value<Deserializer> notNull = buildDeserializer(cls);
            return Metaprogramming.emit(() -> Deserializer.nullable(notNull.get()));
        });
        definedDeserializers.put(cls.getName(), generated);
        return generated;
    }

    private static Value<Deserializer> buildDeserializer(Class<?> cls) {
        if (cls.isEnum()) {
            return buildEnumDeserializer(cls);
        }
        if (cls.isArray()) {
            return buildArrayDeserializer(cls);
        }
        return buildObjectDeserializer(cls);
    }

    private static Value<Deserializer> buildEnumDeserializer(Class<?> cls) {
        ReflectClass<?> refCl = Metaprogramming.findClass(cls);
        ReflectMethod valueOf = refCl.getMethod("valueOf", Metaprogramming.findClass(String.class));
        return Metaprogramming.proxy(Deserializer.class, (instance, method, args) -> {
            Value<JSString> val = Metaprogramming.emit(() -> (JSString) args[0]);
            Metaprogramming.exit(() -> valueOf.invoke(null, val.get().stringValue()));
        });
    }

    private static Value<Deserializer> buildArrayDeserializer(Class<?> cls) {
        return null;
//        Class<?> elementInfo = cls.componentType();
//        Value<Deserializer> childDeserializer = getDeserializer(elementInfo);
//        if (childDeserializer == null) {
//            Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "No deserializer for " + elementInfo.getName());
//        }
//        ReflectClass<?> refCl = Metaprogramming.findClass(cls);
//        return Metaprogramming.proxy(Deserializer.class, (instance, method, args) -> {
//            Value<JSArray<?>> value = Metaprogramming.emit(() -> (JSArray<?>) args[0]);
//            Metaprogramming.exit(() -> {
//                JSArray<?> jsArray = value.get();
//                int length = jsArray.getLength();
//                Object[] result = refCl.createArray(length);
//                Deserializer itemDeserializer = childDeserializer.get();
//                for (int i = 0; i < length; ++i) {
//                    result[i] = itemDeserializer.read(jsArray.get(i));
//                }
//                return result;
//            });
//        });
    }

    private static Value<Deserializer> buildObjectDeserializer(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        ReflectClass<?> refCl = Metaprogramming.findClass(cls);
        ReflectMethod defaultConstructor = refCl.getDeclaredMethod("<init>");
        return Metaprogramming.proxy(Deserializer.class, (instance, method, args) -> {
            Value<Object> jo = Metaprogramming.emit(() -> defaultConstructor.construct());
            @SuppressWarnings("unchecked") Value<JSMapLike<JSObject>> jso = Metaprogramming.emit(() -> (JSMapLike<JSObject>) args[0]);
            for (Field fd : fields) {
                int mod = fd.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
                    continue;
                }
                String propName = fd.getName();
                ReflectField refFd = refCl.getDeclaredField(propName);
                Value<Deserializer> fieldDeserializer = getDeserializer(fd.getType());
                Value<JSObject> jsProp = Metaprogramming.emit(() -> jso.get().get(propName));
                Value<Object> javaProp = Metaprogramming.emit(() -> fieldDeserializer.get().read(jsProp.get()));
                Metaprogramming.emit(() -> refFd.set(jo.get(), javaProp.get()));
            }
            Metaprogramming.exit(() -> jo.get());
        });
    }
}
