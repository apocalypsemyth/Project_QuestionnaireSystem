package com.QuestionnaireProject.QuestionnaireSystem.enums;

public enum RtnInfo {
	SUCCESSFUL("200", "成功。"),
	PARAMETER_REQUIRED("400", "必要參數缺失，請再嘗試。"),
	NOT_FOUND("404", "未找到資料。"),
	FAILED("500", "發生錯誤，請再嘗試。"),
	DATA_REQUIRED("417", "請填寫完整的資料。"),
	SESSION_EXPIRED("512", "操作時間過長，請再嘗試。"),
	// 更新模式時，刪除全部資料，只是被標記刪除，沒有真的刪掉於Session。
	NOT_EMPTY_EMPTY("666", "Update mode empty"),
	ONE_QUESTION("667", "請加入至少一個問題。"),
	ONE_REQUIRED_QUESTION("668", "請加入至少一個必填問題。"),
	JUST_ONE_QUESTION("669", "只能加入一個問題。"),
	NAME_OF_COMMON_QUESTION_IS_CUSTOMIZED_QUESTION("670", "常用問題其名稱不得為自訂問題。");
	
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
