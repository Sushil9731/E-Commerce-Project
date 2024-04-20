package com.example.service;



import com.example.exception.CartItemException;
import com.example.exception.UserException;
import com.example.modal.Cart;
import com.example.modal.CartItem;
import com.example.modal.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId, Long id,CartItem cartItem) throws CartItemException, UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size, Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException, UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
}

