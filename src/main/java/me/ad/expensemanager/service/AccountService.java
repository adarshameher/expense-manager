package me.ad.expensemanager.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.ad.expensemanager.model.Account;
import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.AccountRepository;
import me.ad.expensemanager.repo.UserRepository;

@Service
public class AccountService {
	
	private final static Logger log = LoggerFactory.getLogger(AccountService.class);
	
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
	
	public Account getAccountDetails(String accNo) {
		Account findByAccountNo = accountRepository.findByAccountNo(accNo);
		if(findByAccountNo != null) {
			return findByAccountNo;
		} else {
			throw new EntityNotFoundException("Account with accountNo " + accNo + " not found");
		}
	}
	
	public Account addAccountForUser(Account account, Long userId){
		doValidate(account);
		Optional<User> findUserById = userRepository.findById(userId);
		if(findUserById.isPresent()){
			List<Account> userAccountList = accountRepository.findByUser_UserId(userId);
			for(Account acc : userAccountList) {
				if(acc.getAccountNo().equals(account.getAccountNo())){
					throw new IllegalArgumentException("Account with accountNo: " + account.getAccountNo() + " is already exists for the User");
				}
			}
			// Add new Account to the User
			User user = findUserById.get();
			account.setUser(user);
			user.getAccounts().add(account);
			//Persist the new Account
			return accountRepository.save(account);
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}
	
	private void doValidate(Account account) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account);
	    if (constraintViolations.size() > 0 ) {
	    	log.error(
	    		constraintViolations.stream()
	    		.map(cv -> cv == null ? "null" : "Invalid " + cv.getPropertyPath() 
	    		+ " : " + cv.getMessage()).collect(Collectors.joining(", ")));
	    	throw new ConstraintViolationException("ConstraintViolationException", constraintViolations);
	    }
	}

}
