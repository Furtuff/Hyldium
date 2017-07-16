package com.tuff.hyldium.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

import com.tuff.hyldium.model.OrderModel;

@Entity
public class Order {

	@XmlTransient @GeneratedValue @Id public long id;
	public String dateName;
	public String name;
	public long date;
	public boolean isValidated = false; 
	
	public void copyFromOrderModel(OrderModel orderModel ) {
		this.dateName = orderModel.dateName;
		this.name = orderModel.name;
		this.date = Calendar.getInstance().getTimeInMillis();
	}
}
