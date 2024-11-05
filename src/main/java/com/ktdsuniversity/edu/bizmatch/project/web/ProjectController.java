package com.ktdsuniversity.edu.bizmatch.project.web;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectApplyFailException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectWriteFailException;
import com.ktdsuniversity.edu.bizmatch.common.utils.ParameterCheck;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;
import com.ktdsuniversity.edu.bizmatch.payment.service.PaymentService;
import com.ktdsuniversity.edu.bizmatch.project.service.ProjectService;
import com.ktdsuniversity.edu.bizmatch.project.vo.ApplyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ModifyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectListVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectScrapVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectSkillVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SearchProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SelectApplyMemberVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.PrmStkVO;

@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PaymentService paymentService;

	/**
	 * 프로젝트 등록 페이지를 로드하는 컨트롤러.
	 * 
	 * @return
	 */
	@GetMapping("/project/regist")
	public String viewProjectRegistPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO) {
		// 만약 회원 유형이 프리랜서 유형이라면 접근 못하게 막아야한다.
		if(memberVO.getMbrCtgry() == 1) {
			return "redirect:/";
		}
		// 만약 회원이 계좌인증을 안했으면 접근 못하게 막아야한다.
		if(memberVO.getAccntNum() == null) {
			return "redirect:/";
		}
		// 또한, 계좌인증을 해달라고 클라이언트에게 메세지를 전달해야한다.
		
		return "project/project_register";
	}

	/**
	 * 프로젝트 지원페이지를 로드하는 컨트롤러.
	 * 
	 * @param pjId
	 * @param model
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/project/apply/{pjId}")
	public String viewApplyPage(@PathVariable String pjId, Model model,
			@SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {
		
		if (memberVO == null) {
			return "redirect:/";
		}
		// 만약 회원이 계좌인증을 안했으면 접근 못하게 막아야한다.
		return "project/project_apply";
	}

	/**
	 * 프로젝트 상세보기 페이지.
	 * @param pjId
	 * @param model
	 * @return
	 */
	@GetMapping("/project/info/{pjId}")
	public String viewProjectInfoPage(@PathVariable String pjId, Model model) {
		ProjectVO projectVO = this.projectService.readOneProjectInfo(pjId);
		System.out.println(projectVO.toString());
		model.addAttribute("projectVO", projectVO);
		return "project/project_info";
	}

	/**
	 * 프로젝트 문의 페이지.
	 * 
	 * @return
	 */
	@GetMapping("/project/inquiry")
	public String viewProjectInquiryPage() {
		return "project/project_inquiry_page";
	}

	/**
	 * 프로젝트 찾기 페이지를 반환하는 메서드
	 */
	@GetMapping("/project/find")
	public String viewProjectFindPage(SearchProjectVO searchProjectVO, Model model,
			@SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {

		ProjectListVO projectListVO = this.projectService.selectAllCardProject(searchProjectVO);
		int size = projectListVO.getProjectCnt();

		searchProjectVO.setPageCount(size);

		ProjectListVO projectPaginationListVO = this.projectService.selectForPagination(searchProjectVO);

		// 로그인 확인
		if (memberVO == null) {
			return "redirect:/"; // 로그인 페이지로 리다이렉트
		}

		// 프로젝트 목록 조회
		model.addAttribute("projectListVO", projectListVO);
		model.addAttribute("searchProjectVO", searchProjectVO); // 모델에 데이터 추가
		model.addAttribute("projectPaginationListVO", projectPaginationListVO.getProjectList());

		return "/project/project_find"; // 뷰 이름 반환
	}

	/**
	 * 정렬된 프로젝트 목록을 JSON 형태로 반환하는 메서드
	 */
	@ResponseBody
	@GetMapping(value = "/project/findPage", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> viewSortedProjectFindPage(SearchProjectVO searchProjectVO,
			@SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {

		// 로그인 확인
		if (memberVO == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); // 401 상태 반환
		}

		// 정렬된 프로젝트 목록 조회
//		ProjectListVO projectListVO = this.projectService.selectAllCardProject(searchProjectVO);
//		int size = projectListVO.getProjectCnt();
//		searchProjectVO.setPageCount(size);

		ProjectListVO projectPaginationListVO = this.projectService.selectForPagination(searchProjectVO);

		return ResponseEntity.ok(projectPaginationListVO); // JSON 형태로 반환
	}

	/**
	 * 프로젝트 등록 페이지
	 * 
	 * @param writeProjectVO
	 * @param model
	 * @param loginMemberVO
	 * @return
	 * @throws ParseException
	 */
	// ProjectController.java
	@PostMapping("/project/write")
	public String doCreateProject(WriteProjectVO writeProjectVO 
								, Model model
								, @RequestParam("prmStkId") List<String> prmStkIdList
								, @SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO loginMemberVO) throws ParseException {

		if (loginMemberVO == null) {
			return "redirect:/";
		}

		// writeProjectVO.setIp(RequestUtil.getIp());
		// 신고 누적횟수

		// 유효성 검사들.
		// 프로젝트 제목
		if (writeProjectVO.getPjTtl() == null) {
			throw new ProjectWriteFailException("프로젝트 제목은 필수 입력 사항입니다.", writeProjectVO);
		}
		// 프로젝트 일정
		if (writeProjectVO.getStrtDt() == null || writeProjectVO.getEndDt() == null) {
			throw new ProjectWriteFailException("프로젝트 일정은 필수 입력 사항입니다.", writeProjectVO);
		}
		// 지원 내용
		if (writeProjectVO.getPjDesc() == null) {
			throw new ProjectWriteFailException("프로젝트 상세 설명은 필수 입력 사항입니다.", writeProjectVO);
		}
		// 프로젝트 입찰가격
		// TODO: 이거 테스트 하는 용이라서 나중에 바꿔야해요.
		if (writeProjectVO.getCntrctAccnt() < 1000) {
			throw new ProjectWriteFailException("프로젝트 입찰가격은 1,000원 이상입니다.", writeProjectVO);
		}
		// 프로젝트 모집일
		if (writeProjectVO.getPjRcrutStrtDt() == null || writeProjectVO.getPjRcrutEndDt() == null) {
			throw new ProjectWriteFailException("프로젝트 모집일은 필수 입력 사항입니다.", writeProjectVO);
		}
		// 날짜 문자열을 LocalDate로 변환
		LocalDate startDate = LocalDate.parse(writeProjectVO.getPjRcrutStrtDt());
		LocalDate endDate = LocalDate.parse(writeProjectVO.getPjRcrutEndDt());

		// 날짜 차이 계산
		long daysBetween = java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();

		// 모집 기간 최소 7일 체크
		if (daysBetween < 7) {
			throw new ProjectWriteFailException("프로젝트 모집기간은 최소 7일 이상이어야 합니다.", writeProjectVO);
		}

		// 종료일이 시작일보다 이전인 경우 체크
		if (daysBetween < 0) {
			throw new ProjectWriteFailException("종료일은 시작일 이후여야 합니다.", writeProjectVO);
		}

		// 첨부파일

		// 프로젝트 인원
		if (writeProjectVO.getPjRcrutCnt() <= 0) {
			throw new ProjectWriteFailException("프로젝트 모집 인원은 필수 입력 사항입니다.", writeProjectVO);
		}

		writeProjectVO.setOrdrId(loginMemberVO.getEmilAddr());
		
		
		// 이제 수정된 호출
		List<String> skillList = new ArrayList<>(prmStkIdList);
		

		boolean isSuccessed = this.projectService.createNewProject(writeProjectVO, skillList);
		// 프로젝트 등록에 성공한 경우.
		if (isSuccessed) {
			return "redirect:/project/find";
		}
		// 프로젝트 등록에 실패한 경우.
		else {
			return "redirect:/project/regist";
		}
	}

	/**
	 * 프로젝트 지원을 요청하는 컨트롤러.
	 * 
	 * @param applyProjectVO
	 * @param pjId
	 * @param memberVO
	 * @return
	 */
	@PostMapping("/project/apply/{pjId}")
	public String doApplyProject(ApplyProjectVO applyProjectVO, @PathVariable String pjId,
			@SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {

		// 이게 null로 들어오는 듯.
	
		applyProjectVO.setPjId(pjId);
		System.out.println("프로젝트 아이디:" + pjId);

		if (memberVO == null) {
			return "redirect:/";
		}

		applyProjectVO.setEmilAddr(memberVO.getEmilAddr());

		if (ParameterCheck.parameterCodeValid(applyProjectVO.getPjApplyDesc(), 0)) {
			throw new ProjectApplyFailException("pjApplyDesc를 입력해주세요", applyProjectVO);
		}

		boolean isSuccessed = this.projectService.createNewProjectApply(applyProjectVO);
		// 프로젝트 지원에 성공한 경우.
		if (isSuccessed) {
			return "redirect:/project/find";
		}
		// 프로젝트 지원에 실패한 경우.
		else {
			return "redirect:/project/apply" + pjId;
		}
	}

	/**
	 * 프로젝트 게시글의 조회수를 증가하는 요청을 받는 컨트롤러.
	 * 
	 * @param memberVO
	 * @param pjId
	 * @return
	 */
	@ResponseBody
	@PostMapping("/project/update/viewcount/{pjId}")
	public Map<String, Object> updateProjectViewCnt(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO,
			@PathVariable String pjId) {
		Map<String, Object> resultMap = new HashMap<>();

		boolean isUpdate = this.projectService.updateProjectViewCnt(pjId);
		resultMap.put("response", isUpdate);

		return resultMap;
	}

	/**
	 * 특정 프로젝트 수정페이지를 로드하는 컨트롤러.
	 * 
	 * @param memberVO
	 * @param pjId
	 * @return
	 */
	@GetMapping("/project/update/content/{pjId}")
	public String loadUpdateProjectPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO,
			@PathVariable String pjId) {
		return null;
	}

	/**
	 * 프로젝트 수정 요청을 보내는 컨트롤러.
	 * 
	 * @param modifyProjectVO
	 * @return
	 */
	@PostMapping("/project/update/content/{pjId}")
	public String UpdateProjectInfo(ModifyProjectVO modifyProjectVO) {
		boolean isUpdated = this.projectService.updateOneProject(modifyProjectVO);
		return null;
	}

	/**
	 * 프로젝트 추가모집 수정 페이지를 로드하는 컨트롤러.
	 * 
	 * @param memberVO
	 * @param projectVO
	 * @return
	 */
	@GetMapping("/project/update/addrecurit/")
	public String loadAddRecuritPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO, ProjectVO projectVO) {

		return null;
	}

	/**
	 * 추가모집 정보를 서버에 업로드하는 컨트롤러.
	 * 
	 * @param modifyProjectVO
	 * @return
	 */
	@PostMapping("/project/update/addrecurit/")
	public String createRecuritInfo(ModifyProjectVO modifyProjectVO) {
		boolean isUpdated = this.projectService.updateAddtionalRecruitment(modifyProjectVO);

		return null;
	}

	/**
	 * 프로젝트 삭제를 처리하는 컨트롤러.
	 * 
	 * @param memberVO
	 * @param pjId
	 * @return
	 */
	@PostMapping("/project/delete/")
	public String deleteProject(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO, String pjId) {
		boolean isDeleted = this.projectService.deleteOneProject(null);

		return null;
	}

	/**
	 * 지원서 수정페이지를 로드하는 컨트롤러.
	 * 
	 * @param memberVO
	 * @param applyProjectVO
	 * @return
	 */
	@GetMapping("/project/apply/edit")
	public String loadUpdateApplyContentPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO) {
		return null;
	}

	/**
	 * 지원서 수정을 요청하는 컨트롤러.
	 * 
	 * @param applyProjectVO
	 * @return
	 */
	@PostMapping("/project/apply/edit")
	public String updateApplyContent(ApplyProjectVO applyProjectVO) {
		boolean isUpdated = this.projectService.updateProjectApply(applyProjectVO);
		return null;
	}

	/**
	 * 프로젝트 지원을 삭제 요청을 하는 컨트롤러.
	 * @param memberVO
	 * @param applyProjectVO
	 * @return
	 */
	@PostMapping("/project/apply/delete/{pjApplyId}")
	public String deleteApplyContent(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO,
			ApplyProjectVO applyProjectVO) {
		applyProjectVO.setEmilAddr(memberVO.getEmilAddr());
		boolean isDeleted = this.projectService.deleteProjectApply(applyProjectVO);
		return null;
	}

	/**
	 * 회사에 지원한 지원 기업리스트
	 * 
	 * @param memberVO
	 * @param pjId
	 * @param model
	 * @return
	 */
	@GetMapping("/project/apply/member/{pjId}")
	public String viewApplyMemberPage(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO
									, @PathVariable String pjId
									, Model model) {
		ProjectVO projectVO = this.projectService.readOneProjectInfo(pjId);
		System.out.println(projectVO.getPjId());
		if(projectVO.getPaymentVO().getGrntPdDt()==null) {
			return "redirect:/bizmatch/payment/ask/deposit/"+pjId;
		}
		List<ApplyProjectVO> applyProjectVOList = this.projectService.readAllApplyMember(pjId, memberVO);
		model.addAttribute(applyProjectVOList);
		
		// 보증금을 납부했는지 먼저 검사해야한다.
		boolean isPaid = this.paymentService.readIsPaymentDeposit(pjId);
		
		// 납부했으면 지원기업 리스트 페이지를 보여줘야함.
		if(isPaid) {
			return "redirect:/project/apply/member/" + pjId;
		}
		// 납부 안했으면 결제 페이지로 리다이랙트.
		return "redirect:/bizmatch/payment/ask/deposit/" + pjId;
	}

