package com.balonbal.game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.Main;

public class PlayMenu extends BasicGameState {
	
	private boolean debug = false;
	private int mouseX;
	private int mouseY;
	
	private int state;
	
	public PlayMenu(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics)
			throws SlickException {
		
		//Draw debug information
		if (debug) {
			//Draw mouse position
			graphics.drawString("Mouse X: " + (mouseX < 0 ? "??" : mouseX) +
					", Y: " + (mouseY < 0 ? "??" : mouseY), 10, 30);
		}
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
		debug = Main.isDebugging();
		
		Input input = container.getInput();
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		
		if (input.isKeyPressed(Input.KEY_BACKSLASH)) {
			Main.setDebugging(!debug);
		}
		
	}

	@Override
	public int getID() {
		return state;
	}

}
