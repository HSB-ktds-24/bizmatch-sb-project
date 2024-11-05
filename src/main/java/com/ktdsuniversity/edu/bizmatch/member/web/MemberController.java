package com.ktdsuniversity.edu.bizmatch.member.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;

import com.ktdsuniversity.edu.bizmatch.common.category.vo.CategoryVO;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.ResetPassword;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.member.SignUpFailException;
import com.ktdsuniversity.edu.bizmatch.common.skills.vo.MbrPrmStkVO;
import com.ktdsuniversity.edu.bizmatch.common.utils.ParameterCheck;
import com.ktdsuniversity.edu.bizmatch.member.service.MemberService;
import com.ktdsuniversity.edu.bizmatch.member.vo.CompanyVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberCompanyModifyVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberCompanySignUpVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberFreelancerModifyVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberLoginVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberModifyVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberMyPageIndsryVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberPaginationVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberPortfolioVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberResetPwdVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberSignUpVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.PrmStkVO;
import com.ktdsuniversity.edu.bizmatch.project.review.vo.ReviewVO;

import jakarta.servlet.http.HttpSession;

/**
 * 이 컨트롤러는 회원과 관련한 api 요청을 다루는 컨트롤러입니다.
 * 
 * @author jeong-uijin
 */
@Controller
public class MemberController {
	
	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	private final RestTemplate restTemplate;
	
	public MemberController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * 비밀번호 재설정 페이지를 로드하는 컨트롤러.
	 * @return
	 */
	@GetMapping("/member/findpwd")
	public String loadFindPwdPage() {
		return "/member/member_findpwd";
	}
	
	@PostMapping("/member/findpwd")
	public String sendMemberFindPwd(@RequestParam String email) {
		boolean isSuccess = this.memberService.sendFindPwdEmail(email);
		if(!isSuccess) {
			return "redirect:/member/findpwd";
		}
		return "redirect:/";
	}
	
	@GetMapping("/member/resetpwd")
	public String loadResetPwdPage() {
		return "/member/member_reset_pwd";
	}
	
	@PostMapping("/member/resetpwd")
	public String requestResetPwd(MemberResetPwdVO memberResetPwdVO) {
		
		if(ParameterCheck.parameterCodeValid(memberResetPwdVO.getPwd(), 0)) {
			throw new ResetPassword("비밀번호는 필수 입력사항입니다.");
		}
		if(ParameterCheck.parameterCodeValid(memberResetPwdVO.getConfirmNewPwd(), 0)) {
			throw new ResetPassword("비밀번호 확인은 필수 입력사항입니다.");
		}
		
		boolean isSuccess = this.memberService.resetMemberPwd(memberResetPwdVO);
		
		if(!isSuccess) {
			return "redirect:/member/resetpwd";
		}
		return "redirect:/";
	}
	
	/**
	 * 회원가입 유형 선택 페이지를 로드하는 컨트롤러.
	 * @return
	 */
	@GetMapping("/member/select/membertype")
	public String loadSelectMemberType() {
		return "member/select_member_type";
	}
	
	/**
	 * 기업형 회원가입 페이지를 로드하는 컨트롤러
	 * @return
	 */
	@GetMapping("/member/signup/company")
	public String loadSignUpPageCompany() {
		return "member/signup_company";
	}
	
