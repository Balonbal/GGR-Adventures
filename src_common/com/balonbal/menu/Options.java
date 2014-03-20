package com.balonbal.menu;

import java.awt.Font;
import java.util.ArrayList;

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
import com.balonbal.core.Logger;
import com.balonbal.lib.Reference;
import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;
import com.balonbal.menu.options.ConfigurationHandler;
import com.balonbal.menu.options.Option;
import com.balonbal.menu.options.OptionTab;

public class Options extends BasicGameState {
	
	//Screen variables
	private int state;
	private int width;
	private int height;
	private int selectedHeader = 2;
	private int selectedItem;
	private ArrayList<OptionTab> options;
	
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
		
		//Create a resolution array
		Settings.initResolutions();
		
		//Set up the fonts we are going to use
		Font header = new Font("Verdana", Font.BOLD, 20);
		Font item = new Font("Verdana", Font.PLAIN, 12);
		this.header = new TrueTypeFont(header, true);
		this.item = new TrueTypeFont(item, true);
		
		//Create an arrayList for all the tabs in the menu
		options = new ArrayList<OptionTab>();
		
		//Create a video tab
		OptionTab video = new OptionTab("Video");
		video.addOption(new Option(Strings.FULLSCREEN_SETTING_STRING, "Fullscreen", new String[] {"true", "false"}, (Settings.fullscreen + ""), 0, 0));
		video.addOption(new Option("resolution", "Resolution", Settings.AVAILABLE_RESOLUTIONS, Settings.width + "x" + Settings.height, 0, 0));
		video.addOption(new Option(Strings.FRAMERATE_SETTING_STRING, "Framerate limit", intArrToStringArr(Settings.AVAILABLE_FRAMERATES), Settings.framerate + "", 0, 0));
		options.add(video);
		
		//Create a audio tab
		OptionTab audio = new OptionTab("Audio");
		//TODO add audio options
		options.add(audio);
		
		//Create a keybinds tab
		OptionTab keyBinds = new OptionTab("Keybinds");
		keyBinds.addOption(new Option(Strings.KEYBINDS_A_ENABLED, "Enable set A", new String[] { "true", "false" }, Settings.enabled_a+"", 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBINDS_B_ENABLED, "Enable set B", new String[] { "true", "false" }, Settings.enabled_b+"", 0, 0));
		
		keyBinds.addOption(new Option(Strings.NO_OPTION, " ", new String[] { " " }, " ", 0, 0));
		keyBinds.addOption(new Option(Strings.NO_OPTION, "-- Keybinds A --", new String[] { " " }, " ", 0, 0));
		
		keyBinds.addOption(new Option(Strings.KEYBIND_A_FORWARD, "Move forward", Settings.AVAILABLE_KEYS, Settings.move_forward_a, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_A_BACK, "Move back", Settings.AVAILABLE_KEYS, Settings.move_back_a, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_A_RIGHT, "Move right", Settings.AVAILABLE_KEYS, Settings.move_forward_a, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_A_LEFT, "Move left", Settings.AVAILABLE_KEYS, Settings.move_left_a, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_A_ATTACK, "Attack", Settings.AVAILABLE_KEYS, Settings.attack_a, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_A_USE, "Interact", Settings.AVAILABLE_KEYS, Settings.use_a, 0, 0));
		
		keyBinds.addOption(new Option(Strings.NO_OPTION, " ", new String[] { " " }, " ", 0, 0));
		keyBinds.addOption(new Option(Strings.NO_OPTION, "-- Keybinds B --", new String[] { " " }, " ", 0, 0));
		
		keyBinds.addOption(new Option(Strings.KEYBIND_B_FORWARD, "Move forward", Settings.AVAILABLE_KEYS, Settings.move_forward_b, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_B_BACK, "Move back", Settings.AVAILABLE_KEYS, Settings.move_back_b, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_B_RIGHT, "Move right", Settings.AVAILABLE_KEYS, Settings.move_forward_b, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_B_LEFT, "Move left", Settings.AVAILABLE_KEYS, Settings.move_left_b, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_B_ATTACK, "Attack", Settings.AVAILABLE_KEYS, Settings.attack_b, 0, 0));
		keyBinds.addOption(new Option(Strings.KEYBIND_B_USE, "Interact", Settings.AVAILABLE_KEYS, Settings.use_b, 0, 0));
		options.add(keyBinds);
		
