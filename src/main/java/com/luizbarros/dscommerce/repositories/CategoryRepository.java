package com.luizbarros.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizbarros.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
