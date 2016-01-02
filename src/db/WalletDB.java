package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rest.model.Wallet;

public class WalletDB {

	public static Wallet createWallet(String login, int typeCoin){
		Connection c = null;
		Statement stmt = null;
		Wallet wallet = null;
		try {
			c = ConnectionJDBC.createConnection();
			stmt = c.createStatement();

			String code = login+":"+typeCoin;
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			String sDate = sdf.format(date);

			String sql = "INSERT INTO wallets"
					+ "(code, type_coin_id, user_login, date)"
					+ "VALUES ('"+code+"',"+typeCoin+",'"+login+"','"+sDate+"');";

			int row = stmt.executeUpdate(sql);

			stmt.close();

			if(row != 1){
				c.rollback();
				c.close();
				return null;
			}
			wallet = new Wallet(code, typeCoin, login, date);

			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return wallet;
	}

	public static Wallet getWallet(String login, int typeCoin){
		Connection c = null;
		Statement stmt = null;
		Wallet wallet = null;
		try {
			c = ConnectionJDBC.createConnection();
			stmt = c.createStatement();

			String sql = "SELECT * FROM wallets WHERE user_login= '"+login+"' AND type_coin_id = "+typeCoin+";";

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String code = rs.getString("code");
				int typeCoinId = rs.getInt("type_coin_id");
				String userLogin = rs.getString("user_login");
				Date date = rs.getDate("date");
				Double saldo = HistoryOperationDB.getSaldo(userLogin, userLogin+":"+typeCoinId);
				wallet = new Wallet(code,typeCoinId,userLogin, saldo, date);
			}

			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return wallet;
	}

	public static List<Wallet> getWallets(String login){
		Connection c = null;
		Statement stmt = null;
		List<Wallet> wallets = new ArrayList<Wallet>(3);
		try {
			c = ConnectionJDBC.createConnection();
			stmt = c.createStatement();

			String sql = "SELECT * FROM wallets WHERE user_login= '"+login+"';";

			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String code = rs.getString("code");
				int typeCoinId = rs.getInt("type_coin_id");
				String userLogin = rs.getString("user_login");
				Date date = rs.getDate("date");
				Double saldo = HistoryOperationDB.getSaldo(userLogin, userLogin+":"+typeCoinId);
				wallets.add(new Wallet(code,typeCoinId,userLogin, saldo, date));
			}

			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return wallets;
	}
}
