package model;

public class Bar {
	
	private Integer length = 0;
	private Integer ID;
	private Integer xPosition;
	private Integer yPosition;
	
	public Bar (Integer xPosition, Integer yPosition, Integer ID, Integer length) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	public Integer getLength() {
		return this.length;
	}
	
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Integer getXPosition() {
		return this.xPosition;
	}
	
	public int getYPosition() {
		return this.yPosition;
	}
	
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	
}
