package com.app.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.entity.Board;
import com.app.entity.BoardSquare;
import com.app.repository.BoardRepository;
import com.app.service.impl.TicTacToeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TicTacTocServiceTest {
	@Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private TicTacToeServiceImpl ticTacToeService;

    @Test
    public void testPlayMove_ValidMove() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }

        Board updatedBoard = ticTacToeService.playMove(board, 0, 1); // Primer movimiento

        assertEquals(1, updatedBoard.getBoardState().get(0).getState()); // Verificar que el estado es correcto
    }

    @Test
    public void testPlayMove_InvalidMove() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }

        board = ticTacToeService.playMove(board, 0, 1);

        Board result = ticTacToeService.playMove(board, 0, 2);

        assertNotEquals(2, result.getBoardState().get(0).getState()); // Verificar que no se actualizó
    }
    
    @Test
    public void testIsBoardFull() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        Random rand = new Random();
        
        for (int i = 0; i < 9; i++) {
        	int state = rand.nextInt(2)+1;
            board.getBoardState().add(new BoardSquare(0L,state,null));
        }
        
        boolean isFull = ticTacToeService.isBoardFull(board);
        
        assertEquals(true, isFull);
    }
    
    @Test
    public void testIsBoardNotFull() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }
        
        boolean isFull = ticTacToeService.isBoardFull(board);
        
        assertEquals(false, isFull);
    }
    
    @Test
    public void testCheckWinnerX() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }
        
        board.getBoardState().get(0).setState(1);
        board.getBoardState().get(1).setState(2);
        board.getBoardState().get(2).setState(2);
        board.getBoardState().get(3).setState(2);
        board.getBoardState().get(4).setState(1);
        board.getBoardState().get(5).setState(1);
        board.getBoardState().get(6).setState(2);
        board.getBoardState().get(7).setState(1);
        board.getBoardState().get(8).setState(1);
        
        boolean isWinner = ticTacToeService.checkWinner(board, 1);
        
        assertEquals(true, isWinner);
    }
    
    @Test
    public void testCheckWinnerO() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }
        
        board.getBoardState().get(0).setState(2);
        board.getBoardState().get(1).setState(2);
        board.getBoardState().get(2).setState(2);
        board.getBoardState().get(7).setState(1);
        board.getBoardState().get(8).setState(1);
        
        boolean isWinner = ticTacToeService.checkWinner(board, 2);
        
        assertEquals(true, isWinner);
    }
    
    @Test
    public void testCheckNotWinner() {
        Board board = new Board();
        board.setBoardState(new ArrayList<>());
        
        for (int i = 0; i < 9; i++) {
            board.getBoardState().add(new BoardSquare());
        }
        
        board.getBoardState().get(0).setState(1);
        board.getBoardState().get(1).setState(2);
        board.getBoardState().get(2).setState(1);
        board.getBoardState().get(3).setState(1);
        board.getBoardState().get(4).setState(2);
        board.getBoardState().get(5).setState(1);
        board.getBoardState().get(6).setState(2);
        board.getBoardState().get(7).setState(1);
        board.getBoardState().get(8).setState(2);
        
        boolean isWinnerX = ticTacToeService.checkWinner(board, 1);
        boolean isWinnerY = ticTacToeService.checkWinner(board, 2);

        assertEquals(false, isWinnerX);
        assertEquals(false, isWinnerY);
    }
}
