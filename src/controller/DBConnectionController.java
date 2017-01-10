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
		
		this.dbURL = "10.0.1.4:3306";
		this.dbName = "highscore";
		this.dbUser = "JackRyan";
		this.dbPass = "jackychan";
		
		
	}
	
	public void writeData(String sqlQuery) {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + dbURL + "/" + dbName, dbUser, dbPass);
			this.sqlStaement = this.connection.createStatement();
			this.sqlStaement.executeQuery(sqlQuery);
			this.sqlStaement.close();
			this.connection.close();	
			
		        } catch (SQLException e) {
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

        } catch (SQLException e) {
        	e.printStackTrace();
		}
		
		return sqlResult;
	}
}
