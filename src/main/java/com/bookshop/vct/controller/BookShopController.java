package com.bookshop.vct.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bookshop.vct.entity.Author;
import com.bookshop.vct.entity.Category;
import com.bookshop.vct.entity.Customer;
import com.bookshop.vct.entity.Product;
import com.bookshop.vct.entity.Publisher;
import com.bookshop.vct.entity.ShoppingCart;
import com.bookshop.vct.repositories.CustomerRepository;
import com.bookshop.vct.service.BookShopService;
import com.bookshop.vct.service.CustomerService;
import com.bookshop.vct.service.MailService;


@Controller
public class BookShopController {

	@Autowired
	private BookShopService bookShopService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping(value = {"/","/index"})
	public String home(Model model) {
		model.addAttribute("listBook",bookShopService.getProduct(0));
		return "index";
	}
	
	@GetMapping(value = "/shop-grid")
	public String shopGrid(Model model) {
		model.addAttribute("message", "Tất cả các sách");
		model.addAttribute("listCategory", bookShopService.getCategories());
		model.addAttribute("listAuthor", bookShopService.getAuthors());
		model.addAttribute("listBook",bookShopService.getProduct(0));
		return "shop-grid";
	}
	
	@GetMapping("/category/{id}")
	public String getProductByCategory(@PathVariable("id") int id,Model model) {
		Category category = bookShopService.getCategoryById(id);
		String message = "Kết quả tìm kiếm sách trong danh mục "+category.getTitle();
		model.addAttribute("message", message);
		model.addAttribute("listCategory", bookShopService.getCategories());
		model.addAttribute("listAuthor", bookShopService.getAuthors());
		model.addAttribute("listBook", category.getProducts());
		return "shop-grid";
	}
	
	@GetMapping("/author/{id}")
	public String getProductByAuthor(@PathVariable("id") int id,Model model) {
		Author author = bookShopService.getAuthorById(id);
		String message = "Kết quả tìm kiếm sách các tác giả "+author.getName();
		model.addAttribute("message", message);
		model.addAttribute("listCategory", bookShopService.getCategories());
		model.addAttribute("listAuthor", bookShopService.getAuthors());
		model.addAttribute("listBook", author.getProducts());
		return "shop-grid";
	}
	@GetMapping("/addCart/{id}")
	public String addToCart(HttpSession session, @PathVariable int id, Model model) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		if (id != 0) {
			Product p = bookShopService.getProductById(id);
			cart.add(id, p);
			System.out.println("them san pham vao gio hang thanh cong");
		}
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
	}
	
	@GetMapping("/removeCart/{id}")
	public String removeCart(HttpSession session, @PathVariable("id") int id, Model model) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		if (id != 0) {
			cart.remove(id);
		}
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
	}
	
	@GetMapping("/cart")
	public String getCartPage(Model model, HttpSession session) {
		ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
		if (cart == null) {
			cart = new ShoppingCart();
			session.setAttribute("cart", cart);
		}
		model.addAttribute("numberOfItems", cart.getNumberOfItems());
		model.addAttribute("total", cart.getTotal());
		model.addAttribute("books", cart.getItems());
		return "cart";
	}
	
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
	
	@GetMapping("/addCustomerForm")
	public String addCustomerForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "addCustomer";
	}
	
	@GetMapping("/singleProductSearch/{name}")
	public String singleProductByName(@PathVariable("name")String name, Model model) {
		model.addAttribute("book", bookShopService.findByName(name));
		return "singleProduct";
	}
	
	@GetMapping("/singleProduct/{id}")
	public String singleProductById(@PathVariable("id")int id, Model model) {
		model.addAttribute("book", bookShopService.findById(id));
		return "singleProduct";
	}
	
	@GetMapping("/formMailReset")
	public String formEmailRest() {
		return "formMailRest";
	}
	
	@GetMapping("/drawChart")
	public String drawChart() {
		return "drawChart";
	}
	
	@GetMapping("/dashBoard")
	public String dashBoard() {
		return "dashBoard";
	}
}
