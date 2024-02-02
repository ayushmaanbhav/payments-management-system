package com.ayushmaanbhav.commons.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointSerializer extends StdSerializer<Point> {

    public PointSerializer() {
        this(null);
    }

    public PointSerializer(Class<Point> t) {
        super(t);
    }

    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(point.toString());
    }
}
