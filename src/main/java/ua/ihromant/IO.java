package ua.ihromant;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;

import java.io.Serializable;

@CompileTime
public final class IO {
    private IO() {

    }

    @Meta
    private static native Proxy getProxy(Class<?> cls);

    private static void getProxy(ReflectClass<?> cls) {
        if (!Metaprogramming.findClass(Serializable.class).isAssignableFrom(cls)) {
            Metaprogramming.unsupportedCase();
        }
        var pr = Metaprogramming.proxy(Proxy.class, (instance, method, args) -> {
            String name = cls.getName();
            Metaprogramming.exit(() -> name);
        });
        Metaprogramming.exit(() -> pr.get());
    }

    private interface Proxy {
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

    public static void debug() {
        var a = new Abc();
        System.out.println(getProxy(a.getClass()).write(a));
        var b = new Def();
        System.out.println(getProxy(b.getClass()).write(b));
    }
}
