/**
 * 取得常用問題其問題輸入控制項資料。
 * @return Object 常用問題其問題輸入控制項資料
 */
function GetQuestionOfCommonQuestionInputs() {
    let strCategoryName = $(selectCategoryList).find(":selected").text();
    let strTypingName = $(selectTypingList).find(":selected").text();
    let strQuestionNameOfCommonQuestion = $(txtQuestionNameOfCommonQuestion).val();
    let strQuestionAnswerOfCommonQuestion = $(txtQuestionAnswerOfCommonQuestion).val();
    let boolQuestionRequiredOfCommonQuestion =
        $(ckbQuestionRequiredOfCommonQuestion).is(":checked");

    let objQuestionOfCommonQuestion = {
        "question_category": strCategoryName,
        "question_typing": strTypingName,
        "question_name": strQuestionNameOfCommonQuestion,
        "question_answer": strQuestionAnswerOfCommonQuestion,
        "question_required": boolQuestionRequiredOfCommonQuestion,
    };

    return objQuestionOfCommonQuestion;
}
/**
 * 檢查常用問題其問題輸入控制項資料是否正確，藉由常用問題其問題輸入控制項資料。
 * @param Object 常用問題其問題輸入控制項資料
 * @return Boolean/Array 常用問題其問題輸入控制項資料是否正確/錯誤訊息陣列
 */
function CheckQuestionOfCommonQuestionInputs(objQuestionOfCommonQuestion) {
    let { question_name, question_answer } = objQuestionOfCommonQuestion;
    let arrErrorMsg = [];

    if (!question_name)
        arrErrorMsg.push("請填入問題名稱。");

    if (!question_answer)
        arrErrorMsg.push("請填入問題回答。");
    else {
        let hasSemicolon = question_answer.indexOf(";") !== -1;

        let strArrChecking =
            hasSemicolon
                ? question_answer.trim().split(";")
                : question_answer;

        if (Array.isArray(strArrChecking)) {
            let hasWhiteSpace = strArrChecking.some(item => /\s/.test(item));
            let hasStartOrEndWhiteSpace = strArrChecking.some(strChecking => !strChecking);

            if (hasWhiteSpace)
                arrErrorMsg.push("請不要留空於分號之間。");
            if (hasStartOrEndWhiteSpace)
                arrErrorMsg.push("請不要分號於開頭或結尾。");
        }
    }

    if (arrErrorMsg.length > 0)
        return arrErrorMsg.join("\n");
    else
        return true;
}
