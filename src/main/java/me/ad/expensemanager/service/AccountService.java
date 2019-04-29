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

import me.ad.expensemanager.entity.Account;
import me.ad.expensemanager.entity.User;
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
			List<Account> findByUserId = accountRepository.findByUser_Id(userId);
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
			List<Account> userAccountList = accountRepository.findByUser_Id(userId);
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
	
	public Account updateAccountForUserByAccountId(Account account, Long accId, Long userId) {
		doValidate(account);
		Optional<User> findUserById = userRepository.findById(userId);
		if(findUserById.isPresent()){
			Optional<Account> findAccountById = accountRepository.findById(accId);
			if(findAccountById.isPresent()){
				Account acc = findAccountById.get();
				// Update Account
				if(account.getAccountName() != null && account.getAccountName().length() > 0) acc.setAccountName(account.getAccountName());
				if(account.getAccountNo() != null && account.getAccountNo().length() > 0) acc.setAccountNo(account.getAccountNo());
				acc.setBalance(account.getBalance());
				//Persist the new Account
				return accountRepository.save(account);
			} else {
				throw new IllegalArgumentException("Account with id: " + accId + " doesn't exist for the User");
			}
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}
	
	public void removeAccountForUserByAccountId(Long accId, Long userId) {
		Account findBySlNoAndUserId = accountRepository.findByIdAndUser_Id(accId, userId);
		if(findBySlNoAndUserId == null) {
			throw new IllegalArgumentException("Account with id: " + accId + " doesn't exist for the User id: " + userId);
		} else {
			accountRepository.delete(findBySlNoAndUserId);
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
