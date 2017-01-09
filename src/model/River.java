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
		if((position % 2) == 0) {
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

	Random random = new Random();
	Integer distanceToNewTree = random.nextInt(3)+2; //Abstand zum naechsten Baum

	public void checkForTrees() {

		if(trees.isEmpty()) {
			this.makeTree();
		}	else{



			}
	}

	public void makeTree(){

		Integer length = random.nextInt(3)+2;//Brett der Laenge 2 - 4
		Integer positionX = Configuration.xFields; //Startposition des Baums
		if(this.trees.size() < Configuration.treeMaxPerLane) {
			if(this.leftToRight) {
					positionX = ((length-1)*-1);
			}

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
