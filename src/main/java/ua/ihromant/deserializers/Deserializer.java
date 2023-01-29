package ua.ihromant.deserializers;

import org.teavm.jso.JSObject;

public interface Deserializer {
    Object read(JSObject data);
}
