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


}
