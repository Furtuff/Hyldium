package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.Delivery;

public class DeliveryModel {
	public long id;
	public String name;
	public long orderId;
	public long date;
	public boolean isReceived;

	public DeliveryModel() {
	}

	public DeliveryModel(Delivery delivery) {
		this.id = delivery.id;
		this.name = delivery.name;
		this.orderId = delivery.order.id;
		this.date = delivery.date;
		this.isReceived = delivery.isReceived;
	}
}
