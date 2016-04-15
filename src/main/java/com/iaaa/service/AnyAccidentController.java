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
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


/**
 * Created by jackalhan on 3/22/16.
 */
@RestController
public class AnyAccidentController {

    private final AtomicLong counter = new AtomicLong();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

    // Rest sample query link
    // http://localhost:8080/anyAccidentOverHere?lat=36.2152214&lon=-94.4473324&speedOfVehicle=55
    @RequestMapping("/anyAccidentOverHere")
    public AnyAccidentResponse anyAccidentOverHere(@RequestParam(value = "lon") double lon,
                                                   @RequestParam(value = "lat") double lat,
                                                   @RequestParam(value = "speedOfVehicle") int speedOfVehicle
    ) throws CloneNotSupportedException, ParseException

    {
        AccidentMetrics accidentMetrics = querySpeedLimitData(queryWeatherData(new AccidentMetrics(new Coordinates(lon, lat), speedOfVehicle)));
        List<AccidentHistory> accidentHistoryList = anyAccidentBasedOnCoordinates(accidentMetrics.getCoordinates().getLon(), accidentMetrics.getCoordinates().getLat());
        ConcurrentHashMap<AccidentHistory, Double> scoreList = new ConcurrentHashMap<>();

        Date currentTime = new Date();
        //int currentDateScore = calculateDateScore(currentTime);
        int currentTimeScore = calculateTimeScore(currentTime);
        double currentSpeedScore = calculateSpeedScore(speedOfVehicle, accidentMetrics.getSpeedLimitOfRoad());
        int currentWeatherConditionScore = calculateWeatherConditionScore(accidentMetrics.getWeatherCondition());
        double currentRoadConditionScore = calculateRoadConditionScore(accidentMetrics.getRoadCondition());

        double currentTotalScore = currentTimeScore + currentSpeedScore + currentWeatherConditionScore + currentRoadConditionScore;

        for (AccidentHistory accidentHistory : accidentHistoryList) {


            int dateScore = calculateDateScore(accidentHistory.getAccidentTime());
            int timeScore = calculateTimeScore(accidentHistory.getAccidentTime());
            int weatherConditionScore = calculateWeatherConditionScore(accidentHistory.getWeatherCondition());
            int roadConditionScore = calculateRoadConditionScore(accidentHistory.getRoadCondition());
            double totalScore = dateScore + timeScore + weatherConditionScore + roadConditionScore;

            if ((currentWeatherConditionScore >= weatherConditionScore) &&
                    (currentRoadConditionScore >= roadConditionScore ) &&
                    (currentSpeedScore >= 0) &&
                    (dateScore >= 3) &&
                    ( currentTimeScore >= timeScore)) {
                System.out.println(" ::::::::::::::::::::::::::::::::::::::::::::");
                System.out.println(" Current Total Score => " + currentTotalScore);
                System.out.println(" Accident History Score => " + totalScore);
                scoreList.put(accidentHistory, totalScore);
            }
        }

        int accidentCount = scoreList.size();
        int numberOfInjured = 0;
        int numberOfKilled = 0;
        boolean hasAccident = false;
        for (ConcurrentHashMap.Entry<AccidentHistory, Double> entry : scoreList.entrySet()) {
            numberOfInjured = numberOfInjured + entry.getKey().getNumberOfInjured();
            numberOfKilled = numberOfKilled + entry.getKey().getNumberOfKilled();
            hasAccident = true;
        }


        return new AnyAccidentResponse(
                hasAccident,
                accidentCount,
                numberOfInjured,
                numberOfKilled,
                "BE CAREFUL! You are in a risk with current conditions. In the past total " + accidentCount + " accident(s) had occured with similar conditions including total number of " + numberOfInjured + " injured and " + numberOfKilled + " killed people."
        );
    }

