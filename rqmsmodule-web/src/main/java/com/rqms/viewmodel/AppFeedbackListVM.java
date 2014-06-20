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
import com.rqms.domain.ApplicantfeedbackId;
import com.rqms.service.CRUDService;

public class AppFeedbackListVM  {

	
	private Component view;
	private Component parentView;
	@WireVariable
	private CRUDService CRUDService;

	private ApplicantfeedbackId selectedItem;
	private List<ApplicantfeedbackId> allReords = null;
	//private List<ApplicantfeedbackId> userList = null;
	
	private ListModelList<ApplicantfeedbackId> allReordsInDB;
	private ListModelList<ApplicantfeedbackId> userList;
	protected ListboxViewModel listBoxViewModel;
	
	@Wire
	Listbox adminListboxViewModelId;
	private static final Map<String, String> tabPath;
	
	static {
		tabPath = new HashMap<String, String>();
		tabPath.put("User Detail", "/app_feedback_detail.zul");
		
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
		for(ApplicantfeedbackId up:allReords){
			allReordsInDB.add(up);
		}
		allReordsInDB.setMultiple(true);
		listBoxViewModel.setModel(allReordsInDB);
		
		
		//loadUserTabs();
		if (usertabs != null && listBoxViewModel.getModel().size() > 0) {
			saveType="EDIT";
			loadDetailPageTab((ApplicantfeedbackId)listBoxViewModel.getModel().get(selectedRowNo));
		}

	}
	
	@Command
	public void valueChangedListnerForSubTab(@BindingParam("BeanData") ApplicantfeedbackId changedObject) {
		selectedRowNo = allReordsInDB.indexOf(changedObject);
		parameters.put("SelectedRowNo", selectedRowNo);
		loadDetailPageTab(changedObject);
	}
	
	/*@NotifyChange("*")
	@Command
	public void add() {
		 ApplicantfeedbackId selectedrecord = new ApplicantfeedbackId();
		 listBoxViewModel.getModel().add(selectedrecord);
		 listBoxViewModel.setSelectItem(selectedrecord);
	}*/
	
	@Command
	@NotifyChange({ "allReordsInDB", "listBoxViewModel" })
	public void add() {
		ApplicantfeedbackId up=new ApplicantfeedbackId();
		allReordsInDB.add(up);
		saveType="NEW";
		//adminNewListModelList.add(new_record);
		//isNewFreeForm = true;
		setFocusOnAddedItem();
	}
	
	protected void setFocusOnAddedItem() {
		if (listBoxViewModel != null && listBoxViewModel.getModel().size() > 0) {
			listBoxViewModel.setSelectedListItem(new HashSet<ApplicantfeedbackId>());
			listBoxViewModel.getSelectedListItem().add(listBoxViewModel.getModel().get(listBoxViewModel.getModel().size() - 1));
			Listitem item = listBoxViewModelId.getItemAtIndex(listBoxViewModel.getModel().size()-1);
			Clients.scrollIntoView(item);
			//if (!pageNode.getDetailPages().isEmpty()) {
				loadDetailPageTab((ApplicantfeedbackId) listBoxViewModel.getModel().get(listBoxViewModel.getModel().size() - 1));
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
			listBoxViewModel.setSelectedListItem(new HashSet<ApplicantfeedbackId>());
			listBoxViewModel.getSelectedListItem().add(listBoxViewModel.getModel().get(0));
		}
	}

	protected void autoFocusItem(int rowNo) {
		if (listBoxViewModel != null && listBoxViewModel.getModel().size() > 0) {
			listBoxViewModel.setSelectedListItem(new HashSet<ApplicantfeedbackId>());
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
	protected void loadDetailPageTab(final ApplicantfeedbackId record) {
		//tabPath = getModuleTabPath();
		ApplicantfeedbackId tempRecord=null;
		/*if(listBoxViewModel.getSelectedListItem().size()>0){*/
			tempRecord=record;
		/*}else{
			tempRecord=(ApplicantfeedbackId) listBoxViewModel.getModel().get(0);
		}*/
		final ApplicantfeedbackId selectedRecord=tempRecord;
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
	
	public void loadTab(String tabId, ApplicantfeedbackId record) {
		loadTab(tabId, tabPath.get(tabId), record);
	}

	public void loadTab(String tabId, String uri, ApplicantfeedbackId record) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		Map arguments = new HashMap(3);
		// (ChargeList.getSelectedItem() != null) {
			arguments.put("selectedRecord", selectedItem);
			arguments.put("recordMode", saveType);
			arguments.put(ContextType.VIEW, view);
		//}
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(), arguments);
	}
	
	public void getDataRecordList(){
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReords = CRUDService.getAll(ApplicantfeedbackId.class);
		//userList = new ArrayList<ApplicantfeedbackId>((allReords));
		//userList = allReordsInDB;
		if(allReords.size()>0)
		selectedItem=allReords.get(0);
	}
	
	public void loadUserTabs() {
		String selectedTabId = usertabs.getSelectedTab().getId();
		closeAllTabs();
		for (Map.Entry<String, String> entry : tabPath.entrySet()) {
			Tab tab = addTab(entry.getKey(), entry.getKey(),
					new EventListener() {

						public void onEvent(Event event) throws Exception {
							loadTab(event.getTarget().getId());
						}
					});
			if (StringUtils.equals(entry.getKey(), selectedTabId)) {
				usertabs.setSelectedTab(tab);
			}
		}
		if (usertabs.getSelectedTab() == null) {
			usertabs.setSelectedIndex(0);
		}
		loadTab(usertabs.getSelectedTab().getId());
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
	
	public void loadTab(String tabId) {
		loadTab(tabId, tabPath.get(tabId));
	}

	public void loadTab(String tabId, String uri) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		Map arguments = new HashMap(3);
		// (ChargeList.getSelectedItem() != null) {
			arguments.put("selectedRecord", selectedItem);
			arguments.put("recordMode", "EDIT");
			arguments.put(ContextType.VIEW, view);
		//}
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(),
				arguments);
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
	
	/*public List<ApplicantfeedbackId> getDataSet() {
		return allReordsInDB;
	}*/

	public ApplicantfeedbackId getSelectedRecord() {
		 if(listBoxViewModel.getSelectedListItem()==null)
		 selectedItem = (ApplicantfeedbackId) listBoxViewModel.getModel().get(0) ;
		 else
		selectedItem = (ApplicantfeedbackId) listBoxViewModel.getSelectItem() ;	 
		 
		 return selectedItem;
	}

	@NotifyChange("*")
	@Command
	public void setSelectedRecord(@BindingParam("BeanData") ApplicantfeedbackId selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ListboxViewModel getListBoxViewModel() {
		return listBoxViewModel;
	}

	public void setListBoxViewModel(ListboxViewModel listBoxViewModel) {
		this.listBoxViewModel = listBoxViewModel;
	}

}
