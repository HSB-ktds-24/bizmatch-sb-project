package com.ktdsuniversity.edu.bizmatch.board.vo;

public class BoardWriteVO {

	
	private String athrId   ;
	private int pstCtgry ;
	
	 
	private String pstNm    ;
	
	private String pstCntnt ;
	private int isPstOpn;
	
	public String getAthrId() {
		return athrId;
	}
	public void setAthrId(String athrId) {
		this.athrId = athrId;
	}
	public int getPstCtgry() {
		return pstCtgry;
	}
	public void setPstCtgry(int pstCtgry) {
		this.pstCtgry = pstCtgry;
	}
	public String getPstNm() {
		return pstNm;
	}
	public void setPstNm(String pstNm) {
		this.pstNm = pstNm;
	}
	public String getPstCntnt() {
		return pstCntnt;
	}
	public void setPstCntnt(String pstCntnt) {
		this.pstCntnt = pstCntnt;
	}
	
	public int getIsPstOpn() {
		return isPstOpn;
	}
	public void setIsPstOpn(int isPstOpn) {
		this.isPstOpn = isPstOpn;
	}
}
