package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Rivers implements SubscriberInterface {

	public List<River> riverlines = new ArrayList<River>();

	public Rivers() {
		for(Integer position: Configuration.riverLines) {
			this.riverlines.add(
				new River(position)
			);
		}
		Observer.add("tree", this);
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		this.showStreetsInConsole();
	}

	public void showStreetsInConsole() {
		for(River river: this.riverlines) {
			river.showInConsole();
		}
		System.out.println("\r\n");
	}

}