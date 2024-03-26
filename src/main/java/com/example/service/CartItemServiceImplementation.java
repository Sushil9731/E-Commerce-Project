package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.exception.CartItemException;
import com.example.exception.UserException;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;

import jakarta.persistence.Id;

@Service
public class CartItemServiceImplementation implements CartItemService{

	
	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	public CartItemServiceImplementation(CartItemRepository cartItemRepository,
			UserService userService,CartRepository cartRepository) 
	{
		this.cartItemRepository=cartItemRepository;
		this.cartRepository=cartRepository;
		this.userService=userService;
	}
	
	
	
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);   //5*100=500 price
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId))
		{
	
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
			
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemexist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem=findCartItemById(cartItemId);
		User user=userService.findUserById(cartItem.getUserId());// find cartItem and user
		User reqUser=userService.findUserById(userId);//req user is here
		if(user.getId().equals(reqUser.getId()))
		{
			cartItemRepository.deleteById(cartItemId);
		}
		else
		{
			throw new UserException("you can't remove another user item");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepository.findById(cartItemId);
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id:"+cartItemId);
	}

}
