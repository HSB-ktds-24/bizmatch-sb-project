package com.ktdsuniversity.edu.bizmatch.payment.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktdsuniversity.edu.bizmatch.payment.service.PaymentService;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentRequestVO;
import com.ktdsuniversity.edu.bizmatch.project.service.ProjectService;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectVO;

@Controller

public class PaymentController {
	
	public static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/bizmatch/payment/ask/downpayment/error/500")
	public String viewDownPaymentErrorPage() {
		return "/error/payment_error";
	}
	/**
	 * 계약금 결제 페이지 가져오기.
	 * @param pjId
	 * @param model
	 * @return
	 */
	@GetMapping("/bizmatch/payment/ask/downpayment/{pjId}")
	public String paymentDownpaymentPage(@PathVariable String pjId, Model model) {
		ProjectVO projectVO = this.projectService.readOneProjectInfo(pjId);
		
		model.addAttribute("projectVO",projectVO);
		return "payment/payment_page_downpayment";
	}
	
	/**
	 * 보증금 결제 페이지 가져오기.
	 * @param pjId
	 * @param model
	 * @return
	 */
	@GetMapping("/bizmatch/payment/ask/deposit/{pjId}")
	public String paymentDepositPage(@PathVariable String pjId
									, Model model) {
		// 프로젝트 정보 가져와야함.
		ProjectVO projectVO = this.projectService.readOneProjectInfo(pjId);
		
		// 모델에 담아줘서 반환함.
		model.addAttribute("projectVO", projectVO);
		return "/payment/payment_page_deposit";
	}
	
	/**
	 * 계약금 결제 요청을 하는 컨트롤러.
	 * @param paymentVO
	 * @return
	 */
	@PostMapping("/payment/ask/downpayment")
	public String askDownPayment(PaymentRequestVO paymentRequestVO) {
		
		paymentRequestVO.setPaymentType(1);
		
		boolean isSuccess = this.paymentService.createDownPayment(paymentRequestVO);
		
		if(!isSuccess) {
			return "error/payment_error";
		}
		return "redirect:/project/find";
	}
	
	/**
	 * 보증금 결제 요청을 하는 컨트롤러.
	 * @param depositPaymentRequestVO
	 * @return
	 */
	@PostMapping("/bizmatch/payment/ask/deposit")
	public String doPaymentDeposit(PaymentRequestVO depositPaymentRequestVO) {
		
		// 결제 타입 0-> 보증금 결제.
		depositPaymentRequestVO.setPaymentType(0);
		
		boolean isSuccess = this.paymentService.createDepositPay(depositPaymentRequestVO);
		
		if(isSuccess) {
			return "/project/apply/list";
		}
		return "error/payment_error";
	}
	
	/**
	 * 결제 취소를 요청하는 컨트롤러.
	 * @param paymentRequestVO
	 * @return
	 */
	@GetMapping("/payments/cancel")
	public String doCancel(PaymentRequestVO paymentRequestVO) {
		
		this.paymentService.cancelRequest(paymentRequestVO);
		
		return "redirect:/";
	}
	/**
	 * 결제 오류 페이지를 보여주는 컨트롤러.
	 * @return
	 */
	@GetMapping("/bizmatch/payment/ask/deposit/error/500")
	public String viewPaymentErrorPage() {
		return "/error/payment_error";
	}
}
