package ua.ihromant;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;

@CompileTime
public final class IO {
    private IO() {

    }

    @Meta
    private static native Proxy getProxy(Class<?> cls);

    private static void getProxy(ReflectClass<?> cls) {
        var pr = Metaprogramming.proxy(Proxy.class, (instance, method, args) -> {
            //var val = Metaprogramming.emit(() -> "abc");
            Metaprogramming.exit(() -> null);
        });
        Metaprogramming.exit(() -> null);
    }

    private interface Proxy {
        String write(Object object);
    }

    static class Abc {
        String foo() {
            return "qwe";
        }
    }

    public static void debug() {
        var a = new Abc();
        System.out.println(getProxy(a.getClass()).write(a));
    }
}
