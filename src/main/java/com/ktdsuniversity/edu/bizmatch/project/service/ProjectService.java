package com.ktdsuniversity.edu.bizmatch.project.service;


import java.util.List;


import java.text.ParseException;
import java.util.List;

import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;

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

public interface ProjectService {
	
	/**
	 * 새로운 프로젝트를 생성하는 메서드.
	 * @param writeProjectVO
	 * @return
	 * @throws ParseException 
	 */
	public boolean createNewProject(WriteProjectVO writeProjectVO) throws ParseException;
	
	/**
	 * 하나의 프로젝트 정보를 수정하는 메서드.
	 * @param modifyProjectVO
	 * @return
	 */
	public boolean updateOneProject(ModifyProjectVO modifyProjectVO);
	
	/**
	 * 하나의 프로젝트를 삭제하는 메서드.
	 * @param pjId
	 * @return
	 */
	public boolean deleteOneProject(String pjId);
	
	/**
	 * 
	 * @param searchProjectVO
	 * @return
	 */
	public ProjectListVO selectAllCardProject(SearchProjectVO searchProjectVO);
	
	/**
	 * 프로젝트 지원을 수행하는 메서드.
	 * @param applyProjectVO
	 * @return
	 */
	public boolean createNewProjectApply(ApplyProjectVO applyProjectVO);
	
	/**
	 * 정렬해서 불러오는거 프로젝트.
	 * @param orderBy
	 * @return
	 */
	public ProjectListVO selectAllCardProjectSorted(String orderBy);
	
	/**
	 * 페이지네이션 메서드.
	 * @param searchProjectVO
	 * @return
	 */
	public ProjectListVO selectForPagination (SearchProjectVO searchProjectVO);
	
	/**
	 * 하나의 프로젝트에 대한 정보를 가져오는 메서드.
	 * @param pjId
	 * @return
	 */
	public ProjectVO readOneProjectInfo(String pjId);
	
	/**
<<<<<<< HEAD
	 * 이하 댓글 관련 Service 메서드
	 * @param id
	 * @return
	 */
	public boolean updateDeleteState(String id);
	public List<ProjectCommentVO> getPaginationComment(ProjectCommentPaginationVO projectCommentPaginationVO,String id);
	public boolean createNewComment(ProjectCommentWriteVO projectCommentWriteVO);
	public boolean modifyComment(ProjectCommentModifyVO projectCommentModifyVO);
	public List<ProjectCommentVO> getAllComment(String id);
	/*
	 * 프로젝트 게시글 조회수 증가시켜주는 메서드.
	 * @param pjId : 프로젝트 아이디.
	 * @return : 조회수 증가 성공 여부.
	 */
	public boolean updateProjectViewCnt(String pjId);
	
	/**
	 * 추가모집시 프로젝트 정보를 수정하는 메소드.
	 * @param modifyProjectVO : 수정하는 정보를 담은 객체.
	 * @return : 수정여부.
	 */
	public boolean updateAddtionalRecruitment(ModifyProjectVO modifyProjectVO);
	
	/**
	 * 프로젝트 지원 내용을 수정하는 메서드.
	 * @param applyProjectVO
	 * @return
	 */
	public boolean updateProjectApply(ApplyProjectVO applyProjectVO);
	
	/**
	 * 특정 프로젝트의 지원서를 삭제하는 메서드.
	 * @param applyProjectVO
	 * @return
	 */
	public boolean deleteProjectApply(ApplyProjectVO applyProjectVO);
	
	/**
	 * 지원자 모두를 불러오는 메소드
	 * @param pjId
	 * @param memberVO
	 * @return
	 */
	public List<ApplyProjectVO> readAllApplyMember(String pjId, MemberVO memberVO);
	
	/**
	 * 
	 * @param selectApplyMemberVO
	 * @param memberVO
	 * @return
	 */
	public boolean updateApplyMember(SelectApplyMemberVO selectApplyMemberVO, MemberVO memberVO);
	
	/**
	 * 특정 프로젝트의 필요한 모든 주요 기술들을 조회하는 메서드.
	 * @param pjId
	 * @return
	 */
	public List<ProjectSkillVO> readAllProjectSkill(String pjId);

	/**
	 * 하나의 기업이 발주했던 모든 프로젝트 검색
	 * @param cmpId : 회사 아이디.
	 * @return
	 */
	public List<ProjectVO> readAllProjectCompanyOrder(MemberVO memberVO);

	/**
	 * 회원이 수행했던 모든 프로젝트 검색
	 * 기업이랑 회원이랑 상관 없음 Mapper 에다가 기업이랑 회원 if문으로 제어해둠
	 * @param memberVO
	 * @return
	 */
	public List<ProjectVO> readAllProjectOrderRecipient(MemberVO memberVO);
	
	/**
	 * 지원한 지원서 전부 가져오기
	 * @param memberVO
	 * @return
	 */
	public List<ApplyProjectVO> readAllApply(MemberVO memberVO);
	
	/**
	 * 새로운 프로젝트 스크랩 정보를 추가하는 메서드.
	 * @param projectScrapVO : 스크랩하는 프로젝트의 정보를 담은 객체.
	 */
	public void insertProjectScrap(ProjectScrapVO projectScrapVO);
	
	/**
	 * 한 프로젝트에 대한 특정한 지원서의 정보를 조회하는 메서드이다.
	 * @param applyProjectVO
	 * @return
	 */
	public ApplyProjectVO readOneApplyProject(ApplyProjectVO applyProjectVO);
	
	public ApplyProjectVO findOneApplyProjectWithoutApplyId(ApplyProjectVO applyProjectVO);
	
}
