package com.iaaa.service;

/**
 * Created by jackalhan on 3/21/16.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import com.google.maps.GeoApiContext;
import com.google.maps.model.GeocodingResult;
import com.iaaa.dto.Accident;
import com.iaaa.models.AccidentHistory;
import com.iaaa.models.AccidentHistoryDao;
import com.iaaa.outsource.GoogleGeocodingApi;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WebParserBatch {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    private String baseLink = "http://www.asp.state.ar.us/fatal/";
    private String reportLinks = baseLink + "index.php?do=reportsLinks&year=";
    private int counter = 0;
    private final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

    @Scheduled(fixedDelay = 600000) //10 minutes
    public void parseAllReportLinks() {

        LocalDate date = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy hh24:nnn:ss");
        String str = date.toString(fmt);

        System.out.println(" ************************************************************ ");
        System.out.println(" ****************** ACCIDENT REPORT PARSER  ***************** ");
        System.out.println(" ************************************************************ ");
        System.out.println(" Execution Time : " + str);
        System.out.println(" Execution Counter : " + counter);
        System.out.println(" ************************************************************ ");

        counter++;

        try {
            int year = date.getYear();
            List<Accident> accidentList = new ArrayList<Accident>();
            Accident accident = null;
            Document document = null;
            do {
            document = Jsoup.connect(reportLinks + year).
                    userAgent(userAgent)
                    .get();

            Elements elements = document.select("a[href]");
            int i = 0;
            for (Element link : elements) {
                System.out.println("\n Validate link : " + link.attr("href"));
                if (i == 25) {

                    i = 0;
                    TimeUnit.SECONDS.sleep(15);
                }
                i++;
                if (link.attr("href").contains("accident_number=261")) {

                    try {
                        accident = parseReport(link.attr("href"));
                        accidentList.add(accident);
                        create(dataCleansing(accidentList));
                    } catch (Exception ex) {

                        System.out.println(":::::::::: Google Location Fixing Error ::::::::::::");
                        //System.out.println("Data => " + accident.toString());
                        System.out.println(ex.toString());

                    }
                }
            }
                year++;
            } while (year < year + 1);

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
        System.out.println("\nData Cleansing Starting ------------> ");
        for (Accident accident : accidentList) {
            try {
                accidentHistory = new AccidentHistory();
                //Whereas this (using || instead of |) is short-circuiting - if the first condition evaluates to true, the second is not evaluated.
                //if (accident.getFatalNumber() == null | accident.getFatalNumber().length() == 0) {
                accidentHistory.setFatalNumber(accident.getFatalNumber());
                accidentHistory.setAccidentNumber(Integer.parseInt(accident.getAccidentNumber()));
                accidentHistory.setLon(Double.parseDouble(accident.getLocationLong()));
                accidentHistory.setLat(Double.parseDouble(accident.getLocationLat()));
                try {
                    accidentHistory.setAccidentTime(formatter.parse(accident.getAccidentDate() + ' ' + accident.getAccidentTime().toUpperCase().replace("A", " A").replace("P", " P")));
                } catch (Exception ex) {
                    String newAccidentTime = null;
                    try {
                        if (!((accident.getAccidentTime() == null) || (accident.getAccidentTime().contains("")))) {
                            if (!accident.getAccidentTime().contains(":")) {
                                if (accident.getAccidentTime().trim().length() == 5) {
                                    newAccidentTime = "0" + accident.getAccidentTime().substring(0, 1) + ":" + accident.getAccidentTime().substring(1, 5);
                                } else if (accident.getAccidentTime().trim().length() == 6) {
                                    newAccidentTime = accident.getAccidentTime().substring(0, 2) + ":" + accident.getAccidentTime().substring(2, 6);
                                }
                            } else {
                                if (accident.getAccidentTime().trim().length() == 7) {
                                    newAccidentTime = accident.getAccidentTime().replace("P", "").replace("A", "");
                                }
                            }

                        } else {
                            newAccidentTime = "05:55pm";
                        }
                        accidentHistory.setAccidentTime(formatter.parse(accident.getAccidentDate() + ' ' + newAccidentTime.toUpperCase().replace("A", " A").replace("P", " P")));

                    } catch (Exception ex1) {
                        System.out.println(":::::::::: Cleansing Error EXCEPTION :::::::::::: AccidentNumber " + accident.getAccidentNumber());
                        System.out.println(" ===============>" + newAccidentTime);
                        System.out.println(ex1.toString());
                    }
                }

                try {
                    accidentHistory.setNumberOfKilled(Integer.parseInt(accident.getNumberOfKilled()));
                } catch (Exception ex) {
                    accidentHistory.setNumberOfKilled(0);
                }

                try {
                    accidentHistory.setNumberOfInjured(Integer.parseInt(accident.getNumberOfInjured()));
                } catch (Exception ex) {
                    accidentHistory.setNumberOfInjured(0);
                }

                accidentHistory.setWeatherCondition(accident.getWeatherCondition());
                accidentHistory.setRoadCondition(accident.getRoadCondition());
                accidentHistory.setLocation(accident.getLocation());
                accidentHistory.setFormattedLocation(accident.getFormattedLocation());
                accidentHistory.setAccidentScenario(accident.getAccidentScenario());
                accidentHistory.setCity(accident.getCity());
                accidentHistory.setCounty(accident.getCounty());
                accidentHistory.setState(accident.getState());
                accidentHistory.setInsertTime(new Date());

                accidentHistoryList.add(accidentHistory);

            } catch (Exception ex) {
                System.out.println(":::::::::: Cleansing Error ::::::::::::");
                System.out.println("Data => " + accident.toString());
                System.out.println(ex.toString());
            }
        }
        return accidentHistoryList;
    }

    public boolean isAlreadyInHistory(AccidentHistory accidentHistory) {
        boolean result = false;
        try {
            Calendar calendar = Calendar.getInstance(); // this would default to now
            calendar.add(Calendar.DAY_OF_MONTH, -7);

            if (accidentHistoryDao.findAccidentByFatalAccidentNumberSince1Week(accidentHistory.getFatalNumber(), accidentHistory.getAccidentNumber(), calendar.getTime()).size() > 0) {
                result = true;
            } else {
                result = false;
            }


        } catch (Exception ex) {
            System.out.println(":::::::::: DB Query : isAlreadyInHistory Error ::::::::::::");
            System.out.println("Data => " + accidentHistory.toString());
            System.out.println(ex.toString());
            result = true;
        }
        return result;
    }

    public void create(List<AccidentHistory> accidentHistoryList) {
        String accidentId = "";
        int totalSize, successCount, failCount, inDBCount;

        totalSize = accidentHistoryList.size();
        successCount = 0;
        failCount = 0;
        inDBCount = 0;
        for (AccidentHistory accidentHist : accidentHistoryList) {
            try {
                if (!isAlreadyInHistory(accidentHist)) {
                    accidentHistoryDao.save(accidentHist);
                    successCount = successCount + 1;
                    System.out.println("Saving..........." + successCount);
                } else {
                    inDBCount = inDBCount + 1;
                }
            } catch (Exception ex) {
                failCount = failCount + 1;
                System.out.println(":::::::::: DB Query : create Error (" + failCount + ")::::::::::::");
                System.out.println("Data => " + accidentHist.toString());
                System.out.println(ex.toString());
            }

        }

    }


    @Autowired
    private AccidentHistoryDao accidentHistoryDao;


}

