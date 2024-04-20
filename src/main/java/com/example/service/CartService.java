package com.example.service;


import com.example.exception.ProductException;
import com.example.modal.Cart;
import com.example.modal.CartItem;
import com.example.modal.User;
import com.example.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public CartItem addCartItem(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);

}

