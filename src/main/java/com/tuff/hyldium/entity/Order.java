package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Order {

	@XmlTransient @GeneratedValue @Id public long id;
	public String date;
	public String name;
	public boolean isValidated = false; 
	
}
