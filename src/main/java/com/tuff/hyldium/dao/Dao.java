package com.tuff.hyldium.dao;

import static com.tuff.hyldium.dao.DaoErrors.USER_UNKNOWN;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.QuizItem;
import com.tuff.hyldium.entity.QuizItemData;
import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.model.Emf;

public class Dao {
	
	public static boolean copyItems() {
		EntityManager em = Emf.instance.getEntityManager();
		
		File file = new File("/firstdb.ods");
		Sheet sheet = null;
		try {
			sheet = SpreadSheet.createFromFile(file).getSheet("ARTICLE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int columnNumber,rowNumber;
		 columnNumber = sheet.getSpreadSheet().getSheet("ARTICLE").getColumnCount();
		 rowNumber = sheet.getSpreadSheet().getSheet("ARTICLE").getRowCount();
		 
		 return false;
		
	}
	
	public static List<Item> addItems(List<Item> items){
		EntityManager em = Emf.instance.getEntityManager();

		if(items != null) {
			em.getTransaction().begin();
			for(Item i : items) {
				em.persist(i);
			}
			em.getTransaction().commit();
		}
		TypedQuery<Item> query = em.createQuery("SELECT i From Item i",Item.class);
		
		
		return query.getResultList();
	}
	
	
	
}
