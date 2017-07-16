package com.tuff.hyldium.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
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
	@ManyToOne(cascade=CascadeType.REFRESH)
	public Order order;
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	public User user;
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	public Item item;
	
	public long date;
	public float bundlePart;
	
	public UserItemOrder(User user, Order order, Item item, UserItemOrderModel userItemOrderModel) {
		this.order = order;
		this.user = user;
		this.item = item;
		this.date = Calendar.getInstance().getTimeInMillis();
		this.bundlePart = userItemOrderModel.bundlePart;
	}
	
}
