package com.tuff.hyldium.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}
