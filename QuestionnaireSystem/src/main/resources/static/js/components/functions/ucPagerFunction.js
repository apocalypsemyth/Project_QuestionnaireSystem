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
        }, 3);
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

    $(divUcPagerContainer).append(
        `
            <a id="aLinkUcPager-First"
                class="text-decoration-none"
                href="?index=First"
            >
                首頁
            </a>
            <a id="aLinkUcPager-Prev"
                class="text-decoration-none"
                href="?index=Prev"
            >
                上一頁
            </a>
        `
    );

    for (let i = 1; i <= intPageIndex; i++) {
        $(divUcPagerContainer).append(
            `
                <a id="aLinkUcPager-${i}" 
                    class="text-decoration-none"
                    href="?index=${i}"
                >
                    ${i}
                </a>
            `
        );
    }

    $(divUcPagerContainer).append(
        `
            <a id="aLinkUcPager-Next"
                class="text-decoration-none"
                href="?index=Next"
            >
                下一頁
            </a>
            <a id="aLinkUcPager-Last"
                class="text-decoration-none"
                href="?index=Last"
            >
                末頁
            </a>
        `
    );
}
