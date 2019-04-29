package me.ad.expensemanager.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(max = 30, message = "AccountNo can't be of more than 30 characters")
	@Column(length = 30)
	private String accountNo;
	
	@NotNull
	@Size(max = 30, message = "AccountName can't be of more than 30 characters")
	@Column(length = 30)
	private String accountName;
	
	@NotNull
	private double balance;
	
	@CreationTimestamp
	private Date createdDate;
	
	@UpdateTimestamp
	private Date modifiedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id_FK")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "fromAccount")
	private List<Expense> fromAccExpenses = new ArrayList<Expense>();
	
	@OneToMany(mappedBy = "toAccount")
	private List<Expense> toAccExpenses = new ArrayList<Expense>();
	
	public Account() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Expense> getFromAccExpenses() {
		return fromAccExpenses;
	}
	public List<Expense> getToAccExpenses() {
		return toAccExpenses;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNo=" + accountNo + ", accountName=" + accountName + ", balance="
				+ balance + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", user.userId=" + user.getId() + "]";
	}
	
	
	

}
