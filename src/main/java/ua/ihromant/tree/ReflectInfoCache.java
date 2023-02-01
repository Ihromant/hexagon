package ua.ihromant.tree;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSMapLike;
import org.teavm.jso.core.JSObjects;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectField;
import ua.ihromant.cls.ReflectClassInfo;
import ua.ihromant.serializers.Serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@CompileTime
public class ReflectInfoCache {
    public static final ReflectInfoCache INSTANCE = new ReflectInfoCache();

    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Map<String, Value<Serializer>> definedSerializers = new HashMap<>();
    private final ClassLoader classLoader = Metaprogramming.getClassLoader();

    private ReflectInfoCache() {
        definedSerializers.put(int.class.getName(), Metaprogramming.emit(() -> Serializer.INT));
        definedSerializers.put(Integer.class.getName(), Metaprogramming.emit(() -> Serializer.nullable(Serializer.INT)));
        definedSerializers.put(boolean.class.getName(), Metaprogramming.emit(() -> Serializer.BOOLEAN));
        definedSerializers.put(Boolean.class.getName(), Metaprogramming.emit(() -> Serializer.nullable(Serializer.BOOLEAN)));
        definedSerializers.put(double.class.getName(), Metaprogramming.emit(() -> Serializer.DOUBLE));
        definedSerializers.put(Double.class.getName(), Metaprogramming.emit(() -> Serializer.nullable(Serializer.DOUBLE)));
        definedSerializers.put(String.class.getName(), Metaprogramming.emit(() -> Serializer.nullable(Serializer.STRING)));
    }

    public Value<Serializer> getSerializer(ReflectClassInfo info) {
        if (info.isArray()) {
            return arraySerializer(info);
        }
        if (info.isPrimitive() || "java.lang.String".equals(info.name())) {
            return definedSerializers.get(info.name());
        }
        if (info.isEnum()) {
            return enumSerializer();
        }
        return getSerializer(find(info.name()));
    }

    private static Value<Serializer> enumSerializer() {
        return Metaprogramming.emit(() -> Serializer.ENUM);
    }

    public Value<Serializer> getSerializer(Class<?> cls) {
        if (cls.isEnum()) {
            return Metaprogramming.emit(() -> Serializer.nullable(Serializer.ENUM));
        }
        return definedSerializers.computeIfAbsent(cls.getName(), n -> buildSerializer(cls));
    }

    private Class<?> find(String name) {
        if (!classes.containsKey(name)) {
            try {
                Class<?> cls = classLoader.loadClass(name);
                classes.put(name, cls);
            } catch (Exception e) {
                Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "Was not able to find class " + name);
            }
        }
        return classes.get(name);
    }

    private Value<Serializer> buildSerializer(Class<?> cls) {
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

    private Value<Serializer> arraySerializer(ReflectClassInfo info) {
        ReflectClassInfo elementInfo = info.componentType();
        Value<Serializer> childSerializer = getSerializer(elementInfo);
        if (childSerializer == null) {
            Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "No serializer for " + elementInfo.name());
        }
        ReflectClass<?> cls = info.unwrap();
        return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            Value<Object> value = args[0];
            Metaprogramming.exit(() -> {
                JSArray<JSObject> target = JSArray.create();
                int sz = cls.getArrayLength(value.get());
                Serializer itemSerializer = childSerializer.get();
                for (int i = 0; i < sz; ++i) {
                    Object component = cls.getArrayElement(value.get(), i);
                    target.push(itemSerializer.write(component));
                }
                return target;
            });
        });
    }
}
