package com.ktdsuniversity.edu.bizmatch.board.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.bizmatch.board.dao.BoardDao;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardModifyVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardWriteVO;
import com.ktdsuniversity.edu.bizmatch.common.vo.PaginationVO;

@Repository
public class BoardDaoImpl extends SqlSessionDaoSupport implements BoardDao{

	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<BoardVO> selectBoardList(int flag) {
		return getSqlSessionTemplate().selectList(NAMESPACE +".selectBoardList", flag);
	}

	@Override
	public int selectBoardCount( ) {
		return getSqlSessionTemplate().selectOne(NAMESPACE+ ".selectBoardCount");
	}

	@Override
	public BoardVO selectOneBoard(String id) {
		return getSqlSessionTemplate().selectOne(NAMESPACE+".selectOneBoard",id);
	}

	@Override
	public int insertPost(BoardWriteVO boardWirteVO) {
		return getSqlSessionTemplate().insert(NAMESPACE+".insertPost", boardWirteVO);
	}

	@Override
	public int updateModifyPost(BoardModifyVO modifyVO) {
		return getSqlSessionTemplate().update(NAMESPACE+".updateModifyPost", modifyVO);
	}

	@Override
	public int updateIncreaseViews(String id) {
		return getSqlSessionTemplate().update(NAMESPACE+".updateIncreaseViews", id);
	}

	@Override
	public int updateDeletePost(String id) {
		return getSqlSessionTemplate().update(NAMESPACE+".updateDeletePost", id);
	}

	@Override
	public List<BoardVO> selectForPagination(PaginationVO paginationVO) {
		return getSqlSessionTemplate().selectList(NAMESPACE+".selectForPagination",paginationVO);
	}
	

}
