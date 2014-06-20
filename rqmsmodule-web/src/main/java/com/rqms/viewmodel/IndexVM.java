package com.rqms.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.web.theme.StandardTheme.ThemeOrigin;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.theme.Themes;

public class IndexVM {

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {

		Themes.register("bluetheme", ThemeOrigin.FOLDER);
		Themes.register("greentheme", ThemeOrigin.FOLDER);
		Themes.register("browntheme", ThemeOrigin.FOLDER);
		Themes.register("purpletheme", ThemeOrigin.FOLDER);
		Themes.register("redtheme", ThemeOrigin.FOLDER);
		boolean themeSet = false;
		String theme = FHSessionUtil.getCurrentUser().getTheme();
		if (theme != null) {
			for (String themeTemp : Themes.getThemes()) {
				if (theme.equals(themeTemp)) {
					Themes.setTheme(Executions.getCurrent(), theme);
					themeSet = true;
					break;
				}
			}
			if (!themeSet) {
				Themes.setTheme(Executions.getCurrent(), "bluetheme");
			}
		} else {
			Themes.setTheme(Executions.getCurrent(), "bluetheme");
		}

		Executions.sendRedirect("main.zul");
	}

}
