package com.example.demo.service;

import com.example.demo.client.UserServiceClient;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.repo.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Autowired
	private UserServiceClient userServiceClient;

	public Users register(RegisterRequest request) {

		// 1️⃣ Save profile in user-service
		UserDTO dto = new UserDTO();
		dto.setName(request.getName());
		dto.setAge(request.getAge());
		dto.setPhone(request.getPhone());
		dto.setAddress(request.getAddress());
		dto.setRole(request.getRole());

		UserDTO savedUser = userServiceClient.createUser(dto);

		// 2️⃣ Save credentials in auth-service
		Users user = new Users();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user.setUserId(savedUser.getId());

		return repo.save(user);
	}

	public String verify(Users user) {

		Authentication authentication =
				authManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								user.getUsername(),
								user.getPassword()
						)
				);

		if(authentication.isAuthenticated()) {

			Users dbUser = repo.findByUsername(user.getUsername());

			return jwtService.generateToken(
					dbUser.getUsername(),
					dbUser.getRole()
			);
		}

		return "Fail";
	}


}