	/**
	 * 기업형 회원가입을 처리하는 컨트롤러이다.
	 * 
	 * @param memberCompanySignUpVO
	 * @return
	 */
	@PostMapping("/member/signup/company")
	public String signUpCompanyMember(MemberCompanySignUpVO memberCompanySignUpVO, Model model) {
		
		
		// 사용자가 입력한 값 유효성 검사.
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getMbrNm(), 0)) {
//			throw new SignUpFailException("이용자명은 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getEmilAddr(), 7)) {
//			throw new SignUpFailException("이메일은 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getCmpnyBrn() , 10)) {
//			throw new SignUpFailException("사업자 번호는 필수 입력사항입니다.");
//		}
//		 
//		if(memberCompanySignUpVO.getCmpnyEmplyCnt() == 0) {
//			throw new SignUpFailException("직원 수는 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getMbrPhnNum(), 10)) {
//			throw new SignUpFailException("전화번호는 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getCmpnyNm(), 0)) {
//			throw new SignUpFailException("기업명은 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getCmpnyPhnNum(), 0)) {
//			throw new SignUpFailException("기업 전화번호는 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberCompanySignUpVO.getCmpnyBizCtgry(), 0)) {
//			throw new SignUpFailException("기업 주요 산업분야는 필수 입력사항입니다.");
//		}
//		
//		if(!memberCompanySignUpVO.getAgreeOne().equals("Y") || !memberCompanySignUpVO.getAgreeTwo().equals("Y") || !memberCompanySignUpVO.getAgreeThree().equals("Y")) {
//			throw new SignUpFailException("동의 사항에 모두 동의해야합니다.");
//		}
		logger.debug(memberCompanySignUpVO.toString());
		
		boolean isSuccessed = this.memberService.signupCompanyMember(memberCompanySignUpVO);
		
		// 회원가입에 성공한 경우.
		if(isSuccessed) {
			//회원가입이 완료되었다면 이메일 인증 번호 테이블에 로우 삭제해준다.
			return "main/mainpage";
		} 
		// 회원가입에 실패한 경우.
		else {
			return "redirect:/member/signup/company";
		}
	}
	
	@ResponseBody
	@GetMapping("/member/signup/cmpnycheck/{cmpnyBrn}")
	public Map<String, Object> companyCheck(@PathVariable String cmpnyBrn){
		
		CompanyVO companyVO = this.memberService.readOneCompany(cmpnyBrn);
		
		if(companyVO == null) {
			return Map.of("response", true);
		}		
		return Map.of("response", companyVO);
	}
	
	@ResponseBody
	@GetMapping("/bizno/api/ask/{cmpnyBrn}")
	public Map handleBiznoApi(@PathVariable String cmpnyBrn) {
		Map<String, Object> request = new HashMap<>();
		request.put("key", "amVqMDAxMjI4QGdtYWlsLmNvbSAg");
		request.put("gb", "1");
		request.put("q", cmpnyBrn);
		request.put("type", "json");
		
		String queryParam = "?";
		
		queryParam += request.entrySet()
				.stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue().toString())
				.collect(Collectors.joining("&"));
		
		
		ResponseEntity<Map> response = this.restTemplate.getForEntity("https://bizno.net/api/fapi" + queryParam, Map.class);
		
		logger.debug(response.getBody().toString());
		
		return response.getBody();
	}
	
	/**
	 * 프리랜서형 회원가입 페이지를 로드하는 컨트롤러.
	 * @return
	 */
	@GetMapping("/member/signup/freelancer")
	public String loadSignUpPageFreelancer() {
		return "member/signup_freelancer";
	}
	
	@PostMapping("/member/signup/freelancer")
	public String signUpFreelancer(MemberSignUpVO memberSignUpVO , CategoryVO categoryVO) {
//		if(ParameterCheck.parameterCodeValid(memberSignUpVO.getMbrNm(), 0)) {
//			throw new SignUpFailException("이용자명은 필수 입력사항입니다.");
//		}
//		
//		if (ParameterCheck.parameterCodeValid(memberSignUpVO.getBrthDt(), 0)) {
//			throw new SignUpFailException("생년월일은 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberSignUpVO.getEmilAddr(), 7)) {
//			throw new SignUpFailException("이메일은 필수 입력사항입니다.");
//		}
		
//		if(ParameterCheck.parameterCodeValid(memberSignUpVO.getFrlncrIndstrId(), 0))
//			throw new SignUpFailException("주요 산업분야는 필수 입력사항입니다.");
//		}
//		
//		if(ParameterCheck.parameterCodeValid(memberSignUpVO.getIntrstdIndstrId(), 0)) {
//			throw new SignUpFailException("관심 산업분야는 필수 입력사항입니다.");
//		}
		
//		if(!memberSignUpVO.getAgree1().equals("Y") || !memberSignUpVO.getAgree2().equals("Y") || !memberSignUpVO.getAgree3().equals("Y")) {
//			throw new SignUpFailException("동의 사항에 모두 동의해야합니다.");
//		}
		boolean isInserted = this.memberService.signupFreelancerMember(memberSignUpVO, categoryVO);
		if(!isInserted) {
			return "redirect:/member/signup/freelancer";
		}
		// 회원가입을 완료하면 메인페이지로 이동.
		return "/main/mainpage";
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	@ResponseBody
	@GetMapping("/member/signup/email/available/") 
	public Map<String, Object> checkAvailableEmail(@RequestParam String email) {
				
		boolean isAvailableEmail = this.memberService.isDuplicatedEmail(email);
		
		Map<String, Object> response = new HashMap<>();
		response.put("email", email);
		response.put("available", isAvailableEmail);
		
		return response;
	}
	/**
	 * 로그인
	 * @param memberLoginVO 
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/member/signin")
	public String doSignIn(MemberLoginVO memberLoginVO, HttpSession session, Model model) {
		// 유효성검사.
		if(ParameterCheck.parameterCodeValid(memberLoginVO.getEmilAddr(), 0)) {
			model.addAttribute("ex_message", "이메일을 입력해주세요");
			return "redirect:/";
		}
		if(ParameterCheck.parameterCodeValid(memberLoginVO.getPwd(), 0)) {
			model.addAttribute("ex_message", "비밀번호를 입력해주세요");
			return "redirect:/";
		}
		MemberVO memberVO = this.memberService.isVarifiedMemberLogin(memberLoginVO);
		
		session.setAttribute("_LOGIN_USER_", memberVO);
		MemberVO memberVOSession = (MemberVO)session.getAttribute("_LOGIN_USER_");
		
		System.out.println(memberVOSession.toString());
		
		return "main/mainpage";
	}
	/**
	 * 로그아웃
	 * @param memberVO
	 * @param session
	 * @return
	 */
	@GetMapping("/member/logout")
	public String doLogout(@SessionAttribute(value = "_LOGIN_USER_", required = false)MemberVO memberVO
							, HttpSession session) {
		if(memberVO == null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * 기업형 마이페이지를 로드하는 컨트롤러.
	 * @return
	 */
	@GetMapping("/member/mypage/company/{cmpnyId}")
	public String loadCompanyMyPage(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO, Model model, @RequestParam(required = false, defaultValue = "late-date") String orderBy, @PathVariable String cmpnyId) {
		
		if(loginMemberVO == null || loginMemberVO.getEmilAddr() == null || loginMemberVO.getEmilAddr().isEmpty() || !loginMemberVO.getCmpId().equals(cmpnyId)) {
			return "redirect:/";
		}
		
		// 기업 정보 조회
		CompanyVO companyVO = memberService.selectOneCompanyByEmilAddr(loginMemberVO);
		model.addAttribute("companyVO", companyVO);
		
		// 보유기술 리스트 조회
		List<MbrPrmStkVO> mbrPrmStkList = memberService.selectMbrPrmStkList(loginMemberVO.getEmilAddr());
		
		model.addAttribute("test", "test");

		model.addAttribute("mbrPrmStkList", mbrPrmStkList);
		
		// 주요 산업 조회
		MemberMyPageIndsryVO mbrIndstrVO = memberService.readMbrIndstr(cmpnyId);
		System.out.println(mbrIndstrVO.getMjrNm());
		model.addAttribute("mbrIndstrVO", mbrIndstrVO);
		
		// 리뷰 리스트 조회
		Map<String, Function<String, List<ReviewVO>>> sortMethodMap = new HashMap<>();
		sortMethodMap.put("late-date", memberService::selectReviewList);
		sortMethodMap.put("high-rate", memberService::selectReviewListBySrcDesc);
		sortMethodMap.put("low-rate", memberService::selectReviewListBySrcAsc);
		
		List<ReviewVO> reviewList = sortMethodMap.getOrDefault(orderBy, memberService::selectReviewList).apply(loginMemberVO.getEmilAddr());
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("orderBy", orderBy);
		
		// 전체 리뷰 평균 별 계산
		double averageRate = reviewList.stream().mapToDouble(ReviewVO::getScr).average().orElse(0);
		
		model.addAttribute("averageRate", averageRate);
	
		return "member/mypage_company";
	}
	
	/**
	 * 프리랜서 마이페이지 로드하는 컨트롤러.
	 * @return
	 */
	@GetMapping("/member/mypage/freelancer/{email}/")
	public String loadFreelancerMyPage(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO, @PathVariable String email, Model model, @RequestParam(required = false, defaultValue = "late-date") String orderBy) {
//		this.memberService.read
		
		// 보유기술 리스트 조회
		List<MbrPrmStkVO> mbrPrmStkList = memberService.selectMbrPrmStkList(loginMemberVO.getEmilAddr());
		model.addAttribute("mbrPrmStkList", mbrPrmStkList);
		
		// 관심 산업 조회
		MemberMyPageIndsryVO mbrIndstrVO = memberService.readMbrIndstr(loginMemberVO.getEmilAddr());
		model.addAttribute("mbrIndstrVO", mbrIndstrVO);
		
		// 리뷰 리스트 조회
		Map<String, Function<String, List<ReviewVO>>> sortMethodMap = new HashMap<>();
		sortMethodMap.put("late-date", memberService::selectReviewList);
		sortMethodMap.put("high-rate", memberService::selectReviewListBySrcDesc);
		sortMethodMap.put("low-rate", memberService::selectReviewListBySrcAsc);
		
		List<ReviewVO> reviewList = sortMethodMap.getOrDefault(orderBy, memberService::selectReviewList).apply(loginMemberVO.getEmilAddr());
		System.out.println("Listsize"+reviewList.size());
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("orderBy", orderBy);
		
		// 전체 리뷰 평균 별 계산
		double averageRate = reviewList.stream().mapToDouble(ReviewVO::getScr).average().orElse(0);
		model.addAttribute("averageRate", averageRate);
				
		return "member/mypage_freelancer";
	}
	
	@GetMapping("/member/mypage/myproject")
	public String loadMyProjectPage() {
		return "member/myproject";
	}
	
	/**
	 * 마이페이지 리뷰 더 보기 상세페이지
	 * @return
	 */
	@GetMapping("/member/mypage/reivewlist")
	public String viewMoreReviewListPage(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO, Model model, @RequestParam(required = false, defaultValue = "late-date") String orderBy, MemberPaginationVO memberPaginationVO) {
		if(loginMemberVO == null || loginMemberVO.getEmilAddr() == null || loginMemberVO.getEmilAddr().isEmpty()) {
			return "redirect:/";
		}
		
		int reviewListSize = this.memberService.selectReviewList(loginMemberVO.getEmilAddr()).size();
		memberPaginationVO.setPageCount(reviewListSize);
		memberPaginationVO.setSearchEmilParameter(loginMemberVO.getEmilAddr());
		
		// 리뷰 리스트 조회
		// String: 정렬 기준
		// Function<MemberPaginationVO, List<ReviewVO>>: MemberPaginationVO 입력 받아 List<ReviewVO> 반환하는 함수
		// Map.ofEntries: 3개의 키-값 쌍을 한 번에 Map으로 생성
		Map<String, Function<MemberPaginationVO, List<ReviewVO>>> sortMethodMap = Map.ofEntries(
				Map.entry("high-rate",
						paginationVO -> memberService.selectPaginationByScrDesc(paginationVO,
								loginMemberVO.getEmilAddr())),
				Map.entry("low-rate",
						paginationVO -> memberService.selectPaginationByScrAsc(paginationVO,
								loginMemberVO.getEmilAddr())),
				Map.entry("late-date",
						paginationVO -> memberService.selectPagination(paginationVO, loginMemberVO.getEmilAddr())));
		
		// 기본 정렬 함수로 "late-date"의 pagination 적용
		List<ReviewVO> reviewList = sortMethodMap
				.getOrDefault(orderBy,
						paginationVO -> memberService.selectPagination(paginationVO, loginMemberVO.getEmilAddr()))
				.apply(memberPaginationVO);
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("paginationVO", memberPaginationVO);
		
		
		return "member/morereviewlist";
	}
	
	/**
	 * 기업 마이페이지 수정 페이지
	 * @return
	 */
	@GetMapping("/member/mypage/company/edit/{cmpnyId}")
	public String viewCompanyMyPageEdit(Model model 
										,@PathVariable String cmpnyId
										, @SessionAttribute(value = "_LOGIN_USER_", required = false)MemberVO memberVO) {
		// FIXME: memberVO.getCmpId()==null
		if(!memberVO.getCmpId().equals(cmpnyId) || memberVO.getCmpId()==null ) {
			throw new IllegalArgumentException("회사정보가 없음");
		}
		CompanyVO companyVO =  this.memberService.selectOneCompanyByEmilAddr(memberVO);
		model.addAttribute("companyVO",companyVO);
		
		System.out.println(companyVO.getCmpnyAddr()+": 상세주소");
		return "member/mypagecompanyedit";
		
	}
	@ResponseBody
	@PostMapping("/member/mypage/company/edit")
	public Map<String, Object> doCompanyMyPageEdit(@RequestBody MemberCompanyModifyVO memberCompanyModifyVO 
									, @SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO) {
		System.out.println("cmpnyNme"+memberCompanyModifyVO.getCmpnyNm());
		System.out.println("cmpnyurl"+memberCompanyModifyVO.getCmpnySiteUrl());
		System.out.println("cmpnyintr"+memberCompanyModifyVO.getCmpnyIntr());
		System.out.println("계좌번호"+memberCompanyModifyVO.getCmpnyAccuntNum());
		boolean isSuccess = this.memberService.updateCompanyMemberMyPage(memberCompanyModifyVO, memberVO);
		
		if(isSuccess) {
			return Map.of("response", isSuccess);
		}
		
		return Map.of("reponse", isSuccess);
	}
	
	/**
	 * 프리랜서 마이페이지 수정 페이지
	 * @return
	 */
	@GetMapping("/member/mypage/freelancer/edit/{email}")
	public String viewFreelancerMyPageEdit(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO loginMemberVO
										, @PathVariable String email, Model model) {
		
		int ctgry = loginMemberVO.getMbrCtgry();
		// 보유기술 리스트 조회
		List<MbrPrmStkVO> mbrPrmStkList = memberService.selectMbrPrmStkList(loginMemberVO.getEmilAddr());
		model.addAttribute("mbrPrmStkList", mbrPrmStkList);
		

		// TODO 고쳐야 할 수도 있음
		if(ctgry == 0) {
			return "redirect:/member/mypage/company/edit/"+loginMemberVO.getCmpId();
		}
		return "member/mypagefreelanceredit";
	}
	
	@ResponseBody
	@PostMapping("/member/mypage/freelancer/edit")
	public Map<String, Object> doFreelancerMyPageEdit(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO
										, @RequestBody MemberFreelancerModifyVO memberFreelancerModifyVO) {
		
		int cmpId = memberVO.getMbrCtgry();
		if (cmpId == 0) {
			return Map.of("response", false, "redirectUrl", "/member/mypage/company/edit/" + memberVO.getCmpId());
		}
		
		memberFreelancerModifyVO.setEmilAddr(memberVO.getEmilAddr());
		memberFreelancerModifyVO.setMbrIntr(memberFreelancerModifyVO.getMbrIntr());
		memberFreelancerModifyVO.setMjrId(memberVO.getMjrId());
		memberFreelancerModifyVO.setSmjrId(memberVO.getSmjrId());
		// 오류 발생시 주석처리
	    memberFreelancerModifyVO.setMbrPrmStkList(memberFreelancerModifyVO.getMbrPrmStkList());
		// 상범 - 추가함
	    memberFreelancerModifyVO.setAccntNum(memberFreelancerModifyVO.getAccntNum());
				
		boolean isSuccess = memberService.updateFreelancerMemberMypage(memberFreelancerModifyVO);
		
		
		return Map.of("response", isSuccess);
	}
	
	@GetMapping("/member/newportfolio")
	public String loadWriteNewPortfolioPage() {
		return "/portfolio/portfolio_write";
	}
	
	/**
	 * 새로운 포트폴리오를 등록하는 요청을 보내는 컨트롤러이다.
	 * @param memberPortfolioVO : 사용자가 입력한 포트폴리오 정보.
	 * @return
	 */
	@PostMapping("/member/newportfolio")
	public String requestNewPortfolio(@SessionAttribute(value = "_LOGIN_USER_")MemberVO memberVO, MemberPortfolioVO memberPortfolioVO) {
		// 서비스에 새로운 포트폴리오 등록 요청을 보낸다.
		memberPortfolioVO.setEmilAddr(memberVO.getEmilAddr());
		boolean isCreated = this.memberService.createNewPortfolio(memberPortfolioVO);
		
		if(!isCreated) {
			return "redirect:/member/newportfolio";
		}
		// 기업회원인경우.
		if(memberVO.getMbrCtgry() == 0) {
			return "redirect:/member/mypage/company/portfolio";
		}
		// 프리랜서인경우.
		return "redirect:/member/mypage/freelancer/portfolio";
	}
	
	/**
	 * 프리랜서 포트폴리오 페이지를 로드하는 컨트롤러.
	 * @param loginMemberVO
	 * @return
	 */
	@GetMapping("/member/mypage/freelancer/portfolio")
	public String loadPortfolioListPageFL(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO) {
		return "/portfolio/portfoliolist";
	}
	
	/**
	 * 기업 회원의 포트폴리오 목록 리스트 페이지로딩.
	 * @return
	 */	
	@GetMapping("/member/mypage/company/portfolio")
	public String loadPortfolioListPageCmp(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO) {
		return "/portfolio/portfoliolist";
	}
	
	/**
	 * 특정 회원의 포트폴리오 목록 리스트 불러오는 메서드.
	 * @param loginMemberVO
	 * @return
	 */
	@ResponseBody
	@GetMapping("/member/mypage/company/portfolio/content")
	public List<Map<String, Object>> viewPortfolioListPage(@SessionAttribute(value = "_LOGIN_USER_")MemberVO loginMemberVO) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		
		List<MemberPortfolioVO> portfolioList = this.memberService.selectAllPortfolios(loginMemberVO.getEmilAddr());
		for (MemberPortfolioVO memberPortfolioVO : portfolioList) {
			Map<String, Object> tempMap = new HashMap<>();
			// 대표이미지는 첨부파일의 가장 첫번째를 기준으로 한다.
			// FIXME: 이거 0번 없다고 존재함.
//			String img = memberPortfolioVO.getAttVOs().get(0).getAttUrl();
			String title = memberPortfolioVO.getMbrPrtflTtl();
			String text = memberPortfolioVO.getMbrPrtflText();
			String id = memberPortfolioVO.getMbrPrtflId();
			// map에다가 데이터를 추가한다.
//			tempMap.put("img", img);
			tempMap.put("title", title);
			tempMap.put("text", text);
			tempMap.put("id", id);
			resultList.add(tempMap);
			
////			System.out.println("이미지:" + img);
//			System.out.println("제목:" + title);
//			System.out.println("내용:" + text);
		}
		for(Map<String, Object> tempMap2 : resultList) {
			System.out.println(tempMap2.get("title"));
			System.out.println(tempMap2.get("text"));
			System.out.println(tempMap2.get("id"));
		}
		return resultList;
	}
	
	/**
	 * 내 정보 수정 페이지 로딩.
	 * @return
	 */
	@GetMapping("/member/mypage/myinfo-edit")
	public String viewMyInfoEditPage() {
		return "member/myinfo_edit";
	}
	@PostMapping("/member/mypage/myinfo-edit")
	public String doMyInfoEdit(MemberModifyVO memberModifyVO, @SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO, Model model) {
		
		memberModifyVO.setEmilAddr(memberVO.getEmilAddr());
		boolean isSuccess = this.memberService.updateMyInfoMember(memberModifyVO);
		
		if(isSuccess) {
			return "redirect:/";
		}
		model.addAttribute("memberModifyVO",memberModifyVO);
		return "member/myinfo_edit";
		
		
	}
	/**
	 * 기업 주소 정보
	 * @param loginMemberVO
	 * @return
	 */
	@ResponseBody
	@GetMapping("/member/mypage/company/address")
	public ResponseEntity<?> getCompanyAddress(@SessionAttribute(value = "_LOGIN_USER_") MemberVO loginMemberVO) {
		
		if(loginMemberVO.getEmilAddr() == null || loginMemberVO.getEmilAddr().isEmpty()) {
			return ResponseEntity.badRequest().body("로그인이 필요합니다");
		}
		
		CompanyVO companyVO = memberService.selectOneCompanyByEmilAddr(loginMemberVO);
		
		if(companyVO.getCmpnyAddr() == null ||companyVO.getCmpnyAddr().isEmpty()) {
			return ResponseEntity.badRequest().body("회사 주소가 없습니다.");
		}
		
		Map<String, String> response = new HashMap<>();
		response.put("cmpnyAddr", companyVO.getCmpnyAddr());
		
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 
	 * @param loginMemberVO 로그인한 회원 정보 객체
	 * @param orderBy 리뷰 정렬 기준
	 * @return
	 */
	@GetMapping("/member/mypage/company/reviews")
	@ResponseBody
	// 파라미터 sort 수신, 값이 없을 경우 최신순 default로 설정
	public Map<String, Object> selectSortedReviewsList(@SessionAttribute(value = "_LOGIN_USER_") MemberVO loginMemberVO,  @RequestParam(required = false, defaultValue = "late-date") String orderBy , MemberPaginationVO memberPaginationVO) {
		
		int reviewListSize = this.memberService.selectReviewList(loginMemberVO.getEmilAddr()).size();
		memberPaginationVO.setPageCount(reviewListSize);
		memberPaginationVO.setSearchEmilParameter(loginMemberVO.getEmilAddr());
		
		// 정렬 기준에 따라 호출할 메서드 매핑하는 Map 생성
		Map<String, Function<MemberPaginationVO, List<ReviewVO>>> sortMethodMap = Map.ofEntries(
				Map.entry("high-rate",
						paginationVO -> memberService.selectPaginationByScrDesc(paginationVO,
								loginMemberVO.getEmilAddr())),
				Map.entry("low-rate",
						paginationVO -> memberService.selectPaginationByScrAsc(paginationVO,
								loginMemberVO.getEmilAddr())),
				Map.entry("late-date",
						paginationVO -> memberService.selectPagination(paginationVO, loginMemberVO.getEmilAddr())));
		
		List<ReviewVO> reviewList = sortMethodMap
				.getOrDefault(orderBy ,
						paginationVO -> memberService.selectPagination(paginationVO, loginMemberVO.getEmilAddr()))
				.apply(memberPaginationVO);
		
		Map<String, Object> result = new HashMap<>();
		result.put("reviewList", reviewList);
		result.put("paginationVO", memberPaginationVO);
		
		return result;
	}
	
	/**
	 * 특정 회원의 관심 산업 데이터를 조회
	 * @param loginMemberVO 로그인한 멤버 정보 객체
	 * @return
	 */
	@GetMapping("/member/mypage/company/industry")
	@ResponseBody
	public ResponseEntity<?> getCompanyIndustry(@SessionAttribute(value= "_LOGIN_USER_") MemberVO loginMemberVO) {
		MemberMyPageIndsryVO mbrLkIndstrVO = memberService.selectMbrLkIndstr(loginMemberVO.getEmilAddr());
		if(mbrLkIndstrVO != null) {
			return ResponseEntity.ok(mbrLkIndstrVO);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("관심 산업 데이터가 없습니다.");
		}
	}
	
	/**
	 * 하나의 포트폴리오 정보를 조회하는 컨트롤러.
	 * @param loginMemberVO
	 * @param mbrPrtflId
	 * @return
	 */
	@ResponseBody
	@GetMapping("/view/portfolio/detail/{mbrPrtflId}")
	public Map<String, Object> loadDataPortfolioDetailsOne(@SessionAttribute(value= "_LOGIN_USER_") MemberVO loginMemberVO
															, @PathVariable String mbrPrtflId) {
		MemberPortfolioVO memberPortfolioVO = this.memberService.selectOnePortfolio(mbrPrtflId);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("id", memberPortfolioVO.getMbrPrtflId());
		resultMap.put("title", memberPortfolioVO.getMbrPrtflTtl());
		resultMap.put("text", memberPortfolioVO.getMbrPrtflText());
		resultMap.put("att", memberPortfolioVO.getAttVOs());
		System.out.println("아이디: " + "");
		return resultMap;
	}
	
	@GetMapping("/view/portfolio/view/detail/{mbrPrtflId}")
	public String loadDataPortfolioDetailsOne() {
		return "/portfolio/portfoliolist";
	}
	
	/**
	 * 하나의 포트폴리오 정보를 제거하는 메소드.
	 * @param mbrPrtflId
	 * @return
	 */
	@PostMapping("/member/delete/portfolio/{mbrPrtflId}")
	public String deleteOnePortfolio(@SessionAttribute(value = "_LOGIN_USER_")MemberVO memberVO 
										,@PathVariable String mbrPrtflId) {
		boolean isDeleted = this.memberService.deleteOnePortfolio(mbrPrtflId);
		if(!isDeleted) {
			return "redirect:/member/delete/portfolio/{mbrPrtflId}";
		}
		return "redirect:/view/portfolio/detail/" + mbrPrtflId;
	}
	
	/**
	 * 회원의 포트폴리오를 수정하는 컨트롤러.
	 * @param memberVO
	 * @param memberPortfolioVO
	 * @return
	 */
	@PostMapping("/member/update/portfolio/{mbrPrtflId}")
	public String updatePortfolio(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO
									, MemberPortfolioVO memberPortfolioVO) {
		boolean isUpdated = this.memberService.updateOnePortfolio(memberPortfolioVO);
		// 실패시 현재 화면 보여줌.
		if(!isUpdated) {
			return "/member/delete/portfolio/{mbrPrtflId}";
		}
		// 성공시 포트폴리오 목록 페이지로 이동.
		return "redirect:/view/portfolio/view/detail/" + memberPortfolioVO.getMbrPrtflId();
	}
}
