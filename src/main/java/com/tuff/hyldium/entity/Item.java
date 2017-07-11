package com.tuff.hyldium.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Item {
	@Id @GeneratedValue public long id;
	public long date;
	public String reference;
	public String name;
	public double price;
	public double priceHT;
	public float byBundle;
	public float TVA;
	public String label;
	public Byte[] photo;
	public String barcode;
	
	@OneToMany
	public ItemOrder itemOrder;
	
	@OneToMany
	public ItemDelivery itemDelivery;
	
	public void copyFrom(Item another) {
		this.reference = another.reference;
		this.name = another.name;
		this.price = another.price;
		this.priceHT = another.priceHT;
		this.byBundle = another.byBundle;
		this.TVA = another.TVA;
		this.label = another.label;
		this.photo = another.photo;
		this.barcode = another.barcode;		
	}
}
