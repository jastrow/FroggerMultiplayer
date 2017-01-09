package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Streets {

	public List<Street> streetlines = new ArrayList<Street>(); 
	
	public Streets() {
		for(Integer position: Configuration.streetLines) {
			this.streetlines.add(
				new Street(position)
			);
		}
	}
	
	public void showStreetsInConsole() {
		for(Street street: this.streetlines) {
			street.showInConsole();
		}
		System.out.println("\r\n");
	}

	public boolean collisionCheck(Integer positionX, Integer positionY) {
		for(Street street: this.streetlines) {
			if(street.getPositionY() == positionY) {
				if(street.collisionCheck(positionX, positionY)) {
					return true;
				}
			}
		}
		return false;
	}

	
}
