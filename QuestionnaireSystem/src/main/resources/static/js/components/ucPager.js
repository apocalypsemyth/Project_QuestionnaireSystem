$(document).ready(function () {
    window.onpopstate = function () {
        executeFuncWithUcPager(document.location.search);
    }

    $(document).on("click", aLinkUcPager, function (e) {
        e.preventDefault();
		
		let isBackAdmin = IsTargetUrl(Url.BACK_ADMIN.uri);
        let aLinkHref = $(this).attr("href");
        let strPageIndex = aLinkHref.split("?index=")[1];
        let strResult = HandleUcPagerEdge(strPageIndex);
        executeFuncWithUcPager(strResult, isBackAdmin);
    });
});