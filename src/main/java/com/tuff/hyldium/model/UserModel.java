package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.User;

public class UserModel {

	public long id;
	public String name;
	public byte[] password;
	public byte[] photo;
	
	
	public UserModel() {
	}


	public UserModel(User user) {
		this.id = user.id;
		this.name = user.name;
		this.photo = user.photo;
	}
	
	
}
