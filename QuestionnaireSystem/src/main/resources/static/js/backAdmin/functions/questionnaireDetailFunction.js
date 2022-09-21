/**
 * 設置容器控制項的顯示狀態，藉由其Session名稱和顯示狀態。
 * @param String 容器控制項Session名稱
 * @param String 顯示狀態 (showState/hideState)
 */
function SetContainerShowStateSession(strSessionName, strShowState) {
    sessionStorage.setItem(strSessionName, strShowState);
}
/**
 * 設置是否套用常用問題於問卷上之現在狀態，藉由Session名稱和現在狀態。
 * @param String Session名稱
 * @param String 現在狀態 (setState/notSetState)
 */
function SetElementCurrentStateSession(strSessionName, strCurrentState) {
    sessionStorage.setItem(strSessionName, strCurrentState);
}

/**
 * 取得問卷其問題輸入控制項資料。
 * @return Object 問卷其問題輸入控制項資料
 */
function GetQuestionInputs() {
    let strCategoryName = $(selectCategoryList).find(":selected").text();
    let strTypingName = $(selectTypingList).find(":selected").text();
    let strQuestionName = $(txtQuestionName).val();
    let strQuestionAnswer = $(txtQuestionAnswer).val();
    let boolQuestionRequired = $(ckbQuestionRequired).is(":checked");

    let objQuestion = {
        "question_category": strCategoryName,
        "question_typing": strTypingName,
        "question_name": strQuestionName,
        "question_answer": strQuestionAnswer,
        "question_required": boolQuestionRequired,
    };

    return objQuestion;
}
/**
 * 檢查問卷其問題輸入控制項資料是否正確，藉由其問題輸入控制項資料。
 * @param Object 問卷其問題輸入控制項資料
 * @return Boolean/Array 問卷其問題輸入控制項資料是否正確/錯誤訊息
 */
function CheckQuestionInputs(objQuestion) {
    let { question_name, question_answer } = objQuestion;
    let arrErrorMsg = [];

    if (!question_name)
        arrErrorMsg.push("請填入問題名稱。");

    if (!question_answer)
        arrErrorMsg.push("請填入問題回答。");
    else {
        let hasSemicolon = question_answer.indexOf(";") !== -1;

        let strArrChecking =
            hasSemicolon
                ? question_answer.trim().split(";")
                : question_answer;

        if (Array.isArray(strArrChecking)) {
            let hasWhiteSpace = strArrChecking.some(item => /\s/.test(item));
            let hasStartOrEndWhiteSpace = strArrChecking.some(strChecking => !strChecking);

            if (hasWhiteSpace)
                arrErrorMsg.push("請不要留空於分號之間。");
            if (hasStartOrEndWhiteSpace)
                arrErrorMsg.push("請不要分號於開頭或結尾。");
        }
    }

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join("\n");
    else
        return true;
}

/**
 * 重置問卷輸入控制項資料。
 */
function ResetQuestionnaireInputs() {
    let result = FormatDate(Date.now());
	let temp = GetDefaultObjQuestionnaire();
	
    temp.startDate = result;
    SetQuestionnaireInputs(temp);
}
/**
 * 取得問卷回應Body，藉由問卷Id。
 * @param String 問卷Id
 */
