package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.OrderDTO;
import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.entities.OrderItem;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.repositories.OrderItemRepository;
import com.luizbarros.dscommerce.repositories.OrderRepository;
import com.luizbarros.dscommerce.repositories.ProductRepository;
import com.luizbarros.dscommerce.services.exceptions.ForbiddenException;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;
import com.luizbarros.dscommerce.tests.OrderFactory;
import com.luizbarros.dscommerce.tests.ProductFactory;
import com.luizbarros.dscommerce.tests.UserFactory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

	@InjectMocks
	private OrderService service;

	@Mock
	private OrderRepository repository;

	@Mock
	private AuthService authService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private OrderItemRepository orderItemRepository;
	
	@Mock
	private UserService userService;

	private Long existingOrderId, nonExistingOrderId;
	private Long existingProductId, nonExistingProductId;
	private Order order;
	private OrderDTO orderDTO;
	private User admin, client;
	private Product product;

	@BeforeEach
	void setUp() throws Exception {
		existingOrderId = 1L;
		nonExistingOrderId = 2L;
		
		existingProductId = 1L;
		nonExistingProductId = 2L;

		admin = UserFactory.createCustomAdminUser(1L, "Ana");
		client = UserFactory.createCustomClientUser(2L, "Bob");

		order = OrderFactory.createOrder(client);
		orderDTO = new OrderDTO(order);

		product = ProductFactory.createProduct();
		
		when(repository.findById(existingOrderId)).thenReturn(Optional.of(order));
		when(repository.findById(nonExistingOrderId)).thenReturn(Optional.empty());
		
		when(productRepository.getReferenceById(existingProductId)).thenReturn(product);
		when(productRepository.getReferenceById(nonExistingProductId)).thenThrow(EntityNotFoundException.class);
		
		when(repository.save(any())).thenReturn(order);
		when(orderItemRepository.saveAll(any())).thenReturn(new ArrayList<>(order.getItems()));
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
	
	@Test
	public void insertShouldReturnOrderDToWhenAdminLogged() {
		when(userService.authenticated()).thenReturn(admin);		
		OrderDTO result = service.insert(orderDTO);		
		assertNotNull(result);		
	}
	
	@Test
	public void insertShouldReturnOrderDToWhenClientLogged() {
		when(userService.authenticated()).thenReturn(client);		
		OrderDTO result = service.insert(orderDTO);		
		assertNotNull(result);		
	}
	
	@Test
	public void insertShouldThrowsUserNotFoundExceptionWhenUserNotLooged() {
		doThrow(UsernameNotFoundException.class).when(userService).authenticated();
		order.setClient(new User());
		orderDTO = new OrderDTO(order);
		assertThrows(UsernameNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
	
	@Test
	public void insertShouldThrowsEntityNotFoundExceptionWhenOrderProductIdDoesNotExist() {
		when(userService.authenticated()).thenReturn(client);
		product.setId(nonExistingProductId);
		OrderItem orderItem = new OrderItem(order, product, 2, 10.0);
		order.getItems().add(orderItem);
		orderDTO = new OrderDTO(order);		
		assertThrows(EntityNotFoundException.class, () -> {
			OrderDTO result = service.insert(orderDTO);
		});
	}
	
}
