function appendProjectCard(response) {
  $("#result").empty();
  response.projectList.forEach(function (project) {
    const projectCard = `
  <div class="project-card-container">
<div class="project-card">
<div class="project-box">
<div class="project-head">
  <div class="project-head-front">
    <div class="status-recruiting">모집중</div>
    <!-- <div class="status-additional-recruiting">추가모집중</div> -->
    <!-- <div class="status-ing">진행중</div> -->
    <!-- <div class="status-done">완료</div> -->
    <h2 id="pjttl" class="project-title" data-pjid="${project.pjId}">${project.pjTtl}</h2>
  </div>
  <div class="post-date">등록일자 ${project.rgstrDt}</div>
</div>
<div class="project-body">
  <div class="project-body-box">
    <div class="project-body-title">프로젝트 분야</div>
    <div class="project-body-content bold">
      IT·프로그래밍 / 웹사이트·모바일앱 개발
    </div>
  </div>
  <div class="sidebar"></div>
  <div class="project-body-box">
    <div class="project-body-title">관련기술</div>
    <div class="project-body-content">
      <div class="circle-box">
        <div class="circle"></div>
      </div>
      <div class="language">Java, HTML5</div>
    </div>
  </div>
  <div class="sidebar"></div>
  <div class="project-body-box">
    <div class="project-body-title">모집 마감일</div>
    <div class="project-body-content">${project.pjRcrutEndDt}</div>
  </div>
  <div class="sidebar"></div>
  <div class="project-body-box">
    <div class="project-body-title">프로젝트 일정</div>
    <div class="project-body-content">
      ${project.strtDt} ~ ${project.endDt}
    </div>
  </div>
</div>
<div class="project-footer">
  <div class="button-box">
    <input
      class="apply"
      id="apply"
      data-id="${project.pjId}"
      type="button"
      value="신청하기"
    />
  </div>
  <div class="estimated-amount">
    <div>예상 금액</div>
    <div class="half-sidebar"></div>
    <div class="bold">${project.cntrctAccnt} 원</div>
  </div>
</div>
</div>
</div>
</div>
`;
    $("#result").append(projectCard);
  });
}

$().ready(function () {
  var moneyValue = $(".estimated-amount .bold").text();

  // 포맷 함수: 금액을 확실히 숫자로 변환 후 천 단위 쉼표 추가
  function formatCurrency(amount) {
    const parsedAmount = parseInt(amount.toString().replace(/[^0-9]/g, ""), 10); // 금액을 정수로 변환
    console.log("Formatted Amount:", parsedAmount); // 디버깅용 콘솔 출력
    return new Intl.NumberFormat().format(parsedAmount); // 천 단위 구분 쉼표
  }

  var formattedMoney = formatCurrency(moneyValue);
  moneyValue = formattedMoney;

  pagination(function (response) {
    appendProjectCard(response);
  });

  $(".filters span").on("click", function (event) {
    event.preventDefault();
    var orderBy = $(this).data("order");
    $("#orderBy").val(orderBy);

    callList("/project/findPage", { orderBy }, 0, 5, function (response) {
      appendProjectCard(response);
    });

    $(".filters span").removeClass("active");

    $(this).addClass("active");

    $("#find-title").on("click", function () {
      var pjId = $(this).data("pjid");
      console.log(pjId);
      location.href = `/project/info/${pjId}`;
    });
  });
});
