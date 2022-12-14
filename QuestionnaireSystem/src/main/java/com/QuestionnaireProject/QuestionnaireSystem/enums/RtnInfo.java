package com.QuestionnaireProject.QuestionnaireSystem.enums;

public enum RtnInfo {
	SUCCESSFUL("200", "¬÷B"),
	PARAMETER_REQUIRED("400", "KvÒÉã¸C¿Ä¦B"),
	NOT_FOUND("404", "¢Q¿B"),
	FAILED("500", "á¢¶öëC¿Ä¦B"),
	DATA_REQUIRED("417", "¿U®®I¿B"),
	SESSION_EXPIRED("512", "ìÔß·C¿Ä¦B"),
	// XVÍ®CS¿Cü¥íWLCL^I{SessionB
	NOT_EMPTY_EMPTY("666", "Update mode empty"),
	ONE_QUESTION("667", "¿Áü­êÂâèB"),
	ONE_REQUIRED_QUESTION("668", "¿Áü­êÂKUâèB"),
	JUST_ONE_QUESTION("669", "ü\ÁüêÂâèB"),
	NAME_OF_COMMON_QUESTION_IS_CUSTOMIZED_QUESTION("670", "ípâè´¼âis¾×©ùâèB");
	
	private String code;
	
	private String message;
	
	RtnInfo(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
