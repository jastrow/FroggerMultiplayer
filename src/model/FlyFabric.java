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
 * @author max
 *
 */
public class FlyFabric implements SubscriberInterface {

	private Queue<Fly> flys = new ConcurrentLinkedQueue<Fly>();
	private Random rand;
	
	public FlyFabric() {
		this.rand = new Random();
		Observer.add("tree", this);
		Observer.add("start", this);
		Observer.add("fly", this);
		Observer.add("time", this);
		Observer.add("frog", this);
	}
	
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
	
	private void reset() {
		for(Fly fly: this.flys) {
			Observer.removeMe(fly);
		}
		this.flys.clear();
	}
	
	private void randomFly(SubscriberDaten data) {
		Integer num = this.rand.nextInt(10);
		if(num == 0) {
			Integer xpos = data.xPosition + this.rand.nextInt(data.length);
			Fly newfly = new Fly(xpos, data.yPosition, data.id, data.leftToRight);
			this.flys.add(newfly);
		}
		
	}
	
	private void deleteFly(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getId().equals(id)) {
				Observer.removeMe(fly);
				this.flys.remove(fly);
				break;
			}
		}
	}
	
	private void deleteByTree(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getFlyOnTreeId().equals(id)) {
				Observer.removeMe(fly);
				this.flys.remove(fly);
				break;
			}
		}
	}
	
	private Fly getById(Integer id) {
		for(Fly fly: this.flys) {
			if(fly.getId().equals(id)) {
				return fly;
			}
		}
		return null;
	}
	
	private void checkFrog(SubscriberDaten data) {
		Integer flyId = this.checkPosition(data.xPosition, data.yPosition);
		if(flyId.compareTo(0) > 0) {
			Fly flyEaten = this.getById(flyId);
			if(flyEaten != null) {
				flyEaten.triggerObserver("delete");
				Observer.trigger("flyeaten", data);
			}
		}
	}
	
	public Integer checkPosition(Integer x, Integer y) {
		for(Fly fly: this.flys) {
			if(fly.getY() == y && fly.getX() == x) {
				return fly.getId();
			}
		}
		return 0;
	}
	
	public void showInConsole() {
		String out = "";
		for(Integer y = 5; y <= 8; y++) {
			for(Integer x = 1; x <= Configuration.xFields; x++) {
				if(this.checkPosition(x, y) > 0) {
					out += "*";
				} else {
					out += "_";
				}
			}
			out += "\r\n";
		}
		System.out.println(out);
	}
	
}
