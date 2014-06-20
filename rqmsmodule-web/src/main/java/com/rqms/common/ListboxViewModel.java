package com.rqms.common;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.zkoss.zul.ListModelList;

public class ListboxViewModel implements Serializable {


	private static final long serialVersionUID = 1L;

	/** The header id. */
	private List<String> headerId;
	
	
	
	/** The model. */
	@SuppressWarnings("rawtypes")
	private  ListModelList model;
	
	/** The available headers. */
	@SuppressWarnings("rawtypes")
	private ListModelList  availableHeaders = new ListModelList ();
	
	
	
	/** The selected list item. */
	@SuppressWarnings("rawtypes")
	private Set  selectedListItem;
	
	/** The select item. */
	@SuppressWarnings("unused")
	private Object selectItem;


	/**
	 * Gets the available headers.
	 *
	 * @return the available headers
	 */
	@SuppressWarnings("rawtypes")
	public ListModelList getAvailableHeaders() {
		return availableHeaders;
	}

	/**
	 * Sets the available headers.
	 *
	 * @param availableHeaders the new available headers
	 */
	@SuppressWarnings("rawtypes")
	public void setAvailableHeaders(ListModelList availableHeaders) {
		this.availableHeaders = availableHeaders;
	}

	/**
	 * Gets the header id.
	 *
	 * @return the header id
	 */
	public List<String> getHeaderId() {
		return headerId;
	}

	/**
	 * Sets the header id.
	 *
	 * @param headerId the new header id
	 */
	public void setHeaderId(List<String> headerId) {
		this.headerId = headerId;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	@SuppressWarnings("rawtypes")
	public ListModelList getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	@SuppressWarnings("rawtypes")
	public void setModel(ListModelList model) {
		this.model = model;
	}

	/**
	 * Gets the selected list item.
	 *
	 * @return the selected list item
	 */
	@SuppressWarnings("rawtypes")
	public Set getSelectedListItem() {
		return selectedListItem;
	}

	/**
	 * Sets the selected list item.
	 *
	 * @param selectedListItem the new selected list item
	 */
	@SuppressWarnings("rawtypes")
	public void setSelectedListItem(Set selectedListItem) {
		this.selectedListItem = selectedListItem;
	}

	/**
	 * Gets the select item.
	 *
	 * @return the select item
	 */
	public Object getSelectItem() {
		return selectItem;
	}

	/**
	 * Sets the select item.
	 *
	 * @param selectItem the new select item
	 */
	public void setSelectItem(Object selectItem) {
		this.selectItem = selectItem;
	}

}
