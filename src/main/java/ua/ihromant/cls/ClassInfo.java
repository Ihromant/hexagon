package ua.ihromant.cls;

public interface ClassInfo {
    boolean assignableTo(Class<?> constant);

    boolean isInterface();

    boolean isEnum();

    boolean isPrimitive();

    boolean isArray();

    String name();
}
