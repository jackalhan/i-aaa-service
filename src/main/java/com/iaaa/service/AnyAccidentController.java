package com.iaaa.service;

import com.iaaa.dto.AccidentMetrics;
import com.iaaa.dto.AnyAccidentResponse;
import com.iaaa.outsource.WeatherApi;
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

    @RequestMapping("/anyAccidentOverHere")
    public AnyAccidentResponse anyAccidentOverHere(@RequestParam(value = "lat") float lat,
                                                   @RequestParam(value = "long") float longt,
                                                   @RequestParam(value = "speedOfVehicle") int speedOfVehicle) throws CloneNotSupportedException {
        System.out.println("Returned");
        AccidentMetrics accidentMetrics = feedAccidentMetrics(new AccidentMetrics(lat, longt, speedOfVehicle));
        //return new AnyAccidentResponse(counter.incrementAndGet(), false);
        System.out.println("Sonuclar Alindiktan Sonra");
        System.out.println(accidentMetrics.toString());
        return new AnyAccidentResponse(accidentMetrics.getWeatherCondition() + "=====" +  accidentMetrics.getRoadCondition());
    }

    public AccidentMetrics feedAccidentMetrics(AccidentMetrics accidentMetrics) throws CloneNotSupportedException {
        System.out.println("Sonuclar Aliniyor");
        AccidentMetrics feededAccidentMetrics = (AccidentMetrics) accidentMetrics.clone();
        System.out.println(feededAccidentMetrics.toString());
        WeatherApi weatherApi = new WeatherApi(feededAccidentMetrics.getLongOfVehicle(),feededAccidentMetrics.getLatOfVehicle());
        feededAccidentMetrics.setWeatherCondition(weatherApi.getDescription());
        return feededAccidentMetrics;
    }


}
