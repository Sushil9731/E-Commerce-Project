package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.OrderException;
import com.example.model.Address;
import com.example.model.Order;
import com.example.model.User;
import com.example.repository.CartRepository;

@Service
public class OrderServiceImplementation implements OrderService {

	//making instance
	private CartRepository cartRepository;
	private CartService cartItemService;
	private ProductService productService;
	
	
	public OrderServiceImplementation(CartRepository cartRepository,
			CartService cartItemService,ProductService productService) {
	
		
		this.cartItemService=cartItemService;
		this.cartRepository=cartRepository;
		this.productService=productService;
	}
	
	
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		
		return null;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		
		return null;
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
		
		return null;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
	
		return null;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		
		return null;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {


		return null;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		
		return null;
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		
		return null;
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		
		
	}

	
	
}
