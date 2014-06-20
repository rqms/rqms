package com.rqms.comp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.rqms.core.RQMSStringUtils;

public class RQMSTabUtil {

	// This map is used to hold the tab id of closed tabs
	private static Map<String, String> map = new HashMap<String, String>();

	/**
	 * Method to open new tab at desktop level without parameters
	 * 
	 * @param title
	 *            - Title of the tab
	 * @param path
	 *            - Path of ZUL page to open in Tab
	 * 
	 */
	public static void openNewTab(String title, String path, Tabbox mainTab) {
		Tab tab = new Tab(title);
		tab.setClosable(true);
		tab.setParent(mainTab.getTabs());

		tab.addEventListener("onSwitchTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSwitchEvent(event);
			}
		});

		tab.addEventListener("onSortTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSortEvent(event);
			}
		});

		tab.setWidgetOverride("onClose", "function(notify, init) {zAu.send(new zk.Event(this, 'onSwitchTab'));event.stopPropagation();}");
		tab.setWidgetOverride("_sel",
				"function(notify, init) {if (this.desktop && !init && notify) zAu.send(new zk.Event(this, 'onSortTab')); else this.$_sel(notify, init);}");

		Tabpanel tabpanel = new Tabpanel();
		Include include = new Include(path);
		include.setParent(tabpanel);
		tabpanel.setParent(mainTab.getTabpanels());
		mainTab.setSelectedTab(tab);
	}

	/**
	 * Method to open new tab at desktop level and pass parameters to the included page
	 * 
	 * @param title
	 *            - Title of the tab
	 * @param path
	 *            - Path of the ZUL page to open in the tab
	 * @param parameters
	 *            - Parameters map to be passed to the included page
	 */
	public static void openNewTab(String title, String path, Tabbox mainTab, Map<String, Object> parameters) {
		String randomNumber = RQMSStringUtils.generateRandomString();
		Tab tab = new Tab(title);
		tab.setId(title + randomNumber);
		tab.setClosable(true);
		tab.setParent(mainTab.getTabs());
		tab.addEventListener("onSwitchTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSwitchEvent(event);
			}
		});

		tab.addEventListener("onSortTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSortEvent(event);
			}
		});

		tab.setWidgetOverride("onClose", "function(notify, init) {zAu.send(new zk.Event(this, 'onSwitchTab'));event.stopPropagation();}");
		tab.setWidgetOverride("_sel",
				"function(notify, init) {if (this.desktop && !init && notify) zAu.send(new zk.Event(this, 'onSortTab')); else this.$_sel(notify, init);}");

		Tabpanel tabpanel = new Tabpanel();
		Include include = new Include(path);
		include.setParent(tabpanel);
		parameters.put("TAB_ID", tab.getId());
		if (parameters != null) {
			Set<Map.Entry<String, Object>> entry = parameters.entrySet();
			for (Map.Entry<String, Object> parameter : entry) {
				include.setDynamicProperty(parameter.getKey(), parameter.getValue());
			}
		}
		tabpanel.setParent(mainTab.getTabpanels());
		mainTab.setSelectedTab(tab);
	}

	public static Tab openAnyNewTab(String title, String path, Tabbox mainTab, Map<String, Object> parameters) {
		String randomNumber = RQMSStringUtils.generateRandomString();
		Tab tab = new Tab(title);
		tab.setId(title + randomNumber);
		tab.setClosable(true);
		tab.setParent(mainTab.getTabs());

		tab.addEventListener("onSwitchTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSwitchEvent(event);
			}
		});

		tab.addEventListener("onSortTab", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				handleSortEvent(event);
			}
		});
		tab.setWidgetOverride("onClose", "function(notify, init) {zAu.send(new zk.Event(this, 'onSwitchTab'));event.stopPropagation();}");
		tab.setWidgetOverride("_sel",
				"function(notify, init) {if (this.desktop && !init && notify) zAu.send(new zk.Event(this, 'onSort')); else this.$_sel(notify, init);}");

		Tabpanel tabpanel = new Tabpanel();
		Include include = new Include(path);
		include.setParent(tabpanel);

		if (parameters != null) {
			Set<Map.Entry<String, Object>> entry = parameters.entrySet();
			for (Map.Entry<String, Object> parameter : entry) {
				include.setDynamicProperty(parameter.getKey(), parameter.getValue());
			}
		}
		tabpanel.setParent(mainTab.getTabpanels());
		return tab;
	}

	public static void closeSelectedTab(Tabbox mainTab) {
		Components.removeAllChildren(mainTab.getSelectedTab().getLinkedPanel());
		mainTab.getSelectedTab().close();
	}

	/**
	 * Dirty Check Implementation for top level tabs. The tabs which are opened and closed via HomeViewModel class.
	 * 
	 * @param event
	 */
	private static void handleSwitchEvent(Event event) {}

	private static void handleSortEvent(Event event) { // Actual Select Event
		final Tab tab = (Tab) event.getTarget();
		if (map.containsKey(tab.getId())) {
			map.remove(tab.getId());
			return;
		}
		tab.setSelected(true);
		Events.sendEvent(new Event(Events.ON_SELECT, tab, null));
	}
}
