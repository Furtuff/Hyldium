package com.tuff.hyldium.dao;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.tuff.hyldium.entity.Delivery;
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.Order;
import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.entity.UserItemDelivery;
import com.tuff.hyldium.entity.UserItemOrder;
import com.tuff.hyldium.model.DeliveryModel;
import com.tuff.hyldium.model.OrderModel;
import com.tuff.hyldium.model.UserItemDeliveryId;
import com.tuff.hyldium.model.UserItemDeliveryModel;
import com.tuff.hyldium.model.UserItemOrderId;
import com.tuff.hyldium.model.UserItemOrderModel;
import com.tuff.hyldium.utils.StreamUtil;

public class Dao {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("HyldiumPU");
	private final static long MAX_NUMBER = 20;

	private static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public static List<Item> copyItems() {

		File file = null;
		try {
			file = StreamUtil.stream2file(Dao.class.getResourceAsStream("firstdb.ods"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Sheet sheet = null;
		try {
			sheet = SpreadSheet.createFromFile(file).getSheet(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Item> prout = saveItemListFromSheet(sheet);
		if (prout == null) {
			return null;

		} else {
			return prout;
		}

	}

	private static List<Item> saveItemListFromSheet(Sheet sheet) {

		EntityManager em = getEntityManager();

		boolean hasNext = true;
		List<Item> list = new ArrayList<Item>();
		int line = 2;

		while (hasNext) {
			Item item = new Item();
			String lineString = String.valueOf(line);
			if (line < 10) {
				lineString = "0" + lineString;
			}
			System.out.println(line);
			item.reference = (String) sheet.getCellAt("A" + lineString).getTextValue();
			item.name = (String) sheet.getCellAt("B" + lineString).getTextValue();
			try {
				item.price = Double.parseDouble(sheet.getCellAt("J" + lineString).getTextValue().replace(",", "."));
				item.priceHT = Double.parseDouble(sheet.getCellAt("G" + lineString).getTextValue().replace(",", "."));
				item.byBundle = ((BigDecimal) sheet.getCellAt("E" + lineString).getValue()).floatValue();
				item.TVA = ((BigDecimal) sheet.getCellAt("F" + lineString).getValue()).floatValue();
				item.date = Calendar.getInstance().getTimeInMillis();
			} catch (NumberFormatException e) {
			}

			item.label = (String) sheet.getCellAt("H" + lineString).getValue();
			list.add(item);
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			String nextString = String.valueOf(line + 1);
			if (line + 1 < 10) {
				nextString = "0" + nextString;
			}
			String next = (String) sheet.getCellAt("A" + nextString).getValue();
			if (next == null) {
				hasNext = false;
			} else {
				if (next.isEmpty()) {
					hasNext = false;
				}
			}
			line++;

		}

		return list;
	}

	public static List<Item> getItemsList(long id) {
		EntityManager em = getEntityManager();

		TypedQuery<Item> query = em.createQuery("SELECT d FROM Item d WHERE d.id >= :idmin AND d.id < :idmax",
				Item.class);
		query.setParameter("idmin", id);
		query.setParameter("idmax", id + MAX_NUMBER);
		return query.getResultList();

	}

	public static Item addItem(Item item) {
		EntityManager em = getEntityManager();

		if (item != null) {
			Item existingItem = em.find(Item.class, item.id);
			em.getTransaction().begin();
			if (existingItem == null) {
				em.persist(item);
			} else {
				existingItem.copyFrom(item);
				em.persist(existingItem);
			}
			em.getTransaction().commit();
		}

		return item;
	}

	public static long addUser(User user) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user.id;
	}

	public static List<Order> getOrder(long orderId) {
		EntityManager em = getEntityManager();
		TypedQuery<Order> query = null;
		if (orderId == 0) {
			query = em.createQuery("SELECT o FROM Order", Order.class);
		} else {
			query = em.createQuery("SELECT o FROM Order WHERE o.id =:oderId", Order.class);
			query.setParameter("orderId", orderId);
		}

		return query.getResultList();
	}

	public static List<User> getUserList() {
		EntityManager em = getEntityManager();

		TypedQuery<User> query = em.createQuery("SELECT u FROM User d", User.class);

		return query.getResultList();
	}

	public static long createOrder(OrderModel orderModel) {
		EntityManager em = getEntityManager();
		Order order = new Order();
		order.copyFromOrderModel(orderModel);
		em.getTransaction().begin();
		em.persist(order);
		em.getTransaction().commit();
		return order.id;
	}

	public static long createDelivery(DeliveryModel deliveryModel) {
		EntityManager em = getEntityManager();
		Delivery delivery = new Delivery();
		Order order = em.getReference(Order.class, deliveryModel.orderId);
		delivery.order = order;
		delivery.name = deliveryModel.name;
		em.getTransaction().begin();
		em.persist(delivery);
		em.getTransaction().commit();
		return delivery.id;
	}

	public static List<Delivery> getDeliveries(long deliveryId) {
		EntityManager em = getEntityManager();
		TypedQuery<Delivery> query = null;
		if (deliveryId == 0) {
			query = em.createQuery("SELECT o FROM Delivery", Delivery.class);
		} else {
			query = em.createQuery("SELECT o FROM Delivery WHERE o.id =:deliveryId", Delivery.class);
			query.setParameter("deliveryId", deliveryId);
		}

		return query.getResultList();
	}

	public static boolean orderItem(UserItemOrderModel userItemOrderModel) {
		EntityManager em = getEntityManager();
		User user = em.getReference(User.class, userItemOrderModel.userId);
		Item item = em.getReference(Item.class, userItemOrderModel.itemId);
		Order order = em.getReference(Order.class, userItemOrderModel.orderId);
		UserItemOrderId userItemOrderId = new UserItemOrderId(order, user, item);
		UserItemOrder userItemOrder = em.getReference(UserItemOrder.class, userItemOrderId);
		if (userItemOrderModel.bundlePart != 0.0) {
			if (userItemOrder == null) {
				userItemOrder = new UserItemOrder(user, order, item, userItemOrderModel);
			}

			em.getTransaction().begin();
			em.persist(userItemOrder);
			em.getTransaction().commit();
			return true;

		} else {
			if (userItemOrder != null) {
				em.getTransaction().begin();
				;
				em.remove(userItemOrder);
				em.getTransaction().commit();
			}
			return false;
		}
	}

	public static Object copyFromOrder(Order order) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object updateOrder(long orderId, OrderModel orderModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean copyFromOrder(long deliveryId) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean updateDelivery(long deliveryId, DeliveryModel deliveryModel) {
		EntityManager em = getEntityManager();
		Delivery delivery = em.getReference(Delivery.class, deliveryId);

		if (delivery == null) {
			return false;
		} else if (deliveryModel.name == null) {
			TypedQuery<UserItemDelivery> query = null;
			query = em.createQuery("SELECT uid FROM UserItemDelivery WHERE uid.delivery.id =:deliveryId ", UserItemDelivery.class);
			query.setParameter("deliveryId", deliveryId);
			List<UserItemDelivery> userItemDeliveries = query.getResultList();
			em.getTransaction().begin();
			for (int i = 0; i < userItemDeliveries.size(); i++) {
				em.remove(userItemDeliveries.get(i));
			}
			
			em.remove(delivery);
			em.getTransaction().commit();
			return false;
		} else {
			delivery.name = deliveryModel.name;
			Order order = em.getReference(Order.class, deliveryModel.orderId);
			delivery.order = order;

			em.getTransaction().begin();
			em.persist(delivery);
			em.getTransaction().commit();

			return true;
		}
	}

	public static boolean deliveryItem(UserItemDeliveryModel userItemDeliveryModel) {
		EntityManager em = getEntityManager();
		User user = em.getReference(User.class, userItemDeliveryModel.userId);
		Item item = em.getReference(Item.class, userItemDeliveryModel.itemId);
		Delivery delivery = em.getReference(Delivery.class, userItemDeliveryModel.deliveryId);
		UserItemDeliveryId userItemDeliveryId = new UserItemDeliveryId(user, delivery, item);
		UserItemDelivery userItemDelivery = em.getReference(UserItemDelivery.class, userItemDeliveryId);
		if (userItemDeliveryModel.bundlePart != 0.0) {
			if (userItemDelivery == null) {
				userItemDelivery = new UserItemDelivery( delivery,user, item, userItemDeliveryModel.bundlePart);
			}

			em.getTransaction().begin();
			em.persist(userItemDelivery);
			em.getTransaction().commit();
			return true;

		} else {
			if (userItemDelivery != null) {
				em.getTransaction().begin();
				em.remove(userItemDelivery);
				em.getTransaction().commit();
			}
			return false;
		}		
	}

}
