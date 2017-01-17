package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Steuert den Zugriff auf die Datenbankschnittstelle
 * 
 * @author Die UMeLs
 *
 */
public class DBConnectionController {
	
	private String dbURL;

	
	/**
	 * Konstruktor
	 *
	 *
	 */
	public DBConnectionController () {
		
		this.dbURL = "http://mdnetz.de/frogger/";		
	}
	
	
	/** 
	 * sendet Post an DB Schnittstelle
	 *
	 * @param playerName  / Spielername
	 * @param playerScore / PunkteStand
	 * @throws IOException / Ausnahme wenn Anfrage fehlschlaegt
	 * 
	 */
	public void writeData(String playerName, Integer playerScore) throws IOException {	
		String body = "name=" + URLEncoder.encode( playerName, "UTF-8" ) + "&" +
	                  "zeit=" + URLEncoder.encode( playerScore.toString(), "UTF-8" );
	
		URL url = new URL(this.dbURL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod( "POST" );
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setUseCaches( false );
		connection.setRequestProperty( "Content-Type","application/x-www-form-urlencoded" );
		connection.setRequestProperty( "Content-Length", String.valueOf(body.length()) );
	
		OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
		writer.write( body );
		writer.flush();
	
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()) );
	
		writer.close();
		reader.close();
	
	}
	
	
	/** 
	 * schickt Trigger an Schnittstelle um HighScoreDaten zu erhalten
	 *
	 * @throws Exception / Ausnahme wenn Anfrage fehlschlaegt
	 * @return String[] / Daten von DB Schnittstelle
	 */
	public String[] readData() throws Exception {
		
		//Daten empfangen - z.B. Anfrage auf eine API
		URL url = new URL(this.dbURL);
		URLConnection connection = url.openConnection();
		 
		BufferedReader readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		String[] result = new String[3];
		Integer i = 0; 
		
		while ((line = readBuffer.readLine()) != null) {
		result[i] = line;
		i++;
		}
		return result;
		
	}

}
