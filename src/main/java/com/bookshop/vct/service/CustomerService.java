package com.bookshop.vct.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Role;
import com.bookshop.vct.repositories.CustomerRepository;

import net.bytebuddy.utility.RandomString;


@Service
public class CustomerService implements UserDetailsService {

	@Autowired
	private BookShopService bookShopService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private MailService mailService;
	
	public void saveCustomer(Customer customer, String siteURL)throws UnsupportedEncodingException, MessagingException {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		List<Role> roles = new ArrayList<Role>(); 
		roles.add(bookShopService.getRoleByName("customer"));
		String randomCode = RandomString.make(64);
	    customer.setVerificationCode(randomCode);
		customer.setRoles(roles);
		customer.setActive(false);
		bookShopService.addCustommer(customer);
		sendVerificationEmail(customer, siteURL);
	}
	
	public boolean setForgetPasswordToken(String userName) {
		Customer customer = findCustomerByUserName(userName);
		if(customer == null) {
			System.out.println("user not found or not exist in system");
			return false;
		}else {
			String randomToken = RandomString.make(64);
			customer.setForgetPasswordToken(randomToken);
			customerRepository.save(customer);
			return true;
		}
	}
	
	
	
	private void sendVerificationEmail(Customer user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	    content = content.replace("[[name]]", user.getName());
	    String verifyURL = siteURL + "/verify/" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    mailService.sendMail(toAddress, subject, content);
	}

	public void sendForgetPassEmail(Customer user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String subject = "New password for you";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to resset your password:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">SET_NEW_PASSWORD</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    content = content.replace("[[name]]", user.getName());
	    String verifyURL = siteURL + "/setNewPass/" + user.getForgetPasswordToken();
	    content = content.replace("[[URL]]", verifyURL);
	     
	    mailService.sendMail(toAddress, subject, content);
	}
	
	public Customer findCustomerByUserName(String userName) {
		return bookShopService.findCustomerByUserName(userName);
	}
	
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user = bookShopService.findCustomerByUserName(username);
		if (user == null) throw new UsernameNotFoundException(username);
        return new CustomerDetail(user);
	}
	
	public Customer findByForgetPasswordToken(String token) {
		return customerRepository.findByForgetPasswordToken(token);
	}
	public boolean verify(String verificationCode) {
		Customer customer = customerRepository.findByVerificationCode(verificationCode);
		if(customer == null || customer.isActive()) {
			return false;
		}else {
			customer.setVerificationCode(null);
			customer.setActive(true);
			customerRepository.save(customer);
			return true;
		}
	}
	
	public boolean setNewPass(String token, String newPass) {
		Customer customer = customerRepository.findByForgetPasswordToken(token);
		if(customer == null) {
			return false;
		}else {
			customer.setForgetPasswordToken(null);
			customer.setPassword(passwordEncoder.encode(newPass));
			customerRepository.save(customer);
			return true;
		}
	}
	
}
