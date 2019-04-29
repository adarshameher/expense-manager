package me.ad.expensemanager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(max = 30, message = "Name of User can't be more than 30 characters")
	@Column(length = 30)
	private String name;
	
	@NotNull
	@Min(value = 1000000000L, message = "Mobile Number should be a 10 digit number")
	@Max(value = 9999999999L, message = "Mobile Number should be a 10 digit number")
	@Column(length = 10, unique = true)
	private long mobileNo;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	//@Fetch(value = FetchMode.SUBSELECT)
	private List<Account> accounts = new ArrayList<Account>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	//@Fetch(value = FetchMode.SUBSELECT)
	private List<Category> categories = new ArrayList<Category>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	//@Fetch(value = FetchMode.SUBSELECT)
	private List<Expense> expenses = new ArrayList<Expense>();
	
	@CreationTimestamp
	private Date createdDate;
	
	@UpdateTimestamp
	private Date modifiedDate;
	
	public User() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public List<Account> getAccounts() {
		if(this.accounts == null) {
			this.accounts = new ArrayList<Account>();
		}
		return accounts;
	}
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	public void removeAccount(Account account) {
		this.accounts.remove(account);
	}
	public List<Category> getCategories() {
		if(this.categories == null) {
			this.categories = new ArrayList<Category>();
		}
		return categories;
	}
	public void addCategory(Category category) {
		this.categories.add(category);
	}
	public void removeCategory(Category category) {
		this.categories.remove(category);
	}
	public List<Expense> getExpenses() {
		if(this.expenses == null) {
			this.expenses = new ArrayList<Expense>();
		}
		return expenses;
	}
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}
	public void removeExpense(Expense expense) {
		this.expenses.remove(expense);
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", mobileNo=" + mobileNo
				+ ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
}
