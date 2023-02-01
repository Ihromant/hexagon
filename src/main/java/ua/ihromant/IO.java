package ua.ihromant;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.json.JSON;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Meta;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;
import org.teavm.metaprogramming.Value;
import ua.ihromant.cls.ClassInfo;
import ua.ihromant.cls.CommonClassInfo;
import ua.ihromant.cls.ReflectClassInfo;
import ua.ihromant.serializers.Serializer;
import ua.ihromant.tree.ReflectInfoCache;

@CompileTime
public final class IO {
    private static final String BOOLEAN = "boolean";
    private static final String INT = "int";
    private static final String DOUBLE = "double";

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
        return !cls.assignableTo(IsSerializable.class);
    }

    @Meta
    private static native Serializer getProxy(Class<?> cls);
    private static void getProxy(ReflectClass<?> cls) {
        ReflectClassInfo info = new ReflectClassInfo(cls);
        if (blackList(new ReflectClassInfo(cls))) {
            Metaprogramming.unsupportedCase();
            return;
        }
        Metaprogramming.getDiagnostics().warning(Metaprogramming.getLocation(), cls.getName());
        Value<Serializer> serializer = genericSerializer(info); // TODO later here should be generic serializer
        Metaprogramming.exit(() -> serializer.get());
    }

    private static Value<Serializer> genericSerializer(ReflectClassInfo info) {
        if (info.isArray()) {
            return arraySerializer(info);
        }
        if (info.isEnum()) {
            return enumSerializer();
        }
        if (info.isPrimitive()) {
            return primitiveSerializer(info);
        }
        return ReflectInfoCache.INSTANCE.getSerializer(ReflectInfoCache.INSTANCE.find(info.name()));
    }

    private static Value<Serializer> arraySerializer(ReflectClassInfo info) {
        ReflectClassInfo elementInfo = info.componentType();
        Value<Serializer> childSerializer = genericSerializer(elementInfo);
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

    private static Value<Serializer> enumSerializer() {
        return Metaprogramming.emit(() -> Serializer.ENUM);
    }

    private static Value<Serializer> primitiveSerializer(ReflectClassInfo info) {
        switch (info.name()) {
            case BOOLEAN: return Metaprogramming.emit(() -> Serializer.BOOLEAN);
            case INT: return Metaprogramming.emit(() -> Serializer.INT);
            case DOUBLE: return Metaprogramming.emit(() -> Serializer.DOUBLE);
            default: {
                Metaprogramming.getDiagnostics().error(Metaprogramming.getLocation(), "Tried to serialize unsupported primitive " + info.name());
                return null;
            }
        }
    }

    static class Abc implements IsSerializable {
        private int a;
        private Integer b;
        private String c;
        private boolean d;
        private Def e;

        String foo() {
            return "qwe";
        }
    }

    enum Def implements IsSerializable {
        A, B, C;
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
        var b = Def.A;
        System.out.println(IO.write(b));
        var a = new Abc();
        System.out.println(IO.write(a));
    }
}
