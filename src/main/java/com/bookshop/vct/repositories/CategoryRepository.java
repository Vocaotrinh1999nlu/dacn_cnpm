package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT c FROM Category c where c.title = ?1")
	public Category getCategoryByName(String name);
	
	@Query("SELECT c FROM Category c where c.id = ?1")
	public Category getCategoryBId(int id);
}
