package com.tuff.hyldium.dao;

import com.tuff.hyldium.entity.*;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.*;
import com.tuff.hyldium.utils.StreamUtil;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Dao {
	public static final String ADMIN = "admin";
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("HyldiumPU");
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
		IndexWriter wr = null;
		IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
		try {
			wr = new IndexWriter(Search.index, config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println(item.id);

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
			try {
				Search.addDocList(wr, item.name, item.reference, String.valueOf(item.id));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static List<Item> getItemsList(int offset) {
		EntityManager em = getEntityManager();
		TypedQuery<Item> query = em.createQuery("SELECT d FROM Item d ORDER BY d.name", Item.class);
		query.setMaxResults(MAX_NUMBER);
		query.setFirstResult(offset);

		return query.getResultList();
	}

	public static Item getItem(long id) {
		EntityManager em = getEntityManager();
		TypedQuery<Item> query = em.createQuery("SELECT d FROM Item d WHERE d.id=:id", Item.class);
		query.setParameter("id", id);

		return query.getSingleResult();
	}

	public static long crudItem(ItemModel itemModel) {
		EntityManager em = getEntityManager();

		if (itemModel.id == 0) {
			Item item = new Item(itemModel);
			em.getTransaction().begin();
			em.persist(item);
			em.getTransaction().commit();
			Search.addDoc(item.name, item.reference, String.valueOf(item.id));
			return item.id;
		} else {
			Item existingItem = em.find(Item.class, itemModel.id);
			if (existingItem == null) {
				return -1;
			} else if (itemModel.name == null) {
				Search.removeDoc(String.valueOf(itemModel.id));
				em.getTransaction().begin();
				em.remove(existingItem);
				em.getTransaction().commit();

			} else {
				Search.removeDoc(String.valueOf(existingItem.id));
				em.getTransaction().begin();
				existingItem.copyFrom(itemModel);
				em.persist(existingItem);
				Search.addDoc(existingItem.name, existingItem.reference, String.valueOf(existingItem.id));
			}
			return existingItem.id;
		}

	}

	public static long addUser(UserModel userModel) {
		EntityManager em = getEntityManager();
		User user = new User(userModel.firstName, userModel.lastName, userModel.login, userModel.password,
				userModel.photo, userModel.role);
		TypedQuery<User> query = null;
		query = em.createQuery("SELECT u FROM User u WHERE u.login=:ulogin ORDER BY u.id", User.class);
		query.setParameter("ulogin", userModel.login);
		if (!query.getResultList().isEmpty()) {
			return -1;
		}
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		return user.id;
	}

	public static List<OrderModel> getOrders(int offset) {
		EntityManager em = getEntityManager();
		TypedQuery<Order> query = null;
		query = em.createQuery("SELECT o FROM Order o  ORDER BY o.date DESC", Order.class);
		query.setFirstResult(offset);
		query.setMaxResults(MAX_NUMBER);
		List<Order> orders = query.getResultList();
		List<OrderModel> orderModels = new ArrayList<>();
		for (Order o : orders) {
			orderModels.add(new OrderModel(o));
		}

		return orderModels;
	}

	public static List<UserModel> getUserList() {
		EntityManager em = getEntityManager();

		TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
		List<User> userList = query.getResultList();
		List<UserModel> list = new ArrayList<>();

		for (User u : userList) {
			list.add(new UserModel(u));
		}
		return list;
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
		delivery.date = Calendar.getInstance().getTimeInMillis();
		em.getTransaction().begin();
		em.persist(delivery);
		em.getTransaction().commit();
		return delivery.id;
	}

	public static List<DeliveryModel> getDeliveries(int offset) {
		EntityManager em = getEntityManager();
		TypedQuery<Delivery> query = null;
		query = em.createQuery("SELECT d FROM Delivery d ORDER BY d.date DESC", Delivery.class);
		query.setFirstResult(offset);

		List<Delivery> deliveries = query.getResultList();
		List<DeliveryModel> deliveriesModel = new ArrayList<>();
		for (Delivery d : deliveries) {
			deliveriesModel.add(new DeliveryModel(d));
		}
		return deliveriesModel;
	}

	public static boolean orderItem(UserItemOrderModel userItemOrderModel) {
		EntityManager em = getEntityManager();
		User user = em.getReference(User.class, userItemOrderModel.userId);
		Item item = em.getReference(Item.class, userItemOrderModel.itemId);
		Order order = em.getReference(Order.class, userItemOrderModel.orderId);
		UserItemOrderId userItemOrderId = new UserItemOrderId(order, user, item);
		UserItemOrder userItemOrder = null;
		try {
			userItemOrder = em.getReference(UserItemOrder.class, userItemOrderId);
		} catch (EntityNotFoundException e) {

		}
		if (!order.isValidated) {
			if (userItemOrderModel.bundlePart != 0.0) {
				if (userItemOrder == null) {
					userItemOrder = new UserItemOrder(user, order, item, userItemOrderModel);
					em.getTransaction().begin();
					em.persist(userItemOrder);
					em.getTransaction().commit();
				} else {
					em.getTransaction().begin();
					userItemOrder.bundlePart = userItemOrderModel.bundlePart;
					userItemOrder.toString();
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
		} else {
			// TODO exception for order closed
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
			query = em.createQuery("SELECT uio FROM UserItemOrder uio WHERE uio.order.id =:orderId ",
					UserItemOrder.class);
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
			order.toString();
			em.getTransaction().commit();

			return true;
		}
	}

	public static List<UserItemDeliveryModel> copyFromOrder(long deliveryId) {
		EntityManager em = getEntityManager();
		Delivery delivery = em.getReference(Delivery.class, deliveryId);
		TypedQuery<UserItemOrder> query = em
				.createQuery("SELECT uio FROM UserItemOrder uio WHERE uio.order.id=:orderId", UserItemOrder.class);
		query.setParameter("orderId", delivery.order.id);
		List<UserItemOrder> itemOrders = query.getResultList();
		em.getTransaction().begin();
		List<UserItemDeliveryModel> deliveries = new ArrayList<>();
		for (int i = 0; i < itemOrders.size(); i++) {
			UserItemDelivery userItemDelivery = new UserItemDelivery(delivery, itemOrders.get(i).user,
					itemOrders.get(i).item, itemOrders.get(i).bundlePart);
			em.persist(userItemDelivery);
			deliveries.add(new UserItemDeliveryModel(userItemDelivery));

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
			query = em.createQuery("SELECT uid FROM UserItemDelivery uid WHERE uid.delivery.id =:deliveryId ",
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
			delivery.toString();
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

	public static BetterList<UserItemOrderModel> getOrderItems(long orderId, int from) {
		EntityManager em = getEntityManager();
		TypedQuery<UserItemOrder> query = em.createQuery(
				"SELECT uio From UserItemOrder uio WHERE uio.order.id =:orderId ORDER BY uio.item.id",
				UserItemOrder.class);
		query.setParameter("orderId", orderId);
		query.setMaxResults(MAX_NUMBER * 3);
		query.setFirstResult(from);
		TypedQuery<UserItemOrder> queryCount = em.createQuery(
				"SELECT uio From UserItemOrder uio WHERE uio.order.id =:orderId ORDER BY uio.item.id",
				UserItemOrder.class);
		queryCount.setParameter("orderId", orderId);
		List<UserItemOrder> itemsOrder = query.getResultList();
		BetterList<UserItemOrderModel> itemOrdersModel = new BetterList<>();
		itemOrdersModel.elementList = new ArrayList<>();
		for (UserItemOrder uio : itemsOrder) {
			itemOrdersModel.elementList.add(new UserItemOrderModel(uio));
		}
		itemOrdersModel.elementCount = queryCount.getResultList().size();
		itemOrdersModel.maxRequestElement = MAX_NUMBER * 3;
		return itemOrdersModel;
	}

	public static boolean updateUser(UserModel userModel) {
		EntityManager em = getEntityManager();
		User user = em.getReference(User.class, userModel.id);
		if (user == null) {
			return false;
		} else if (userModel.firstName == null) {
			int count = 2;
			if (user.role.contains(ADMIN)) {
				count = 0;
				for (UserModel userLambda : getUserList()) {
					if (userLambda.role.contains(ADMIN)) {
						count++;
					}
				}
			}
			if (count > 1) {
				em.getTransaction().begin();
				em.remove(user);
				em.getTransaction().commit();
			}
			return false;
		} else {
			em.getTransaction().begin();
			user.firstName = userModel.firstName;
			user.lastName = userModel.lastName;
			user.login = userModel.login;
			if(userModel.password !=null) {
				user.setPassword(userModel.password);
			}
			em.getTransaction().commit();
			return true;
		}

	}

	public static BetterList<UserItemDeliveryModel> getDeliveryItems(long deliveryId, int offset) {
		EntityManager em = getEntityManager();
		TypedQuery<UserItemDelivery> query = em.createQuery(
				"SELECT uid FROM UserItemDelivery uid WHERE uid.delivery.id=:deliveryId ORDER BY uid.item.id",
				UserItemDelivery.class);
		query.setParameter("deliveryId", deliveryId);
		query.setMaxResults(MAX_NUMBER * 3);
		query.setFirstResult(offset);
		TypedQuery<UserItemDelivery> queryCount = em.createQuery(
				"SELECT uid FROM UserItemDelivery uid WHERE uid.delivery.id=:deliveryId ORDER BY uid.item.id",
				UserItemDelivery.class);
		queryCount.setParameter("deliveryId", deliveryId);
		List<UserItemDelivery> itemsDelivery = query.getResultList();
		BetterList<UserItemDeliveryModel> itemDeliveriesModel = new BetterList<>();
		itemDeliveriesModel.elementList = new ArrayList<>();
		for (UserItemDelivery uid : itemsDelivery) {
			itemDeliveriesModel.elementList.add(new UserItemDeliveryModel(uid));
		}
		itemDeliveriesModel.elementCount = queryCount.getResultList().size();
		itemDeliveriesModel.maxRequestElement = MAX_NUMBER * 3;
		return itemDeliveriesModel;
	}

	public static BetterList<UserItemOrderModel> getOrderItemsUser(long userId, long orderId, int from) {
		EntityManager em = getEntityManager();
		TypedQuery<UserItemOrder> query = em.createQuery(
				"SELECT uio From UserItemOrder uio WHERE uio.order.id = :orderId AND uio.user.id=:userId ORDER BY uio.item.id",
				UserItemOrder.class);
		query.setParameter("orderId", orderId);
		query.setParameter("userId", userId);
		query.setMaxResults(MAX_NUMBER * 3);
		query.setFirstResult(from);
		TypedQuery<UserItemOrder> queryCount = em.createQuery(
				"SELECT uio From UserItemOrder uio WHERE uio.order.id = :orderId AND uio.user.id=:userId ORDER BY uio.item.id",
				UserItemOrder.class);
		queryCount.setParameter("orderId", orderId);
		queryCount.setParameter("userId", userId);
		List<UserItemOrder> itemsOrder = query.getResultList();
		BetterList<UserItemOrderModel> itemOrdersModel = new BetterList<>();
		itemOrdersModel.elementList = new ArrayList<>();
		for (UserItemOrder uio : itemsOrder) {
			itemOrdersModel.elementList.add(new UserItemOrderModel(uio));
		}
		itemOrdersModel.elementCount = queryCount.getResultList().size();
		itemOrdersModel.maxRequestElement = MAX_NUMBER * 3;
		return itemOrdersModel;

	}

	public static Object getDeliveriesItemsUser(long userId, long deliveryId, int from) {
		EntityManager em = getEntityManager();
		TypedQuery<UserItemDelivery> query = em.createQuery(
				"SELECT uid FROM UserItemDelivery uid WHERE uid.delivery.id=:deliveryId AND uid.user.id=:userId ORDER BY uid.item.id",
				UserItemDelivery.class);
		query.setParameter("deliveryId", deliveryId);
		query.setParameter("userId", userId);
		query.setMaxResults(MAX_NUMBER * 3);
		query.setFirstResult(from);
		TypedQuery<UserItemDelivery> queryCount = em.createQuery(
				"SELECT uid FROM UserItemDelivery uid WHERE uid.delivery.id=:deliveryId AND uid.user.id=:userId ORDER BY uid.item.id",
				UserItemDelivery.class);
		queryCount.setParameter("deliveryId", deliveryId);
		queryCount.setParameter("userId", userId);
		List<UserItemDelivery> itemsDelivery = query.getResultList();
		BetterList<UserItemDeliveryModel> itemDeliveriesModel = new BetterList<>();
		itemDeliveriesModel.elementList = new ArrayList<>();
		for (UserItemDelivery uid : itemsDelivery) {
			itemDeliveriesModel.elementList.add(new UserItemDeliveryModel(uid));
		}
		itemDeliveriesModel.maxRequestElement = MAX_NUMBER * 3;
		itemDeliveriesModel.elementCount = queryCount.getResultList().size();
		return itemDeliveriesModel;
	}

	public static List<UserModel> login(UserModel logModel) {
		EntityManager em = getEntityManager();

		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.login=:ulogin ORDER BY u.id", User.class);
		query.setParameter("ulogin", logModel.login);
		List<User> loggedUser = query.getResultList();
		if (loggedUser == null || loggedUser.size() == 0) {
			return null;
		} else if (Arrays.equals(loggedUser.get(0).secret, logModel.password)) {
			if (loggedUser.get(0).role.contains(ADMIN)) {
				return getUserList();
			} else {
				List<UserModel> list = new ArrayList<>();
				list.add(new UserModel(loggedUser.get(0)));
				return list;
			}
		}
		return null;
	}

}
