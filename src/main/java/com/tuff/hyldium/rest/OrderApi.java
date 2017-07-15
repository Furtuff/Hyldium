package com.tuff.hyldium.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.Delivery;
import com.tuff.hyldium.entity.Order;
import com.tuff.hyldium.entity.UserItemOrder;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderApi extends Api{
	
	@POST
	@Path("/add")
	public Response createOrder(Order order) {
		if(order.id !=0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.createOrder(order)).build();
	}
	
	@POST
	@Path("/item/update")
	public Response orderItem(UserItemOrder userItemOrder) {
		
		return Response.ok(Dao.orderItem(userItemOrder)).build();
	}
	
	@POST
	@Path("/delivery/add")
	public Response createDelivery(Delivery delivery) {
		if(delivery.id !=0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.createDelivery(delivery)).build();
	}
	@POST
	@Path("/delivery/copy")
	public Response copyDeliveryOrder(Order order) {
		
		return Response.ok(Dao.copyFromOrder(order)).build();
	}
	
	
	@GET
	@Path("")
	public Response getOrder(long orderId) {
		
		return Response.ok(Dao.getOrder(orderId)).build();
	}
	@GET
	@Path("/delivery/")
	public Response getDeliveries() {
		
		return Response.ok(Dao.getDeliveries()).build();
	}
}
