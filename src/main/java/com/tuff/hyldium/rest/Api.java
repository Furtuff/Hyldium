package com.tuff.hyldium.rest;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
public abstract class Api {
	protected static final long REQUEST_ERROR = -1;
	protected static final long REQUEST_SUCCESS = 1;

}
