package ua.ihromant.serializers;

import org.teavm.jso.JSObject;

public interface Serializer {
    JSObject write(Object object);
}
