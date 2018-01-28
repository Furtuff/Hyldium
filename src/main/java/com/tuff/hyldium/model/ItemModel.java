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
	
	public ItemModel() {
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPriceHT() {
		return priceHT;
	}

	public void setPriceHT(double priceHT) {
		this.priceHT = priceHT;
	}

	public float getByBundle() {
		return byBundle;
	}

	public void setByBundle(float byBundle) {
		this.byBundle = byBundle;
	}

	public float getTVA() {
		return TVA;
	}

	public void setTVA(float tVA) {
		TVA = tVA;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Override
	public String toString() {

		return this.name + " " + this.label;
	}
}

