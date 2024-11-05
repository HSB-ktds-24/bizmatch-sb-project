package com.ktdsuniversity.edu.bizmatch.project.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.PrmStkVO;
import com.ktdsuniversity.edu.bizmatch.project.dao.ProjectDao;
import com.ktdsuniversity.edu.bizmatch.project.vo.ApplyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ModifyProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectIndustryVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectScrapVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectSkillVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.ProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SearchProjectVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.SelectApplyMemberVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.UpdateProjectSttVO;
import com.ktdsuniversity.edu.bizmatch.project.vo.WriteProjectVO;

@Repository
public class ProjectDaoImpl extends SqlSessionDaoSupport implements ProjectDao {

	@Autowired
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int insertOneProject(WriteProjectVO writeProjectVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertOneProject", writeProjectVO);
	}

	@Override
	public int updateOneProject(ModifyProjectVO modifyProjectVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateOneProject", modifyProjectVO);
	}

	@Override
	public int deleteOneProject(String pjId) {
		return this.getSqlSession().delete(NAMESPACE + ".deleteOneProject", pjId);
	}

	@Override
	public List<ProjectVO> selectAllCardProject(SearchProjectVO searchProjectVO) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllCardProject", searchProjectVO);
	}

	@Override
	public int selectProjectAllCount(SearchProjectVO searchProjectVO) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectProjectAllCount", searchProjectVO);
	}

	@Override
	public List<ProjectVO> selectAllCardProject() {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllCardProject");
	}

	@Override
	public int insertOneProjectApply(ApplyProjectVO applyProjectVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertOneProjectApply", applyProjectVO);
	}

	@Override
	public List<ProjectVO> selectAllCardProjectSorted(String orderBy) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllCardProjectSorted", orderBy);
	}

	@Override
	public List<ProjectVO> selectForPagination(SearchProjectVO searchProjectVO) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectForPagination", searchProjectVO);
	}

	@Override
	public ProjectVO selectOneProjectInfo(String email) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectOneProjectInfo", email);
	}

	@Override
	public int updateOneProjectStt(UpdateProjectSttVO updateProjectSttVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateOneProjectStt", updateProjectSttVO);
	}

	@Override
	public ProjectVO selectProjectInfo(String pjId) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectProjectInfo", pjId);

	}

	@Override
	public int updateProjectViewCnt(String pjId) {
		return this.getSqlSession().update(NAMESPACE + ".updateProjectViewCnt", pjId);
	}

	@Override
	public int updateProjectIndustry(ProjectIndustryVO projectIndustryVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateProjectIndustry", projectIndustryVO);
	}

	@Override
	public int updateAddtionalRecruitment(ModifyProjectVO modifyProjectVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateAddtionalRecruitment", modifyProjectVO);
	}

	@Override
	public int updateProjectApply(ApplyProjectVO applyProjectVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateProjectApply", applyProjectVO);
	}

	@Override
	public int deleteProjectApply(ApplyProjectVO applyProjectVO) {
		return this.getSqlSession().delete(NAMESPACE + ".deleteProjectApply", applyProjectVO);
	}

	@Override
	public List<ApplyProjectVO> selectAllApplyMember(String pjId) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllApplyMember", pjId);
	}

	@Override
	public int updateProjectApplyMember(SelectApplyMemberVO selectApplyMemberVO) {
		return this.getSqlSession().update(NAMESPACE + ".updateProjectApplyMember", selectApplyMemberVO);
	}

	public List<ProjectVO> selectAllProjectCompanyOrder(MemberVO memberVO) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllProjectCompanyOrder", memberVO);
	}

	@Override
	public List<ProjectVO> selectAllProjectOrderRecipient(MemberVO memberVO) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllProjectOrderRecipient", memberVO);
	}

	@Override
	public List<ApplyProjectVO> selectAllApply(MemberVO memberVO) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllApply", memberVO);
	}

	@Override
	public int insertProjectScrap(ProjectScrapVO projectScrapVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertProjectScrap", projectScrapVO);
	}

	@Override
	public List<ProjectIndustryVO> selectAllProjectIndustryInfo(String pjId) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllProjectIndustryInfo", pjId);
	}

	@Override
	public List<ProjectSkillVO> selectAllProjectSkill(String pjId) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllProjectSkill", pjId);
	}

	@Override
	public List<PrmStkVO> selectAllProjectSkillList() {
		return this.getSqlSession().selectList(NAMESPACE + ".selectAllProjectSkillList");
	}

	@Override
	public int insertProjectSkills(ProjectSkillVO projectSkillVO) {
		return this.getSqlSession().insert(NAMESPACE + ".insertProjectSkills", projectSkillVO);
	}

	
	@Override
	public ApplyProjectVO selectOneApplyProjectInfo(ApplyProjectVO applyProjectVO) {
		return this.getSqlSession().selectOne(NAMESPACE + ".selectOneApplyProjectInfo", applyProjectVO);
	}
}
