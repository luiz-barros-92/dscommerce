package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.OrderDTO;
import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.repositories.OrderRepository;
import com.luizbarros.dscommerce.services.exceptions.ForbiddenException;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;
import com.luizbarros.dscommerce.tests.OrderFactory;
import com.luizbarros.dscommerce.tests.UserFactory;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;

	@Mock
	private OrderRepository repository;

	@Mock
	private AuthService authService;

	private Long existingOrderId, nonExistingOrderId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;

	@BeforeEach
	void setUp() throws Exception {
		existingOrderId = 1L;
		nonExistingOrderId = 2L;

		admin = UserFactory.createCustomAdminUser(1L, "Ana");
		client = UserFactory.createCustomClientUser(2L, "Bob");

		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);

		when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
		when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());
	}

	@Test
	public void findByidShouldReturnOrderDTOWhenIdExistsAndAdminLogged() {

		doNothing().when(authService).validateSelfOrAdmin(any());

		OrderDTO result = service.findById(existingOrderId);

		assertNotNull(result);
		assertEquals(result.id(), existingOrderId);
	}

	@Test
	public void findByIdShouldReturnOrderDTOWhenIdExistsAndSelfClientLogged() {
		doNothing().when(authService).validateSelfOrAdmin(any());

		OrderDTO result = service.findById(existingOrderId);
		
		assertNotNull(result);
		assertEquals(result.id(), existingOrderId);
	}
	
	@Test
	public void findByIdShouldThrowsForbiddenExceptionWhenIdExistsAndOtherClientLogged() {
		doThrow(ForbiddenException.class).when(authService).validateSelfOrAdmin(any());
		
		assertThrows(ForbiddenException.class, () -> {
			OrderDTO result = service.findById(existingOrderId);
		});
	}
	
	@Test
	public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist() {
		doNothing().when(authService).validateSelfOrAdmin(any());
		
		assertThrows(ResourceNotFoundException.class, () -> {
			OrderDTO result = service.findById(nonExistingOrderId);
		});
	}
}
