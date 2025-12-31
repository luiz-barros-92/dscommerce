package com.luizbarros.dscommerce.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luizbarros.dscommerce.dto.CategoryDTO;
import com.luizbarros.dscommerce.dto.ProductDTO;
import com.luizbarros.dscommerce.dto.ProductMinDTO;
import com.luizbarros.dscommerce.entities.Category;
import com.luizbarros.dscommerce.entities.Product;
import com.luizbarros.dscommerce.repositories.ProductRepository;
import com.luizbarros.dscommerce.services.exceptions.DatabaseException;
import com.luizbarros.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {	
	
	private final ProductRepository repository;	
	
	public ProductService(ProductRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x -> new ProductMinDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		DtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);		
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getReferenceById(id);
			DtoToEntity(dto, entity);
			repository.save(entity);
			return new ProductDTO(entity);
		} 
		catch (EntityNotFoundException e){
			throw new ResourceNotFoundException("Resource not found");
		}		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Resource not found");
		}
		try {
	        repository.deleteById(id);    		
		}
	    catch (DataIntegrityViolationException e) {
	        throw new DatabaseException("Referential integrity violation");
	   	}
	}
	
	private void DtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.name());
		entity.setDescription(dto.description());
		entity.setPrice(dto.price());
		entity.setImgUrl(dto.imgUrl());
		entity.getCategories().clear();
        for (CategoryDTO catDto : dto.categories()) {
        	Category cat = new Category();
        	cat.setId(catDto.id());
        	entity.getCategories().add(cat);
        }
	}
}
