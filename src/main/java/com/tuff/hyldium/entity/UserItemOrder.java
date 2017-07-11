package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserItemOrder {

	@Id
	@ManyToOne
	public ItemOrder itemOrder;
	
	@Id
	@ManyToOne
	public User user;
	
	public long date;
	public float bundlePart;
	
}
