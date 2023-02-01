package ua.ihromant.tree;

import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSString;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.Value;
import ua.ihromant.cls.ReflectClassInfo;
import ua.ihromant.serializers.Serializer;

import java.util.HashMap;
import java.util.Map;

@CompileTime
public class ReflectInfoCache {
    public static final ReflectInfoCache INSTANCE = new ReflectInfoCache();

    private Map<String, Value<Serializer>> definedSerializers = new HashMap<>();
    private final ClassLoader classLoader = Metaprogramming.getClassLoader();

    private ReflectInfoCache() {

    }

    public Value<Serializer> getSerializer(ReflectClassInfo info) {
        String name = info.name();
        return definedSerializers.computeIfAbsent(name, cnm -> buildSerializer(info));
    }

    private Value<Serializer> buildSerializer(ReflectClassInfo info) {
        String name = info.name();
        return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            boolean b = info.isPrimitive();
            //cls.getFields()[0].getType()
            Metaprogramming.exit(() -> b ? JSNumber.valueOf(1) : JSString.valueOf(name));
        });
    }
}
