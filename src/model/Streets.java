package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;

public class Streets {

	public List<Street> streetlines = new ArrayList<Street>(); 
	
	public Streets() {
		for(Integer position: Configuration.streetLines) {
			this.streetlines.add(
				new Street(position)
			);
		}
		
	}

}
