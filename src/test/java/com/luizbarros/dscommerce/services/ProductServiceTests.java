package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.ProductDTO;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.repositories.ProductRepository;
import com.luizbarros.dscommerce.tests.ProductFactory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private Long existingProductId, nonExistingProductId;
	private String productName;
	private Product product;
	
	@BeforeEach
	void setUp() throws Exception {
		existingProductId = 1L;
		nonExistingProductId = 10000L;
		productName = "Playstation 5";
		product = ProductFactory.createProduct(productName);
		
		when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.findById(existingProductId);
		assertNotNull(result);
		assertEquals(result.id(), existingProductId);
		assertEquals(result.name(), product.getName());
	}

}
