package com.bookshop.vct.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.vct.entity.ShoppingCart;
import com.bookshop.vct.service.BookShopService;

@CrossOrigin("*")
@RestController
public class CartRestController {

	@Autowired
	private BookShopService bookShopService;
	
	@GetMapping(value= {"/addCart/updateCart","/removeCart/updateCart","/updateCart","/cart/updateCart"})
	public double updateCart(HttpSession session,int id, int newQuantity) {
		System.out.println("Id "+id+" newQuantity "+newQuantity);
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		cart.update(id, newQuantity);
		return cart.getTotal();
	}
	
	
	@GetMapping("/getAutoComplete")
	public List<String> autocomplteSearch(String name){
		List<String> products = bookShopService.getProductByAutoComplete(name);
		return products;
	}
	
	@GetMapping("/sumByMonth")
	public List<Integer> sumByMonth() {
		List<Integer> sum = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			sum.add(bookShopService.sumByMonth(i));
		}
		return sum;
	}
	
	@GetMapping("/sumByMonthBetween")
	public List<Integer> sumByMonthBetween(int monthFrom, int monthTo){
		List<Integer> sum = new ArrayList<>();
		for (int i = monthFrom; i <= monthTo; i++) {
			sum.add(bookShopService.sumByMonth(i));
		}
		return sum;
	}
}
