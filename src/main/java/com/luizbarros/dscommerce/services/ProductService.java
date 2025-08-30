package com.luizbarros.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luizbarros.dscommerce.dto.ProductDTO;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id).get();
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		entity.setName(dto.name());
		entity.setDescription(dto.description());
		entity.setPrice(dto.price());
		entity.setImgUrl(dto.imgUrl());
		repository.save(entity);
		return new ProductDTO(entity);		
	}
}
