package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import application.*;
import sandbox.FrogMain;

public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	public List<Tree> trees = new ArrayList<Tree>();

	public River(Integer position) {
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



	public void checkForTrees() {

		Integer distanceToNewTree = (new Random()).nextInt(5) + 2; //Abstand zum naechsten Baum

		if(trees.isEmpty()) {
			this.makeTree();
		}	else if(this.trees.size() < Configuration.treeMaxPerLane) {

			Tree lastBaum = (trees.get(trees.size()-1));//.getPositionX();

				if(this.leftToRight){
					Integer freeFieldsLeft = (lastBaum.getPositionX()-1);

					if(freeFieldsLeft==distanceToNewTree){
						//System.out.println("Abstand links: " + distanceToNewTree);
						this.makeTree();
					}
				}
				else{
					Integer freeFieldsRight = (Configuration.xFields-(lastBaum.getPositionX()+lastBaum.getLength()-1));

					if(freeFieldsRight==distanceToNewTree){
						//System.out.println("Abstand rechts: " + distanceToNewTree);
						this.makeTree();
					}
				}

			}
	}

	public void makeTree(){

		Integer length = (new Random()).nextInt(3)+2;//Brett der Laenge 2 - 4

		Integer positionX = Configuration.xFields;
		if(this.leftToRight) {
			positionX = 1 - length;
		}

		Tree baum = new Tree(positionX, this.positionY, length, this.leftToRight);
		this.trees.add(baum);
		System.out.println("leftToRight: " + leftToRight + ", tree with length: " + length + " created on lane: " + this.positionY + " an Position " + positionX);

	}

	public void deleteTree(Integer id) {
		for(Tree tree: trees) {
			if(id == tree.getId()) {
				trees.remove(tree);
				break;
			}

		}
	}

	/**
	 * Nur für Ausgabetests
	 */
	public void  showInConsole() {
		System.out.println(
			this.showRiver()
		);
	}
	public String showRiver() {
		String ASCIIstreet = String.format("%02d", this.positionY) + " ";
		for(int i = 1; i <= Configuration.yFields; i++) {
			if(this.checkPosition(i)) {
				ASCIIstreet += "*";
			} else {
				ASCIIstreet += "_";
			}
		}
		return ASCIIstreet;
	}
	public Boolean checkPosition(int p) {
		for(Tree tree: this.trees) {
			if(tree.getPositionX() == p) {
				return true;
			}
		}	
		return false;
	}
}