    private int calculateWeatherConditionScore(String weatherCondition) {
        int score = 0;
        String condition = weatherCondition.toLowerCase();

        if (condition.contains("cloud") || condition.contains("rain") ||
                condition.contains("storm") ||
                condition.contains("wind") || condition.contains("drizzle")) {
            score = 2;
        } else if (condition.contains("clear") || condition.contains("dry") ||
                condition.contains("sunny") || condition.contains("fair")) {
            score = 1;
        } else if (condition.contains("cold") || condition.contains("foogy") ||
                condition.contains("fog") || condition.contains("mist") || condition.contains("ic")
                || condition.contains("poor") || condition.contains("sleet") || condition.contains("wet") || condition.contains("winter")) {
            score = 3;
        } else if (condition.contains("dark") || condition.contains("smoke")) {
            score = 4;
        } else {
            score = 0;
        }
        return score;

    }

    private int calculateRoadConditionScore(String roadCondition) {
        int score = 0;
        String condition = roadCondition.toLowerCase();

        if (condition.contains("rain") || condition.contains("flood") ||
                condition.contains("wet") ||
                condition.contains("damp")) {
            score = 2;
        } else if (condition.contains("clear") || condition.contains("dry") ||
                condition.contains("good") || condition.contains("fair")
                || condition.contains("grade") || condition.contains("paveme")
                || condition.contains("normal") || condition.contains("rual")
                || condition.contains("straight")
                ) {
            score = 1;
        } else if (condition.contains("snow") || condition.contains("ic") ||
                condition.contains("fog") || condition.contains("mist") || condition.contains("slash")
                || condition.contains("oily") || condition.contains("slick") || condition.contains("wet") || condition.contains("winter")) {
            score = 3;
        } else if (condition.contains("dark") || condition.contains("smoke")) {
            score = 4;
        } else if (condition.contains("curve") || condition.contains("gravel")) {
            score = 5;
        } else {
            score = 0;
        }
        return score;

    }

    private double calculateSpeedScore(int speedOfVehicle, double speedLimit) {
         /*
            *********************************************************
            *********************************************************
            */
            /*
              Speed Limit Increment = 0.5
            */
        double incrementThreshold = speedOfVehicle - speedLimit;
        double speedScore = 0;
        for (int i = 0; i <= incrementThreshold; i = i + 5)
        {
            speedScore = speedScore + 0.5;
        }
        return speedScore;

    }

    private int calculateDateScore(Date accidentRecord) throws ParseException {
          /*
               Recent Period : currentDate - 01/01/2012
               Mid Period    : 12/31/2011  - 01/01/2008
               Old Period    : 12/31/2007  - 01/01/2004
             */
        Date recentDataStart = dateFormat.parse("2012-01-01");
        Date midDataStart = dateFormat.parse("2008-01-01");
        Date oldDateStart = dateFormat.parse("2004-01-01");

        int dateScore = 0;

        if (accidentRecord.compareTo(recentDataStart) > 0) {
            dateScore = 5;
        } else if ((accidentRecord.compareTo(recentDataStart) < 0) && (accidentRecord.compareTo(midDataStart) > 0)) {
            dateScore = 3;
        } else {
            dateScore = 1;
        }
        return dateScore;
    }

