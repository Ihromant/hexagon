package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSBoolean;
import org.teavm.jso.core.JSMapLike;
import org.teavm.jso.core.JSNumber;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSString;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Serializer {
    Serializer BOOLEAN = jo -> JSBoolean.valueOf((boolean) jo);
    Serializer INT = jo -> JSNumber.valueOf((int) jo);
    Serializer DOUBLE = jo -> JSNumber.valueOf((double) jo);
    Serializer ENUM = jo -> JSString.valueOf(((Enum<?>) jo).name());
    Serializer STRING = jo -> JSString.valueOf((String) jo);

    static Serializer nullable(Serializer base) {
        return jo -> jo == null ? JSObjects.undefined() : base.write(jo);
    }

    static Serializer mapSerializer(Serializer valueSerializer) {
        return jo -> {
            JSMapLike<JSObject> result = JSObjects.create();
            for (Map.Entry<?, ?> e : ((Map<?, ?>) jo).entrySet()) {
                result.set(e.getKey().toString(), valueSerializer.write(e.getValue()));
            }
            return result;
        };
    }

    static Serializer listSerializer(Serializer elemSerializer) {
        return jo -> {
            JSArray<JSObject> result = JSArray.create();
            for (Object o : (List<?>) jo) {
                result.push(elemSerializer.write(o));
            }
            return result;
        };
    }

    static Serializer setSerializer(Serializer elemSerializer) {
        return jo -> {
            JSArray<JSObject> result = JSArray.create();
            for (Object o : (Set<?>) jo) {
                result.push(elemSerializer.write(o));
            }
            return result;
        };
    }

    JSObject write(Object jo);
}
