package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.HttpsURLConnection;

import sun.net.www.http.HttpClient;


public class DBConnectionController {
	
	private String dbURL;
	private Object resultQuery; 

	
	public DBConnectionController () {
		
		this.dbURL = "http://mdnetz.de/frogger/";		
	}
	
public void writeData(String query) throws Exception {
	
	String charset = "UTF-8"; 
	URLConnection connection = new URL(this.dbURL).openConnection();
	connection.setDoOutput(true); // Triggers POST.
	connection.setRequestProperty("Accept-Charset", charset);
	connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

	try (OutputStream output = connection.getOutputStream()) {
		output.write(query.getBytes(charset));
	}

	InputStream response = connection.getInputStream();
	System.out.println(response.toString());
	
	}	


public StringBuffer readData() throws Exception {
	
	//Daten empfangen - z.B. Anfrage auf eine API
	URL url = new URL(this.dbURL);
	URLConnection connection = url.openConnection();
	 
	BufferedReader readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	String line;
	StringBuffer sb = new StringBuffer ();
	 
	while ((line = readBuffer.readLine()) != null) {
	sb.append(line + "\n");
	System.out.println(line);
	}
	return sb;
	
}

}
