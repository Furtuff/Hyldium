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
	
	
}
