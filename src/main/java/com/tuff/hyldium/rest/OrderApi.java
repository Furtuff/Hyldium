package com.tuff.hyldium.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.Delivery;
import com.tuff.hyldium.entity.Order;
import com.tuff.hyldium.entity.UserItemDelivery;
import com.tuff.hyldium.entity.UserItemOrder;
import com.tuff.hyldium.model.DeliveryModel;
import com.tuff.hyldium.model.OrderModel;
import com.tuff.hyldium.model.UserItemDeliveryModel;
import com.tuff.hyldium.model.UserItemOrderModel;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderApi extends Api {

	@POST
	@Path("/add")
	public Response createOrder(OrderModel orderModel) {
		return Response.ok(Dao.createOrder(orderModel)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}

	@POST
	@Path("/update/{orderId}")
	public Response updateOrder(OrderModel orderModel, @PathParam("orderId") long orderId) {
		if (orderId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();

		}else  {
			return Response.ok(Dao.updateOrder(orderId, orderModel)).header("X-HM-RC", REQUEST_SUCCESS).build();
		}
	}

	@POST
	@Path("/item/update")
	public Response orderItem(UserItemOrderModel userItemOrderModel) {

		if (userItemOrderModel.itemId == 0 || userItemOrderModel.orderId == 0 || userItemOrderModel.userId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}

		return Response.ok(Dao.orderItem(userItemOrderModel)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}

	@POST
	@Path("/delivery/add")
	public Response createDelivery(DeliveryModel deliveryModel) {

		return Response.ok(Dao.createDelivery(deliveryModel)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}

	@POST
	@Path("/delivery/update/{deliveryId}")
	public Response updateDelivery(DeliveryModel deliveryModel, @PathParam("deliveryId") long deliveryId) {
		if (deliveryId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();

		} else {
			return Response.ok(Dao.updateDelivery(deliveryId, deliveryModel)).header("X-HM-RC", REQUEST_SUCCESS)
					.build();

		}
	}

	@POST
	@Path("/delivery/copy/{deliveryId}")
	public Response copyDeliveryOrder(@PathParam("deliveryId") long deliveryId) {
		if (deliveryId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		} else {
			return Response.ok(Dao.copyFromOrder(deliveryId)).header("X-HM-RC", REQUEST_SUCCESS).build();

		}
	}

	@POST
	@Path("/delivery/item/update")
	public Response deliveryItem(UserItemDeliveryModel userItemDeliveryModel) {

		return Response.ok(Dao.deliveryItem(userItemDeliveryModel)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}

	@GET
	@Path("/{offset}")
	public Response getOrders(@PathParam("offset") int offset) {

		return Response.ok(Dao.getOrders(offset)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}

	@GET
	@Path("/delivery/{offset}")
	public Response getDeliveries(@PathParam("offset") int offset) {

		return Response.ok(Dao.getDeliveries(offset)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}
	@GET
	@Path("/delivery/details/{deliveryId}/{from}")
	public Response getDeliveryItems(@PathParam("deliveryId") long deliveryId,@PathParam("from") int from) {
		if(deliveryId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}

		return Response.ok(Dao.getDeliveryItems(deliveryId,from)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}
	@GET
	@Path("/{orderId}/{from}")
	public Response getOrderItems(@PathParam("orderId") long orderId,@PathParam("from") int from) {
		if(orderId == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.getOrderItems(orderId,from)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}
	@GET
	@Path("/{userId}/{orderId}/{from}")
	public Response getOrderItemsUser(@PathParam("userId") long userId,@PathParam("orderId") long orderId,@PathParam("from") int from) {
		if(orderId == 0 || userId ==0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.getOrderItemsUser(userId,orderId,from)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}
	@GET
	@Path("/delivery/{userId}/{deliveryId}/{from}")
	public Response getDeliveriesItemsUser(@PathParam("userId") long userId ,@PathParam("deliveryId") long deliveryId ,@PathParam("from") int from) {
		if(deliveryId == 0 || userId ==0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.getDeliveriesItemsUser(userId,deliveryId,from)).header("X-HM-RC", REQUEST_SUCCESS).build();
	}
}
