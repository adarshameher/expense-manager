package me.ad.expensemanager.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.ad.expensemanager.entity.Expense;

@Repository
@Transactional
public interface ExpenseRepository extends JpaRepository<Expense, String> {
	List<Expense> findByUser_Id(Long userId);
}
