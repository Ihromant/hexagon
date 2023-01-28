package ua.ihromant;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import ua.ihromant.cls.ClassInfo;
import ua.ihromant.cls.CommonClassInfo;
import ua.ihromant.cls.ReflectClassInfo;

import java.io.Serializable;
import java.util.Map;

@CompileTime
public final class IO {
    private IO() {

    }

    private static Serializer proxyFor(Class<?> cls) {
        if (notSupported(new CommonClassInfo(cls))) {
            throw new IllegalArgumentException("Not supported class " + cls.getName());
        }
        return getProxy(cls);
    }

    @Meta
    private static native Serializer getProxy(Class<?> cls);

    private static boolean notSupported(ClassInfo cls) {
        if (cls.isArray()) {
            return false;
        }
        if (cls.assignableTo(Map.class)) {
            return false;
        }
        return !cls.assignableTo(Serializable.class);
    }

    private static void getProxy(ReflectClass<?> cls) {
        if (notSupported(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        var pr = Metaprogramming.proxy(Serializer.class, (instance, method, args) -> {
            //cls.getFields()[0].getType()
            String name = cls.getName();
            Metaprogramming.exit(() -> name);
        });
        //Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "Was not able to find serializer for class " + cls.getName());
        Metaprogramming.exit(() -> pr.get());
    }

    private interface Serializer {
        String write(Object object);
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
