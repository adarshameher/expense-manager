package me.ad.expensemanager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.ad.expensemanager.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	List<Account> findByUser_UserId(Long userId);

	Account findByAccountNo(String accNo);
}
