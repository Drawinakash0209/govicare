package com.govicare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.govicare.entity.User;
import com.govicare.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	public User registerUser(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("FARMER");
		return userRepository.save(user);
		
	}
	
	
	public User findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

}
