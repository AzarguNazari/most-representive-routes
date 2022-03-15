package com.example.route.representative;

import com.example.route.representative.dto.Coordinate;
import com.example.route.representative.services.GeoJsonGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class RouteRepresentativeApplicationTests {

	@Autowired
	private GeoJsonGenerator geoJsonGenerator;

	@Test
	@DisplayName("Test the GeoJSON generator's output")
	void GeoJsonGenerator_givenAcorrectCoordinates_theGeneratedOutIsAcorrectGeoJson(){

		List<Coordinate> coordinateList = List.of(new Coordinate(1.0, 1.0));

		String generatedGeoJson = geoJsonGenerator.generateGeoJsonRoute(coordinateList);

		Assertions.assertNotNull(generatedGeoJson);
		Assertions.assertTrue(generatedGeoJson.length() > 0);
		Assertions.assertTrue(generatedGeoJson.contains("1.0"));
	}


}
