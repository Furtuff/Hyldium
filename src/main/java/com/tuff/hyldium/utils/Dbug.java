package com.tuff.hyldium.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class Dbug implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable arg0) {
		// TODO Auto-generated method stub
        arg0.printStackTrace();
		 return Response.status(500).build();
	}
		
	
}
