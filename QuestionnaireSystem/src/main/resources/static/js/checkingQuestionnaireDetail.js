$(document).ready(function () {
	$(BtnUpdate()).click(function (e) {
		e.preventDefault();
		
		if (IsTargetUrl(Url.CHECKING_QUESTIONNAIRE_DETAIL.uri)) {
			window.history.back();
		}
	});
});