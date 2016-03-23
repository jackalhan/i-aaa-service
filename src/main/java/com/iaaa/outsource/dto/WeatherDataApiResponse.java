package com.iaaa.outsource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iaaa.dto.Coordinates;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataApiResponse {
    private Coordinates coord;
    private WeatherData weather[];
    private String base;
    private WeatherDataMain main;
    private long visibility;
    private WeatherDataWind wind;
    private WeatherDataCloud clouds;
    private WeatherDataVolume rainVolume;
    private WeatherDataVolume snowVolume;
    private long dt;
    private WeatherDataSys sys;
    private long id;
    private String name;
    private long cod;



    public WeatherDataApiResponse() {
    }

    public WeatherDataMain getMain() {
        return main;
    }

    public void setMain(WeatherDataMain main) {
        this.main = main;
    }

    public WeatherDataVolume getRainVolume() {
        return rainVolume;
    }

    public void setRainVolume(WeatherDataVolume rainVolume) {
        this.rainVolume = rainVolume;
    }

    public WeatherDataVolume getSnowVolume() {
        return snowVolume;
    }

    public void setSnowVolume(WeatherDataVolume snowVolume) {
        this.snowVolume = snowVolume;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public WeatherData[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherData[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }


    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public WeatherDataWind getWind() {
        return wind;
    }

    public void setWind(WeatherDataWind wind) {
        this.wind = wind;
    }

    public WeatherDataCloud getClouds() {
        return clouds;
    }

    public void setClouds(WeatherDataCloud clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public WeatherDataSys getSys() {
        return sys;
    }

    public void setSys(WeatherDataSys sys) {
        this.sys = sys;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "WeatherDataApiResponse{" +
                "coord=" + coord +
                ", weather=" + Arrays.toString(weather) +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", rainVolume=" + rainVolume +
                ", snowVolume=" + snowVolume +
                ", dt=" + dt +
                ", sys=" + sys +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}