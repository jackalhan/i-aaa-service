package com.iaaa.service;

import com.iaaa.dto.AccidentMetrics;
import com.iaaa.dto.AnyAccidentResponse;
import com.iaaa.dto.Coordinates;
import com.iaaa.outsource.WeatherDataApi;
import com.iaaa.outsource.dto.WeatherDataApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    // http://localhost:8080/anyAccidentOverHere?lat=36.2152214&long=-94.4473324&speedOfVehicle=55
    @RequestMapping("/anyAccidentOverHere")
    public AnyAccidentResponse anyAccidentOverHere(@RequestParam(value = "lon") double lon,
                                                   @RequestParam(value = "lat") double lat,
                                                   @RequestParam(value = "speedOfVehicle")
                                                           int speedOfVehicle
    ) throws CloneNotSupportedException

    {

        AccidentMetrics accidentMetrics = feedAccidentMetrics(new AccidentMetrics(new Coordinates(lon, lat), speedOfVehicle));
        //return new AnyAccidentResponse(counter.incrementAndGet(), false);
        return new AnyAccidentResponse(accidentMetrics.getWeatherCondition() + "=====" + accidentMetrics.getRoadCondition());
    }

    public AccidentMetrics feedAccidentMetrics(AccidentMetrics accidentMetrics) throws CloneNotSupportedException {
        System.out.println("Sonuclar Aliniyor");
        System.out.println(accidentMetrics.toString());
        AccidentMetrics feededAccidentMetrics = (AccidentMetrics) accidentMetrics.clone();
        System.out.println(feededAccidentMetrics.toString());
        WeatherDataApi weatherApi = new WeatherDataApi(feededAccidentMetrics.getCoordinates());
        WeatherDataApiResponse weatherDataApiResponse = weatherApi.query();
        feededAccidentMetrics.setWeatherCondition(String.valueOf(weatherDataApiResponse.getMain().getTemp()));
        System.out.println(weatherDataApiResponse.toString());
        return feededAccidentMetrics;
    }


}
