// 後台頁面全域方法
/**
 * 設置控制項Html於Session，藉由控制項Id和Session名稱。
 * @param String 控制項Id
 * @param String Session名稱
 */
function SetContainerSession(strSelector, strSessionName) {
    let strHtml = $(strSelector).html();
    sessionStorage.setItem(strSessionName, strHtml);
}

// 問卷和常用問題詳細頁面方法
/**
 * 創建問題種類Html，藉由問題種類陣列。
 * @param Array 問題種類陣列
 */
function CreateCategoryList(objArrCategory) {
    for (let i = 0; i < objArrCategory.length; i++) {
        $(selectCategoryList).append(`
            <option value="${objArrCategory[i].categoryId}">${objArrCategory[i].categoryName}</option>
        `);
    }
}
/**
 * 取得問題種類陣列，藉由是否為常用問題詳細頁面。
 * @param Boolean 是否為常用問題詳細頁面
 * @return Promise promise
 */
function GetCategoryList(boolIsCommonQuestion = false) {
	let strQueryString = `?is_common_question=${boolIsCommonQuestion}`;
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getCategoryList${strQueryString}`,
        method: METHOD_GET,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        success: function (objArrCategoryResp) {
            let { status_code, message, category_list } = objArrCategoryResp;
            $(selectCategoryList).empty();
            
            if (status_code === RtnInfo.NOT_FOUND.statusCode
                || message === RtnInfo.NOT_FOUND.message) {
                alert(RtnInfo.NOT_FOUND.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!category_list || !category_list.length) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                CreateCategoryList(category_list);
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
/**
 * 創建問題類別Html，藉由問題類別陣列。
 * @param Array 問題類別陣列
 */
function CreateTypingList(objArrTyping) {
    for (let i = 0; i < objArrTyping.length; i++) {
        $(selectTypingList).append(`
            <option value="${objArrTyping[i].typingId}">${objArrTyping[i].typingName}</option>
        `);
    }
}
/**
 * 取得問題類別陣列。
 * @return Promise promise
 */
function GetTypingList() {
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getTypingList`,
        method: METHOD_GET,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        success: function (objArrTypingResp) {
            let { status_code, message, typing_list } = objArrTypingResp;
            $(selectTypingList).empty();
            
            if (status_code === RtnInfo.NOT_FOUND.statusCode
                || message === RtnInfo.NOT_FOUND.message) {
                alert(RtnInfo.NOT_FOUND.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!typing_list || !typing_list.length) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                CreateTypingList(typing_list);
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
/**
 * 顯示刪除問卷或常用問題其問題按鈕，藉由檢查目標資料是否存在。
 * @param String 問卷或常用問題Id
 * @param Boolean 是否為問卷詳細頁面
 * @return String 錯誤或成功訊息
 */
function ShowBtnDeleteQuestionByHasData(strQuestionnaireOrCommonQuestionId, boolIsQuestionnaire) {
	let objPostData = { [boolIsQuestionnaire 
		? DataProperty.QUESTIONNAIRE_ID.key 
		: DataProperty.COMMON_QUESTION_ID.key]: strQuestionnaireOrCommonQuestionId };
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/showBtnDeleteQuestionByHasData`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        data: JSON.stringify(objPostData),
        success: function (strRtnInfo) {
			$(boolIsQuestionnaire 
				? btnDeleteQuestion 
				: btnDeleteQuestionOfCommonQuestion).hide();
			
            if (strRtnInfo === RtnInfo.FAILED.message) {
				alert(RtnInfo.FAILED.message);
            }
            else if (strRtnInfo === RtnInfo.NOT_FOUND.message) {
				alert(RtnInfo.NOT_FOUND.message);
            }
            else if (strRtnInfo === RtnInfo.NOT_EMPTY_EMPTY.message) {
				
			}
            else if (strRtnInfo === RtnInfo.SUCCESSFUL.message) {
				
			}
			else if (strRtnInfo) {
				alert(RtnInfo.FAILED.message);
			}
            else {
                $(boolIsQuestionnaire 
					? btnDeleteQuestion 
					: btnDeleteQuestionOfCommonQuestion).show();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}
/**
 * 創建問卷或常用問題其問題列表Html，藉由其問題陣列和是否為問卷詳細頁面。
 * @param Array 問卷或常用問題其問題陣列
 * @param Boolean 是否為問卷詳細頁面
 */
function CreateQuestionList(objArrQuestion, boolIsQuestionnaire) {
    $(boolIsQuestionnaire 
    	? divQuestionListContainer 
    	: divQuestionListOfCommonQuestionContainer).append(
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
                            問題
                        </th>
                        <th scope="col">
                            種類
                        </th>
                        <th scope="col">
                            必填
                        </th>
                        <th scope="col">
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        `
    );
	
	let strResult = 
		boolIsQuestionnaire 
	    	? divQuestionListContainer 
	    	: divQuestionListOfCommonQuestionContainer;
    for (let i = 0; i < objArrQuestion.length; i++) {
        $(strResult + " table tbody").append(
            `
                <tr>
                    <td>
                        <input id="${objArrQuestion[i].questionId}" type="checkbox">
                    </td>
                    <td>
                        ${i + 1}
                    </td>
                    <td>
                        ${objArrQuestion[i].questionName}
                    </td>
                    <td>
                        ${objArrQuestion[i].questionTyping}
                    </td>
                    <td>
                    	<input type="checkbox" style="pointer-events: none;" ${objArrQuestion[i].questionRequired ? "checked='checked'" : null} />
                    </td>
                    <td>
                        <a
                        	th:onclick="javascript:void(0)"
                            id="${boolIsQuestionnaire 
                            	? "aLinkUpdateQuestion" 
                            	: "aLinkUpdateQuestionOfCommonQuestion"}-${objArrQuestion[i].questionId}"
                            href="${boolIsQuestionnaire 
                            	? "#QuestionID" 
                            	: "#QuestionIDOfCommonQuestion"}=${objArrQuestion[i].questionId}"
                        >
                        	編輯
                        </a>
                    </td>
                </tr>
            `
        );
    }
}
/**
 * 顯示問卷或常用問題其問題列表，藉由檢查問題陣列之isDeleted屬性。
 * @param Array 問卷或常用問題其問題陣列
 * @param Boolean 是否為問卷詳細頁面
 */
function ShowQuestionListByIsDeleted(objArrQuestion, boolIsQuestionnaire) {
    let filteredObjArrQuestion = objArrQuestion.filter(item => !item.isDeleted);

    if (!filteredObjArrQuestion.length) {
        $(boolIsQuestionnaire 
    		? btnDeleteQuestion 
    		: btnDeleteQuestionOfCommonQuestion).hide();
        $(boolIsQuestionnaire 
        	? divQuestionListContainer 
        	: divQuestionListOfCommonQuestionContainer).append(emptyMessageOfList);
    }
    else {
		$(boolIsQuestionnaire 
	    	? btnDeleteQuestion 
	    	: btnDeleteQuestionOfCommonQuestion).show();
        CreateQuestionList(filteredObjArrQuestion, boolIsQuestionnaire);
    }
}
/**
 * 重置問卷其問題輸入控制項資料，藉由種類名稱(自訂問題/常用問題)。
 * @param String 種類名稱(自訂問題/常用問題)
 */
function ResetQuestionInputs(strCategoryName = customizedQuestionOfCategory) {
    SelectCategoryInSelectCategoryList(strCategoryName);
    $(selectTypingList + " option").filter(function () {
        return $(this).text() === SINGLE_SELECT;
    }).prop("selected", true);
    $(txtQuestionName).val("");
    $(txtQuestionAnswer).val("");
    $(ckbQuestionRequired).prop("checked", false);
}
/**
 * 重置常用問題其問題輸入控制項資料，藉由種類名稱(自訂問題/常用問題)。
 * @param String 種類名稱(自訂問題/常用問題)
 */
function ResetQuestionOfCommonQuestionInputs(strCategoryName = commonQuestionOfCategory) {
    $(selectCategoryList + " option").filter(function () {
        return $(this).text() === strCategoryName;
    }).prop("selected", true);
    $(selectTypingList + " option").filter(function () {
        return $(this).text() === SINGLE_SELECT;
    }).prop("selected", true);
    $(txtQuestionNameOfCommonQuestion).val("");
    $(txtQuestionAnswerOfCommonQuestion).val("");
    $(ckbQuestionRequiredOfCommonQuestion).prop("checked", false);
}
/**
 * 創建問卷或常用問題其問題，藉由其問題輸入控制項資料和是否為問卷詳細頁面。
 * @param Object 問卷其問題輸入控制項資料
 * @param Boolean 是否為問卷詳細頁面
 * @return Promise promise
 */
function CreateQuestion(objQuestion, boolIsQuestionnaire) {
	let strQueryString = `?is_questionnaire=${boolIsQuestionnaire}`;
    let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/createQuestion${strQueryString}`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objQuestion),
        success: function (objArrQuestionResp) {
            let { status_code, message, question_session_list } = objArrQuestionResp;
            if (boolIsQuestionnaire) {
	            ResetQuestionInputs();
	            $(btnDeleteQuestion).hide();
	            $(divQuestionListContainer).empty();
			}
			else {
				ResetQuestionOfCommonQuestionInputs();
	            $(btnDeleteQuestionOfCommonQuestion).hide();
	            $(divQuestionListOfCommonQuestionContainer).empty();
			}
            
            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                ShowQuestionListByIsDeleted(question_session_list, boolIsQuestionnaire);
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
/**
 * 刪除問卷其問題列表，藉由其問題Id陣列和是否為問卷詳細頁面。
 * @param Array 問卷其問題Id陣列
 * @param Boolean 是否為問卷詳細頁面
 */
function DeleteQuestionList(arrCheckedQuestionId, boolIsQuestionnaire) {
	let strQueryString = `?is_questionnaire=${boolIsQuestionnaire}`;
    let objPostData = { [DataProperty.QUESTION_ID_LIST.key]: arrCheckedQuestionId };

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteQuestionList${strQueryString}`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrQuestionResp) {
            let { status_code, message, question_session_list } = objArrQuestionResp;
            if (boolIsQuestionnaire) {
	            ResetQuestionInputs();
	            $(btnDeleteQuestion).hide();
	            $(divQuestionListContainer).empty();
			}
			else {
				ResetQuestionOfCommonQuestionInputs();
	            $(btnDeleteQuestionOfCommonQuestion).hide();
	            $(divQuestionListOfCommonQuestionContainer).empty();
			}
            let resultDivDataListContainer = 
            	boolIsQuestionnaire 
            		? divQuestionListContainer 
            		: divQuestionListOfCommonQuestionContainer;
            
            if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                $(resultDivDataListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(resultDivDataListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else if (!question_session_list || !question_session_list.length) {
                $(resultDivDataListContainer).append(emptyMessageOfList);
            }
            else {
                ShowQuestionListByIsDeleted(question_session_list, boolIsQuestionnaire);
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}
/**
 * 設置問卷其問題輸入控制項資料，藉由其問題輸入控制項資料。
 * @param Object 問卷其問題輸入控制項資料
 */
function SetQuestionInputs(objQuestion) {
    let { questionCategory, questionTyping, questionName, questionAnswer, questionRequired } = objQuestion;

    $(selectCategoryList + " option").filter(function () {
        return $(this).text() === questionCategory;
    }).prop("selected", true);
    $(selectTypingList + " option").filter(function () {
        return $(this).text() === questionTyping;
    }).prop("selected", true);
    $(txtQuestionName).val(questionName);
    $(txtQuestionAnswer).val(questionAnswer);
    $(ckbQuestionRequired).prop("checked", questionRequired);
}
/**
 * 設置常用問題其問題輸入控制項資料，藉由常用問題其問題輸入控制項資料。
 * @param Object 常用問題其問題輸入控制項資料
 */
function SetQuestionOfCommonQuestionInputs(objQuestionOfCommonQuestion) {
    let { questionCategory, questionTyping, questionName, questionAnswer, questionRequired } = objQuestionOfCommonQuestion;

    $(selectCategoryList + " option").filter(function () {
        return $(this).text() === questionCategory;
    }).prop("selected", true);
    $(selectTypingList + " option").filter(function () {
        return $(this).text() === questionTyping;
    }).prop("selected", true);
    $(txtQuestionNameOfCommonQuestion).val(questionName);
    $(txtQuestionAnswerOfCommonQuestion).val(questionAnswer);
    $(ckbQuestionRequiredOfCommonQuestion).prop("checked", questionRequired);
}
/**
 * 顯示欲更新問卷其問題，藉由其問題Id和是否為問卷詳細頁面。
 * @param String 問卷其問題Id
 * @param Boolean 是否為問卷詳細頁面
 */
function ShowToUpdateQuestion(strQuestionId, boolIsQuestionnaire) {
    let objPostData = { [DataProperty.QUESTION_ID.key]: strQuestionId };

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/showToUpdateQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrQuestionResp) {
            let { status_code, message, question_session_list } = objArrQuestionResp;
			let resultDivDataListContainer = 
            	boolIsQuestionnaire 
            		? divQuestionListContainer 
            		: divQuestionListOfCommonQuestionContainer;
    		let resultSetDataInputs = 
    			boolIsQuestionnaire 
    				? SetQuestionInputs 
    				: SetQuestionOfCommonQuestionInputs;
			
            if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                $(resultDivDataListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(resultDivDataListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else {
                resultSetDataInputs(question_session_list.pop());
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}
/**
 * 更新問卷其問題，藉由其問題輸入控制項資料和是否為問卷詳細頁面。
 * @param Object 問卷其問題輸入控制項資料
 * @param Boolean 是否為問卷詳細頁面
 * @return Promise promise
 */
function UpdateQuestion(objQuestion, boolIsQuestionnaire) {
    let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/updateQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objQuestion),
        success: function (objArrQuestionResp) {
            let { status_code, message, question_session_list } = objArrQuestionResp;
            if (boolIsQuestionnaire) {
	            ResetQuestionInputs();
	            $(btnDeleteQuestion).hide();
	            $(divQuestionListContainer).empty();
			}
			else {
				ResetQuestionOfCommonQuestionInputs();
	            $(btnDeleteQuestionOfCommonQuestion).hide();
	            $(divQuestionListOfCommonQuestionContainer).empty();
			}

            if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                ShowQuestionListByIsDeleted(question_session_list, boolIsQuestionnaire);
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

// 問卷詳細頁面和ucSubmitButton component方法
/**
 * 取得問卷輸入控制項資料。
 * @return Object 問卷輸入控制項資料
 */
function GetQuestionnaireInputs() {
    let strCaption = $(txtCaption).val();
    let strDescription = $(txtDescription).val();
    let strStartDate = $(txtStartDate).val();
    let strEndDate = $(txtEndDate).val();
    let boolIsEnable = $(ckbIsEnable).is(":checked");

    let objQuestionnaire = {
        "caption": strCaption,
        "description": strDescription,
        "start_date": strStartDate,
        "end_date": strEndDate,
        "is_enable": boolIsEnable,
    };

    return objQuestionnaire;
}
/**
 * 檢查問卷輸入控制項資料是否正確，藉由問卷輸入控制項資料。
 * @return Boolean/Array 問卷輸入控制項資料是否正確/錯誤訊息陣列
 */
function CheckQuestionnaireInputs(objQuestionnaire) {
    let { caption, description, start_date, end_date, is_enable } = objQuestionnaire;
    let arrErrorMsg = [];
    let regex = /^[0-9]{4}\/[0-9]{2}\/[0-9]{2}$/;

    if (!caption)
        arrErrorMsg.push("請填入問卷名稱。");

    if (!description)
        arrErrorMsg.push("請填入描述內容。");

    let isUpdateMode = HasText(GetQueryParamOfQueryString("ID"));
    let today = new Date().toDateString();
    let todayMillisecond = Date.parse(today);

    if (!start_date)
        arrErrorMsg.push("請填入開始日期。");
    else {
        if (!regex.test(start_date))
            arrErrorMsg.push(`請以 "yyyy/MM/dd" 的格式輸入開始日期。`);
        else if (!IsValidDate(start_date))
        	arrErrorMsg.push(`請輸入合法的開始日期。`);
        else {
            let startDate = new Date(start_date);
            let startDateMillisecond = Date.parse(startDate);

            if (is_enable && startDateMillisecond > todayMillisecond)
                arrErrorMsg.push("系統屆時會開放此問卷，請取消已啟用的勾選");
            else if (!isUpdateMode && startDateMillisecond < todayMillisecond)
                arrErrorMsg.push("請填入今天或其後的開始日期。");
        }
    }

    if (end_date) {
        if (!regex.test(end_date))
            arrErrorMsg.push(`請以 "yyyy/MM/dd" 的格式輸入結束日期。`);
        else if (!IsValidDate(end_date))
        	arrErrorMsg.push(`請輸入合法的結束日期。`);
        else {
            let startDate = new Date(start_date);
            let startDateMillisecond = Date.parse(startDate);
            let endDate = new Date(end_date);
            let endDateMillisecond = Date.parse(endDate);

            if (is_enable && endDateMillisecond < todayMillisecond)
                arrErrorMsg.push("若要開放此問卷，請填入今天或其後的結束日期。");
            else if (!isUpdateMode && endDateMillisecond < todayMillisecond)
                arrErrorMsg.push("請填入今天或其後的結束日期。");
            else if (startDateMillisecond > endDateMillisecond)
                arrErrorMsg.push("請填入一前一後時序的始末日期。");
        }
    }

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join("\n");
    else
        return true;
}
/**
 * 取得預設之問卷輸入控制項資料，為了新增模式時的初始狀態。
 * @return Object 預設之問卷輸入控制項資料
 */
function GetDefaultObjQuestionnaire() {
    return {
        "caption": "",
        "description": "",
        "startDate": "",
        "endDate": "",
        "isEnable": true
    };
}
/**
 * 設置問卷輸入控制項資料，藉由預設或使用者填寫之問卷輸入控制項資料。
 */
function SetQuestionnaireInputs(objQuestionnaire = GetDefaultObjQuestionnaire()) {
    let { caption, description, startDate, endDate, isEnable } = objQuestionnaire;

    $(txtCaption).val(caption);
    $(txtDescription).val(description);
    $(txtStartDate).val(startDate);
    $(txtEndDate).val(endDate);
    $(ckbIsEnable).prop("checked", isEnable);
}
/**
 * 創建問卷，藉由問卷輸入控制項資料。
 * @param Object 問卷輸入控制項資料
 * @return Promise promise
 */
function CreateQuestionnaire(objQuestionnaire) {
    let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/createQuestionnaire`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objQuestionnaire),
        success: function (objQuestionnaireResp) {
            let { status_code, message, questionnaire_session } = objQuestionnaireResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!questionnaire_session) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                let { startDate, endDate } = questionnaire_session;

                questionnaire_session.startDate = FormatDate(startDate);
                questionnaire_session.endDate = HasText(endDate) ? FormatDate(endDate) : null;
                SetQuestionnaireInputs(questionnaire_session);
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
/**
 * 更新問卷，藉由問卷輸入控制項資料。
 * @param Object 問卷輸入控制項資料
 * @return Promise promise
 */
function UpdateQuestionnaire(objQuestionnaire) {
    let defer = $.Deferred();

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/updateQuestionnaire`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objQuestionnaire),
        success: function (objQuestionnaireResp) {
            let { status_code, message, questionnaire_session } = objQuestionnaireResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!questionnaire_session) {
				alert(RtnInfo.FAILED.message);
                defer.reject();
			}
            else {
                let { startDate, endDate } = questionnaire_session;

                questionnaire_session.startDate = FormatDate(startDate);
                questionnaire_session.endDate = HasText(endDate) ? FormatDate(endDate) : null;
                SetQuestionnaireInputs(questionnaire_session);
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

// 常用問題詳細頁面和ucSubmitButton component方法
/**
 * 取得常用問題輸入控制項資料。
 * @return Object 常用問題輸入控制項資料
 */
function GetCommonQuestionInputs() {
    let strCommonQuestionName = $(txtCommonQuestionName).val();

    let objCommonQuestion = {
        "common_question_name": strCommonQuestionName,
    };

    return objCommonQuestion;
}
/**
 * 檢查常用問題輸入控制項資料是否正確，藉由常用問題輸入控制項資料。
 * @param Object 常用問題輸入控制項資料
 * @return Boolean/Array 常用問題輸入控制項資料是否正確/錯誤訊息陣列
 */
function CheckCommonQuestionInputs(objCommonQuestion) {
	let { common_question_name } = objCommonQuestion;
    let arrErrorMsg = [];

    if (!common_question_name)
        arrErrorMsg.push("請填入常用問題名稱。");

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join();
    else
        return true;
}
/**
 * 設置常用問題輸入控制項資料，藉由常用問題輸入控制項資料。
 * @param Object 常用問題輸入控制項資料
 */
function SetCommonQuestionInputs(objCommonQuestion) {
    let { commonQuestionName } = objCommonQuestion;

    $(txtCommonQuestionName).val(commonQuestionName);
}
/**
 * 創建常用問題，藉由常用問題輸入控制項資料。
 * @param Object 常用問題輸入控制項資料
 * @return Promise promise
 */
function CreateCommonQuestion(objCommonQuestion) {
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/createCommonQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objCommonQuestion),
        success: function (objCommonQuestionResp) {
            let { status_code, message, common_question_session } = objCommonQuestionResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!common_question_session) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
                SetCommonQuestionInputs(common_question_session);
                defer.resolve();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        }
    });
    
    return defer.promise();
}
/**
 * 更新常用問題，藉由常用問題輸入控制項資料。
 * @param Object 常用問題輸入控制項資料
 * @return Promise promise
 */
function UpdateCommonQuestion(objCommonQuestion) {
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/updateCommonQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objCommonQuestion),
        success: function (objCommonQuestionResp) {
            let { status_code, message, common_question_session } = objCommonQuestionResp;

            if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!common_question_session) {
				alert(RtnInfo.FAILED.message);
                defer.reject();
			}
            else {
                SetCommonQuestionInputs(common_question_session);
                defer.resolve();
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        }
    });
    
    return defer.promise();
}
