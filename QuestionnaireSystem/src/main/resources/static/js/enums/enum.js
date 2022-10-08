/**
 * 與Enum相關的錯誤
 */
class EnumError {
	static get CANNOT_SET() {
		return "Cannot set";
	}
	static set CANNOT_SET(value) {
		throw new Error("Cannot set");
	}
}

/**
 * 回應資訊之狀態代碼和訊息
 */
class RtnInfo {
    static get SUCCESSFUL() {
		return new RtnInfo("200", "成功。");
	}
    static set SUCCESSFUL(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get PARAMETER_REQUIRED() {
		return new RtnInfo("400", "必要參數缺失，請再嘗試。");
	}
    static set PARAMETER_REQUIRED(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get NOT_FOUND() {
		return new RtnInfo("404", "未找到資料。");
	}
    static set NOT_FOUND(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get FAILED() {
		return new RtnInfo("500", "發生錯誤，請再嘗試。");
	}
    static set FAILED(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get DATA_REQUIRED() {
		return new RtnInfo("417", "請填寫完整的資料。");
	}
    static set DATA_REQUIRED(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get SESSION_EXPIRED() {
		return new RtnInfo("512", "操作時間過長，請再嘗試。");
	}
    static set SESSION_EXPIRED(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    // 更新模式時，刪除全部資料，只是被標記刪除，沒有真的刪掉於Session。
    static get NOT_EMPTY_EMPTY() {
		return new RtnInfo("666", "Update mode empty");
	}
    static set NOT_EMPTY_EMPTY(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get ONE_QUESTION() {
		return new RtnInfo("667", "請加入至少一個問題。");
	}
    static set ONE_QUESTION(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get ONE_REQUIRED_QUESTION() {
		return new RtnInfo("668", "請加入至少一個必填問題。");
	}
    static set ONE_REQUIRED_QUESTION(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get JUST_ONE_QUESTION() {
		return new RtnInfo("669", "只能加入一個問題。");
	}
    static set JUST_ONE_QUESTION(value) {
		throw new Error(EnumError.CANNOT_SET);
	}
    static get NAME_OF_COMMON_QUESTION_IS_CUSTOMIZED_QUESTION() { 
		return new RtnInfo("670", "常用問題其名稱不得為自訂問題。");
	}
    static set NAME_OF_COMMON_QUESTION_IS_CUSTOMIZED_QUESTION(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}

    constructor(statusCode, message) {
		Object.defineProperties(this, {
	      statusCode: {
	        enumerable: true,
	        set: (value) => {
	          throw new Error(EnumError.CANNOT_SET);
	        },
	        get: () => statusCode,
	      },
	      message: {
	        enumerable: true,
	        set: (value) => {
	          throw new Error(EnumError.CANNOT_SET);
	        },
	        get: () => message,
	      },
	    });
    }
}

/**
 * Url之uri
 */
class Url {
	static get FRONT() { 
		return new Url("/front");
	}
	static set FRONT(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get ANSWERING_QUESTIONNAIRE_DETAIL() { 
		return new Url("/answeringQuestionnaireDetail");
	}
    static set ANSWERING_QUESTIONNAIRE_DETAIL(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get CHECKING_QUESTIONNAIRE_DETAIL() { 
		return new Url("/checkingQuestionnaireDetail");
	}
    static set CHECKING_QUESTIONNAIRE_DETAIL(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	static get QUESTIONNAIRE_STATISTICS() { 
		return new Url("/questionnaireStatistics");
	}
	static set QUESTIONNAIRE_STATISTICS(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	
    static get QUESTIONNAIRE_LIST() { 
		return new Url("/questionnaireList");
	}
    static set QUESTIONNAIRE_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}

	static get BACK_ADMIN() { 
		return new Url("/backAdmin");
	}
	static set BACK_ADMIN(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get QUESTIONNAIRE_DETAIL() { 
		return new Url("/questionnaireDetail");
	}
    static set QUESTIONNAIRE_DETAIL(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	static get COMMON_QUESTION_LIST() { 
		return new Url("/commonQuestionList");
	}
	static set COMMON_QUESTION_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	static get COMMON_QUESTION_DETAIL() { 
		return new Url("/commonQuestionDetail");
	}
	static set COMMON_QUESTION_DETAIL(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	static get DOWNLOAD_DATA_TO_CSV() { 
		return new Url("/downloadDataToCSV");
	}
	static set DOWNLOAD_DATA_TO_CSV(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
	
    constructor(uri) {
        Object.defineProperties(this, {
	      uri: {
	        enumerable: true,
	        set: (value) => {
	          throw new Error(EnumError.CANNOT_SET);
	        },
	        get: () => uri,
	      },
	    });
    }
}

/**
 * 請求資料屬性之健值
 */
class DataProperty {
    static get QUESTIONNAIRE_ID() { 
		return new DataProperty("questionnaire_id");
	}
    static set QUESTIONNAIRE_ID(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get QUESTIONNAIRE_ID_LIST() { 
		return new DataProperty("questionnaire_id_list");
	}
    static set QUESTIONNAIRE_ID_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get QUESTION_ID() { 
		return new DataProperty("question_id");
	}
    static set QUESTION_ID(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get QUESTION_ID_LIST() { 
		return new DataProperty("question_id_list");
	}
    static set QUESTION_ID_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get COMMON_QUESTION_ID() { 
		return new DataProperty("common_question_id");
	}
    static set COMMON_QUESTION_ID(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get COMMON_QUESTION_ID_LIST() { 
		return new DataProperty("common_question_id_list");
	}
    static set COMMON_QUESTION_ID_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get CATEGORY_ID() { 
		return new DataProperty("category_id");
	}
    static set CATEGORY_ID(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get USER_ANSWER_STRING_LIST() { 
		return new DataProperty("user_answer_string_list");
	}
    static set USER_ANSWER_STRING_LIST(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get USER_ID() { 
		return new DataProperty("user_id");
	}
    static set USER_ID(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}
    static get STR_INDEX() { 
		return new DataProperty("str_index");
	}
    static set STR_INDEX(value) { 
		throw new Error(EnumError.CANNOT_SET);
	}

    constructor(key) {
        Object.defineProperties(this, {
	      key: {
	        enumerable: true,
	        set: (value) => {
	          throw new Error(EnumError.CANNOT_SET);
	        },
	        get: () => key,
	      },
	    });
    }
}
