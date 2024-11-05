package com.ktdsuniversity.edu.bizmatch.project.vo;

import java.util.List;

import com.ktdsuniversity.edu.bizmatch.comment.vo.CommentVO;
import com.ktdsuniversity.edu.bizmatch.member.vo.MemberVO;
import com.ktdsuniversity.edu.bizmatch.payment.vo.PaymentVO;

public class ProjectVO {

	private String pjId; // 프로젝트 아이디
	private String ordrId; // 발주자 아이디
	private String obtnId; // 수주자 아이디
	private String pjTtl; // 프로젝트 제목
	private String rgstrDt; // 프로젝트 등록일
	private String strtDt; // 프로젝트 시작일
	private String endDt; // 프로젝트 종료일
	private int cntrctAccnt; // 프로젝트 입찰가격
	private String pjDesc; // 프로젝트 상세설명
	private int isDlt; // 프로젝트 게시글 삭제여부
	private String dltDt; // 프로젝트 게시글 삭제일
	private int isRcrutAdd; // 프로젝트 추가모집 여부
	private int pjStt; // 프로젝트 상태
	private int viewCnt; // 조회수
	private int pjRcrutCnt; // 프로젝트 모집인원
	private String pjRcrutStrtDt; // 프로젝트 모집 시작일
	private String pjRcrutEndDt; // 프로젝트 모집 종료일
	private MemberVO memberVO; // 프로젝트 등록을 작성한 회원의 정보
	private List<CommentVO> projectCommentList; // 프로젝트에 작성된 댓글들 리스트
	private List<ProjectSkillVO> projectSkillList; // 해당 프로젝트의 보유 기술 목록
	private List<ProjectIndustryVO> projectIndustryList; // 해당 프로젝트의 산업군 목록.
	private PaymentVO paymentVO ; // 결제 객체 
	
	// getter and setter
	
	public List<ProjectIndustryVO> getProjectIndustryList() {
		return projectIndustryList;
	}
	
	public PaymentVO getPaymentVO() {
		return paymentVO;
	}

	public void setPaymentVO(PaymentVO paymentVO) {
		this.paymentVO = paymentVO;
	}

	public void setProjectIndustryList(List<ProjectIndustryVO> projectIndustryList) {
		this.projectIndustryList = projectIndustryList;
	}
	
	public List<ProjectSkillVO> getProjectSkillList() {
		return projectSkillList;
	}

	public void setProjectSkillList(List<ProjectSkillVO> projectSkillList) {
		this.projectSkillList = projectSkillList;
	}

	public List<CommentVO> getProjectCommentList() {
		return projectCommentList;
	}

	public void setProjectCommentList(List<CommentVO> projectCommentList) {
		this.projectCommentList = projectCommentList;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;  
	}

	public String getPjId() {
		return pjId;
	}

	public void setPjId(String pjId) {
		this.pjId = pjId;
	}

	public String getOrdrId() {
		return ordrId;
	}

	public void setOrdrId(String ordrId) {
		this.ordrId = ordrId;
	}

	public String getObtnId() {
		return obtnId;
	}

	public void setObtnId(String obtnId) {
		this.obtnId = obtnId;
	}

	public String getPjTtl() {
		return pjTtl;
	}

	public void setPjTtl(String pjTtl) {
		this.pjTtl = pjTtl;
	}

	public String getRgstrDt() {
		return rgstrDt;
	}

	public void setRgstrDt(String rgstrDt) {
		this.rgstrDt = rgstrDt;
	}

	public String getStrtDt() {
		return strtDt;
	}

	public void setStrtDt(String strtDt) {
		this.strtDt = strtDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public int getCntrctAccnt() {
		return cntrctAccnt;
	}

	public void setCntrctAccnt(int cntrctAccnt) {
		this.cntrctAccnt = cntrctAccnt;
	}

	public String getPjDesc() {
		return pjDesc;
	}

	public void setPjDesc(String pjDesc) {
		this.pjDesc = pjDesc;
	}

	public int getIsDlt() {
		return isDlt;
	}

	public void setIsDlt(int isDlt) {
		this.isDlt = isDlt;
	}

	public String getDltDt() {
		return dltDt;
	}

	public void setDltDt(String dltDt) {
		this.dltDt = dltDt;
	}

	public int getIsRcrutAdd() {
		return isRcrutAdd;
	}

	public void setIsRcrutAdd(int isRcrutAdd) {
		this.isRcrutAdd = isRcrutAdd;
	}

	public int getPjStt() {
		return pjStt;
	}

	public void setPjStt(int pjStt) {
		this.pjStt = pjStt;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getPjRcrutCnt() {
		return pjRcrutCnt;
	}

	public void setPjRcrutCnt(int pjRcrutCnt) {
		this.pjRcrutCnt = pjRcrutCnt;
	}

	public String getPjRcrutStrtDt() {
		return pjRcrutStrtDt;
	}

	public void setPjRcrutStrtDt(String pjRcrutStrtDt) {
		this.pjRcrutStrtDt = pjRcrutStrtDt;
	}

	public String getPjRcrutEndDt() {
		return pjRcrutEndDt;
	}

	public void setPjRcrutEndDt(String pjRcrutEndDt) {
		this.pjRcrutEndDt = pjRcrutEndDt;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("this.ordrId" + this.ordrId + "\n");
		sb.append("this.cntrctAccnt" + this.cntrctAccnt + "\n");
		sb.append("this.pjTtl" + this.pjTtl + "\n");
		sb.append("this.rgstrDt" + this.rgstrDt + "\n");
		return sb.toString();
	}

}
