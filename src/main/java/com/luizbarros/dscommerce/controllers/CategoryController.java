package com.luizbarros.dscommerce.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizbarros.dscommerce.dto.CategoryDTO;
import com.luizbarros.dscommerce.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	private final CategoryService service;	
	
	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> dto = service.findAll();
		return ResponseEntity.ok(dto) ;
	}
}
