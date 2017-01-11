package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionController {
	
	private String dbURL;
	private String dbName;
	private String dbUser;
	private String dbPass;
	private Connection connection;
	private Statement sqlStaement;
	
	public DBConnectionController () {
		
		this.dbURL = "rdbms.strato.de";
		this.dbName = "DB2825793";
		this.dbUser = "U2825793";
		this.dbPass = "Jackychan180";
		
		
	}
	
	public void writeData(String sqlQuery) {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + dbURL + "/" + dbName, dbUser, dbPass);
			this.sqlStaement = this.connection.createStatement();
			this.sqlStaement.executeQuery(sqlQuery);
			this.sqlStaement.close();
			this.connection.close();	
			System.out.println("Write DB");
		        } catch (SQLException e) {
		        	System.out.println("Write DB erfolglos");
		        	e.printStackTrace();			
		}
		
	}
	
	public ResultSet readData(String sqlQuery){
	
		ResultSet sqlResult = null;
		
		try {
			
			this.connection = DriverManager.getConnection("jdbc:mysql://" + dbURL + "/" + dbName, dbUser, dbPass);
			this.sqlStaement = this.connection.createStatement();
			sqlResult = this.sqlStaement.executeQuery(sqlQuery);
			this.sqlStaement.close();
			this.connection.close();	
			System.out.println("Read DB");

        } catch (SQLException e) {
        	System.out.println("ReadFehler");
        	e.printStackTrace();
		}
		
		return sqlResult;
	}
}
