package ua.ihromant.deserializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSString;

public interface Deserializer {
    Deserializer BOOLEAN = jso -> jso.<JSBoolean>cast().booleanValue();
    Deserializer INT = jso -> jso.<JSNumber>cast().intValue();
    Deserializer DOUBLE = jso -> jso.<JSNumber>cast().doubleValue();
    Deserializer STRING = jso -> jso.<JSString>cast().stringValue();

    Deserializer BOOLEAN_ARRAY = jso -> {
        JSArray<JSBoolean> boolArray = jso.cast();
        int length = boolArray.getLength();
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; ++i) {
            result[i] = boolArray.get(i).booleanValue();
        }
        return result;
    };

    Deserializer INT_ARRAY = jso -> {
        JSArray<JSNumber> intArray = jso.cast();
        int length = intArray.getLength();
        int[] result = new int[length];
        for (int i = 0; i < length; ++i) {
            result[i] = intArray.get(i).intValue();
        }
        return result;
    };

    Deserializer DOUBLE_ARRAY = jso -> {
        JSArray<JSNumber> doubleArray = jso.cast();
        int length = doubleArray.getLength();
        double[] result = new double[length];
        for (int i = 0; i < length; ++i) {
            result[i] = doubleArray.get(i).doubleValue();
        }
        return result;
    };

    static Deserializer nullable(Deserializer base) {
        return jso -> jso == null || JSObjects.isUndefined(jso) ? null : base.read(jso);
    }

    Object read(JSObject jso);
}
