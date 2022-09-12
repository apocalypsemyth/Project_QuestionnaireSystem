$(document).ready(function () {
    if (!IsTargetUrl(Url.ANSWERING_QUESTIONNAIRE_DETAIL.uri)) {
        
    }
    else {
        $(aLinkCheckingQuestionnaireDetail).click(function () {
            ResetUserInputsItsIsInvalidClass();
            ResetUserInputsItsValidMessage();

            let objUser = GetUserInputs();
            let isValidUserInputs = CheckUserInputs(objUser);
            if (!isValidUserInputs) {
                alert("填寫的個資有錯，請再檢查。");
                return;
            }

            let isValidRequiredQuestionInputs = CheckRequiredQuestionInputs();
            if (typeof isValidRequiredQuestionInputs === "string") {
                alert(isValidRequiredQuestionInputs);
                return;
            }

            let isValidAtLeastOneQuestionInputs = CheckAtLeastOneQuestionInputs();
            if (typeof isValidAtLeastOneQuestionInputs === "string") {
                alert(isValidAtLeastOneQuestionInputs);
                return;
            }
            
            let strQuestionnaireId = GetQueryParamOfQueryString("ID");
            objUser.questionnaire_id = strQuestionnaireId;
            $.when(CreateUser(objUser))
            .then(function () {
            	return $.when(CreateUserAnswer(isValidRequiredQuestionInputs, isValidAtLeastOneQuestionInputs));
			})
			.then(function () {
				PushStateOfUrl(Url.ANSWERING_QUESTIONNAIRE_DETAIL.uri + "?ID=" + strQuestionnaireId);
			})
			.then(function () {
				ReplaceUrl(Url.CHECKING_QUESTIONNAIRE_DETAIL.uri + "?ID=" + strQuestionnaireId);
			})
			.fail(function () {
				setTimeout(() => {
					ReplaceUrl(Url.QUESTIONNAIRE_LIST.uri);
				}, DELAY_TIME);
			});
        });
    }
});