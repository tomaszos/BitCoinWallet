package rest.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import rest.model.HistoryOperation;
import rest.model.Response;
import utils.Utils;
import db.HistoryOperationDB;
import db.UserDB;

@Path("/historyOperationService")
public class HistoryOperationService {


	@GET
	@Path("/historyOperations")
	@Produces(MediaType.APPLICATION_XML)
	public Response getHistoryOperations( @Context HttpHeaders headers,
			@QueryParam("l") final String userLogin, @QueryParam("typeCoin") final int typeCoin, @QueryParam("from") final String from, @QueryParam("to") final String to ) {
		Response response;

		String restKey = Utils.getKeyFromHeaders(headers);
		if(restKey == null){
			return response = new Response(1,"Empty Key");
		}
		if(!UserDB.validRestKey(userLogin, restKey)){
			return response = new Response(1,"Can't authorizations");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate;
		Date toDate;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e) {
			return response = new Response(1, "Invalid format date");
		}

		List<HistoryOperation> historyOperations = HistoryOperationDB.getHistoryWallet(fromDate, toDate, userLogin, typeCoin);
		if(historyOperations.size()<=0){
			return response = new Response(1,"Empty list");
		}
		return response = new Response(0, historyOperations);
	}
}
