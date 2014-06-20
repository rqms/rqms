package com.rqms.comp;

import java.util.HashMap;
import java.util.Map;

public class MenuConstants {

	// File
	public static String JS_MENU_FILE = "File";
	public static String SUBMENU_CHANGE_PASSWORD = "Change Password";
	public static String SUBMENU_LOG_OUT = "Log Out";
	
	public static String JS_MENU_ACTIONS = "Actions";
	public static String SUBMENU_ADD_NEW_USER = "Add New User";
	public static String SUBMENU_ADD_NEW_JOB = "Add New Job";
	public static String SUBMENU_ROLE = "Add Role";
	public static String SUBMENU_SKILL = "Add Skill";
	public static String SUBMENU_FEEDBACK = "Feedback";
	
	// Themes
	public static String JS_MENU_THEMES = "Themes";
	public static String SUBMENU_THEMES_BLUE = "SUBMENU_THEMES_BLUE";
	public static String SUBMENU_THEMES_BROWN = "SUBMENU_THEMES_BROWN";
	public static String SUBMENU_THEMES_GREEN = "SUBMENU_THEMES_GREEN";
	public static String SUBMENU_THEMES_PURPLE = "SUBMENU_THEMES_PURPLE";
	public static String SUBMENU_THEMES_RED = "SUBMENU_THEMES_RED";

	// Help
	public static String JS_MENU_HELP = "Help";
	public static String SUBMENU_HELP_RQMSSUPPORTWEBSITE = "Support Site";
	public static String SUBMENU_HELP_ABOUT = "About";

	public static Map<String, String> menuIcons = new HashMap<String, String>();
	static {
		menuIcons.put(SUBMENU_ADD_NEW_USER, "exit.png");
	}

}
