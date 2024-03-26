package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.exception.ProductException;
import com.example.model.Product;
import com.example.request.CreateProductRequest;

public interface ProductService {

	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId)throws ProductException;
	
	public Product updateProduct(Long productId, Product req)throws ProductException;
	
	public Product findProductById(Long id)throws ProductException;
	
	public List<Product>findProductByCategory(String category); // no need to ths method but if we apply filter on get all product then it used
	
	public Page<Product>getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
	// sort for ascending and descending order and page number and page size is for applying pagination
	
	
	
	
}

