package ua.ihromant.deserializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSString;

public interface Deserializer {
    Deserializer BOOLEAN = jso -> jso.<JSBoolean>cast().booleanValue();
    Deserializer INT = jso -> jso.<JSNumber>cast().intValue();
    Deserializer DOUBLE = jso -> jso.<JSNumber>cast().doubleValue();
    Deserializer STRING = jso -> jso.<JSString>cast().stringValue();

    static Deserializer nullable(Deserializer base) {
        return jso -> JSObjects.isUndefined(jso) ? null : base.read(jso);
    }

    Object read(JSObject jso);
}
