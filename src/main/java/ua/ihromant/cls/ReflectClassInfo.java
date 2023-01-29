package ua.ihromant.cls;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;

@CompileTime
public class ReflectClassInfo implements ClassInfo {
    private final ReflectClass<?> cls;

    public ReflectClassInfo(ReflectClass<?> cls) {
        this.cls = cls;
    }

    @Override
    public boolean assignableTo(Class<?> other) {
        return Metaprogramming.findClass(other).isAssignableFrom(cls);
    }

    @Override
    public boolean isEnum() {
        return cls.isEnum();
    }

    @Override
    public boolean isArray() {
        return cls.isArray();
    }

    @Override
    public String name() {
        return cls.getName();
    }
}