    private int calculateTimeScore(Date accidentRecord) {
          /*
            *********************************************************
            *********************************************************
            */
            /*
              Early Morning : 4:01 AM - 8:00 AM
              Morning       : 8:01 AM - 12:00 PM
              Afternoon     : 12:01 PM - 5:00 PM
              Evening       : 5:01 PM - 11:00 PM
              Middle Night  : 11:01 PM - 4:00 PM
            */

        int timeScore = 0;
        DateTime accidentHistoryTime = new DateTime(accidentRecord);

        DateTime earlyMorningTimeStart = new DateTime().withYear(accidentHistoryTime.getYear()).
                withMonthOfYear(accidentHistoryTime.getMonthOfYear()).
                withDayOfMonth(accidentHistoryTime.getDayOfMonth()).
                withHourOfDay(4).withMinuteOfHour(0).withSecondOfMinute(1);

        DateTime morningTimeStart = new DateTime().withYear(accidentHistoryTime.getYear()).
                withMonthOfYear(accidentHistoryTime.getMonthOfYear()).
                withDayOfMonth(accidentHistoryTime.getDayOfMonth()).
                withHourOfDay(8).withMinuteOfHour(0).withSecondOfMinute(1);

        DateTime afternoonTimeStart = new DateTime().withYear(accidentHistoryTime.getYear()).
                withMonthOfYear(accidentHistoryTime.getMonthOfYear()).
                withDayOfMonth(accidentHistoryTime.getDayOfMonth()).
                withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(1);

        DateTime eveningTimeStart = new DateTime().withYear(accidentHistoryTime.getYear()).
                withMonthOfYear(accidentHistoryTime.getMonthOfYear()).
                withDayOfMonth(accidentHistoryTime.getDayOfMonth()).
                withHourOfDay(17).withMinuteOfHour(0).withSecondOfMinute(1);

        DateTime middleNightTimeStart = new DateTime().withYear(accidentHistoryTime.getYear()).
                withMonthOfYear(accidentHistoryTime.getMonthOfYear()).
                withDayOfMonth(accidentHistoryTime.getDayOfMonth()).
                withHourOfDay(23).withMinuteOfHour(0).withSecondOfMinute(1);


        int absoluteHourDiff = Math.abs(Hours.hoursBetween(accidentHistoryTime, earlyMorningTimeStart).getHours() % 24);
        timeScore = 4;
        if (absoluteHourDiff > Math.abs(Hours.hoursBetween(accidentHistoryTime, morningTimeStart).getHours() % 24)) {
            absoluteHourDiff = Math.abs(Hours.hoursBetween(accidentHistoryTime, morningTimeStart).getHours() % 24);
            timeScore = 1;
        }
        if (absoluteHourDiff > Math.abs(Hours.hoursBetween(accidentHistoryTime, afternoonTimeStart).getHours() % 24)) {
            absoluteHourDiff = Math.abs(Hours.hoursBetween(accidentHistoryTime, afternoonTimeStart).getHours() % 24);
            timeScore = 2;
        }
        if (absoluteHourDiff > Math.abs(Hours.hoursBetween(accidentHistoryTime, eveningTimeStart).getHours() % 24)) {
            absoluteHourDiff = Math.abs(Hours.hoursBetween(accidentHistoryTime, eveningTimeStart).getHours() % 24);
            timeScore = 5;
        }
        if (absoluteHourDiff > Math.abs(Hours.hoursBetween(accidentHistoryTime, middleNightTimeStart).getHours() % 24)) {
            absoluteHourDiff = Math.abs(Hours.hoursBetween(accidentHistoryTime, middleNightTimeStart).getHours() % 24);
            timeScore = 3;
        }
        return timeScore;

    }

    //http://localhost:8080/anyAccidentBasedOnCoordinates?lon=-94.0303089&lat=33.410011&speedOfVehicle=20
    @RequestMapping("/anyAccidentBasedOnCoordinates")
    public List<AccidentHistory> anyAccidentBasedOnCoordinates(@RequestParam(value = "lon") double lon,
                                                               @RequestParam(value = "lat") double lat) throws CloneNotSupportedException

    {
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
       // System.out.println("......................................................");
       // System.out.println("Calculating New Coordinates within " + radius + " mile(s) to " + lon + "," + lat + " with the degree " + degree);
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

        //System.out.println("......................................................");

        DecimalFormat decimalFormat;
        if (coordinatesSet.size() <= 1000) {
            decimalFormat = new DecimalFormat("###.###");
        } else if (coordinatesSet.size() > 1000 && coordinatesSet.size() <= 10000) {
            decimalFormat = new DecimalFormat("###.##");
        } else {
            decimalFormat = new DecimalFormat("###.#");
        }
        //System.out.println("Simplifying New Coordinates according to " + decimalFormat + " format");
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
