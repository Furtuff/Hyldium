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
import com.tuff.hyldium.entity.UserItemOrder;
import com.tuff.hyldium.model.DeliveryModel;
import com.tuff.hyldium.model.OrderModel;
import com.tuff.hyldium.model.UserItemOrderModel;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderApi extends Api{
	
	@POST
	@Path("/add")
	public Response createOrder(OrderModel orderModel) {
		return Response.ok(Dao.createOrder(orderModel)).build();
	}
	@POST
	@Path("/update/{orderId}")
	public Response updateOrder(OrderModel orderModel,@PathParam("orderId") long orderId) {
		
		return Response.ok(Dao.updateOrder(orderId,orderModel)).build();
	}
	
	@POST
	@Path("/item/update")
	public Response orderItem(UserItemOrderModel userItemOrderModel) {
		
		return Response.ok(Dao.orderItem(userItemOrderModel)).build();
	}
	
	@POST
	@Path("/delivery/add")
	public Response createDelivery(DeliveryModel deliveryModel) {

		return Response.ok(Dao.createDelivery(deliveryModel)).build();
	}
	@POST
	@Path("/delivery/update/{deliveryId")
	public Response updateDelivery(DeliveryModel deliveryModel,@PathParam("deliveryId")long deliveryId) {

		return Response.ok(Dao.updateDelivery(deliveryId,deliveryModel)).build();
	}
	@POST
	@Path("/delivery/copy/{orderId}")
	public Response copyDeliveryOrder(@PathParam("orderId")long orderId) {
		
		return Response.ok(Dao.copyFromOrder(orderId)).build();
	}
	
	
	@GET
	@Path("{orderId}")
	public Response getOrder(@PathParam("orderId")long orderId) {
		
		return Response.ok(Dao.getOrder(orderId)).build();
	}
	@GET
	@Path("/delivery/{deliveryId}")
	public Response getDeliveries(@PathParam("deliveryId")long deliveryId) {
		
		return Response.ok(Dao.getDeliveries(deliveryId)).build();
	}
}
