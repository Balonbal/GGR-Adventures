package com.balonbal;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.balonbal.core.ResourceManager;
import com.balonbal.game.Game;
import com.balonbal.game.PlayMenu;
import com.balonbal.lib.Reference;
import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;
import com.balonbal.menu.Menu;
import com.balonbal.menu.options.ConfigurationHandler;
import com.balonbal.menu.options.Options;

public class Main extends StateBasedGame {
	
	private static boolean debugging;
	
	private static ResourceManager rm;
	
	public Main(String name) {
		
		super(name);
		this.addState(new Menu(Reference.MENU_STATE));
		this.addState(new PlayMenu(Reference.PLAY_MENU_STATE));
		this.addState(new Options(Reference.OPTIONS_STATE));
		this.addState(new Game(Reference.PLAY_STATE));
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//Add states
		this.getState(Reference.MENU_STATE).init(container, this);
		this.getState(Reference.PLAY_MENU_STATE).init(container, this);
		this.getState(Reference.OPTIONS_STATE).init(container, this);
		this.getState(Reference.PLAY_STATE).init(container, this);
		//Select the initial state
		this.enterState(Reference.MENU_STATE);
	}
	
	public static void main(String[] args) {
		AppGameContainer container;
		
		rm = new ResourceManager();
		
		ConfigurationHandler ch = new ConfigurationHandler();
		ch.initialize();
		
		try {
			container = new AppGameContainer(new Main(Strings.GAME_NAME + " - " + Strings.VERSION));
			container.setDisplayMode(Settings.width, Settings.height, Settings.fullscreen);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "WHOPS! Something went terribly wrong");
		}
	}
	
	public static boolean isDebugging() {
		return debugging;
	}
	
	public static void setDebugging(boolean bol) {
		debugging = bol;
	}
	
	public static void registerImages() {
		rm.addImage(Strings.DEFAULT_IMAGE, Reference.DEFAULT_IMAGE);
		
		rm.addImage(Strings.PLAY_0, Reference.PLAY0);
		rm.addImage(Strings.PLAY_1, Reference.PLAY1);
		rm.addImage(Strings.OPTIONS_0, Reference.OPTIONS0);
		rm.addImage(Strings.OPTIONS_1, Reference.OPTIONS1);
		rm.addImage(Strings.QUIT_0, Reference.QUIT0);
		rm.addImage(Strings.QUIT_1, Reference.QUIT1);
	}
	
	public static ResourceManager getResourceManager() {
		return rm;
	}

}
