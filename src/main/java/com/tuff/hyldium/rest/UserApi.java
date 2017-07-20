package com.tuff.hyldium.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.model.UserModel;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserApi extends Api{
	
	@POST
	@Path("/add")
	public Response addUser(UserModel userModel) {
		if (userModel == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}else if(userModel.password == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}else {
			
			return Response.ok(Dao.addUser(userModel)).header("X-HM-RC", "OK").build();
		}
	}
	
	@POST
	@Path("/update")
	public Response updateUser(UserModel userModel) {
		if (userModel == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}else if(userModel.id == 0) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}else {
			return Response.ok(Dao.updateUser(userModel)).header("X-HM-RC", "OK").build();

		}
	}
			
	@GET
	@Path("/list")
	public Response getUserList() {
		return Response.ok(Dao.getUserList()).build();
	}
	
	
}
