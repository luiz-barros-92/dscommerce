package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.ProductDTO;
import com.luizbarros.dscommerce.dto.ProductMinDTO;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.repositories.ProductRepository;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;
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
	private ProductDTO productDTO;
	private PageImpl<Product> page;
	
	@BeforeEach
	void setUp() throws Exception {
		existingProductId = 1L;
		nonExistingProductId = 10000L;
		productName = "Playstation 5";
		product = ProductFactory.createProduct(productName);
		productDTO = new ProductDTO(product);
		page = new PageImpl<>(List.of(product));
		
		when(repository.findById(existingProductId)).thenReturn(Optional.of(product));
		when(repository.findById(nonExistingProductId)).thenReturn(Optional.empty());
		when(repository.searchByName(any(), (Pageable)any())).thenReturn(page);
		when(repository.save(any())).thenReturn(product);
	}
	
	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		ProductDTO result = service.findById(existingProductId);
		assertNotNull(result);
		assertEquals(result.id(), existingProductId);
		assertEquals(result.name(), product.getName());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExists() {
		assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingProductId);
		});
	}
	
	@Test
	public void findAllShouldReturnPagedProductMinDTO() {
		Pageable pageable = PageRequest.of(0, 12);		
		Page<ProductMinDTO> result = service.findAll(productName, pageable);
		assertNotNull(result);
		assertEquals(result.getSize(), 1);
		assertEquals(result.iterator().next().name(), productName);
	}
	
	@Test
	public void insertShouldReturnProductDTO() {
		ProductDTO result = service.insert(productDTO);
		assertNotNull(result);
		assertEquals(product.getId(), result.id());
	}
}
