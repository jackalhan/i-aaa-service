package com.iaaa.service;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by jackalhan on 3/22/16.
 */
@Component
public class WeatherApp {

    @Scheduled(fixedRate = 1000000000)
    public void weatherAnal() {
        weather();
    }

    public void weather() {

    }
}

