package me.ad.expensemanager.cmd;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import me.ad.expensemanager.model.Account;
import me.ad.expensemanager.model.Category;
import me.ad.expensemanager.model.User;
import me.ad.expensemanager.repo.UserRepository;

@Component
public class UserCommandLineRunner implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(addUser());
	}
	
	private User addUser() {
		User user = new User();
		user.setName("Aditya");
		user.setMobileNo(9988776655L);
		
		Account account = new Account();
		account.setAccountNo("112233");
		account.setAccountName("SBI");
		account.setBalance(0);
		account.setUser(user);
		user.getAccounts().add(account);
		
		List<Category> categories = user.getCategories();
		
		Category cat = new Category();
		cat.setCategoryName("Education");
		cat.setUser(user);
		categories.add(cat);
		cat = new Category();
		cat.setCategoryName("Transport");
		cat.setUser(user);
		categories.add(cat);
		
		user.setCategories(categories);
		
		return user;
	}

}
