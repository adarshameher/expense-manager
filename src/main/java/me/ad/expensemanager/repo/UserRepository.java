package me.ad.expensemanager.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.ad.expensemanager.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	User findByMobileNo(Long mobileNo);
	
}
