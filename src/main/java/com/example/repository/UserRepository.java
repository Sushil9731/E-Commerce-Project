package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	

	// using naming convention if we want to find something then we have to write findBy(name what we want to fine)
	public User findByEmail(String email);
}
