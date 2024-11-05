$().ready(function(){
	$("#detail-btn").on("click", function(){
		var type= $(this).data("type");
		var id = $(this).data("index");
		if(type=='1'){
			location.href=`/member/mypage/freelancer/${id}`
		}
		else if(type=='0'){
			location.href=`/member/mypage/company/${id}`
		}
	})
})