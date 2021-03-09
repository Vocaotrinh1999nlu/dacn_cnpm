package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("SELECT a FROM Author a WHERE a.name = ?1")
	public Author getAuthorByName(String name);
}