//	@GetMapping("/member/mypage/myproject/company/{cmnyId}")
//	public String viewMyProjectPage(@PathVariable String cmnyId) {
//		return "project/myproject";
//	}

	/**
	 * 검색할때 스킬 목록 불러오기
	 */
	@GetMapping("/project/skill/{pjId}")
	public ResponseEntity<List<ProjectSkillVO>> getAllSkills(@PathVariable String pjId) {
		List<ProjectSkillVO> skills = this.projectService.readAllProjectSkill(pjId);
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}

	@GetMapping("/project/skill")
	public ResponseEntity<List<PrmStkVO>> getAllSkills() {
		List<PrmStkVO> skills = this.projectService.selectAllProjectSkillList();

		return new ResponseEntity<>(skills, HttpStatus.OK);
	}

	/**
	 * 지원기업 선택하기
	 * 
	 * @param pjId
	 * @param memberVO
	 * @param selectApplyMemberVO
	 * @return
	 */
	@PostMapping("/project/apply/member/{pjId}")
	public String doChoiceApplyMember(@PathVariable String pjId
									, @SessionAttribute(value = "_LOGIN_USER_", required = false)MemberVO memberVO
									, SelectApplyMemberVO selectApplyMemberVO) {
		
		return "redirect:/bizmatch/payment/ask/deposit/" + pjId;
	}

	/**
	 * 하나의 기업이 발주한 모든 프로젝트 조회
	 * 
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/project/company/all/order")
	public String viewAllProjectOrder(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO, Model model) {

		List<ProjectVO> projectList = this.projectService.readAllProjectCompanyOrder(memberVO);
		model.addAttribute("projectList", projectList);

		return "project/myproject";
	}

	/**
	 * 한명의 회원이 수주했던 즉 수행했던 모든 프로젝트 조회
	 * 
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/project/all/order/recipient")
	public String viewAllProject(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO, Model model) {
		List<ProjectVO> projectList = this.projectService.readAllProjectOrderRecipient(memberVO);
		model.addAttribute("projectList",projectList);
		return "project/myApplyProjectList";
	}

	/**
	 * 내가 지원한 지원서 불러오는 컨트롤러
	 */
	@GetMapping("/project/apply/list")
	public String viewApplyList(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO
								, Model model) {
		
		List<ApplyProjectVO> applyProjectVOList = this.projectService.readAllApply(memberVO);
		model.addAttribute("applyProjectVOList", applyProjectVOList);
		
		return "project/myApplyView";
	}

	/*
	 * 프로젝트 스크랩을 요청하는 컨트롤러.
	 * 
	 * @param pjId
	 * 
	 * @param memberVO
	 * 
	 * @return
	 */
	@PostMapping("/project/scrap/{pjId}")
	public String doScrapProject(@PathVariable String pjId,
			@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO) {
		ProjectScrapVO projectScrapVO = new ProjectScrapVO();

		projectScrapVO.setEmilAddr(memberVO.getEmilAddr());
		projectScrapVO.setPjId(pjId);

		this.projectService.insertProjectScrap(projectScrapVO);

		return "redirect:/project/info/{pjId}";
	}
	
//	/**
//	 * 특정 프로젝트의 지원서 정보 조회를 요청하는 컨트롤러.
//	 * @param applyProjectVO
//	 * @return
//	 */
//	@GetMapping("/project/view/applyinfo")
//	public String getApplyProjectInfo(@RequestParam("pjId") String pjId
//									, Model model) {
//		
//		applyProjectVO = this.projectService.readOneApplyProject(applyProjectVO);
//		model.addAttribute("applyProjectVO", applyProjectVO);
//		
//		return "/project/myApplyView";
//	}
	
}
