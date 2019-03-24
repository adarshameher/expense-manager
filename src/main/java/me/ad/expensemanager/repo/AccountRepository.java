package me.ad.expensemanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ad.expensemanager.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
}
