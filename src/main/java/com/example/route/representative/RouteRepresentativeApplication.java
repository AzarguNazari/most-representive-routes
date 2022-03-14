package com.example.route.representative;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.example.route.representative.services.ParseCSVservice.parseRouteFromCSV;

@SpringBootApplication
public class RouteRepresentativeApplication implements ApplicationRunner {

	private static final String SAMPLE_CSV_FILE_PATH = "DEBRV_DEHAM_historical_routes.csv";

	public static void main(String[] args) {
		SpringApplication.run(RouteRepresentativeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		parseRouteFromCSV();
	}

}

