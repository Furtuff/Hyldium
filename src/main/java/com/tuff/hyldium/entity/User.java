package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class User {
	public String name;
	public byte[] secret;
	public byte[] nonce;
	public Long creationDate;
	
	@XmlTransient @Id @GeneratedValue public Long id;
	
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
