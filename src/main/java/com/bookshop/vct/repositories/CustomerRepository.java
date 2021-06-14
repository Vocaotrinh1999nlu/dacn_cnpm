package com.bookshop.vct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookshop.vct.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("SELECT c FROM Customer c WHERE c.userName = ?1")
	public Customer findByUserName(String userName);
	
	public Customer findByEmail(String email);
	
	@Query("SELECT c FROM Customer c WHERE c.verificationCode=?1" )
	public Customer findByVerificationCode(String verificationCode);
	
	@Query("SELECT c FROM Customer c WHERE c.forgetPasswordToken=?1")
	public Customer findByForgetPasswordToken(String token);
}
