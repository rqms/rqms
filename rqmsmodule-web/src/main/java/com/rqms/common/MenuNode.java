package com.rqms.common;

import java.io.Serializable;
import java.util.List;

import com.rqms.comp.MenuConstants;

public class MenuNode implements Serializable {

	private static final long serialVersionUID = 1L;
	private String label;
	private String action;
	private String iconImage;
	private int id;// Unique ID of the action performed
	private static final String IMAGE_HOME = "/resource/image/";
	private List<MenuNode> children;
	private boolean checked;
	private boolean disable;

	public MenuNode() {

	}

	public MenuNode(String token) {
		this.label = token;
		this.action = token;
		if (MenuConstants.menuIcons.get(token) != null) {
			this.iconImage = IMAGE_HOME + MenuConstants.menuIcons.get(token);
		}
	}

	public MenuNode(String token, String action) {
		this(token);
		this.action = action;
	}

	public MenuNode(String token, boolean checked) {
		this(token);
		this.checked = checked;
	}

	public MenuNode(String token, boolean checked, String label) {
		this(token);
		this.checked = checked;
		this.label = label;
	}

	public MenuNode(String token, String action, boolean disable) {
		this(token);
		this.action = action;
		this.disable = disable;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MenuNode> getChildren() {
		return children;
	}

	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
}