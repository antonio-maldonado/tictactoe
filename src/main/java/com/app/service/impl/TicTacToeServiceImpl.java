package com.app.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.entity.BoardSquare;

@Service
public class TicTacToeServiceImpl implements TicTacToeService{;
	
	@Override
	public boolean checkWinner(Board board, int player) {
		List<BoardSquare> squares = board.getBoardState();
		
        for (int[] combination : WINNING_COMBINATIONS) {
            if (squares.get(combination[0]).getState() == player &&
                squares.get(combination[1]).getState() == player &&
                squares.get(combination[2]).getState() == player) {
                return true;
            }
        }
        return false;
	}

	@Override
	public Board playMove(Board board, int position, int player) {
		List<BoardSquare> squares = board.getBoardState();
		
        if (squares.get(position).getState() == 0) {
            squares.get(position).setState(player);
        }

        boolean gameFinished = checkWinner(board, player) || isBoardFull(board);
        if (gameFinished) {
            board.setFinished(true);
        }

        return board;
	}

	@Override
	public boolean isBoardFull(Board board) {
		for (BoardSquare square : board.getBoardState()) {
            if (square.getState() == 0) {
                return false;
            }
        }
        return true;
	}

	@Override
	public Board bestMove(Board board) {
		List<BoardSquare> squares = board.getBoardState();
        Random rand = new Random();
        while (true) {
            int position = rand.nextInt(9);
            if (squares.get(position).getState() == 0) {
                squares.get(position).setState(2); 
                break;
            }
        }
        
        return board;
	}

}
