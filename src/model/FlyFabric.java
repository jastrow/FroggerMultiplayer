package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * Fliegen sitzen nur auf Bäumen.
 * Werden generiert, wenn ein neuer Baum entsteht.
 * 
 * @author Die UMeLs
 *
 */

public class FlyFabric implements SubscriberInterface {

	private Queue<Fly> flys = new ConcurrentLinkedQueue<Fly>();
	private Random rand;
	
	/**
	 * Konstruktor
	 *
	 *
	 */
	public FlyFabric() {
		this.rand = new Random();
		Observer.add("tree", this);
		Observer.add("start", this);
		Observer.add("fly", this);
		Observer.add("time", this);
		Observer.add("frog", this);
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger.equals("tree")) {
			if(data.typ.equals("new")) {
				this.randomFly(data);
			}
			if(data.typ.equals("delete")) {
				this.deleteByTree(data.id);
			}
		}
		if(trigger.equals("start")) {
			this.reset();
		}
		if(trigger.equals("fly")) {
			if(data.typ == "delete") {
				this.deleteFly(data.id);
			}
		}
		if(trigger.equals("frog")) {
			this.checkFrog(data);
		}
		
		if(trigger == "time") {
			//this.showInConsole();
		}
	}
	
	/** 
	 * loescht alle Fliegen
	 * 
	 */
	private void reset() {
		for(Fly fly: this.flys) {
			Observer.removeMe(fly);
		}
		this.flys.clear();
	}
	
	/** 
	 * erstellt eine zufaellige Fliege
	 *
	 * @param data / Datenobjekt mit Positionsdaten
	 * 
	 */
	private void randomFly(SubscriberDaten data) {
		Integer num = this.rand.nextInt(Configuration.flyRandom);
		if(num == 0) {
			Integer xOnTree = this.rand.nextInt( Configuration.xTree[(data.length-2)] - Configuration.xFly );
			Integer xpos = data.xPosition + xOnTree;
			Fly newfly = new Fly(xOnTree, xpos, data.yPosition, data.id, data.leftToRight);
			this.flys.add(newfly);
		}
		
	}
	
	/** 
	 * loescht Fliege
	 *
	 * @param id / FliegenID
	 * 
	 */
	private void deleteFly(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getId().equals(id)) {
				Observer.removeMe(fly);
				this.flys.remove(fly);
				break;
			}
		}
	}
	
	/** 
	 * Fliege wird durch Baumstamm geloescht
	 *
	 * @param id / FliegenID
	 * 
	 */
	private void deleteByTree(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getFlyOnTreeId().equals(id)) {
				Observer.removeMe(fly);
				this.flys.remove(fly);
				break;
			}
		}
	}
	
	/** 
	 * Fliege an Hand ID auslesen
	 *
	 * @param id / FliegenID
	 * 
	 * @return Fly / Fliegenobjekt
	 * 
	 */
	private Fly getById(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getId().equals(id)) {
				return fly;
			}
		}
		return null;
	}
	
	/** 
	 * prueft ob Frosch auf Fliegenfeld
	 *
	 * @param data  / Datenobjekt mit Positionsdaten
	 * 
	 */
	private void checkFrog(SubscriberDaten data) {
		Integer flyId = this.collisionCheck(data.xPosition, data.xPositionEnd, data.yPosition);
		
		if(flyId.compareTo(0) > 0) {
			Fly flyEaten = this.getById(flyId);
			if(flyEaten != null) {
				flyEaten.triggerObserver("delete");
				Observer.trigger("flyeaten", data);
			}
		}
	}
	
	private Integer collisionCheck(Integer x, Integer xEnd, Integer y) {
		for(Fly fly: this.flys) {
			if( fly.getY().compareTo(y) == 0 ) {
				if(
					(fly.getX().compareTo(x) > 0 && fly.getX().compareTo(xEnd) < 0)
					||
					(fly.getXend().compareTo(x) > 0 && fly.getXend().compareTo(xEnd) < 0)
				) {
					return fly.getId();
				}
			}
		}
		return 0;
		
	}
	

	

	
}
