package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSMapLike;
import org.teavm.jso.core.JSObjects;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@CompileTime
public class SerializerGenerator {
    private static final Map<String, Value<Serializer>> definedSerializers = new HashMap<>();

    static {
        definedSerializers.put(int.class.getName(), Metaprogramming.lazy(() -> Serializer.INT));
        definedSerializers.put(Integer.class.getName(), Metaprogramming.lazy(() -> Serializer.nullable(Serializer.INT)));
        definedSerializers.put(boolean.class.getName(), Metaprogramming.lazy(() -> Serializer.BOOLEAN));
        definedSerializers.put(Boolean.class.getName(), Metaprogramming.lazy(() -> Serializer.nullable(Serializer.BOOLEAN)));
        definedSerializers.put(double.class.getName(), Metaprogramming.lazy(() -> Serializer.DOUBLE));
        definedSerializers.put(Double.class.getName(), Metaprogramming.lazy(() -> Serializer.nullable(Serializer.DOUBLE)));
        definedSerializers.put(String.class.getName(), Metaprogramming.lazy(() -> Serializer.nullable(Serializer.STRING)));
    }

    public static Value<Serializer> getSerializer(Class<?> cls) {
        Value<Serializer> result = definedSerializers.get(cls.getName());
        if (result != null) {
            return result;
        }
        Value<Serializer> generated = Metaprogramming.lazyFragment(() -> buildSerializer(cls));
        definedSerializers.put(cls.getName(), generated);
        return generated;
    }

    private static Value<Serializer> buildSerializer(Class<?> cls) {
        if (cls.isEnum()) {
            return Metaprogramming.lazy(() -> Serializer.nullable(Serializer.ENUM));
        }
        if (cls.isArray()) {
            return buildArraySerializer(cls);
        }
        return buildObjectSerializer(cls);
    }

    private static Value<Serializer> buildArraySerializer(Class<?> cls) {
        Class<?> elementInfo = cls.componentType();
        Value<Serializer> childSerializer = getSerializer(elementInfo);
        if (childSerializer == null) {
            Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "No serializer for " + elementInfo.getName());
        }
        ReflectClass<?> rCls = Metaprogramming.findClass(cls);
        return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            Value<Object> value = args[0];
            Metaprogramming.exit(() -> {
                JSArray<JSObject> target = JSArray.create();
                int sz = rCls.getArrayLength(value.get());
                Serializer itemSerializer = childSerializer.get();
                for (int i = 0; i < sz; ++i) {
                    Object component = rCls.getArrayElement(value.get(), i);
                    target.push(itemSerializer.write(component));
                }
                return target;
            });
        });
    }

    private static Value<Serializer> buildObjectSerializer(Class<?> cls) {
        //ClassInfo inf = new CommonClassInfo(cls);
        Field[] fields = cls.getDeclaredFields();
        ReflectClass<?> reflCl = Metaprogramming.findClass(cls);
        return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            //boolean b = inf.isPrimitive();
            //cls.getFields()[0].getType()
            Value<JSMapLike<JSObject>> result = Metaprogramming.emit(() -> JSObjects.create());
            Value<Object> object = args[0];
            for (Field fd : fields) {
                int mod = fd.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
                    continue;
                }
                String propName = fd.getName();
                ReflectField reflFd = reflCl.getDeclaredField(propName);
                //Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), reflFd.getName() + " " + reflFd.getClass());
                Value<Serializer> fieldSerializer = getSerializer(fd.getType());
                Value<Object> javaProp = Metaprogramming.emit(() -> reflFd.get(object.get()));
                Value<JSObject> jsProp = Metaprogramming.emit(() -> fieldSerializer.get().write(javaProp.get()));
                Metaprogramming.emit(() -> result.get().set(propName, jsProp.get()));
                //Metaprogramming.emit()
            }
            Metaprogramming.exit(() -> result.get());
            //Metaprogramming.exit(() -> b ? JSNumber.valueOf(1) : JSString.valueOf(cls.getName()));
        });
    }
}