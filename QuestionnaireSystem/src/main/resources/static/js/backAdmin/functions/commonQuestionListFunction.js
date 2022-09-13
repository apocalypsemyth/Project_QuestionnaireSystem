/**
 * 刪除常用問題和其問題Session。
 */
function DeleteCommonQuestionSession() {
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteCommonQuestionSession`,
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
 * 取得常用問題列表頁面的為了搜尋之輸入控制項資料。
 * @return Object 為了搜尋之輸入控制項資料。
 */
function GetSearchCommonQuestionInputs() {
    let strKeyword = $(txtKeyword).val();
    
    let objSearchCommonQuestion = {
        "keyword": strKeyword,
    };

    return objSearchCommonQuestion;
}
/**
 * 檢查常用問題列表頁面的為了搜尋之輸入控制項資料是否正確，藉由其為了搜尋之輸入控制項資料。
 * @param Object 為了搜尋之輸入控制項資料
 * @return Boolean/Array 為了搜尋之輸入控制項資料是否正確/錯誤訊息陣列
 */
function CheckSearchCommonQuestionInputs(objSearchCommonQuestion) {
	let { keyword } = objSearchCommonQuestion;
    let arrErrorMsg = [];

    if (!HasText(keyword))
        arrErrorMsg.push("請輸入要搜尋的關鍵字。");

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join("\n");
    else
        return true;
}

/**
 * 創建常用問題列表Html，藉由常用問題陣列、資料總數、每頁大小和頁數。
 * @param Array 常用問題陣列
 * @param Number 資料總數
 * @param String 每頁大小
 * @param String 頁數
 */
function CreateCommonQuestionList(objArrCommonQuestion, intTotalRows, strPageSize, strPageIndex) {
    $(divCommonQuestionListContainer).append(
        `
            <table class="table table-bordered w-auto">
                <thead>
                    <tr>
                        <th scope="col">
                        </th>
                        <th scope="col">
                            #
                        </th>
                        <th scope="col">
                            常用問題
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        `
    );

    for (let i = 0; i < objArrCommonQuestion.length; i++) {
        $(divCommonQuestionListContainer + " table tbody").append(
            `
                <tr>
                    <td>
                        <input id="${objArrCommonQuestion[i].commonQuestionId}" type="checkbox">
                    </td>
                    <td>
                        ${intTotalRows - (strPageIndex * strPageSize) - i}
                    </td>
                    <td>
                        <a
                            id="aLinkCommonQuestionDetail-${objArrCommonQuestion[i].commonQuestionId}"
                            href="${Url.BACK_ADMIN.uri}${Url.COMMON_QUESTION_DETAIL.uri}?ID=${objArrCommonQuestion[i].commonQuestionId}"
                        >
                            ${objArrCommonQuestion[i].commonQuestionName}
                        </a>
                    </td>
                </tr>
            `
        );
    }
}
/**
 * 取得符合QueryString條件的常用問題列表回應Body，藉由QueryString。
 * @param String QueryString
 */
function GetCommonQuestionList(strQueryString) {
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getCommonQuestionList${strQueryString}`,
        method: METHOD_GET,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        success: function (objArrCommonQuestion) {
            let { status_code,
                message,
                common_question_list,
                total_data,
                total_rows,
                page_size,
                page_index } = objArrCommonQuestion;
            $(divCommonQuestionListContainer).empty();
            $(divUcPagerContainer).empty();
			EnableControlByHasData(true, false, common_question_list, total_data, total_rows);
			
            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divCommonQuestionListContainer).append(emptyMessageOfList);
            }
            else if (!common_question_list || !common_question_list.length) {
                $(divCommonQuestionListContainer).append(emptyMessageOfList);
            }
            else {
                CreateCommonQuestionList(
                    common_question_list,
                    total_rows,
                    page_size,
                    page_index
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

/**
 * 刪除常用問題、其問題和其問題種類列表。
 * @param Array 常用問題Id陣列
 * @param String QueryString
 */
function DeleteCommonQuestionList(arrCheckedCommonQuestionId, strQueryString) {
    let postData = { [DataProperty.COMMON_QUESTION_ID_LIST.key]: arrCheckedCommonQuestionId };
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteCommonQuestionList${strQueryString}`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(postData),
        success: function (objArrCommonQuestion) {
            let { status_code,
                message,
                common_question_list,
                total_data,
                total_rows,
                page_size,
                page_index } = objArrCommonQuestion;
            $(divCommonQuestionListContainer).empty();
            $(divUcPagerContainer).empty();
			EnableControlByHasData(true, false, total_data, total_rows);
			
            if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                $(divCommonQuestionListContainer).append(emptyMessageOfList);
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divCommonQuestionListContainer).append(emptyMessageOfList);
            }
            else if (!common_question_list || !common_question_list.length) {
                $(divCommonQuestionListContainer).append(emptyMessageOfList);
            }
            else {
                CreateCommonQuestionList(
                    common_question_list,
                    total_rows,
                    page_size,
                    page_index
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
