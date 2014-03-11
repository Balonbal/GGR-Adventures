package com.balonbal.menu.options;

public class Option {
	
	private String ID;
	private String displayName;
	private String value;
	private int xpos, ypos;
	
	public Option(String ID, String displayName, String value, int xpos, int ypos) {
		this.ID = ID;
		this.displayName = displayName;
		this.value = value;
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getX() {
		return xpos;
	}
	
	public int getY() {
		return ypos;
	}
	
	public void changeValue(String newVal) {
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
