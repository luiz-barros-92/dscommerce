package com.luizbarros.dscommerce.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.projections.UserDetailsProjection;
import com.luizbarros.dscommerce.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	private String existingUsername, nonExistingUsername;
	private User user;	
	private List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setup() throws Exception {
		existingUsername = "abreu@gmail.com";
		nonExistingUsername = "nemeu@gmail.com";
		//TODO

	}

}
