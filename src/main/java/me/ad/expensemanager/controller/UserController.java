package me.ad.expensemanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.UserRepository;
import me.ad.expensemanager.service.UserService;

@RestController
@RequestMapping("/userService")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/users")
	public @ResponseBody List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/users/{userId}")
	public @ResponseBody User getUserById(@PathVariable("userId") Long userId) {
		return userService.getUserById(userId, false);
	}
	
	@GetMapping(path = "/users/{userId}/silent")
	public @ResponseBody User getUserByIdSilent(@PathVariable("userId") Long userId) {
		return userService.getUserById(userId, true);
	}
	
	@PostMapping(path = "/users")
	public @ResponseBody User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
}
