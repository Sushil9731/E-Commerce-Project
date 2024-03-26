package com.example.service;

import org.springframework.stereotype.Service;

import com.example.exception.ProductException;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	
	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImplementation(CartRepository cartRepository,
			CartItemService cartItemService,ProductService productService) 
	{
		this.cartItemService=cartItemService;
		this.cartRepository=cartRepository;
		this.productService=productService;


	}
	@Override
	public Cart createCart(User user) {
		Cart cart=new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepository.findByUserId(userId);
		// find the product
		Product product=productService.findProductById(req.getProductId());
		// check cartItem is present then update otherwise create new cartItem
		CartItem isPresent=cartItemService.isCartItemexist(cart, product, req.getSize(), userId);
		
				if(isPresent==null)
				{
					CartItem cartItem=new CartItem();
					cartItem.setProduct(product);
					cartItem.setCart(cart);
					cartItem.setQuantity(req.getQuantity());
					cartItem.setUserId(userId);
					
					int price=req.getQuantity()*product.getDiscountedPrice();// for set price and the save on database
					cartItem.setPrice(price);
					cartItem.setSize(req.getSize());
					
					CartItem createdCartItem=cartItemService.createCartItem(cartItem);
					cart.getCartItems().add(createdCartItem);
				}
				
				return "Item add to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalItem=0;
		int totalDiscountedPrice=0;
		for(CartItem cartItem:cart.getCartItems())
		{
			totalPrice=totalPrice+cartItem.getPrice();
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			totalItem=totalItem+cartItem.getQuantity();
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart);
	}
	
	
	
}
