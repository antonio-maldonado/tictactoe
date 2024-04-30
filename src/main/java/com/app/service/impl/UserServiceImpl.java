package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.entity.User;
import com.app.entity.UserDAO;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder ;

	
	@Override
	public UserDAO createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return new UserDAO(userRepository.save(user));
	}

	@Override
	public UserDAO getUserById(long id) {
		return new UserDAO(userRepository.findById(id).orElseThrow());
	}

	@Override
	public List<UserDAO> getAllUsers() {
		return UserDAO.converToDAO(userRepository.findAll());
	}

	@Override
	public UserDAO updateUser(User user, long id) {
		User existingUser = userRepository.findById(id).orElseThrow();
		existingUser.setBoards(user.getBoards());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return new UserDAO(userRepository.save(existingUser));
	}

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
	
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<Board> getBoardsByUserId(long id) {
		UserDAO existingUser = getUserById(id);	
		
		return existingUser.getBoards();
	}

}
