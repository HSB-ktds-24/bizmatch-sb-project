package com.ktdsuniversity.edu.bizmatch.project.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.bizmatch.common.beans.FileHandler;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.file.CreateNewProjectFileException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectDeleteException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectScrapException;
import com.ktdsuniversity.edu.bizmatch.common.exceptions.project.ProjectWriteFailException;
import com.ktdsuniversity.edu.bizmatch.common.vo.StoreResultVO;
import com.ktdsuniversity.edu.bizmatch.file.dao.FileDao;
import com.ktdsuniversity.edu.bizmatch.file.vo.ProjectApplyFileVO;
import com.ktdsuniversity.edu.bizmatch.file.vo.ProjectFileVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;
import com.ktdsuniversity.edu.bizmatch.project.dao.ProjectDao;
import com.ktdsuniversity.edu.bizmatch.project.service.ProjectService;
import com.ktdsuniversity.edu.bizmatch.project.vo.ApplyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ModifyProjectVO;


import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentModifyVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentPaginationVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectCommentWriteVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectListVO;


import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectApplyAttVO;

import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectDateVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectIndustryVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectListVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectScrapVO;

import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectSkillVO;

import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SearchProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SelectApplyMemberVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;

@Service
public class ProjectServiceImple implements ProjectService {

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private FileHandler fileHandler;

	@Override
	public boolean createNewProject(WriteProjectVO writeProjectVO) throws ParseException {
		
		// 날짜 정보 설정하기.
		ProjectDateVO projectDateVO = new ProjectDateVO();
		projectDateVO.setStrtDt(writeProjectVO.getStrtDt());
		projectDateVO.setEndDt(writeProjectVO.getEndDt());
		projectDateVO.setPjRcrutStrtDt(writeProjectVO.getPjRcrutStrtDt());
		projectDateVO.setPjRcrutEndDt(writeProjectVO.getPjRcrutEndDt());
		
		if(!this.verifyPrjectDate(projectDateVO)) {
			throw new ProjectWriteFailException("날짜 정보가 유효하지 않습니다.", writeProjectVO);
		}

		int insertCount = this.projectDao.insertOneProject(writeProjectVO);
		String pjId = writeProjectVO.getPjId();
	
		// 파일 업로드 처리
		List<MultipartFile> fileList = writeProjectVO.getFileList(); // 사용자가 입력한 파일 리스트 가져옴.

		if (fileList == null || fileList.isEmpty()) {
			throw new CreateNewProjectFileException("파일첨부는 필수사항입니다.", writeProjectVO);
		}
		List<StoreResultVO> fileStoreList = this.fileHandler.storeListFile(fileList); // 파일 리스트 난독화 해서 리스트 반환해줌.
		if (fileStoreList == null || fileStoreList.isEmpty()) {
			throw new CreateNewProjectFileException("서버상의 이유로 파일 저장에 실패했습니다.", writeProjectVO);
		}

		int totalInsertedFiles = 0;

		for (StoreResultVO fileResult : fileStoreList) {
			ProjectFileVO projectFileVO = new ProjectFileVO();
			String originfileName = fileResult.getOriginFileName(); // 원본 파일 이름.
			String obfuscatedFileName = fileResult.getObfuscatedFileName(); // 난독화한 파일 이름.
			projectFileVO.setPjAttUrl(originfileName);
			projectFileVO.setPjAttUrlNonread(obfuscatedFileName);
			projectFileVO.setEmilAddr(writeProjectVO.getEmilAddr());
			projectFileVO.setPjId(pjId);

			// 파일을 insert함.
			int insertFileCnt = this.fileDao.insertProjectFile(projectFileVO);

			if (insertFileCnt == 0) {
				throw new ProjectWriteFailException("시스템 오류로 파일 처리가 불가능합니다. 다시 시도해 주세요.", writeProjectVO);
			}
			totalInsertedFiles++;
		}
		
		
		boolean insertresults = false;
		
		// 프로젝트 산업군 집합 테이블에 입력 받은 값을 넣는 추가 작업 
		
		if (insertCount > 0 && totalInsertedFiles > 0) {
			ProjectIndustryVO projectIndustryVOFirst= new ProjectIndustryVO();
			ProjectIndustryVO projectIndustryVOSecond= new ProjectIndustryVO();
			
			projectIndustryVOFirst.setPjId(pjId);
			projectIndustryVOSecond.setPjId(pjId);
			projectIndustryVOFirst.setPjIndstrId(writeProjectVO.getFirstIndstrId());
			projectIndustryVOSecond.setPjIndstrId(writeProjectVO.getSecondIndstrId());
			
			System.out.println(writeProjectVO.getFirstIndstrId()+"산업 아이디");
			
			int insertresultA = this.projectDao.insertNewIndustryGroup(projectIndustryVOFirst);
			int insertresultB = this.projectDao.insertNewIndustryGroup(projectIndustryVOSecond);
			
			if(insertresultA>0&& insertresultB>0) {
				insertresults = true;
			}

			
		}

		return insertresults;
	}

