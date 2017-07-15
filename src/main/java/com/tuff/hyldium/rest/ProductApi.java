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

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductApi extends Api {
	@POST
	@Path("/products/update")
	public Response addItems(Item item) {
		if (item == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.addItem(item)).header("X-HM-RC", "OK").build();
	}

	@GET
	@Path("/products/{itemid}")
	public Response getItemsList(@PathParam("itemid") long itemId) {
		
		return Response.ok(Dao.getItemsList(itemId)).build();
	}
}