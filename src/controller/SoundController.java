package controller;

import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Klasse steuert die Wiedergabe der implementierten Sounddateien.
 * 
 * @author Die UMeLs
 *
 */
public class SoundController implements SubscriberInterface {
	
	Boolean musicOn;

	Media music;
	MediaPlayer musicPlayer;
	
	AudioClip hop;
	
	/**
	 * Konstruktor.
	 *
	 *
	 */
	public SoundController() {
		this.music = new Media( 
			ClassLoader.getSystemResource("views/music.mp3").toString()
		);		
		this.musicPlayer = new MediaPlayer(this.music);
		
		this.hop = new AudioClip( 
				ClassLoader.getSystemResource("views/hop.mp3").toString()
			);		
		
		this.musicOn = true;
		
		playMusic();
		Observer.add("sound", this);
		Observer.add("key", this);
	}
	
	/** 
	 * Methode zum Musik abspielen.
	 * 
	 */
	public void playMusic() {
		this.musicPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				musicPlayer.seek(Duration.ZERO);
			}
		});
		musicPlayer.play();		
	}

	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "key") {
			this.hop.play();
		}
		if(trigger == "sound") {
			switch (data.typ) {
				case "musicOff": { 
					this.musicOn = false;
					this.musicPlayer.stop();
					break;
				}
				case "musicOn": { 
					this.musicOn = true;
					this.musicPlayer.play();
					break;
				}
				case "toggle": { 
					if(this.musicOn) {
						this.musicOn = false;
						this.musicPlayer.stop();
					} else {
						this.musicOn = true;
						this.musicPlayer.play();
					}
					break;
				}
			}
		}
	}
}
