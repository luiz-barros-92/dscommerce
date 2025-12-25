package com.luizbarros.dscommerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.luizbarros.dscommerce.dto.CategoryDTO;
import com.luizbarros.dscommerce.entities.Category;
import com.luizbarros.dscommerce.repositories.CategoryRepository;
import com.luizbarros.dscommerce.tests.CategoryFactory;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTests {

	@InjectMocks
	private CategoryService service;
	
	@Mock
	private CategoryRepository repository;
	
	private Category category;
	private List<Category> list;
	
	@BeforeEach
	void setUp() throws Exception {
		category = CategoryFactory.createCategory();
		
		list = new ArrayList<>();
		list.add(category);
		
		when(repository.findAll()).thenReturn(list);
	}
	
	@Test
	public void findAllShouldReturnListCategoryDTO() {
		List<CategoryDTO> result = service.findAll();
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).id(), category.getId());
		assertEquals(result.get(0).name(), category.getName());
	}
	
}
