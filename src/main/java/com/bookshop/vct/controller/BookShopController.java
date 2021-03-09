package com.bookshop.vct.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bookshop.vct.entity.Author;
import com.bookshop.vct.entity.Category;
import com.bookshop.vct.entity.Product;
import com.bookshop.vct.entity.Publisher;
import com.bookshop.vct.service.BookShopService;

@Controller
public class BookShopController {

	@Autowired
	private BookShopService bookShopService;
	
	@GetMapping("/addCategoryView")
	public String addCategoryView(Model model) {
		model.addAttribute("category", new Category());
		return "add/addCategory";
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@ModelAttribute("category") Category category) {
		category.setActive(true);
		bookShopService.addCategory(category);
		return "add/addSucess";
	}
	
	@GetMapping("/addAuthorView")
	public String addAuthorView(Model model) {
		model.addAttribute("author", new Author());
		return "add/addAuthor";
	}
	
	@PostMapping("/addAuthor")
	public String addAuthor(@ModelAttribute("author") Author author) {
		author.setActive(true);
		bookShopService.addAuthor(author);
		return "add/addSucess";
	}
	
	@GetMapping("/addPublisherView")
	public String addPublisherView(Model model) {
		model.addAttribute("publisher", new Publisher());
		return "add/addPublisher";
	}
	
	@PostMapping("/addPublisher")
	public String addPublisher(@ModelAttribute("publisher") Publisher publisher) {
		publisher.setActive(true);
		bookShopService.addPublisher(publisher);
		return "add/addSucess";
	}
	
	@GetMapping("/addProductView")
	public String addProductView(Model model) {
		model.addAttribute("listCategory", bookShopService.listCategory());
		model.addAttribute("listAuthor", bookShopService.listAuthor());
		model.addAttribute("listPublisher", bookShopService.listPublishers());
		model.addAttribute("product", new Product());
		return "add/addProduct";
	}
	
	@PostMapping("/addProduct")
	public String addProduct(Product product) {
		product.setActive(true);
		bookShopService.addProduct(product);
		return "add/addSucess";
	}
}