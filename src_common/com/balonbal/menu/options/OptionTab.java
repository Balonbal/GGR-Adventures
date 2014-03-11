package com.balonbal.menu.options;

import java.util.ArrayList;

public class OptionTab {
	
	private String header;
	private ArrayList<Option> options;
	
	public OptionTab(String header) {
		this.header = header;
		options = new ArrayList<Option>();
	}
	
	public void addOption(Option option) {
		options.add(option);
	}
	
	public Option getOption(String ID) {
		for (Option o: options) {
			if (o.getID().equals(ID)) {
				return o;
			}
		}
		
		return null;
	}
	
	public ArrayList<Option> getOptions() {
		return options;
	}

}
