package com.app.service.impl;

import com.app.entity.Board;

public interface TicTacToeService {
	 public static int[][] WINNING_COMBINATIONS = {
	        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, 
	        {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
	        {0, 4, 8}, {2, 4, 6}             
	    };
	 
	 boolean checkWinner(Board board, int player);
	 
	 Board playMove(Board board, int position, int player);
	 
	 boolean isBoardFull(Board board);
	 
	 Board bestMove(Board board);
}
