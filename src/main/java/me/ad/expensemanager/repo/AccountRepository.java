package me.ad.expensemanager.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.ad.expensemanager.entity.Account;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long>{
	List<Account> findByUser_Id(Long userId);

	Account findByAccountNo(String accNo);
	Account findByIdAndUser_Id(Long accId, Long userId);
}
