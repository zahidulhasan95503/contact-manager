package com.smart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entites.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository Userrepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		 
		model.addAttribute("title","HOME-smart contact Application");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		
		model.addAttribute("title","ABOUT-smart contact Application");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("title","SignUp-smart contact Application");
		return "signup";
	}
	
	//handler
	@PostMapping("/do_register")
	public String handleform(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value="agreement",defaultValue="false") boolean agreement, Model model, HttpSession session ) {
		
		
		try {
			
			
			if(result.hasErrors()) {
				System.out.println(result.toString());
				model.addAttribute("user",user);
				return "signup";
			}
			
			if(!agreement) {
				System.out.println("you have not agreed terms and conditions");
				throw new Exception("you have not agreed terms and conditions");
			}
			
			
			
			
			
			System.out.println("agreement="+agreement);
			System.out.println(user);
			
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImageurl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			User save = this.Userrepository.save(user);
			  
			model.addAttribute("user",new User());
			
			session.setAttribute("message",new Message("Successfully Registered","alert-success"));
			return "signup";
			
		}	
		
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("Something Went Wrong "+e.getMessage(),"alert-danger"));
			return "signup";
		}
		
	}
	
	@GetMapping("/signin")
	public String login(Model model) {
		
		model.addAttribute("title","LOGIN");
		return "login";
	}
	
	

}
