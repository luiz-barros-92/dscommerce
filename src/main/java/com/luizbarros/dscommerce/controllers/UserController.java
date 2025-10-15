package com.luizbarros.dscommerce.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizbarros.dscommerce.dto.UserDTO;
import com.luizbarros.dscommerce.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	private final UserService service;
		
	public UserController(UserService service) {
		this.service = service;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(value = "/me")
	public ResponseEntity<UserDTO> getMe() {
		return ResponseEntity.ok(service.getMe());
	}	
}
