package com.tuff.hyldium.listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.objectdb.Enhancer;

public class HyldiumListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {

		Enhancer.enhance("com.tuff.hyldium");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("HyldiumPU");
		e.getServletContext().setAttribute("emf", emf);

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		EntityManagerFactory emf = (EntityManagerFactory) e.getServletContext().getAttribute("emf");
		emf.close();

	}

}
