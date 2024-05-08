package com.app.service.impl;

import java.util.List;

import com.app.entity.Board;
import com.app.entity.User;

public interface UserService {
	User createUser(User newUser); 

	User getUserById(long userId);

	List<User> getAllUsers();

	User updateUser(User newUser, long userId);

	User getUserByEmail(String email);

	List<Board> getBoardsByUserId(long userId);
	
	void deleteUser(Long userId);
}
