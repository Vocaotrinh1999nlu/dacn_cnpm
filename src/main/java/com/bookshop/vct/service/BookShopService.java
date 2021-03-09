package com.bookshop.vct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookshop.vct.entity.Author;
import com.bookshop.vct.entity.Category;
import com.bookshop.vct.entity.Comment;
import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Oder;
import com.bookshop.vct.entity.OderItem;
import com.bookshop.vct.entity.Product;
import com.bookshop.vct.entity.Publisher;
import com.bookshop.vct.repositories.AuthorRepository;
import com.bookshop.vct.repositories.CategoryRepository;
import com.bookshop.vct.repositories.CommentRepository;
import com.bookshop.vct.repositories.CustomerRepository;
import com.bookshop.vct.repositories.OderItemRepository;
import com.bookshop.vct.repositories.OderRepository;
import com.bookshop.vct.repositories.ProductRepository;
import com.bookshop.vct.repositories.PublisherRepository;

@Service
public class BookShopService {

	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private OderItemRepository oderItemRepository;
	
	@Autowired
	private OderRepository oderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	//method for service
	public void addAuthor(Author author) { 
		authorRepository.save(author);
	}
	
	public void addCategory(Category category) {
		categoryRepository.save(category);
	}
	
	public void addComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public void addCustommer(Customer custommer) {
		customerRepository.save(custommer);
	}
	
	public void saveOder(Oder oder) { 
		oderRepository.save(oder);
	}
	
	public void saveOderItem(OderItem oderItem) {
		oderItemRepository.save(oderItem);
	}
	
	public void addProduct(Product product) {
		productRepository.save(product);
	}
	
	public void addPublisher(Publisher publisher) {
		publisherRepository.save(publisher);
	}
	
	public List<Category> listCategory(){
		return categoryRepository.findAll();
	}
	
	public List<Author> listAuthor(){
		return authorRepository.findAll();
	}
	
	public List<Publisher> listPublishers(){
		return publisherRepository.findAll();
	}
	
	public Author getAuthorByName(String name) {
		return authorRepository.getAuthorByName(name);
	}
	
	public Category getCategoryByName(String name) {
		return categoryRepository.getCategoryByName(name);
	}
	
	public Publisher getPublisherByName(String name) {
		return publisherRepository.getPublisherByName(name);
	}
}
