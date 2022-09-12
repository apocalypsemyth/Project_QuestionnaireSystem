/**
 * 回應資訊之狀態代碼和訊息
 */
class RtnInfo {
    static SUCCESSFUL = new RtnInfo("200", "成功。");
    static PARAMETER_REQUIRED = new RtnInfo("400", "必要參數缺失，請再嘗試。");
    static NOT_FOUND = new RtnInfo("404", "未找到資料。");
    static FAILED = new RtnInfo("500", "發生錯誤，請再嘗試。");
    static DATA_REQUIRED = new RtnInfo("417", "請填寫完整的資料。");
    static SESSION_EXPIRED = new RtnInfo("512", "操作時間過長，請再嘗試。");
    // 更新模式時，刪除全部資料，只是被標記刪除，沒有真的刪掉於Session。
    static NOT_EMPTY_EMPTY = new RtnInfo("666", "Update mode empty");
    static ONE_QUESTION = new RtnInfo("667", "請加入至少一個問題");
    static ONE_REQUIRED_QUESTION = new RtnInfo("668", "請加入至少一個必填問題");

    constructor(statusCode, message) {
        this.statusCode = statusCode;
        this.message = message;
        Object.freeze(this);
    }
}

/**
 * Url之uri
 */
class Url {
	static FRONT = new Url("/front");
    static ANSWERING_QUESTIONNAIRE_DETAIL = new Url("/answeringQuestionnaireDetail");
    static CHECKING_QUESTIONNAIRE_DETAIL = new Url("/checkingQuestionnaireDetail");
	static QUESTIONNAIRE_STATISTICS = new Url("/questionnaireStatistics");
	
    static QUESTIONNAIRE_LIST = new Url("/questionnaireList");

	static BACK_ADMIN = new Url("/backAdmin");
    static QUESTIONNAIRE_DETAIL = new Url("/questionnaireDetail");
	static COMMON_QUESTION_LIST = new Url("/commonQuestionList");
	static COMMON_QUESTION_DETAIL = new Url("/commonQuestionDetail");
	static DOWNLOAD_DATA_TO_CSV = new Url("/downloadDataToCSV");
	
    constructor(uri) {
        this.uri = uri;
        Object.freeze(this);
    }
}

/**
 * 請求資料屬性之健值
 */
class DataProperty {
    static QUESTIONNAIRE_ID = new DataProperty("questionnaire_id");
    static QUESTIONNAIRE_ID_LIST = new DataProperty("questionnaire_id_list");
    static QUESTION_ID = new DataProperty("question_id");
    static QUESTION_ID_LIST = new DataProperty("question_id_list");
    static COMMON_QUESTION_ID = new DataProperty("common_question_id");
    static COMMON_QUESTION_ID_LIST = new DataProperty("common_question_id_list");
    static CATEGORY_ID = new DataProperty("category_id");
    static USER_ANSWER_STRING_LIST = new DataProperty("user_answer_string_list");
    static USER_ID = new DataProperty("user_id");
    static STR_INDEX = new DataProperty("str_index");

    constructor(key) {
        this.key = key;
        Object.freeze(this);
    }
}
