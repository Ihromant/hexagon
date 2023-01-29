package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;

public class BooleanSerializer implements Serializer {
    public static final Serializer INSTANCE = new BooleanSerializer();

    private BooleanSerializer() {

    }

    @Override
    public JSObject write(Object object) {
        return JSBoolean.valueOf((boolean) object);
    }
}
