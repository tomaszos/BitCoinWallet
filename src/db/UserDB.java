package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import rest.model.User;
import utils.Utils;

public class UserDB {

	public static User getUserByLogin(String login, String password){
		Connection c = null;
		Statement stmt = null;
		User user = null;
		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			String sql = "SELECT * FROM users WHERE login = '"+login+"' AND password = '"+password+"';" ;
			System.err.println("SQL: " + sql);
			ResultSet rs = stmt.executeQuery(sql);

			while ( rs.next() ) {
				String sLogin = rs.getString("login");
				String  sPassword = rs.getString("password");
				String  sRestKey = rs.getString("rest_key");
				user = new User(sLogin, sPassword, sRestKey);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return null;
		}
		return user;
	}

	public static boolean createUser(String login, String password){
		Connection c = null;
		Statement stmt = null;
		try {
			c = ConnectionJDBC.createConnection();
			stmt = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			// dodać escapowanie stringów!!!
			String sql = "INSERT INTO users(login, password, rest_key) VALUES('"+login+"','"+password+"','"+Utils.getEncrypted(login+":"+password)+"');";
			int row = stmt.executeUpdate(sql);

			stmt.close();

			if(row != 1){
				c.rollback();
				c.close();
				return false;
			}
			c.commit();
			c.close();

		}catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return false;
		}
		return true;
	}

	public static boolean validRestKey(String login, String restKey){
		Connection c = null;
		Statement stmt = null;
		String sLogin = null;
		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			String sql = "SELECT login FROM users WHERE rest_key = '"+restKey+"';" ;
			ResultSet rs = stmt.executeQuery(sql);

			while ( rs.next() ) {
				sLogin = rs.getString("login");
			}

			rs.close();
			stmt.close();
			c.close();

			if(sLogin == null || sLogin.isEmpty() || !sLogin.equals(login)){
				return false;
			}

		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return false;
		}
		return true;
	}
}
