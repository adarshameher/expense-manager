package me.ad.expensemanager.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.ad.expensemanager.entity.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}
