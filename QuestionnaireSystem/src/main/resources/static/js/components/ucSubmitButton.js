$(document).ready(function () {
	$(BtnUcSubmit()).click(function (e) {
        e.preventDefault();
        
        if (IsTargetUrl(Url.BACK_ADMIN.uri)) {
	        if (IsTargetUrl(Url.QUESTIONNAIRE_DETAIL.uri)) {
		        let objQuestionnaire = GetQuestionnaireInputs();
		        let isValidQuestionnaire = CheckQuestionnaireInputs(objQuestionnaire);
		        if (typeof isValidQuestionnaire === "string") {
		            alert(isValidQuestionnaire);
		            return;
		        }
		        let resultOperateQuestionnaire = 
		        	HasText(GetQueryParamOfQueryString("ID")) 
			        	? UpdateQuestionnaire 
			        	: CreateQuestionnaire; 
				$.when(HasQuestionSession())
				.then(function () {
                	return $.when(resultOperateQuestionnaire(objQuestionnaire));
		        })
		        .then(function () {
					return $.when(SaveQuestionnaire(objQuestionnaire));
				})
				.then(function () {
					ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
				})
		        .fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.BACK_ADMIN.uri + Url.QUESTIONNAIRE_LIST.uri);
					}, DELAY_TIME);
				});
	        }
	        else if (IsTargetUrl(Url.COMMON_QUESTION_DETAIL.uri)) {
				let objCommonQuestion = GetCommonQuestionInputs();
		        let isValidCommonQuestion = CheckCommonQuestionInputs(objCommonQuestion);
		        if (typeof isValidCommonQuestion === "string") {
		            alert(isValidCommonQuestion);
		            return;
		        }
		        let resultOperateCommonQuestion = 
		        	HasText(GetQueryParamOfQueryString("ID")) 
			        	? UpdateCommonQuestion 
			        	: CreateCommonQuestion;
		        $.when(HasQuestionSession())
		        .then(function () {
			        return $.when(WhetherNameOfCommonQuestionIsCustomizedQuestion(objCommonQuestion));
				})
		        .then(function () {
	                return $.when(resultOperateCommonQuestion(objCommonQuestion));
		        })
		        .then(function () {
					return $.when(SaveCommonQuestion(objCommonQuestion));
				})
				.then(function () {
					ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
				})
		        .fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
					}, DELAY_TIME);
				});
			}
		}
		else {
			if (IsTargetUrl(Url.CHECKING_QUESTIONNAIRE_DETAIL.uri)) {
				$.when(SaveUserAndUserAnswerList())
				.then(function () {
					ReplaceUrl(Url.QUESTIONNAIRE_LIST.uri);
				})
				.fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.QUESTIONNAIRE_LIST.uri);
					}, DELAY_TIME);
				});
			}
		}
	});
});