package com.luizbarros.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(long userId) {
		User user = userService.authenticated();
		if(!user.hasRole("ROLE_ADMIN") && !user.getId().equals(userId)) {
			throw new ForbiddenException("Access denied");
		}
	}
}
