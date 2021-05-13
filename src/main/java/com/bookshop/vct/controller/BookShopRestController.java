package com.bookshop.vct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.vct.entity.Product;
import com.bookshop.vct.service.BookShopService;

@CrossOrigin("*")
@RestController
public class BookShopRestController {

	@Autowired
	private BookShopService bookShopService;
	
	@GetMapping("/getAutoComplete/{value}")
	public List<Product> autocomplteSearch(@PathVariable("value")String value){
		
		List<Product> products = bookShopService.getProductByAutoComplete(value);
		for (Product product : products) {
			System.out.println(product.getName());
		}
		return products;
	}
}
