package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.User;

public class UserModel {

	public long id;
	public String firstName;
	public String lastName;
	public String login;
	public byte[] password;
	public byte[] photo;
	public boolean isSU;
	
	
	public UserModel() {
	}


	public UserModel(User user) {
		this.id = user.id;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.login = user.login;
		this.photo = user.photo;
		this.isSU= user.isSuperuser;
	}
	
	
}
