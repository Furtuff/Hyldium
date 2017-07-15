package com.tuff.hyldium.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tuff.hyldium.dao.Dao;

@Path("/util")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DataUtilsApi extends Api{
	
	@POST
	@Path("/copy")
	public Response copyItemsFromFile() {
		
		return Response.ok(Dao.copyItems()).header("X-HM-RC", "OK").build();
	}

}
