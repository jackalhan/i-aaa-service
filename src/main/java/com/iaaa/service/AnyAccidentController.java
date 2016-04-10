package com.iaaa.service;

import com.iaaa.dto.Accident;
import com.iaaa.dto.AccidentMetrics;
import com.iaaa.dto.AnyAccidentResponse;
import com.iaaa.dto.Coordinates;
import com.iaaa.models.AccidentHistory;
import com.iaaa.outsource.HereRouteApi;
import com.iaaa.outsource.WeatherDataApi;
import com.iaaa.outsource.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jackalhan on 3/22/16.
 */
@RestController
public class AnyAccidentController {

    private static final String template = "Hello, %s!";
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
        /* uncomment it in order to get data  From Google */
        //GoogleRoadsApiResponse googleRoadsApiResponse = new GoogleRoadsApi(accidentMetrics.getCoordinates()).query();
        //accidentMetrics.setSpeedLimitOfRoad(googleRoadsApiResponse.getGoogleRoadsSpeedLimits().getSpeedLimit());

        /* uncomment it in order to get data  From Here */
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
                    Coordinates coordinate = calculateCoordinateInCircleEdge(lon, lat, initialRadius, initialDegree);
                    coordinates.add(coordinate);
                }
            }
        }
        System.out.println("Total found coordinates : " + coordinates.size());
        return coordinates;
    }

    @RequestMapping("/calculateCoordinateInCircleEdge")
    public Coordinates calculateCoordinateInCircleEdge(@RequestParam(value = "lon") double lon,
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

/*    public Coordinates simlifyCoordinatesSet(List<Coordinates> coordinatesSet)
    {


    }*/

/*
    public double radiansOfPosition(double degree)
    {
        return degree * (Math.PI / 180);
    }*/

}
