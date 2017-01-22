package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.*;

/**
 * Klasse zum erstellen eines Flusses auf der Spielflaeche und erzeugen 
 * der Baeume auf dem Fluss.
 * 
 * @author Die UMeLs
 *
 */
public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	public Queue<Tree> trees = new ConcurrentLinkedQueue<Tree>();
	Tree lastTree;
	Integer newRandomTree;

	/**
	 * Konstruktor.
	 *
	 * @param position / yPosition des Flusses im Spielfeldraster
	 *
	 */
	public River(Integer position) {
		this.positionY = position;
		Integer modulo = (position / 50) % 2;
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
	 * Methode zum pruefen ob Stamm auf Fluss mit genuegend Abstand vorhanden.
	 * 
	 */
	public void checkForTrees() { 

		if(trees.isEmpty()) {
			this.makeTree();
		}	else if(this.trees.size() < Configuration.treeMaxPerLane) {

				if(this.leftToRight){
					Integer freeFieldsLeft = this.lastTree.getPositionX();
					if(freeFieldsLeft.compareTo(this.newRandomTree) > 0) {
						this.makeTree();
					}
				}
				else{
					Integer freeFieldsRight = Configuration.xGameZone - this.lastTree.getPositionXend();
					if(freeFieldsRight.compareTo(this.newRandomTree) > 0){
						this.makeTree();
					}
				}

			}
	}

	/** 
	 * Methode erzeugt Stamm auf Fluss.
	 * 
	 */
	public void makeTree(){

		Integer length = (new Random()).nextInt(3) + 2;

		Integer positionX = Configuration.xGameZone;
		if(this.leftToRight) {
			positionX = 1 - Configuration.xTree[(length-2)];
		}

		Tree baum = new Tree(positionX, this.positionY, length, this.leftToRight);
		this.trees.add(baum);
		this.lastTree = baum;
		this.newRandomTree = (new Random()).nextInt(250) + 200;


	}

	/** 
	 * Methode loescht Stamm.
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
	 * Kollisionspruefung Baustaemme.
	 *
	 * @param positionX / xPosition des Stammes
	 * @param positionXend / positionXend  
	 * @return Integer / ID des kollidierenden Stammes
	 */
	public Integer collisionCheck(Integer positionX, Integer positionXend) {
		Integer quarterFrog = (int)Configuration.xFrog / 4;
		Integer treeStart;
		Integer treeEnd;
		for(Tree tree: this.trees) {
			treeStart = tree.getPositionX() + quarterFrog;
			treeEnd = tree.getPositionXend() - quarterFrog;
			if(
				(positionX.compareTo(treeStart) > 0 && positionX.compareTo(treeEnd) < 0)
				||
				(positionXend.compareTo(treeStart) > 0 && positionXend.compareTo(treeEnd) < 0)
			) {
				return tree.getId();
			}
		}	
		return 0;
	}

	/** 
	 * Methode zum ermitteln der yPosition des Flusses im Spiel.
	 *
	 * @return Integer / yPosition im Spiel
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
