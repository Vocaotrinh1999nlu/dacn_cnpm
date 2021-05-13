package com.bookshop.vct.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Oder;
import com.bookshop.vct.entity.OderItem;
import com.bookshop.vct.entity.ShoppingCart;
import com.bookshop.vct.repositories.OderItemRepository;
import com.bookshop.vct.repositories.OderRepository;

@Service
public class OrderService {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private OderItemRepository oderItemRepository;
	@Autowired
	private OderRepository oderRepository;

	public Customer getCustomerLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return customerService.findCustomerByUserName(userName);
	}

	public List<Oder> getOderByCustomer(){
		return oderRepository.findByCustomer(getCustomerLogged());
	}
	
	public void saveOrderItem(HttpSession session) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		Oder oder = saveOrder(session);
		oderRepository.save(oder);
		List<OderItem> oderItems = cart.getItems();
		for (OderItem oderItem : oderItems) {
			oderItem.setOder(oder);
		}
		oderItemRepository.saveAll(oderItems);
	}

	public Oder saveOrder(HttpSession session) {
		Oder oder = new Oder();
		// get list order items
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		oder.setPay(false);
		oder.setOderDate(LocalDateTime.now());
		oder.setTotal(cart.getTotal());
		oder.setShipingCost(shipingCost(cart));
		oder.setSubtotal(subtotal(cart));
		oder.setCustomer(getCustomerLogged());
		return oder;
	}
	
	public double shipingCost(ShoppingCart cart) {
		double shippingCost = 0.0;
		double total = cart.getTotal();
		if(total>100000 && total < 200000) {
			shippingCost = (total*10)/100;
		}else if(total >= 200000 && total <300000) {
			shippingCost = (total*5)/100;
		}else if(total >= 300000){
			shippingCost = 0;
		}
		return roundOff(shippingCost);
		
	}
	public double subtotal(ShoppingCart cart) { 
		return roundOff(cart.getTotal()+shipingCost(cart));
	}
	private double roundOff(double x) {
		long val = Math.round(x * 100); // cents
		return val / 100.0;
	}
	public double transferVNDToUSD(double vnd) {
		double value = vnd/23000;
		return roundOff(value);
	}
}
