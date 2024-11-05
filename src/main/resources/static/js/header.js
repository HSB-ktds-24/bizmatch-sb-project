function pagination(callbackFunction) {
  $(".pagenation-ajax")
    .find("a")
    .on("click", function () {
      var orderBy = $("#orderBy").val();
      var url = $(this).data("url");
      var pageNo = $(this).data("page-no");
      var listSize = $(this).data("exposure-list-size");

      console.log("Page URL:", url);
      console.log("Page Number:", pageNo);
      console.log("List Size:", listSize);

      callList(url, { orderBy }, pageNo, listSize, callbackFunction);
    });
}

function callList(url, param, pageNo, exposureListSize, callbackFunction) {
  var paramObject = {};

  if (location.search) {
    var params = location.search.replace("?", "");
    var paramArray = params.split("&");
    for (var p of paramArray) {
      var keyValue = p.split("=");
      paramObject[keyValue[0]] = decodeURI(keyValue[1]);
    }
  }

  paramObject.currPageNo = pageNo;
  paramObject.exposureListSize = exposureListSize;
  paramObject = { ...paramObject, ...param };

  var option = {
    url,
    dataType: "json",
    data: paramObject,
    success: function (response) {
      callbackFunction(response);
    },
    error: function (status, error) {
      console.error("AJAX Error: ", status, error);
    },
  };

  $.get(option);
}

$().ready(function () {
  $("#main-logo").on("click", function () {
    location.href = "/";
  });

  $(document).on("click", "#pjttl ", function () {
	var pjid = $(".project-title").data("pjid");
   	console.log(pjid);
   	location.href = "/project/info/"+pjid;
   	});

	$(document).on("click", "#applyBtn", function () {
	var pjid = $(".project-title").data("pjid");
	  	console.log(pjid);
	  	location.href = "/project/apply/" + pjid;
	});
		
  // 이게 아이콘 클릭 이벤트임.
  var membertype = $(".notification-mypage-list").data("membertype");
  var rel = null;
  console.log($("#sessionA").data("email"));
  console.log(membertype);
  if (membertype == 0) {
    // 0이면 기업회원이다.
    rel = "company/" + $("#sessionA").data("cmpid");
  } else if (membertype == 1) {
    // 1이면 프리랜서다.
    rel = "freelancer/" + $("#sessionA").data("email") + "/";
  }
  var id = $(".notification-mypage-list").data("id");

  $(".my-profile").on("click", function () {
    console.log("/member/mypage/" + rel);
    location.href = "/member/mypage/" + rel;
  });
  
  $(".my-info").on("click", function () {
    location.href = "/member/mypage/myinfo-edit";
  });
  
  $(".my-project").on("click", function () {
    location.href = "/project/company/all/order";
  });
  
  $(".log-out").on("click", function () {
    location.href = "/member/logout";
  });
 
});
