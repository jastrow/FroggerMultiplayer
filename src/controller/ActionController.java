package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ActionController implements KeyListener {
	
	
	public ActionController() {

		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		System.out.println(c);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		System.out.println(c);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		System.out.println(c);
	}

}
