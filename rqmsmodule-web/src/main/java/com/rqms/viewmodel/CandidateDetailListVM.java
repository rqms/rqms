package com.rqms.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.rqms.common.ListboxViewModel;
import com.rqms.domain.Candidateprofile;
import com.rqms.domain.Jobdetails;
import com.rqms.service.CRUDService;
import com.rqms.service.JobdetailsService;
import com.rqms.utilities.MyLib;

public class CandidateDetailListVM {
	private Component view;
	private int _jobID;
	private boolean makeAsReadOnly;
	protected ListboxViewModel listBoxViewModel;
	private ListModelList<Candidateprofile> allReordsInDB;
	private List<Candidateprofile> allReords = null;
	private Candidateprofile selectedItem;
	private Set<Candidateprofile> allReordsSet;
	private JobdetailsService jobdetailsService;

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

	public Candidateprofile getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Candidateprofile selectedItem) {
		this.selectedItem = selectedItem;
	}

	static {
		tabPath = new HashMap<String, String>();
		tabPath.put("Job Detail", "/add_candidate_detail.zul");
	}

	@WireVariable
	private CRUDService CRUDService;

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}

	public void getDataRecordList() {
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		jobdetailsService = (JobdetailsService) SpringUtil.getBean("JobdetailsService");
		allReordsSet = jobdetailsService.findByJobID(_jobID);
		try {
			if (!allReordsSet.isEmpty()) {
				Iterator<Candidateprofile> itr = allReordsSet.iterator();
				allReords = new ArrayList<Candidateprofile>();
				while (itr.hasNext()) {
					allReords.add(itr.next());
				}
			}
		} catch (Exception e) {
			allReords = new ArrayList<Candidateprofile>();
			allReordsSet = new HashSet<Candidateprofile>();
		}
		if (allReords != null && allReords.size() > 0)
			selectedItem = allReords.get(0);
	}

	@Wire("#usertabs")
	Tabbox usertabs;

	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("map") Map<String, Object> parameters) {
		Selectors.wireComponents(view, this, false);
		Object JobID = Executions.getCurrent().getParameter("JobID");
		if (JobID != null) {
			_jobID = Integer.valueOf(JobID.toString());
		}
		this.view = view;
		listBoxViewModel = new ListboxViewModel();
		allReordsInDB = new ListModelList<>();
		getDataRecordList();
		if (allReords != null)
			for (Candidateprofile up : allReords) {
				allReordsInDB.add(up);
			}
		// allReordsInDB.setMultiple(true);
		listBoxViewModel.setModel(allReordsInDB);

		// loadUserTabs();
		if (usertabs != null && listBoxViewModel.getModel().size() > 0) {
			if (selectedItem != null)
				loadDetailPageTab(selectedItem);
			else {
				selectedItem = new Candidateprofile();
				loadDetailPageTab(selectedItem);
			}
		} else {
			selectedItem = new Candidateprofile();
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

	public void loadTab(String tabId, Candidateprofile record) {
		loadTab(tabId, tabPath.get(tabId), record);
	}

	public void loadTab(String tabId, String uri, Candidateprofile record) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		this.selectedItem = record;
		Map arguments = new HashMap(3);
		arguments.put("selectedItem", record);
		arguments.put("recordMode", "EDIT");
		arguments.put(ContextType.VIEW, view);
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(), arguments);
	}

	@Command
	@NotifyChange("usertabs")
	public void loadDetailPageTab() {
		if (!listBoxViewModel.getSelectedListItem().isEmpty()) {
			for (Iterator itr = listBoxViewModel.getSelectedListItem().iterator(); itr.hasNext();) {
				Candidateprofile data = (Candidateprofile) itr.next();
				loadDetailPageTab(data);
			}
		}
	}

	public void loadDetailPageTab(final Candidateprofile record) {
		String selectedTabId = usertabs.getSelectedTab().getId();
		closeAllTabs();

		for (Entry<String, String> entry : tabPath.entrySet()) {
			@SuppressWarnings("rawtypes")
			Tab tab = addTab(entry.getKey(), entry.getKey(), new EventListener() {
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
	public void addNewCandidateDetail() {
		Candidateprofile selectedrecord = new Candidateprofile();
		if (listBoxViewModel.getModel() == null) {
			listBoxViewModel.setModel(new ListModelList<>());
		}
		listBoxViewModel.getModel().add(selectedrecord);
		listBoxViewModel.setSelectItem(selectedrecord);
		loadDetailPageTab(selectedrecord);
	}

	@Command
	@NotifyChange("*")
	public void refresh() {
		getDataRecordList();
		allReordsInDB.removeAll(allReordsInDB);
		if (allReords != null)
			for (Candidateprofile up : allReords) {
				allReordsInDB.add(up);
			}
		listBoxViewModel.setModel(allReordsInDB);

		if (usertabs != null && listBoxViewModel.getModel().size() > 0) {
			this.selectedItem = (Candidateprofile) listBoxViewModel.getModel().get(0);
			loadDetailPageTab(selectedItem);
		}
	}

	@Command
	@NotifyChange("*")
	public void saveThis() {
		Jobdetails jobdetail = jobdetailsService.findById(_jobID);
		if (MyLib.IsValidBean(this.selectedItem) == false) {
			return;
		}
		allReordsSet.add(selectedItem);
		jobdetail.setCandidates(allReordsSet);
		// CRUDService.Save(this.selectedItem);
		CRUDService.Save(jobdetail);
		MyLib.showSuccessmessage();
		BindUtils.postNotifyChange(null, null, CandidateDetailListVM.this, "selectedItem");
		refresh();
	}

	public int get_jobID() {
		return _jobID;
	}

	public void set_jobID(int _jobID) {
		this._jobID = _jobID;
	}

	@Command
	public void addFeedBack(@ContextParam(ContextType.VIEW) Component comp, @BindingParam("candidateRecord") Candidateprofile candidateprofile) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("candidateID", candidateprofile.getCandidateid());
		map.put("jobID", _jobID);
		Executions.getCurrent().createComponents("add_feed_back.zul", comp, map);
	}
}
