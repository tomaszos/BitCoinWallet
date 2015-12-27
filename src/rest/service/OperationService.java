package rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import rest.model.Response;
import db.HistoryOperationDB;

@Path("/operationService")
public class OperationService {

	@GET
	@Path("/payment")
	@Produces(MediaType.APPLICATION_XML)
	public Response Payment( @Context HttpHeaders headers,
			@QueryParam("l") final String userLogin, @QueryParam("coin") final int typeCoin, @QueryParam("value") final double value) {
		Response response;

		String walletCode = userLogin + ":" + typeCoin;

		boolean result = HistoryOperationDB.runOperation(userLogin, walletCode, value, 1);
		if (!result){
			return response = new Response(1,"Payment failed. Try again");
		}

		return response = new Response(0,"OK");
	}
}
