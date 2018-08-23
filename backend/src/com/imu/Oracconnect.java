package com.imu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Oracconnect {
	protected Oracconnect() {};
	protected static Connection connection=null;
	public static Connection getConnection() {
		if(connection==null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "scott", "wcnmd101");
				return connection;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
		
	}
	public static ResultSet getQuery(String sql,java.sql.Statement s) {
		try {
			ResultSet r = null;
			 r = s.executeQuery(sql);
			return r;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static int getInsert(String sql,Statement s) {
		
			try {
				return s.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
	}
}
