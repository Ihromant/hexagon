package ua.ihromant;

import org.teavm.jso.json.JSON;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import ua.ihromant.cls.ClassInfo;
import ua.ihromant.cls.CommonClassInfo;
import ua.ihromant.cls.ReflectClassInfo;
import ua.ihromant.cls.ClassCache;
import ua.ihromant.serializers.Serializer;
import ua.ihromant.serializers.SerializerGenerator;

@CompileTime
public final class IO {
    private IO() {

    }

    public static String write(Object o) {
        return JSON.stringify(proxyFor(o.getClass()).write(o));
    }

    private static Serializer proxyFor(Class<?> cls) {
        if (blackList(new CommonClassInfo(cls))) {
            throw new IllegalArgumentException("Not supported class " + cls.getName());
        }
        return getProxy(cls);
    }

    private static boolean blackList(ClassInfo cls) {
        if (cls.isInterface()) {
            return true;
        }
        if (cls.isArray()) {
            return blackList(cls.componentType());
        }
        return !cls.assignableTo(IsSerializable.class);
    }

    @Meta
    private static native Serializer getProxy(Class<?> cls);
    private static void getProxy(ReflectClass<?> cls) {
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), cls.getName());
        Value<Serializer> serializer = SerializerGenerator.INSTANCE.getSerializer(ClassCache.find(cls.getName()));
        Metaprogramming.exit(() -> serializer.get());
    }

    static class Abc implements IsSerializable {
        private int a;
        private Integer b;
        private String c;
        private boolean d;
        private Def e;
        private int[] f;
        private Integer[] g;
        private Ghi h;
    }

    enum Def implements IsSerializable {
        A, B, C;
    }

    static class Ghi implements IsSerializable {
        private int a;
        private int b;
    }

    public static void debug() {
        Def b = Def.A;
        System.out.println(IO.write(b));
        Ghi c = new Ghi();
        c.a = 10;
        c.b = 20;
        Abc a = new Abc();
        a.e = b;
        a.f = new int[] {1, 2, 3};
        a.g = new Integer[] {1, null, 2, null};
        a.h = c;
        System.out.println(IO.write(a));
    }
}
