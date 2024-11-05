$().ready(function () {
  $("input[type=button]").on("click", function () {
    var pjId = $(this).data("id");

    location.href = `/project/apply/${pjId}`;
  });
  $("h2").on("click", function () {
      var pjId = $(this).data("pjid");
      location.href = `/project/info/${pjId}`;
	  console.log(pjId);
    });
});
