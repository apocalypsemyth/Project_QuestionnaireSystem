package com.QuestionnaireProject.QuestionnaireSystem.constant;

public class UrlConstant {
	
	/**
	 * òHúlèÌù…ÅB
	 */
	public static class Path {
		
		public static final String ROOT = "/";
		
		public static final String FRONT = "/front";
		
		public static final String ANSWERING_QUESTIONNAIRE_DETAIL = "/answeringQuestionnaireDetail";
		
		public static final String CHECKING_QUESTIONNAIRE_DETAIL = "/checkingQuestionnaireDetail";
		
		public static final String QUESTIONNAIRE_STATISTICS = "/questionnaireStatistics";
		
		/** Key of front and backAdmin */
		public static final String QUESTIONNAIRE_LIST = "/questionnaireList";

		public static final String NOT_FOUND = "/notFound";
		/** */
		
		public static final String BACK_ADMIN = "/backAdmin";
		
		public static final String QUESTIONNAIRE_DETAIL = "/questionnaireDetail";
		
		public static final String COMMON_QUESTION_LIST = "/commonQuestionList";
		
		public static final String COMMON_QUESTION_DETAIL = "/commonQuestionDetail";
		
	}
	
	public static class QueryParam {
		
		public static final String INDEX = "index";
		
		public static final String KEYWORD = "keyword";

		public static final String START_DATE = "startDate";
		
		public static final String END_DATE = "endDate";
		
		public static final String ID = "ID";
		
	}
	
	public static class Sign {
		
		public static final String EQUAL = "=";
		
		public static final String AMPERSAND = "&";
		
	}
	
	public static class Hash {
		
		public static final String STATISTICS = "#statistics";
		
	}
	
	/**
	 * çTêßÁzíµòHúlÅB
	 */
	public static class Control {
		public static final String REDIRECT = "redirect:";
	}
	
}
