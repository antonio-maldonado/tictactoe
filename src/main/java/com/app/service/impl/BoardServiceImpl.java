package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public Board createBoard(Board newBoard) {
		if(newBoard == null) {
			return null;
		}
		
		return boardRepository.save(newBoard);
	}

	@Override
	public Board getBoardById(long userId) {
		Optional<Board> existingBoard =  boardRepository.findById(userId);
		
		if(existingBoard.isEmpty()) {
			return null;
		}
		
		return existingBoard.get();
	}

	@Override
	public List<Board> getAllBoards() {
		return boardRepository.findAll();
	}

	@Override
	public Board updateBoard(Board newBoard, long userId) {
		Optional<Board> existingBoardOptional = boardRepository.findById(userId);
		
		if(existingBoardOptional.isEmpty()) {
			return null;
		}
		
		Board existingBoard = existingBoardOptional.get();
		
		existingBoard.setBoardState(newBoard.getBoardState());
		existingBoard.setIsFinished(newBoard.getIsFinished());
		
		return boardRepository.save(existingBoard);
	}

	@Override
	public void deleteBoard(long userId) {
		boardRepository.deleteById(userId);
	}
}
