package com.example.route.representative.services;

import com.example.route.representative.dto.Point;
import com.example.route.representative.dto.Route;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/***
 * This class parses the CSV file into records
 */

@Service
public class ParseCSVService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseCSVService.class);

    private List<Point> parsePoint(String points){
        return Stream.of(points.split("],"))
                .map(point -> point.replace("[", "").trim())
                .map(point -> point.replace("]", ""))
                .map(point -> point.replace(",", ""))
                .map(point -> List.of(point.split(" ")))
                .map(point -> {
                    double x = Double.parseDouble(point.get(0));
                    double y = Double.parseDouble(point.get(1));
                    long epoch = Long.parseLong(point.get(2));
                    double speed = point.get(3).equals("null") ? 0.0 : Double.parseDouble(point.get(3));
                    return new Point(x, y, epoch, speed);
                })
                .toList();
    }

    private Route parseLine(CSVRecord csvRow){
        return new Route(csvRow.get(0),
                         csvRow.get(1),
                         csvRow.get(2),
                         csvRow.get(3),
                         csvRow.get(4),
                         Integer.parseInt(csvRow.get(5)),
                         Integer.parseInt(csvRow.get(6)),
                         parsePoint(csvRow.get(7)));
    }

    public List<Route> parseRoutesFromCSV(String fileName) {

        List<Route> routes = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {

            List<CSVRecord> records = csvParser.getRecords();
            records.subList(1, records.size() - 1).forEach(record -> routes.add(parseLine(record)));

        } catch (IOException ex) {
            LOGGER.error("Some error happened {}", ex.getMessage());
        }

        return routes;
    }

}
