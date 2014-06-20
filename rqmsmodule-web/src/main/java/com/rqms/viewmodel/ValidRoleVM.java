package com.rqms.viewmodel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;

import com.rqms.common.ListboxViewModel;
import com.rqms.domain.Validrole;
import com.rqms.service.CRUDService;
import com.rqms.utilities.MyLib;

public class ValidRoleVM {
	
	private Component view;
	protected ListboxViewModel listBoxViewModel;
	private ListModelList<Validrole> allReordsInDB;
	private List<Validrole> allReords = null;
	private Validrole selectedrecord;
	
	@WireVariable
	private CRUDService crudService;
	
	@AfterCompose
	public void doAfterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("map") Map<String, Object> parameters) {
		Selectors.wireComponents(view, this, false);
		this.view = view;
		listBoxViewModel = new ListboxViewModel();
		allReordsInDB = new ListModelList<>();
		initRecord();
		for (Validrole up : allReords) {
			allReordsInDB.add(up);
		}
		allReordsInDB.setMultiple(true);
		listBoxViewModel.setModel(allReordsInDB);
		
	}
	
	public void initRecord() {
		crudService = (CRUDService) SpringUtil.getBean("CRUDService");
		allReords = crudService.getAll(Validrole.class);
	}
	
	@Command("refreshList")
	@NotifyChange({ "*" })
	public void refreshList(@ContextParam(ContextType.VIEW) Component view) {
		doAfterCompose(view, null);
	}
	
	@Command
	public void changedRoleType(@BindingParam("comp") Component comp ,@BindingParam("BeanData") Validrole changedObject) {
		if(selectedrecord!=null){
			selectedrecord.setRoletype(changedObject.getRoletype());
		}else{
			selectedrecord=changedObject;
		}
	}
	
	@Command
	public void changedRoleDesc(@BindingParam("comp") Component comp ,@BindingParam("BeanData") Validrole changedObject) {
		if(selectedrecord!=null){
			selectedrecord.setRoledescription(changedObject.getRoledescription());
		}else{
			selectedrecord=changedObject;
		}
	}
	
	@Command
	@NotifyChange("*")
	public void save() {
		java.util.Date date= new java.util.Date();
		selectedrecord.setStampdate(new Timestamp(date.getTime()));
		selectedrecord.setStampuser("admin");
		if (MyLib.IsValidBean(this.selectedrecord) == false) {
			return;
		}
		crudService.Save(this.selectedrecord);
		MyLib.showSuccessmessage();
	}
	
	@NotifyChange("*")
	@Command
	public void add() {
		selectedrecord = new Validrole();
		if(listBoxViewModel.getModel() == null) {
			listBoxViewModel.setModel(new ListModelList<>());
		}
		listBoxViewModel.getModel().add(selectedrecord);
		listBoxViewModel.setSelectItem(selectedrecord);
		
	}

	public ListboxViewModel getListBoxViewModel() {
		return listBoxViewModel;
	}

	public void setListBoxViewModel(ListboxViewModel listBoxViewModel) {
		this.listBoxViewModel = listBoxViewModel;
	}

	public Validrole getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Validrole selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

}
