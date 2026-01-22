package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.UserDTO;
import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.projections.UserDetailsProjection;
import com.luizbarros.dscommerce.repositories.UserRepository;
import com.luizbarros.dscommerce.tests.UserDetailsFactory;
import com.luizbarros.dscommerce.tests.UserFactory;
import com.luizbarros.dscommerce.util.CustomUserUtil;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private CustomUserUtil customUserUtil;
	
	private String existingUsername, nonExistingUsername;
	private User user;	
	private List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setup() throws Exception {
		existingUsername = "abreu@gmail.com";
		nonExistingUsername = "nemeu@gmail.com";
		
		user = UserFactory.createCustomClientUser(1L, existingUsername);
		userDetails = UserDetailsFactory.createCustomAdminUser(existingUsername);
		
		when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());
		
		when(repository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
		when(repository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
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
	
	@Test	
	public void authenticatedShouldReturnUserWhenUserExists() {
		when(customUserUtil.getLoggedUsername()).thenReturn(existingUsername);
		User result = service.authenticated();
		
		assertNotNull(result);
		assertEquals(result.getUsername(), existingUsername);
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
		doThrow(ClassCastException.class).when(customUserUtil).getLoggedUsername();
		
		assertThrows(UsernameNotFoundException.class, () -> {
			service.authenticated();
		});
	}
	
	@Test
	public void getMeShouldReturnUserDTOWhenUserAuthenticated() {
		UserService spyUserService = Mockito.spy(service);
		doReturn(user).when(spyUserService).authenticated();
		
		UserDTO result = spyUserService.getMe();
		
		assertNotNull(result);
		assertEquals(result.email(), existingUsername);
		
	}
	
	@Test
	public void getMeShouldReturnUsernameNotFoundExceptionWhenUserNotAuthenticated() {
		UserService spyUserService = Mockito.spy(service);
		doThrow(UsernameNotFoundException.class).when(spyUserService).authenticated();
		
		assertThrows(UsernameNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			UserDTO result = spyUserService.getMe();
		});
	}
}
