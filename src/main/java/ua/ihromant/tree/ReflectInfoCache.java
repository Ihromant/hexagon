package ua.ihromant.tree;

import org.teavm.metaprogramming.CompileTime;
import org.teavm.metaprogramming.Metaprogramming;

@CompileTime
public class ReflectInfoCache {
    public static final ReflectInfoCache INSTANCE = new ReflectInfoCache();
    private ReflectInfoCache() {

    }

    private final ClassLoader classLoader = Metaprogramming.getClassLoader();
}
