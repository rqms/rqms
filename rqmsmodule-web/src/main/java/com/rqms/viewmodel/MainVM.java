package com.rqms.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Tabbox;

import com.rqms.common.MenuNode;
import com.rqms.comp.RQMSMenu;
import com.rqms.domain.UserProfile;
 

public class MainVM {

	@Wire("#mainlayout")
	private Borderlayout borderLayout;
	private List<MenuNode> menuNodes;
	@Wire("#mainTab")
	Tabbox mainTab;
	/**
	 * This method will be called after host component composition has been done
	 * (AfterCompose)
	 * 
	 * @param view
	 *            Root Component of the ZUL File.
	 */
	
	@Command("menuClicked")
	public void menuClicked(@BindingParam("node") final MenuNode menuNode) {
		boolean isContinue = true;
		if (isContinue) {
			RQMSMenu.menuClicked(menuNode, mainTab);
		}
	}
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		UserProfile userProfile = FHSessionUtil.getCurrentUser();
		menuNodes = RQMSMenu.generateMenu(userProfile);
		Selectors.wireComponents(view, this, false);
	}
	public List<MenuNode> getMenuNodes() {
		return menuNodes;
	}
	public void setMenuNodes(List<MenuNode> menuNodes) {
		this.menuNodes = menuNodes;
	}
	 
}
