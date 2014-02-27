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
import com.balonbal.core.ResourceManager;
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
	private Image quitNoHighlight;
	private Image quitHighlight;
	
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
		
		ResourceManager rm = Main.getResourceManager();
		
		selected = 0;
		
		width = container.getWidth();
		height = container.getHeight();
		playNoHighlight = rm.getImage(Strings.PLAY_0);
		playHighlight = rm.getImage(Strings.PLAY_1);
		optionsNoHighlight = rm.getImage(Strings.OPTIONS_0);
		optionsHighlight = rm.getImage(Strings.OPTIONS_1);
		quitNoHighlight = rm.getImage(Strings.QUIT_0);
		quitHighlight = rm.getImage(Strings.QUIT_1);
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.drawString("Version: " + Strings.VERSION, 0, height - 20);
		
		Image play = null;
		Image options = null;
		Image quit = null;
		
		//Select image to display
		switch(selected) {
		case 0:
			play = playHighlight;
			options = optionsNoHighlight;
			quit = quitNoHighlight;
			break;
		case 1:
			play = playNoHighlight;
			options = optionsHighlight;
			quit = quitNoHighlight;
			break;
		case 2:
			play = playNoHighlight;
			options = optionsNoHighlight;
			quit = quitHighlight;
			break;
		}
		
		//Draw images
		graphics.drawImage(play, width/2 - play.getWidth()/2, (height/4));
		graphics.drawImage(options, width/2 - options.getWidth()/2, height/2-options.getHeight()/2);
		graphics.drawImage(quit, width/2 - quit.getWidth()/2, height/4*3-quit.getHeight());
		
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
		} else if (input.isKeyDown(Input.KEY_UP)) {
			selected--;
			if (selected < 0) {
				selected = 2;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			selected++;
			if (selected > 2) {
				selected = 0;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch(selected) {
			case 0:
				//stateBasedGame.enterState(Reference.PLAY_MENU_STATE);
				break;
			case 1:
				stateBasedGame.enterState(Reference.OPTIONS_STATE);
				break;
			case 2:
				System.exit(0);
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
