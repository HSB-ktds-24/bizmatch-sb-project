package com.ktdsuniversity.edu.bizmatch.project.web;

import java.text.ParseException;
import java.time.LocalDate;
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
import com.ktdsuniversity.edu.bizmatch.project.service.ProjectService;
import com.ktdsuniversity.edu.bizmatch.project.vo.ApplyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ModifyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentModifyVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentPaginationVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentWriteVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectListVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectScrapVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectSkillVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SearchProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SelectApplyMemberVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;


@Controller
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	/**
	 * 프로젝트 등록 페이지.
	 * 
	 * @return
	 */
	@GetMapping("/project/regist")
	public String viewProjectRegistPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO) {
		if(memberVO.getMbrCtgry() == 1) {
			return "redirect:/";
		}
		return "project/project_register";
	}

	/**
	 * 프로젝트 지원페이지.
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
		return "project/project_apply";
	}

	/**
	 * 프로젝트 상세보기 페이지
	 */
	@GetMapping("/project/info/{pjId}")
	public String viewProjectInfoPage(@PathVariable String pjId, Model model , ProjectCommentPaginationVO projectCommentPaginationVO
			, @SessionAttribute(value="_LOGIN_USER_", required =false) MemberVO loginMemberVO) {
		int listSize = this.projectService.getAllComment(pjId).size();
		System.out.println(listSize);
		projectCommentPaginationVO.setPageCount(listSize);
		projectCommentPaginationVO.setSearchIdParam(pjId);
		List<ProjectCommentVO> commentList = this.projectService.getPaginationComment(projectCommentPaginationVO, pjId);
		
		ProjectVO projectVO =  this.projectService.readOneProjectInfo(pjId);
		System.out.println(projectVO.toString());
		model.addAttribute("projectId",pjId);
		model.addAttribute("projectVO",projectVO);
		model.addAttribute("comments", commentList);
		model.addAttribute("paginationVO", projectCommentPaginationVO);
		model.addAttribute("loginMemberVO", loginMemberVO);
		
		return "project/project_info";
	}

	
	
	// 페이지네이션 빈
	// 삭제 기능
	@GetMapping("/project/info/delete/{id}")
	@ResponseBody
	public Map<String, Object> setDeleteSate(@PathVariable String id) {
		boolean result = this.projectService.updateDeleteState(id);
	
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	
	@PostMapping("/project/info/write")
	@ResponseBody
	public Map<String, Object> writeComment(ProjectCommentWriteVO projectCommentWriteVO , @SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO){
		projectCommentWriteVO.setAthrId(memberVO.getEmilAddr());
		boolean result = this.projectService.createNewComment(projectCommentWriteVO);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
	}
	
	
	// 수정 기능
	@PostMapping("/project/info/modify")
	@ResponseBody
	public Map<String, Object> modifyComment( ProjectCommentModifyVO projectCommentModifyVO) {
		
		boolean result = this.projectService.modifyComment(projectCommentModifyVO);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", result);
		return resultMap;
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
	public String viewProjectFindPage(SearchProjectVO searchProjectVO
									, Model model
									, @SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {
		
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
	public ResponseEntity<?> viewSortedProjectFindPage(SearchProjectVO searchProjectVO
							, @SessionAttribute(value = "_LOGIN_USER_", required = false) MemberVO memberVO) {

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
	@PostMapping("/project/write")
	public String doCreateProject(WriteProjectVO writeProjectVO
								, Model model
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
		if (writeProjectVO.getCntrctAccnt() < 1000000) {
			throw new ProjectWriteFailException("프로젝트 입찰가격은 1,000,000원 이상입니다.", writeProjectVO);
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

		boolean isSuccessed = this.projectService.createNewProject(writeProjectVO);
	
		
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

		applyProjectVO.setPjId(pjId);

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
	public String loadAddRecuritPage(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO
									, ProjectVO projectVO) {
	
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

	
	@PostMapping("/project/apply/delete/{pjApplyId}")
	public String deleteApplyContent(@SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO,
			ApplyProjectVO applyProjectVO) {
		applyProjectVO.setEmilAddr(memberVO.getEmilAddr());
		boolean isDeleted = this.projectService.deleteProjectApply(applyProjectVO);
		return null;
	}

	/**
	 * 회사에 지원한 지원 기업리스트
	 * @param memberVO
	 * @param pjId
	 * @param model
	 * @return
	 */
	@GetMapping("/project/apply/member/{pjId}")
	public String viewApplyMemberPage(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO
									, @PathVariable String pjId
									, Model model) {
		List<ApplyProjectVO> applyProjectVOList = this.projectService.readAllApplyMember(pjId, memberVO);
		model.addAttribute(applyProjectVOList);
		return "project/projectapplylist";
	}



	@GetMapping("/member/mypage/myproject/company/{cmnyId}")
	public String viewMyProjectPage(@PathVariable String cmnyId) {
		return "project/myproject";
	}

//	@GetMapping("/member/mypage/myproject/company/{cmnyId}")
//	public String viewMyProjectPage(@PathVariable String cmnyId) {
//		return "project/myproject";
//	}


	/**
	 * 스킬
	 */
	@GetMapping ("/project/skill/{pjId}")
	public ResponseEntity<List<ProjectSkillVO>> getAllSkills(@PathVariable String pjId) {
		List<ProjectSkillVO> skills = this.projectService.readAllProjectSkill(pjId);
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}



	/**
	 * 수주자 선정하기
=======
	/**
	 * 지원기업 선택하기
>>>>>>> 2e5ae75c81d8a0a157856527fa6d4c50f6fa775a
	 * @param pjId
	 * @param memberVO
	 * @param selectApplyMemberVO
	 * @return
	 */

	@PostMapping("/project/apply/member/{pjId}")
	public String doChoiceApplyMember(@PathVariable String pjId
									, @SessionAttribute(value = "_LOGIN_USER_", required = false)MemberVO memberVO
									, SelectApplyMemberVO selectApplyMemberVO) {		
		
		return "view";
	}
	
	/**
	 * 하나의 기업이 발주한 모든 프로젝트 조회
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/project/company/all/order")
	public String viewAllProjectOrder(@SessionAttribute(value = "_LOGIN_USER_")MemberVO memberVO
									, Model model) {
		
		List<ProjectVO> projectList = this.projectService.readAllProjectCompanyOrder(memberVO);
		model.addAttribute("projectList",projectList);
		
		return "project/myproject";
	}
	
	/**
	 * 한명의 회원이 수주했던 즉 수행했던 모든 프로젝트 조회
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/project/all/order/recipient")
	public String viewAllProject(@SessionAttribute(value = "_LOGIN_USER_")MemberVO memberVO, Model model) {
		List<ProjectVO> projectList = this.projectService.readAllProjectOrderRecipient(memberVO);
		String email = memberVO.getEmilAddr();
		model.addAttribute("projectList",projectList);
		
		model.addAttribute("email",email);
		return "project/myApplyProjectList";
	}
	
	/**
	 * 내가 지원한 지원서 불러오는 컨트롤러
	 */
	@GetMapping("/project/apply/list")
	public String viewApplyList(@SessionAttribute(value = "_LOGIN_USER_" , required = false)MemberVO memberVO, Model model) {
		
		List<ApplyProjectVO> applyProjectVOList =this.projectService.readAllApply(memberVO);
		model.addAttribute("applyProjectVOList", applyProjectVOList);
		return "project/myApplyView";
	}
	/*
	 * 프로젝트 스크랩을 요청하는 컨트롤러.
	 * @param pjId
	 * @param memberVO
	 * @return
	 */
	@PostMapping("/project/scrap/{pjId}")
	public String doScrapProject(@PathVariable String pjId
								 , @SessionAttribute(value = "_LOGIN_USER_") MemberVO memberVO) {
		ProjectScrapVO projectScrapVO = new ProjectScrapVO();
		
		projectScrapVO.setEmilAddr(memberVO.getEmilAddr());
		projectScrapVO.setPjId(pjId);
		
		this.projectService.insertProjectScrap(projectScrapVO);
		
		return "redirect:/project/info/{pjId}";
	}
	
	/**
	 * 특정 프로젝트의 지원서 정보 조회를 요청하는 컨트롤러.
	 * @param applyProjectVO
	 * @return
	 */

	@GetMapping("/project/view/applyinfo")
	public String getApplyProjectInfo(@RequestParam String emilAddr,
	                                  @RequestParam String pjId,
	                                  Model model) {
	    // Create a new ApplyProjectVO and set the values
	    ApplyProjectVO applyProjectVO = new ApplyProjectVO();
	    applyProjectVO.setEmilAddr(emilAddr);
	    applyProjectVO.setPjId(pjId);
	    
	    
	    ApplyProjectVO applyProjectVO2 = this.projectService.findOneApplyProjectWithoutApplyId(applyProjectVO);
	    System.out.println("아이디 가져와라" +applyProjectVO2.getPjApplyId());
	    
	    // Add the result to the model
	    model.addAttribute("applyProjectVO", applyProjectVO2);
	    System.out.println("실행중");
	    return "redirect:/";
	}
	
}
