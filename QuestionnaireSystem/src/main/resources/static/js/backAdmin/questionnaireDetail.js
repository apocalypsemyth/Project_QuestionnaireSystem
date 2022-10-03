$(document).ready(function () {
    if (!IsTargetUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_DETAIL.uri)) {
        sessionStorage.removeItem(activeTab);
        sessionStorage.removeItem(currentCommonQuestionOfCategoryId);
        sessionStorage.removeItem(currentUserList);
        sessionStorage.removeItem(currentUserListShowState);
        sessionStorage.removeItem(currentUserAnswerDetail);
        sessionStorage.removeItem(currentUserAnswerDetailShowState);
        sessionStorage.removeItem(currentUserListPager);
    }
    else {
		if (IsTargetUrl("#statistics")) {
			sessionStorage.setItem(activeTab, "#statistics");
			let strQuestionnaireId = GetQueryParamOfQueryString("ID");
			setTimeout(() => {
	            ReplaceStateOfUrl(`${Url.BACK_ADMIN.uri}${Url.QUESTIONNAIRE_DETAIL.uri}?ID=${strQuestionnaireId}`);
	        }, DELAY_TIME);
		}
		
        let currentActiveTab = sessionStorage.getItem(activeTab);
        // question-info userList
        let strUserListHtml = sessionStorage.getItem(currentUserList);
        let strUserListShowState = sessionStorage.getItem(currentUserListShowState);
        let strUserListPagerHtml = sessionStorage.getItem(currentUserListPager);
        if (strUserListHtml && currentActiveTab === "#question-info") {
            if (strUserListShowState === showState) {
                strUserListHtml === emptyMessageOfUserListOrStatistics
                    ? $(btnDownloadDataToCSVContainer).hide()
                    : $(btnDownloadDataToCSVContainer).show();
                $(divUserListContainer).show();
                $(divUserListContainer).html(strUserListHtml);
                $(divUserListPagerContainer).show();
                $(divUserListPagerContainer).html(strUserListPagerHtml);
            }
            else {
                $(btnDownloadDataToCSVContainer).hide();
                $(divUserListContainer).empty();
                $(divUserListContainer).hide();
                $(divUserListPagerContainer).empty();
                $(divUserListPagerContainer).hide();
            }
        }
        else if (strUserListHtml) {
            if (strUserListShowState === showState) {
                strUserListHtml === emptyMessageOfUserListOrStatistics
                    ? $(btnDownloadDataToCSVContainer).hide()
                    : $(btnDownloadDataToCSVContainer).show();
                $(divUserListContainer).show();
                $(divUserListContainer).html(strUserListHtml);
                $(divUserListPagerContainer).show();
                $(divUserListPagerContainer).html(strUserListPagerHtml);
            }
            else {
                $(btnDownloadDataToCSVContainer).hide();
                $(divUserListContainer).empty();
                $(divUserListContainer).hide();
                $(divUserListPagerContainer).empty();
                $(divUserListPagerContainer).hide();
            }
        }
        // question-info userAnswerDetail
        let strUserAnswerDetailHtml = sessionStorage.getItem(currentUserAnswerDetail);
        let strUserAnswerDetailHtmlShowState = sessionStorage.getItem(currentUserAnswerDetailShowState);
        if (strUserAnswerDetailHtml && currentActiveTab === "#question-info") {
            if (strUserAnswerDetailHtmlShowState === showState)
                $(divUserAnswerDetailContainer).html(strUserAnswerDetailHtml);
            else
                $(divUserAnswerDetailContainer).empty();
        }
        else if (strUserAnswerDetailHtml) {
            if (strUserAnswerDetailHtmlShowState === showState)
                $(divUserAnswerDetailContainer).html(strUserAnswerDetailHtml);
            else
                $(divUserAnswerDetailContainer).empty();
        }
        
        let strQuestionnaireId = GetQueryParamOfQueryString("ID");
        if (HasText(strQuestionnaireId)) {
			let hasStrUserListHtml = HasText(strUserListHtml);
            if (!hasStrUserListHtml)
                GetUserList(strQuestionnaireId);
            else if (hasStrUserListHtml && strUserListHtml === showState)
                GetUserList(strQuestionnaireId);
        }
        else {
            $(btnDownloadDataToCSVContainer).hide();
            $(divUserListContainer).show();
            $(divUserListContainer).html(emptyMessageOfUserListOrStatistics);
            $(divUserListPagerContainer).empty();
            $(divUserListPagerContainer).hide();
        }
        
        //為了在按瀏覽器上下頁按鈕時，觸發以下Async方法
        $(window).on("unload", function () {
		});
	    $.when(GetCategoryList(), GetTypingList())
        .then(function () {
			return $.when(ResetQuestionInputs());
		})
		.then(function () {
			return $.when(SetCommonQuestionOfCategoryId());
		})
		.then(function () {
			return $.when(HideCommonQuestionOfCategory());
		})
		.then(function () {
			return $.when(GetIsUpdateModeSession());
		})
		.then(function () {
			return $.when(ShowBtnDeleteQuestionByHasData(strQuestionnaireId, true));
		})
		.fail(function () {
			setTimeout(() => {
				ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
			}, DELAY_TIME);
        });
		
        $(ulQuestionnaireDetailTabs + " li a[data-bs-toggle='tab']").on("show.bs.tab", function () {
            sessionStorage.setItem(activeTab, $(this).attr("href"));
        });
        let strActiveTab = sessionStorage.getItem(activeTab);
        if (strActiveTab) {
            $(ulQuestionnaireDetailTabs + " a[href='" + strActiveTab + "']").tab("show");
		}
		
        $(selectCategoryList).change(function (e) {
            e.preventDefault();
			
			let strSelectedCategory = $(this).find(":selected");
    		let strSelectedCategoryText = strSelectedCategory.text();
    		let strSelectedCategoryId = strSelectedCategory.val();
        	if (strSelectedCategoryText === customizedQuestionOfCategory) {
				ResetQuestionInputs();
			}
			else {
				$.when(ShowToAddQuestionOfCommonQuestion(strSelectedCategoryId))
				.fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
					}, DELAY_TIME);
				});
			}
        });
        $(btnAddQuestion).click(function (e) {
            e.preventDefault();
            
            let objQuestionnaire = GetQuestionnaireInputs();
            let isValidQuestionnaire = CheckQuestionnaireInputs(objQuestionnaire);
            if (typeof isValidQuestionnaire === "string") {
                alert(isValidQuestionnaire);
                return;
            }

            let objQuestion = GetQuestionInputs();
            let isValidQuestion = CheckQuestionInputs(objQuestion);
            if (typeof isValidQuestion === "string") {
                alert(isValidQuestion);
                return;
            }
			
			let strQuestionnaireId = GetQueryParamOfQueryString("ID");
            if (HasText(strQuestionnaireId)) {
                let strQuestionId = $(this).attr("href");

                if (strQuestionId) {
                    objQuestion.question_id = strQuestionId;
                    $.when(UpdateQuestionnaire(objQuestionnaire))
                    .then(function () {
                        return $.when(UpdateQuestion(objQuestion, true));
                    })
                    .fail(function () {
						setTimeout(() => {
							ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
						}, DELAY_TIME);
                    });
                    $(this).removeAttr("href");
                }
                else {
                    $.when(UpdateQuestionnaire(objQuestionnaire))
                    .then(function () {
                        return $.when(CreateQuestion(objQuestion, true));
                    })
                    .fail(function () {
                        setTimeout(() => {
							ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
						}, DELAY_TIME);
                    });
                }
            }
            else {
                $.when(CreateQuestionnaire(objQuestionnaire))
                .then(function () {
                    return $.when(CreateQuestion(objQuestion, true));
                })
                .fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
					}, DELAY_TIME);
                });
            }
        });
        $(btnDeleteQuestion).click(function (e) {
            e.preventDefault();

            let arrCheckedQuestionId = [];
            $(divQuestionListContainer + " input[id][type=checkbox]:checked").each(function () {
                arrCheckedQuestionId.push($(this).attr("id"));
            });
            if (!arrCheckedQuestionId.length) {
                alert("請選擇要刪除的問題。");
                return;
            }
			
            DeleteQuestionList(arrCheckedQuestionId, true);
        });

        $(document).on("click", aLinkUpdateQuestion, function (e) {
            e.preventDefault();
	        
            if (!GetQueryParamOfQueryString("ID")) {
                alert("請先新增後，再編輯。");
                return;
            }
            
            let aLinkHref = $(this).attr("href");
            let strQuestionId = aLinkHref.split("#QuestionID=")[1];
            $(btnAddQuestion).attr("href", strQuestionId);
            ShowToUpdateQuestion(strQuestionId, true);
        });
		
		$(aLinkDownloadDataToCSV).attr("href", Url.DOWNLOAD_DATA_TO_CSV.uri);
        $(document).on("click", aLinkUserAnswerDetail, function (e) {
            e.preventDefault();

            let strQuestionnaireId = GetQueryParamOfQueryString("ID");
            let aLinkHref = $(this).attr("href");
            let strUserId = aLinkHref.split("#UserID=")[1];
            GetUserAnswerDetail(strQuestionnaireId, strUserId);
        });
        $(document).on("click", aLinkUserListPager, function (e) {
            e.preventDefault();
			
			let strQuestionnaireId = GetQueryParamOfQueryString("ID");
            let aLinkHref = $(this).attr("href");
            let strIndex = aLinkHref.split("#Index=")[1];
            UpdateUserList(strQuestionnaireId, strIndex);
        });
        $(document).on("click", btnBackToUserList, function (e) {
            e.preventDefault();

            $(divUserAnswerDetailContainer).empty();

            $(btnDownloadDataToCSVContainer).show();
            let strUserListHtml = sessionStorage.getItem(currentUserList);
            let strUserListPagerHtml = sessionStorage.getItem(currentUserListPager);
            $(divUserListContainer).show();
            $(divUserListContainer).html(strUserListHtml);
            $(divUserListPagerContainer).show();
            $(divUserListPagerContainer).html(strUserListPagerHtml);

            SetContainerShowStateSession(currentUserAnswerDetailShowState, hideState);
            SetContainerShowStateSession(currentUserListShowState, showState);
        });
    }
});