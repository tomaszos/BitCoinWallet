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

@Path("/operationService")
public class OperationService {

	@GET
	@Path("/payment")
	@Produces(MediaType.APPLICATION_XML)
	public Response Payment( @Context HttpHeaders headers,
			@QueryParam("l") final String userLogin, @QueryParam("typeCoin") final int typeCoin, @QueryParam("value") final double value) {
		Response response;

		String restKey = Utils.getKeyFromHeaders(headers);
		if(restKey == null){
			return response = new Response(1,"Empty Key");
		}
		if(!UserDB.validRestKey(userLogin, restKey)){
			return response = new Response(1,"Can't authorizations");
		}
		
		String walletCode = userLogin + ":" + typeCoin;

		boolean result = HistoryOperationDB.runOperation(userLogin, walletCode, value, 1);
		if (!result){
			return response = new Response(1,"Payment failed. Try again");
		}

		return response = new Response(0,"OK");
	}

	@GET
	@Path("/payoff")
	@Produces(MediaType.APPLICATION_XML)
	public Response Payoff( @Context HttpHeaders headers,
			@QueryParam("l") final String userLogin, @QueryParam("coin") final int typeCoin, @QueryParam("value") final double value) {
		Response response;

		String restKey = Utils.getKeyFromHeaders(headers);
		if(restKey == null){
			return response = new Response(1,"Empty Key");
		}
		if(!UserDB.validRestKey(userLogin, restKey)){
			return response = new Response(1,"Can't authorizations");
		}
		
		String walletCode = userLogin + ":" + typeCoin;

		double saldo = HistoryOperationDB.getSaldo(userLogin, walletCode);

		if(value > saldo){
			return response = new Response(1,"Payment failed. Low balance");
		}

		boolean result = HistoryOperationDB.runOperation(userLogin, walletCode, value, 2);
		if (!result){
			return response = new Response(1,"Payment failed. Try again");
		}

		return response = new Response(0,"OK");
	}

	@GET
	@Path("/exchange")
	@Produces(MediaType.APPLICATION_XML)
	public Response Exchange( @Context HttpHeaders headers,
			@QueryParam("l") final String userLogin, @QueryParam("fromCoin") final int fromCoin, @QueryParam("toCoin") final int toCoin, @QueryParam("value") final double value) {
		Response response;

		String restKey = Utils.getKeyFromHeaders(headers);
		if(restKey == null){
			return response = new Response(1,"Empty Key");
		}
		if(!UserDB.validRestKey(userLogin, restKey)){
			return response = new Response(1,"Can't authorizations");
		}
		
		String fromWalletCode = userLogin + ":" + fromCoin;
		double saldo = HistoryOperationDB.getSaldo(userLogin, fromWalletCode);
		if(value > saldo){
			return response = new Response(1,"Payment failed. Low balance");
		}

		boolean result = HistoryOperationDB.getExchangeDolar(userLogin, fromCoin, toCoin, value);
		if (!result){
			return response = new Response(1,"Payment failed. Try again");
		}

		return response = new Response(0,"OK");
	}
}