	@Override
	public boolean updateOneProject(ModifyProjectVO modifyProjectVO) {
		int updateCount = this.projectDao.updateOneProject(modifyProjectVO);
		int updateIndustryCount = this.projectDao.updateProjectIndustry(modifyProjectVO.getProjectIndustryVO());
		
		if(updateCount == 0 || updateIndustryCount == 0) {
			throw new ProjectWriteFailException("서버상의 문제로 정보 수정이 불가능합니다.", modifyProjectVO);
		}
		
		return updateCount > 0;
	}

	@Override
	public boolean deleteOneProject(String pjId) {
		int deleteCount = this.projectDao.deleteOneProject(pjId);
		// TODO: 해당 프로젝트와 관련된 파일들도 삭제해야함.
		if(deleteCount == 0) {
			throw new ProjectDeleteException("서버상의 이유로 삭제 중 오류가 발생했습니다.", pjId);
		}
		return deleteCount > 0;
	}

	@Override
	public ProjectListVO selectAllCardProject(SearchProjectVO searchProjectVO) {

		int projectCount = this.projectDao.selectProjectAllCount(searchProjectVO);

		if (projectCount == 0) {
			ProjectListVO projectListVO = new ProjectListVO();
			projectListVO.setProjectCnt(0);
			projectListVO.setProjectList(new ArrayList<>());

			return projectListVO;

		}

		List<ProjectVO> projectList = null;
		if (searchProjectVO == null) {
			projectList = this.projectDao.selectAllCardProject();
		} else {
			projectList = this.projectDao.selectAllCardProject(searchProjectVO);
		}

		ProjectListVO projectListVO = new ProjectListVO();
		projectListVO.setProjectCnt(projectCount);
		projectListVO.setProjectList(projectList);

		return projectListVO;
	}

	@Override
	public boolean createNewProjectApply(ApplyProjectVO applyProjectVO) {

		int insertCount = this.projectDao.insertOneProjectApply(applyProjectVO);
		// 파일 업로드 처리
		List<MultipartFile> fileList = applyProjectVO.getFileList(); // 사용자가 입력한 파일 리스트 가져옴.

		if (fileList == null || fileList.isEmpty()) {
//			throw new ProjectWriteFailException("업로드할 파일이 없습니다.");
		}
		List<StoreResultVO> fileStoreList = this.fileHandler.storeListFile(fileList); // 파일 리스트 난독화 해서 리스트 반환해줌.
		fileStoreList.forEach(a->{
			System.out.println(a.getObfuscatedFileName());
		});
		if (fileStoreList == null || fileStoreList.isEmpty()) {
//			throw new ProjectWriteFailException("파일 저장에 실패했습니다.");
		}

		int totalInsertedFiles = 0;

		for (StoreResultVO fileResult : fileStoreList) {
			ProjectApplyFileVO projectApplyFileVO = new ProjectApplyFileVO();
			String originfileName = fileResult.getOriginFileName(); // 원본 파일 이름.
			String obfuscatedFileName = fileResult.getObfuscatedFileName(); // 난독화한 파일 이름.
			projectApplyFileVO.setPjApplyAttUrl(originfileName);
			projectApplyFileVO.setPjApplyAttUrlNoneread(obfuscatedFileName);
			projectApplyFileVO.setEmilAddr(applyProjectVO.getEmilAddr());
			projectApplyFileVO.setPjApplyId(applyProjectVO.getPjApplyId());
			
		

			// 파일을 insert함.

			int insertFileCnt = this.fileDao.insertApplyProjectFile(projectApplyFileVO);

			if (insertFileCnt == 0) {
//				throw new ProjectWriteFailException("시스템 오류로 파일 처리가 불가능합니다. 다시 시도해 주세요.");
			}

			totalInsertedFiles++;
		}

		return insertCount > 0 && totalInsertedFiles > 0;
	}


	@Override
	public ProjectListVO selectAllCardProjectSorted(String orderBy) {

		List<ProjectVO> projects = projectDao.selectAllCardProjectSorted(orderBy);

		ProjectListVO projectList = new ProjectListVO();
		projectList.setProjectList(projects);

		return projectList; 
	}

