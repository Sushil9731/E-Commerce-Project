package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Product;
// we create this interface repository for product and ID type is Long
public interface ProductRepository extends JpaRepository<Product,Long> {

	
	//query for category for achieve product by category wise
	@Query("SELECT p FROM Product p"+
	"WHERE(p.category.name=:catrgory OR :category='')"+
			"AND((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))"+
			"AND(:minDiscount IS NULL OR p.discountPersent >= :minDiscount)"+
			"ORDER BY"+
			"CASE WHEN :sort='price_low'THEN p.discountedPrice END ASC"+
			"CASE WHEN :sort='price_high'THEN p.discountedPrice END DESC"
			)
	
	public List<Product>filterProducts(@Param("category")String category,
			@Param("minPrice")Integer minPrice,@Param("maxPrice")Integer maxPrice,
			@Param("minDiscount")Integer minDiscount,
			@Param("sort")String sort);
	
	
}
