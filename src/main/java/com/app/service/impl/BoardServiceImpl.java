package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public Board createBoard(Board board) {
		return boardRepository.save(board);
	}

	@Override
	public Board getBoardById(long id) {
		return boardRepository.findById(id).orElseThrow();
	}

	@Override
	public List<Board> getAllBoards() {
		return boardRepository.findAll();
	}

	@Override
	public Board updateBoard(Board board, long id) {
		Board existingBoard = boardRepository.findById(id).orElseThrow();
		existingBoard.setBoardState(board.getBoardState());
		existingBoard.setFinished(board.isFinished());
		return boardRepository.save(existingBoard);
	}

	@Override
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
}
