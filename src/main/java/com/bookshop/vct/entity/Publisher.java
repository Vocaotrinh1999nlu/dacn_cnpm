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
public class Publisher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String image;
	
	private String description;
	
	private boolean isActive;
	
	@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Product> products;

}
