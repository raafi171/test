package com.viva.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viva.dto.AuthDto;
import com.viva.model.UserInfo;
import com.viva.repository.AuthRepository;
import com.viva.service.PasswordService;
import com.viva.service.UserDetailsImpl;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;;
@Controller
@Slf4j
public class Auth_Controller {
	
	@Autowired
	AuthRepository repository;
	
	@Autowired
	PasswordService passwordService;

	private String message;
	
	//@GetMapping("/sign_up.html")
	@GetMapping("/register")
	public String register(Model model) {
		
		//repository.save(AuthenticModel.builder().userName("admin").password(passwordService.encode("admin")).build());
		return "sign_up";
		
	}
	@GetMapping("/login")
	public String login(@Param(value = "message") String message, Model model, HttpSession session) {
		
		if(message != null && !message.equals("")) {
			model.addAttribute("modelMessage",message);
		}
		return "login";
		
	}
	@GetMapping("/")
	public String loggedin() {
		return "redirect:/home";
	}
	@PostMapping("/saveUser")
	public String save(AuthDto dto, Model model, RedirectAttributes attributes) {

		repository.save(UserInfo.builder().userName(dto.getUserName()).password(passwordService.encode(dto.getPassword())).build());
		//attributes.addFlashAttribute("message", "New blog created");
		return "redirect:/login";
	}
	
}