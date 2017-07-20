package com.tuff.hyldium.model;

import com.tuff.hyldium.entity.Item;

public class ItemModel {
	public long id;
	public long date;
	public String reference;
	public String name;
	public double price;
	public double priceHT;
	public float byBundle;
	public float TVA;
	public String label;
	public Byte[] photo;
	public String barCode;	
	
	public ItemModel(Item item) {
		this.id = item.id;
		this.date = item.date;
		this.reference = item.reference;
		this.name = item.name;
		this.price = item.price;
		this.priceHT = item.priceHT;
		this.byBundle = item.byBundle;
		this.TVA =item.TVA;
		this.label = item.label;
		this.photo = item.photo;
		this.barCode = item.barCode;
		
	}
}

