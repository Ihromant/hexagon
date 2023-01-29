package ua.ihromant.cls;

public class CommonClassInfo implements ClassInfo {
    private final Class<?> cls;

    public CommonClassInfo(Class<?> cls) {
        this.cls = cls;
    }

    @Override
    public boolean assignableTo(Class<?> other) {
        return other.isAssignableFrom(cls);
    }

    @Override
    public boolean isInterface() {
        return cls.isInterface();
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public boolean isPrimitive() {
        return cls.isPrimitive();
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
