package bigdata.deserializers;

import bigdata.pojo.geojson.GeoJSONObject;
import com.google.gson.Gson;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class GeometryDeserializer implements DeserializationSchema<GeoJSONObject.Geometry> {
    private static final Gson gson = new Gson();

    @Override
    public GeoJSONObject.Geometry deserialize(byte[] bytes) throws IOException {
        return gson.fromJson(new String(bytes), GeoJSONObject.Geometry.class);
    }

    @Override
    public boolean isEndOfStream(GeoJSONObject.Geometry geometry) {
        return false;
    }

    @Override
    public TypeInformation<GeoJSONObject.Geometry> getProducedType() {
        return TypeInformation.of(GeoJSONObject.Geometry.class);
    }
}
