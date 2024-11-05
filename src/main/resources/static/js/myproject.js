$().ready(function () {
  $.get({
    url: "/project/findPage",
    dataType: "json",

    success: function (response) {
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
                  <h2>${project.pjTtl}</h2>
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
                <div class="button-box" style="gap:2rem; ">
                  <input
                    class="apply"
                    id="applylist"
                    data-id="${project.pjId}"
                    type="button"
                    value="지원기업 목록"
                  />
                     <input
                    class="apply"
                    id="apply"
                    data-id="${project.pjId}"
                    type="button"
                    value="취소하기"
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

        $("#applylist").on("click", function () {
          var pjid = $(this).data("id");
          console.log("!");
          location.href = `/project/apply/member/${pjid}`;
        });
      });
    },
  });

  // $(".main-title").click(function () {
  //   $(".main-title").removeClass("active").css("color", "gray");

  //   $(this).addClass("active").css("color", "black");
  // });

  $("#myApplyProjectList").on("click", function () {
    location.href = "/project/all/order/recipient";
  });

  $("#myProjectList").on("click", function () {
    location.href = "/project/company/all/order";
  });

  $("#applyview").on("click", function () {
	  alert("!");
      var pjId = $(this).data("id");
      var emilAddr = $(this).data("email"); 

      $.get({
          url: "/project/view/applyinfo",
          dataType: "json",
          data: {
              emilAddr: emilAddr,  
              pjId: pjId
          }
      });
  })
});
