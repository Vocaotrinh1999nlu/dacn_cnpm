package com.bookshop.vct.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String address;
	
	private String phone;
	 
	private boolean isActive;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private int yearOfBirth;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	  @JoinTable(name = "customer_role", 
	    joinColumns = { @JoinColumn(name = "customer_id") }, 
	    inverseJoinColumns = {@JoinColumn(name = "role_id") })
	private List<Role> roles;
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Oder> oders;
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode;
	
	@Column(name="forget_password_token", length = 64)
	private String forgetPasswordToken;
	
}
