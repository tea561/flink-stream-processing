package bigdata.pojo.geojson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.MultiPolygon;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoJSONObject {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("properties")
    @Expose
    private Map<String, Object> properties;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeoJSONObject(String type, Geometry geometry, Map<String, Object> properties) {
        this.type = type;
        this.geometry = geometry;
        this.properties = properties;
    }

    public GeoJSONObject() {
    }

    public MultiPolygon createMultipolygon() {
        GeometryFactory geomFactory = new GeometryFactory();
        List<List<List<List<Double>>>> coordinates = this.getGeometry().getCoordinates();
        List<Polygon> polygons = new ArrayList<>();
        for (List<List<List<Double>>> polygon : coordinates) {
            List<Coordinate> coordinatesList = new ArrayList<>();

            for (List<List<Double>> ring : polygon) {
                for (List<Double> point : ring) {
                    double x = point.get(0);
                    double y = point.get(1);
                    coordinatesList.add(new Coordinate(x, y));
                }
            }
            Polygon geomFactoryPolygon = geomFactory.createPolygon(coordinatesList.toArray(new Coordinate[0]));
            polygons.add(geomFactoryPolygon);
        }
        return geomFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
    }

    public Suburb createSuburb() {
        String name = "";
        if (properties.containsKey("name")) {
            name = properties.get("name").toString();
        }
        return new Suburb(createMultipolygon(), name);
    }

    public static class Geometry {
        private String type;
        @SerializedName("coordinates")
        @Expose
        private List<List<List<List<Double>>>> coordinates = null;

        public Geometry(String type, List<List<List<List<Double>>>> coordinates) {
            this.type = type;
            this.coordinates = coordinates;
        }

        public Geometry() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }



        public List<List<List<List<Double>>>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
            this.coordinates = coordinates;
        }

    }

}
