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

import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.UserRepository;

@Service
public class UserService {
	
	private final static Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User addUser(User user) {
		doValidate(user);
		user.getAccounts().forEach(acc -> acc.setUser(user));
		user.getCategories().forEach(cat -> cat.setUser(user));
		user.getExpenses().forEach(exp -> exp.setUser(user));
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
	
	public User getUserByMobileNo(Long mobileNo, boolean isSilent) {
		User findByMobileNo = userRepository.findByMobileNo(mobileNo);
		if(findByMobileNo != null) {
			return findByMobileNo;
		}else if(!isSilent){
			throw new EntityNotFoundException("User with mobileNo " + mobileNo + " not found");
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

	public User updateUserNameAndMobileById(Long userId, User user) {
		Optional<User> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			User findUser = findById.get();
			findUser.setName(user.getName());
			findUser.setMobileNo(user.getMobileNo());
			return userRepository.save(findUser);
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}

	public void removeUser(Long userId) {
		Optional<User> findById = userRepository.findById(userId);
		if(findById.isPresent()) {
			User findUser = findById.get();
			userRepository.delete(findUser);
		} else {
			throw new EntityNotFoundException("User with id " + userId + " not found");
		}
	}

}
