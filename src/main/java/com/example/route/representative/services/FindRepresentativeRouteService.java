package com.example.route.representative.services;

import com.example.route.representative.data.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FindRepresentativeRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindRepresentativeRouteService.class);

    @Autowired
    private ParseCSVService parseCSVService;

    public void findTheMostRepresentativeRoute(){

        System.out.println(categorizeRouteBasedOnDestination().keySet());

    }

    private Map<String, List<Route>> categorizeRouteBasedOnDestination(){
        List<Route> routes = parseCSVService.parseRoutesFromCSV();
        return routes.stream().collect(Collectors.groupingBy(Route::getFromPort));
    }

}
