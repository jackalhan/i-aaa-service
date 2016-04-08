package com.iaaa.service;

/**
 * Created by jackalhan on 3/21/16.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.google.maps.GeoApiContext;
import com.google.maps.model.GeocodingResult;
import com.iaaa.dto.Accident;
import com.iaaa.models.AccidentHistory;
import com.iaaa.models.AccidentHistoryDao;
import com.iaaa.outsource.GoogleGeocodingApi;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebParserBatch {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private String baseLink = "http://www.asp.state.ar.us/fatal/";
    private String reportLinks = baseLink + "index.php?do=reportsLinks&year=";
    private int year = 2004;
    private final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

    @Scheduled(fixedRate = 1000000000)
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
                if (i == 25) {

                    i = 0;
                    TimeUnit.SECONDS.sleep(10);
                }
                //i++;
                if (link.attr("href").contains("accident_number=595")) {
                        /*System.out.println("\n Accepted link : " + link.attr("href"));
                        System.out.println("\nlink : " + link.attr("href"));
                        System.out.println("\ntext : " + link.text());*/
                    accident = parseReport(link.attr("href"));
                    accidentList.add(accident);
                    create(dataCleansing(accidentList));
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

        System.out.println("\nParsing the ------------> " + baseLink + href);
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
        GeocodingResult[] results = GoogleGeocodingApi.geocode(context, accident.getLocation() + "," + accident.getState()).await();
        accident.setFormattedLocation(results[0].formattedAddress);
        accident.setLocationLat(String.valueOf(results[0].geometry.location.lat));
        accident.setLocationLong(String.valueOf(results[0].geometry.location.lng));
        System.out.println("After Google");
        System.out.println(accident.toString());
        return accident;
    }

    public List<AccidentHistory> dataCleansing(List<Accident> accidentList) {
        List<AccidentHistory> accidentHistoryList = new ArrayList<AccidentHistory>();
        AccidentHistory accidentHistory = null;
        for (Accident accident : accidentList) {
            try {
                accidentHistory = new AccidentHistory();
                //Whereas this (using || instead of |) is short-circuiting - if the first condition evaluates to true, the second is not evaluated.
                //if (accident.getFatalNumber() == null | accident.getFatalNumber().length() == 0) {
                accidentHistory.setFatalNumber(Integer.parseInt(accident.getFatalNumber()));
                //}
                accidentHistory.setAccidentNumber(Integer.parseInt(accident.getAccidentNumber()));
                accidentHistory.setLon(Double.parseDouble(accident.getLocationLong()));
                accidentHistory.setLat(Double.parseDouble(accident.getLocationLat()));
                accidentHistory.setAccidentTime(dateFormat.parse(accident.getAccidentDate() + ' ' + accident.getAccidentTime().toUpperCase().replace("A", " A").replace("P", " P")));
                accidentHistory.setNumberOfKilled(Integer.parseInt(accident.getNumberOfKilled()));
                accidentHistory.setNumberOfInjured(Integer.parseInt(accident.getNumberOfInjured()));
                ///DAHA DEVAMI VAR. MAPLENEREK ATILACAK DATALAR.
                accidentHistoryList.add(accidentHistory);

            } catch (Exception ex) {
                System.out.println(":::::::::: Cleansing Error ::::::::::::");
                System.out.println("Data => " + accident.toString());
                System.out.println(ex.toString());
            }
        }
        return  accidentHistoryList;
    }

    public String create(List<AccidentHistory> accidentHistoryList) {
        String accidentId = "";
        try {
            for (AccidentHistory accidentHist : accidentHistoryList) {
                accidentHistoryDao.save(accidentHist);
                accidentId = String.valueOf(accidentHist.getId());
            }

        } catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + accidentId;
    }


    @Autowired
    private AccidentHistoryDao accidentHistoryDao;


}

