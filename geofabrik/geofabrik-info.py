import osmium
import json
import folium
from folium import PolyLine, Polygon

import shapely.wkb as wkblib

geojsonfab = osmium.geom.GeoJSONFactory()
wkbfab = osmium.geom.WKBFactory()
highway_values = ['primary_link', 'secondary_link']

class BoundingBoxHandler(osmium.SimpleHandler):
    def __init__(self, city):
        super(BoundingBoxHandler, self).__init__()
        self.city = city
        self.multipolygon = None

    def area(self, a):
        if 'place' in a.tags and a.tags['place'] == 'city':
            if 'name' in a.tags and a.tags['name'] ==  self.city:
                self.save_multipolygon(wkbfab.create_multipolygon(a))

    def save_multipolygon(self, mp):
        poly = wkblib.loads(mp, hex=True)
        self.multipolygon = poly
                
class AreaFilter(osmium.SimpleHandler):
    def __init__(self, multipolygon) -> None:
        super(AreaFilter, self).__init__()
        self.areas = set()
        self.multipolygon = multipolygon
        self.m = folium.Map(location=[44.8125, 20.4612], zoom_start=10)
        self.first = True

    def area(self, a):
        print(a)
        if 'place' in a.tags and a.tags['place'] == 'suburb':
            print(a)
            polygon = wkbfab.create_multipolygon(a)
            poly = wkblib.loads(polygon, hex=True)

            if poly.intersects(self.multipolygon):
                self.areas.add(a)
                #coords = [(n.lat, n.lon) for n in a.nodes]
                #polygon = Polygon(coords)
                #polygon.add_to(self.m)
                self.save_object_to_json(geojsonfab.create_multipolygon(a), a.tags)

    def save_object_to_json(self, geojson, tags):
        geom = json.loads(geojson)
        folium.GeoJson(data=geom).add_to(self.m)
        if geom:
            feature = {'type': 'Feature', 'geometry': geom, 'properties': dict(tags)}
            with open("areas.geojson", "a") as f:
                if self.first:
                    self.first = False
                    f.write("[")
                else:
                    f.write(",")
                f.write(json.dumps(feature))

if __name__ == '__main__':
    file = "serbia-latest.osm.pbf"

    handler = BoundingBoxHandler('Београд')
    handler.apply_file(file)

    # ways = LinkFilter(handler.multipolygon)
    # ways.apply_file(file, locations=True, idx='flex_mem')

    # ways.m.save('belgrade_map.html')

    areas = AreaFilter(handler.multipolygon)
    areas.apply_file(file, locations=True, idx='flex_mem')

    areas.m.save('belgrade_areas.html')

    with open("areas.geojson", "a") as f:
        f.write("]")

    
