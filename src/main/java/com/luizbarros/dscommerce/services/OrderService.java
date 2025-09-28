package com.luizbarros.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizbarros.dscommerce.dto.OrderDTO;
import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.repositories.OrderRepository;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new OrderDTO(order);
	}
}
