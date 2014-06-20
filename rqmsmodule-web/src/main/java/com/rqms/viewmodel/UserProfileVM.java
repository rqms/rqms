package com.rqms.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.theme.Themes;

import com.rqms.common.ListboxViewModel;
import com.rqms.common.SelectOption;
import com.rqms.comp.RQMSTabUtil;
import com.rqms.domain.UserProfile;
import com.rqms.domain.Validrole;
import com.rqms.service.CRUDService;
import com.rqms.utilities.MyLib;

public class UserProfileVM {

	private Component view;
	private boolean makeAsReadOnly;
	protected ListboxViewModel listBoxViewModel;
	private ListModelList<UserProfile> allReordsInDB;
	private List<UserProfile> allReords = null;
	private UserProfile selectedItem;

	public boolean isMakeAsReadOnly() {
		return makeAsReadOnly;
	}

	private static final Map<String, String> tabPath;

	public ListboxViewModel getListBoxViewModel() {
		return listBoxViewModel;
	}

	public void setListBoxViewModel(ListboxViewModel listBoxViewModel) {
		this.listBoxViewModel = listBoxViewModel;
	}

	public UserProfile getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(UserProfile selectedItem) {
		this.selectedItem = selectedItem;
	}

	static {
		tabPath = new HashMap<String, String>();
		tabPath.put("User Detail", "/user_profile_detail.zul");
	}

