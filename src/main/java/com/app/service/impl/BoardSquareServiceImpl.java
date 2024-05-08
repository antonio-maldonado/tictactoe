package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.BoardSquare;
import com.app.repository.BoardSquareRepository;

@Service
public class BoardSquareServiceImpl implements BoardSquareService {
	@Autowired
	private BoardSquareRepository boardSquareRepository;
	
	@Override
	public BoardSquare createBoardSquare(BoardSquare newBoard) {
		if(newBoard == null) {
			return null;
		}
		
		return boardSquareRepository.save(newBoard);
	}

	@Override
	public BoardSquare getBoardSquareById(long userId) {
		Optional<BoardSquare> existingBoardSquareOptional = boardSquareRepository.findById(userId);
		
		if(existingBoardSquareOptional.isEmpty()) {
			return null;
		}
		
		return existingBoardSquareOptional.get();
	}

	@Override
	public List<BoardSquare> getAllBoardSquares() {
		return boardSquareRepository.findAll();
	}

	@Override
	public BoardSquare updateBoardSquare(BoardSquare newBoard, long userId) {
		Optional<BoardSquare> existingBoardSquareOptional = boardSquareRepository.findById(userId);
		
		if(existingBoardSquareOptional.isEmpty()) {
			return null;
		}
		
		BoardSquare existingBoardSquare = existingBoardSquareOptional.get();
		existingBoardSquare.setState(newBoard.getState());
		
		return boardSquareRepository.save(existingBoardSquare);
	}

	@Override
	public void deleteBoardSquare(long userId) {
		boardSquareRepository.deleteById(userId);
	}
}
