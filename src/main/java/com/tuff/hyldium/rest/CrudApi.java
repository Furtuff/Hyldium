package com.tuff.hyldium.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.Item;

@Path("/crud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CrudApi{
	
	
	@POST
	@Path("/add")
	public Response addItems(List<Item> items) {
		
		
		return Response.ok(Dao.addItems(items)).build();
	}
	@POST
	@Path("/copy")
	public Response copyItemsFromFile() {
		
		return Response.ok(Dao.copyItems()).header("X-FM-RC", "0").build();
	}
}
