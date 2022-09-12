/**
 * 取得問卷列表頁面的為了搜尋之輸入控制項資料。
 * @return Object 為了搜尋之輸入控制項資料。
 */
function GetSearchQuestionnaireInputs() {
    let strKeyword = $(txtKeyword).val();
    let strStartDate = $(txtStartDate).val();
    let strEndDate = $(txtEndDate).val();

    let objSearchQuestionnaire = {
        "keyword": strKeyword,
        "startDate": strStartDate,
        "endDate": strEndDate,
    };

    return objSearchQuestionnaire;
}
/**
 * 檢查問卷列表頁面的為了搜尋之輸入控制項資料是否正確，藉由其為了搜尋之輸入控制項資料。
 * @param Object 為了搜尋之輸入控制項資料
 * @return Boolean/Array 為了搜尋之輸入控制項資料是否正確/錯誤訊息陣列
 */
function CheckSearchQuestionnaireInputs(objSearchQuestionnaire) {
    let { keyword, startDate, endDate } = objSearchQuestionnaire;
    let arrErrorMsg = [];
    let regex = /^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/;
    let isAnyInputsHasText = HasText(keyword) || HasText(startDate) || HasText(endDate);
    let isAllInputsHasText = HasText(keyword) && (HasText(startDate) || HasText(endDate));

    if (isAllInputsHasText)
        arrErrorMsg.push("一次只能利用關鍵字或始末日期搜尋。");
    else if (HasText(keyword))
        return true;
    else if (HasText(startDate) || HasText(endDate)) {
        if (!HasText(startDate))
            arrErrorMsg.push("請輸入要搜尋的開始日期。");
        else if (!regex.test(startDate))
            arrErrorMsg.push(`請以 "yyyy/MM/dd" 的格式輸入開始日期。`);
		else if (!IsValidDate(startDate))
        	arrErrorMsg.push(`請輸入合法的開始日期。`);
		
        if (!HasText(endDate))
            arrErrorMsg.push("請輸入要搜尋的結束日期。");
        else if (!regex.test(endDate))
            arrErrorMsg.push(`請以 "yyyy/MM/dd" 的格式輸入結束日期。`);
        else if (!IsValidDate(endDate))
        	arrErrorMsg.push(`請輸入合法的結束日期。`);

        let startDateToParse = new Date(startDate);
        let startDateMillisecond = Date.parse(startDateToParse);
        let endDateToParse = new Date(endDate);
        let endDateMillisecond = Date.parse(endDateToParse);

        if (startDateMillisecond > endDateMillisecond)
            arrErrorMsg.push("請填入一前一後時序的始末日期。");
    }
    else if (!isAnyInputsHasText)
        arrErrorMsg.push("請輸入要搜尋的關鍵字或始末日期");

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join("\n");
    else
        return true;
}
/**
 * 啟用控制項，藉由是否為後台、問卷頁面、全部資料總數和資料總數。
 * @param Boolean 是否為後台頁面
 * @param Boolean 是否為問卷頁面
 * @param Number 全部資料總數
 * @param Number 資料總數
 */
function EnableControlByHasData(boolIsBackAdmin, boolIsQuestionnaire, intTotalData, intTotalRows) {
	let hasTotalData = isNaN(intTotalData) ? false : intTotalData === 0 ? false : true;
	let hasTotalRows = isNaN(intTotalRows) ? false : intTotalRows === 0 ? false : true;
	if (hasTotalData && hasTotalRows) {
		let isDisabled = false;
		if (boolIsBackAdmin && boolIsQuestionnaire) {
			$(btnSearchQuestionnaireList).attr("disabled", isDisabled);
			$(btnDeleteQuestionnaireList).attr("disabled", isDisabled);
		}
		else if (boolIsQuestionnaire) {
			$(btnSearchQuestionnaireList).attr("disabled", isDisabled);
		}
		else {
			$(btnSearchCommonQuestionList).attr("disabled", isDisabled);
			$(btnDeleteCommonQuestionList).attr("disabled", isDisabled);
		}
	}
	else {
		let isDisabled = true;
		if (boolIsBackAdmin && boolIsQuestionnaire) {
			$(btnSearchQuestionnaireList).attr("disabled", !hasTotalData);
			$(btnDeleteQuestionnaireList).attr("disabled", isDisabled);
		}
		else if (boolIsQuestionnaire) {
			$(btnSearchQuestionnaireList).attr("disabled", !hasTotalData);
		}
		else {
			$(btnSearchCommonQuestionList).attr("disabled", !hasTotalData);
			$(btnDeleteCommonQuestionList).attr("disabled", isDisabled);
		}
	}
}
/**
 * 創建問卷列表Html，藉由問卷陣列、資料總數、每頁大小、頁數和是否為後台頁面。
 * @param Array 問卷陣列
 * @param Number 資料總數
 * @param String 每頁大小
 * @param String 頁數
 * @param Boolean 是否為後台頁面
 */
