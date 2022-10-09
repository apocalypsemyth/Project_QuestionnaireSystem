$(document).ready(function () {
    if (!IsTargetUrl(Url.QUESTIONNAIRE_LIST.uri)) {
        
    }
    else if (!IsTargetUrl(Url.BACK_ADMIN.uri) && IsTargetUrl(Url.QUESTIONNAIRE_LIST.uri)) {
		$(window).on("beforeunload", function () {
			DeleteIsUpdateModeSession();
			DeleteSessionOfUserAndUserAnswerList();
		});
		
		UcPagerProperty.EXECUTE_FUNC_WITH_UCPAGER = GetQuestionnaireList;
	    GetQuestionnaireList(window.location.search, false);
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
	
	        GetQuestionnaireList(strQueryString, false);
	    });
    }
});