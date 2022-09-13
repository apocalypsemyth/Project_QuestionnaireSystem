package com.QuestionnaireProject.QuestionnaireSystem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtil {
	
	public static class Func {
		
		/**
		 * 檢查目標字串是否為合法的QueryString。
		 * @param String 目標字串
		 * @return boolean 目標字串是否為合法的QueryString
		 */
		public static boolean isValidQueryString(String queryString) {
			if (!StringUtils.hasText(queryString)) return false;
			
			String atomicPattern = "\\w+=\\S+";
			String pattern = atomicPattern + "|" + "(&" + atomicPattern + ")+";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(queryString);
			if (!m.matches()) return false;
			
			return true;
		}
		
		/**
		 * 檢查目標字串是否以特定字串結尾，藉由目標字串和特定字串。
		 * @param String 目標字串
		 * @param String 特定字串
		 * @return boolean 目標字串是否以特定字串結尾
		 */
		public static boolean isEndWithSpecial(String string, String special) {
			if (!StringUtils.hasText(string)) return false;
			
			String pattern = ".*" + special + "$";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(string);
			if (!m.matches()) return false;
			
			return true;
		}
		
		/**
		 * 檢查目標字串是否可轉換為的UUID。
		 * @param String 目標字串
		 * @return boolean 目標字串是否可轉換為UUID
		 */
		public static boolean isValidUUID(String strMaybeUUID) {
			if (!StringUtils.hasText(strMaybeUUID)) return false;
			
			String pattern = 
					"^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(strMaybeUUID);
			if (!m.matches()) return false;
			
			return true;
		}
		
		/**
		 * 檢查目標字串是否可轉換為數字。
		 * @param String 目標字串
		 * @return boolean 目標字串是否可轉換為數字
		 */
		public static boolean isNumeric(String string) {
		    String regex = "[0-9]+[\\.]?[0-9]*";
		    return Pattern.matches(regex, string);
		}
		
	}
	
}
