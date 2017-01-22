package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


/**
 * Diese Klasse steuert den Zugriff auf die Datenbankschnittstelle.
 * 
 * @author Die UMeLs
 *
 */
public class DBConnectionController {
	
	private String dbURLHigh;
	private String dbURLFrog;
	private URL url;

	
	/**
	 * Konstruktor.
	 *
	 *
	 */
	public DBConnectionController () {
		
		this.dbURLHigh = "http://mdnetz.de/frogger/";
		this.dbURLFrog = "http://mdnetz.de/frogger/frogs.php";	
	}
	
	
	/** 
	 * Methode schickt Trigger an Schnittstelle um Daten zu schreiben.
	 *
	 * @param body  / Datensatz
	 * @param urlIdentifier / Identifikator welche URL verwendet werden soll
	 * 
	 */
	public void writeData(String body,Boolean urlIdentifier) {	

		try {
			if (urlIdentifier) {
				this.url = new URL(this.dbURLHigh);
			} else {
				this.url = new URL(this.dbURLFrog);
			}

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
			//System.out.println(reader.toString());
			
			String line;
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
			}
		
			writer.close();
			reader.close();
		
			
		} catch (Exception e) {
			System.out.println("Schreiben erfolglos");
		}
	
	}
	
	
	/** 
	 * Methode schickt Trigger an Schnittstelle um Daten zu erhalten.
	 *
	 * @param urlIdentifier / Identifikator welche URL verwendet werden soll
	 * @return String[] / Daten von DB Schnittstelle
	 */
	public String[] readData(Boolean urlIdentifier) {
		String[] result = new String[3];
		
		try {
		
		//Daten empfangen - z.B. Anfrage auf eine API
			if (urlIdentifier) {
				this.url = new URL(this.dbURLHigh);
			} else {
				this.url = new URL(this.dbURLFrog);
			}
			
			URLConnection connection = url.openConnection();
			 
			BufferedReader readBuffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			Integer i = 0; 
			
			while ((line = readBuffer.readLine()) != null) {
			result[i] = line;
			i++;
			//System.out.println(line);
			}
			return result;
		
		} catch (Exception e) {
			System.out.println("Lesen erfolglos");
		}
		return result;
	}

}
