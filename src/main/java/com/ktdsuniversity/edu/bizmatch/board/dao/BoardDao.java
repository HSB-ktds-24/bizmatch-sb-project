package com.ktdsuniversity.edu.bizmatch.board.dao;

import java.util.List;

import com.ktdsuniversity.edu.bizmatch.board.vo.BoardModifyVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardWriteVO;
import com.ktdsuniversity.edu.bizmatch.common.vo.PaginationVO;

public interface BoardDao {

	static String NAMESPACE = "com.ktdsuniversity.edu.bizmatch.board.dao.BoardDao";
	
	public List<BoardVO> selectBoardList(int flag);
	public int selectBoardCount( );
	public BoardVO selectOneBoard(String id);
	
	public int insertPost(BoardWriteVO boardWirteVO);
	public int updateModifyPost(BoardModifyVO modifyVO);
	
	public int updateIncreaseViews(String id);
	public int updateDeletePost(String id);
	
	public List<BoardVO> selectForPagination (PaginationVO paginationVO);
}
