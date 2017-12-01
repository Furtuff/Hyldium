package com.tuff.hyldium.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;

import org.glassfish.jersey.server.mvc.Viewable;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.model.UserModel;
@Produces(MediaType.TEXT_HTML)
@Path("")
public class ManageApi extends Api {
	
	@GET
	@Path("/login/")
	public Response login(@javax.ws.rs.core.Context HttpServletRequest request) {
		HashMap<String,String> map = new HashMap<>();
		map.put("url","http://localhost:8080/Hyldium/hoho/login/");
		Viewable v = new  Viewable("/login.jsp",map);
		return  Response.ok(v).build();
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login/")
	public Response login(UserModel usermodel ,@Context UriInfo uriInfo) {
		if(usermodel.login != null && usermodel.password != null) {
		List<UserModel> loggedUser = Dao.login(usermodel);
		if(loggedUser != null) {
			if(loggedUser.get(0).role.contains((Dao.ADMIN))) {
			return Response.ok(new Viewable("/logged.jsp",loggedUser)).build();
			}
		}
		}
		Map<String, String> error = new HashMap<String, String>();
		error.put("message","Try Again");
		return Response.ok(new Viewable("/login.jsp",error)).build() ;
		
	}


	@POST
	@Path("/logged")
	public Response logged() {
		return Response.ok().entity(new Viewable("/logged.jsp")).build();
	}
}
