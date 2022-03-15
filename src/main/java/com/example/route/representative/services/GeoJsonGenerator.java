package com.example.route.representative.services;

import com.example.route.representative.dto.Coordinate;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * This class generates the GeoJSON text where you see the points on the map (geojson.io)
 */
@Service
public class GeoJsonGenerator {

    public String generateGeoJsonRoute(List<Coordinate> coordinates){

        StringBuilder geojson = new StringBuilder();

        geojson.append("""
        {
          "type": "FeatureCollection",
          "features": [""".trim());

        for(Coordinate coordinate: coordinates){
            geojson.append(String.format("""
                {
                  "type": "Feature",
                  "properties": {},
                  "geometry": {
                    "type": "Point",
                    "coordinates": [
                      %f,
                      %f
                    ]
                  }
                },
                """, coordinate.getY(), coordinate.getX()));
        }

        String substring = geojson.substring(0, geojson.length() - 2);
        substring += "]}";

        return substring;
    }

}
