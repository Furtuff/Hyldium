package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemOrder {
	@Id
	@ManyToOne
	public Item item;
	
	@Id
	@ManyToOne
	public Order order;
	
	@ManyToOne
	public UserItemOrder userItemOrder;
	
	public long date;
	public Float bundleNumber;

}
