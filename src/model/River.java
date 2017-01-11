package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.*;

public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	public Queue<Tree> trees = new ConcurrentLinkedQueue<Tree>();
	Tree lastTree;

	public River(Integer position) {
		this.positionY = position;
		Integer modulo = position % 2;
		if(modulo.equals(0)) {
			this.leftToRight = true;
		}
		Observer.add("time", this);
		Observer.add("tree", this);
		Observer.add("start", this);
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger.equals("time")) {
			this.checkForTrees();
		} else if(trigger.equals("tree")) {
			if(daten.typ.equals("delete") && daten.yPosition.equals(this.positionY)) {
				this.deleteTree(daten.id);
			}
		}
		if(trigger.equals("start")) {
			for(Tree tree: this.trees) {
				Observer.removeMe(tree);
			}
			this.trees.clear();
		}
	}

	public void checkForTrees() {

		Integer distanceToNewTree = (new Random()).nextInt(5) + 2; 

		if(trees.isEmpty()) {
			this.makeTree();
		}	else if(this.trees.size() < Configuration.treeMaxPerLane) {

				if(this.leftToRight){
					Integer freeFieldsLeft = this.lastTree.getPositionX() - 1;

					if(freeFieldsLeft.equals(distanceToNewTree)) {
						this.makeTree();
					}
				}
				else{
					Integer freeFieldsRight = Configuration.xFields - (this.lastTree.getPositionX() + this.lastTree.getLength() - 1);
					if(freeFieldsRight.equals(distanceToNewTree)){
						this.makeTree();
					}
				}

			}
	}

	public void makeTree(){

		Integer length = (new Random()).nextInt(3) + 2;

		Integer positionX = Configuration.xFields;
		if(this.leftToRight) {
			positionX = 1 - length;
		}

		Tree baum = new Tree(positionX, this.positionY, length, this.leftToRight);
		this.trees.add(baum);
		this.lastTree = baum;

	}

	public void deleteTree(Integer id) {
		for(Tree tree: trees) {
			Integer tid = tree.getId();
			if(id.equals(tid)) {
				this.trees.remove(tree);
				break;
			}
		}
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

	
	
	/**
	 * Nur für Ausgabetests
	 */
	public void showInConsole() {
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
