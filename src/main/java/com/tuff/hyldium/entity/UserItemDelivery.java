package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserItemDelivery {
	
	@Id
	@ManyToOne
	public ItemDelivery itemDelivery;
	
	@Id
	@ManyToOne
	public User user;
	
	public long date;
	public float bundlePart;

}