		setPositions();
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics)throws SlickException {
		
		int headerX = width/4;
		
		for (int i = 0; i < options.size(); i++) {
			OptionTab tab = options.get(i);
			
			//Define the color to draw the header for this tab
			Color headerColor = unselectedColor;
			if (i == selectedHeader) {
				headerColor = selectedColor;
			}
			
			//Draw the header
			header.drawString(headerX, 50, tab.getHeader(), headerColor);
			//Move the xpos 
			headerX += header.getWidth(tab.getHeader()) + 20;
			
			if (i == selectedHeader) {
				 ArrayList<Option> options = tab.getOptions();
				 for (int j = 0; j < options.size(); j++) {
					 
					 //Select the color for each item
					 Color itemColor = unselectedColor;
					 if (j == selectedItem) {
						 itemColor = selectedColor;
					 }
					 
					 //Get the current option to draw
					 Option o = options.get(j);
					 
					 String name = o.getDisplayName();
					 String val = o.getValue();
					 if (o.getID().equals(Strings.FRAMERATE_SETTING_STRING) && val.equals("-1")) {
						 val = "UNLIMITED";
					 }
					 if (o.getID().equals(Strings.NO_OPTION)) {
						 item.drawString(o.getX(), o.getY(), name, Color.lightGray);
					 } else {
						 item.drawString(o.getX(), o.getY(), name + ": " + val, itemColor);
					 }
				}
			}
		}
		
		//Draw debug information
		if (debug) {
			//Draw mouse position
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
			
			//Go one up until it finds an option
			do {
				selectedItem--;
			} while (selectedItem > 0 ? options.get(selectedHeader).getOptions().get(selectedItem).getID().equals(Strings.NO_OPTION) : false);
			
			//Start from the bottom if it reaches the top
			if (selectedItem < 0) {
				selectedItem = options.get(selectedHeader).getOptions().size()-1;
			}
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			
			int size = options.get(selectedHeader).getOptions().size();
			
			//Go one down until it finds an option
			do {
				selectedItem++;
			} while (selectedItem < size ? options.get(selectedHeader).getOptions().get(selectedItem).getID().equals(Strings.NO_OPTION) : false);
			
			//If it reaches the end of the options, start from the top
			if (selectedItem > size -1) {
				selectedItem = 0;
			}
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			//Shift the option one right
			changeOption(selectedHeader, selectedItem, "+");
		} else if (input.isKeyPressed(Input.KEY_LEFT)) {
			//Shift the option one left
			changeOption(selectedHeader, selectedItem, "-");
		} else if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			saveOptions();
			stateBasedGame.enterState(Reference.MENU_STATE);
		}
		
	}

	@Override
	public int getID() {
		return state;
	}
	
	public String[] intArrToStringArr(int[] arr) {
		String[] r = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			r[i] = arr[i] + "";
		}
		return r;
	}
	
	public void setPositions() {
		//Loop through all tabs
		for (OptionTab tab: options) {
			
			//Get the options for each tab
			ArrayList<Option> list = tab.getOptions();
			int lastY = 100; //Keep track of y position, and start at 50
			
			//Loop through options and set their position
			for (Option o: list) {
				int xstart = width/2 - item.getWidth(o.getDisplayName() + ": " + o.getValue())/2;
				o.move(xstart, lastY);
				lastY += 10 + item.getHeight();
			}
		}
	}
	
	private void changeOption(int head, int item, String mod) {
		//get the option
		Option opt = options.get(head).getOptions().get(item);
		switch (mod) {
		case "+":
			opt.nextValue();
			break;
		case "-":
			opt.prevValue();
			break;
		}
	}
	
	private void saveOptions() {
		for (OptionTab tab: options) {
			for (Option o: tab.getOptions()) {
				ConfigurationHandler.changeSetting(o.getID(), o.getValue());
			}
		}
	}

}
