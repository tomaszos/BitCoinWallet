package rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import rest.model.Response;
import rest.model.TypeCoin;
import db.TypeCoinDB;

@Path("/typeCoinService")
public class TypeCoinService {


	@GET
	@Path("/getCoin")
	@Produces(MediaType.APPLICATION_XML)
	public Response getTypeCoin( @Context HttpHeaders headers,
			@QueryParam("name") final String name) {
		Response response;

		TypeCoin typeCoin = TypeCoinDB.getCoinByName(name);
		if (typeCoin == null){
			return response = new Response(1,"Coin not exist");
		}

		return response = new Response(0,"", typeCoin);
	}
}
