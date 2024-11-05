$().ready(function (){

	var btnId = null;
	var index =null;
	
	$(".show-model-button").on("click", function () {
		btnId = "new";
	  $("#commentModal").css("display","flex");
	  $("#commentModal")[0].showModal();
	  $(".modal-container").css("display", "block");
	});

	$(".close-btn").on("click", function () {
		$("#commentModal").css("display","none");
	  $("#commentModal")[0].close();
	  $(".modal-container").css("display", "none");
	});
	
	$(".submit-btn").on("click", function () {
	  $(".modal-container").css("display", "none");
	  $("#commentModal").css("display","none");
	});
	
	$(".delete-btn").on("click", function () {
		var data = $(this).closest(".fuction-line").data("id");
	
		$.get(`/comment/delete/${data}`, {}, function(){
			location.reload(); // 새로고침
		});
			
	});
	
	$(".modify-btn").on("click", function () {
		
		btnId = "modify";
		index= $(this).closest(".fuction-line").data("id");
		$("#commentModal").css("display","flex");
		$("#commentModal")[0].showModal();
		$(".modal-container").css("display", "block");
	});
	
	$(".recomment-btn").on("click",function(){
		
		btnId = "recomment";
		index= $(this).closest(".fuction-line").data("id");
		$("#commentModal").css("display","flex");
		$("#commentModal")[0].showModal();
		$(".modal-container").css("display", "block");
	})
	
	
	$(".submit-btn").on("click", function(){
		
		var text = $("#cmmntCntnt").val();
	console.log(btnId)
		if (btnId === "modify"){
			$.post(`/comment/modify/${index}`, { cmmntCntnt : text} ,function(){
				location.reload();
			})
		}
		else if (btnId === "new"){
			$.post("/comment/list", { cmmntCntnt : text } ,function(){
				location.reload();
			})
		}
		else if (btnId === "recomment"){
			$.post("/comment/list", { cmmntCntnt : text , pjCmmntId : index} ,function(){
				location.reload();
			})
		}
		$("#commentModal").css("display","none");
		$("#commentModal")[0].close();
		$(".modal-container").css("display", "none");
	})
	
});