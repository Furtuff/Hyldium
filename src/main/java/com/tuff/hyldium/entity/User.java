package com.tuff.hyldium.entity;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class User {
	public String firstName;
	public String lastName;
	public String login;
	public byte[] photo;
	public byte[] secret;
	public byte[] nonce;
	public long date;
	public boolean isSuperuser;

	@XmlTransient
	@Id
	@GeneratedValue
	public Long id;

	@OneToMany(cascade = CascadeType.REFRESH)
	public Collection<UserItemOrder> userItemOrders;

	@OneToMany(cascade = CascadeType.REFRESH)
	public Collection<UserItemDelivery> userItemDeliveries;

	public User() {
	}

	public User(String firstName, String lastName, String login, byte[] secret, byte[] photo,boolean isSupeUser) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.secret = secret;
		this.photo = photo;
		this.isSuperuser = isSupeUser;
		this.date = Calendar.getInstance().getTimeInMillis();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof User && id != null && id.equals(((User) obj).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
