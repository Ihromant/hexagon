package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSString;

public interface Serializer {
    Serializer BOOLEAN = object -> JSBoolean.valueOf((boolean) object);
    Serializer OBOOLEAN = nullable(BOOLEAN);
    Serializer INT = object -> JSNumber.valueOf((int) object);
    Serializer OINT = nullable(INT);
    Serializer DOUBLE = object -> JSNumber.valueOf((double) object);
    Serializer ODOUBLE = nullable(DOUBLE);
    Serializer ENUM = object -> JSString.valueOf(((Enum<?>) object).name());
    Serializer STRING = object -> JSString.valueOf((String) object);

    static Serializer nullable(Serializer base) {
        return object -> object == null ? JSObjects.undefined() : base.write(object);
    }

    JSObject write(Object object);
}
