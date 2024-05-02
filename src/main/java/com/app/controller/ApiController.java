package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.app.entity.User;
import com.app.entity.UserDAO;
import com.app.service.impl.BoardService;
import com.app.service.impl.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
//	
//	@Autowired
//	private BoardSquareService boardSquareService;
	                  
	@PostMapping("user")
	public ResponseEntity<UserDAO> createUser(@RequestBody User userRequest){
        UserDAO user= userService.createUser(userRequest);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("user")
	public ResponseEntity<List<UserDAO>> getAllUsers(){
		List<UserDAO> users= userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.CREATED);
	}
	
	@GetMapping("user/{id}")
	public ResponseEntity<UserDAO> getUserById(@PathVariable(name = "id") long id){
        UserDAO user= userService.getUserById(id);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
	
	@PutMapping("user/{id}")
	public ResponseEntity<UserDAO> updateUserById(@PathVariable(name="id") long id,@RequestBody User newUser){
        UserDAO user= userService.updateUser(newUser,id);

		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping("board")
	public ResponseEntity<List<Board>> getAllBoards(){
		List<Board> boards = boardService.getAllBoards();
		return new ResponseEntity<>(boards,HttpStatus.OK);
	}
	
	@GetMapping("board/{id}")
	public ResponseEntity<Board> getBoardById(@PathVariable(name="id") long id){
		Board board = boardService.getBoardById(id);
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
	@PutMapping("board/{id}")
	public ResponseEntity<Board> updateBoardById(@PathVariable(name="id") long id, @RequestBody Board newBoard){
		Board board = boardService.updateBoard(newBoard, id);
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
	@PostMapping("board")
	public ResponseEntity<Board> createBoard(@RequestBody Board newBoard){
		Board board = boardService.createBoard(newBoard);
		return new ResponseEntity<>(board,HttpStatus.OK);
	}
	
	@DeleteMapping("board/{id}")
	public ResponseEntity<String> deleteBoardById(@PathVariable(name="id") long id){
		boardService.deleteBoard(id);
		return new ResponseEntity<>("Board deleted",HttpStatus.OK);
	}
	
	
	
}
