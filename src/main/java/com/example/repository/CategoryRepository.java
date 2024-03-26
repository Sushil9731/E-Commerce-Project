package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	
	@Query("SELECT c FROM Category c WHERE c.name=:name AND c.parantCategory.name=:parantCategoryName")
	public Category findByName(String name);
	public Category findByNameAndParant(@Param("name")String name,@Param("parantCategoryName")String parantCategoryName);
}
