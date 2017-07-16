package com.tuff.hyldium.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class User {
	public String name;
	public byte[] secret;
	public byte[] nonce;
	public Long creationDate;
	public long date;
	
	@XmlTransient @Id @GeneratedValue public Long id;
	
	@OneToMany(cascade=CascadeType.REFRESH)
	public UserItemOrder userItemOrder;
	
	@OneToMany(cascade=CascadeType.REFRESH)
	public UserItemDelivery userItemDelivery;
	
	public User() {
	}
	
	public User(String name) {
		this.name = name;
		this.secret = new byte[32];
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof User && id != null && id.equals(((User)obj).id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
