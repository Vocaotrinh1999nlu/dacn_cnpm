package com.bookshop.vct.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookshop.vct.service.OrderService;

@Controller
public class OderController {

	@Autowired
	private OrderService orderService;
	@GetMapping("/checkout")
	public String checkOut(HttpSession session) {
		orderService.saveOrderItem(session);
		return "checkOut2";
	}
}
