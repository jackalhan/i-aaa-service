package com.iaaa.service;


import com.iaaa.dto.Accident;
import com.iaaa.dto.AccidentMetrics;
import com.iaaa.dto.AnyAccidentResponse;
import com.iaaa.dto.Coordinates;
import com.iaaa.models.AccidentHistory;
import com.iaaa.models.AccidentHistoryDao;
import com.iaaa.outsource.HereRouteApi;
import com.iaaa.outsource.WeatherDataApi;
import com.iaaa.outsource.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Created by jackalhan on 3/22/16.
 */
@RestController
public class AnyAccidentController {

    private final AtomicLong counter = new AtomicLong();

    // Rest sample query link
    // http://localhost:8080/anyAccidentOverHere?lat=36.2152214&lon=-94.4473324&speedOfVehicle=55
    @RequestMapping("/anyAccidentOverHere")
    public AnyAccidentResponse anyAccidentOverHere(@RequestParam(value = "lon") double lon,
                                                   @RequestParam(value = "lat") double lat,
                                                   @RequestParam(value = "speedOfVehicle") int speedOfVehicle
    ) throws CloneNotSupportedException

    {
        AccidentMetrics accidentMetrics = querySpeedLimitData(queryWeatherData(new AccidentMetrics(new Coordinates(lon, lat), speedOfVehicle)));


        return new AnyAccidentResponse(accidentMetrics.getWeatherCondition() + "=====" + accidentMetrics.getRoadCondition() + "==========" + accidentMetrics.getSpeedLimitOfRoad());
    }

    //http://localhost:8080/anyAccidentBasedOnCoordinates?lon=-94.0303089&lat=33.410011&speedOfVehicle=20
    @RequestMapping("/anyAccidentBasedOnCoordinates")
    public List<AccidentHistory> anyAccidentBasedOnCoordinates(@RequestParam(value = "lon") double lon,
                                                               @RequestParam(value = "lat") double lat,
                                                               @RequestParam(value = "speedOfVehicle") int speedOfVehicle
    ) throws CloneNotSupportedException

    {
        AccidentMetrics accidentMetrics = querySpeedLimitData(queryWeatherData(new AccidentMetrics(new Coordinates(lon, lat), speedOfVehicle)));
        List<Coordinates> coordinatesList = getSimplifiedCoordinatesInCircleArea(lon, lat, 2, 0.01, 10);
        List<AccidentHistory> accidentHistoryList = new ArrayList<AccidentHistory>();
        for (Coordinates coordinates : coordinatesList) {
            List<AccidentHistory> accidentHistorySubList = accidentHistoryDao.findAccidentByCoordinates(coordinates.getLat(), coordinates.getLon());
            int sameRecordCounter;
            for (AccidentHistory accidentSubHistory : accidentHistorySubList) {
                sameRecordCounter = 0;
                for (AccidentHistory accidentHistory : accidentHistoryList) {
                    if ((accidentHistory.getAccidentNumber() == accidentSubHistory.getAccidentNumber())
                            && (accidentHistory.getFatalNumber().equals(accidentSubHistory.getFatalNumber()))
                            && (accidentHistory.getAccidentTime().equals(accidentSubHistory.getAccidentTime()))) {
                        sameRecordCounter = sameRecordCounter + 1;
                    }
                }
                if (sameRecordCounter == 0)
                    accidentHistoryList.add(accidentSubHistory);
            }

        }
        System.out.println(accidentHistoryList.toString());
        return accidentHistoryList;
    }

    public AccidentMetrics queryWeatherData(AccidentMetrics metrics) throws CloneNotSupportedException {

        System.out.println("......................................................");
        AccidentMetrics accidentMetrics = (AccidentMetrics) metrics.clone();
        WeatherDataApiResponse weatherDataApiResponse = new WeatherDataApi(accidentMetrics.getCoordinates()).query();
        accidentMetrics.setWeatherCondition(String.valueOf(weatherDataApiResponse.getMain().getTemp()));
        System.out.println("Data, after queried for Weather Data ");
        System.out.println(accidentMetrics.toString());
        return accidentMetrics;
    }

    public AccidentMetrics querySpeedLimitData(AccidentMetrics metrics) throws CloneNotSupportedException {
        System.out.println("......................................................");
        AccidentMetrics accidentMetrics = (AccidentMetrics) metrics.clone();
        /* uncomment the following part it in order to get data  From Google */
        //GoogleRoadsApiResponse googleRoadsApiResponse = new GoogleRoadsApi(accidentMetrics.getCoordinates()).query();
        //accidentMetrics.setSpeedLimitOfRoad(googleRoadsApiResponse.getGoogleRoadsSpeedLimits().getSpeedLimit());

        /* uncomment the following part it in order to get data From Here */
        HereRouteDataApiResponse hereRouteDataApiResponse = new HereRouteApi(accidentMetrics.getCoordinates()).query();
        accidentMetrics.setSpeedLimitOfRoad(hereRouteDataApiResponse.getResponse().getLink()[0].getSpeedLimit());
        System.out.println("Data, after queried for Road Speed Limit Data ");
        System.out.println(accidentMetrics.toString());
        return accidentMetrics;
    }

