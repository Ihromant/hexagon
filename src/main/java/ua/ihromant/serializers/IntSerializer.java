package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSNumber;

public class IntSerializer implements Serializer {
    public static final Serializer INSTANCE = new IntSerializer();

    private IntSerializer() {

    }

    @Override
    public JSObject write(Object object) {
        return JSNumber.valueOf((int) object);
    }
}
