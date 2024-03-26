package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	
	// if userId is match then return cart
	// by passing userid we can access his cart
	@Query("SELECT c FROM cart c WHERE c.user.id=:userId")
	public Cart findByUserId(@Param("userId")Long userId);
}
