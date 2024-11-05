$().ready(function(){
	$("#submit").on("click",function(){
		
		var selected = $("#genre").val();
		var checked = $("#ck-box").is(':checked')?1:0;
		console.log(selected);
		console.log(checked);
		
		$.post("/board/write", 
			{ athrId : "0",
			pstCtgry :selected ,
			pstNm : $("#title").val(),
			pstCntnt : $("#content").val() ,
			isPstOpn : checked },function(){
				location.href = "/board/list";
			});
			
	});
})
