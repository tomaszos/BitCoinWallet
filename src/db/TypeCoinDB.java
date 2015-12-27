package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import rest.model.TypeCoin;

public class TypeCoinDB {
	public static TypeCoin getCoinByName(String name){
		Connection c = null;
		Statement stmt = null;
		TypeCoin tCoin = null;
		try {
			c = ConnectionJDBC.createConnection();

			stmt = c.createStatement();
			String sql = "SELECT * FROM type_coins WHERE name = '"+name+"';" ;
			ResultSet rs = stmt.executeQuery(sql);

			while ( rs.next() ) {
				String nameCoin = rs.getString("name");
				double exchangeDolar = rs.getDouble("exchangeDolar");
				tCoin = new TypeCoin(nameCoin, exchangeDolar);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			return null;
		}
		return tCoin;
	}
}
