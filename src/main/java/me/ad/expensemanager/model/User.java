package me.ad.expensemanager.model;

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
	private long userId;
	
	@NotNull
	@Size(max = 30, message = "Name of User can't be more than 30 characters")
	@Column(length = 30)
	private String name;
	
	@NotNull
	@Min(value = 1000000000L, message = "Mobile Number should be a 10 digit number")
	@Max(value = 9999999999L, message = "Mobile Number should be a 10 digit number")
	@Column(length = 10, unique = true)
	private long mobileNo;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_FK")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Account> accounts;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_FK")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Category> categories;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id_FK")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Expense> expenses;
	
	@CreationTimestamp
	private Date createdDate;
	
	@UpdateTimestamp
	private Date modifiedDate;
	
	public User() {
		super();
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public List<Category> getCategories() {
		if(this.categories == null) {
			this.categories = new ArrayList<Category>();
		}
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public List<Expense> getExpenses() {
		if(this.expenses == null) {
			this.expenses = new ArrayList<Expense>();
		}
		return expenses;
	}
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
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
		return "User [userId=" + userId + ", name=" + name + ", mobileNo=" + mobileNo + ", accounts=" + accounts
				+ ", categories=" + categories + ", expenses=" + expenses + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + "]";
	}
	
}