	@Override
	public ProjectListVO selectForPagination(SearchProjectVO searchProjectVO) {

		List<ProjectVO> projectList = null;
		
		if (searchProjectVO == null) {
			projectList = this.projectDao.selectAllCardProject();
		} else {
			projectList = this.projectDao.selectForPagination(searchProjectVO);
		}
		
		ProjectListVO projectListVO = new ProjectListVO();
		
		projectListVO.setProjectList(projectList);
		
		
		return projectListVO;
	}

	@Override
	public ProjectVO readOneProjectInfo(String pjId) {
		// 해당 프로젝트에 대한 정보를 가져와야함.
		ProjectVO projectVO = this.projectDao.selectProjectInfo(pjId);
		// 해당 프로젝트에 대한 모든 주요 기술 정보를 가져옴.
		List<ProjectSkillVO> projectSkillList = this.projectDao.selectAllProjectSkill(pjId);
		
		// 해당 프로젝트에 대한 모든 산업군 정보를 가져옴.
		List<ProjectIndustryVO> projectIndustryList = this.projectDao.selectAllProjectIndustryInfo(pjId);
		
		projectVO.setProjectIndustryList(projectIndustryList);
		projectVO.setProjectSkillList(projectSkillList);
		
		return projectVO;
	}

	
	/**
	 * 이하 댓글 관련 Service 메서드
	 */
	
	@Override
	public boolean updateDeleteState(String id) {
		return this.projectDao.deleteOneComment(id)>0;
	}

	@Override
	public List<ProjectCommentVO> getPaginationComment(ProjectCommentPaginationVO projectCommentPaginationVO, String id) {
		List<ProjectCommentVO> result;
		if(projectCommentPaginationVO==null) {
			result =  this.projectDao.selectAllComment(id);
		}
		else {
			result = this.projectDao.selectPaginationComment(projectCommentPaginationVO);
		}
		
		return result;
	};


	@Override
	public boolean createNewComment(ProjectCommentWriteVO projectCommentWriteVO) {
		return this.projectDao.insertNewComment(projectCommentWriteVO)>0;
	}

	@Override
	public boolean modifyComment(ProjectCommentModifyVO projectCommentModifyVO) {
		return this.projectDao.updateOneComment(projectCommentModifyVO)>0;
	}

	@Override
	public List<ProjectCommentVO> getAllComment(String id) {
		List<ProjectCommentVO> result = this.projectDao.selectAllComment(id);
		return result ;
	}


	
	@Override
	public boolean updateProjectViewCnt(String pjId) {
		int updateCnt = this.projectDao.updateProjectViewCnt(pjId);
		if(updateCnt == 0) {
//			throw new ProjectWriteFailException("서버상의 이유로 조회수 증가가 불가합니다.");
		}
		return updateCnt > 0;
	}
	
	@Override
	public boolean updateAddtionalRecruitment(ModifyProjectVO modifyProjectVO) {
		// 기본적인 정보 수정.
		int updateCnt = this.projectDao.updateAddtionalRecruitment(modifyProjectVO);
		// 산업정보 수정.
		int updateIndustryCnt = this.projectDao.updateProjectIndustry(modifyProjectVO.getProjectIndustryVO());
		
		if(updateCnt == 0 || updateIndustryCnt == 0) {
//			throw new ProjectWriteFailException("서버상의 이유로 정보 수정이 불가능합니다.");
		}
		return true;
	}
	
	@Override
	public boolean updateProjectApply(ApplyProjectVO applyProjectVO) {
		int updateCnt = this.projectDao.updateProjectApply(applyProjectVO);
		if(updateCnt == 0) {
//			throw new ProjectApplyFailException("프로젝트 지원서를 수정하는 중 서버에서 오류가 발생했습니다.", a);
		}
		return updateCnt > 0;
	}
	
	@Override
	public boolean deleteProjectApply(ApplyProjectVO applyProjectVO) {
		int deleteCnt = this.projectDao.deleteProjectApply(applyProjectVO);
		if(deleteCnt == 0) {
			throw new ProjectDeleteException("지원서를 삭제하는 중 서버상의 이유로 오류가 발생했습니다.", applyProjectVO.getPjId());
		}
		return deleteCnt > 0;
	}

	@Override
	public List<ApplyProjectVO> readAllApplyMember(String pjId, MemberVO memberVO) {
		
		ProjectVO projectVO = this.projectDao.selectProjectInfo(pjId);
		
		if(!projectVO.getOrdrId().equals(memberVO.getEmilAddr())){
			throw new IllegalArgumentException("정보가 일치하지 않습니다.");
		}
		if(!(projectVO.getObtnId()==null)) {
			throw new IllegalArgumentException("지원자 선정을 완료하였습니다.");
		}
		
		return this.projectDao.selectAllApplyMember(pjId);
	}

