package com.luizbarros.dscommerce.dto;

import java.time.Instant;
import java.util.List;

import com.luizbarros.dscommerce.entities.Order;
import com.luizbarros.dscommerce.entities.OrderStatus;

import jakarta.validation.constraints.NotEmpty;

public record OrderDTO(
	Long id,
	Instant moment,
	OrderStatus status,
	UserMinDTO user,
	PaymentDTO payment,
	
	@NotEmpty(message = "Order cannot be empty")
	List<OrderItemDTO> items) {
	
	public OrderDTO(Order entity) {
		this(
			entity.getId(),
			entity.getMoment(),
			entity.getStatus(),
			new UserMinDTO(entity.getClient()),
			entity.getPayment() != null ? new PaymentDTO(entity.getPayment()) : null,
			entity.getItems().stream().map(OrderItemDTO::new).toList());		
	}
	
	public Double getTotal() {
		double sum = 0.0;
		for (OrderItemDTO item : items) {
			sum += item.getSubTotal();
		}
		return sum;
	}
}
