package com.balonbal.menu;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.Main;
import com.balonbal.core.Logger;
import com.balonbal.core.ResourceManager;
import com.balonbal.lib.Reference;
import com.balonbal.lib.Strings;

public class Menu extends BasicGameState {
	
	//Graphics variables
	private int state;
	private int width;
	private int height;
	private byte selected;
	private String lastMouse;
	private long lastMove;
	
	//Image variables
	private HashMap<String, List<Integer>> buttons;
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
		lastMove = 0;
		
		width = container.getWidth();
		height = container.getHeight();
		
		playNoHighlight = rm.getImage(Strings.PLAY_0);
		playHighlight = rm.getImage(Strings.PLAY_1);
		optionsNoHighlight = rm.getImage(Strings.OPTIONS_0);
		optionsHighlight = rm.getImage(Strings.OPTIONS_1);
		quitNoHighlight = rm.getImage(Strings.QUIT_0);
		quitHighlight = rm.getImage(Strings.QUIT_1);
		
		//Create a map of all x and y positions of the images
		buttons = new HashMap<String, List<Integer>>();
				
		addButton("play", width/2 - playHighlight.getWidth()/2, height/4,  width/2 + playHighlight.getWidth()/2, height/4 + playHighlight.getHeight());
		addButton("options", width/2 - optionsHighlight.getWidth()/2, height/2-optionsHighlight.getHeight()/2, width/2 + optionsHighlight.getWidth()/2, height/2+optionsHighlight.getHeight()/2);
		addButton("quit", width/2 - quitHighlight.getWidth()/2, height/4*3-quitHighlight.getHeight(), width/2 + quitHighlight.getWidth()/2, height/4*3+quitHighlight.getHeight());
		
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
		graphics.drawImage(play, buttons.get("play").get(0), buttons.get("play").get(1));
		graphics.drawImage(options, buttons.get("options").get(0), buttons.get("options").get(1));
		graphics.drawImage(quit, buttons.get("quit").get(0), buttons.get("quit").get(1));
		
		//Draw debug information
		if (debug) {
			graphics.drawString("Mouse X: " + (mouseX < 0 ? "??" : mouseX) +
					", Y: " + (mouseY < 0 ? "??" : height - mouseY), 10, 30);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
		debug = Main.isDebugging();
		
		Input input = container.getInput();
		mouseX = Mouse.getX();
		mouseY = Mouse.getY();
		
		String mouseOver = mouseOver(mouseX, mouseY);
		if (mouseOver != null && !mouseOver.equals(lastMouse)) {
			switch(mouseOver) {
			case "play":
				selected = 0;
				lastMouse = "play";
				break;
			case "options":
				selected = 1;
				lastMouse = "options";
				break;
			case "quit":
				selected = 2;
				lastMouse = "quit";
				break;
			}
		}
		if (Mouse.isButtonDown(0) && mouseOver != null) {
			switch(mouseOver){
			case "play":
				//stateBasedGame.enterState(Reference.PLAY_MENU_STATE);
				break;
			case "options":
				stateBasedGame.enterState(Reference.OPTIONS_STATE);
				break;
			case "quit":
				System.exit(0);
				break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_BACKSLASH)) {
			Main.setDebugging(!debug);
		} else if (input.isKeyDown(Input.KEY_UP) && (System.currentTimeMillis() - lastMove > 200)) {
			selected--;
			if (selected < 0) {
				selected = (byte) (buttons.size() - 1);
			}
			lastMove = System.currentTimeMillis();
		} else if (input.isKeyDown(Input.KEY_DOWN) && (System.currentTimeMillis() - lastMove > 200)) {
			selected++;
			if (selected == buttons.size()) {
				selected = 0;
			}
			lastMove = System.currentTimeMillis();
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
	
	public void addButton(String s, int xlow, int ylow, int xhigh, int yhigh) {

		//Add the sides of the button to the hashmap
		List<Integer> l = new ArrayList<Integer>();
		l.add(xlow);
		l.add(ylow);
		l.add(xhigh);
		l.add(yhigh);
		buttons.put(s, l);
	}
	
	public String mouseOver(int x, int y) {
		
		y = height - y;
		
		Set<String> set = buttons.keySet();
		
		//Loop through all the buttons
		for (String s: set) {
			List<Integer> l = buttons.get(s);
			
			//check if the mouse is within the button
			if (x > l.get(0) && x < l.get(2) && y > l.get(1) && y < l.get(3)) {
				return s;
			}
		}
		
		return null;
	}

}
