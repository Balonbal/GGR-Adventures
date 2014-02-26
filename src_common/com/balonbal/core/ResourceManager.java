package com.balonbal.core;

import java.util.Hashtable;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ResourceManager {
	
	private Hashtable<String, Image> images = new Hashtable<String, Image>();
	
	public Image getImage(String key) {
		if (images.containsKey(key)) {
			return images.get(key); //If the requested image is registered, return it
		} else {
			return images.get("default"); //If not, use the default image
		}
	}
	
	public void addImage(String key, String imageLocation) {
		try {
			Image img = new Image(imageLocation);
			addImage(key, img);
		} catch (Exception e) {
			System.out.println("Unable to add image "+key+ " @ "+imageLocation);
		}
		
	}
	
	public void addImage(String key, Image image) {
		if (key == null || image == null) { //Avoid nullpointer exceptions
			System.out.println("Bad key or image, not registering");
			return;
		}
		while (images.containsKey(key)) { //avoid duplicate keys 
			key += "-";
		}
		
		images.put(key, image);
	}

}
