package com.balonbal.lib;

public class Settings {
	
	//Display Options
	public static boolean fullscreen;
	public static int width;
	public static int height;
	public static int framerate;
	
	//Default display options
	public static final boolean FULLSCREEN_DEFAULT = false;
	public static final int WIDTH_DEFAULT = 800;
	public static final int[] AVAILABLE_WIDTHS = {
		800,
		1920
	};
	public static final int HEIGHT_DEFAULT = 600;
	public static final int[] AVAILABLE_HEIGTHS = {
		600,
		1080
	};
	//NOTE: Those are not actually configs, they are only for display purposes
	public static final String RESOLUTION_DEFAULT = WIDTH_DEFAULT + "x" + HEIGHT_DEFAULT;
	public static String[] AVAILABLE_RESOLUTIONS = new String[AVAILABLE_WIDTHS.length];
	
	public static void initResolutions() {
		for (int i = 0; i < AVAILABLE_WIDTHS.length; i++) {
			AVAILABLE_RESOLUTIONS[i] = AVAILABLE_WIDTHS[i] + "x" + AVAILABLE_HEIGTHS[i];
		}
	}
	
	public static final int FRAMERATE_DEFAULT = -1;
	public static final int[] AVAILABLE_FRAMERATES = {
		-1,
		30,
		60,
		120
	};
	
	//Key bindings A 
	
	public static boolean enabled_a;
	
	public static final boolean ENABLED_A_DEFAULT = true;
	public static final boolean ENABLED_B_DEFAUTL = false;
	
	/*Movement*/
	public static String move_forward_a;
	public static String move_back_a;
	public static String move_right_a;
	public static String move_left_a;
	
	//Defaults
	public static final String MOVE_FORWARD_DEFAULT_A = "W";
	public static final String MOVE_BACK_DEFAULT_A = "S";
	public static final String MOVE_RIGHT_DEFAULT_A = "D";
	public static final String MOVE_LEFT_DEFAULT_A = "A";
	
	/*Interactions*/
	public static String attack_a;
	public static String use_a;
	
	//Defaults
	
	public static final String ATTACK_DEFAULT_A = "MOUSE_LEFT";
	public static final String USE_DEFAULT_A = "E";
	
	//Key bindings B
	
	public static boolean enabled_b;
	
	/*Movement*/
	public static String move_forward_b;
	public static String move_back_b;
	public static String move_right_b;
	public static String move_left_b;
	
	/*Interactions*/
	public static String attack_b;
	public static String use_b;

	
}
