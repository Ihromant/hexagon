package ua.ihromant.deserializers;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectMethod;

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
        Value<Deserializer> generated = Metaprogramming.lazyFragment(() -> buildDeserializer(cls));
        definedDeserializers.put(cls.getName(), generated);
        return generated;
    }

    private static Value<Deserializer> buildDeserializer(Class<?> cls) {
        if (cls.isEnum()) {
            return buildEnumDeserializer(cls);
        }
        return null; // TODO
//        if (cls.isArray()) {
//            return buildArraySerializer(cls);
//        }
//        return buildObjectSerializer(cls);
    }

    private static Value<Deserializer> buildEnumDeserializer(Class<?> cls) {
        ReflectClass<?> refCl = Metaprogramming.findClass(cls);
        ReflectMethod valueOf = refCl.getMethod("valueOf");
        return Metaprogramming.proxy(Deserializer.class, (instance, method, args) -> {

        });
    }
}
