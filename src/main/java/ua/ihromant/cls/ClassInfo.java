package ua.ihromant.cls;

public interface ClassInfo {
    boolean assignableTo(Class<?> constant);

    boolean isEnum();

    boolean isArray();

    String name();
}
