package com.app.service.impl;

import java.util.List;

import com.app.entity.BoardSquare;


public interface BoardSquareService {
	BoardSquare createBoardSquare(BoardSquare newBoard); 

	BoardSquare getBoardSquareById(long userId);

	List<BoardSquare> getAllBoardSquares();

	BoardSquare updateBoardSquare(BoardSquare newBoard, long userId);

	void deleteBoardSquare(long userId);
}
