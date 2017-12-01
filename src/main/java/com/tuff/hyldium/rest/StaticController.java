package com.tuff.hyldium.rest;

import java.io.File;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Produces(MediaType.TEXT_HTML)
@Path("")
public class StaticController {

	@GET
	@Path("{param1 : ^(?![\\s\\S])|(\\w+/)*?}css/{id}")
	public Response getCss(@Context HttpServletRequest request, @PathParam("id") String id) {
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/css/" + id + ".css");

		File f = new File(path);
		if (f.exists()) {
			return Response.ok(f).build();
		}
		return null;
	}
	@GET
	@Path("{param1 : ^(?![\\s\\S])|(\\w+/)*?}js/{id}")
	public Response getJs(@Context HttpServletRequest request, @PathParam("id") String id) {
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/js/" + id + ".js");

		File f = new File(path);
		if (f.exists()) {
			return Response.ok(f).build();
		}
		return null;
	}
	@GET
	@Path("{param1 : ^(?![\\s\\S])|(\\w+/)*?}img/{id}")
	public Response getImg(@Context HttpServletRequest request, @PathParam("id") String id) {
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/img/" + id);

		File f = new File(path);
		if (f.exists()) {
			return Response.ok(f).build();
		}
		return null;
	}
}