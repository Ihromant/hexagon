package ua.ihromant;

import org.teavm.jso.JSObject;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Diagnostics;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import org.teavm.metaprogramming.reflect.ReflectField;
import ua.ihromant.cls.ClassInfo;
import ua.ihromant.cls.CommonClassInfo;
import ua.ihromant.cls.ReflectClassInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CompileTime
public final class IO {
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
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Value<Serializer> serializer = getSerializer(cls);
        Metaprogramming.exit(() -> serializer.get());
    }

    private static Value<Serializer> getSerializer(ReflectClass<?> cls) {
        if (cls.isArray()) {
            ReflectClass<?> childClass = cls.getComponentType();
            Value<Serializer> childSerializer = getSerializer(childClass);
            if (childSerializer == null) {
                Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "No serializer for " + childClass.getName());
            }
            return null;
        } else {
            return Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
                //cls.getFields()[0].getType()
                String name = cls.getName();
                Metaprogramming.exit(() -> null);
            });
        }
    }

    private interface Serializer {
        JSObject write(Object object);
    }

    private interface Deserializer {
        Object read(JSObject data);
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
