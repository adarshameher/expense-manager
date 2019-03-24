package me.ad.expensemanager.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.ad.expensemanager.model.Account;
import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.AccountRepository;
import me.ad.expensemanager.repo.UserRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;

	public List<Account> getAllAccountsByUserId(Long userId) {
		Optional<User> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			List<Account> findByUserId = accountRepository.findByUser_UserId(userId);
			return findByUserId;
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}

}
