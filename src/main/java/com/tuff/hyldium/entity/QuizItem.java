package com.tuff.hyldium.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class QuizItem {
	
	@XmlTransient @Id @GeneratedValue
	public Long id;
	
	public Long tag;
}
