package ua.ihromant.cls;

import lombok.RequiredArgsConstructor;
import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;
import org.teavm.metaprogramming.ReflectClass;

@CompileTime
@RequiredArgsConstructor
public class ReflectClassInfo implements ClassInfo {
    private final ReflectClass<?> cls;

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
