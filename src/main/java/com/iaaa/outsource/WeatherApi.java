package com.iaaa.outsource;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;

/**
 * Created by jackalhan on 3/22/16.
 */
public class WeatherApi {

    private float longt;
    private float lat;
    private float temperature;
    private String description;
    private float radius = 0.1f; //Read from properties.

    public WeatherApi(float longt, float lat) {
        System.out.println("WeatherApi nin icindeyiz");
        System.out.println("WeatherApi ye gelen veri");
        System.out.println(toString());
        this.longt = longt;
        this.lat = lat;
        fillWeatherData();
    }

    private void fillWeatherData()
    {
        OwmClient owm = new OwmClient();
        WeatherStatusResponse currentWeather = null;
        try {
            currentWeather = owm.currentWeatherInCircle(getLat(),getLong(),radius);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentWeather.hasWeatherStatus()) {
            System.out.println("WeatherApi sorguluyor");
            WeatherData weather = currentWeather.getWeatherStatus().get(0);
            setTemperature(weather.getTemp());
            setDescription(weather.getWeatherConditions().get(0).getDescription());
            System.out.println("WeatherApi nin dondugu cevap");
            System.out.println(toString());
        }
    }

    private float getLong() {
        return longt;
    }

    private void setLong(float longt) {
        this.longt = longt;
    }

    private float getLat() {
        return lat;
    }

    private void setLat(float lat) {
        this.lat = lat;
    }

    public float getTemperature() {
        return temperature;
    }

    private void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "WeatherApi{" +
                "longt=" + longt +
                ", lat=" + lat +
                ", temperature=" + temperature +
                ", description='" + description + '\'' +
                ", radius=" + radius +
                '}';
    }
}
