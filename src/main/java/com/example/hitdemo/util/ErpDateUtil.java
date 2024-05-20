package com.example.hitdemo.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.Assert;

public class ErpDateUtil {
	public static final String UTC = "Coordinated Universal Time";

	  private ErpDateUtil() {

	    throw new IllegalStateException("Erp Date Util Class");
	  }

	  /**
	   * Method to add days in given date.
	   * 
	   * @param date
	   * @param days
	   * @return
	   */
	  public static Date addDays(Date date, Integer days) {

	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, days);
	    return cal.getTime();
	  }

	  /**
	   * Method to get the string date in UTC time zone.
	   * 
	   * @param strDate
	   * @param format
	   * @return
	   * @throws ParseException
	   */
	  public static Date getUTCDate(final String strDate, final String format) throws ParseException {

	    SimpleDateFormat sf = new SimpleDateFormat(format);
	    sf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    return sf.parse(strDate);
	  }

	  /**
	   * Method to convert given date in given string format. <br/>
	   * In this method formated date contains special character present in format.
	   *
	   * @param date
	   * @param format
	   * @return string date
	   */
	  public static String stringFormatedDate(final Date date, final String format) {

	    final SimpleDateFormat simpleDF = new SimpleDateFormat(format);
	    return simpleDF.format(date);
	  }

	  /**
	   * Method to convert given date to String in a given format with time zone.
	   *
	   * @param date
	   * @param format
	   * @param timeZone
	   * @return
	   */
	  public static String stringFormatedDate(final Date date, final String format,
	      final String timeZone) {

	    final SimpleDateFormat simpleDF = new SimpleDateFormat(format);
	    simpleDF.setTimeZone(TimeZone.getTimeZone(timeZone));
	    return simpleDF.format(date);
	  }

	  public static LocalDateTime stringToLocalDateTime(final String date) {

	    return LocalDateTime.parse(date);
	  }

	  public static LocalDate stringToLocalDate(final String date) {
	    // Date format here is, 2022-05-16T00:00:00.000Z

	    // DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	    // System.out.println("formater value is:- " + formatter);
	    // return LocalDate.parse(date, formatter);
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    System.out.println("formater value is:- " + format);
	    System.out.println("Date of local  value is:- " + LocalDate.parse(date, format));
	    return LocalDate.parse(date, format);
	  }

	  /**
	   * method to get timestamp value of input date
	   *
	   * @param date
	   * @return
	   */
	  public static Timestamp getCurrentTimestamp() {

	    return new Timestamp(new Date().getTime());
	  }

	  public static String getTimestamp(Calendar date) {

	    Long t1 = date.getTimeInMillis();
	    Timestamp t = new Timestamp(date.getTimeInMillis());
	    return "" + t1;
	  }

	  /**
	   * End of the day in required time zone
	   *
	   * @param date
	   * @param timeZone
	   * @return
	   */
	  public static Calendar toEndOfTheDay(final Date date, final String timeZone) {

	    Assert.notNull(date, "date can't be null");
	    final Calendar calendar = ErpDateUtil.toBeginningOfTheDay(date, timeZone);
	    calendar.add(Calendar.DATE, 1);
	    calendar.add(Calendar.SECOND, -1);
	    return calendar;
	  }

	  /**
	   * Get Beginning of the Day in the required time zone
	   *
	   * @param date
	   * @param timeZone
	   * @return
	   */
	  public static Calendar toBeginningOfTheDay(final Date date, final String timeZone) {

	    Assert.notNull(date, "date can't be null.");
	    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar;
	  }

	  public static Date getDate(final String strDate, final String format) throws ParseException {

	    SimpleDateFormat sf = new SimpleDateFormat(format);
	    return sf.parse(strDate);
	  }

	  /**
	   * This method converts java.util.Date to LocalDate instance.
	   * 
	   * @param date
	   * @return
	   */
	  public static LocalDate dateToLocalDate(Date date) {

	    return date.toInstant()
	        .atZone(ZoneId.systemDefault())
	        .toLocalDate();
	  }
}
