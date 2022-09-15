/**
 * 取得目標QueryParam值於多個QueryParam，藉由QueryParam健值。
 * @param String QueryParam健值
 * @return String 目標QueryParam值
 */
const GetQueryParamVal = function (strQueryParamKey) {
    let urlParams = new URLSearchParams(window.location.search);

    return urlParams.has(strQueryParamKey)
        ? urlParams.get(strQueryParamKey)
        : "";
}
/**
 * 處理ucPager的首頁、上一頁、下一頁和末頁之控制項觸發問題。
 * @param String 頁數
 * @return String index(QueryParam)值
 */
const HandleUcPagerEdge = function (strPageIndex) {
    let strResultPageIndex = "";
    let intTempPageIndex = 0;

    if (strPageIndex === "First")
        strResultPageIndex = "1";
    else if (strPageIndex === "Prev") {
        intTempPageIndex = intPageIndex - 1;
        strResultPageIndex =
            intTempPageIndex <= 0
                ? "1"
                : intTempPageIndex.toString();
    }
    else if (strPageIndex === "Next") {
        intTempPageIndex = intPageIndex + 1;
        strResultPageIndex =
            intPageIndex < intTempPageIndex
                ? intPageIndex.toString()
                : intTempPageIndex;
    }
    else if (strPageIndex === "Last") {
        strResultPageIndex = intPageIndex;
    }
    else {
        strResultPageIndex = strPageIndex;
    }

    return GetQueryParamVal("index") === ""
        ? `?index=${strResultPageIndex}`
        : window.location.search.replace(/index=\d+/i, `index=${strResultPageIndex}`);
}
/**
 * 創建為了ucPager之QueryString。
 * @param String 頁數
 * @param Array QueryParam陣列
 * @return String 為了ucPager之QueryString
 */
const CreateQueryStringForUcPager = function (strPageIndex, ...arrQueryParamVal) {
    arrQueryParamVal.push(GetQueryParamVal(strPageIndex) === "" ? "" : "1");
    let arrQueryParam = [];

    for (let i = 0; i < arrQueryParamVal.length; i++) {
        if (arrQueryParamVal[i].trim() === "" || arrQueryParamVal[i] == null) continue;

        arrQueryParam.push(`${arrQueryParamKey[i]}=${arrQueryParamVal[i]}`)
    }

    return arrQueryParam.join("&") === ""
        ? ""
        : `?${arrQueryParam.join("&")}`;
}
/**
 * 創建使用ucPager時之歷史紀錄，藉由QueryString。
 * @param String QueryString
 */
const CreateHistory = function (strQueryString) {
    if (window.location.search.trim() === "") {
        setTimeout(() => {
            window.history.replaceState(null, null, "?index=1");
        }, DELAY_TIME);
    }
    else if (window.location.search !== strQueryString)
        window.history.pushState(null, null, strQueryString);
}
/**
 * 計算ucPager頁數。
 * @param Number 資料總數
 * @param String 每頁大小
 */
const CountPageIndex = function (intTotalRows, strPageSize) {
    let pageIndex = 1;

    if (intTotalRows < strPageSize)
        pageIndex = 1;
    else if ((intTotalRows % strPageSize) == 0)
        pageIndex = intTotalRows / strPageSize;
    else
        pageIndex = Number(Math.floor(intTotalRows / strPageSize)) + 1;

    return pageIndex;
}
/**
 * 創建ucPager Html，藉由資料總數、每頁大小和QueryString。
 * @param Number 資料總數
 * @param String 每頁大小
 * @param String QueryString
 */
const CreateUcPager = function (intTotalRows, strPageSize, strQueryString) {
    intPageIndex = CountPageIndex(intTotalRows, strPageSize);
    CreateHistory(strQueryString);
    
    $(divUcPagerContainer).attr("class", "d-flex align-items-center");
    $(divUcPagerContainer).append(
        `
            <a id="aLinkUcPager-First"
                href="?index=First"
                class="d-block mx-1 external-link"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-double-left" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M8.354 1.646a.5.5 0 0 1 0 .708L2.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				  <path fill-rule="evenodd" d="M12.354 1.646a.5.5 0 0 1 0 .708L6.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
            <a id="aLinkUcPager-Prev"
                href="?index=Prev"
                class="d-block mx-1 external-link"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
        `
    );

    for (let i = 1; i <= intPageIndex; i++) {
        $(divUcPagerContainer).append(
            `
                <a id="aLinkUcPager-${i}" 
                    class="d-block mx-2 external-link"
                    href="?index=${i}"
                >
                    ${i}
                </a>
                <a class="d-block mx-2 fs-5">|</a>
            `
        );
    }

    $(divUcPagerContainer).append(
        `
            <a id="aLinkUcPager-Next"
                href="?index=Next"
                class="d-block mx-1 external-link"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
				</svg>
            </a>
            <a class="d-block mx-2 fs-5">|</a>
            <a id="aLinkUcPager-Last"
                href="?index=Last"
                class="d-block mx-1 external-link"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-double-right" viewBox="0 0 16 16">
				  <path fill-rule="evenodd" d="M3.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L9.293 8 3.646 2.354a.5.5 0 0 1 0-.708z"/>
				  <path fill-rule="evenodd" d="M7.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L13.293 8 7.646 2.354a.5.5 0 0 1 0-.708z"/>
				</svg>
            </a>
        `
    );
}
