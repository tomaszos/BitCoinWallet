package rest.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import rest.model.Response;
import rest.model.Wallet;
import db.WalletDB;

@Path("/walletService")
public class WalletService {

	@GET
	@Path("/getWallet")
	@Produces(MediaType.APPLICATION_XML)
	public Response getWallet( @Context HttpHeaders headers,
			@QueryParam("l") final String login, @QueryParam("typeCoin") final int typeCoin) {
		Response response;

		Wallet wallet = WalletDB.getWallet(login, typeCoin);
		if (wallet == null){
			return response = new Response(1,"Wallet not exist");
		}

		return response = new Response(0,"", wallet);
	}

	@GET
	@Path("/getWallets")
	@Produces(MediaType.APPLICATION_XML)
	public Response getWallets( @Context HttpHeaders headers,
			@QueryParam("l") final String login) {
		Response response;

		List<Wallet> wallets = WalletDB.getWallets(login);
		if (wallets == null && wallets.isEmpty()){
			return response = new Response(1,"Wallets not exist");
		}

		return response = new Response(0,"", wallets);
	}

	@GET
	@Path("/createWallet")
	@Produces(MediaType.APPLICATION_XML)
	public Response createWallet( @Context HttpHeaders headers,
			@QueryParam("l") final String login, @QueryParam("type") final int typeCoin) {
		Response response;

		Wallet wallet = WalletDB.createWallet(login, typeCoin);
		if (wallet == null){
			return response = new Response(1,"Can't create wallet");
		}

		return response = new Response(0,"", wallet);
	}
}
