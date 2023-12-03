package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jtspringproject.JtSpringProject.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.services.userService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.cartService;


@Controller
public class UserController {

    @Autowired
    private userService userService;

    @Autowired
    private productService productService;

    public UserController(com.jtspringproject.JtSpringProject.services.userService userService, com.jtspringproject.JtSpringProject.services.productService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/register")
    public String registerUser() {
        return "register";
    }

    @GetMapping("/buy")
    public String buy() {
        return "buy";
    }


    @GetMapping("/")
    public String userlogin(Model model) {

        return "userLogin";
    }

    @RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
    public ModelAndView userlogin(@RequestParam("username") String username, @RequestParam("password") String pass, Model model, HttpServletResponse res) {

        User u = this.userService.checkLogin(username, pass);

        if ((u.getRole() != null) && u.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("msg", "Admin can't login from here");
            ModelAndView mView = new ModelAndView("userLogin");
            mView.addObject("message", "Admin can't login from here");
            return mView;
        }

        if (!(u.getUsername() == null) && u.getUsername().equals(username)) {

            res.addCookie(new Cookie("username", u.getUsername()));
            ModelAndView mView = new ModelAndView("index");
            mView.addObject("user", u);
            List<Product> products = this.productService.getProducts();

            if (products.isEmpty()) {
                mView.addObject("msg", "No products are available");
            } else {
                mView.addObject("products", products);
            }
            return mView;

        } else {
            model.addAttribute("msg", "Please enter correct username and password");
            ModelAndView mView = new ModelAndView("userLogin");
            mView.addObject("message", "Please enter correct email and password");
            return mView;
        }

    }


    @GetMapping("/user/products")
    public ModelAndView getproduct() {

        ModelAndView mView = new ModelAndView("uproduct");

        List<Product> products = this.productService.getProducts();

        if (products.isEmpty()) {
            mView.addObject("msg", "No products are available");
        } else {
            mView.addObject("products", products);
        }

        return mView;
    }

    @RequestMapping(value = "newuserregister", method = RequestMethod.POST)
    public String newUseRegister(@ModelAttribute User user, Model model, @RequestParam("confirm-password") String confirmPassword) {
        System.out.println(user.getPassword() + confirmPassword);
        if (containsSpecialCharacter(user.getUsername()) || containsSpecialCharacter(user.getAddress())) {
            ModelAndView mView = new ModelAndView("register");
            mView.addObject("message", "Can not use special characters here");
            model.addAttribute("message", "Can not use special characters here");
            return "/register";
        }
		if(user.getPassword().length() < 8) {
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("message", "Password must be at least 8 characters long");
			model.addAttribute("message", "Password must be at least 8 characters long");
			return "/register";
		}
        if (!user.getPassword().equalsIgnoreCase(confirmPassword)) {
            ModelAndView mView = new ModelAndView("register");
            mView.addObject("message", "Password and Confirm Password must be same");
            model.addAttribute("message", "Password and Confirm Password must be same");
            return "/register";
        }
        if(userNameExists(user.getUsername())) {
            ModelAndView mView = new ModelAndView("register");
            mView.addObject("message", "Username already exists");
            model.addAttribute("message", "Username already exists");
            return "/register";
        }
        System.out.println(user.getEmail());
        user.setRole("ROLE_NORMAL");
        this.userService.addUser(user);

        return "redirect:/";
    }
    boolean userNameExists(String username) {
        List<User> users = this.userService.getUsers();
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsSpecialCharacter(String s) {
        String specialCharsRegex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|<>\\/?]+";
        Pattern pattern = Pattern.compile(specialCharsRegex);
        return pattern.matcher(s).find();
    }


    //for Learning purpose of model
    @GetMapping("/test")
    public String Test(Model model) {
        System.out.println("test page");
        model.addAttribute("author", "jay gajera");
        model.addAttribute("id", 40);

        List<String> friends = new ArrayList<String>();
        model.addAttribute("f", friends);
        friends.add("xyz");
        friends.add("abc");

        return "test";
    }

    // for learning purpose of model and view ( how data is pass to view)

    @GetMapping("/test2")
    public ModelAndView Test2() {
        System.out.println("test page");
        //create modelandview object
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "jay gajera 17");
        mv.addObject("id", 40);
        mv.setViewName("test2");

        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(25);
        mv.addObject("marks", list);
        return mv;


    }


//	@GetMapping("carts")
//	public ModelAndView  getCartDetail()
//	{
//		ModelAndView mv= new ModelAndView();
//		List<Cart>carts = cartService.getCarts();
//	}

}
