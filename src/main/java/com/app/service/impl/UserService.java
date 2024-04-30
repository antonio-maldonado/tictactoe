package com.app.service.impl;

import java.util.List;

import com.app.entity.Board;
import com.app.entity.User;
import com.app.entity.UserDAO;

public interface UserService {
	UserDAO createUser(User user); 

	UserDAO getUserById(long id);

	List<UserDAO> getAllUsers();

	UserDAO updateUser(User user, long id);

	User getUserByEmail(String email);

	List<Board> getBoardsByUserId(long id);
	
	void deleteUser(Long id);
}
