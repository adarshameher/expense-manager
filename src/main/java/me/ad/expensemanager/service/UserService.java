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

import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.UserRepository;

@Service
public class UserService {
	
	private final static Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User user) {
		doValidate(user);
		return userRepository.save(user);
	}
	
	public User getUserById(Long userId, boolean isSilent) {
		Optional<User> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			return findById.get();
		}else if(!isSilent){
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}else {
			return null;
		}
	}
	
	private void doValidate(User user) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
	    if (constraintViolations.size() > 0 ) {
	    	log.error(
	    		constraintViolations.stream()
	    		.map(cv -> cv == null ? "null" : "Invalid " + cv.getPropertyPath() 
	    		+ " : " + cv.getMessage()).collect(Collectors.joining(", ")));
	    	throw new ConstraintViolationException("ConstraintViolationException", constraintViolations);
	    }
	}

}
