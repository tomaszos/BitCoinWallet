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

	public static List<HistoryOperation> getHistoryWallet(Date from, Date to, String login, String code){
		Connection c = null;
		Statement stmt = null;
		List <HistoryOperation> historyOperations = new ArrayList<HistoryOperation>();

		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM history_operations LEFT JOIN type_operations ON operation_id = type_operation_id WHERE user_login = '"+login+"' AND wallet_code='"+code+"' AND (date BETWEEN "+from+" AND "+to+");");
			while ( rs.next() ) {
			String userLogin = rs.getString("user_login");
			String walletCode = rs.getString("wallet_code");
			String name = rs.getString("name");
			double value = rs.getDouble("value");
			Date date = rs.getDate("date");
			historyOperations.add(new HistoryOperation (userLogin, walletCode, name, value, date));
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

			ResultSet rs = stmt.executeQuery( "SELECT value, operation_id FROM history_operations WHERE wallet_code= '"+walletCode+"' AND user_login = '"+userLogin+"';" );
			while ( rs.next() ) {
			double value = rs.getDouble("value");
			int operationId = rs.getInt("operation_id");
			if(operationId == 1){
				saldo += value;
			}else{
				saldo -= value;
			}
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
		boolean result = false;

		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
			String sDate = sdf.format(date);

			String sql = "INSERT INTO history_operations(user_login, wallet_code, operation_id, date, value) VALUES ('"+userLogin+"','"+walletCode+"',"+operationType+",'"+sDate+"',"+value+");";
			System.err.println("SQL: " + sql);
			result = stmt.execute(sql);

			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return result;
	}

	public static double getExchangeDolar(String name){
		Connection c = null;
		Statement stmt = null;
		double exchangeDolar = 0.0;

		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery( "SELECT exchangeDolar FROM type_coins WHERE name= '"+name+"';" );
			while ( rs.next() ) {
			exchangeDolar = rs.getDouble("exchangeDolar");
		}
			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return exchangeDolar;
	}
}
