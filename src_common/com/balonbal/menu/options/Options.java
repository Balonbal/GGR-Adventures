package com.balonbal.menu.options;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.Main;
import com.balonbal.lib.Reference;
import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;

public class Options extends BasicGameState {
	
	//Screen variables
	private int state;
	private int width;
	private int height;
	private byte selectedHeader;
	private String selectedItem;
	protected List<HashMap<String, String>> tables;
	
	//Fonts
	private TrueTypeFont header;
	private TrueTypeFont item;
	private Color unselectedColor = Color.white;
	private Color selectedColor = Color.orange;
	
	//debugging variables
	private boolean debug = false;
	private int mouseX;
	private int mouseY;
	
	public Options(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		
		//Get the dimensions for the screen
		width = gameContainer.getWidth();
		height = gameContainer.getHeight();
		
		//Set up the fonts we are going to use
		Font header = new Font("Verdana", Font.BOLD, 50);
		Font item = new Font("Verdana", Font.PLAIN, 22);
		this.header = new TrueTypeFont(header, true);
		this.item = new TrueTypeFont(item, true);
		
		//Add the settings panels
		tables = new ArrayList<HashMap<String, String>>();
		
		//Hashtable for video settings
		HashMap<String, String> video = new HashMap<String, String>();
		video.put("header", "VIDEO");
		video.put(Strings.FULLSCREEN_SETTING_STRING, "" + Settings.fullscreen);
		video.put("Aspect ratio", Settings.width + "x" + Settings.height);
		video.put("Frames per second", "" + Settings.framerate);
		
		//Hashtable for key bindings
		HashMap<String, String> keybinds = new HashMap<String, String>();
		keybinds.put("header", "KEYBINDS");
		keybinds.put(Strings.KEYBINDS_A_ENABLED, "" + Settings.enabled_a);
		keybinds.put(Strings.KEYBIND_A_ATTACK, Settings.attack_a);
		
		tables.add(video);
		tables.add(keybinds);
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics)throws SlickException {
		
		//Loop through all tables for rendering
		for (int i = 0; i < tables.size(); i++) {  
			
			//Get all keys in the table
			Set<String> keys = tables.get(i).keySet();
			
			Color ch = null;
			
			//check what color we should color the current table
			if (i == selectedHeader) {
				ch = selectedColor;
			} else {
				ch = unselectedColor;
			}
			
			
			int lastPos = 100;
			
			for (String s: keys) {
				
				
				String val = tables.get(i).get(s);
				
				if (s.equals("header")) {
					//Handle headers separately
					header.drawString(50 + (i+1) * header.getWidth("MAX HEADER")/2 - header.getWidth(val), 30, val, ch);
					if (i != selectedHeader) break; //End it here if we only need the header
				} else {
					
					Color ci = null;
					
					if (s.equalsIgnoreCase(selectedItem)) {
						ci = selectedColor;
					} else if (selectedItem == null) {
						selectedItem = s;
						ci = selectedColor;
					} else {
						ci = unselectedColor;
					}
					
					//Update the position
					lastPos += item.getHeight() + 3;
					
					//Check if the string is to be drawn
					if (i == selectedHeader) {
						String str = s + ": " + val;
						item.drawString(width/2 - item.getWidth(str)/2, lastPos, str, ci);
					}
				}
			}
		}

		
		
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
		/*} else if (input.isKeyPressed(Input.KEY_UP)) {
			selectedItem--;
			if (selectedItem < 0) {
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
			}*/
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			stateBasedGame.enterState(Reference.MENU_STATE);
		}
		
	}

	@Override
	public int getID() {
		return state;
	}

}
