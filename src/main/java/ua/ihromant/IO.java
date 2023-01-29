package ua.ihromant;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import ua.ihromant.cls.ClassInfo;
import ua.ihromant.cls.CommonClassInfo;
import ua.ihromant.cls.ReflectClassInfo;
import ua.ihromant.deserializers.Deserializer;
import ua.ihromant.serializers.BooleanSerializer;
import ua.ihromant.serializers.DoubleSerializer;
import ua.ihromant.serializers.IntSerializer;
import ua.ihromant.serializers.Serializer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CompileTime
public final class IO {
    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final List<String> SUPPORTED = Arrays.asList(BOOLEAN, INT, DOUBLE);
    private static final Map<Class<?>, Serializer> serializers = new IdentityHashMap<>();
    private static final Map<Class<?>, Deserializer> deserializers = new IdentityHashMap<>();

    static {

    }

    private IO() {

    }

    private static Serializer proxyFor(Class<?> cls) {
        if (blackList(new CommonClassInfo(cls))) {
            throw new IllegalArgumentException("Not supported class " + cls.getName());
        }
        return getProxy(cls);
    }

    @Meta
    private static native Serializer getProxy(Class<?> cls);

    private static boolean blackList(ClassInfo cls) {
        if (cls.isArray()) {
            return false;
        }
        if (cls.isEnum()) {
            return false;
        }
        if (cls.isPrimitive()) {
            return !SUPPORTED.contains(cls.name());
        }
        if (cls.assignableTo(Map.class)) {
            return false;
        }
        if (cls.assignableTo(List.class)) {
            return false;
        }
        if (cls.assignableTo(Set.class)) {
            return false;
        }
        return !cls.assignableTo(Serializable.class);
    }

    private static void getProxy(ReflectClass<?> cls) {
        ReflectClassInfo info = new ReflectClassInfo(cls);
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), cls.getName());
        Value<Serializer> serializer = getSerializer(info);
        Metaprogramming.exit(() -> serializer.get());
    }

    private static Value<Serializer> getSerializer(ReflectClassInfo info) {
        if (info.isArray()) {
            ReflectClassInfo elementInfo = info.elementInfo();
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
        if (info.isPrimitive()) {
            switch (info.name()) {
                case BOOLEAN: return Metaprogramming.emit(() -> BooleanSerializer.INSTANCE);
                case INT: return Metaprogramming.emit(() -> IntSerializer.INSTANCE);
                case DOUBLE: return Metaprogramming.emit(() -> DoubleSerializer.INSTANCE);
                //default: Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "Tried to serialize unsupported primitive " + info.name());
            }
        }
        return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            //cls.getFields()[0].getType()
            String name = info.name();
            Metaprogramming.exit(() -> null);
        });
    }

    static class Abc implements Serializable {
        String foo() {
            return "qwe";
        }
    }

    static class Def implements Serializable {
        String bar() {
            return "qwe";
        }
    }

    static class Ghi {
        String baz() {
            return "qwe";
        }
    }

    public static void debug() {
        var a = new Abc();
        System.out.println(proxyFor(a.getClass()).write(a));
        var b = new Ghi();
        System.out.println(proxyFor(b.getClass()).write(b));
    }
}
