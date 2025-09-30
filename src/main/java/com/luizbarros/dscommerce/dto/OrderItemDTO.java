package com.luizbarros.dscommerce.dto;

import com.luizbarros.dscommerce.entities.OrderItem;

public record OrderItemDTO(
	Long productId,
	String name,
	Double price,
	Integer quantity,
	String imgUrl) {
	
	public OrderItemDTO(OrderItem entity) {
		this(
			entity.getProduct().getId(),
			entity.getProduct().getName(),
			entity.getPrice(),
			entity.getQuantity(),
			entity.getProduct().getImgUrl());		
	}
	
	public Double getSubTotal() {
        return price * quantity;
    }
}
