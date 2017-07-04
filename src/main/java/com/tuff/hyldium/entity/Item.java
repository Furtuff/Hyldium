package com.tuff.hyldium.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Item {
	@XmlTransient @Id @GeneratedValue public long id;
	
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

}
