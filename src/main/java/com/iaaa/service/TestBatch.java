package com.iaaa.service;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jackalhan on 4/12/16.
 */
@Component
public class TestBatch {

    String dateNow = "1970-01-01 10:31:48 AM";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");

    private final SimpleDateFormat timeFormat =  new SimpleDateFormat("hh:mm:ss aa");

    //@Scheduled(fixedDelay = 3000) //5 minutes
    public void dateTestParsing() throws ParseException {

        Date earlyMorningTimeStart = timeFormat.parse("4:00:01 AM");
        Date morningTimeStart = timeFormat.parse("8:00:01 AM");
        Date afternoonTimeStart = timeFormat.parse("12:00:01 PM");
        Date eveningTimeStart = timeFormat.parse("5:00:01 PM");
        Date middleNightTimeStart = timeFormat.parse("11:00:01 PM");
        Date now = format.parse(dateNow);
        DateTime dte = new DateTime(earlyMorningTimeStart);
        DateTime dtm = new DateTime(morningTimeStart);
        DateTime dta = new DateTime(afternoonTimeStart);
        DateTime dtev = new DateTime(eveningTimeStart);
        DateTime dtmn = new DateTime(middleNightTimeStart);
        DateTime dt2 = new DateTime(now);

        //Interval interval = new Interval(new Date().getTime(), earlyMorningTimeStart.getTime());

        System.out.println(Hours.hoursBetween(dte,dt2).getHours()% 24);
        System.out.println(Hours.hoursBetween(dtm,dt2).getHours()% 24);
        System.out.println(Hours.hoursBetween(dta,dt2).getHours()% 24);
        System.out.println(Hours.hoursBetween(dtev,dt2).getHours()% 24);
        System.out.println(Hours.hoursBetween(dtmn,dt2).getHours()% 24);
        /*System.out.println(now.compareTo(earlyMorningTimeStart));
        System.out.println(now.compareTo(morningTimeStart));
        System.out.println(now.compareTo(afternoonTimeStart));
        System.out.println(now.compareTo(eveningTimeStart));
        System.out.println(now.compareTo(middleNightTimeStart));*/

    }

}
