package com.app.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class UserDAO {
	private Long id;
	private String email;
	private List<Board> boards = new ArrayList<>();
	
	public UserDAO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.boards = user.getBoards();
	}
	
	public static List<UserDAO> converToDAO (List<User> users) {
		List<UserDAO> usersDAO = users.stream()
				.map(user->new UserDAO(user))
				.collect(Collectors.toList());
		return usersDAO;
		
	}
	
	
}

