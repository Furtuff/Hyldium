package com.tuff.hyldium.listener;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import com.objectdb.Enhancer;
import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.UserModel;

public class HyldiumListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		EntityManagerFactory emf = (EntityManagerFactory) e.getServletContext().getAttribute("emf");
		emf.close();

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {

		Enhancer.enhance("com.tuff.hyldium.entity.*");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("HyldiumPU");
		e.getServletContext().setAttribute("emf", emf);
		if(Dao.getItemsList(0).isEmpty()) {
			Search s = new Search();
			Dao.copyItems();
		}
		if(Dao.getUserList().isEmpty()) {
			UserModel baseSu = new UserModel();
			baseSu.firstName ="jean";
			baseSu.lastName ="valjean";
			baseSu.login= "SUperman";
			baseSu.isSU = true;
			baseSu.password = "password".getBytes();
			Dao.addUser(baseSu);
		}
		loadSearchModule();
	}

	private void loadSearchModule() {
		Search search = new Search();
		
		addAllItems(search);
		
	}

	private void addAllItems(Search search) {
		int offset = 0;
		IndexWriter w = null;
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try {
			w = new IndexWriter(Search.index, config);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Item> list = Dao.getItemsList(offset);
		while (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				try {
					Search.addDocList(w,list.get(i).name,list.get(i).reference, String.valueOf(list.get(i).id));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			offset += 20;
			list = Dao.getItemsList(offset);
		}
		try {
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
