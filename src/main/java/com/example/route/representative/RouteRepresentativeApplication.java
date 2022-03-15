package com.example.route.representative;

import com.example.route.representative.services.FindRepresentativeRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RouteRepresentativeApplication implements ApplicationRunner {

	@Autowired
	private FindRepresentativeRouteService findRepresentativeRouteService;

	private static final String SAMPLE_CSV_FILE_PATH = "DEBRV_DEHAM_historical_routes.csv";

	public static void main(String[] args) {
		SpringApplication.run(RouteRepresentativeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		findRepresentativeRouteService.findTheMostRepresentativeRoute(SAMPLE_CSV_FILE_PATH);
	}

}

