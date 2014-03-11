package com.balonbal.menu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.balonbal.menu.options.Option;
import com.balonbal.menu.options.OptionTab;

public class Options extends BasicGameState {
	
	//Screen variables
	private int state;
	private int width;
	private int height;
	private byte selectedHeader;
	private String selectedItem;
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
		
		//Set up the fonts we are going to use
		Font header = new Font("Verdana", Font.BOLD, 20);
		Font item = new Font("Verdana", Font.PLAIN, 12);
		this.header = new TrueTypeFont(header, true);
		this.item = new TrueTypeFont(item, true);
		
		//
		options = new ArrayList<OptionTab>();
		OptionTab t = new OptionTab("Video");
		t.addOption(new Option(Strings.FULLSCREEN_SETTING_STRING, "Fullscreen", Settings.fullscreen + "", 50, 70));
	}

	@Override
	public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics graphics)throws SlickException {
		
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
