package com.balonbal.menu.options;

import com.balonbal.core.Logger;

public class Option {
	
	private String ID;
	private String displayName;
	private String[] values;
	private int value;
	private int xpos, ypos;
	
	public Option(String ID, String displayName, String[] values, String defaultVal, int xpos, int ypos) {
		this.ID = ID;
		this.displayName = displayName;
		this.values = values;
		this.xpos = xpos;
		this.ypos = ypos;
		for (int i = 0; i < values.length; i++) {
			if (values[i].equals(defaultVal)) {
				value = i;
				break;
			}
		}
		value = 0;
		
	}
	
	public String getID() {
		return ID;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getValue() {
		return getValue(value);
	}
	
	public String getValue(int val) {
		if (val >= 0 && val < values.length) {
			return values[val];
		}
		return null;
	}
	
	public String[] getValues() {
		return values;
	}
	
	public int getX() {
		return xpos;
	}
	
	public int getY() {
		return ypos;
	}
	
	public void changeValue(int newVal) {
		value = newVal;
	}
	
	public void setX(int newx) {
		xpos = newx;
	}
	
	public void setY(int newy) {
		ypos = newy;
	}
	
	public void move(int x, int y) {
		setX(x);
		setY(y);
	}

}
