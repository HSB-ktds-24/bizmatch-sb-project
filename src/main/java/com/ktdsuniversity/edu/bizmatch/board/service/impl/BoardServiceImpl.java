package com.ktdsuniversity.edu.bizmatch.board.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.bizmatch.board.dao.BoardDao;
import com.ktdsuniversity.edu.bizmatch.board.service.BoardService;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardModifyVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardVO;
import com.ktdsuniversity.edu.bizmatch.board.vo.BoardWriteVO;
import com.ktdsuniversity.edu.bizmatch.comment.web.CommentController;
import com.ktdsuniversity.edu.bizmatch.common.vo.PaginationVO;

@Service
public class BoardServiceImpl implements BoardService {
	public static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public List<BoardVO> getBoardList(int flag) {
		List<BoardVO> result = boardDao.selectBoardList(flag);
		return result;
	}

	@Override
	public int getBoardCount( ) {
		int count = boardDao.selectBoardCount();
		return count;
	}

	@Override
	public BoardVO getOneBoard(String id) {
		BoardVO result = boardDao.selectOneBoard(id);
		return result;
	}

	@Override
	public boolean createNewPost(BoardWriteVO boardWirteVO) {
		return boardDao.insertPost(boardWirteVO)>0;
	}

	@Override
	public boolean doModifyPost(BoardModifyVO modifyVO) {
		return boardDao.updateModifyPost(modifyVO)>0;
	}

	@Override
	public boolean doIncreaseViews(String id) {
		return boardDao.updateIncreaseViews(id)>0;
	}

	@Override
	public boolean doDeletePost(String id) {
		return boardDao.updateDeletePost(id)>0;
	}

	@Override
	public List<BoardVO> getForPagination(PaginationVO paginationVO) {
		List<BoardVO> result ;

		if(paginationVO == null) {
			result = boardDao.selectBoardList(0);
		}
		else {
			result = boardDao.selectForPagination(paginationVO);
		}
		
		return result;
	}

}
