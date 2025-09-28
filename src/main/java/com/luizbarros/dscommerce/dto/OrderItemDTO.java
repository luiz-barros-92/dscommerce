package com.luizbarros.dscommerce.dto;

import com.luizbarros.dscommerce.entities.OrderItem;

public record OrderItemDTO(
	Long id,
	String name,
	Double price,
	Integer quantity) {
	
	public OrderItemDTO(OrderItem entity) {
		this(
			entity.getProduct().getId(),
			entity.getProduct().getName(),
			entity.getPrice(),
			entity.getQuantity());		
	}
	
	public Double getSubTotal() {
        return price * quantity;
    }
}
