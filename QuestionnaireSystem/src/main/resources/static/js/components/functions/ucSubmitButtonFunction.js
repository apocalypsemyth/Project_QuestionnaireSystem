// 問卷詳細和常用問題詳細頁面方法
/**
 * 檢查問卷或常用問題其問題Session是否為空。
 * @return Promise promise
 */
function HasQuestionSession() {
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/hasQuestionSession`,
        method: METHOD_GET,
        success: function (strRtnInfo) {
			//test
			console.log("HasQuestionSession");
            if (strRtnInfo === RtnInfo.ONE_QUESTION.message) {
                alert(RtnInfo.ONE_QUESTION.message);
                return;
            }
            else if (strRtnInfo === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
				defer.reject();
            }
            else {
                defer.resolve();
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        },
    });
    
    return defer.promise();
}

// 問卷詳細頁面方法
/**
 * 儲存問卷和其問題列表。
 * @param Object 問卷輸入控制項資料
 * @return Promise promise
 */
function SaveQuestionnaire(objQuestionnaire) {
    let defer = $.Deferred();
    
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/saveQuestionnaire`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objQuestionnaire),
        success: function (objQuestionnaireResp) {
            let { status_code, message } = objQuestionnaireResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.ONE_QUESTION.statusCode
                || message === RtnInfo.ONE_QUESTION.message) {
                alert(RtnInfo.ONE_QUESTION.message);
                return;
            }
            else if (status_code === RtnInfo.ONE_REQUIRED_QUESTION.statusCode
                || message === RtnInfo.ONE_REQUIRED_QUESTION.message) {
                alert(RtnInfo.ONE_REQUIRED_QUESTION.message);
				return;
            }
            else {
                defer.resolve();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        },
    });
    
    return defer.promise();
}

// 常用問題詳細頁面方法
/**
 * 儲存常用問題、其問題列表和其問題種類。
 * @param Object 常用問題輸入控制項資料
 * @return Promise promise
 */
function SaveCommonQuestion(objCommonQuestion) {
	let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/saveCommonQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objCommonQuestion),
        success: function (objCommonQuestionResp) {
            let { status_code, message } = objCommonQuestionResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.ONE_QUESTION.statusCode
                || message === RtnInfo.ONE_QUESTION.message) {
                alert(RtnInfo.ONE_QUESTION.message);
                return;
            }
            else if (status_code === RtnInfo.ONE_REQUIRED_QUESTION.statusCode
                || message === RtnInfo.ONE_REQUIRED_QUESTION.message) {
                alert(RtnInfo.ONE_REQUIRED_QUESTION.message);
                return;
            }
            else {
                defer.resolve();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        },
    });

    return defer.promise();
}

// 確認問卷頁面方法
/**
 * 儲存問卷的使用者和使用者回答列表。
 */
function SaveUserAndUserAnswerList() {
	let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/saveUserAndUserAnswerList`,
        method: METHOD_GET,
        success: function (strRtnInfo) {
            if (strRtnInfo === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (strRtnInfo) {
				alert(RtnInfo.FAILED.message);
                defer.reject();
			}
            else {
                defer.resolve();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        },
    });

    return defer.promise();
}
