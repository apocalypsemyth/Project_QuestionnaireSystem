$(document).ready(function () {
	if (!IsTargetUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri)) {
		
	}
	else {
		$(window).on("beforeunload", function () {
			DeleteCommonQuestionSession();
		});
		
		$(window).on("popstate", function () {
			GetCommonQuestionList(window.location.search);
		});
		
		executeFuncWithUcPager = GetCommonQuestionList;
	    GetCommonQuestionList(window.location.search);
	    $(btnSearchCommonQuestionList).click(function (e) {
	        e.preventDefault();
	        
	        let objSearchCommonQuestion = GetSearchCommonQuestionInputs();
	        let isValidSearchCommonQuestion = CheckSearchCommonQuestionInputs(objSearchCommonQuestion);
	        if (typeof isValidSearchCommonQuestion === "string") {
	            alert(isValidSearchCommonQuestion);
	            return;
	        }
	
	        let { keyword } = objSearchCommonQuestion;
	        let strQueryString = CreateQueryStringForUcPager(
	            "index",
	            keyword,
	            "",
	            ""
	        );
	
	        GetCommonQuestionList(strQueryString);
	    });
	
	    $(btnDeleteCommonQuestionList).click(function (e) {
	        e.preventDefault();
	
	        let arrCheckedCommonQuestionId = [];
	        $(divCommonQuestionListContainer + " input[type=checkbox]:checked").each(function () {
	            arrCheckedCommonQuestionId.push($(this).attr("id"));
	        });
	        if (!arrCheckedCommonQuestionId.length) {
	            alert("請選擇要刪除的常用問題。");
	            return;
	        }
	
	        let strQueryString = CreateQueryStringForUcPager(
	            "index",
	            GetQueryParamVal("keyword"),
	            GetQueryParamVal("startDate"),
	            GetQueryParamVal("endDate")
	        );
	
	        DeleteCommonQuestionList(arrCheckedCommonQuestionId, strQueryString);
	    });
	
	    $(aLinkCreateCommonQuestion).attr("href", Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_DETAIL.uri);
	}
});
