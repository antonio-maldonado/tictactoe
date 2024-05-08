package com.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Board;
import com.app.entity.User;
import com.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder ;

	
	@Override
	public User createUser(User newUser) {
		if(newUser == null || newUser.getEmail() == null || newUser.getPassword() == null) {
			return null;
		}
		
		if(userRepository.findByEmail(newUser.getEmail()) != null) {
			return null;
		}
		
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}

	@Override
	public User getUserById(long userId) {
		Optional<User> existingUser =  userRepository.findById(userId);
		
		if(existingUser.isEmpty()) {
			return null;
		}		
		
		return existingUser.get();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User updateUser(User newUser, long userId) {
		if(newUser == null) {
			return null;
		}
		
		Optional<User> existingUserOptional =  userRepository.findById(userId);
		
		if(existingUserOptional.isEmpty()) {
			return null;
		}		
		
		User existingUser = existingUserOptional.get();
		
		existingUser.setBoards(newUser.getBoards());
		existingUser.setEmail(newUser.getEmail());
		existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		
		return userRepository.save(existingUser);
	}

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
	
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public List<Board> getBoardsByUserId(long userId) {
		User existingUser = getUserById(userId);	
		
		if(existingUser == null) {
			return null;
		}
		
		return existingUser.getBoards();
	}

}
