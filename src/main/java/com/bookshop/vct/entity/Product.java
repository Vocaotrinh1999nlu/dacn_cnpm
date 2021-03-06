package com.bookshop.vct.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;
	
	private String image;
	
	private double price;
	
	private boolean isActive;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
	private Author author;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
	private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
	private Publisher publisher;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<OderItem> oderItem;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Comment> comment;
	
}
