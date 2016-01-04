package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rest.model.HistoryOperation;

public class HistoryOperationDB {

	public static List<HistoryOperation> getHistoryWallet(Date from, Date to, String login, int typeCoin){
		Connection c = null;
		Statement stmt = null;
		List <HistoryOperation> historyOperations = new ArrayList<HistoryOperation>();

		try {
			c = ConnectionJDBC.createConnection();
			String walletCode = login+":"+typeCoin;

			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			String sFrom = sdf.format(from);
			String sTo = sdf.format(to);

			stmt = c.createStatement();
			String sql ="SELECT * FROM history_operations LEFT JOIN type_operations ON operation_id = type_operation_id WHERE user_login = '"+login+"' AND wallet_code='"+walletCode+"' AND (date BETWEEN '"+sFrom+"' AND '"+sTo+"');";
			System.err.println("SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while ( rs.next() ) {
			String userLogin = rs.getString("user_login");
			String sWalletCode = rs.getString("wallet_code");
			String name = rs.getString("name");
			double value = rs.getDouble("value");
			Date date = rs.getDate("date");
			historyOperations.add(new HistoryOperation (userLogin, sWalletCode, name, value, date));
		}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return historyOperations;
	}

	public static double getSaldo(String userLogin, String walletCode){
		Connection c = null;
		Statement stmt = null;
		double saldo = 0.0;

		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT SUM(value) FROM history_operations WHERE wallet_code= '"+walletCode+"' AND user_login = '"+userLogin+"';" );
			while ( rs.next() ) {
			saldo = rs.getDouble("sum");
		}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return saldo;
	}

	public static boolean runOperation(String userLogin, String walletCode, double value, int operationType){
		Connection c = null;
		Statement stmt = null;

		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			String sDate = sdf.format(date);
			String sql = null;
			if (operationType==1)
				sql = "INSERT INTO history_operations(user_login, wallet_code, operation_id, date, value) VALUES ('"+userLogin+"','"+walletCode+"',"+operationType+",'"+sDate+"',"+value+");";
			else
				sql = "INSERT INTO history_operations(user_login, wallet_code, operation_id, date, value) VALUES ('"+userLogin+"','"+walletCode+"',"+operationType+",'"+sDate+"',"+(-value)+");";
			int row = stmt.executeUpdate(sql);
			stmt.close();

			if(row != 1){
				c.rollback();
				c.close();
				return false;
			}
			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return false;
		}
		return true;
	}

	public static boolean getExchangeDolar(String login, int fromTypeCoin, int toTypeCoin, double value){
		Connection c = null;
		Statement stmt = null;
		double fromExchange = 0.0;
		double toExchange = 0.0;
		String sql = null;
		try {
			c = ConnectionJDBC.createConnection();
			String fromWalletCode = login +":"+fromTypeCoin;
			String toWalletCode = login +":"+toTypeCoin;

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT type_coin_id, exchangeDolar FROM type_coins WHERE type_coin_id= "+fromTypeCoin+" OR type_coin_id= "+toTypeCoin+";" );

			while ( rs.next() ) {
			int typeCoins = rs.getInt("type_coin_id");
				if(typeCoins == fromTypeCoin){
					fromExchange = rs.getDouble("exchangeDolar");
				}else{
					toExchange = rs.getDouble("exchangeDolar");
				}
			}

			double tmp = (value * fromExchange) / toExchange;
			double exchangeValue = Math.round(tmp*Math.pow(10, 2))/Math.pow(10, 2);;

			rs.close();

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			String sDate = sdf.format(date);

			sql = "INSERT INTO history_operations(user_login, wallet_code, operation_id, date, value) VALUES ('"+login+"','"+fromWalletCode+"',3,'"+sDate+"',"+(-value)+");";
			int rowFromUpdate = stmt.executeUpdate(sql);

			sql = "INSERT INTO history_operations(user_login, wallet_code, operation_id, date, value) VALUES ('"+login+"','"+toWalletCode+"',3,'"+sDate+"',"+exchangeValue+");";
			int rowToUpdate =  stmt.executeUpdate(sql);

			stmt.close();

			if(rowFromUpdate != 1 || rowToUpdate != 1){
				c.rollback();
				c.close();
				return false;
			}

			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}

		return true;
	}
}
