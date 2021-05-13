package com.bookshop.vct.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Role;


public class CustomerDetail implements UserDetails{

	private Customer customer;
	
	public CustomerDetail(Customer customer) {
		this.customer = customer;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles= customer.getRoles();
		List<GrantedAuthority> listGrantedAuthorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			listGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return listGrantedAuthorities;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public List<Role> getRole() {
		return customer.getRoles();
	}
}
