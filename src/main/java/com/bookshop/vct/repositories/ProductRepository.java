package com.bookshop.vct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.OderItem;
import com.bookshop.vct.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT p FROM Product p WHERE p.id =?1")
	public Product getProductById(int id);
	
	public Product findByOderItem(OderItem oderItem);
	
	@Query("SELECT p.name FROM Product p WHERE p.name like %?1% ")
	public List<String> getProductAutoComplete(String value);
	
	@Query("SELECT p FROM Product p WHERE p.name = ?1")
	public Product findByName(String name);
}
