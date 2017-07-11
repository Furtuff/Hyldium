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

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderApi extends Api{
	
	@POST
	@Path("/add")
	public Response createOrder(Order order) {
		
		return Response.ok(Dao.createOrder(order)).build();
	}
	
	@POST
	@Path("/delivery/add")
	public Response createDelivery(Delivery delivery) {
		
		return Response.ok(Dao.createDelivery(delivery)).build();
	}
	
	
	@GET
	@Path("")
	public Response getOrder() {
		
		return Response.ok(Dao.getOrder()).build();
	}
	@GET
	@Path("/delivery/")
	public Response getDeliveries() {
		
		return Response.ok(Dao.getDeliveries()).build();
	}
}
