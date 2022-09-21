/**
 * 取得QueryString的QueryParam值，藉由QueryParam健值。
 * @param String QueryParam健值
 * @return String QueryParam值
 */
const GetQueryParamOfQueryString = function (strQueryParamKey) {
    strQueryParamKey = strQueryParamKey + "=";

    let currentQueryString = window.location.search;
    let isExistQueryString = currentQueryString.indexOf(strQueryParamKey) !== -1;
    let strResult = isExistQueryString ? currentQueryString.split(strQueryParamKey)[1] : "";

    return strResult;
}
/**
 * 檢查目標字串是否為有內容字串，藉由目標字串。
 * @param String 目標字串
 * @return Boolean 目標字串是否為有內容字串
 */
const HasText = function (str) {
    return str != null
        && !/^\s*$/.test(str)
        && typeof str !== "number"
        && typeof str !== "boolean";
}
/**
 * 格式目標字串為yyyy/MM/dd，藉由目標字串。
 * @param String 目標字串
 * @return String 格式為yyyy/MM/dd字串
 */
const FormatDate = function (strDate) {
    if (!HasText(strDate)) return;

    let d = new Date(strDate);
    let month = d.getMonth() < 9 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
    let date = d.getDate() < 10 ? "0" + d.getDate() : d.getDate();
    let result = d.getFullYear() + "/" + month + "/" + date;

    return result;
}
/**
 * 格式目標字串為yyyy/MM/dd hh:mm:ss，藉由目標字串。
 * @param String 目標字串
 * @return String 格式為yyyy/MM/dd hh:mm:ss字串
 */
const FormatDateTime = function (strDate) {
	if (!HasText(strDate)) return;
	
	let d = new Date(strDate);
	let second = d.getSeconds();
	let minute = d.getMinutes();
	let hour = d.getHours();
	let resultTime = hour + ":" + minute + ":" + second;
	let resultDate = FormatDate(strDate);
	let result = resultDate + " " + resultTime;
	
	return result;
}
/**
 * 檢查目標字串是否為合法的日期，藉由目標字串。
 * @param String 目標字串
 * @return Boolean 目標字串是否為合法的日期
 */
const IsValidDate = function (strDate) {
  let isValidDate = !isNaN(Date.parse(strDate));
  if (!isValidDate) return false;
  if (Number(strDate.slice(0, 4)) < 100) return false;
  let date = new Date(strDate);
  let isValidYear = date.getFullYear() === Number(strDate.slice(0, 4));
  let isValidMonth = date.getMonth() + 1 === Number(strDate.slice(5, 7));
  let isValidDay = date.getDate() === Number(strDate.slice(8, strDate.length));
  return isValidYear && isValidMonth && isValidDay;
} 
/**
 * 檢查現在頁面Url是否包含目標字串，藉由目標字串。
 * @param String 目標字串
 * @return Boolean 現在頁面Url是否包含目標字串
 */
const IsTargetUrl = function (strUrl) {
	return window.location.href.indexOf(strUrl) !== -1;
}
/**
 * 替換且轉跳現在Url為目標Url，藉由目標Url。
 * @param String 目標Url
 */
const ReplaceUrl = function (strUrl) {
	window.location.replace(strUrl);
}
/**
 * 推移現在Url為目標Url後，更新History，藉由目標Url。
 * @param String 目標Url
 */
const PushStateOfUrl = function (strUrl) {
	window.history.pushState(null, null, strUrl);
}
/**
 * 替換現在Url為目標Url後 更新History，藉由目標Url。
 * @param String 目標Url
 */
const ReplaceStateOfUrl = function (strUrl) {
	window.history.replaceState(null, null, strUrl);
}
/**
 * 利用Html tag包裹訊息。
 * @param String 訊息
 * @param String Html opening tag
 */
const WrapMessageWithHtmlTag = function (strMessage, strHtmlOpeningTag = "<p>") {
	let strHtmlClosingTag = 
		strHtmlOpeningTag.slice(0, 1) 
		+ "/" 
		+ strHtmlOpeningTag.slice(1, strHtmlOpeningTag.length);
	let strHtmlOpeningTagAppendStyle = 
		strHtmlOpeningTag.slice(0, strHtmlOpeningTag.length - 1) 
		+ ` style="color: red;"` 
		+ strHtmlOpeningTag.slice(strHtmlOpeningTag.length - 1);
	return strHtmlOpeningTagAppendStyle + strMessage + strHtmlClosingTag;
}