package controller;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;
import model.IdCounter;

/**
 * Controller zur Implementierung der Multiplayerfunktion.
 * 
 * @author Die UMeLs
 *
 */
public class GhostController implements Runnable, SubscriberInterface {
	
	private DBConnectionController dbConnectionController;
	private String body;
	private String[] resultQuery = new String[3];
	private String name;
	private Integer id;
	private String typ;
	private String facing;
	private Integer xPosition;
	private Integer yPosition;
	private Integer time;
	private Integer length;
	private Boolean leftToRight;
	private SubscriberDaten data;
	private String readWrite;
	private Thread t;
	private Integer lastTime = 0;
	
	
	/**
	 * Konstruktor.
	 *
	 * @param dbConnectionController / Dantenbankcontroller
	 *
	 */
	public GhostController(DBConnectionController dbConnectionController) {
		this.dbConnectionController = dbConnectionController;	
		this.id = IdCounter.getId();
		Observer.add("frog", this);
		Observer.add("time", this);
		Observer.add("resetGame", this);
	}
	
	/** 
	 * Methode liest GhostFrogDaten aus Datenbank aus.
	 * 
	 */
	public void readGhostFrog() {
		this.readWrite = "read";
		if(this.t == null) {
			this.t = new Thread(this);
			t.start();
		} else {
			if(!this.t.isAlive()) {
				this.t = new Thread(this);
				t.start();
			}
		}
	}
	
	/** 
	 * Methode speichert GhostFrogDaten in Datenbank.
	 *
	 * @param data / GhostFrogDaten
	 */
	public void writeGhostFrog(SubscriberDaten data) {
		this.data = data;
		this.readWrite = "write";
		this.t = new Thread(this);
		t.start();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		if(this.readWrite.equals("write")) {
		
			try {
						
				this.body =   "name=" + URLEncoder.encode( this.data.name, "UTF-8" ) + "&" +
		                	  "id=" + URLEncoder.encode( this.data.id.toString(), "UTF-8" ) + "&" +
		                	  "typ=" + URLEncoder.encode( this.data.typ, "UTF-8" ) + "&" +
		                	  "facing=" + URLEncoder.encode( this.data.facing, "UTF-8" ) + "&" +
		                	  "xPosition=" + URLEncoder.encode( this.data.xPosition.toString(), "UTF-8" ) + "&" +
		                	  "yPosition=" + URLEncoder.encode( this.data.yPosition.toString(), "UTF-8" ) + "&" +
		                	  "time=" + URLEncoder.encode( "0", "UTF-8" ) + "&" +
		                	  "length=" + URLEncoder.encode( "0", "UTF-8" ) + "&" +
		                	  "leftToRight=" + URLEncoder.encode( "t", "UTF-8" ) + "&" +
		                	  "treeRide=" + URLEncoder.encode( "0", "UTF-8" );	
				
				this.dbConnectionController.writeData(this.body,false);
			
			} catch (Exception e) {
				System.out.println("GeisterfroschDaten nicht geschrieben");
			}			
		} else {
	
			try {
				this.resultQuery = this.dbConnectionController.readData(false);
				String[] actString = this.resultQuery[0].split(Pattern.quote("|"));
				this.name = actString[0];
				this.typ = actString[2];
				this.facing = actString[3]; 
				this.xPosition = Integer.valueOf(actString[4]);
				this.yPosition = Integer.valueOf(actString[5]);
				this.time = Integer.valueOf(actString[6]);
				this.length = Integer.valueOf(actString[7]);
				if (actString[8].toString() != null) {
					this.leftToRight = true;
				} else {
					this.leftToRight = null;
				}
				
				SubscriberDaten data = new SubscriberDaten();
				data.name = this.name;
				data.id = this.id;
				data.typ = this.typ;
				data.facing = this.facing;
				data.xPosition = this.xPosition;
				data.yPosition = this.yPosition;
				data.time = this.time;
				data.length = this.length;
				data.leftToRight = this.leftToRight;
				Platform.runLater(new Runnable() {
					public void run() {
						String string = actString[11];
						DateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
						Date date;
						try {
							date = format.parse(string);
							Date actDate = new Date();
							//System.out.println(date.getTime() + "   " + actDate.getTime());
							//System.out.println(((actDate.getTime() - date.getTime())/1000));
							if ( ((actDate.getTime() - date.getTime())/1000) < 65) {
								Observer.trigger("ghostfrog", data);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});	
				
			} catch (Exception e) {
				
				System.out.println("GeisterfroschDaten nicht gelesen: " + e.getMessage());
			}

		}
	}


	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	@Override
	public void calling(String trigger, SubscriberDaten data) {
		// TODO Auto-generated method stub
		
		switch(trigger) {
			case "frog": {
				switch (data.typ) {
					case "new": {
						this.writeGhostFrog(data);
						break;
					}
					case "move": {
						this.writeGhostFrog(data);
						break;
					}
				}
				break;
			}
			case "time": {
				this.time = data.time;
				Integer diff = data.time - this.lastTime;
				if (diff.compareTo(500) > 0) {
					this.lastTime = data.time;
					this.readGhostFrog();
				}
				break;
			}
			case "resetGame": {
				this.lastTime = 0;
				break;
			}
		}
	}
}
