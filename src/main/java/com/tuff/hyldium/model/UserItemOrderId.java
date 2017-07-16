package com.tuff.hyldium.model;

import java.io.Serializable;

import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.Order;
import com.tuff.hyldium.entity.User;

public class UserItemOrderId implements Serializable {
	

	public Order order;
	public User user;
	public Item item;
	
	public UserItemOrderId(Order order, User user, Item item) {
	 	this.order = order;
	 	this.user = user;
	 	this.item =item;
	}

}
