package com.ktdsuniversity.edu.bizmatch.member.vo;

public class MemberModifyVO {
	private String emilAddr;
	private String mbrNm;
	private String newEmilAddr;
	private String mbrPhnNum; // 이용자 전화번호
	private String emilAddrCnfrmNmbr;
	
	
	public String getEmilAddrCnfrmNmbr() {
		return emilAddrCnfrmNmbr;
	}
	public void setEmilAddrCnfrmNmbr(String emilAddrCnfrmNmbr) {
		this.emilAddrCnfrmNmbr = emilAddrCnfrmNmbr;
	}
	public String getEmilAddr() {
		return emilAddr;
	}
	public void setEmilAddr(String emilAddr) {
		this.emilAddr = emilAddr;
	}
	public String getMbrNm() {
		return mbrNm;
	}
	public void setMbrNm(String mbrNm) {
		this.mbrNm = mbrNm;
	}
	public String getNewEmilAddr() {
		return newEmilAddr;
	}
	public void setNewEmilAddr(String newEmilAddr) {
		this.newEmilAddr = newEmilAddr;
	}
	public String getMbrPhnNum() {
		return mbrPhnNum;
	}
	public void setMbrPhnNum(String mbrPhnNum) {
		this.mbrPhnNum = mbrPhnNum;
	}
	
	
}
