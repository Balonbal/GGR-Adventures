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
import com.balonbal.lib.Reference;
import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;
import com.balonbal.menu.options.Option;
import com.balonbal.menu.options.OptionTab;

public class Options extends BasicGameState {
	
	//Screen variables
	private int state;
	private int width;
	private int height;
	private int selectedHeader;
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
		
		setPositions();
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics)throws SlickException {
		
		int headerX = 50;
		
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
					 item.drawString(o.getX(), o.getY(), o.getDisplayName() + ": " + o.getValue(), itemColor);
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

}
