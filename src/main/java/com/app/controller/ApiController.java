package com.app.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Board;
import com.app.entity.BoardSquare;
import com.app.entity.User;
import com.app.entity.UserDAO;
import com.app.repository.BoardRepository;
import com.app.repository.UserRepository;
import com.app.service.impl.BoardService;
import com.app.service.impl.TicTacToeServiceImpl;
import com.app.service.impl.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@RestController
@RequestMapping("api")
@Tag(name = "API Controller", description = "Operations related to the main API")
public class ApiController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BoardService boardService;
	
    @Autowired
    private TicTacToeServiceImpl ticTacToeService;
    
    @Autowired
    private BoardRepository boardRepository;
    
	@PostMapping("/user")
	public ResponseEntity<?> createUser(@RequestBody User userRequest){
		if(userRequest.getEmail().trim().equals("") || userRequest.getEmail() == null) {
			return ResponseEntity.badRequest().body("Email is required");
		}
		
		if(userRequest.getPassword().trim().equals("") || userRequest.getPassword() == null) {
			return ResponseEntity.badRequest().body("Password is required");
		}
		
		if(userRepository.findByEmail(userRequest.getEmail())!=null) {
			return ResponseEntity.badRequest().body("Email already exists");
		}
		
        User user = userService.createUser(userRequest);
        
		return ResponseEntity.status(201)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new UserDAO(user));
	}
	
	@GetMapping("user")
	public ResponseEntity<List<UserDAO>> getAllUsers(){
		List<User> users= userService.getAllUsers();
		return new ResponseEntity<>(UserDAO.converToDAO(users), HttpStatus.OK);
	}
	
    
	@GetMapping("user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable(name = "userId") long userId){
		if(!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body("User not found");
		}
		
        User user = userService.getUserById(userId);
        
        for(Board board : user.getBoards()) {
        	ticTacToeService.sortBoardSquares(board);
        }
        
		return new ResponseEntity<>(new UserDAO(user), HttpStatus.OK);
	}
	
	@PutMapping("user/{userId}")
	public ResponseEntity<?> updateUserById(@PathVariable(name="userId") long userId,@RequestBody User newUser){
		if(!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body("User not found");
		}
		
        User user = userService.updateUser(newUser,userId);
        
		return new ResponseEntity<>(new UserDAO(user),HttpStatus.OK);
	}
	
	@GetMapping("board")
	public ResponseEntity<List<Board>> getAllBoards(){
		List<Board> boards = boardService.getAllBoards();
		
		return new ResponseEntity<>(boards,HttpStatus.OK);
	}
	
	@GetMapping("board/{boardId}")
	public ResponseEntity<?> getBoardById(@PathVariable(name="boardId") long boardId){
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().body("Board not found"); 
        }
        
		Board board = boardService.getBoardById(boardId);
		
    	ticTacToeService.sortBoardSquares(board);
		
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
	@PutMapping("board/{boardId}")
	public ResponseEntity<?> updateBoardById(@PathVariable(name="boardId") long boardId, @RequestBody Board newBoard){
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().body("Board not found"); 
        }
        
		Board board = boardService.updateBoard(newBoard, boardId);
		
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
	@PostMapping("board")
	public ResponseEntity<?> createBoard(@RequestBody Board newBoard){
		if(newBoard == null) {
            return ResponseEntity.badRequest().body("Board not found"); 

		}
		Board board = boardService.createBoard(newBoard);
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name="boardId") Long boardId) {
        if (!boardRepository.existsById(boardId)) {
            return ResponseEntity.badRequest().build(); 
        }

        boardRepository.deleteById(boardId); 
        return ResponseEntity.noContent().build(); 
    }
	
	@PostMapping("start/{userId}")
    public ResponseEntity<?> startGame(@PathVariable(name="userId") long userId) {
		if(!userRepository.existsById(userId)) {
			return ResponseEntity.badRequest().body("User not found");
		}
		
        Board newBoard = new Board();
        
        newBoard.setDate(new Date());
        newBoard.setName("board");
        
        for (int i = 0; i < 9; i++) {
            newBoard.getBoardState().add(new BoardSquare());
        }
        
        User user = userRepository.findById(userId).orElseThrow();
        newBoard.setUser(user);
        
        Board savedBoard = boardRepository.save(newBoard);
       
        for(BoardSquare bs : savedBoard.getBoardState() ) {
        	bs.setBoard(savedBoard);
        }
        
        savedBoard = boardRepository.save(savedBoard);
        return new ResponseEntity<>(savedBoard,HttpStatus.OK);
    }

    @PostMapping("/play")
    public ResponseEntity<Board> playMove(
        @RequestBody Game boardData) {
        if (!boardRepository.existsById(boardData.getBoardId())) {
            return ResponseEntity.notFound().build(); 
        }

        Board board = boardRepository.findById(boardData.getBoardId()).get();
        
        if (board.getIsFinished()==1) {
            return ResponseEntity.status(409).body(board); 
        }
        
        board = ticTacToeService.playMove(board, boardData.getPosition(), 1);

        board.setName(boardData.getName());
        
        if (board.getIsFinished()==0) {
            board = ticTacToeService.bestMove(board);
        }

        boardRepository.save(board);
        return new ResponseEntity<>(board,HttpStatus.OK);
    }
	
}

@Data
class Game{
	private long boardId;
	private int position;
	private String name;
}
