package com.bookshop.vct.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Oder> oders;

}
