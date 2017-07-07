package com.tuff.hyldium.dao;

import static com.tuff.hyldium.dao.DaoErrors.USER_UNKNOWN;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.dom.spreadsheet.Table;

import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.QuizItem;
import com.tuff.hyldium.entity.QuizItemData;
import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.model.Emf;

public class Dao {
	
	public static List<Item> copyItems() {
		
		File file = new File("/home/tuffery/firstdb.ods");
		Sheet sheet = null;
		try {
			sheet = SpreadSheet.createFromFile(file).getSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Item> prout = saveItemListFromSheet(sheet);
		if(prout==null) {
			 return null;

		}else {
			return prout;
		}

		
	}
	private static List<Item> saveItemListFromSheet(Sheet sheet){
		
		EntityManager em = Emf.instance.getEntityManager();

		boolean hasNext = true;
		List<Item> list = new ArrayList<Item>();
		int line = 2;
		
		while(hasNext) {
			Item item = new Item();
			String lineString = String.valueOf(line);
			if(line < 10) {
				lineString = "0" + lineString;
			}
			System.out.println(line);
			item.reference = (String) sheet.getCellAt("A"+lineString).getTextValue();
			item.name = (String)sheet.getCellAt("B"+lineString).getTextValue();
			try {
				item.price = Double.parseDouble(sheet.getCellAt("J"+lineString).getTextValue().replace(",","."));
				item.priceHT = Double.parseDouble(sheet.getCellAt("G"+lineString).getTextValue().replace(",", "."));
				item.byBundle = ((BigDecimal) sheet.getCellAt("E"+lineString).getValue()).floatValue();
				item.TVA = ((BigDecimal) sheet.getCellAt("F"+lineString).getValue()).floatValue();
			}catch (NumberFormatException e) {
				}
			
			
			item.label = (String) sheet.getCellAt("H"+lineString).getValue();
			list.add(item);
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			String nextString = String.valueOf(line+1);
			if (line+1 <10) {
				nextString = "0"+nextString;
			}
			String next =(String)sheet.getCellAt("A"+nextString).getValue();
			if(next == null) {
				hasNext = false;
			}else {
				if(next.isEmpty()) {
					hasNext = false;
				}
			}
			line ++;
			
		}
		
		return list;
	}
	
	public static List<Item> getItemsList(){
		EntityManager em = Emf.instance.getEntityManager();
		
		TypedQuery<Item> query = em.createQuery("SELECT d FROM Item d WHERE d.id < 10", Item.class);
		return query.getResultList();

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
