package com.ktdsuniversity.edu.bizmatch.payment.service;

import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentRequestVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentUpdateVO;

public interface PaymentService {

	/**
	 * 계약금 결제를 요청하는 메서드.
	 * @param paymentRequestVO
	 * @return
	 */
	public boolean createDownPayment(PaymentRequestVO paymentRequestVO);
	
	/**
	 * 보증금 결제를 요청하는 메서드.
	 * @param depositPaymentRequestVO
	 * @return
	 */
	public boolean createDepositPay(PaymentRequestVO depositPaymentRequestVO);
	
	/**
	 * 프로젝트 진행 중 거래가 파기가 된 경우 메소드 실행.
	 * @param paymentUpdateVO : 업데이트하는 결제 정보를 담은 객체.
	 * @return : 업데이트 여부.
	 */
	public boolean updateDownPaymentWhenBrokenDeal(PaymentUpdateVO paymentUpdateVO);
	
	/**
	 * 결제 취소 요청을 하는 메소드.
	 * @param paymentRequestVO
	 * @return
	 */
	public boolean cancelRequest(PaymentRequestVO paymentRequestVO);
	
	/**
	 * 보증금 납부 여부를 조회하는 메서드.
	 * @param pjId
	 * @return
	 */
	public boolean readIsPaymentDeposit(String pjId);
}
