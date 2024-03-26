package com.example.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ProductException;
import com.example.model.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	//instance
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>>findProductByCategoryHandler(@RequestParam
			String category,@RequestParam List<String>color,@RequestParam List<String>size,
			@RequestParam Integer minPrice,@RequestParam Integer maxprice,@RequestParam Integer minDiscount,
			@RequestParam String sort,@RequestParam String stock,@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
	{
		Page<Product> res=productService.getAllProduct(category, color, 
				size, minPrice, maxprice, minDiscount, 
				sort, stock, pageNumber, pageSize);// I want this much data from frontend
		System.out.println("complete products");
		return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
	}
	
	
//	@GetMapping("/products/id/{productId}")
//	public ResponseEntity<Product> findProductByIdHandler(@RequestParam Long productid)throws ProductException
//	{
//		Product product=productService.findProductById(productid);
//		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
//	}
//	
//	@GetMapping("/products/search")
//	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q)
//	{
//		List<Product> products=productService.searchProduct(q);
//		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
//	}
	
	
	
}
