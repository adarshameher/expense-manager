package me.ad.expensemanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long slNo;
	
	@NotNull
	private String accountId;
	
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
	
	public Account() {
		
	}
	public long getSlNo() {
		return slNo;
	}
	public void setSlNo(long slNo) {
		this.slNo = slNo;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
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
	@Override
	public String toString() {
		return "Account [slNo=" + slNo + ", accountId=" + accountId + ", accountName=" + accountName + ", balance="
				+ balance + "]";
	}
	
	

}
