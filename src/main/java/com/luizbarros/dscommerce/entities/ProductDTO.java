package com.luizbarros.dscommerce.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductDTO(Long id,
		
		@NotBlank(message = "Required field")
		@Size(min = 3, max = 80, message = "Name must have from 3 to 80 characters")
		String name,
		
		@NotBlank(message = "Required field")
		@Size(min = 10, message = "Description must have at least 10 characters") 
		String description,
		
		@Positive(message = "Price must be positive")
		Double price,
		
		String imgUrl) {

		public ProductDTO(Product entity) {
			this(
				entity.getId(),
				entity.getName(),
				entity.getDescription(),
				entity.getPrice(),
				entity.getImgUrl());
		}
}
