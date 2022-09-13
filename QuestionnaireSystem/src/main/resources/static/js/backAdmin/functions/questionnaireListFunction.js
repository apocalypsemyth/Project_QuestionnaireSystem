/**
 * 刪除問卷和其問題Session。
 */
function DeleteQuestionnaireSession() {
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteQuestionnaireSession`,
        method: METHOD_GET,
        success: function (strIsDeleted) {
            if (strIsDeleted === "false") {
                alert(RtnInfo.FAILED.message);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}
/**
 * 刪除使用者和使用者回答列表Session。
 */
function DeleteSessionOfListOfUserAndUserAnswer() {
	$.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteSessionOfListOfUserAndUserAnswer`,
        method: METHOD_GET,
        success: function (strIsDeleted) {
            if (strIsDeleted === "false") {
                alert(RtnInfo.FAILED.message);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}

/**
 * 刪除問卷、其問題、其使用者和其使用者回答列表。
 * @param Array 問卷Id陣列
 * @param String QueryString
 */
function DeleteQuestionnaireList(arrCheckedQuestionnaireId, strQueryString) {
    let postData = { [DataProperty.QUESTIONNAIRE_ID_LIST.key]: arrCheckedQuestionnaireId };
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteQuestionnaireList${strQueryString}`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(postData),
        success: function (objArrQuestionnaire) {
            let { status_code,
                message,
                questionnaire_list,
                total_data,
                total_rows,
                page_size,
                page_index } = objArrQuestionnaire;
            $(divQuestionnaireListContainer).empty();
            $(divUcPagerContainer).empty();
			EnableControlByHasData(true, true, questionnaire_list, total_data, total_rows);

            if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                $(divQuestionnaireListContainer).append(emptyMessageOfList);
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divQuestionnaireListContainer).append(emptyMessageOfList);
            }
            else if (!questionnaire_list || !questionnaire_list.length) {
                $(divQuestionnaireListContainer).append(emptyMessageOfList);
            }
            else {
                CreateQuestionnaireList(
                    questionnaire_list,
                    total_rows,
                    page_size,
                    page_index,
                    true
                );
                CreateUcPager(total_rows, page_size, strQueryString);
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}
