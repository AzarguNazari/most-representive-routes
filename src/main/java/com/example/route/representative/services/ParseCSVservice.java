package com.example.route.representative.services;

import com.example.route.representative.Point;
import com.example.route.representative.Route;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ParseCSVservice {

    private static final String SAMPLE_CSV_FILE_PATH = "DEBRV_DEHAM_historical_routes.csv";
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseCSVservice.class);

    private static List<Point> parsePoint(String points){
        return Stream.of(points.split("],"))
                .map(point -> point.replaceAll("\\[", ""))
                .map(point -> point.replaceAll(",", ""))
                .map(point -> {
                    System.out.println(point);
                    var list = List.of(point.replaceAll("\\[|\\,|\\]", "").split(" "));
                    System.out.println(list.size());
                    return list;
                })
                .map(point -> {
                    System.out.println(point);
                    double x = Double.parseDouble(point.get(0));
                    double y = Double.parseDouble(point.get(1));
                    long epoch = Long.parseLong(point.get(2));
//                    double speed = point.get(3).equals("null") ? 0.0 : Double.parseDouble(point.get(3));
                    LOGGER.error("{}", x);
                    LOGGER.error("{}", y);
                    LOGGER.error("{}", epoch);
//                    LOGGER.error("{}", speed);
//                  return new Point(x, y, epoch, speed);
                    return new Point();
                })
                .toList();
    }

//"id","from_seq","to_seq","from_port","to_port","leg_duration","count","points"
    private static Route parseLine(CSVRecord record){
        Route route = new Route();
        route.setId(record.get(0));
        route.setFromSeq(record.get(1));
        route.setToSeq(record.get(2));
        route.setFromPort(record.get(3));
        route.setToPort(record.get(4));
        route.setLegDuration(Integer.parseInt(record.get(5)));
        route.setCount(Integer.parseInt(record.get(6)));
        route.setLinePoints(parsePoint(record.get(7)));
        return route;
    }

    public static List<Route> parseRouteFromCSV() {


        List<Route> routes = new ArrayList<>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {

            List<CSVRecord> records = csvParser.getRecords();


            for(int index = 1; index < records.size(); index++){
                routes.add(parseLine(records.get(index)));
                break;
            }
        } catch (IOException ex) {
            LOGGER.error("Some error happened {}", ex.getMessage());
        }

        return null;
    }

}
