$(document).ready(function () {
	if (!IsTargetUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri)) {
		
	}
	else {
		$(window).on("beforeunload", function () {
			DeleteIsUpdateModeSession();
			DeleteQuestionnaireSession();
			DeleteSessionOfListOfUserAndUserAnswer();
			sessionStorage.removeItem(activeTab);
			sessionStorage.removeItem(currentSetCommonQuestionOnQuestionnaireState);
        	sessionStorage.removeItem(currentCommonQuestionOfCategoryId);
	        sessionStorage.removeItem(currentUserList);
	        sessionStorage.removeItem(currentUserListShowState);
	        sessionStorage.removeItem(currentUserAnswerDetail);
	        sessionStorage.removeItem(currentUserAnswerDetailShowState);
	        sessionStorage.removeItem(currentUserListPager);
		});
		
		$(window).on("popstate", function () {
			GetQuestionnaireList(window.location.search, true);
		});
		
		executeFuncWithUcPager = GetQuestionnaireList;
    	GetQuestionnaireList(window.location.search, true);
	    $(btnSearchQuestionnaireList).click(function (e) {
	        e.preventDefault();
	        
	        let objSearchQuestionnaire = GetSearchQuestionnaireInputs();
	        let isValidSearchQuestionnaire = CheckSearchQuestionnaireInputs(objSearchQuestionnaire);
	        if (typeof isValidSearchQuestionnaire === "string") {
	            alert(isValidSearchQuestionnaire);
	            return;
	        }
	
	        let { keyword, startDate, endDate } = objSearchQuestionnaire;
	        let strQueryString = CreateQueryStringForUcPager(
	            "index",
	            keyword,
	            startDate,
	            endDate
	        );
	
	        GetQuestionnaireList(strQueryString, true);
	    });
	
	    $(btnDeleteQuestionnaireList).click(function (e) {
	        e.preventDefault();
	
	        let arrCheckedQuestionnaireId = [];
	        $(divQuestionnaireListContainer + " input[type=checkbox]:checked").each(function () {
	            arrCheckedQuestionnaireId.push($(this).attr("id"));
	        });
	        if (!arrCheckedQuestionnaireId.length) {
	            alert("請選擇要刪除的問卷。");
	            return;
	        }
	
	        let strQueryString = CreateQueryStringForUcPager(
	            "index",
	            GetQueryParamVal("keyword"),
	            GetQueryParamVal("startDate"),
	            GetQueryParamVal("endDate")
	        );
	        
	        DeleteQuestionnaireList(arrCheckedQuestionnaireId, strQueryString);
	    });
		
	    $(aLinkCreateQuestionnaire).attr("href", Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_DETAIL.uri);
	}
});
