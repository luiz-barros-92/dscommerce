package com.luizbarros.dscommerce.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.OrderDTO;
import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.repositories.OrderRepository;
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
	void setUp() {
		existingOrderId = 1L;
		nonExistingOrderId = 2L;
		
		admin = UserFactory.createCustomAdminUser(1L, "Ana");
		client = UserFactory.createCustomClientUser(2L, "Bob");
		
		order = OrderFactory.createOrder(client);
		
		orderDTO = new OrderDTO(order);
		
		when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
		when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());		
	}

	

}
