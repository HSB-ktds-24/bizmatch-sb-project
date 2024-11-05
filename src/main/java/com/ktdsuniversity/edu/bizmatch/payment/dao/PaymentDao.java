package com.ktdsuniversity.edu.bizmatch.payment.dao;

import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentRequestVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.RefundDepositVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;

public interface PaymentDao {
	
	public String NAMESPACE = "com.ktdsuniversity.edu.bizmatch.payment.dao.PaymentDao";
	
	/**
	 * 새로운 결제정보를 추가하는 메서드.
	 * @param paymentVO
	 * @return
	 */
	public int insertNewPaymentInfo(PaymentRequestVO paymentRequestVO);
	
	/**
	 * 프로젝트를 등록할 때 새로운 결제정보를 등록하는 쿼리문을 호출하는 메서드.
	 * @param writeProjectVO
	 * @return
	 */
	public int isnertNewPaymentInfo(WriteProjectVO writeProjectVO);

	/**
	 * 프로젝트의 금액을 조회하는 메소드.
	 * @param pjId : 프로젝트 아이디.
	 * @return : 조회한 컬럼의 개수.
	 */
	public int selectProjectAmount(String pjId);
	
	/**
	 * BizMatch 통장 잔고를 변경하는 쿼리문을 수행하는 메서드.
	 * @param amount : 변경할 금액.
	 * @return : 업데이트된 컬럼의 개수.
	 */
	public int updateAccountBalance(int amount);
	
	/**
	 * 보증급 환급 해주는 메소드
	 * @param 
	 * @return
	 */
	public int updateDeposit(RefundDepositVO RefunddepositVO);
	
	/**
	 * 보증금 결제 정보를 가져오는 메소드 
	 * @param pjId 프로젝트 아이디를 통해 해당 프로젝트에 대한 결제 정보를 가져온다.
	 * @return
	 */
	public PaymentVO selectOneDeposit(String pjId);

	/**
	 * 보증금 정보를 insert하는 쿼리문을 호출하는 메서드.
	 * @param depositPaymentRequsetVO
	 * @return
	 */
	public int insertNewDepositPaymentInfo(PaymentRequestVO depositPaymentRequsetVO);
}
