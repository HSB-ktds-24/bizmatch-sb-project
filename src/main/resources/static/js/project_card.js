$().ready(function () {
  $("#applyBtn").on("click", function () {
    var pjId = $(this).data("id");

    location.href = `/project/apply/${pjId}`;
  });
  $(".project-title").on("click", function () {
    var pjId = $(this).data("pjid");
    location.href = `/project/info/${pjId}`;
    console.log(pjId);
  });
});
