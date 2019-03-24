package me.ad.expensemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ad.expensemanager.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
