package com.luizbarros.dscommerce.dto;

import java.time.LocalDate;
import java.util.List;

import com.luizbarros.dscommerce.entities.Role;
import com.luizbarros.dscommerce.entities.User;

public record UserDTO(
	Long id,
    String name,
    String email,
    String phone,
    LocalDate birthDate,
    List<String> roles) {
	
	public UserDTO(User entity) {
		this(
			entity.getId(),
			entity.getName(),
			entity.getEmail(),
			entity.getPhone(),
			entity.getBirthDate(),
			entity.getRoles().stream().map(Role::getAuthority).toList()
		);
	}
}
