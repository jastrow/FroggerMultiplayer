package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.*;

/**
 * erstellen eines Flusses auf der Spielflaeche und erzeugen 
 * der Baeume auf dem Fluss
 * 
 * @author Die UMeLs
 *
 */
public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	public Queue<Tree> trees = new ConcurrentLinkedQueue<Tree>();
	Tree lastTree;

	/**
	 * Konstruktor
	 *
	 * @param position / yPosition des Flusses im Spielfeldraster
	 *
	 */
	public River(Integer position) {
		this.positionY = position;
		Integer modulo = position % 2;
		if(modulo.equals(0)) {
			this.leftToRight = true;
		}
		Observer.add("time", this);
		Observer.add("tree", this);
		Observer.add("start", this);
		Observer.add("resetGame", this);		
	}

	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger.equals("time")) {
			this.checkForTrees();
		}
		if(trigger.equals("tree")) {
			if(daten.typ.equals("delete") && daten.yPosition.equals(this.positionY)) {
				this.deleteTree(daten.id);
			}
		}
		if(trigger.equals("resetGame")) {
			for(Tree tree: this.trees) {
				Observer.removeMe(tree);
			}
			this.trees.clear();
			this.lastTree = null;
		}
	}

	/** 
	 * pruefen ob Stamm auf Fluss mit genuegend Abstand vorhanden
	 * 
	 */
	public void checkForTrees() {

		Integer distanceToNewTree = (new Random()).nextInt(5) + 5; 

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

	/** 
	 * erzeuge Stamm auf Fluss
	 * 
	 */
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

	/** 
	 * loesche Stamm
	 *
	 * @param id / StammID
	 * 
	 */
	public void deleteTree(Integer id) {
		for(Tree tree: trees) {
			Integer tid = tree.getId();
			if(id.equals(tid)) {
				this.trees.remove(tree);
				break;
			}
		}
	}

	/** 
	 * Kollisionspruefung Baustaemme
	 *
	 * @param positionX / xPosition des Stammes 
	 * @return Integer / ID des kollidierenden Stammes
	 */
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

	/** 
	 * ermitteln der yPosition des Flusses im Spielraster
	 *
	 * @return Integer / yPosition im Spielraster
	 * 
	 */
	public Integer getPositionY() {
		return this.positionY;
	}

	
	
	/**
	 * Nur fuer Ausgabetests
	 */
	public void showInConsole() {
		System.out.println(
			this.showRiver()
		);
	}
	
	/** 
	 * showRiver
	 *
	 * @return String / showRiver
	 */
	public String showRiver() {
		String ASCIIstreet = String.format("%02d", this.positionY) + " ";
		for(int i = 1; i <= Configuration.xFields; i++) {
			if(this.checkPosition(i)) {
				ASCIIstreet += "*";
			} else {
				ASCIIstreet += "_";
			}
		}
		ASCIIstreet += " "+this.trees.size();
		return ASCIIstreet;
	}
	
	/** 
	 * Positionspruefung eines Stammes
	 *
	 * @param p / xPosition des Stammes
	 * 
	 * @return Boolean / Position belegt
	 */
	public Boolean checkPosition(int p) {
		for(Tree tree: this.trees) {
			if(tree.getPositionX() == p) {
				return true;
			}
		}	
		return false;
	}
	
}
