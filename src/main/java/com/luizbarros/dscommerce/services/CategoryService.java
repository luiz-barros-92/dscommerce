package com.luizbarros.dscommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizbarros.dscommerce.dto.CategoryDTO;
import com.luizbarros.dscommerce.entities.Category;
import com.luizbarros.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {	
	
	private final CategoryRepository repository;
		
	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> result = repository.findAll();
		return result.stream().map(x -> new CategoryDTO(x)).toList();
	}	
}
