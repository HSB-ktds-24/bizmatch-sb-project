package com.ktdsuniversity.edu.bizmatch.project.vo;

public class ProjectAttachmentVO {

	private String emilAddr; // 회원 이메일
	
	private String PJ_ATT_ID; // 프로젝트 등록 첨부자료 아이디
	
	private String PJ_ID; // 프로젝트 아이디
	
	private String PJ_ATT_URL; // 프로젝트 등록 첨부자료 원래이름
	
	private String PJ_ATT_URL_NONREAD; // 첨부자료 난독화 파일이름

	
	
	public String getEmilAddr() {
		return emilAddr;
	}

	public void setEmilAddr(String emilAddr) {
		this.emilAddr = emilAddr;
	}

	public String getPJ_ATT_ID() {
		return PJ_ATT_ID;
	}

	public void setPJ_ATT_ID(String pJ_ATT_ID) {
		PJ_ATT_ID = pJ_ATT_ID;
	}

	public String getPJ_ID() {
		return PJ_ID;
	}

	public void setPJ_ID(String pJ_ID) {
		PJ_ID = pJ_ID;
	}

	public String getPJ_ATT_URL() {
		return PJ_ATT_URL;
	}

	public void setPJ_ATT_URL(String pJ_ATT_URL) {
		PJ_ATT_URL = pJ_ATT_URL;
	}

	public String getPJ_ATT_URL_NONREAD() {
		return PJ_ATT_URL_NONREAD;
	}

	public void setPJ_ATT_URL_NONREAD(String pJ_ATT_URL_NONREAD) {
		PJ_ATT_URL_NONREAD = pJ_ATT_URL_NONREAD;
	}
	
	
}
