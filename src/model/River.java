package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.*;

public class River implements SubscriberInterface {
	Integer prevLength;//globale Laenge vom letzten Baum speicheren
	Integer firstLeftPositionX;//globale Hilfsvariable fuer Abstand Brett 1 zu Brett 2
	Integer lastRigthPositionX;//

	Integer positionY;
	Boolean leftToRight = false;
	public List<Tree> trees = new ArrayList<Tree>();

	//public River(Integer[] riverlines) {
	public River(Integer position) {
		// TODO Auto-generated constructor stub
		this.positionY = position;
		if((position % 2) == 1) {
			this.leftToRight = true; //Bahn 1 von links nach rechts
		}
		Observer.add("time", this);
		Observer.add("tree", this);
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			this.checkForTrees();
		} else if(trigger == "tree") {
			if(daten.typ == "delete") {
				this.deleteTree(daten.id);
			}
		}
	}
	public void checkForTrees() {

		Random random = new Random();
		Integer positionX = Configuration.xFields; //Startposition des Baums
		Integer length = random.nextInt(3)+2;//Brett der Laenge 2 - 4
		Integer distanceToNewTree = random.nextInt(3)+2; //Abstand zum naechsten Baum

		if(trees.isEmpty()) {
			if(this.leftToRight) {
				positionX = ((length-1)*-1);
				firstLeftPositionX = positionX;
			}else{
				positionX = Configuration.xFields;
			}
			prevLength = length;//Laenge wird zwischengespeichert

			Tree baum = new Tree(IdCounter.getId(), positionX, this.positionY, length, this.leftToRight);
			this.trees.add(baum);
			//System.out.println("tree with length: " + length + " created on lane: " + this.positionY + " an Position " + positionX);

		} else {
			//System.out.println("prevLength " + prevLength);

			if(this.leftToRight) {
				positionX = (((length-1)+prevLength)*-1);
				/**
				 * in ersten Durchlauf wird nicht "length+distanceToNewTree" durchlaufen
				 * d.h. das zweite Brett geht nahtlos in das erste ueber
				 * daher wird positionX dekremntiert um 1
				*/
				if(positionX+length==firstLeftPositionX){positionX--;};
				//System.out.println(positionX);
			}else{
				positionX = positionX+prevLength+distanceToNewTree;
			}
			prevLength+= length+distanceToNewTree;
			//System.out.println("prevLength " + prevLength);
			Tree baum = new Tree(IdCounter.getId(), positionX, this.positionY, length, this.leftToRight);
			this.trees.add(baum);
			//System.out.println("tree with length: " + length + " created on lane: " + this.positionY + " an Position " + positionX);
		}
	}

	public void deleteTree(Integer id) {
		for(Tree tree: trees) {
			if(id == tree.getId()) {
				trees.remove(tree);
				break;
			}
		}
	}


}
