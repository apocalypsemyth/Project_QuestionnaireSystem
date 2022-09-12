$(document).ready(function () {
    $(BtnUcCancel()).click(function (e) {
        e.preventDefault();
        
        if (IsTargetUrl(Url.BACK_ADMIN.uri)) {
			if (IsTargetUrl(Url.QUESTIONNAIRE_DETAIL.uri)) {
	        	ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
			}
	    	else if (IsTargetUrl(Url.COMMON_QUESTION_DETAIL.uri)) {
	    		ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
			}
		}
		else {
			if (IsTargetUrl(Url.ANSWERING_QUESTIONNAIRE_DETAIL.uri)) {
	        	ReplaceUrl(Url.QUESTIONNAIRE_LIST.uri);
			}
		}
    });
});