package com.govicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govicare.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username); 

}
