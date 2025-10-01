package com.luizbarros.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizbarros.dscommerce.dto.OrderDTO;
import com.luizbarros.dscommerce.dto.OrderItemDTO;
import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.entities.OrderItem;
import com.luizbarros.dscommerce.entities.OrderStatus;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.entities.User;
import com.luizbarros.dscommerce.repositories.OrderItemRepository;
import com.luizbarros.dscommerce.repositories.OrderRepository;
import com.luizbarros.dscommerce.repositories.ProductRepository;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		authService.validateSelfOrAdmin(order.getClient().getId());
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = service.authenticated();
		order.setClient(user);
		
		for(OrderItemDTO itemDto : dto.items()) {
			Product product = productRepository.getReferenceById(itemDto.productId());
			OrderItem item = new OrderItem(order, product, itemDto.quantity(), product.getPrice());
			order.getItems().add(item);
		}
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDTO(order);
	}	
}
