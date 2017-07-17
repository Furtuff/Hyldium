package com.tuff.hyldium.entity;


import java.util.Calendar;

import javax.jdo.annotations.Persistent;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.tuff.hyldium.model.ItemModel;

@Entity
public class Item {
	@Id @GeneratedValue public long id;
	public long date;
	public String reference;
	@Persistent
	public String name;
	public double price;
	public double priceHT;
	public float byBundle;
	public float TVA;
	public String label;
	public Byte[] photo;
	public String barCode;
	
	@OneToMany(cascade=CascadeType.REFRESH)
	public ItemOrder itemOrder;
	
	@OneToMany(cascade=CascadeType.REFRESH)
	public ItemDelivery itemDelivery;
	public Item() {
		
	}
	public Item(ItemModel another) {
		this.date = Calendar.getInstance().getTimeInMillis();
		this.reference = another.reference;
		this.name = another.name;
		this.price = another.price;
		this.priceHT = another.priceHT;
		this.byBundle = another.byBundle;
		this.TVA = another.TVA;
		this.label = another.label;
		this.photo = another.photo;
		this.barCode = another.barCode;		
	}
	public void copyFrom(ItemModel another) {
		this.reference = another.reference;
		this.name = another.name;
		this.price = another.price;
		this.priceHT = another.priceHT;
		this.byBundle = another.byBundle;
		this.TVA = another.TVA;
		this.label = another.label;
		this.photo = another.photo;
		this.barCode = another.barCode;		
	}
}