function GetQuestionnaire(strQuestionnaireId) {
    let objPostData = { [DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId };

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getQuestionnaire`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objQuestionnaireResp) {
            let { status_code, message, questionnaire_session } = objQuestionnaireResp;

            if (status_code === RtnInfo.NOT_FOUND.statusCode
                || message === RtnInfo.NOT_FOUND.message) {
                alert(RtnInfo.NOT_FOUND.message);
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
            }
            else if (status_code === RtnInfo.SUCCESSFUL.statusCode
                && message === RtnInfo.SUCCESSFUL.message
                && !questionnaire_session) {
				let temp = GetDefaultObjQuestionnaire();
                temp.startDate = FormatDate(new Date());
                temp.endDate = null;
                temp.isEnable = true;
                SetQuestionnaireInputs(temp);
            }
            else if (!questionnaire_session) {
                alert(RtnInfo.FAILED.message);
            }
            else {
                let { startDate, endDate, isEnable } = questionnaire_session;

                questionnaire_session.startDate = FormatDate(HasText(startDate) ? startDate : new Date());
                questionnaire_session.endDate = HasText(endDate) ? FormatDate(endDate) : null;
                questionnaire_session.isEnable = isEnable == null ? true : isEnable;
                SetQuestionnaireInputs(questionnaire_session);
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}

/**
 * 設置問題種類其種類名稱為常用問題之Id於Session，藉由種類名稱(自訂問題/常用問題)和Session名稱。
 * @param String 種類名稱(自訂問題/常用問題)
 * @param String Session名稱
 */
function SetCommonQuestionOfCategoryId(
	strCategoryName = commonQuestionOfCategory, 
	strSessionName = currentCommonQuestionOfCategoryId
	) {
	let defer = $.Deferred();
	try {
		$(selectCategoryList + " option").each(function() {
			if ($(this).text() === strCategoryName)
				sessionStorage.setItem(strSessionName, $(this).val());
		});
		defer.resolve();
	} catch (e) {
		console.log(`Set common question of categoryId error: ${e}`)
		defer.reject();
	}
	return defer.promise();
}
/**
 * 顯示或隱藏問題種類其種類名稱為常用問題於下拉選單，藉由是否套用常用問題於問卷上之現在狀態。
 */
function ShowOrHideCommonQuestionOfCategory() {
	let defer = $.Deferred();
	try {
		let strCurrentSetCommonQuestionOnQuestionnaireState =
            sessionStorage.getItem(currentSetCommonQuestionOnQuestionnaireState);
	    let strCurrentCommonQuestionOfCategoryId =
	        sessionStorage.getItem(currentCommonQuestionOfCategoryId);
	    if (strCurrentSetCommonQuestionOnQuestionnaireState === setState) {
	        $(selectCategoryList 
	        	+ " option[value='" 
	        	+ strCurrentCommonQuestionOfCategoryId 
	        	+ "']").show();
		}
	    else {
			$(selectCategoryList 
				+ " option[value='" 
				+ strCurrentCommonQuestionOfCategoryId 
				+ "']").hide();
		}
		defer.resolve();
	} catch (e) {
		console.log(`Show or hide common question of category error: ${e}`)
		defer.reject();
	}
	return defer.promise();
}
/**
 * 隱藏擁有目標Id的問題種類於下拉選單，藉由問題種類Id。
 * @param String 問題種類Id
 */
function HideCategoryInSelectCategoryList(strCategoryId) {
	$(selectCategoryList 
    	+ " option[value='" 
    	+ strCategoryId 
    	+ "']").hide();
}
/**
 * 選擇擁有目標種類名稱的問題種類於下拉選單，藉由種類名稱(自訂問題/常用問題)。
 * @param String 種類名稱(自訂問題/常用問題)
 */
function SelectCategoryInSelectCategoryList(strCategoryName = customizedQuestionOfCategory) {
	$(selectCategoryList + " option").filter(function () {
        return $(this).text() === strCategoryName;
    }).prop("selected", true);
}
/**
 * 取得問卷其問題列表回應Body，藉由問卷Id。
 * @param String 問卷Id
 */
function GetQuestionList(strQuestionnaireId) {
    let objPostData = { [DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId };

    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getQuestionList`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrQuestionResp) {
            let { status_code, message, question_session_list } = objArrQuestionResp;
            ResetQuestionInputs();
            $(btnDeleteQuestion).hide();
            $(divQuestionListContainer).empty();

            if (status_code === RtnInfo.NOT_FOUND.statusCode
                || message === RtnInfo.NOT_FOUND.message) {
                alert(RtnInfo.NOT_FOUND.message);
                $(divQuestionListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divQuestionListContainer).append(WrapMessageWithHtmlTag(RtnInfo.FAILED.message));
            }
            else if (!question_session_list || !question_session_list.length) {
				
			}
            else {
                ShowQuestionListByIsDeleted(question_session_list, true);
            }
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}

/**
 * 取得後端Session之是否套用常用問題其問題列表於問卷上後，設置於前端Session，接著相應地重置問卷其問題輸入控制項資料。
 * @return Promise promise
 */
function GetIsSetQuestionListOfCommonQuestion() {
	let defer = $.Deferred();
	
	$.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getIsSetQuestionListOfCommonQuestion`,
        method: METHOD_GET,
        success: function (strIsSet) {
			ResetQuestionInputs();
			
			if (strIsSet === RtnInfo.FAILED.message) {
				alert(RtnInfo.FAILED.message);
				defer.reject();
			}
			else if (strIsSet === "false") {
				SetElementCurrentStateSession(
                    currentSetCommonQuestionOnQuestionnaireState,
                    notSetState
                );
                defer.resolve();
			}
			else if (strIsSet === "true") {
				SetElementCurrentStateSession(
                    currentSetCommonQuestionOnQuestionnaireState,
                    setState
                );
				ResetQuestionInputs(commonQuestionOfCategory);
                defer.resolve();
			}
			else {
				alert(RtnInfo.FAILED.message);
				defer.reject();
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
 * 設置是否套用常用問題其問題列表於問卷上之狀態於後端Session。
 * @param Boolean 是否套用常用問題其問題列表於問卷上
 * @return Promise promise
 */
function SetIsSetQuestionListOfCommonQuestion(boolIsSet) {
	let strQueryString = `?is_set=${boolIsSet}`;
	let defer = $.Deferred();
	
	$.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/setIsSetQuestionListOfCommonQuestion${strQueryString}`,
        method: METHOD_POST,
        success: function (strIsSet) {
			if (strIsSet === RtnInfo.FAILED.message) {
				alert(RtnInfo.FAILED.message);
				defer.reject();
			}
			else if (boolIsSet === false && strIsSet === "false") {
				SetElementCurrentStateSession(
                    currentSetCommonQuestionOnQuestionnaireState,
                    notSetState
                );
                defer.resolve();
			}
			else if (boolIsSet === true && strIsSet === "true") {
				SetElementCurrentStateSession(
                    currentSetCommonQuestionOnQuestionnaireState,
                    setState
                );
                defer.resolve();
			}
			else {
				alert(RtnInfo.FAILED.message);
				defer.reject();
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
 * 設置常用問題其問題列表於問卷上，藉由其問題種類Id。
 * @param String 常用問題其問題種類Id
 * @return Promise promise
 */
function SetQuestionListOfCommonQuestion(strSelectedCategoryId) {
    let objPostData = { [DataProperty.CATEGORY_ID.key]: strSelectedCategoryId };
    let defer = $.Deferred();
    
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/setQuestionListOfCommonQuestion`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrQuestionOfCommonQuestionResp) {
			let { status_code, 
				message, 
				question_session_list } = objArrQuestionOfCommonQuestionResp;
			
			if (status_code === RtnInfo.PARAMETER_REQUIRED.statusCode
                || message === RtnInfo.PARAMETER_REQUIRED.message) {
                alert(RtnInfo.PARAMETER_REQUIRED.message);
                defer.reject();
            }
			else if (status_code === RtnInfo.NOT_FOUND.statusCode
                || message === RtnInfo.NOT_FOUND.message) {
                alert(RtnInfo.NOT_FOUND.message);
                defer.reject();
            }
            else if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!question_session_list || !question_session_list.length) {
				alert(RtnInfo.FAILED.message);
				defer.reject();
			}
			else {
				$(btnDeleteQuestion).show();
				$(divQuestionListContainer).empty();
				
				ResetQuestionInputs(commonQuestionOfCategory);
                SetElementCurrentStateSession(
                    currentSetCommonQuestionOnQuestionnaireState,
                    setState
                );
				ShowQuestionListByIsDeleted(question_session_list, true);
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
 * 刪除常用問題其問題列表於問卷上。
 * @return Promise promise
 */
function DeleteSetQuestionListOfCommonQuestion() {
	let defer = $.Deferred();
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteSetQuestionListOfCommonQuestion`,
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
				ResetQuestionInputs();
	            $(btnDeleteQuestion).hide();
	            $(divQuestionListContainer).empty();
				$(divQuestionListContainer).append(emptyMessageOfList);
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
 * 創建使用者列表Html，藉由使用者陣列、資料總數和頁數。
 * @param Array 使用者陣列
 * @param Number 資料總數
 * @param Number 頁數
 */
function CreateUserList(objArrUser, intTotalRows, intPagerIndex) {
    $(divUserListContainer).append(
        `
            <table class="table table-bordered w-auto">
                <thead>
                    <tr>
                        <th scope="col">
                            #
                        </th>
                        <th scope="col">
                            姓名
                        </th>
                        <th scope="col">
                            填寫時間
                        </th>
                        <th scope="col">
                            觀看細節
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        `
    );

    for (let i = 0; i < objArrUser.length; i++) {
        $(divUserListContainer + " table tbody").append(
            `
                <tr>
                    <td>
                        ${intTotalRows - (intPagerIndex * PAGE_SIZE) - i}
                    </td>
                    <td>
                        ${objArrUser[i].userName}
                    </td>
                    <td>
                        ${FormatDate(objArrUser[i].answerDate)}
                    </td>
                    <td>
                        <a
                            id="aLinkUserAnswerDetail-${objArrUser[i].userId}"
                            href="#UserID=${objArrUser[i].userId}"
                        >
                            前往
                        </a>
                    </td>
                </tr>
            `
        );
    }
}
/**
 * 創建使用者列表Pager Html，藉由頁數。
 * @param Number 頁數
 */
function CreateUserListPager(intPageIndex) {
    $(divUserListPagerContainer).append(
        `
            <a th:onclick="javascript:void(0)"
            	 id="aLinkUserListPager-First" 
            	href="#Index=First"
            	class="d-block mx-1 external-link"
        	>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-double-left" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M8.354 1.646a.5.5 0 0 1 0 .708L2.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				  <path fill-rule="evenodd" d="M12.354 1.646a.5.5 0 0 1 0 .708L6.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
            <a th:onclick="javascript:void(0)"
            	id="aLinkUserListPager-Prev" 
            	href="#Index=Prev"
            	class="d-block mx-1 external-link"
        	>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
        `
    );

    for (let i = 1; i <= intPageIndex; i++) {
        $(divUserListPagerContainer).append(
            `
                <a th:onclick="javascript:void(0)"
                	id="aLinkUserListPager-${i}" 
                	class="d-block mx-2 external-link" 
                	href="#Index=${i}"
            	>
                    ${i}
                </a>
                <a class="d-block mx-2 fs-5">|</a>
            `
        );
    }


    $(divUserListPagerContainer).append(
        `
            <a th:onclick="javascript:void(0)"
            	id="aLinkUserListPager-Next" 
            	href="#Index=Next"
            	class="d-block mx-1 external-link"
        	>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
            <a th:onclick="javascript:void(0)"
            	id="aLinkUserListPager-Last" 
            	href="#Index=Last"
            	class="d-block mx-1 external-link"
        	>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-double-right" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708z"/>
				  <path fill-rule="evenodd" d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708z"/>
				</svg>
            </a>
        `
    );
}
/**
 * 取得使用者列表回應Body，藉由問卷Id。
 * @param String 問卷Id 
 */
function GetUserList(strQuestionnaireId) {
	let objPostData = { [DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId };
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getUserList`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrUserResp) {
			let { status_code, 
				message, 
				user_list, 
				total_rows, 
				page_index } = objArrUserResp;
			$(btnDownloadDataToCSVContainer).hide();
			$(divUserListContainer).show();
			$(divUserListContainer).empty();
			$(divUserListPagerContainer).show();
			$(divUserListPagerContainer).empty();
			
			if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divUserListContainer).append(emptyMessageOfUserListOrStatistics);
                SetContainerSession(divUserListContainer, currentUserList);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
            }
            else if (!user_list || !user_list.length) {
				$(divUserListContainer).append(emptyMessageOfUserListOrStatistics);
				SetContainerSession(divUserListContainer, currentUserList);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
			}
            else {
				$(btnDownloadDataToCSVContainer).show();
	
				CreateUserList(user_list, total_rows, page_index);
				CreateUserListPager(CountPageIndex(total_rows, PAGE_SIZE));
				
				SetContainerSession(divUserListContainer, currentUserList);
				SetContainerSession(divUserListPagerContainer, currentUserListPager);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        }
    });
}
/**
 * 更新使用者列表Html，藉由問卷Id和Index。
 * @param String 問卷Id 
 * @param String Index
 */
function UpdateUserList(strQuestionnaireId, strIndex) {
	let objPostData = { 
		[DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId, 
		[DataProperty.STR_INDEX.key]: strIndex 
		};
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/updateUserList`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        dataType: DATA_TYPE_JSON,
        data: JSON.stringify(objPostData),
        success: function (objArrUserResp) {
			let { status_code, 
				message, 
				user_list, 
				total_rows, 
				page_index } = objArrUserResp;
            $(divUserListContainer).show();
            $(divUserListContainer).empty();
            $(divUserListPagerContainer).empty();
            $(divUserListPagerContainer).hide();
            
			if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                $(divUserListContainer).append(emptyMessageOfUserListOrStatistics);
                SetContainerSession(divUserListContainer, currentUserList);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
            }
            else if (!user_list || !user_list.length) {
				$(divUserListContainer).append(emptyMessageOfUserListOrStatistics);
                SetContainerSession(divUserListContainer, currentUserList);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);

			}
            else {
				$(divUserListPagerContainer).show();
		
				CreateUserList(user_list, total_rows, page_index);
				CreateUserListPager(CountPageIndex(total_rows, PAGE_SIZE));
				
                SetContainerSession(divUserListContainer, currentUserList);
                SetContainerSession(divUserListPagerContainer, currentUserListPager);
                SetContainerShowStateSession(currentUserListShowState, showState);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        }
    });
}

/**
 * 創建使用者詳細Html，藉由使用者物件。
 * @param Object 使用者物件 
 */
function CreateUserDetail(objUser) {
    let userName = objUser.userName;
    let phone = objUser.phone;
    let email = objUser.email;
    let age = objUser.age;
    let answerDate = objUser.answerDate;

    $(divUserAnswerDetailContainer).append(
        `
            <div id="divUserAnswerInnerContainer" class="row gy-3">
                <div class="col-md-10">
                    <div class="row gy-3">
                        <div class="col-md-6">
                            <div class="row">
                                <label for="txtUserName" class="col-sm-2 col-form-label">姓名</label>
                                <div class="col-sm-10">
                                    <input id="txtUserName" class="form-control" value="${userName}" disabled />
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="row">
                                <label for="txtUserPhone" class="col-sm-2 col-form-label">手機</label>
                                <div class="col-sm-10">
                                    <input id="txtUserPhone" class="form-control" value="${phone}" disabled />
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="row">
                                <label for="txtUserEmail" class="col-sm-2 col-form-label">Email</label>
                                <div class="col-sm-10">
                                    <input id="txtUserEmail" class="form-control" value="${email}" disabled />
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <div class="row">
                                <label for="txtUserAge" class="col-sm-2 col-form-label">年齡</label>
                                <div class="col-sm-10">
                                    <input id="txtUserAge" class="form-control" value="${age}" disabled />
                                </div>
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="d-flex align-items-center justify-content-end">
                                填寫時間: ${FormatDate(answerDate)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `
    );
}
/**
 * 創建使用者回答詳細Html，藉由問題陣列和使用者回答陣列。
 * @param Array 問題陣列
 * @param Array 使用者回答陣列
 */
function CreateUserAnswerDetail(objArrQuestion, objArrUserAnswer) {
    $("#divUserAnswerInnerContainer").append(
        `
            <div class="col-md-10">
                <div id="divUserAnswerDetailInnerContainer"
                     class="row gy-3"
                >
                </div>
            </div>
        `
    );

    let i = 1;
    for (let question of objArrQuestion) {
        let questionId = question.questionId;
        let questionName = question.questionName;
        let questionRequired = question.questionRequired;
        let questionTyping = question.questionTyping;
        let arrQuestionAnswer = question.questionAnswer.split(";");

        let arrQuestionItsUserAnswer = objArrUserAnswer
            .filter(item => item.questionId === questionId);
        let arrUserAnswerNum = [];
        if (arrQuestionItsUserAnswer.length)
            arrUserAnswerNum = arrQuestionItsUserAnswer.map(item2 => item2.answerNum);
        else
            arrUserAnswerNum.push(-1);

        $("#divUserAnswerDetailInnerContainer").append(
            `
                <div id="${questionId}" class="col-12">
                    <div class="d-flex flex-column">
                        <h3>
                            ${i}. ${questionName} ${questionRequired ? "(必填)" : ""}
                        </h3>
                    </div>
                </div>
            `
        );

        for (let j = 0; j < arrQuestionAnswer.length; j++) {
            let anothorJ = j;
            let jPlus1 = anothorJ + 1;

            if (questionTyping === SINGLE_SELECT) {
                $(`#divUserAnswerDetailInnerContainer #${questionId} div.d-flex.flex-column`).append(
                    `
                        <div class="form-check">
                            <input id="rdoQuestionAnswer_${questionId}_${jPlus1}" class="form-check-input" type="radio" ${arrUserAnswerNum.indexOf(jPlus1) !== -1 ? "checked" : null} disabled />
                            <label class="form-check-label" for="rdoQuestionAnswer_${questionId}_${jPlus1}">
                                ${arrQuestionAnswer[j]}
                            </label>
                        </div>
                    `
                );
            }

            if (questionTyping == MULTIPLE_SELECT) {
                $(`#divUserAnswerDetailInnerContainer #${questionId} div.d-flex.flex-column`).append(
                    `
                        <div class="form-check">
                            <input id="ckbQuestionAnswer_${questionId}_${jPlus1}" class="form-check-input" type="checkbox" ${arrUserAnswerNum.indexOf(jPlus1) !== -1 ? "checked" : null} disabled />
                            <label class="form-check-label" for="ckbQuestionAnswer_${questionId}_${jPlus1}">
                                ${arrQuestionAnswer[j]}
                            </label>
                        </div>
                    `
                );
            }

            if (questionTyping == TEXT) {
                let isExitValue = arrUserAnswerNum.indexOf(jPlus1) === -1
                    ? false
                    : arrQuestionItsUserAnswer.filter(item => item.answerNum === jPlus1)[0].answer;

                $(`#divUserAnswerDetailInnerContainer #${questionId} div.d-flex.flex-column`).append(
                    `
                        <div class="row">
                            <label class="col-sm-2 col-form-label" for="txtQuestionAnswer_${questionId}_${jPlus1}">
                                ${arrQuestionAnswer[j]}
                            </label>
                            <div class="col-sm-10">
                                <input id="txtQuestionAnswer_${questionId}_${jPlus1}" class="form-control" type="text" value="${isExitValue === false ? "" : isExitValue}" disabled />
                            </div>
                        </div>
                    `
                );
            }
        }

        i++;
    }

    $("#divUserAnswerInnerContainer").append(
        `
            <div class="col-10">
                <div class="d-flex align-items-center justify-content-end">
                    <button id="btnBackToUserList" class="btn btn-success">返回</button>
                </div>
            </div>
        `
    );
}
/**
 * 取得使用者回答詳細回應Body，藉由問卷Id和使用者Id。
 * @param String 問卷Id 
 * @param String 使用者Id 
 */
function GetUserAnswerDetail(strQuestionnaireId, strUserId) {
	let objPostData = { 
		[DataProperty.QUESTIONNAIRE_ID.key]: strQuestionnaireId,
		[DataProperty.USER_ID.key]: strUserId 
		}
	
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/getUserAnswerDetail`,
        method: METHOD_POST,
        contentType: APPLICATION_JSON,
        data: JSON.stringify(objPostData),
        success: function (objUserAnswerDetailResp) {
			let { status_code, 
				message, 
				question_session_list, 
				user, 
				user_answer_list } = objUserAnswerDetailResp;
			$(btnDownloadDataToCSVContainer).hide();
			$(divUserListContainer).empty();
            $(divUserListContainer).hide();
            $(divUserListPagerContainer).empty();
            $(divUserListPagerContainer).hide();
			
			if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
            }
            else if (!question_session_list 
            	|| !question_session_list.length 
            	|| !user 
            	|| !user_answer_list 
            	|| !user_answer_list.length) {
				alert(RtnInfo.FAILED.message);
			}
			else {
				$(divUserAnswerDetailContainer).empty();
				
				CreateUserDetail(user);
                CreateUserAnswerDetail(question_session_list, user_answer_list);
                
                SetContainerSession(divUserAnswerDetailContainer, currentUserAnswerDetail);
                SetContainerShowStateSession(currentUserAnswerDetailShowState, showState);
                SetContainerShowStateSession(currentUserListShowState, hideState);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        }
    });
}
