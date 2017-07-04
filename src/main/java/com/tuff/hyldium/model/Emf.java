package com.tuff.hyldium.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum Emf {
	instance;
	
	private  EntityManagerFactory emf;
	private  EntityManager em;
	
	public  EntityManager getEntityManager() {
		if(emf == null) {
			emf = Persistence.createEntityManagerFactory("HyldiumPU");
		}
		if(em == null) {
			em = emf.createEntityManager();
		}
		return em;
	}
}
