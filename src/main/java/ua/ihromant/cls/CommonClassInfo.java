package ua.ihromant.cls;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonClassInfo implements ClassInfo {
    private final Class<?> cls;

    @Override
    public boolean assignableTo(Class<?> other) {
        return other.isAssignableFrom(cls);
    }

    @Override
    public boolean isEnum() {
        return false;
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
