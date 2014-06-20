package com.rqms.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

import com.rqms.domain.ApplicantfeedbackId;
import com.rqms.service.ApplicantfeedbackidService;
import com.rqms.service.CRUDService;
import com.rqms.utilities.MyLib;

public class ApplicantFeedBackDetail {
	private Component currentView;
	private ApplicantfeedbackId selectedItem;
	private Integer jobid;
	private Integer candidateid;
	private static final Map<String, String> tabPath;
	static {
		tabPath = new HashMap<String, String>();
		tabPath.put("Candidate Feed Back", "/add_feedback_detail.zul");
	}
	@Wire("#usertabs")
	Tabbox usertabs;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("jobID") Integer jobId,
			@ExecutionArgParam("candidateID") Integer candidateId) {
		Selectors.wireComponents(view, this, false);
		this.currentView = view;
		jobid = jobId;
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		candidateid = candidateId;
		userName = FHSessionUtil.getCurrentUser().getUserLoginID();

		loadFeedBackDetails(jobId, candidateId);

		if (usertabs != null)
			loadDetailPageTab(selectedItem);
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

	public void loadTab(String tabId, ApplicantfeedbackId record) {
		loadTab(tabId, tabPath.get(tabId), record);
	}

	public void loadTab(String tabId, String uri, ApplicantfeedbackId record) {
		List tabpanels = usertabs.getTabpanels().getChildren();
		for (Object child : tabpanels) {
			Components.removeAllChildren((Component) child);
		}
		this.selectedItem = record;
		Map arguments = new HashMap(3);
		arguments.put("selectedItem", record);
		arguments.put("recordMode", "EDIT");
		arguments.put(ContextType.VIEW, currentView);
		Executions.createComponents(uri, getTab(tabId).getLinkedPanel(), arguments);
	}

	public void loadDetailPageTab(final ApplicantfeedbackId record) {
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

	public void loadFeedBackDetails(int jobId, int candidateId) {
		ApplicantfeedbackidService jobdetailsService = (ApplicantfeedbackidService) SpringUtil.getBean("ApplicantfeedbackidService");
		selectedItem = jobdetailsService.findByUniqueIDId(jobid, candidateid);
		if (selectedItem == null) {
			selectedItem = new ApplicantfeedbackId();
			selectedItem.setUserid(userName);
			selectedItem.setCandidateid(candidateId);
			selectedItem.setJobid(jobId);	
			selectedItem.setStampuser(userName);
		}
	}

	public Component getCurrentView() {
		return currentView;
	}

	public void setCurrentView(Component currentView) {
		this.currentView = currentView;
	}

	public ApplicantfeedbackId getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ApplicantfeedbackId selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Integer getJobid() {
		return jobid;
	}

	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}

	public Integer getCandidateid() {
		return candidateid;
	}

	public void setCandidateid(Integer candidateid) {
		this.candidateid = candidateid;
	}

	@Command
	public void close(@ContextParam(ContextType.VIEW) final Component view, @ContextParam(ContextType.TRIGGER_EVENT) Event event) {
		event.stopPropagation();
		view.detach();
	}

	private String userName;
	@WireVariable
	private CRUDService CRUDService;

	@Command
	@NotifyChange("*")
	public void saveThis() {
		if (MyLib.IsValidBean(this.selectedItem) == false) {
			return;
		}
		CRUDService.Save(this.selectedItem);
		MyLib.showSuccessmessage();
		BindUtils.postNotifyChange(null, null, ApplicantFeedBackDetail.this, "selectedItem");
	}
}
