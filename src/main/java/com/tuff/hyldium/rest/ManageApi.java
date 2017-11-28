package com.tuff.hyldium.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.model.UserModel;
@Produces(MediaType.TEXT_HTML)
@Path("")
public class ManageApi extends Api {
	
	@GET
	@Path("/login/")
	public Response login(@javax.ws.rs.core.Context HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/css/" + "login" + ".css");
		HashMap<String,String> map = new HashMap<>();
		map.put("path",path);
		Viewable v = new  Viewable("/login.jsp",map);
		return  Response.ok(v).build();
	}
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login/")
	public Response login(@FormParam("uname")String login,@FormParam("password")String pwd) {
		if(login != null && pwd != null) {
		UserModel loginUser = new UserModel();
		loginUser.login = login;
		loginUser.password = pwd.getBytes();// DatatypeConverter.parseBase64Binary(pwd);
		List<UserModel> loggedUser = Dao.login(loginUser);
		if(loggedUser != null) {
			
			return Response.ok(new Viewable("/logged.jsp")).build();
		}
		}
		Map<String, String> error = new HashMap<String, String>();
		error.put("message","Try Again");
		return Response.ok(new Viewable("/Login.jsp",error)).build() ;
		
	}

	@PermitAll
	@POST
	@Path("/logged")
	public Response logged() {
		return Response.ok().entity(new Viewable("/logged.jsp")).build();
	}
}
