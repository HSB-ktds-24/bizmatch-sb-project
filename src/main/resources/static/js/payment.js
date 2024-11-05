$().ready(function(){
	// 가맹점 식별하기
	  // 결제 데이터 정의하기
	  $("#paymentBox").on("click", function(){
		var order = {
		            productId: 1,
		            productName: '정의진짱1',
		            price: 10,
		            quantity: 1
		        };
		IMP.init("imp81518761");
		IMP.request_pay({
		            pg: "html5_inicis",
		            pay_method:"card",
		            merchant_uid: `mid_${new Date().getTime()}`, // 주문번호 생성
		            name: '정의진짱1',
		            amount: 10, // 결제 가격
		            buyer_name: '정의진짱',
		            buyer_tel: '010-1234-5678'
		        }, function(rsp) {
		            if (rsp.success) {
		                // 결제 성공 시
						console.log(rsp);
						$.post(`/bizmatch/payment/ask/deposit`,{
							// 결제 유형, 결제 금액, 프로젝트 아이디, 이메일, 이름
							pjId: "프로젝트아이디",
							emilAddr: rsp.buyer_email,
							mbrNm: rsp.name,
							impUid: rsp.imp_uid,
							paymentType: $("#paymentType").data("paymentType"),
							cntrctAmt: rsp.paid_amount
						},function(){
							location.href="/project/apply/list";
						});
						location.href="/project/apply/list";
		               alert("결제 성공");
		            } else {
		                alert(rsp.error_msg);
						location.href="error/500";
		            }
		        });
		
	  });
	   
});
