package ua.ihromant.deserializers;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.Value;
import ua.ihromant.serializers.Serializer;

import java.util.HashMap;
import java.util.Map;

@CompileTime
public class DeserializerGenerator {
    public static final DeserializerGenerator INSTANCE = new DeserializerGenerator();

    private final Map<String, Class<?>> classes = new HashMap<>();
    private final Map<String, Value<Deserializer>> definedDeserializers = new HashMap<>();
    private final ClassLoader classLoader = Metaprogramming.getClassLoader();

    private DeserializerGenerator() {
        classes.put(boolean.class.getName(), boolean.class);
        classes.put(int.class.getName(), int.class);
        classes.put(double.class.getName(), double.class);
        definedDeserializers.put(int.class.getName(), Metaprogramming.lazy(() -> Deserializer.INT));
        definedDeserializers.put(Integer.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.INT)));
        definedDeserializers.put(boolean.class.getName(), Metaprogramming.lazy(() -> Deserializer.BOOLEAN));
        definedDeserializers.put(Boolean.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.BOOLEAN)));
        definedDeserializers.put(double.class.getName(), Metaprogramming.lazy(() -> Deserializer.DOUBLE));
        definedDeserializers.put(Double.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.DOUBLE)));
        definedDeserializers.put(String.class.getName(), Metaprogramming.lazy(() -> Deserializer.nullable(Deserializer.STRING)));
    }
}
