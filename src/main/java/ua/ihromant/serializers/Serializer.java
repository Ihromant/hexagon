package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSString;

public interface Serializer {
    Serializer BOOLEAN = jo -> JSBoolean.valueOf((boolean) jo);
    Serializer INT = jo -> JSNumber.valueOf((int) jo);
    Serializer DOUBLE = jo -> JSNumber.valueOf((double) jo);
    Serializer ENUM = jo -> JSString.valueOf(((Enum<?>) jo).name());
    Serializer STRING = jo -> JSString.valueOf((String) jo);

    static Serializer nullable(Serializer base) {
        return jo -> jo == null ? JSObjects.undefined() : base.write(jo);
    }

    JSObject write(Object jo);
}
