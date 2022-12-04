package cloud.deuterium.redis.countries.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.geo.Point;

import java.io.IOException;

/**
 * Created by Milan Stojkovic 01-Dec-2022
 */
public class PointSerializer extends StdSerializer<Point> {

    protected PointSerializer() {
        super(Point.class);
    }

    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("lat", point.getX());
        jsonGenerator.writeNumberField("lng", point.getY());
        jsonGenerator.writeEndObject();
    }
}
