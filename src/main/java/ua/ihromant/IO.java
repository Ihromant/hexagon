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

@CompileTime
public final class IO {
    private IO() {

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
        if (cls.isArray()) {
            return blackList(cls.componentType());
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
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), cls.getName());
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
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), cls.getName());
        Value<Deserializer> deserializer = DeserializerGenerator.getDeserializer(ClassCache.find(cls.getName()));
        Metaprogramming.exit(() -> deserializer.get());
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

        @Override
        public String toString() {
            return "Abc{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c='" + c + '\'' +
                    ", d=" + d +
                    ", e=" + e +
//                    ", f=" + Arrays.toString(f) +
//                    ", g=" + Arrays.toString(g) +
                    ", h=" + h +
                    '}';
        }
    }

    enum Def implements IsSerializable {
        A, B, C;
    }

    static class Ghi implements IsSerializable {
        private int a;
        private int b;

        @Override
        public String toString() {
            return "Ghi{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    public static void debug() {
        Def b = Def.A;
        System.out.println(JSON.stringify(IO.javaToJs(b)));
        Ghi c = new Ghi();
        c.a = 10;
        c.b = 20;
        Abc a = new Abc();
        a.e = b;
        //a.f = new int[] {1, 2, 3};
        //a.g = new Integer[] {1, null, 2, null};
        a.h = c;
        String str = JSON.stringify(IO.javaToJs(a));
        System.out.println(str);
        Abc reParsed = (Abc) IO.jsToJava(JSON.parse(str), Abc.class);
        System.out.println(reParsed);
    }
}
