package com.app.service.impl;

import java.util.List;

import com.app.entity.BoardSquare;


public interface BoardSquareService {
	BoardSquare createBoardSquare(BoardSquare board); 

	BoardSquare getBoardSquareById(long id);

	List<BoardSquare> getAllBoardSquares();

	BoardSquare updateBoardSquare(BoardSquare board, long id);

	void deleteBoardSquare(Long id);
}
