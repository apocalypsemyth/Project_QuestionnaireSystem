/**
 * 重置使用者輸入控制項之錯誤提示樣式。
 */
function ResetUserInputsItsIsInvalidClass() {
    $(txtUserName).removeClass(isInvalidClass);
    $(txtUserPhone).removeClass(isInvalidClass);
    $(txtUserEmail).removeClass(isInvalidClass);
    $(txtUserAge).removeClass(isInvalidClass);
}
/**
 * 重置使用者輸入控制項之錯誤提示訊息。
 */
function ResetUserInputsItsValidMessage() {
    $(divValidateUserName).text("");
    $(divValidateUserPhone).text("");
    $(divValidateUserEmail).text("");
    $(divValidateUserAge).text("");
}
/**
 * 設置使用者輸入控制項之錯誤提示樣式，藉由其控制項、div控制項和其錯誤訊息。
 * @param String 輸入控制項
 * @param String div控制項
 * @param String div控制項之錯誤訊息
 */
function SetUserInputsItsIsInvalidClass(strInputSelector, strDivSelector, strDivErrMsg) {
    $(strInputSelector).addClass(isInvalidClass);
    $(strDivSelector).text(strDivErrMsg);
}
/**
 * 取得使用者輸入資料。
 * @return Object 使用者輸入資料
 */
function GetUserInputs() {
    let strUserName = $(txtUserName).val();
    let strUserPhone = $(txtUserPhone).val();
    let strUserEmail = $(txtUserEmail).val();
    let strUserAge = $(txtUserAge).val();

    let objUser = {
        "user_name": strUserName,
        "phone": strUserPhone,
        "email": strUserEmail,
        "age": strUserAge,
    };

    return objUser;
}
/**
 * 檢查使用者輸入資料是否正確，藉由使用者輸入資料。
 * @return Boolean 使用者輸入資料是否正確
 */
function CheckUserInputs(objUser) {
	let { user_name, phone, email, age } = objUser;
    let resultChecked = true;
    let phoneRx = /^[0]{1}[0-9]{9}$/;
    let emailRx = /^(([^<>()[\]\\.,;:\s@\""]+(\.[^<>()[\]\\.,;:\s@\""]+)*)|(\"".+\""))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (!user_name) {
        resultChecked = false;
        SetUserInputsItsIsInvalidClass(
            txtUserName,
            divValidateUserName,
            "請填入您的姓名。"
        );
    }

    if (!phone) {
        resultChecked = false;
        SetUserInputsItsIsInvalidClass(
            txtUserPhone,
            divValidateUserPhone,
            "請填入您的手機。"
        );
    }
    else
    {
        if (!phoneRx.test(phone) || phone.length > 10) {
            resultChecked = false;
            SetUserInputsItsIsInvalidClass(
                txtUserPhone,
                divValidateUserPhone,
                `請以 "0123456789" 開頭零後九碼的格式填寫。`
            );
        }
    }

    if (!email)
    {
        resultChecked = false;
        SetUserInputsItsIsInvalidClass(
            txtUserEmail,
            divValidateUserEmail,
            "請填入您的信箱。"
        );
    }
    else
    {
        if (!emailRx.test(email)) {
            resultChecked = false;
            SetUserInputsItsIsInvalidClass(
                txtUserEmail,
                divValidateUserEmail,
                "請填入合法的信箱格式。"
            );
        }
    }

    if (!age)
    {
        resultChecked = false;
        SetUserInputsItsIsInvalidClass(
            txtUserAge,
            divValidateUserAge,
            "請填入您的年齡。"
        );
    }
    else
    {
        if (isNaN(age)) {
            resultChecked = false;
            SetUserInputsItsIsInvalidClass(
                txtUserAge,
                divValidateUserAge,
                "請填寫數字。"
            );
        }
        else if (age <= 0) {
            resultChecked = false;
            SetUserInputsItsIsInvalidClass(
                txtUserAge,
                divValidateUserAge,
                "請填寫大於零的年齡。"
            );
        }
        else if (age > 150) {
            resultChecked = false;
            SetUserInputsItsIsInvalidClass(
                txtUserAge,
                divValidateUserAge,
                "請填寫小於150的年齡。"
            );
        }
    }

    return resultChecked;
}
/**
 * 創建使用者，藉由使用者輸入資料。
 * @param Object 使用者輸入資料
 * @return Promise promise
 */
