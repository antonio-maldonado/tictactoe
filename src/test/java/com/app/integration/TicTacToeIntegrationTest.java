package com.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.Board;
import com.app.entity.BoardSquare;
import com.app.entity.User;
import com.app.service.impl.BoardService;
import com.app.service.impl.BoardSquareService;
import com.app.service.impl.UserService;

@SpringBootTest
@Transactional
public class TicTacToeIntegrationTest {
	@Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private BoardSquareService boardSquareService;

    @Test
    public void testCreateBoardForUser() {
        User user = new User();
        user.setEmail("user@hotmail.com");
        user.setPassword("12345");

        User savedUser = userService.createUser(user);

        Board board = new Board();
        
        board.setUser(savedUser);

        Board savedBoard = boardService.createBoard(board);

        System.out.println("User ID: "+ savedUser.getId());
        System.out.println("Board user ID: "+ savedBoard.getUser().getId());

        assertNotNull(savedBoard);
        assertNotNull(savedUser);
        assertEquals(savedUser.getId(), savedBoard.getUser().getId());
    }
    
    @Test
    public void testCreateBoardsSquaresForBoard() {
        User user = new User();
        user.setEmail("user@hotmail.com");
        user.setPassword("password");
        
        User savedUser = userService.createUser(user);
        Board board = new Board();
        
        board.setUser(savedUser);
        
        List<BoardSquare> boardSquares = new ArrayList<>();   

        for(int i = 0; i < 9 ; i++) {
        	BoardSquare bs = new BoardSquare();
        	bs.setBoard(board);
        	BoardSquare savedBS = boardSquareService.createBoardSquare(bs);
        	boardSquares.add(savedBS);
        }
        
        board.setBoardState(boardSquares);
        
        Board savedBoard = boardService.createBoard(board);

        System.out.println("Board ID: "+ savedBoard.getId());
        System.out.println("BoardSquare board ID: "+ boardSquares.get(0).getBoard().getId());

        assertEquals(savedBoard.getId(), boardSquares.get(0).getBoard().getId());
    }
    
    @Test
    public void testErrorCreatingUserNull() {
        User user = new User();
        user.setPassword("123");
        User savedUser = userService.createUser(user);
        
        assertNull(savedUser);
    }
    
    @Test
    public void testErrorGettingBoardById() {
    	Board board = boardService.getBoardById(99999L);
    	
    	assertNull(board);
    }
    
    @Test
    public void testErrorDuplicateUser() {
    	 User user = new User();
         user.setEmail("user@hotmail.com");
         user.setPassword("password");
         
         userService.createUser(user);
         
         User repeatedUser = new User();
         user.setEmail("user@hotmail.com");
         user.setPassword("password");
         
         User userRepeated = userService.createUser(repeatedUser);
         
         assertNull(userRepeated);
    }
    
    @Test
    public void testGetUserByEmail() {
    	User existingUser = userService.getUserByEmail("antonio@gmail.com");
    	
    	assertNotNull(existingUser);
    }
    
    @Test
    public void testUpdateUser() {
    	User user = new User();
        user.setEmail("user@hotmail.com");
        user.setPassword("password");
        
        User savedUser = userService.createUser(user);
        
        long userId = savedUser.getId();
        
        User newUser = new User();
        newUser.setEmail("test@hotmail.com");
        newUser.setPassword("password");
        
        User updatedUser = userService.updateUser(newUser, userId);
        
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
    }
}
