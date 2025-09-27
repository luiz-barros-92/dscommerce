package com.luizbarros.dscommerce.dto;

import com.luizbarros.dscommerce.entities.Product;

public record ProductMinDTO(
		Long id,		
		String name,			
		Double price,		
		String imgUrl) {

		public ProductMinDTO(Product entity) {
			this(
				entity.getId(),
				entity.getName(),				
				entity.getPrice(),
				entity.getImgUrl());
		}
}
