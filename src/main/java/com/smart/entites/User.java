package com.smart.entites;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;


@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int Id;
	
	@NotBlank(message="UserName cannot be blank")
	@Size(min=3,max=13,message="UserName should be between 3 to 12 characters")
	private String Name;
	
	@Column(unique=true)
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message ="Please enter valid email id")
	private String Email;
	
	
	private String Password;
	
	private String Imageurl;
	@Column(length=500)
	private String Description;
	private String Role;
	private boolean Enabled;
	
	@AssertTrue(message="must agree terms snd conditions")
	private boolean agreement;
	
	


	public boolean isAgreement() {
		return agreement;
	}


	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}


	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy="user",orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		this.Password = password;
	}


	public String getImageurl() {
		return Imageurl;
	}


	public void setImageurl(String imageurl) {
		Imageurl = imageurl;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		this.Description = description;
	}


	public String getRole() {
		return Role;
	}


	public void setRole(String role) {
		Role = role;
	}


	public boolean isEnabled() {
		return Enabled;
	}


	public void setEnabled(boolean enabled) {
		this.Enabled = enabled;
	}


	public List<Contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}


	@Override
	public String toString() {
		return "User [Id=" + Id + ", Name=" + Name + ", Email=" + Email + ", Password=" + Password + ", Imageurl="
				+ Imageurl + ", Description=" + Description + ", Role=" + Role + ", Enabled=" + Enabled + ", contacts="
				+ contacts + "]";
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.Id == ((User)obj).getId();
	}
	
	


	
	
	
	
	

}
