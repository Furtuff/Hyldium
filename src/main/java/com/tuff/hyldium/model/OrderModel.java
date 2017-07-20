package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.Order;

public class OrderModel {
	public String dateName;
	public String name;
	public boolean isValidated;
	public long id;
	
	public OrderModel() {
	}

	public OrderModel(Order order) {
		super();
		this.dateName = order.dateName;
		this.name = order.name;
		this.isValidated = order.isValidated;
		this.id = order.id;
	}
	

}
