package com.example.service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.exception.ProductException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import com.example.request.CreateProductRequest;

// we have to write here all business logic
@Service
public class ProductServiceImplementation implements ProductService{

	// making instances. we can inotate @ constructor or define empty constructor also
	private ProductRepository productRepository;
	private UserService userService;
	private CategoryRepository categoryRepository;
	
	
	public ProductServiceImplementation(ProductRepository productRepository,
			UserService userService,CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
		this.productRepository=productRepository;
		this.userService=userService;
	}
	
	@Override
	public Product createProduct(CreateProductRequest req) {
		// category is present or not at top level
		Category topLevel=categoryRepository.findByName(req.getTopLevelCategory());
		if(topLevel==null)
		{
			// set or create top level category
			Category topLevelCategory=new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			
			// first save and then assign it to top level
			topLevel=categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel=categoryRepository.
			findByNameAndParant(req.getSecondLevelCategory(),topLevel.getName());
		if(secondLevel==null)
		{
			Category secondLevelCategory=new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setLevel(1);
			
			// first save and then assign it to second level
			secondLevel=categoryRepository.save(secondLevelCategory);
			
		}
		
		Category thirdLevel=categoryRepository.
			findByNameAndParant(req.getThirdLevelCategory(),secondLevel.getName());
		
		if(thirdLevel==null)
		{
			Category thirdLevelCategory=new Category();
			thirdLevelCategory.setName(req.getSecondLevelCategory());
			thirdLevelCategory.setLevel(1);
			
			// first save and then assign it to third level
			thirdLevel=categoryRepository.save(thirdLevelCategory);
			
		}
		
		
		
		Product product=new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPersent(req.getDiscountPersent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSize(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevel);
		product.setCreatedAt(LocalDateTime.now());
		
		// save this product in database
		Product savedProduct=productRepository.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		// delete product method
		Product product=findProductById(productId);
		
		// clearing sizes 
		product.getSize().clear();
		productRepository.delete(product);
		
		return "Product delete Sucessfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product=findProductById(productId);
		if(req.getQuantity()!=0)
		{
			product.setQuantity(req.getQuantity());
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
	
		Optional<Product>opt=productRepository.findById(id);
		if(opt.isPresent())
		{
			return opt.get();
		}
		throw new ProductException("Product not found with ID");
		
		}

	@Override
	public List<Product> findProductByCategory(String category) {
		
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		// most imp method
		
		org.springframework.data.domain.Pageable pageable=PageRequest.of(pageNumber,pageSize);
		
		List<Product>products=productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		if(colors.isEmpty())
		{
			// if product is not empty the change the value changed by filter
			//check to list of color to match product color
			//if one of them match then all those products we want here
			products=products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
		
		if(stock!=null)
		{
			if(stock.equals("in_stock"))
			{
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock"))
			{
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
			}
		}
		
		int startIndex=(int)pageable.getOffset();
		//if we new page open then start index is here and end index
		// how to make it dynamic. start index+endindex = next end index number 
		// cause we have 10 products in one single page
		int endIndex=Math.min(startIndex+pageable.getPageSize(),products.size());
		List<Product>ppageContent=products.subList(startIndex, endIndex);
		Page<Product>filteredProducts=new PageImpl<>(ppageContent,pageable,products.size());
		
		return filteredProducts;
	}

}
