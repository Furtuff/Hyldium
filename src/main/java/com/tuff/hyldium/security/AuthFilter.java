package com.tuff.hyldium.security;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.model.UserModel;

public class AuthFilter implements ContainerRequestFilter {
	public static final String AUTHORIZATION = "authorization";
	@Override
	public void filter(ContainerRequestContext containerRequest) throws IOException {
		String method = containerRequest.getMethod();
		String path = containerRequest.getUriInfo().getPath(true);
		
		if(method.equals("GET")|| path.contains("login")) {
			return;
		}
		
		String auth = containerRequest.getHeaderString(AUTHORIZATION);
		
		if(auth == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		UserModel userModel = new UserModel();
		String[] userData = BasicAuth.decode(auth);
		userModel.login = userData[0];
		userModel.password = userData[1].getBytes();

		UserModel authenticationResult =  Dao.login(userModel).get(0);
		
		if (authenticationResult == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();

		containerRequest.setSecurityContext(new HyldiumSecurityContext(authenticationResult, scheme));
	}

}
