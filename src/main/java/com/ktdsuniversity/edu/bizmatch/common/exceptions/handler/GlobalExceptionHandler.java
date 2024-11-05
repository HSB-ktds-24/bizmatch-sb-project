package com.ktdsuniversity.edu.bizmatch.common.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.bizmatch.common.exceptions.board.BoardException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.comment.ReviewFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.comment.ReviewReportFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.file.CreateNewProjectFileException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.file.FileUploadFailedException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.LoginFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.MemberPortfolioException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.ResetPassword;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.SignUpFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.SignUpNotApprovedException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.payment.PaymentServerSaveException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectApplyFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectDeleteException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectScrapException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectWriteFailException;
import com.ktdsuniversity.edu.bizmatch.project.vo.ApplyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectScrapVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BoardException.class)
    @ResponseBody
    public Map<String, Object> handleBoardException(BoardException be) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "로그인 세션이 없습니다.");
        errorResponse.put("message", be.getMessage());

        return errorResponse;
    }
    
    @ExceptionHandler(MemberPortfolioException.class)
    @ResponseBody
    public Map<String, Object> handleMemberPortfolioException(MemberPortfolioException mpe) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "포트폴리오 관련하여 오류가 발생했습니다.");
        errorResponse.put("message", mpe.getMessage());

        return errorResponse;
    }
    
    @ExceptionHandler(ResetPassword.class)
    @ResponseBody
    public Map<String, Object> handleResetPassword(ResetPassword rp) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "비밀번호 재설정에 실패했습니다.");
        errorResponse.put("message", rp.getMessage());
        
        return errorResponse;
    }

    @ExceptionHandler(SignUpFailException.class)
    @ResponseBody
    public Map<String, Object> handleSignUpFailException(SignUpFailException sufe) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "회원가입에 실패했습니다.");
        errorResponse.put("message", sufe.getMessage());

        return errorResponse;
    }
    
    @ExceptionHandler(LoginFailException.class)
    @ResponseBody
    public Map<String, Object> handleLoginFailException(LoginFailException lfe){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "로그인 실패");
        errorResponse.put("message", lfe.getMessage());
        
        return errorResponse;
    }
    
    @ExceptionHandler(ReviewFailException.class)
    @ResponseBody
    public Map<String, Object> handleReviewFailException(ReviewFailException rfe) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "리뷰 처리 중 에러가 발생했습니다");
        errorResponse.put("message", rfe.getMessage());
        
        return errorResponse;
    }
    
    @ExceptionHandler(ReviewReportFailException.class)
    @ResponseBody
    public Map<String, Object> handleReviewReportFailException(ReviewReportFailException rrfe) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "리뷰 신고 처리 중 에러가 발생했습니다");
        errorResponse.put("message", rrfe.getMessage());
        
        return errorResponse;
    }
    
    /**
     * 
     * @param snae
     * @param model
     * @return
     */
    @ExceptionHandler(SignUpNotApprovedException.class)
    public String handleSignUpNotApprovedException(SignUpNotApprovedException snae, Model model) {
    	model.addAttribute("error", snae.getMessage());
    	return "main/mainpage";
    }
    
    /**
     * 
     * @param fufe
     * @return
     */
    @ExceptionHandler(FileUploadFailedException.class)
    public String handleFileUploadFailedException(FileUploadFailedException fufe, Model model) {
    	model.addAttribute("error", fufe.getMessage());
    	
    	return "redirect:/project/apply/{pjId}";
    }
    
    /**
     * 프로젝트 등록 중 파일 관련해서 오류가 발생했을 때 처리하는 핸들러.
     * @param cnpfe
     * @param model
     * @return : 작성하고 있던 페이지에 정보를 담아서 돌려줌.
     */
    @ExceptionHandler(CreateNewProjectFileException.class)
    public String handleProjectFailException(CreateNewProjectFileException cnpfe
    										, WriteProjectVO writeProjectVO
    										, Model model) {
    	// 모델에 담아주기.
    	model.addAttribute("strtDt", writeProjectVO.getStrtDt());
    	model.addAttribute("endDt", writeProjectVO.getEndDt());
    	model.addAttribute("pjRcrutStrtDt", writeProjectVO.getPjRcrutStrtDt());
    	model.addAttribute("pjRcrutEndDt", writeProjectVO.getPjRcrutEndDt());
    	model.addAttribute("indstrNm", writeProjectVO.getIndstrNm());
    	model.addAttribute("pjTtl", writeProjectVO.getPjTtl());
    	model.addAttribute("pjDesc", writeProjectVO.getPjDesc());
    	model.addAttribute("cntrctAccnt", writeProjectVO.getCntrctAccnt());
    	model.addAttribute("pjRcrutCnt", writeProjectVO.getPjRcrutCnt());
    	model.addAttribute("error", cnpfe.getMessage());
    	
    	return "redirect:/project/regist";
    }
    
    /**
     * 프로젝트 지원서를 작성 중 에러 발생시 해결하는 핸들러.
     * @param pafe
     * @param applyProjectVO
     * @param model
     * @return : 원래 작성하고 있던 페이지로 돌려줌.
     */
    @ExceptionHandler(ProjectApplyFailException.class)
    public String handleProjectApplyFailException(ProjectApplyFailException pafe
    											, ApplyProjectVO applyProjectVO
    											, Model model) {
    	
    	// 모델에 담아주기.
    	model.addAttribute("pjApplyTtl", applyProjectVO.getPjApplyTtl());
    	model.addAttribute("pjApplyDesc", applyProjectVO.getPjApplyDesc());
    	
    	return "redirect:/project/apply/" + applyProjectVO.getPjId();
    }
    
    /**
     * 프로젝트 등록 중 예외가 발생했을 때 처리하는 핸들러.
     * @param pwfe
     * @param writeProjectVO
     * @param model
     * @return : redirect.
     */
    @ExceptionHandler(ProjectWriteFailException.class)
    public String handleProjectWriteFailException(ProjectWriteFailException pwfe
    											, WriteProjectVO writeProjectVO
    											, Model model) {
    	
    	model.addAttribute("pjTtl", writeProjectVO.getPjTtl());
    	model.addAttribute("strtDt", writeProjectVO.getStrtDt());
    	model.addAttribute("endDt", writeProjectVO.getEndDt());
    	model.addAttribute("pjDesc", writeProjectVO.getPjDesc());
    	model.addAttribute("cntrctAccnt", writeProjectVO.getCntrctAccnt());
    	model.addAttribute("pjRcrutCnt", writeProjectVO.getPjRcrutCnt());
    	model.addAttribute("pjRcrutStrtDt", writeProjectVO.getPjRcrutStrtDt());
    	model.addAttribute("pjRcrutEndDt", writeProjectVO.getPjRcrutEndDt());
    	
    	return "redirect:/project/regist";
    }
    
    /**
     * 프로젝트 삭제 중 발생하는 예외를 처리하는 핸들러.
     * @param pde
     * @param pjId
     * @param model
     * @return
     */
    @ExceptionHandler(ProjectDeleteException.class)
    public String handleProjectDeleteException(ProjectDeleteException pde
    										, String pjId
    										, Model model) {
    	model.addAttribute("error", pde.getMessage());
    	return "redirect:/project/info/" + pjId;
    }
    
    /**
     * 프로젝트 스크랩 중 발생하는 예외를 처리하는 핸들러.
     * @param pse
     * @param projectScrapVO
     * @param model
     * @return
     */
    @ExceptionHandler(ProjectScrapException.class)
    public String handleProjectScrapException(ProjectScrapException pse
    										, ProjectScrapVO projectScrapVO
    										, Model model) {
    	
    	model.addAttribute("error", pse.getMessage());
    	return "redirect:/project/info/" + projectScrapVO.getPjId();
    }
    
    /**
     * 결제 요청을 성공적으로 끝냈지만 서버에 결제 정보를 저장하는 도중,
     * 오류가 발생하면 예외를 처리해주는 핸들러이다.
     * @param psse
     * @return
     */
    @ExceptionHandler(PaymentServerSaveException.class)
    public String handlePaymentServerSaveException(PaymentServerSaveException psse) {
    	return "/error/payment_error";
    }
}
