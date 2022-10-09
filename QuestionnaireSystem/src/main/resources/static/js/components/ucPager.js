$(document).ready(function () {
    $(window).on("popstate", function () {
        UcPagerProperty.EXECUTE_FUNC_WITH_UCPAGER(document.location.search);
    });

    $(document).on("click", aLinkUcPager, function (e) {
        e.preventDefault();
        
		let isBackAdmin = IsTargetUrl(Url.BACK_ADMIN.uri);
        let aLinkHref = $(this).attr("href");
        let strPageIndex = aLinkHref.split("?index=")[1];
        let strResult = HandleUcPagerEdge(strPageIndex);
        UcPagerProperty.EXECUTE_FUNC_WITH_UCPAGER(strResult, isBackAdmin);
    });
});