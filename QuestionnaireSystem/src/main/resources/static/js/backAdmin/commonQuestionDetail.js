$(document).ready(function () {
    if (!IsTargetUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_DETAIL.uri)) {
        
    }
    else {
		//為了在按瀏覽器上下頁按鈕時，觸發以下Async方法
		$(window).on("unload", function () {
		});
        let strCommonQuestionId = GetQueryParamOfQueryString("ID");
		$.when(GetCategoryList(true), GetTypingList())
		.then(function () {
			return $.when(ResetQuestionOfCommonQuestionInputs());
		})
		.then(function () {
			return $.when(GetIsUpdateModeSession());
		})
		.then(function () {
			return $.when(ShowBtnDeleteQuestionByHasData(strCommonQuestionId, false));
		})
		.fail(function () {
			setTimeout(() => {
	            ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
			}, DELAY_TIME);
        });
		
        $(btnAddQuestionOfCommonQuestion).click(function (e) {
            e.preventDefault();

            let objCommonQuestion = GetCommonQuestionInputs();
            let isValidCommonQuestion = CheckCommonQuestionInputs(objCommonQuestion);
            if (typeof isValidCommonQuestion === "string") {
                alert(isValidCommonQuestion);
                return;
            }

            let objQuestionOfCommonQuestion = GetQuestionOfCommonQuestionInputs();
            let isValidQuestionOfCommonQuestion =
                CheckQuestionOfCommonQuestionInputs(objQuestionOfCommonQuestion);
            if (typeof isValidQuestionOfCommonQuestion === "string") {
                alert(isValidQuestionOfCommonQuestion);
                return;
            }
			
			let strCommonQuestionId = GetQueryParamOfQueryString("ID");
            if (strCommonQuestionId) {
                let strQuestionIdOfCommonQuestion = $(this).attr("href");

                if (strQuestionIdOfCommonQuestion) {
                    objQuestionOfCommonQuestion.question_id = strQuestionIdOfCommonQuestion;
                    $.when(UpdateCommonQuestion(objCommonQuestion))
                    .then(function () {
						return $.when(UpdateQuestion(objQuestionOfCommonQuestion, false));
					})
					.fail(function () {
						setTimeout(() => {
							ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
						}, DELAY_TIME);
                    });
                    $(this).removeAttr("href");
                }
                else {
                    $.when(UpdateCommonQuestion(objCommonQuestion))
                    .then(function () {
						return $.when(CreateQuestion(objQuestionOfCommonQuestion, false));
					})
					.fail(function () {
						setTimeout(() => {
							ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
						}, DELAY_TIME);
                    });
                }
            }
            else {
				$.when(CreateCommonQuestion(objCommonQuestion))
				.then(function () {
					return $.when(CreateQuestion(objQuestionOfCommonQuestion, false));
				})
				.fail(function () {
					setTimeout(() => {
						ReplaceUrl(Url.BACK_ADMIN.uri + Url.COMMON_QUESTION_LIST.uri);
					}, DELAY_TIME);
				})	
			}
        });
        $(btnDeleteQuestionOfCommonQuestion).click(function (e) {
            e.preventDefault();

            let arrCheckedQuestionIdOfCommonQuestion = [];
            $(divQuestionListOfCommonQuestionContainer + " input[id][type=checkbox]:checked").each(function () {
                arrCheckedQuestionIdOfCommonQuestion.push($(this).attr("id"));
            });
            if (!arrCheckedQuestionIdOfCommonQuestion.length) {
                alert("請選擇要刪除的問題。");
                return;
            }

            DeleteQuestionList(arrCheckedQuestionIdOfCommonQuestion, false);
        });

        $(document).on("click", aLinkUpdateQuestionOfCommonQuestion, function (e) {
            e.preventDefault();

            if (!GetQueryParamOfQueryString("ID")) {
                alert("請先新增後，再編輯");
                return;
            }

            let aLinkHref = $(this).attr("href");
            let strQuestionIdOfCommonQuestion = aLinkHref.split("#QuestionIDOfCommonQuestion=")[1];
            $(btnAddQuestionOfCommonQuestion).attr("href", strQuestionIdOfCommonQuestion);
            ShowToUpdateQuestion(strQuestionIdOfCommonQuestion, false);
        });
    }
});