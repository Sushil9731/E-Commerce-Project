package com.example.service;



import java.util.List;

import com.example.exception.ProductException;
import com.example.modal.Review;
import com.example.modal.User;
import com.example.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReview(Long productId);
	
	
}

