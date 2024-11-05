$().ready(function () {
  loadPortfolioList();

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

function loadPortfolioList() {
  // 최신 3개의 포트폴리오 리스트를 가지고 오는 요청을 하는 메소드.
  $.ajax({
    url: "/member/mypage/company/portfolio/content",
    method: "GET",
    dataType: "json",
    success: function (response) {
      $(".result").empty();

      if (response.length === 0) {
        $(".result").append(
          '<div class="no-portfolio">포트폴리오 정보가 없습니다</div>'
        );
      } else {
        response.slice(0, 3).forEach(function (portfolio) {
          var portfolioCard = $(`
              <div class="portfolio-item">
                <a href="/view/portfolio/view/detail/${portfolio.id}">
                  <img class="location-port" src="${portfolio.img}" style="object-fit: cover; width: 100%; height: 100%;" />
                </a>
              </div>
            `);
          $(".result").append(portfolioCard);
        });
      }
    },
    error: function () {
      $(".result")
        .empty()
        .append(
          '<div class="no-portfolio">포트폴리오 정보를 불러오는 중 오류가 발생했습니다.</div>'
        );
    },
  });
}
