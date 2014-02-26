package com.balonbal.menu.options;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.Main;
import com.balonbal.lib.Reference;

public class Options extends BasicGameState {
	
	//Screen variables
	private int state;
	private int width;
	private int height;
	private byte selected;
	
	//Fonts
	private TrueTypeFont header;
	private TrueTypeFont item;
	private Color unselectedColor = Color.WHITE;
	private Color selectedColor = Color.ORANGE;
	
	
	//Filepath variables
	private String url;
	private String OS;
	
	//debugging variables
	private boolean debug = false;
	private int mouseX;
	private int mouseY;
	
	public Options(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Class main = Main.class;
		url = main.getResource("Main.class").toString();
		OS = System.getProperty("os.name");
		url = url.replaceAll("%20", " ");
		url = (OS.equalsIgnoreCase("LINUX") ? url.substring(url.indexOf(":")+1) : url.substring(url.indexOf(":")+2));
		
		System.out.println(url);
		System.out.println(OS);
		
		Font header = new Font("Verdana", Font.BOLD, 50);
		Font item = new Font("Verdana", Font.PLAIN, 32);
		this.header = new TrueTypeFont(header, true);
		this.item = new TrueTypeFont(item, true);
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics)throws SlickException {
		graphics.drawString(OS + "     " +url, 50, 50);
		

		
		
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
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			stateBasedGame.enterState(Reference.MENU_STATE);
		}
		
	}

	@Override
	public int getID() {
		return state;
	}

}
