package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

	ConnectionJDBC(){
		//utils
	}

	public static Connection createConnection() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		// słabe rozwiązanie
		Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","portfel", "portfel");
		c.setAutoCommit(false);
		return c;
	}
}
