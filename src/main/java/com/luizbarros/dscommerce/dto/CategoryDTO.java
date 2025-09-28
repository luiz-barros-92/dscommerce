package com.luizbarros.dscommerce.dto;

import com.luizbarros.dscommerce.entities.Category;

public record CategoryDTO(Long id, String name) {	
	public CategoryDTO(Category entity) {
		this(entity.getId(), entity.getName());
	}
}
