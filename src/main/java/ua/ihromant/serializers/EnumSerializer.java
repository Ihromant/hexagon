package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSString;

public class EnumSerializer implements Serializer {
    public static Serializer INSTANCE = new EnumSerializer();

    @Override
    public JSObject write(Object object) {
        return JSString.valueOf(((Enum<?>) object).name());
    }
}
