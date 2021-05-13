package com.bookshop.vct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Oder;
import com.bookshop.vct.entity.PaymentOrder;
import com.bookshop.vct.repositories.OderRepository;
import com.bookshop.vct.service.OrderService;
import com.bookshop.vct.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.ShippingCost;
import com.paypal.base.rest.PayPalRESTException;
@Controller
public class PayPalController {
	
	@Autowired
	PaypalService service;

	@Autowired
	OrderService orderService;
	
	@Autowired
	OderRepository oderRepository;
	
	public static final String SUCCESS_URL = "pay/success";
	public static final String CANCEL_URL = "pay/cancel";
	
	@GetMapping("/listOrder")
	public String myAccount(Model model) {
		model.addAttribute("listOrder", orderService.getOderByCustomer());
		return "listOrder";
	}
	@GetMapping("/paypage/{id}")
	public String home(@PathVariable("id") int id, Model model, HttpSession session) {
		Optional<Oder> oder =  oderRepository.findById(id);
		double oderTotal = oder.get().getSubtotal();
		double trasfer = orderService.transferVNDToUSD(oder.get().getSubtotal());
		model.addAttribute("total", trasfer);
		model.addAttribute("subtotal", oderTotal);
		session.setAttribute("orderIdPayed", id);
		return "pay";
	}
	
	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") PaymentOrder order) {
		try {
			Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), "paypal",
					order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
					"http://localhost:8080/" + SUCCESS_URL);
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return "redirect:"+link.getHref();
				}
			}
			
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	 @GetMapping(value = CANCEL_URL)
	    public String cancelPay() {
	        return "cancel";
	    }

	    @GetMapping(value = SUCCESS_URL)
	    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session, Model model) {
	        try {
	            Payment payment = service.executePayment(paymentId, payerId);
	            //System.out.println(payment.toJSON());
	           // System.out.println(payment.getPayer().getPayerInfo().getShippingAddress());
	            ShippingAddress shippingAddress = payment.getPayer().getPayerInfo().getShippingAddress();
	            String shipAddress = shippingAddress.getLine1()+","+shippingAddress.getLine2()+","+shippingAddress.getCity()+","+shippingAddress.getCountryCode();
	            if (payment.getState().equals("approved")) {
	            	int id = (int) session.getAttribute("orderIdPayed");
	            	Oder oderUpdate = oderRepository.findById(id).get();
	            	oderUpdate.setPay(true);
	            	oderUpdate.setPayTime(LocalDateTime.now());
	            	oderUpdate.setCurrentcy("USD");
	            	oderUpdate.setShipAdress(shipAddress);
	            	oderUpdate.setExchangeRate(23000);
	            	oderRepository.save(oderUpdate);
	            	session.removeAttribute("orderIdPayed");
	            	model.addAttribute("listOrder", orderService.getOderByCustomer());
	        		return "listOrder";
	            }
	        } catch (PayPalRESTException e) {
	         System.out.println(e.getMessage());
	        }
	        return "redirect:/";
	    }
}
