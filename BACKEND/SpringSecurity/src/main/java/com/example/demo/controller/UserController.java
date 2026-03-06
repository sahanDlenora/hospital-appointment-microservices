package com.example.demo.controller;

import com.example.demo.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping("/register")
	public Users register(@RequestBody RegisterRequest request) {
		return service.register(request);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		
		return service.verify(user);
	}

}
