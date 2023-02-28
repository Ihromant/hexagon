package ua.ihromant;

import org.teavm.jso.JSObject;
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
import ua.ihromant.deserializers.Deserializer;
import ua.ihromant.deserializers.DeserializerGenerator;
import ua.ihromant.serializers.Serializer;
import ua.ihromant.serializers.SerializerGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CompileTime
public final class Converters {
    private Converters() {

    }

    public static JSObject javaToJs(Object jo) {
        return serializerFor(jo.getClass()).write(jo);
    }

    public static Object jsToJava(JSObject jso, Class<?> cls) {
        return deserializerFor(cls).read(jso);
    }

    private static Deserializer deserializerFor(Class<?> cls) {
        if (blackList(new CommonClassInfo(cls))) {
            throw new IllegalArgumentException("Not supported class " + cls.getName());
        }
        return deserializer(cls);
    }

    private static Serializer serializerFor(Class<?> cls) {
        if (blackList(new CommonClassInfo(cls))) {
            throw new IllegalArgumentException("Not supported class " + cls.getName());
        }
        return serializer(cls);
    }

    private static boolean blackList(ClassInfo cls) {
        if (cls.isInterface()) {
            return true;
        }
        return !cls.assignableTo(IsSerializable.class);
    }

    @Meta
    private static native Serializer serializer(Class<?> cls);
    private static void serializer(ReflectClass<?> cls) {
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), "Generating serializer for " + cls.getName());
        Value<Serializer> serializer = SerializerGenerator.getSerializer(ClassCache.find(cls.getName()));
        Metaprogramming.exit(() -> serializer.get());
    }

    @Meta
    private static native Deserializer deserializer(Class<?> cls);
    private static void deserializer(ReflectClass<?> cls) {
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), "Generating deserializer for " + cls.getName());
        Value<Deserializer> deserializer = DeserializerGenerator.getDeserializer(ClassCache.find(cls.getName()));
        Metaprogramming.exit(() -> deserializer.get());
    }

    static class O {
        protected int aa;
    }

    static class Abc extends O implements IsSerializable {
        private int a;
        private Integer b;
        private String c;
        private boolean d;
        private Def e;
        private int[][] f;
        private Integer[] g;
        private Ghi[][] h;
        private List<String> i;
        private Map<String, Def> j;

        @Override
        public String toString() {
            return "Abc{" +
                    "aa=" + aa +
                    ", a=" + a +
                    ", b=" + b +
                    ", c='" + c + '\'' +
                    ", d=" + d +
                    ", e=" + e +
                    ", f=" + (f == null ? null : Arrays.deepToString(f)) +
                    ", g=" + (g == null ? null : Arrays.toString(g)) +
                    ", h=" + (h == null ? null : Arrays.deepToString(h)) +
                    ", i=" + i +
                    ", j=" + j +
                    '}';
        }
    }

    enum Def implements IsSerializable {
        A, B, C;
    }

    record Ghi(int a, int b) implements IsSerializable {
    }

    public static void debug() {
        Def b = Def.A;
        System.out.println(JSON.stringify(Converters.javaToJs(b)));
        Ghi c = new Ghi(10, 20);
        Abc a = new Abc();
        a.aa = 20;
        a.e = b;
        a.f = new int[][] {{1, 2, 3}, null, {1, 2}};
        a.g = new Integer[] {1, null, 2, null};
        a.h = new Ghi[][]{{c}};
        a.i = List.of("abc", "def", "ghi");
        a.j = Map.of("a", Def.A, "b", Def.B, "c", Def.C);
        String str = JSON.stringify(Converters.javaToJs(a));
        System.out.println(str);
        Abc reParsed = (Abc) Converters.jsToJava(JSON.parse(str), Abc.class);
        System.out.println(reParsed);
        System.out.println(reParsed.f.getClass() + " " + reParsed.f[1] + " " + Arrays.toString(reParsed.f[0]));
        System.out.println(reParsed.g.getClass());
    }
}
