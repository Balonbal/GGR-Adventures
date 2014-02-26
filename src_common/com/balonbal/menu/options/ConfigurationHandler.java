package com.balonbal.menu.options;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.balonbal.lib.Settings;
import com.balonbal.lib.Strings;

public class ConfigurationHandler {
	
	public Hashtable<String, String> configuration;
	
	public void initialize() {
		configuration = new Hashtable<String, String>();
		
		try {
			load();
		} catch(FileNotFoundException e) {
			System.out.println("[SEVERE] No settings found. Creating from default.");
			createSettings();
		}
		
		//Set all the settings TODO Improve this
		Settings.fullscreen = configuration.get(Strings.FULLSCREEN_SETTING_STRING).equalsIgnoreCase("true");
		Settings.width = Integer.parseInt(configuration.get(Strings.WIDTH_SETTING_STRING));
		Settings.height = Integer.parseInt(configuration.get(Strings.HEIGHT_SETTING_STRING));
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
			configuration.put(Strings.FULLSCREEN_SETTING_STRING, scan.nextLine());
			configuration.put(Strings.WIDTH_SETTING_STRING, scan.nextLine());
			configuration.put(Strings.HEIGHT_SETTING_STRING, scan.nextLine());
			configuration.put(Strings.KEYBINDS_A_ENABLED, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_FORWARD, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_BACK, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_RIGHT, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_LEFT, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_ATTACK, scan.nextLine());
			configuration.put(Strings.KEYBIND_A_USE, scan.nextLine());
		
			configuration.put(Strings.KEYBINDS_B_ENABLED, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_FORWARD, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_BACK, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_RIGHT, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_LEFT, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_ATTACK, scan.nextLine());
			configuration.put(Strings.KEYBIND_B_USE, scan.nextLine());
		} catch(NoSuchElementException e) {
			System.out.println(file.getAbsolutePath() + " was corrupted, restoring default settings");
			file.delete();
			createSettings();
		}
		
		//Close the file
		scan.close();
	}
	
	private void createSettings() {

		System.out.println(Strings.FULLSCREEN_SETTING_STRING == null);
		System.out.println(Settings.FULLSCREEN_DEFAULT);
		
		configuration.put(Strings.FULLSCREEN_SETTING_STRING, "" + Settings.FULLSCREEN_DEFAULT);
		configuration.put(Strings.WIDTH_SETTING_STRING, "" + Settings.WIDTH_DEFAULT);
		configuration.put(Strings.HEIGHT_SETTING_STRING, "" + Settings.HEIGHT_DEFAULT);
		configuration.put(Strings.KEYBINDS_A_ENABLED, "" + Settings.ENABLED_A_DEFAULT);
		configuration.put(Strings.KEYBIND_A_FORWARD, "" + Settings.MOVE_FORWARD_DEFAULT_A);
		configuration.put(Strings.KEYBIND_A_BACK, Settings.MOVE_BACK_DEFAULT_A);
		configuration.put(Strings.KEYBIND_A_RIGHT, Settings.MOVE_RIGHT_DEFAULT_A);
		configuration.put(Strings.KEYBIND_A_LEFT, Settings.MOVE_LEFT_DEFAULT_A);
		configuration.put(Strings.KEYBIND_A_ATTACK, Settings.ATTACK_DEFAULT_A);
		configuration.put(Strings.KEYBIND_A_USE, Settings.USE_DEFAULT_A);
		
		configuration.put(Strings.KEYBINDS_B_ENABLED, "");
		configuration.put(Strings.KEYBIND_B_FORWARD, "");
		configuration.put(Strings.KEYBIND_B_BACK, "");
		configuration.put(Strings.KEYBIND_B_RIGHT, "");
		configuration.put(Strings.KEYBIND_B_LEFT, "");
		configuration.put(Strings.KEYBIND_B_ATTACK, "");
		configuration.put(Strings.KEYBIND_B_USE, "");
		
		saveSettings();
	}
	
	private void saveSettings() {
		File settings = new File("test.txt");
		
		System.out.println("Using " + settings.getAbsolutePath() + " as settings");
		
		if (!settings.exists()) {
			try {
				settings.createNewFile();
			} catch (Exception e) {
				System.out.println("ERROR CREATING SAVEFILE!");
				
			}
		}
		
		try {
			//Open the file in a buffered writer
			FileWriter filestream = new FileWriter(settings);
			BufferedWriter out = new BufferedWriter(filestream);
			
			out.write(configuration.get(Strings.FULLSCREEN_SETTING_STRING) + "\n");
			out.write(configuration.get(Strings.WIDTH_SETTING_STRING) + "\n");
			out.write(configuration.get(Strings.HEIGHT_SETTING_STRING) + "\n");
			out.write(configuration.get(Strings.KEYBINDS_A_ENABLED) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_FORWARD) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_BACK) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_RIGHT) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_LEFT) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_ATTACK) + "\n");
			out.write(configuration.get(Strings.KEYBIND_A_USE) + "\n");
			
			out.write(configuration.get(Strings.KEYBINDS_B_ENABLED) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_FORWARD) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_BACK) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_RIGHT) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_LEFT) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_ATTACK) + "\n");
			out.write(configuration.get(Strings.KEYBIND_B_USE) + "\n");
			
			//Close the file
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
