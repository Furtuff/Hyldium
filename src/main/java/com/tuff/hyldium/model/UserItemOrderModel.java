package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.UserItemOrder;

public class UserItemOrderModel {
	
	public long orderId;
	public long userId;
	public long itemId;
	
	public long date;
	public float bundlePart;
	
	public UserItemOrderModel() {
		
	}
	
	public UserItemOrderModel(UserItemOrder userItemOrder) {
		this.orderId = userItemOrder.order.id;
		this.userId = userItemOrder.user.id;
		this.itemId = userItemOrder.item.id;
		this.date = userItemOrder.date;
		this.bundlePart = userItemOrder.bundlePart;
	}

}
