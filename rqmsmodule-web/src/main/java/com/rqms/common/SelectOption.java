package com.rqms.common;

import java.io.Serializable;

public class SelectOption<T> implements Serializable,
Comparable<SelectOption<T>> {

	private static final long serialVersionUID = 1L;

	private T value;

	private String label;

	private String description;
	
	public SelectOption() {
	}
	
	public SelectOption(T value) {
		this.value = value;
	}
	
	public SelectOption(T value, String label) {
		this(value);
		this.label = label;
	}

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int compareTo(SelectOption<T> o) {
		return this.label.compareToIgnoreCase(o.label);
	}

	public String toString () {
		return getLabel()  ;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(value==null){
			return super.hashCode();
		}
			return value.hashCode();
	}

	/**
	 * Equals method to compare the value of the drop down. This is used by data
	 * binder for selected item.The value being used for comparing the elements
	 * since it is unique.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SelectOption otherObj = (SelectOption) obj;
		if (value == null) {
			if (otherObj.value != null) {
				return false;
			}
		} else if (!value.equals(otherObj.value)) {
			return false;
		}
		return true;

	}
	
	/*public boolean isNotEmpty(){
		return value!=null && !CsdcStringUtils.isBlank(value.toString());
		
	}*/

}

