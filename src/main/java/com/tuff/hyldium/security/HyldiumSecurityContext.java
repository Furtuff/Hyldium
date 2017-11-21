package com.tuff.hyldium.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.tuff.hyldium.entity.User;
import com.tuff.hyldium.model.UserModel;

public class HyldiumSecurityContext implements SecurityContext {
	private UserModel user;
	private String scheme;
	
	public HyldiumSecurityContext(UserModel user, String scheme) {
		this.user = user;
		this.scheme = scheme;
	}

	@Override
	public String getAuthenticationScheme() {
		
		return SecurityContext.BASIC_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return (Principal) this.user;
	}

	@Override
	public boolean isSecure() {
		//TODO apply return "https".equals(this.scheme);
		return "http".equals(this.scheme);
	}

	@Override
	public boolean isUserInRole(String arg0) {
		if(this.user.role != null) {
			return user.role.contains(arg0);
		}
		return false;
	}

}
