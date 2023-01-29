package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSNumber;

public class DoubleSerializer implements Serializer {
    public static final Serializer INSTANCE = new DoubleSerializer();

    private DoubleSerializer() {

    }

    @Override
    public JSObject write(Object object) {
        return JSNumber.valueOf((double) object);
    }
}

