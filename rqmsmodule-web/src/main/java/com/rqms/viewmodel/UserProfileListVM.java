package com.rqms.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.rqms.common.ListboxViewModel;
import com.rqms.domain.UserProfile;
import com.rqms.service.CRUDService;

public class UserProfileListVM {
	
	private Component view;
	protected Component parentView;
	@WireVariable
	private CRUDService CRUDService;

	private UserProfile selectedItem;
	private List<UserProfile> allReords = null;
	//private List<UserProfile> userList = null;
	
	private ListModelList<UserProfile> allReordsInDB;
	private ListModelList<UserProfile> userList;
	protected ListboxViewModel listBoxViewModel;
	
	@Wire
	Listbox adminListboxViewModelId;
	private static final Map<String, String> tabPath;
	
	static {
		tabPath = new HashMap<String, String>();
		tabPath.put("User Detail", "/user_profile_detail.zul");
		
	}
	
	@Wire("#usertabs")
	Tabbox usertabs;
	@Wire
	public Listbox listBoxViewModelId;
	private Integer selectedRowNo = 0;
	private Map<String, Object> parameters;
	private String saveType;
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("map") Map<String, Object> parameters) {
		Selectors.wireComponents(view, this, false);
		this.view = view;
		this.parentView = view;
		this.parameters=parameters;
		listBoxViewModel = new ListboxViewModel();
		allReordsInDB = new ListModelList<>();
		userList = new ListModelList<>();
		getDataRecordList();
		for(UserProfile up:allReords){
			allReordsInDB.add(up);
		}
		allReordsInDB.setMultiple(true);
		listBoxViewModel.setModel(allReordsInDB);
		
		
		//loadUserTabs();
		if (listBoxViewModel.getModel().size() > 0) {
			saveType="EDIT";
			loadDetailPageTab((UserProfile)listBoxViewModel.getModel().get(selectedRowNo));
		}

	}
	
	@Command
	public void valueChangedListnerForSubTab(@BindingParam("BeanData") UserProfile changedObject) {
		selectedRowNo = allReordsInDB.indexOf(changedObject);
		//parameters.put("SelectedRowNo", selectedRowNo);
		selectedItem=changedObject;
		loadDetailPageTab(changedObject);
	}
	
	
	@Command
	@NotifyChange({ "allReordsInDB", "listBoxViewModel" })
	public void add() {
		UserProfile up=new UserProfile();
		allReordsInDB.add(up);
		saveType="NEW";
		setFocusOnAddedItem();
	}
	
	protected void setFocusOnAddedItem() {
		if (listBoxViewModel != null && listBoxViewModel.getModel().size() > 0) {
			listBoxViewModel.setSelectedListItem(new HashSet<UserProfile>());
			listBoxViewModel.getSelectedListItem().add(listBoxViewModel.getModel().get(listBoxViewModel.getModel().size() - 1));
			Listitem item = listBoxViewModelId.getItemAtIndex(listBoxViewModel.getModel().size()-1);
			Clients.scrollIntoView(item);
			//if (!pageNode.getDetailPages().isEmpty()) {
				loadDetailPageTab((UserProfile) listBoxViewModel.getModel().get(listBoxViewModel.getModel().size() - 1));
			//}
		}
	}
	
	@Command("refreshList")
	@NotifyChange({ "*" })
	public void refreshList(@ContextParam(ContextType.VIEW) Component view) {
		int rowNo = 0;
		/*if (parameters
				.containsKey("SelectedRowNo"))
			rowNo = (Integer) parameters
			.get("SelectedRowNo");*/
		doAfterCompose(view, parameters);
		if (rowNo > 0)
			autoFocusItem(rowNo);

	}
	
	/*
	 * This method is use to close tabs.
	 */
	@Command
	public void onClose() {
		view.detach();
	}
	
	protected void autoFocusFirstItem() {
		if (listBoxViewModel != null && listBoxViewModel.getModel().size() > 0  && listBoxViewModel.getSelectedListItem() == null) {
			listBoxViewModel.setSelectedListItem(new HashSet<UserProfile>());
			listBoxViewModel.getSelectedListItem().add(listBoxViewModel.getModel().get(0));
		}
	}

	protected void autoFocusItem(int rowNo) {
		if (listBoxViewModel != null && listBoxViewModel.getModel().size() > 0) {
			listBoxViewModel.setSelectedListItem(new HashSet<UserProfile>());
			listBoxViewModel.getSelectedListItem().add(listBoxViewModel.getModel().get(rowNo));
		}
	}
	/*
	 * This method is used to load all the sub-tab which is defined in new_admin_config.xml file.
	 * 
	 * We use the detailPage tag to define sub-tab.
	 * 
	 * We can get the list of detailPage from PageNode class.
	 */
	protected void loadDetailPageTab(final UserProfile record) {
		//tabPath = getModuleTabPath();
		UserProfile tempRecord=null;
		//if(listBoxViewModel.getSelectedListItem().size()>0){
			tempRecord=record;
		/*}else{
			tempRecord=(UserProfile) listBoxViewModel.getModel().get(0);
		}*/
		final UserProfile selectedRecord=tempRecord;
		String selectedTabId = usertabs.getSelectedTab().getId();
		closeAllTabs();

		for (Entry<String, String> entry : tabPath.entrySet()) {
			@SuppressWarnings("rawtypes")
			Tab tab = addTab(entry.getKey(), entry.getKey(), new EventListener() {

				public void onEvent(Event event) throws Exception {
					loadTab(event.getTarget().getId(), selectedRecord);
				}
			});
			if (StringUtils.equals(entry.getKey(), selectedTabId)) {
				usertabs.setSelectedTab(tab);
			}
		}
		if (usertabs.getSelectedTab() == null) {
			usertabs.setSelectedIndex(0);
		}
		loadTab(usertabs.getSelectedTab().getId(), selectedRecord);
	}
	
	public void loadTab(String tabId, UserProfile record) {
		loadTab(tabId, tabPath.get(tabId), record);
	}

	public void loadTab(String tabId, String uri, UserProfile record) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		Map arguments = new HashMap(3);
			arguments.put("selectedRecord", record);
			arguments.put("recordMode", saveType);
			arguments.put(ContextType.VIEW, view);
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(), arguments);
	}
	
	public void getDataRecordList(){
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReords = CRUDService.getAll(UserProfile.class);
		if(allReords.size()>0)
		selectedItem=allReords.get(0);
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
	
	private void closeAllTabs() {
		// close all the existing tab before adding new tabs
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
	
	/*public List<UserProfile> getDataSet() {
		return allReordsInDB;
	}*/

	public UserProfile getSelectedRecord() {
		 if(listBoxViewModel.getSelectedListItem()==null)
		 selectedItem = (UserProfile) listBoxViewModel.getModel().get(0) ;
		 else
		selectedItem = (UserProfile) listBoxViewModel.getSelectItem() ;	 
		 
		 return selectedItem;
	}

	@NotifyChange("*")
	@Command
	public void setSelectedRecord(@BindingParam("BeanData") UserProfile selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ListboxViewModel getListBoxViewModel() {
		return listBoxViewModel;
	}

	public void setListBoxViewModel(ListboxViewModel listBoxViewModel) {
		this.listBoxViewModel = listBoxViewModel;
	}
}