	@Override
	public boolean updateApplyMember(SelectApplyMemberVO selectApplyMemberVO, MemberVO memberVO) {
		ProjectVO projectVO = this.projectDao.selectProjectInfo(selectApplyMemberVO.getPjId());
		
		if(!projectVO.getOrdrId().equals(memberVO.getEmilAddr())){
			throw new IllegalArgumentException("정보가 일치하지 않습니다.");
		}
		if(!projectVO.getObtnId().isEmpty()) {
			throw new IllegalArgumentException("지원자 선정을 완료하였습니다.");
		}
		
		return this.projectDao.updateProjectApplyMember(selectApplyMemberVO)>0;
	}
	
	/**
	 * 날짜 관련해서 검증을 수행하는 메서드이다.
	 * @param projectDateVO
	 * @return
	 * @throws ParseException 
	 */
	private boolean verifyPrjectDate(ProjectDateVO projectDateVO) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		// 프로젝트 일정 시작일 일정 마감일.
		String startDt = projectDateVO.getStrtDt();
		String endDt = projectDateVO.getEndDt();
		Date startDate = format.parse(startDt);
		Date endDate = format.parse(endDt);
		int compare = endDate.compareTo(startDate);
		if(compare <= 0) {
			return false;
		}
		
		// 프로젝트 모집 일정 시작일 마감일.
		String recuritStartDt = projectDateVO.getPjRcrutStrtDt();
		String recuritEndDt = projectDateVO.getPjRcrutEndDt();
		Date recuritStartDate = format.parse(recuritStartDt);
		Date recuritEndDate = format.parse(recuritEndDt);
		compare = recuritEndDate.compareTo(recuritStartDate);
		
		if(compare <= 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<ProjectSkillVO> readAllProjectSkill(String pjId) {
		return this.projectDao.selectAllProjectSkill(pjId);
	}

	@Override
	public List<ProjectVO> readAllProjectCompanyOrder(MemberVO memberVO) {
		List<ProjectVO> projectList = this.projectDao.selectAllProjectCompanyOrder(memberVO);
	
		// 하나의 프로젝트에 대한 산업 정보를 가져와야함.
		for (ProjectVO projectVO : projectList) {
			// 산업정보 가져옴.
			List<ProjectIndustryVO> projectIndustryVOs = this.projectDao.selectAllProjectIndustryInfo(projectVO.getPjId());
			projectVO.setProjectIndustryList(projectIndustryVOs);
			
			// 주요 기술정보 가져옴. 
			List<ProjectSkillVO> projectSkillVOs = this.projectDao.selectAllProjectSkill(projectVO.getPjId());
			projectVO.setProjectSkillList(projectSkillVOs);
		}
		return projectList;
	}

	@Override
	public List<ApplyProjectVO> readAllApply(MemberVO memberVO) {
		return this.projectDao.selectAllApply(memberVO);
	}

	@Override
	public void insertProjectScrap(ProjectScrapVO projectScrapVO) {
		int isInserted = this.projectDao.insertProjectScrap(projectScrapVO);
		if(isInserted == 0) {
			throw new ProjectScrapException("프로젝트 정보를 스크랩하는 중 오류가 발생했습니다." , projectScrapVO);
		}
	}


	@Override
	public List<ProjectVO> readAllProjectOrderRecipient(MemberVO memberVO) {
		return this.projectDao.selectAllProjectOrderRecipient(memberVO);
	}


	@Override
	public ApplyProjectVO readOneApplyProject(ApplyProjectVO applyProjectVO) {
		applyProjectVO = this.projectDao.selectOneApplyProjectInfo(applyProjectVO);
		System.out.println("지원서 아이디:" + applyProjectVO.getPjApplyId());
		List<ProjectApplyAttVO> projectApplyAttList = this.fileDao.selectAllProjectApplyAtt(applyProjectVO.getPjApplyId());
		applyProjectVO.setProjectApplyAttVOList(projectApplyAttList);
		
		return applyProjectVO;
	}

	@Override
	public ApplyProjectVO findOneApplyProjectWithoutApplyId(ApplyProjectVO applyProjectVO) {
		ApplyProjectVO applyProjectVO2 = this.projectDao.findOneApplyProjectWithoutApplyId(applyProjectVO);
		List<ProjectApplyAttVO> projectApplyAttList = this.fileDao.selectAllProjectApplyAtt(applyProjectVO2.getPjApplyId());
		applyProjectVO2.setProjectApplyAttVOList(projectApplyAttList);
		
		return applyProjectVO2;
	}


}
