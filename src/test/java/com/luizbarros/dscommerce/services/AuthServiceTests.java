package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.services.exceptions.ForbiddenException;
import com.luizbarros.dscommerce.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

	@InjectMocks
	private AuthService service;

	@Mock
	private UserService userService;

	private User admin, selfClient, otherClient;

	@BeforeEach
	void setUp() throws Exception {
		admin = UserFactory.createAdminUser();
		selfClient = UserFactory.createCustomClientUser(1L, "Bob");
		otherClient = UserFactory.createCustomClientUser(2L, "Ana");
	}

	@Test
	public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
		when(userService.authenticated()).thenReturn(admin);

		Long userId = admin.getId();

		assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}

	@Test
	public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {
		when(userService.authenticated()).thenReturn(selfClient);

		Long userId = selfClient.getId();

		assertDoesNotThrow(() -> {
			service.validateSelfOrAdmin(userId);
		});
	}
	
	@Test
	public void validateSelfOrAdminThrowsForbiddenExceptionWhenOtherClientLogged() {
		when(userService.authenticated()).thenReturn(selfClient);
		
		Long userId = otherClient.getId();
		
		assertThrows(ForbiddenException.class, () -> {
			service.validateSelfOrAdmin(userId);
		});
	}
}
