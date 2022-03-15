package com.example.route.representative.services;

import com.example.route.representative.dto.Coordinate;
import com.example.route.representative.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FindRepresentativeRouteService {

    @Autowired
    private ParseCSVService parseCSVService;

    @Autowired
    private GeoJsonGenerator geoJsonGenerator;

    public void findTheMostRepresentativeRoute(String fileName){

        Map<String, List<Route>> catagorizeBasedOnPort = categorizeRouteBasedOnDestination(fileName);

        List<Coordinate> debrvToDeham = findAverageCoordinates(catagorizeBasedOnPort, "DEBRV");
        List<Coordinate> dehamToDebrv = findAverageCoordinates(catagorizeBasedOnPort, "DEHAM");

        String geoJsonDebrvToDeham = geoJsonGenerator.generateGeoJsonRoute(debrvToDeham);
        String geoJsonDehamToDebrv = geoJsonGenerator.generateGeoJsonRoute(dehamToDebrv);
    }

    private Map<String, List<Route>> categorizeRouteBasedOnDestination(String fileName){
        List<Route> routes = parseCSVService.parseRoutesFromCSV(fileName);
        return routes.stream().collect(Collectors.groupingBy(Route::getFromPort));
    }

    private List<Coordinate> findAverageCoordinates(Map<String, List<Route>> port, String from){

        List<Route> routes = port.get(from);                                                       // Get Route from A -> D
        routes.sort(Comparator.comparingInt(Route::getCount));                                     // Sort Ascending Order

        int maxLenght = routes.get(routes.size() - 1).getCount();                                  // Find Max # of Points in route from DEBRV -> DEHAM
        List<Coordinate> averageCoordinates = new ArrayList<>();

        for(int i = 0; i < maxLenght; i++) averageCoordinates.add(new Coordinate(0, 0));   // Fill Array with point objects for storage average points

        for(int routeIndex = 0; routeIndex < routes.size(); routeIndex++){

            int pointsCount = routes.get(routeIndex).getLinePoints().size();

            for(int pointIndex = 0; pointIndex < pointsCount; pointIndex++){
                averageCoordinates.get(pointIndex).setX((averageCoordinates.get(pointIndex).getX() + routes.get(routeIndex).getLinePoints().get(pointIndex).getX())/2);
                averageCoordinates.get(pointIndex).setY((averageCoordinates.get(pointIndex).getY() + routes.get(routeIndex).getLinePoints().get(pointIndex).getY())/2);
            }
        }

        return averageCoordinates;
    }

}
