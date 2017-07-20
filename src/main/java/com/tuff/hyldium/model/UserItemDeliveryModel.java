package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.UserItemDelivery;

public class UserItemDeliveryModel {
	
	public long deliveryId;
	public long userId;
	public long itemId;
	
	public long date;
	public float bundlePart;
	public UserItemDeliveryModel(UserItemDelivery itemDelivery) {
		super();
		this.deliveryId = itemDelivery.delivery.id;
		this.userId = itemDelivery.user.id;
		this.itemId = itemDelivery.item.id;
		this.date = itemDelivery.date;
		this.bundlePart = itemDelivery.bundlePart;
	}
	
}
