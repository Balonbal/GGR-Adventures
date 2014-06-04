package com.balonbal.game.character;

import java.util.HashMap;

import org.newdawn.slick.Image;

import com.balonbal.game.effect.Effect;

public abstract class Character {
	
	public String name;
	public HashMap<String, Image> images;
	public HashMap<String, Effect> defaultEffects;

	public Character(String name, HashMap<String, Image> images, HashMap<String, Effect> effects) {
		this.name = name;
		this.images = images;
		this.defaultEffects = effects;
	}
	
	public abstract Image[] charSelectAnimation();
	
	public abstract Image[] walkingAnimation(String direction);
}
