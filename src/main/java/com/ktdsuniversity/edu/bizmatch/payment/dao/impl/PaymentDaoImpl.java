package com.ktdsuniversity.edu.bizmatch.payment.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.bizmatch.payment.dao.PaymentDao;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentRequestVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.RefundDepositVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;

@Repository
public class PaymentDaoImpl extends SqlSessionDaoSupport implements PaymentDao{
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int insertNewPaymentInfo(PaymentRequestVO downPaymentRequestVO) {
		return getSqlSession().insert(NAMESPACE + ".insertNewPaymentInfo", downPaymentRequestVO);
	}
	@Override
	public int selectProjectAmount(String pjId) {
		return getSqlSession().selectOne(NAMESPACE + ".selectProjectAmount", pjId);
	}
	@Override
	public int updateAccountBalance(int amount) {
		return getSqlSession().update(NAMESPACE + ".updateAccountBalance", NAMESPACE);
	}

	@Override
	public int insertNewDepositPaymentInfo(PaymentRequestVO depositPaymentRequsetVO) {
		return this.getSqlSession().insert(NAMESPACE+".insertNewDepositPaymentInfo", depositPaymentRequsetVO);
	}

	@Override
	public int updateDeposit(RefundDepositVO refundDepositVO) {
		return this.getSqlSession().update(NAMESPACE+".updateDeposit", refundDepositVO);
	}

	@Override
	public PaymentVO selectOneDeposit(String pjId) {
		return this.getSqlSession().selectOne(NAMESPACE+".selectOneDeposit",pjId);
	}

	@Override
	public int isnertNewPaymentInfo(WriteProjectVO writeProjectVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertNewPaymentInfoWhenInsertProject", writeProjectVO);
	}
	
}