function CreateUser(objUser) {
	let defer = $.Deferred();
	
	$.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/createUser`,
        method: METHOD_POST,
		contentType: APPLICATION_JSON,
		dataType: DATA_TYPE_JSON,
		data: JSON.stringify(objUser),
		success: function (objUserResp) {
			let { status_code, message, user } = objUserResp;
			
			if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!user) {
				alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
				defer.resolve();
			}
		},
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        }
    });
    
    return defer.promise();
}

/**
 * 取得文字問題輸入資料，藉由其控制項。
 * @return Array 文字問題輸入資料陣列
 */
function GetAnsweredTextInputs(strInputSelector) {
    let textEl = $(strInputSelector);
    let arrAnsweredText = [];

    textEl.each(function () {
        let answeredText = $(this).val();
        if (answeredText)
            arrAnsweredText.push(answeredText);
    });

    return arrAnsweredText;
}
/**
 * 檢查必填問題是否填寫。
 * @return Boolean/Array 必填問題是否填寫/錯誤訊息陣列
 */
function CheckRequiredQuestionInputs() {
    let resultChecked = true;
    let arrResult = [];

    if ($(rdoQuestionAnswer + "[required=required]").length
        && !$(rdoQuestionAnswer + "[required=required]:checked").length) {
        resultChecked = false;
        arrResult.push("請勾選必填單選方塊。");
    }

    if ($(ckbQuestionAnswer + "[required=required]").length
        && !$(ckbQuestionAnswer + "[required=required]:checked").length) {
        resultChecked = false;
        arrResult.push("請勾選必填複選方塊。");
    }

    let requiredText = $(txtQuestionAnswer + "[required=required]");
    let arrAnsweredRequiredText = GetAnsweredTextInputs(txtQuestionAnswer + "[required=required]");
    if (requiredText.length !== arrAnsweredRequiredText.length) {
        resultChecked = false;
        arrResult.push("請填寫必填文字。");
    }

    if (arrResult.length)
        return arrResult.join("\n");
    else
        return resultChecked;
}
/**
 * 取得使用者回答之輸入控制項資料陣列，藉由其控制項和問題類別。
 * @param String 輸入控制項
 * @param String 問題類別
 * @return Array 使用者回答之輸入控制項資料陣列
 */
function GetUserAnswerInputs(strQuestionTypingItsControl, strQuestionTyping) {
    let arrResult = [];

    $(strQuestionTypingItsControl).each(function () {
        let arrQuestionId_AnswerNum_Answer_QuestionTyping = $(this).attr("id").split("_").slice(1);
        let strAnswer = $(this).val();
        if (strAnswer) {
            arrQuestionId_AnswerNum_Answer_QuestionTyping.push(strAnswer, strQuestionTyping);
            arrResult.push(arrQuestionId_AnswerNum_Answer_QuestionTyping.join("_"));
        }
    });

    return arrResult;
}
/**
 * 檢查使用者是否至少回答一項問題。
 * @return String/Array 錯誤訊息/使用者回答之輸入控制項資料陣列
 */
function CheckAtLeastOneQuestionInputs() {
    let arrResult = [];

    if ($(rdoQuestionAnswer).length) {
        arrResult.push(
            GetUserAnswerInputs(
                rdoQuestionAnswer + ":checked"
                , SINGLE_SELECT)
        );
    }

    if ($(ckbQuestionAnswer).length) {
        arrResult.push(
            GetUserAnswerInputs(
                ckbQuestionAnswer + ":checked"
                , MULTIPLE_SELECT)
        );
    }

    let arrAnsweredText = GetAnsweredTextInputs(txtQuestionAnswer);
    if (arrAnsweredText.length) {
        arrResult.push(
            GetUserAnswerInputs(
                txtQuestionAnswer
                , TEXT)
        );
    }

    let arrUserAnswer = $.map(arrResult, function (n) {
        return n;
    })

    if (!arrUserAnswer.length)
        return "請至少作答一個問題。";
    else
        return arrUserAnswer;
}
/**
 * 創建使用者回答，藉由必填和非必填問題輸入資料陣列。
 * @param Array 必填問題輸入資料陣列 
 * @param Array 非必填問題輸入資料陣列 
 * @return Promise promise 
 */
function CreateUserAnswer(arrRequiredQuestionInputs, arrAtLeastOneQuestionInputs) {
	let arrResult = [];
    if (arrRequiredQuestionInputs 
    	&& arrRequiredQuestionInputs.length 
    	&& arrAtLeastOneQuestionInputs 
    	&& arrAtLeastOneQuestionInputs.length) {
		arrResult = arrRequiredQuestionInputs.concat(arrAtLeastOneQuestionInputs);
	}
	else if (arrRequiredQuestionInputs 
    	&& arrRequiredQuestionInputs.length) {
		arrResult = arrRequiredQuestionInputs;
	}
	else if (arrAtLeastOneQuestionInputs 
    	&& arrAtLeastOneQuestionInputs.length) {
		arrResult = arrAtLeastOneQuestionInputs;
	}
	let objPostData = { [DataProperty.USER_ANSWER_STRING_LIST.key]: arrResult };
	let defer = $.Deferred();
	
	$.ajax({
        url: `${JAVA_SERVICE_DOMAIN}/createUserAnswer`,
        method: METHOD_POST,
		contentType: APPLICATION_JSON,
		dataType: DATA_TYPE_JSON,
		data: JSON.stringify(objPostData),
		success: function (objArrUserAnswerResp) {
			let { status_code, message, user_answer_list } = objArrUserAnswerResp;

			if (status_code === RtnInfo.FAILED.statusCode
                || message === RtnInfo.FAILED.message) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else if (!user_answer_list || !user_answer_list.length) {
                alert(RtnInfo.FAILED.message);
                defer.reject();
            }
            else {
				defer.resolve();
			}
		},
        error: function (msg) {
            console.log(msg);
            alert(errorMessageOfAjax);
            defer.reject();
        }
    });
    
    return defer.promise();
}
