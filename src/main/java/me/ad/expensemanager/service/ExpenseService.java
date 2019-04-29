package me.ad.expensemanager.service;

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

import me.ad.expensemanager.entity.Expense;
import me.ad.expensemanager.entity.User;
import me.ad.expensemanager.repo.ExpenseRepository;
import me.ad.expensemanager.repo.UserRepository;

@Service
public class ExpenseService {
	
private final static Logger log = LoggerFactory.getLogger(ExpenseService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	public Expense addExpenseForUser(Long userId, Expense expense) {
		doValidate(expense);
		Optional<User> findUserById = userRepository.findById(userId);
		if(findUserById.isPresent()){
			User user = findUserById.get();
			expense.setUser(user);
			user.addExpense(expense);
			System.out.println(expense.getCategory());
			return expenseRepository.save(expense);
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}
	
	private void doValidate(Expense expense) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    Set<ConstraintViolation<Expense>> constraintViolations = validator.validate(expense);
	    if (constraintViolations.size() > 0 ) {
	    	log.error(
	    		constraintViolations.stream()
	    		.map(cv -> cv == null ? "null" : "Invalid " + cv.getPropertyPath() 
	    		+ " : " + cv.getMessage()).collect(Collectors.joining(", ")));
	    	throw new ConstraintViolationException("ConstraintViolationException", constraintViolations);
	    }
	}

}
