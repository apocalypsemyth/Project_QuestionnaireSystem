package com.QuestionnaireProject.QuestionnaireSystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtil {
	
	public static class Func {
		
		/**
		 * 取得截至秒或天的LocalDateTime.now()，藉由是否為秒。
		 * @param boolean 是否為秒
		 * @return LocalDateTime 截至秒或天的LocalDateTime.now()
		 */
		public static LocalDateTime getLocalDateTime(boolean isSecond) {
			return LocalDateTime.now().truncatedTo(isSecond ? ChronoUnit.SECONDS : ChronoUnit.DAYS);
		}
		
		/**
		 * 取得Date的今日，藉由LocalDateTime.now()。
		 * @return Date Date的今日
		 */
		public static Date getDate() {
		    return Convert.convertLocalDateTimeToDate(getLocalDateTime(true));
		}
		
		/**
		 * 取得字串化的Date今日，藉由LocalDateTime.now()。
		 * @return String 字串化的Date今日
		 */
		public static String getStringDate() {
			LocalDateTime ldt = LocalDateTime.now();
			int year = ldt.getYear();
			int month = ldt.getMonthValue();
			String monthStr = month < 10 ? "0" + month : "" + month;
			int day = ldt.getDayOfMonth();
			String dayStr = day < 10 ? "0" + day : "" + day;
			
			return year + "/" + monthStr + "/" + dayStr;
		}
		
	}
	
	public static class Convert {
		
		/**
		 * 轉換Date至LocalDateTime。
		 * @param Date date
		 * @return LocalDateTime localDateTime
		 */
		public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
			return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
		}
		
		/**
		 * 轉換Date至字串。
		 * @param Date date
		 * @return String 字串化的Date
		 */
		public static String convertDateToString(Date dateToConvert) {
			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			return formatter.format(dateToConvert);
		}
		
		/**
		 * 轉換LocalDateTime至Date。
		 * @param LocalDateTime localDateTime
		 * @return Date date
		 */
		public static Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
			return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
		}
		
		/**
		 * 轉換LocalDateTime至字串。
		 * @param LocalDateTime localDateTime
		 * @return String 字串化的LocalDateTime
		 */
		public static String convertLocalDateTimeToString(LocalDateTime dateToConvert) {
			int year = dateToConvert.getYear();
			int month = dateToConvert.getMonthValue();
			String monthStr = month < 10 ? "0" + month : "" + month;
			int day = dateToConvert.getDayOfMonth();
			String dayStr = day < 10 ? "0" + day : "" + day;
			
			return year + "/" + monthStr + "/" + dayStr;
		}
		
		/**
		 * 轉換字串至LocalDateTime。
		 * @param String 字串
		 * @return LocalDateTime localDateTime
		 * @throws 轉換字串至LocalDateTime時，發生錯誤。
		 */
		public static LocalDateTime convertStringToLocalDateTime(
				String stringToConvert
				) throws Exception {
			String pattern = "\\d{4}(/\\d{2}){2}";
			Pattern p  = Pattern.compile(pattern);
			Matcher m = p.matcher(stringToConvert);
			if (!m.matches()) throw new Exception("Invalid format");
			stringToConvert = stringToConvert + " 00:00";
			DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
			return LocalDateTime.parse(stringToConvert, dtformatter);
		}
		
		/**
		 * 轉換字串至Date。
		 * @param String 字串
		 * @return Date date
		 * @throws 轉換字串至Date時，發生錯誤。
		 */
		public static Date convertStringToDate(String stringToConvert) throws Exception {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
			return sdformat.parse(stringToConvert);
		}
		
	}
	
}
