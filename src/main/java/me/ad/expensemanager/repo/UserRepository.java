package me.ad.expensemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ad.expensemanager.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
