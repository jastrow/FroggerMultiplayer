package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Streets implements SubscriberInterface {

	public List<Street> streetlines = new ArrayList<Street>(); 
	
	public Streets() {
		for(Integer position: Configuration.streetLines) {
			this.streetlines.add(
				new Street(position)
			);
		}
		// Observer.add("time", this);
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "time") {
			this.showStreetsInConsole();
		}
	}
	
	public void showStreetsInConsole() {
		for(Street street: this.streetlines) {
			street.showInConsole();
		}
		System.out.println("\r\n");
	}

}