	@WireVariable
	private CRUDService CRUDService;

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}

	public void getDataRecordList() {
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReords = CRUDService.getAll(UserProfile.class);
		if (allReords.size() > 0)
			selectedItem = allReords.get(0);
	}
	
	@Command("refreshList")
	@NotifyChange({ "*" })
	public void refreshList(@ContextParam(ContextType.VIEW) Component view) {
		doAfterCompose(view, null);
	}

	@Wire("#usertabs")
	Tabbox usertabs;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("map") Map<String, Object> parameters) {
		Selectors.wireComponents(view, this, false);
		this.view = view;
		listBoxViewModel = new ListboxViewModel();
		allReordsInDB = new ListModelList<>();
		getDataRecordList();
		for (UserProfile up : allReords) {
			allReordsInDB.add(up);
		}
		//allReordsInDB.setMultiple(true);
		listBoxViewModel.setModel(allReordsInDB);

		// loadUserTabs();
		String[] themes = Themes.getThemes();
		themes = Arrays.copyOf(themes, themes.length + 1);
		if (usertabs != null && listBoxViewModel.getModel().size() > 0) {
			if (selectedItem != null)
				loadDetailPageTab(selectedItem);
			else {
				selectedItem = new UserProfile();
				selectedItem.setTheme(themes.toString());
				loadDetailPageTab(selectedItem);
			}
		} else {
			
			selectedItem = new UserProfile();
			selectedItem.setTheme(themes.toString());
			loadDetailPageTab(selectedItem);
		}
	}

	private void closeAllTabs() {
		Tab selectedTab = null;
		for (Object obj : new ArrayList(usertabs.getTabs().getChildren())) {
			Tab tab = ((Tab) obj);
			if (!tab.isSelected()) {
				Components.removeAllChildren(tab.getLinkedPanel());
				tab.close();
			} else {
				selectedTab = tab;
			}
		}
		if (selectedTab != null) {
			selectedTab.close();
		}
	}

	private Tab addTab(String id, String tabName, EventListener eventListener) {
		Tab tab = new Tab(tabName);
		tab.setId(id);
		tab.setParent(usertabs.getTabs());
		Tabpanel tabpanel = new Tabpanel();
		tabpanel.setParent(usertabs.getTabpanels());
		if (eventListener != null) {
			tab.addEventListener("onSelect", eventListener);
		}
		return tab;
	}

	private Tab getTab(String id) {
		List children = usertabs.getTabs().getChildren();
		for (Object obj : children) {
			Tab component = (Tab) obj;
			if (component.getId().equals(id)) {
				return component;
			}
		}
		return null;
	}

	public void loadTab(String tabId, UserProfile record) {
		loadTab(tabId, tabPath.get(tabId), record);
	}

	public void loadTab(String tabId, String uri, UserProfile record) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		this.selectedItem = record;
		Map arguments = new HashMap(3);
		arguments.put("selectedItem", record);
		arguments.put("recordMode", "EDIT");
		arguments.put(ContextType.VIEW, view);
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(),
				arguments);
	}

	@Command
	@NotifyChange("usertabs")
	public void loadDetailPageTab() {
		if (!listBoxViewModel.getSelectedListItem().isEmpty()) {
			for (Iterator itr = listBoxViewModel.getSelectedListItem()
					.iterator(); itr.hasNext();) {
				UserProfile data = (UserProfile) itr.next();
				loadDetailPageTab(data);
			}
		}
	}

	public void loadDetailPageTab(final UserProfile record) {
		String selectedTabId = usertabs.getSelectedTab().getId();
		closeAllTabs();

		for (Entry<String, String> entry : tabPath.entrySet()) {
			@SuppressWarnings("rawtypes")
			Tab tab = addTab(entry.getKey(), entry.getKey(),
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							loadTab(event.getTarget().getId(), record);
						}
					});
			if (StringUtils.equals(entry.getKey(), selectedTabId)) {
				usertabs.setSelectedTab(tab);
			}
		}
		if (usertabs.getSelectedTab() == null) {
			usertabs.setSelectedIndex(0);
		}
		loadTab(usertabs.getSelectedTab().getId(), record);
	}

	@NotifyChange("*")
	@Command
	public void addUserDetail() {
		UserProfile selectedrecord = new UserProfile();
		String[] themes = Themes.getThemes();
		themes = Arrays.copyOf(themes, themes.length + 1);
		selectedrecord.setTheme(themes.toString());
		if(listBoxViewModel.getModel() == null) {
			listBoxViewModel.setModel(new ListModelList<>());
		}
		listBoxViewModel.getModel().add(selectedrecord);
		listBoxViewModel.setSelectItem(selectedrecord);
		loadDetailPageTab(selectedrecord);
	}

	
	/*
	 * This method is use to set ComboBox record List which is on the screen.
	 */
	public List<SelectOption<Integer>> getComboBoxList() {
		List<SelectOption<Integer>> validCode = null;
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		List<Validrole> allReord= CRUDService.getAll(Validrole.class);
		validCode = buildSelectOptions(allReord);
		return validCode;
	}
	
	public List<SelectOption<Integer>> buildSelectOptions(List<Validrole> comboRecord) {
		List<SelectOption<Integer>> validCode = new ArrayList<SelectOption<Integer>>(comboRecord.size());
		SelectOption<Integer> option = null;//new SelectOption<Integer>(null, " ");
		validCode.add(new SelectOption<Integer>(null, " "));
		for (Validrole rcd : comboRecord) {
			option = new SelectOption<Integer>(rcd.getRoleid(), rcd.getRoledescription());
				validCode.add(option);
			}
		return validCode;
	}
	
	public SelectOption<Integer> getSelectOption(@BindingParam("obj") Integer obj) {
		if(obj!=null)
			return new SelectOption<Integer>(obj);
		else
			return null;

	}

	public void setSelectOption(SelectOption<Integer> code) {
	}

	
	@Command
	public void valueChangedListnerForSubTab(@BindingParam("BeanData") UserProfile changedObject) {
		selectedItem=changedObject;
		loadDetailPageTab(changedObject);
	}
	
	@Command
	@NotifyChange("*")
	public void saveThis() {
		if (MyLib.IsValidBean(this.selectedItem) == false) {
			return;
		}
		CRUDService.Save(this.selectedItem);
		MyLib.showSuccessmessage();
		BindUtils.postNotifyChange(null, null, UserProfileVM.this,
				"selectedItem");
	}

	@Command
	@NotifyChange("*")
	public void addCandidates() {
		Tabbox mainTab = (Tabbox) view.getParent().getParent()
				.getParent().getParent();
		RQMSTabUtil.openNewTab("Candidate List", "add_candidate_list.zul",
				mainTab);
	}
	
	@Command
	@NotifyChange({ "*" })
	public void valueChangedListnerForCombo(@BindingParam("Code") Combobox combobox, @BindingParam("BeanData") UserProfile record) {
		Object selectedObj = combobox.getSelectedItem().getValue();
		record.setRoleid((Integer)selectedObj);
		
	}

}
