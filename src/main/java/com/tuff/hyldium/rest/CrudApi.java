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
import com.tuff.hyldium.entity.Item;
import com.tuff.hyldium.entity.User;

@Path("/crud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CrudApi{
	private static final long REQUEST_ERROR = -1;
	
	
	@POST
	@Path("/product/update")
	public Response addItems(Item item) {
		if (item == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.addItem(item)).header("X-HM-RC", "OK").build();
	}
	@POST
	@Path("/user/add")
	public Response addUser(User user) {
		if (user == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.addUser(user)).header("X-HM-RC", "OK").build();
	}
	@POST
	@Path("/copy")
	public Response copyItemsFromFile() {
		
		return Response.ok(Dao.copyItems()).header("X-HM-RC", "OK").build();
	}
	@GET
	@Path("/orders/")
	public Response getOrder() {
		
		return Response.ok(Dao.getOrder()).build();
	}
	@GET
	@Path("/delivery/")
	public Response getDeliveries() {
		
		return Response.ok(Dao.getOrder()).build();
	}
	@GET
	@Path("/users/")
	public Response getUserList(User user) {
		//if(user == superuser)
		return Response.ok(Dao.getUserList()).build();
	}
	@GET
	@Path("/products/{itemid}")
	public Response getItemsList(@PathParam("itemid") long itemId) {
		
		return Response.ok(Dao.getItemsList(itemId)).build();
	}
	
}
