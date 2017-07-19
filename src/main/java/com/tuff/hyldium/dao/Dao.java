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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.tuff.hyldium.entity.Delivery;
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.Order;
import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.entity.UserItemDelivery;
import com.tuff.hyldium.entity.UserItemOrder;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.BetterList;
import com.tuff.hyldium.model.DeliveryModel;
import com.tuff.hyldium.model.ItemModel;
import com.tuff.hyldium.model.OrderModel;
import com.tuff.hyldium.model.UserItemDeliveryId;
import com.tuff.hyldium.model.UserItemDeliveryModel;
import com.tuff.hyldium.model.UserItemOrderId;
import com.tuff.hyldium.model.UserItemOrderModel;
import com.tuff.hyldium.model.UserModel;
import com.tuff.hyldium.utils.StreamUtil;

public class Dao {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("HyldiumPU");
	private final static int MAX_NUMBER = 20;

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
			Search.addDoc(item.name, String.valueOf(item.id));
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

	public static List<Item> getItemsList(int offset) {
		EntityManager em = getEntityManager();
		TypedQuery<Item> query = em.createQuery("SELECT d FROM Item d ORDER BY d.name",
				Item.class);
		query.setMaxResults(MAX_NUMBER);
		query.setFirstResult(offset);
		
		return query.getResultList();
	}

	public static long crudItem(ItemModel itemModel) {
		EntityManager em = getEntityManager();

		if (itemModel.id == 0) {
			Item item = new Item(itemModel);
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			Search.addDoc(item.name, String.valueOf(item.id));
			return item.id;
		} else {
			Item existingItem = em.find(Item.class, itemModel.id);
			if (existingItem == null) {
				return -1;
			} else if(itemModel.name == null) {
				Search.removeDoc(String.valueOf(itemModel.id));
				em.getTransaction().begin();
				em.remove(existingItem);
				em.getTransaction().commit();
				
				
			}else {
				Search.removeDoc(String.valueOf(existingItem.id));
				em.getTransaction().begin();
				existingItem.copyFrom(itemModel);
				em.persist(existingItem);
				Search.addDoc(existingItem.name, String.valueOf(existingItem.id));
			}
			return existingItem.id;
		}

	}

	public static long addUser(UserModel userModel) {
		EntityManager em = getEntityManager();
		User user = new User(userModel.name, userModel.password, userModel.photo);
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
			query = em.createQuery("SELECT o FROM Order WHERE o.id =:oderId ORDER BY o.date", Order.class);
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
		if(!order.isValidated) {
		if (userItemOrderModel.bundlePart != 0.0) {
			if (userItemOrder == null) {
				userItemOrder = new UserItemOrder(user, order, item, userItemOrderModel);
				em.getTransaction().begin();
				em.persist(userItemOrder);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				userItemOrder.bundlePart = userItemOrderModel.bundlePart;
				em.getTransaction().commit();
			}

			return true;

		} else {
			if (userItemOrder != null) {
				em.getTransaction().begin();
				em.remove(userItemOrder);
				em.getTransaction().commit();
			}
			return false;
		}
		}else {
			//TODO exception for order closed
			return false;
		}
	}

	public static boolean updateOrder(long orderId, OrderModel orderModel) {
		EntityManager em = getEntityManager();
		Order order;
		order = em.getReference(Order.class, orderId);

		if (order == null) {
			return false;
		} else if (orderModel.name == null) {
			TypedQuery<UserItemOrder> query = null;
			query = em.createQuery("SELECT uio FROM UserItemOrder WHERE uio.order.id =:orderId ", UserItemOrder.class);
			query.setParameter("orderId", orderId);
			List<UserItemOrder> itemOrders = query.getResultList();
			em.getTransaction().begin();
			for (int i = 0; i < itemOrders.size(); i++) {
				em.remove(itemOrders.get(i));
			}

			em.remove(order);
			em.getTransaction().commit();
			return false;
		} else {
			em.getTransaction().begin();
			order.name = orderModel.name;
			order.dateName = orderModel.dateName;
			order.isValidated = orderModel.isValidated;
			em.getTransaction().commit();

			return true;
		}
	}

	public static List<UserItemDelivery> copyFromOrder(long deliveryId) {
		EntityManager em = getEntityManager();
		Delivery delivery = em.getReference(Delivery.class, deliveryId);
		TypedQuery<UserItemOrder> query = em.createQuery("SELECT uio FROM UserItemOrder WHERE uio.order.id=:orderId",
				UserItemOrder.class);
		query.setParameter("orderId", delivery.order.id);
		List<UserItemOrder> itemOrders = query.getResultList();
		em.getTransaction().begin();
		List<UserItemDelivery> deliveries = new ArrayList<>();
		for (int i = 0; i < itemOrders.size(); i++) {
			UserItemDelivery userItemDelivery = new UserItemDelivery(delivery, itemOrders.get(i).user,
					itemOrders.get(i).item, itemOrders.get(i).bundlePart);
			deliveries.add(userItemDelivery);
			em.persist(userItemDelivery);
		}
		em.getTransaction().commit();
		return deliveries;
	}

	public static boolean updateDelivery(long deliveryId, DeliveryModel deliveryModel) {
		EntityManager em = getEntityManager();
		Delivery delivery = em.getReference(Delivery.class, deliveryId);

		if (delivery == null) {
			return false;
		} else if (deliveryModel.name == null) {
			TypedQuery<UserItemDelivery> query = null;
			query = em.createQuery("SELECT uid FROM UserItemDelivery WHERE uid.delivery.id =:deliveryId ",
					UserItemDelivery.class);
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

			Order order = em.getReference(Order.class, deliveryModel.orderId);

			em.getTransaction().begin();
			delivery.name = deliveryModel.name;
			delivery.order = order;
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
				userItemDelivery = new UserItemDelivery(delivery, user, item, userItemDeliveryModel.bundlePart);
				em.getTransaction().begin();
				em.persist(userItemDelivery);
				em.getTransaction().commit();
			} else {
				em.getTransaction().begin();
				userItemDelivery.bundlePart = userItemDeliveryModel.bundlePart;
				em.getTransaction().commit();
			}

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

	public static BetterList<UserItemOrder> getOrderItems(long orderId, int from) {
		EntityManager em = getEntityManager();
		TypedQuery<UserItemOrder> query = em.createQuery("SELECT uio From UserItemOrder WHERE uio.order.id = :orderID ORDER BY uio.item.id", UserItemOrder.class)
		query.setParameter("orderId", orderId);
		query.setMaxResults(MAX_NUMBER);
		query.setFirstResult(from);
		TypedQuery<UserItemOrder> queryCount = em.createQuery("SELECT uio From UserItemOrder WHERE uio.order.id = :orderID ORDER BY uio.item.id", UserItemOrder.class);
		BetterList<UserItemOrder> itemOrders = new BetterList<>();
		itemOrders.elementList = query.getResultList();
		itemOrders.elementCount = queryCount.getResultList().size();
		return itemOrders;
	}

	public static boolean updateUser(UserModel userModel) {
		EntityManager em = getEntityManager();
		User user = em.getReference(User.class, userModel.id);
		if (user == null) {
			return false;
		} else if (userModel.name == null) {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			return false;
		} else {
			em.getTransaction().begin();
			user.name = userModel.name;
			user.secret = userModel.password;
			em.getTransaction().commit();
			return true;
		}

	}

}
