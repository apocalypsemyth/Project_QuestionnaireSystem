$(document).ready(function () {
	if (!IsTargetUrl(Url.QUESTIONNAIRE_STATISTICS.uri)) {
		
	}
	else {
		let strQuestionnaireId = GetQueryParamOfQueryString("ID");
		GetQuestionnaireStatistics(strQuestionnaireId);
	}
});