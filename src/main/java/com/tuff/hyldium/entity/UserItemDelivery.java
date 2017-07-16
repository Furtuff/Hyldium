package com.tuff.hyldium.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.tuff.hyldium.model.UserItemDeliveryId;

@Entity
@IdClass(UserItemDeliveryId.class)
public class UserItemDelivery {
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	public Delivery delivery;
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	public User user;
	
	@Id
	@ManyToOne(cascade=CascadeType.REFRESH)
	public Item item;
	
	public long date;
	public float bundlePart;
	
	public UserItemDelivery(com.tuff.hyldium.entity.Delivery delivery, User user, Item item,
			float bundlePart) {
		this.delivery = delivery;
		this.user = user;
		this.item = item;
		this.date = Calendar.getInstance().getTimeInMillis();
		this.bundlePart = bundlePart;
	}

	
}
