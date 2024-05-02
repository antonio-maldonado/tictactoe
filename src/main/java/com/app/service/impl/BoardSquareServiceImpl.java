package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.BoardSquare;
import com.app.repository.BoardSquareRepository;

@Service
public class BoardSquareServiceImpl implements BoardSquareService {
	@Autowired
	private BoardSquareRepository boardSquareRepository;
	
	@Override
	public BoardSquare createBoardSquare(BoardSquare board) {
		return boardSquareRepository.save(board);
	}

	@Override
	public BoardSquare getBoardSquareById(long id) {
		return boardSquareRepository.findById(id).orElseThrow();
	}

	@Override
	public List<BoardSquare> getAllBoardSquares() {
		return boardSquareRepository.findAll();
	}

	@Override
	public BoardSquare updateBoardSquare(BoardSquare board, long id) {
		BoardSquare existingBoardSquare = boardSquareRepository.findById(id).orElseThrow();
		existingBoardSquare.setState(board.getState());
		return boardSquareRepository.save(existingBoardSquare);
	}

	@Override
	public void deleteBoardSquare(Long id) {
		boardSquareRepository.deleteById(id);
		
	}
}
