package com.ktdsuniversity.edu.bizmatch.board.service;

import java.util.List;

import com.ktdsuniversity.edu.bizmatch.board.vo.BoardModifyVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardWriteVO;
import com.ktdsuniversity.edu.bizmatch.common.vo.PaginationVO;

public interface BoardService {
	
	public List<BoardVO> getBoardList(int flag);
	public int getBoardCount();
	
	public BoardVO getOneBoard(String id);
	
	public boolean createNewPost(BoardWriteVO boardWirteVO);
	public boolean doModifyPost(BoardModifyVO modifyVO);
	
	public boolean doIncreaseViews(String id);
	public boolean doDeletePost(String id);
	
	public List<BoardVO> getForPagination(PaginationVO paginationVO);
}
