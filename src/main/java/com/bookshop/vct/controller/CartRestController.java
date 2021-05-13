package com.bookshop.vct.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookshop.vct.entity.ShoppingCart;

@RestController
public class CartRestController {

	@GetMapping(value= {"/addCart/updateCart","/removeCart/updateCart","/updateCart","/cart/updateCart"})
	public double updateCart(HttpSession session,int id, int newQuantity) {
		System.out.println("Id "+id+" newQuantity "+newQuantity);
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		cart.update(id, newQuantity);
		return cart.getTotal();
	}
}
