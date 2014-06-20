package com.rqms.comp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.theme.Themes;

import com.rqms.common.MenuNode;
import com.rqms.domain.UserProfile;
import com.rqms.web.RQMSActions;

public class RQMSMenu {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RQMSMenu.class);

	public static List<MenuNode> generateMenu(UserProfile userProfile) {
		LOGGER.info("Generating Menu Items");
		List<MenuNode> menuNodes = new ArrayList<MenuNode>();
		generateFileMenuItems(menuNodes, userProfile);
		generateActionMenuItems(menuNodes, userProfile);
		generateThemeMenuItems(menuNodes, userProfile);
		generateHelpMenuItems(menuNodes, userProfile);
		return menuNodes;
	}

	private static void generateFileMenuItems(List<MenuNode> menuNodes,
			UserProfile userProfile) {
		if (userProfile.getRoleid() != null
				&& userProfile.getRoleid().equals(1)) {
			MenuNode fileNode = new MenuNode(MenuConstants.JS_MENU_FILE);
			menuNodes.add(fileNode);
			List<MenuNode> fileMenuNodes = new ArrayList<MenuNode>();
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_CHANGE_PASSWORD));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_LOG_OUT));
			fileNode.setChildren(fileMenuNodes);
		} else {
			MenuNode fileNode = new MenuNode(MenuConstants.JS_MENU_FILE);
			menuNodes.add(fileNode);
			List<MenuNode> fileMenuNodes = new ArrayList<MenuNode>();
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_CHANGE_PASSWORD));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_LOG_OUT));
			fileNode.setChildren(fileMenuNodes);
		}
	}
	
	private static void generateActionMenuItems(List<MenuNode> menuNodes,
			UserProfile userProfile) {
		if (userProfile.getRoleid() != null
				&& userProfile.getRoleid().equals(1)) {
			MenuNode fileNode = new MenuNode(MenuConstants.JS_MENU_ACTIONS);
			menuNodes.add(fileNode);
			List<MenuNode> fileMenuNodes = new ArrayList<MenuNode>();
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ADD_NEW_USER));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ADD_NEW_JOB));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ROLE));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_SKILL));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_FEEDBACK));
			fileNode.setChildren(fileMenuNodes);
		} else {
			MenuNode fileNode = new MenuNode(MenuConstants.JS_MENU_ACTIONS);
			menuNodes.add(fileNode);
			List<MenuNode> fileMenuNodes = new ArrayList<MenuNode>();
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ADD_NEW_USER));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ADD_NEW_JOB));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_ROLE));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_SKILL));
			fileMenuNodes.add(new MenuNode(MenuConstants.SUBMENU_FEEDBACK));
			fileNode.setChildren(fileMenuNodes);
		}
	}
	private static void generateThemeMenuItems(List<MenuNode> menuNodes,
			UserProfile userProfile) {
		MenuNode themesNode = new MenuNode(MenuConstants.JS_MENU_THEMES);
		menuNodes.add(themesNode);
		List<MenuNode> themeSubNodes = new ArrayList<MenuNode>();
		for (String theme : Themes.getThemes()) {
			themeSubNodes.add(new MenuNode(toinitCapCase(theme), Themes
					.getCurrentTheme().equalsIgnoreCase(theme)));
		}
		themesNode.setChildren(themeSubNodes);
	}

	private static String toinitCapCase(String name) {
		if (name == null || name.trim().length() == 0) {
			return name;
		}
		String ModifiedName = null;
		if (name.indexOf("theme") > 0)
			ModifiedName = name.substring(1, name.indexOf("theme"));
		else
			ModifiedName = name.substring(1);
		name = name.substring(0, 1).toUpperCase();
		ModifiedName = name + ModifiedName;
		return ModifiedName;
	}

	private static void generateHelpMenuItems(List<MenuNode> menuNodes,
			UserProfile userProfile) {
		MenuNode helpNode = new MenuNode(MenuConstants.JS_MENU_HELP);
		menuNodes.add(helpNode);
		List<MenuNode> helpSubNodes = new ArrayList<MenuNode>();
		helpSubNodes.add(new MenuNode(
				MenuConstants.SUBMENU_HELP_RQMSSUPPORTWEBSITE));
		helpSubNodes.add(new MenuNode(MenuConstants.SUBMENU_HELP_ABOUT));
		helpNode.setChildren(helpSubNodes);
	}

	/**
	 * Method to handle all menu actions.
	 * 
	 * @param action
	 *            - label of the menu item invoked
	 * @param datawin
	 *            - CsdcData instance
	 */
	public static void menuClicked(MenuNode actionNode, final Tabbox mainTab) {

		if (actionNode.getAction().equals(MenuConstants.SUBMENU_ADD_NEW_USER)) {
			RQMSActions.doAddUser(mainTab);
		} else if (actionNode.getAction().equals(
				MenuConstants.SUBMENU_ADD_NEW_JOB)) {
			RQMSActions.doAddJob(mainTab);
		}else if (actionNode.getAction().equals(
				MenuConstants.SUBMENU_ROLE)) {
			RQMSActions.doAddRole(mainTab);
		}
		else if (actionNode.getAction().equals(
				MenuConstants.SUBMENU_SKILL)) {
			RQMSActions.doAddSkill(mainTab);
		}
		else if (actionNode.getAction().equals(MenuConstants.SUBMENU_LOG_OUT)) {
			RQMSActions.doLogOut();
		} else if (actionNode.getAction().equalsIgnoreCase("Blue")) {
			Themes.setTheme(Executions.getCurrent(), "bluetheme");
			Executions.sendRedirect(null);
		} else if (actionNode.getAction().equalsIgnoreCase("Brown")) {
			Themes.setTheme(Executions.getCurrent(), "browntheme");
			Executions.sendRedirect(null);
		} else if (actionNode.getAction().equalsIgnoreCase("Green")) {
			Themes.setTheme(Executions.getCurrent(), "greentheme");
			Executions.sendRedirect(null);
		} else if (actionNode.getAction().equalsIgnoreCase("Purple")) {
			Themes.setTheme(Executions.getCurrent(), "purpletheme");
			Executions.sendRedirect(null);
		} else if (actionNode.getAction().equalsIgnoreCase("Red")) {
			Themes.setTheme(Executions.getCurrent(), "redtheme");
			Executions.sendRedirect(null);
		} else {
			Messagebox.show("Not yet implemented.");
		}
	}
}
