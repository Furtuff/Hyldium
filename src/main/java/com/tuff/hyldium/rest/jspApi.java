package com.tuff.hyldium.rest;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/")
public class jspApi extends Api {
	@GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() {
        return new Viewable("/WEB-INF/index.jsp");
    }
}
