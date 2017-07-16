package com.tuff.hyldium.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.tuff.hyldium.model.UserItemOrderId;
import com.tuff.hyldium.model.UserItemOrderModel;

@Entity
@IdClass(UserItemOrderId.class)
public class UserItemOrder {

	@Id
	@ManyToOne
	public Order Order;
	
	@Id
	@ManyToOne
	public User user;
	
	@Id
	@ManyToOne
	public Item item;
	
	public long date;
	public float bundlePart;
	
	public void copyFromUserItemOrderModel(User user, Order order, Item item, UserItemOrderModel userItemOrderModel) {
		this.Order = order;
		this.user = user;
		this.item = item;
		this.date = Calendar.getInstance().getTimeInMillis();
		this.bundlePart = userItemOrderModel.bundlePart;
	}
	
}
