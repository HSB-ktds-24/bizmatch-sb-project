package com.ktdsuniversity.edu.bizmatch.project.vo;

import com.ktdsuniversity.edu.bizmatch.common.vo.IndstrInfoVO;

public class ProjectIndustryVO {
	
	private String pjIndstrId; // 프로젝트 산업군의 아이디.
	private String pjId; // 프로젝트 아이디.
	private String indstrId; // 산업군 아이디.
	private IndstrInfoVO indstrInfoVO; // 산업군 정보.
	
	// getter and setter
	public String getPjIndstrId() {
		return pjIndstrId;
	}
	public void setPjIndstrId(String pjIndstrId) {
		this.pjIndstrId = pjIndstrId;
	}
	public String getPjId() {
		return pjId;
	}
	public void setPjId(String pjId) {
		this.pjId = pjId;
	}
	public String getIndstrId() {
		return indstrId;
	}
	public void setIndstrId(String indstrId) {
		this.indstrId = indstrId;
	}
	public IndstrInfoVO getIndstrInfoVO() {
		return indstrInfoVO;
	}
	public void setIndstrInfoVO(IndstrInfoVO indstrInfoVO) {
		this.indstrInfoVO = indstrInfoVO;
	}
}
