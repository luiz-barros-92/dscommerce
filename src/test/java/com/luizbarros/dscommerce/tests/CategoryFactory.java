package com.luizbarros.dscommerce.tests;

import com.luizbarros.dscommerce.entities.Category;

public class CategoryFactory {

	public static Category createCategory() {
		return new Category(1L, "AudioBooks");
	}
	
	public static Category createCategory(Long id, String name) {
		return new Category(id, name);
	}
}
