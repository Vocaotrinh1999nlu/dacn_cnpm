package com.bookshop.vct.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Role;
import com.bookshop.vct.repositories.CustomerRepository;
import com.bookshop.vct.repositories.RoleRepository;


@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private BookShopService bookShopService;
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public void saveCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		roles.add(bookShopService.getRoleByName("customer"));
		customer.setRoles(roles);
		customer.setActive(true);
		bookShopService.addCustommer(customer);
	}
	
	public Customer findCustomerByUserName(String userName) {
		return bookShopService.findCustomerByUserName(userName);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user = bookShopService.findCustomerByUserName(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return new CustomerDetail(user);
	}
	

	
}
