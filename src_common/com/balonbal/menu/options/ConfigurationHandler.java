package com.balonbal.menu.options;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import com.balonbal.core.Logger;
import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;

public class ConfigurationHandler {
	
	public static HashMap<String, String> configuration;
	
	public void initialize() {
		configuration = new HashMap<String, String>();
		
		try {
			createSettings();
			load();
		} catch(FileNotFoundException e) {
			Logger.log(" No settings found. Using from default.");
		}
		
		//Set all the settings TODO Improve this
		Settings.fullscreen = configuration.get(Strings.FULLSCREEN_SETTING_STRING).equalsIgnoreCase("true");
		Settings.width = Integer.parseInt(configuration.get(Strings.WIDTH_SETTING_STRING));
		Settings.height = Integer.parseInt(configuration.get(Strings.HEIGHT_SETTING_STRING));
		Settings.framerate = Integer.parseInt(configuration.get(Strings.FRAMERATE_SETTING_STRING));
		
		Settings.enabled_a = configuration.get(Strings.KEYBINDS_A_ENABLED).equalsIgnoreCase("true");
		Settings.enabled_b = configuration.get(Strings.KEYBINDS_B_ENABLED).equalsIgnoreCase("true");
		
		Settings.attack_a = configuration.get(Strings.KEYBIND_A_ATTACK);
		Settings.move_forward_a = configuration.get(Strings.KEYBIND_A_FORWARD);
		Settings.move_back_a = configuration.get(Strings.KEYBIND_A_BACK);
		Settings.move_left_a = configuration.get(Strings.KEYBIND_A_LEFT);
		Settings.move_right_a = configuration.get(Strings.KEYBIND_A_RIGHT);
		Settings.use_a = configuration.get(Strings.KEYBIND_A_USE);
		
		Settings.attack_b = configuration.get(Strings.KEYBIND_B_ATTACK);
		Settings.move_forward_b = configuration.get(Strings.KEYBIND_B_FORWARD);
		Settings.move_back_b = configuration.get(Strings.KEYBIND_B_BACK);
		Settings.move_left_b = configuration.get(Strings.KEYBIND_B_LEFT);
		Settings.move_right_b = configuration.get(Strings.KEYBIND_B_RIGHT);
		Settings.use_b = configuration.get(Strings.KEYBIND_B_USE);
	}
	
	private void load() throws FileNotFoundException {
		File file = new File("test.txt");
		
		//Check if the file already exists, if not throw exception
		if (!file.isFile()) {
			throw new FileNotFoundException();
		}
		Scanner scan = new Scanner(file);
		
		try {
			
			String cfg = "";
			
			while (scan.hasNext()) {
				//Sort all lines of configuration to one string. Separate options with ";;"
				cfg = cfg.concat(scan.nextLine() + ";;");
			}
			
			Logger.log("Successfully loaded configuration from file.");
			
			Set<String> set = configuration.keySet();
			HashMap<String, String> newConfig = new HashMap<String, String>();
			
			for (String s: set) {
				if (cfg.contains(s)) {
					//Skin string to start with our option
					String setting = cfg.substring(cfg.indexOf(s));
					//And end at the next ";;"
					setting = setting.substring(0, setting.indexOf(";;"));
					
					//Update the current setting to the loaded value
					newConfig.put(s, setting.substring(setting.lastIndexOf(": ")+2));
				}
			}
			
			Set<String> newSet = newConfig.keySet();
			//Update the configuration map
			for (String s: newSet) {
				changeSetting(s, newConfig.get(s));
			}
			
		} catch(Exception e) {
			Logger.log("SEVERE", file.getAbsolutePath() + " was corrupted, restoring default settings");
			
			//If something goes wrong, delete the file and create new ones from the defaults
			file.delete();
			createSettings();
			saveSettings();
		}
		
		//Close the file
		scan.close();
		saveSettings();
	}
	
	private void createSettings() {
		
		//Add all our settings to the configuration map
		
		addSetting(Strings.FULLSCREEN_SETTING_STRING, "" + Settings.FULLSCREEN_DEFAULT);
		addSetting(Strings.WIDTH_SETTING_STRING, "" + Settings.WIDTH_DEFAULT);
		addSetting(Strings.HEIGHT_SETTING_STRING, "" + Settings.HEIGHT_DEFAULT);
		addSetting(Strings.FRAMERATE_SETTING_STRING, "" + Settings.FRAMERATE_DEFAULT);
		
		addSetting(Strings.KEYBINDS_A_ENABLED, "" + Settings.ENABLED_A_DEFAULT);
		addSetting(Strings.KEYBIND_A_FORWARD, "" + Settings.MOVE_FORWARD_DEFAULT_A);
		addSetting(Strings.KEYBIND_A_BACK, String.valueOf(Settings.MOVE_BACK_DEFAULT_A));
		addSetting(Strings.KEYBIND_A_RIGHT, String.valueOf(Settings.MOVE_RIGHT_DEFAULT_A));
		addSetting(Strings.KEYBIND_A_LEFT, String.valueOf(Settings.MOVE_LEFT_DEFAULT_A));
		addSetting(Strings.KEYBIND_A_ATTACK, String.valueOf(Settings.ATTACK_DEFAULT_A));
		addSetting(Strings.KEYBIND_A_USE, String.valueOf(Settings.USE_DEFAULT_A));
		
		addSetting(Strings.KEYBINDS_B_ENABLED, "");
		addSetting(Strings.KEYBIND_B_FORWARD, "");
		addSetting(Strings.KEYBIND_B_BACK, "");
		addSetting(Strings.KEYBIND_B_RIGHT, "");
		addSetting(Strings.KEYBIND_B_LEFT, "");
		addSetting(Strings.KEYBIND_B_ATTACK, "");
		addSetting(Strings.KEYBIND_B_USE, "");
	}
	
	private static void saveSettings() {
		File settings = new File("test.txt");
		
		Logger.log("CONFIG", "Using " + settings.getAbsolutePath() + " as settings");
		
		if (!settings.exists()) {
			try {
				settings.createNewFile();
			} catch (Exception e) {
				Logger.log("SEVERE", "ERROR CREATING SAVEFILE!");
				
			}
		}
		
		try {
			//Open the file in a buffered writer
			FileWriter filestream = new FileWriter(settings);
			BufferedWriter out = new BufferedWriter(filestream);
			
			Set<String> set = configuration.keySet();
			
			for (String s: set) {
				Logger.log(s + " saved to value " + configuration.get(s));
				out.write(s + ": " + configuration.get(s) + "\n");
			}
			
			//Close the file
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addSetting(String key, String defaultVal) {
		configuration.put(key, defaultVal);
	}
	
	public static void changeSetting(String key, String newValue) {
		
		//Check if the key exists, if so; remove it
		if (configuration.containsKey(key) && !configuration.get(key).equals(newValue)) {
			Logger.log("Removed: " + key);
			configuration.remove(key);
		}
		//Put in the new value
		configuration.put(key, newValue);
		
		//And save the settings
		saveSettings();
	}
	
	public String getSetting(String key) {
		return configuration.get(key);
	}

}
