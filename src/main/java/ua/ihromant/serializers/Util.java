package ua.ihromant.serializers;

import org.teavm.interop.NoSideEffects;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSObject;

public class Util {
    @JSBody(params = { "object" }, script = "return object === null;")
    @NoSideEffects
    public static native boolean isNull(JSObject object);
}
