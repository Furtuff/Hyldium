package com.tuff.hyldium.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.ApplicationPath;
import javax.xml.bind.DatatypeConverter;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.objectdb.Enhancer;
import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.ItemModel;
import com.tuff.hyldium.model.UserModel;
import com.tuff.hyldium.security.AuthFilter;

@ApplicationPath("hoho")
public class HyldAppInit extends ResourceConfig implements PreDestroy {
	private final static String ADMIN = "admin";

	public HyldAppInit() {
		packages("com.tuff.hyldium.rest");
		register(AuthFilter.class);
		register(LoggingFeature.class);
		register(JacksonFeature.class);
		property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");
		register(JspMvcFeature.class);
		Enhancer.enhance("com.tuff.hyldium.entity.*");
		if(Dao.getItemsList(0).isEmpty()) {
			new Search();
			Dao.copyItems();
		}
		if(Dao.getUserList().isEmpty()) {
			UserModel baseSu = new UserModel();
			baseSu.firstName ="jean";
			baseSu.lastName ="valjean";
			baseSu.login= "SUperman";
			List<String> role = new ArrayList<>();
			role.add(ADMIN);
			baseSu.role = role;
			baseSu.password = Base64.getEncoder().encode("password".getBytes());
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
		List<ItemModel> list = Dao.getItemsList(offset);
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
			
			e.printStackTrace();
		}
	}

	@Override
	public void preDestroy() {
		if(Dao.emf.isOpen()) {
		Dao.emf.close();
		}
	}
}
