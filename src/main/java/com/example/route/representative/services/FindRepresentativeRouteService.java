package com.example.route.representative.services;

import com.example.route.representative.dto.Coordinate;
import com.example.route.representative.dto.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * This class applies the algorithm to find the most representative route
 */
@Service
public record FindRepresentativeRouteService(ParseCSVService parseCSVService, GeoJsonGenerator geoJsonGenerator){

    private static final Logger LOGGER = LoggerFactory.getLogger(FindRepresentativeRouteService.class);

    public void findTheMostRepresentativeRoute(String fileName){

        Map<String, List<Route>> categorizedBasedOnPort = categorizeRouteBasedOnDestination(fileName);

        List<Coordinate> debrvToDeham = findAverageCoordinates(categorizedBasedOnPort, "DEBRV");
        List<Coordinate> dehamToDebrv = findAverageCoordinates(categorizedBasedOnPort, "DEHAM");

        String geoJsonDebrvToDeham = geoJsonGenerator.generateGeoJsonRoute(debrvToDeham);
        String geoJsonDehamToDebrv = geoJsonGenerator.generateGeoJsonRoute(dehamToDebrv);

        LOGGER.debug("Most Representative Path from DEBRV to DEHAM {}", dehamToDebrv);
    }

    private Map<String, List<Route>> categorizeRouteBasedOnDestination(String fileName){
        List<Route> routes = parseCSVService.parseRoutesFromCSV(fileName);
        return routes.stream().collect(Collectors.groupingBy(Route::fromPort));
    }

    private List<Coordinate> findAverageCoordinates(Map<String, List<Route>> port, String from){

        List<Route> routes = port.get(from);                                                       // Get Route from S -> D
        routes.sort(Comparator.comparingInt(Route::count));                                     // Sort Ascending Order

        int maxLenght = routes.get(routes.size() - 1).count();                                  // Find Max # of Points in route from DEBRV -> DEHAM
        List<Coordinate> averageCoordinates = new ArrayList<>();

        for(int i = 0; i < maxLenght; i++) averageCoordinates.add(new Coordinate(0, 0));      // Fill Array with point objects for storage average points

        for (Route route : routes) {

            int pointsCount = route.linePoints().size();

            for (int pointIndex = 0; pointIndex < pointsCount; pointIndex++) {
                averageCoordinates.get(pointIndex).setX((averageCoordinates.get(pointIndex).getX() + route.linePoints().get(pointIndex).x()) / 2);
                averageCoordinates.get(pointIndex).setY((averageCoordinates.get(pointIndex).getY() + route.linePoints().get(pointIndex).y()) / 2);
            }
        }

        return averageCoordinates;
    }

}
