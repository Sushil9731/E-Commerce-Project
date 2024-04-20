package com.example.service;


import java.util.List;

import com.example.exception.ProductException;
import com.example.modal.Rating;
import com.example.modal.User;
import com.example.request.RatingRequest;

public interface RatingServices {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);

}

