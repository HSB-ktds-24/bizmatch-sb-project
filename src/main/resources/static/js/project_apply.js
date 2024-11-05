$().ready(function () {
  // 파일 추가
  $("#fileInput").on("change", function () {
    const files = this.files;

    if (files.length === 0) {
      alert("선택된 파일이 없습니다.");
      return;
    }

    $.each(files, function (index, file) {
      $("#fileSelect").append(
        $("<option>", {
          text: file.name,
          value: file.name,
        })
      );
    });
  });

  // 파일 삭제
  $("#removeButton").on("click", function () {
    const selectedIndex = $("#fileSelect").prop("selectedIndex");
    if (selectedIndex !== -1) {
      $("#fileSelect option").eq(selectedIndex).remove();
    } else {
      alert("삭제할 파일을 선택해주세요.");
    }
  });
});
