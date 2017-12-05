package com.tuff.hyldium.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tuff.hyldium.entity.User;
@JsonIgnoreProperties(ignoreUnknown= true)
public class UserModel {

	public long id;
	public String firstName;
	public String lastName;
	public String login;
	public byte[] password;
	public byte[] photo;
	public List<String> role;
	
	
	public UserModel() {
	}


	public UserModel(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.login = user.login;
		this.photo = user.photo;
		this.role= user.role;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public List<String> getRole() {
		return role;
	}


	public void setRole(List<String> role) {
		this.role = role;
	}


	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	
}
