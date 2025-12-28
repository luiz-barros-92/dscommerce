package com.luizbarros.dscommerce.tests;

import com.luizbarros.dscommerce.entities.Category;
import com.luizbarros.dscommerce.entities.Product;

public class ProductFactory {

	public static Product createProduct() {
		Category category = CategoryFactory.createCategory();
		Product product = new Product(1L, "Playstation 5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor.", 4800.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/6-big.jpg");
		product.getCategories().add(category);
		return product;
	}
	
	public static Product createProduct(String name) {
		Product product = createProduct();
		product.setName(name);
		return product;
	}
}