    @RequestMapping("/calculateCoordinatesInCircleArea")
    public List<Coordinates> calculateCoordinatesInCircleArea(@RequestParam(value = "lon") double lon,
                                                              @RequestParam(value = "lat") double lat,
                                                              @RequestParam(value = "totalRadius") double totalRadius,
                                                              @RequestParam(value = "radiusIncrement") double radiusIncrement,
                                                              @RequestParam(value = "degreeIncrement") int degreeIncrement) {
        System.out.println("......................................................");
        System.out.println("Calculating New Coordinates within " + totalRadius + " mile(s) to " + lon + "," + lat);
        List<Coordinates> coordinates = new ArrayList<Coordinates>();
        for (double initialRadius = 0; initialRadius <= totalRadius; initialRadius = initialRadius + radiusIncrement) {
            for (double initialDegree = 0; initialDegree <= 360; initialDegree = initialDegree + degreeIncrement) {
                if (initialRadius > 0) {
                    Coordinates coordinate = calculateCoordinatesInCircleEdge(lon, lat, initialRadius, initialDegree);
                    coordinates.add(coordinate);
                }
            }
        }
        System.out.println("Total found coordinates : " + coordinates.size());
        return coordinates;
    }

    //http://localhost:8080/getSimplifiedCoordinatesInCircleArea?lon=-92.375321&lat=34.670255&totalRadius=0.1&radiusIncrement=0.01&degreeIncrement=10
    @RequestMapping("/getSimplifiedCoordinatesInCircleArea")
    public List<Coordinates> getSimplifiedCoordinatesInCircleArea(@RequestParam(value = "lon") double lon,
                                                                  @RequestParam(value = "lat") double lat,
                                                                  @RequestParam(value = "totalRadius") double totalRadius,
                                                                  @RequestParam(value = "radiusIncrement") double radiusIncrement,
                                                                  @RequestParam(value = "degreeIncrement") int degreeIncrement) {


        return simplifyCoordinatesSet(calculateCoordinatesInCircleArea(lon, lat, totalRadius, radiusIncrement, degreeIncrement));

    }

    @RequestMapping("/calculateCoordinatesInCircleEdge")
    public Coordinates calculateCoordinatesInCircleEdge(@RequestParam(value = "lon") double lon,
                                                        @RequestParam(value = "lat") double lat,
                                                        @RequestParam(value = "radius") double radius,
                                                        @RequestParam(value = "degree") double degree) {
        System.out.println("......................................................");
        System.out.println("Calculating New Coordinates within " + radius + " mile(s) to " + lon + "," + lat + " with the degree " + degree);
        double distance = radius / 3956;
        Coordinates coordinate = new Coordinates();
        double radiansOfDegree = Math.toRadians(degree);
        double radiansOfLat = Math.toRadians(lat);

        if (degree == 0) {
            coordinate.setLat(lat + distance);
            coordinate.setLon(lon + distance);
        } else if (degree == 180) {
            coordinate.setLat(lat - distance);
            coordinate.setLon(lon - distance);
        } else {

            if (degree == 90) {

                coordinate.setLat(Math.toDegrees(Math.asin(Math.sin(radiansOfLat) * Math.cos(distance))));

            } else {
                coordinate.setLat(Math.toDegrees(Math.asin((Math.sin(radiansOfLat) * Math.cos(distance)) + (Math.cos(radiansOfLat) * Math.sin(distance) * Math.cos(degree)))));
            }
            double radiansOfNewLat = Math.toRadians(coordinate.getLat());
            double dlon = Math.atan2(Math.sin(radiansOfDegree) * Math.sin(distance) * Math.cos(radiansOfLat), Math.cos(distance) - (Math.sin(radiansOfLat) * Math.sin(radiansOfNewLat)));
            double modLon = (lon - dlon + 180) % 360;
            coordinate.setLon(modLon - 180);
        }
        System.out.print(" " + coordinate.toString());
        return coordinate;

    }


    public List<Coordinates> simplifyCoordinatesSet(List<Coordinates> coordinatesSet) {

        System.out.println("......................................................");

        DecimalFormat decimalFormat;
        if (coordinatesSet.size() <= 1000) {
            decimalFormat = new DecimalFormat("###.###");
        } else if (coordinatesSet.size() > 1000 && coordinatesSet.size() <= 10000) {
            decimalFormat = new DecimalFormat("###.##");
        } else {
            decimalFormat = new DecimalFormat("###.#");
        }
        System.out.println("Simplifying New Coordinates according to " + decimalFormat+ " format");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        Map<Double, Long> coordinatesByParsedLat = coordinatesSet.stream().
                collect(Collectors.groupingBy(c -> Double.parseDouble(decimalFormat.format(c.getLat())), Collectors.mapping((Coordinates c) -> c, Collectors.counting())));

        Map<Double, Long> coordinatesByParsedLon = coordinatesSet.stream().
                collect(Collectors.groupingBy(c -> Double.parseDouble(decimalFormat.format(c.getLon())), Collectors.mapping((Coordinates c) -> c, Collectors.counting())));

        List<Coordinates> coordinatesList = new ArrayList<Coordinates>();

        for (Map.Entry<Double, Long> entryLat : coordinatesByParsedLat.entrySet()) {
            for (Map.Entry<Double, Long> entryLon : coordinatesByParsedLon.entrySet()) {
                Coordinates coordinates = new Coordinates();
                coordinates.setLat((Double) entryLat.getKey());
                coordinates.setLon((Double) entryLon.getKey());
                coordinatesList.add(coordinates);
            }
        }

        System.out.println(coordinatesList.toString());
        return coordinatesList;
    }

    @Autowired
    private AccidentHistoryDao accidentHistoryDao;

}
