package me.ad.expensemanager.cmd;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.ad.expensemanager.entity.Account;
import me.ad.expensemanager.entity.Category;
import me.ad.expensemanager.entity.Expense;
import me.ad.expensemanager.entity.TransactionType;
import me.ad.expensemanager.entity.User;
import me.ad.expensemanager.repo.ExpenseRepository;
import me.ad.expensemanager.repo.UserRepository;
import me.ad.expensemanager.service.ExpenseService;

@Component
public class UserCommandLineRunner implements CommandLineRunner{
	
	private final static Logger logger = LoggerFactory.getLogger(UserCommandLineRunner.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		addUser();
		addExpense(1L);
		getExpense();
	}
	
	@Transactional
	private void getExpense() {
		User user = userRepository.findById(1L).get();
		logger.info("User expenses -> {}", user.getExpenses());
	}

	@Transactional
	private void addUser() {
		User user = new User();
		user.setName("Aditya");
		user.setMobileNo(9988776655L);
		
		Account account = new Account();
		account.setAccountNo("112233");
		account.setAccountName("SBI");
		account.setBalance(1000);
		account.setUser(user);
		user.addAccount(account);
		
		Category cat = new Category();
		cat.setName("Education");
		cat.setUser(user);
		user.addCategory(cat);
		cat = new Category();
		cat.setName("Transport");
		cat.setUser(user);
		user.addCategory(cat);
		
		User user1 = userRepository.save(user);
		
		logger.info("User -> {}", user1);
		logger.info("User Accounts -> {}", user1.getAccounts());
		logger.info("User Categories -> {}", user1.getCategories());
	}
	
	@Transactional
	private void addExpense(Long userId) {
		User user = userRepository.findById(userId).get();
		Category cat = user.getCategories().get(0);
		logger.info("Category: " + cat);
		Expense expense = new Expense();
		expense.setAmount(50);
		expense.setCategory(cat);
		expense.setDescription("Cab from office to room");
		expense.setFromAccount(user.getAccounts().get(0));
		expense.setTxnType(TransactionType.DEBIT);
		expense = expenseService.addExpenseForUser(user.getId(), expense);
		
		logger.info("Expense -> {}", expense);
	}

}
