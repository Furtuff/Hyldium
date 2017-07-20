package com.tuff.hyldium.model;

import java.io.Serializable;

import com.tuff.hyldium.entity.Delivery;
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.User;

public class UserItemDeliveryId implements Serializable {
	
	public User user;
	public Delivery delivery;
	public Item item;
	
	public UserItemDeliveryId(User user, Delivery delivery, Item item) {
		super();
		this.user = user;
		this.delivery = delivery;
		this.item = item;
	}

	public UserItemDeliveryId() {
	}
	
	

}
