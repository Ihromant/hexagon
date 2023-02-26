package ua.ihromant.deserializers;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.Value;

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
}
