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
		 * �擾�B���b���V�ILocalDateTime.now()�C�S�R���ۈוb�B
		 * @param boolean ���ۈוb
		 * @return LocalDateTime �B���b���V�ILocalDateTime.now()
		 */
		public static LocalDateTime getLocalDateTime(boolean isSecond) {
			return LocalDateTime.now().truncatedTo(isSecond ? ChronoUnit.SECONDS : ChronoUnit.DAYS);
		}
		
		/**
		 * �擾Date�I�����C�S�RLocalDateTime.now()�B
		 * @return Date Date�I����
		 */
		public static Date getDate() {
		    return Convert.convertLocalDateTimeToDate(getLocalDateTime(true));
		}
		
		/**
		 * �擾�������IDate�����C�S�RLocalDateTime.now()�B
		 * @return String �������IDate����
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
		 * �z��Date��LocalDateTime�B
		 * @param Date date
		 * @return LocalDateTime localDateTime
		 */
		public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {
			return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
		}
		
		/**
		 * �z��Date�������B
		 * @param Date date
		 * @return String �������IDate
		 */
		public static String convertDateToString(Date dateToConvert) {
			DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			return formatter.format(dateToConvert);
		}
		
		/**
		 * �z��LocalDateTime��Date�B
		 * @param LocalDateTime localDateTime
		 * @return Date date
		 */
		public static Date convertLocalDateTimeToDate(LocalDateTime dateToConvert) {
			return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
		}
		
		/**
		 * �z��LocalDateTime�������B
		 * @param LocalDateTime localDateTime
		 * @return String �������ILocalDateTime
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
		 * �z��������LocalDateTime�B
		 * @param String ����
		 * @return LocalDateTime localDateTime
		 * @throws �z��������LocalDateTime���Cᢐ�����B
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
		 * �z��������Date�B
		 * @param String ����
		 * @return Date date
		 * @throws �z��������Date���Cᢐ�����B
		 */
		public static Date convertStringToDate(String stringToConvert) throws Exception {
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
			return sdformat.parse(stringToConvert);
		}
		
	}
	
}
