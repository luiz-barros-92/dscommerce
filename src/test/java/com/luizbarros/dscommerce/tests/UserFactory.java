package com.luizbarros.dscommerce.tests;

import java.time.LocalDate;

import com.luizbarros.dscommerce.entities.Role;
import com.luizbarros.dscommerce.entities.User;

public class UserFactory {

	public static User createClientUser() {
		User user = new User(1L, "Abreu", "abreu@gmail.com", "98544785", LocalDate.parse("2000-05-21"), "$2a$10$o2GPD.GBPQKHBD7MqDL7iupjA.DAzmiMbcxE8Vb1GDZu.zgQNvkMO");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createAdminUser() {
		User user = new User(2L, "Nem Eu", "nemeu@gmail.com", "15484441", LocalDate.parse("1998-07-20"), "$2a$10$o2GPD.GBPQKHBD7MqDL7iupjA.DAzmiMbcxE8Vb1GDZu.zgQNvkMO");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}
	
	public static User createCustomClientUser(Long id, String username) {
		User user = new User(id, "Abreu", username, "98544785", LocalDate.parse("1998-07-20"), "$2a$10$o2GPD.GBPQKHBD7MqDL7iupjA.DAzmiMbcxE8Vb1GDZu.zgQNvkMO");
		user.addRole(new Role(1L, "ROLE_CLIENT"));
		return user;
	}
	
	public static User createCustomAdminUser(Long id, String username) {
		User user = new User(id, "Nem Eu", username, "15484441", LocalDate.parse("1998-07-20"), "$2a$10$o2GPD.GBPQKHBD7MqDL7iupjA.DAzmiMbcxE8Vb1GDZu.zgQNvkMO");
		user.addRole(new Role(2L, "ROLE_ADMIN"));
		return user;
	}
}
