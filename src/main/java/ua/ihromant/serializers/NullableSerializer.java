package ua.ihromant.serializers;

import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSObjects;

public class NullableSerializer implements Serializer {
    private final Serializer notNull;

    private NullableSerializer(Serializer notNull) {
        this.notNull = notNull;
    }

    @Override
    public JSObject write(Object object) {
        if (object == null) {
            return JSObjects.undefined();
        } else {
            return notNull.write(object);
        }
    }

    public static Serializer wrap(Serializer notNull) {
        return new NullableSerializer(notNull);
    }
}
