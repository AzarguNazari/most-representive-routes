package com.example.route.representative.services;

import com.example.route.representative.data.Coordinate;
import com.example.route.representative.data.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FindRepresentativeRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindRepresentativeRouteService.class);

    @Autowired
    private ParseCSVService parseCSVService;

    @Autowired
    private GeoJsonGenerator geoJsonGenerator;

    public void findTheMostRepresentativeRoute(){

        List<Coordinate> debrvToDeham = findAverageCoordinates("DEBRV");
        List<Coordinate> dehamToDebrv = findAverageCoordinates("DEHAM");

        System.out.println(geoJsonGenerator.generateGeoJsonRoute(debrvToDeham));
    }

    private Map<String, List<Route>> categorizeRouteBasedOnDestination(){
        List<Route> routes = parseCSVService.parseRoutesFromCSV();
        return routes.stream().collect(Collectors.groupingBy(Route::getFromPort));
    }

    private List<Coordinate> findAverageCoordinates(String from){

        List<Route> routes = categorizeRouteBasedOnDestination().get(from);  // Get Route from DEBRV -> DEHAM
        routes.sort(Comparator.comparingInt(Route::getCount));                  // Sort Ascending Order

        int maxLenght = routes.get(routes.size() - 1).getCount();   // Find Max # of Points in route from DEBRV -> DEHAM
        List<Coordinate> averageCoordinates = new ArrayList<>();

        for(int i = 0; i < maxLenght; i++) averageCoordinates.add(new Coordinate(0, 0));                   // Fill Array with point objects for storage average points

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
