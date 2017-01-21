package controller;

import java.net.URLEncoder;
import java.util.regex.Pattern;

import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;
import model.IdCounter;

public class GhostController implements SubscriberInterface {
	
	private DBConnectionController dbConnectionController;
	private String[] resultQuery = new String[3];
	String name;
	Integer id = IdCounter.getId();
	String typ;
	String facing;
	Integer xPosition;
	Integer yPosition;
	Integer time;
	Integer length;
	Boolean leftToRight;
	
	
	public GhostController(DBConnectionController dbConnectionController) {
		this.dbConnectionController = dbConnectionController;	
		Observer.add("frog", this);
		Observer.add("time", this);
	}
	
	private void writeGhostFrog(SubscriberDaten data)  {
		
		try {
		
		String leftToRoghtString;
		
		if (data.leftToRight == null) {
			leftToRoghtString = "f";
		} else {
			leftToRoghtString = "t";
		}
		
		String body = "name=" + URLEncoder.encode( data.name, "UTF-8" ) + "&" +
                	  "id=" + URLEncoder.encode( data.id.toString(), "UTF-8" ) + "&" +
                	  "typ=" + URLEncoder.encode( data.typ, "UTF-8" ) + "&" +
                	  "facing=" + URLEncoder.encode( data.facing, "UTF-8" ) + "&" +
                	  "xPosition=" + URLEncoder.encode( data.xPosition.toString(), "UTF-8" ) + "&" +
                	  "yPosition=" + URLEncoder.encode( data.yPosition.toString(), "UTF-8" ) + "&" +
                	  "time=" + URLEncoder.encode( data.time.toString(), "UTF-8" ) + "&" +
                	  "length=" + URLEncoder.encode( data.length.toString(), "UTF-8" ) + "&" +
                	  "lestToRight=" + URLEncoder.encode( leftToRoghtString, "UTF-8" );	
		
		this.dbConnectionController.writeData(body);
		
		} catch (Exception e) {
			System.out.println("GeisterfroschDaten nicht geschrieben");
		}
		
	}
	
	private void readGhostFrog() {
		
		try {
			this.resultQuery = this.dbConnectionController.readData(false);
			
			for (int i = 0 ; i < this.resultQuery.length; i++) {
				String[] actString = this.resultQuery[i].split(Pattern.quote("|"));
				this.name = actString[0];
				this.typ = actString[2];
				this.facing = actString[3]; 
				this.xPosition = Integer.valueOf(actString[4]);
				this.yPosition = Integer.valueOf(actString[6]);
				this.time = Integer.valueOf(actString[7]);
				this.length = Integer.valueOf(actString[8]);
				if (actString[9] == "t") {
					this.leftToRight = true;
				} else {
					this.leftToRight = null;
				}
				System.out.println(actString);
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
					Observer.trigger("ghostfrog", data);
				}
			});

			
		} catch (Exception e) {
			System.out.println("GeisterfroschDaten nicht gelesen");
		}

	}


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
				//this.readGhostFrog();
				break;
			}
		}
	}
}
