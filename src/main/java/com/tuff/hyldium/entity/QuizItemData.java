package com.tuff.hyldium.entity;

import javax.persistence.Entity;

@Entity
public class QuizItemData {
	public Long itemId;
	public byte[] data;
	
	public QuizItemData(Long itemId, byte[] data) {
		this.itemId = itemId;
		this.data = data;
	}
}
