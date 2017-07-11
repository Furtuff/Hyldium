package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Order {

	@XmlTransient @GeneratedValue @Id public long id;
	public String dateName;
	public String name;
	public long date;
	public boolean isValidated = false; 
	
}
