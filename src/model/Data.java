package model;

public class Data {
	
	private String name;  //Frog / Car / Balken
	private Integer id;	 // ArrayID zur identifikation bitte bei 0 beginnen macht es einfacher 	
	private Integer xPosition; //neue XPosition im Raster des Objektes auf der Scene bei 0 wird Objekt entfernt  
	private Integer yPosition; //neue XPosition im Raster des Objektes auf der Scene bei 0 wird Objekt entfernt
	
	public Data(String name, Integer id, Integer xPosition, Integer yPosition) {
		this.name = name;
		this.id = id;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getID() {
		return this.id;
	}
	
	public Integer getXPosition() {
		return this.xPosition;
	}
	
	public Integer getYPosition() {
		return this.yPosition;
	}

}
