package com.bookshop.vct.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@PostMapping("/addCustomer")
    public String processRegister(Customer user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        customerService.saveCustomer(user, getSiteURL(request));       
        return "registerSucess";
    }
	
	@GetMapping("/forgetPassForm")
	public String forgetPassForm() {
		return "forgetPassForm";
	}
	
	@PostMapping("/sendsendForgetPasswordToken")
	public String sendForgetPasswordToken(HttpServletRequest request) throws UnsupportedEncodingException, MessagingException{
		String userName = request.getParameter("username");
		boolean result = customerService.setForgetPasswordToken(userName);
		if(result) {
			customerService.sendForgetPassEmail(customerService.findCustomerByUserName(userName), getSiteURL(request));
			return "setPasswordSucess";
		}else {
			return "setPasswordFail";
		}
	}
	private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    } 
	
	@GetMapping("/verify/{code}")
	public String verifyUser(@PathVariable("code") String verificationCode) {
		System.out.println("Code "+verificationCode);
		if(customerService.verify(verificationCode)) {
			return "verifySucess";
		}else {
			return "verifyFail";
		}
	}
	
	@GetMapping("/setNewPass/{forgetPasswordToken}")
	public String formResetPass(@PathVariable("forgetPasswordToken") String forgetPasswordToken, HttpSession session) {
		session.setAttribute("forgetPasswordToken", forgetPasswordToken);
		return "formResetPass";
	}
	
	@PostMapping("/setNewPassword")
	public String setNewPass(HttpServletRequest request, HttpSession session) {
		String token = (String) session.getAttribute("forgetPasswordToken");
		String newPass = request.getParameter("newPass");
		boolean setPassResult = customerService.setNewPass(token, newPass);
		if(setPassResult) {
			session.removeAttribute("forgetPasswordToken");
			return "login";
		}else {
			return "setNewPassFail";
		}
	}
}
