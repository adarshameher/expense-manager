package me.ad.expensemanager.entity;

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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(max = 20, message = "CategoryName can't be of more than 20 characters")
	@Column(length = 20)
	private String name;
	
	@CreationTimestamp
	private Date createdDate;
	
	@UpdateTimestamp
	private Date modifiedDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id_FK")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Expense> expenses;
	
	public Category() {

	}
	public Category(String categoryName) {
		super();
		this.name = categoryName;
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
	public List<Expense> getExpenses() {
		return expenses;
	}
	@Override
	public String toString() {
		return "Category [categoryId=" + id + ", categoryName=" + name + ", createdDate=" + createdDate
				+ ", modifiedDate=" + modifiedDate + ", user.userId=" + user.getId()
				+ "]";
	}
	
	
	

}
