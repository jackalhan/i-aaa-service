package com.iaaa.service;

/**
 * Created by jackalhan on 3/21/16.
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.iaaa.dto.Accident;
import com.iaaa.dto.Vehicle;
import com.iaaa.outsource.GeocodingApi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebParserBatch {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private String baseLink = "http://www.asp.state.ar.us/fatal/";
    private String reportLinks = baseLink + "index.php?do=reportsLinks&year=";
    private int year = 2004;
    //@Scheduled(fixedRate = 1000000000)
    public void parseAllReportLinks() {

        try {

            List<Accident> accidentList = new ArrayList<Accident>();
            Accident accident = null;
            Document document = null;
            //do {
                document = Jsoup.connect(reportLinks + year).
                        userAgent(userAgent)
                        .get();

                Elements elements = document.select("a[href]");
                int i = 0;
                for (Element link : elements) {
                    //System.out.println("\n Validate link : " + link.attr("href"));
                    if(i == 25) {

                        i = 0;
                        TimeUnit.SECONDS.sleep(10);
                    }
                    i++;
                    if (link.attr("href").contains("accident_number")) {
                        /*System.out.println("\n Accepted link : " + link.attr("href"));
                        System.out.println("\nlink : " + link.attr("href"));
                        System.out.println("\ntext : " + link.text());*/
                        accident = parseReport(link.attr("href"));
                        accidentList.add(accident);
                    }
                }
            //    year++;
           // } while (year <= 2016);

            System.out.println(accidentList.size());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Accident parseReport(String href) throws Exception {

        Accident accident = new Accident();

        System.out.println("\nParsing the ------------> " + baseLink+href);
        Document document = Jsoup.connect(baseLink + href).
                userAgent(userAgent)
                .get();

        Element element = document.select("table[class=light]").select("table[width=590]").first();


        for (Element generalCell : element.select("table[class=light]").select("td")) {

            // hexa for replace &nbsp; to null

            String parsedRow = generalCell.getElementsByTag("b").text().replace("\u00a0", "").trim();
            String parsedRowValue = generalCell.getElementsByTag("u").text().replace("\u00a0", "").trim();


            if (parsedRow.equalsIgnoreCase("Fatal#:")) {
                accident.setFatalNumber(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Accident #:")) {
                accident.setAccidentNumber(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Date of Accident:")) {
                accident.setAccidentDate(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Time of Accident:")) {
                accident.setAccidentTime(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Location:")) {
                accident.setLocation(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("City:")) {
                accident.setCity(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("County:")) {
                accident.setCounty(parsedRowValue);
                accident.setState("Arkansas");
            } else if (parsedRow.equalsIgnoreCase("What Happened?")) {
                accident.setAccidentScenario(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Weather Condition:")) {
                accident.setWeatherCondition(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Road Condition:")) {
                accident.setRoadCondition(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Body Held At:")) {
                accident.setBodyHeldAt(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Investigating Officer:")) {
                accident.setInvestigatingOfficer(parsedRowValue);
            } else if (parsedRow.equalsIgnoreCase("Agency:")) {
                accident.setAgency(parsedRowValue);
            } else if (parsedRow.equals("Killed")) {
                accident.setNumberOfKilled(generalCell.text().replace("\u00a0", "").trim().replaceAll("[^0-9]", ""));
            } else if (parsedRow.equals("Injured")) {
                accident.setNumberOfInjured(generalCell.text().replace("\u00a0", "").trim().replaceAll("[^0-9]", ""));
            }

        }

        System.out.println("Before Google");
        System.out.println(accident.toString());
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDZao4FbodjtM4xsbCMAkESki8Mc4lYy3U");
        GeocodingResult[] results =  GeocodingApi.geocode(context, accident.getLocation() + "," + accident.getState()).await();
        accident.setFormattedLocation(results[0].formattedAddress);
        accident.setLocationLat(String.valueOf(results[0].geometry.location.lat));
        accident.setLocationLong(String.valueOf(results[0].geometry.location.lng));
        System.out.println("After Google");
        System.out.println(accident.toString());
        return accident;
    }


}

