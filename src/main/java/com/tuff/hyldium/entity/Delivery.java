package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Delivery {

	@XmlTransient @GeneratedValue @Id
	public long id;
	
	public long date;
	
	@ManyToOne
	public Order order;
	
	public boolean isReceived = false;
}
