package com.tuff.hyldium.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.ItemModel;
import com.tuff.hyldium.model.SearchText;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductApi extends Api {
	@POST
	@Path("/crud")
	public Response crudItems(ItemModel itemModel) {
		if (itemModel == null) {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
		}
		return Response.ok(Dao.crudItem(itemModel)).header("X-HM-RC", "OK").build();
	}

	@GET
	@Path("/{offset}")
	public Response getItemsList(@PathParam("offset") int offset) {

		return Response.ok(Dao.getItemsList(offset)).build();
	}

	@POST
	@Path("/search")
	public Response searchByName(SearchText param) {
		if (param != null) {

			Search search = new Search();
			try {
				return Response.ok(search.search(param.text)).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();
			}
		} else {
			return Response.ok().header("X-HM-RC", REQUEST_ERROR).build();

		}
	}
}
