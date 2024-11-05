$().ready(function () {
  // 도로명 주소 찾을 때 사용
  $("#asd").on("click", function () {
    sample6_execDaumPostcode();
  });

  $("#save-btn").on("click", function () {
    var address =
      $("#addr").val() +
      " " +
      $("#detailAddress").val() +
      " " +
      $("#extraAddress").val();
    $(".detail-address").text(address);
    $("#reportModal").css("display", "none");
  });

  $("#mypageeditbutton").on("click", function () {
    var cmpid = $("#sessionA").data("cmpid");
    console.log(cmpid);
    var cmpnm = $("#cmpnyNm").val();

    var cmpurl = $("#cmpnyUrl").val();

    var cmpaddr = $("#cmpnyAddr").text();
    var cmpintr = $("#cmpnyIntr").val();

    console.log(cmpintr);

    $.ajax({
      url: `/member/mypage/company/edit`,
      method: "POST",
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify({
        cmpnyId: cmpid,
        cmpnyNm: cmpnm,
        cmpnySiteUrl: cmpurl,
        cmpnyAddr: cmpaddr,
        cmpnyIntr: cmpintr,
      }),
      success: function (response) {
        console.log("asdf");
		location.href=`/member/mypage/company/${cmpid}`;
      },
      error: function (jqXHR, textStatus, errorThrown) {
        // 요청이 실패했을 때 실행될 코드
      },
    });
  });
  // 모달창 부분 - .report 요소가 페이지 로딩 이후에 동적으로 추가되었기 때문에 이벤트 위임 방식
  // 이벤트 위임: 이벤트 위임은 기존에 페이지에 있는 부모 요소에 이벤트를 걸어 두는 방식
  $(document).on("click", ".edit", function () {
    console.log("report 버튼 클릭");
    $("#reportModal").css("display", "block");
  });

  $(".close-btn").on("click", function () {
    $("#reportModal").css("display", "none");
  });

  $(window).on("click", function (e) {
    if ($(e.target).is("#reportModal")) {
      $("#reportModal").css("display", "none");
    }
  });

  // 모달 드래그 이동
  var isDragging = false;

  $(".modal-content").on("mousedown", function (e) {
    isDragging = true;

    // 마우스 클릭 위치와 .modal-content 요소의 현재 위치 차이를 계산
    // offset(): HTML 요소의 위치를 조절하거나 가져올 때 사용
    offsetX = e.pageX - $(this).offset().left;
    offsetY = e.pageY - $(this).offset().top;

    $("body").css("user-select", "none");
  });

  $(document).on("mousemove", function (e) {
    if (isDragging) {
      $(".modal-content").offset({
        top: e.pageY - offsetY, // 마우스 Y 위치에서 초기 클릭한 위치 차이를 뺀 값을 .modal-content의 새 Y 위치로 설정
        left: e.pageX - offsetX,
      });
    }
  });

  $(document).on("mouseup", function () {
    isDragging = false;
    $("body").css("user-select", "auto");
  });

  // 사이드바 이동
  $(".sidebar-menu").click(function () {
    const targetScroll = $(this).data("target");
    const headerHeight = window.innerHeight * 0.2; // 헤더 높이 고려

    if (targetScroll) {
      $("html, body").animate(
        {
          scrollTop: $(targetScroll).offset().top - headerHeight,
        },
        600
      );
    }
  });
});
function sample6_execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      var addr = ""; // 주소 변수
      var extraAddr = ""; // 참고항목 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === "R") {
        // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
      if (data.userSelectedType === "R") {
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if (data.buildingName !== "" && data.apartment === "Y") {
          extraAddr +=
            extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if (extraAddr !== "") {
          extraAddr = " (" + extraAddr + ")";
        }
        // 조합된 참고항목을 해당 필드에 넣는다.
        document.getElementById("extraAddress").value = extraAddr;
      } else {
        document.getElementById("extraAddress").value = "";
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      document.getElementById("postcode").value = data.zonecode;
      document.getElementById("addr").value = addr;
      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("detailAddress").focus();
    },
  }).open();
}
