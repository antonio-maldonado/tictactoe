package com.app.service.impl;

import java.util.List;

import com.app.entity.Board;

public interface BoardService {
	Board createBoard(Board board); 

	Board getBoardById(long id);

	List<Board> getAllBoards();

	Board updateBoard(Board board, long id);

	void deleteBoard(Long id);
}
