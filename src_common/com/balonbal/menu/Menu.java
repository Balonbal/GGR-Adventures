package com.balonbal.menu;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.Main;
import com.balonbal.lib.Reference;
import com.balonbal.lib.Strings;

public class Menu extends BasicGameState {
	
	//Graphics variables
	private int state;
	private int width;
	private int height;
	private byte selected;
	
	//Image variables
	private Image playNoHighlight;
	private Image playHighlight;
	private Image optionsNoHighlight;
	private Image optionsHighlight;
	
	//Debugging variables
	private boolean debug = false;
	private int mouseX;
	private int mouseY;
	
	public Menu(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		Main.registerImages();
		selected = 0;
		width = container.getWidth();
		height = container.getHeight();
		playNoHighlight = new Image(Reference.PLAY0);
		playHighlight = new Image(Reference.PLAY1);
		optionsNoHighlight = new Image(Reference.OPTIONS0);
		optionsHighlight = new Image(Reference.OPTIONS1);
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.drawString("Version: " + Strings.VERSION, 0, height - 20);
		
		Image play = null;
		Image options = null;
		
		//Select image to display
		switch(selected) {
		case 0:
			play = playHighlight;
			options = optionsNoHighlight;
			break;
		case 1:
			play = playNoHighlight;
			options = optionsHighlight;
			break;
		}
		
		//Draw images
		graphics.drawImage(play, width/2 - play.getWidth()/2, (height/4));
		graphics.drawImage(options, width/2 - options.getWidth()/2, height/7*3);
		
		//Draw debug information
		if (debug) {
			graphics.drawString("Mouse X: " + (mouseX < 0 ? "??" : mouseX) +
					", Y: " + (mouseY < 0 ? "??" : mouseY), 10, 30);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
		debug = Main.isDebugging();
		
		Input input = container.getInput();
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		if (input.isKeyPressed(Input.KEY_BACKSLASH)) {
			Main.setDebugging(!debug);
		} else if (input.isKeyPressed(Input.KEY_UP)) {
			selected--;
			if (selected < 0) {
				selected = 1;
			}
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			selected++;
			if (selected > 1) {
				selected = 0;
			}
		} else if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch(selected) {
			case 0:
				//stateBasedGame.enterState(Reference.PLAY_MENU_STATE);
				break;
			case 1:
				stateBasedGame.enterState(Reference.OPTIONS_STATE);
				break;
			}
		}
	}

	@Override
	public int getID() {
		
		return state;
	}
	
	public void drawCenteredString(Graphics graphics, String s, int y) {
		graphics.drawString(s, width/2-graphics.getFont().getWidth(s)/2, y);
	}

}
