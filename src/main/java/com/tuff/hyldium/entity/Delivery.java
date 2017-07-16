package com.tuff.hyldium.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Delivery {

	@XmlTransient @GeneratedValue @Id
	public long id;
	
	public String name;	
	public long date;
	
	@ManyToOne(cascade=CascadeType.REFRESH)
	public Order order;
	
	public boolean isReceived = false;
}
