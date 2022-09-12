/**
 * 刪除使用者和使用者回答列表於Session。
 */
function DeleteSessionOfUserAndUserAnswerList() {
    $.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/deleteSessionOfUserAndUserAnswerList`,
        method: METHOD_GET,
        success: function (strIsDeleted) {
			if (strIsDeleted === "false") {
				alert(RtnInfo.FAILED.message);
			}
        },
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
        },
    });
}