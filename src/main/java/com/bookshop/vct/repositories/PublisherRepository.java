package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

	@Query("SELECT p FROM Publisher p WHERE p.description = ?1")
	public Publisher getPublisherByName(String name);
}
