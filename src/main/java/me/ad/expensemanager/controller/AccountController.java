package me.ad.expensemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.ad.expensemanager.model.Account;
import me.ad.expensemanager.service.AccountService;

@RestController
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping(path = "/accounts")
	public List<Account> getAllAccountsByUserId(@RequestParam("userId") Long userId) {
		return accountService.getAllAccountsByUserId(userId);
	}
	
}
