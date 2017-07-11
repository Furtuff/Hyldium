package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemDelivery {
	@Id
	@ManyToOne
	public Item item;
	
	@Id
	@ManyToOne
	public Delivery delivery;
	public long date;
	public float bundleNumber;
	public float bundlePart;
}
