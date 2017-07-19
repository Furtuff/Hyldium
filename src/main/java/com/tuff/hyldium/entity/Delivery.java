package com.tuff.hyldium.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Delivery {

	@XmlTransient @GeneratedValue @Id
	public long id;
	
	public String name;	
	public long date;
	
	@ManyToOne
	public Order order;
	@OneToMany
	public Collection<UserItemDelivery> deliveries;
	
	public boolean isReceived = false;
}
