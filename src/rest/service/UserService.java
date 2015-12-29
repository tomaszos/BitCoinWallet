package rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import db.UserDB;
import rest.model.Response;
import rest.model.User;

@Path("/userServices")
public class UserService {

	@GET
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_XML)
	public Response getUser( @Context HttpHeaders headers,
			@QueryParam("l") final String login, @QueryParam("p") final String password) {
		Response response;

		User user = UserDB.getUserByLogin(login, password);
		if (user == null){
			return response = new Response(1,"User is null");
		}

		return response = new Response(0,"", user);
	}

	@GET
	@Path("/createUser")
	@Produces(MediaType.APPLICATION_XML)
	public Response createUser( @Context HttpHeaders headers,
			@QueryParam("l") final String login, @QueryParam("p") final String password) {
		Response response;

		boolean result = UserDB.createUser(login, password);
		if (!result){
			return response = new Response(1,"Can't store");
		}

		User user = UserDB.getUserByLogin(login, password);
		if (user == null){
			return response = new Response(1,"Can't create user", user);
		}

		return response = new Response(0,"OK", user);
	}
}
