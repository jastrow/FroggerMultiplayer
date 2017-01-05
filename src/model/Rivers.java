package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;

public class Rivers {

	public List<River> riverlines = new ArrayList<River>();

	public Rivers() {
		for(Integer position: Configuration.riverLines) {
			this.riverlines.add(
				new River(position)
			);
		}

	}

}