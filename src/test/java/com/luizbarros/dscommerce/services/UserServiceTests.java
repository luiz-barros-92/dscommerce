package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.projections.UserDetailsProjection;
import com.luizbarros.dscommerce.repositories.UserRepository;
import com.luizbarros.dscommerce.tests.UserDetailsFactory;
import com.luizbarros.dscommerce.tests.UserFactory;

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
		
		user = UserFactory.createCustomClientUser(1L, existingUsername);
		userDetails = UserDetailsFactory.createCustomAdmintUser(existingUsername);
		
		when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());
	}
	
	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existingUsername);
		assertNotNull(result);
		assertEquals(result.getUsername(), existingUsername);		
	}
	
	@Test
	public void loadUserByUsernameShouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
	}
}
