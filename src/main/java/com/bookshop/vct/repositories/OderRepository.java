package com.bookshop.vct.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Oder;

@Repository
public interface OderRepository extends JpaRepository<Oder, Integer> {
	@Query("SELECT o FROM Oder o WHERE o.customer = ?1")
	public List<Oder> findByCustomer(Customer c);
}
