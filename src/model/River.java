package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.*;

public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	//public List<Tree> trees = new ArrayList<Tree>();
	public Queue<Tree> trees = new ConcurrentLinkedQueue<Tree>();
	Tree lastTree;

	public River(Integer position) {
		this.positionY = position;
		if((position % 2) == 0) {
			this.leftToRight = true; //Bahn 1 von links nach rechts
		}
		Observer.add("time", this);
		Observer.add("tree", this);
		Observer.add("start", this);
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			System.out.print(daten.time+" - ");
			this.checkForTrees();
		} else if(trigger == "tree") {
			if(daten.typ == "delete" && daten.yPosition == this.positionY) {
				this.deleteTree(daten.id);
			}
		}
		if(trigger == "start") {
			for(Tree tree: this.trees) {
				Observer.removeMe(tree);
			}
			this.trees.clear();
		}
	}



	public void checkForTrees() {

		Integer distanceToNewTree = (new Random()).nextInt(5) + 2; //Abstand zum naechsten Baum

		System.out.println("River "+this.positionY+"- "+this.trees.size());

		if(trees.isEmpty()) {
			this.makeTree();
		}	else if(this.trees.size() < Configuration.treeMaxPerLane) {

				if(this.leftToRight){
					Integer freeFieldsLeft = (this.lastTree.getPositionX()-1);

					if(freeFieldsLeft==distanceToNewTree){
						this.makeTree();
					}
				}
				else{
					Integer freeFieldsRight = (Configuration.xFields-(this.lastTree.getPositionX()+this.lastTree.getLength()-1));

					if(freeFieldsRight==distanceToNewTree){
						this.makeTree();
					}
				}

			}
	}

	public void makeTree(){

		Integer length = (new Random()).nextInt(3) + 2;//Brett der Laenge 2 - 4

		Integer positionX = Configuration.xFields;
		if(this.leftToRight) {
			positionX = 1 - length;
		}

		Tree baum = new Tree(positionX, this.positionY, length, this.leftToRight);
		this.trees.add(baum);
		this.lastTree = baum;

	}

	public void deleteTree(Integer id) {
		System.out.println("Delete Tree from Observer "+id);
		for(Tree tree: trees) {
			if(id == tree.getId()) {
				this.trees.remove(tree);
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
	
	public Integer collisionCheck(Integer positionX) {
		for(Tree tree: this.trees) {
			Integer treeStart = tree.getPositionX();
			Integer treeEnd = tree.getPositionX() + tree.getLength() - 1;
			if(positionX >= treeStart && positionX <= treeEnd) {
				return tree.getId();
			}
		}	
		return 0;
	}

	public Integer getPositionY() {
		return this.positionY;
	}
}
