package cloud.deuterium.redis.countries.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.data.geo.Point;

import java.io.IOException;

/**
 * Created by Milan Stojkovic 01-Dec-2022
 */
public class PointDeserializer extends StdDeserializer<Point> {

    protected PointDeserializer() {
        super(Point.class);
    }

    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        double x = 0d;
        double y = 0d;

        JsonNode lat = node.get("lat");
        if (!lat.isNull()) {
            x = lat.asDouble();
        }
        JsonNode lng = node.get("lng");
        if (!lng.isNull()) {
            y = lng.asDouble();
        }
        return new Point(x, y);
    }
}
