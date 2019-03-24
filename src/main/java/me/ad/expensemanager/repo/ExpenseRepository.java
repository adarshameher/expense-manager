package me.ad.expensemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ad.expensemanager.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
	
}
