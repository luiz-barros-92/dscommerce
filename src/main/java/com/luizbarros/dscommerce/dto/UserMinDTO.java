package com.luizbarros.dscommerce.dto;

import com.luizbarros.dscommerce.entities.User;

public record UserMinDTO(Long id, String name) {
	public UserMinDTO(User entity) {
		this(entity.getId(), entity.getName());
	}
}
