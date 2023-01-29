package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSString;

public class StringSerializer implements Serializer {
    public static final StringSerializer INSTANCE = new StringSerializer();

    private StringSerializer() {

    }

    @Override
    public JSObject write(Object object) {
        return JSString.valueOf((String) object);
    }
}
