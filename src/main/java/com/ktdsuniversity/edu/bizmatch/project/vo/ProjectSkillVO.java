package com.ktdsuniversity.edu.bizmatch.project.vo;



import java.util.List;

import com.ktdsuniversity.edu.bizmatch.member.vo.PrmStkVO;

public class ProjectSkillVO {
 
	private String pjPrmStkId; // 프로젝트 주요 기술 아이디
	private String pjId;
	private String prmStkId;
	private PrmStkVO prmStkVO;
	private List<ProjectSkillVO> skillList; // 보유기술 리스트.
	
	public String getPjPrmStkId() {
		return pjPrmStkId;
	}
	public void setPjPrmStkId(String pjPrmStkId) {
		this.pjPrmStkId = pjPrmStkId;
	}
	public String getPjId() {
		return pjId;
	}
	public void setPjId(String pjId) {
		this.pjId = pjId;
	}
	public String getPrmStkId() {
		return prmStkId;
	}
	public void setPrmStkId(String prmStkId) {
		this.prmStkId = prmStkId;
	}
	public PrmStkVO getPrmStkVO() {
		return prmStkVO;
	}
	public void setPrmStkVO(PrmStkVO prmStkVO) {
		this.prmStkVO = prmStkVO;
	}

	public List<ProjectSkillVO> getSkillList() {
		return skillList;
	}
	
	public void setSkillList(List<ProjectSkillVO> skillList) {
		this.skillList = skillList;
	}
	
}
