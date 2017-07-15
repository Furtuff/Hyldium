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

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserApi extends Api{
	
	@POST
	@Path("/add")
	public Response addUser(User user) {
		if (user == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.addUser(user)).header("X-HM-RC", "OK").build();
	}
			
	@GET
	@Path("/list/")
	public Response getUserList(User user) {
		return Response.ok(Dao.getUserList()).build();
	}
	
	
}
