package com.luizbarros.dscommerce.services;

import org.springframework.stereotype.Service;

import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {
	
	private final UserService userService;	
	
	public AuthService(UserService userService) {
		this.userService = userService;
	}

	public void validateSelfOrAdmin(Long userId) {
		User user = userService.authenticated();
		if (user.hasRole("ROLE_ADMIN")) {
			return;
		}
		if (!user.getId().equals(userId)) {
			throw new ForbiddenException("Access denied");
		}
	}	
}
