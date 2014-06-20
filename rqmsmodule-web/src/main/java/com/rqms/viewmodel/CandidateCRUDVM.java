package com.rqms.viewmodel;

import java.io.IOException;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Center;
import org.zkoss.zul.Tabbox;

import com.rqms.domain.Candidateprofile;
import com.rqms.service.CRUDService;
import com.rqms.utilities.MyLib;

public class CandidateCRUDVM {
	@WireVariable
	private CRUDService CRUDService;
	private Candidateprofile selectedRecord;
	private Center centerArea;
	private String recordMode;
	private boolean makeAsReadOnly;
	private Component currentView;

	public boolean isMakeAsReadOnly() {
		return makeAsReadOnly;
	}

	public void setMakeAsReadOnly(boolean makeAsReadOnly) {
		this.makeAsReadOnly = makeAsReadOnly;
	}

	public String getRecordMode() {
		return recordMode;
	}

	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") Candidateprofile userProfile,
			@ExecutionArgParam("recordMode") String recordMode,
			@ExecutionArgParam("centerArea") Center centerArea)
			throws IOException {
		currentView = view;
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		CRUDService = (CRUDService) SpringUtil.getBean("CRUDService");
		this.centerArea = centerArea;
		if (recordMode.equals("NEW")) {
			this.selectedRecord = new Candidateprofile();
		}
		if (recordMode.equals("EDIT")) {
			this.selectedRecord = userProfile;
		}
		if (recordMode == "READ") {
			setMakeAsReadOnly(true);
			this.selectedRecord = userProfile;
		}
	}

	public Candidateprofile getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(Candidateprofile selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	public Center getCenterArea() {
		return centerArea;
	}

	public void setCenterArea(Center centerArea) {
		this.centerArea = centerArea;
	}

	@Command
	@NotifyChange("*")
	public void saveThis() {
		if (MyLib.IsValidBean(this.selectedRecord) == false) {
			return;
		}
		CRUDService.Save(this.selectedRecord);
		MyLib.showSuccessmessage();
		Tabbox mainTab = (Tabbox) currentView.getParent().getParent()
				.getParent().getParent().getParent().getParent().getParent()
				.getParent().getParent();
		mainTab.getChildren();
		BindUtils.postNotifyChange(null, null, CandidateCRUDVM.this,
				"selectedRecord");
	}
}