function CreateQuestionnaireList(
	objArrQuestionnaire, 
	intTotalRows, 
	strPageSize, 
	strPageIndex,
	boolIsBackAdmin
	) {
    $(divQuestionnaireListContainer).append(
        `
            <table class="table table-bordered w-auto">
                <thead>
                    <tr>
                    	${boolIsBackAdmin ? '<th scope="col"></th>' : ''}
                        <th scope="col">
                            #
                        </th>
                        <th scope="col">
                            問卷
                        </th>
                        <th scope="col">
                            狀態
                        </th>
                        <th scope="col">
                            開始時間
                        </th>
                        <th scope="col">
                            結束時間
                        </th>
                        <th scope="col">
                            觀看統計
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        `
    );

    for (let i = 0; i < objArrQuestionnaire.length; i++) {
		let ckbQuestionnaire = 
			boolIsBackAdmin 
				? `<td>
					<input id="${objArrQuestionnaire[i].questionnaireId}" type="checkbox">
				  </td>` 
				: null;
		let aLinkQuestionnaire =
			boolIsBackAdmin 
				? `<a id="aLinkQuestionnaireDetail-${objArrQuestionnaire[i].questionnaireId}"
        			  href="${Url.BACK_ADMIN.uri}${Url.QUESTIONNAIRE_DETAIL.uri}?ID=${objArrQuestionnaire[i].questionnaireId}"
                   >
                      ${objArrQuestionnaire[i].caption}
                  </a>` 
				: `<a id="aLinkAnsweringQuestionnaireDetail-${objArrQuestionnaire[i].questionnaireId}"
                      href="${Url.ANSWERING_QUESTIONNAIRE_DETAIL.uri}?ID=${objArrQuestionnaire[i].questionnaireId}"
                   >
                      ${objArrQuestionnaire[i].caption}
                  </a>`;
		let isEnableQuestionnaire = 
			boolIsBackAdmin 
				? objArrQuestionnaire[i].isEnable ? "開放" : "已關閉" 
				: objArrQuestionnaire[i].isEnable ? "投票中" : "已完結";   
        let aLinkQuestionnaireStatistics =
			boolIsBackAdmin 
				? `<a id="aLinkQuestionnaireDetailStatistics-${objArrQuestionnaire[i].questionnaireId}"
                      href="${Url.BACK_ADMIN.uri}${Url.QUESTIONNAIRE_DETAIL.uri}?ID=${objArrQuestionnaire[i].questionnaireId}#statistics"
                   >
                      前往
                  </a>` 
				: `<a id="aLinkQuestionnaireStatistics-${objArrQuestionnaire[i].questionnaireId}"
                      href="${Url.QUESTIONNAIRE_STATISTICS.uri}?ID=${objArrQuestionnaire[i].questionnaireId}"
                   >
                      前往
                  </a>`;
                           	
        $(divQuestionnaireListContainer + " table tbody").append(
            `
                <tr>
                	${ckbQuestionnaire}
                    <td>
                        ${intTotalRows - (strPageIndex * strPageSize) - i}
                    </td>
                    <td>
                        ${aLinkQuestionnaire}
                    </td>
                    <td>
                        ${isEnableQuestionnaire}
                    </td>
                    <td>
                        ${FormatDate(objArrQuestionnaire[i].startDate)}
                    </td>
                    <td>
                        ${objArrQuestionnaire[i].endDate == null
			                ? "---"
			                : FormatDate(objArrQuestionnaire[i].endDate)}
                    </td>
                    <td>
                        ${aLinkQuestionnaireStatistics}
                    </td>
                </tr>
            `
        );
    }
}
/**
 * 取得符合QueryString條件的問卷列表回應Body，藉由QueryString和是否為後台頁面。
 * @param String QueryString
 * @param Boolean 是否為後台頁面
 */
function GetQuestionnaireList(strQueryString, boolIsBackAdmin) {
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getQuestionnaireList${strQueryString}`,
        method: METHOD_GET,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
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
            EnableControlByHasData(boolIsBackAdmin, true, total_data, total_rows);

            if (status_code === RtnInfo.FAILED.statusCode
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
                    boolIsBackAdmin
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
