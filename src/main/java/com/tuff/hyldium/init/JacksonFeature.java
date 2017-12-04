package com.tuff.hyldium.init;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFeature implements ContextResolver<ObjectMapper> {
	private static final ObjectMapper mapper;
	
	static {
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		
		return mapper;
	}
	
}
