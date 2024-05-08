package com.app.service.impl;

import java.util.List;

import com.app.entity.Board;

public interface BoardService {
	Board createBoard(Board newBoard); 

	Board getBoardById(long boardId);

	List<Board> getAllBoards();

	Board updateBoard(Board newBoard, long boardId);

	void deleteBoard(long boardId);
}
