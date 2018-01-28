package com.tuff.hyldium.rest;

import com.tuff.hyldium.dao.Dao;
import com.tuff.hyldium.lucene.Search;
import com.tuff.hyldium.model.ItemModel;
import com.tuff.hyldium.model.SubModel;
import com.tuff.hyldium.model.UserModel;
import com.tuff.hyldium.utils.Constant;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Produces(MediaType.TEXT_HTML)
@Path("")
public class ManageApi extends Api {

	@GET
	@Path("/login/")
	public Response login(@javax.ws.rs.core.Context HttpServletRequest request) {
		HashMap<String, String> map = new HashMap<>();
		map.put("url", "http://localhost:8080/Hyldium/hoho/login/");
		Viewable v = new Viewable("/login.jsp", map);
		return Response.ok(v).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login/")
	public Response login(UserModel usermodel) {
		if (usermodel.login != null && usermodel.password != null) {
			List<UserModel> loggedUser = Dao.login(usermodel);
			if (loggedUser != null) {
				if (loggedUser.get(0).role.contains((Dao.ADMIN))) {
					return Response.ok(new Viewable("/logged.jsp", loggedUser)).build();
				}
			}
		}
		Map<String, String> error = new HashMap<String, String>();
		error.put("message", "Try Again");
		return Response.ok(new Viewable("/login.jsp", error)).build();
	}

	@GET
	@Path("/logged")
	public Response logged() {
		return Response.ok().entity(new Viewable("/logged.jsp")).build();
	}

	@RolesAllowed(Dao.ADMIN)
	@POST
	@Path("/logged")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getSubmenu(SubModel subModel) {
		Response response = Response.noContent().build();
		switch (subModel.sub) {
		case Constant.ITEM:
			response = Response.ok(new Viewable("/products.jsp",Dao.getItemsList(0))).build();
			break;
		case Constant.ORDER:
			response = Response.ok(new Viewable("/logged.jsp")).build();
			break;
		case Constant.USER:
			List<UserModel> userList = Dao.getUserList();
			response = Response.ok(new Viewable("/users.jsp", userList)).build();
			break;

		}
		return response;
	}

	@RolesAllowed(Dao.ADMIN)
	@POST
	@Path("/logged/user/{action}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response userAction(List<UserModel> userModel, @PathParam("action") String action) {
		Response response = Response.noContent().build();
		if (action.equals("add")) {
			for (UserModel u : userModel) {
				Dao.addUser(u);
			}
			response = Response.ok(new Viewable("/users.jsp", Dao.getUserList())).build();
		} else if (action.equals("update")) {
			for (UserModel u : userModel) {
				Dao.updateUser(u);
				try {
					Thread.sleep((long)10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			response = Response.ok(new Viewable("/users.jsp", Dao.getUserList())).build();
		}

		return response;
	}
	@RolesAllowed(Dao.ADMIN)
	@POST
	@Path("/logged/products/{action}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response productAction(List<ItemModel> items, @PathParam("action") String action) {
		Response response = Response.noContent().build();
        if (action.equals("update")) {
            for (ItemModel i : items) {
                Dao.crudItem(i);

            }
            response = Response.ok(new Viewable("/products.jsp", Dao.getItemsList(0))).build();
        } else if (action.equals("search")) {
            if (!items.get(0).reference.isEmpty()) {
                response = Response.ok(new Viewable("/products.jsp", Dao.getItemsList(items.get(0).reference))).build();
            } else {
                List<ItemModel> list = null;
                Search s = new Search();
                String caca = items.get(0).toString();
                if (caca.endsWith(" ")) {
                    caca = caca.substring(0, caca.length() - 1);
                }
                try {
                    list = s.search(caca);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response = Response.ok(new Viewable("/products.jsp", list)).build();

            }
        }
        //response = Response.ok(new Viewable("products.jsp",Dao.getItemsList(0))).build();

		return response;

	}
}
