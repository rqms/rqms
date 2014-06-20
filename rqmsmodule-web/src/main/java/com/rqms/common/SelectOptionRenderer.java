package com.rqms.common;

import java.io.Serializable;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class SelectOptionRenderer implements ComboitemRenderer, Serializable,
		ListitemRenderer {

	private static final long serialVersionUID = 1L;

	public void render(Comboitem item, Object data, int itemIndex)
			throws Exception {
		SelectOption<?> option = (SelectOption<?>) data;
		item.setValue(option.getValue());
		item.setLabel(option.getLabel());
		if (option.getDescription() != null) {
			item.setDescription(option.getDescription());
		}

	}

	public void render(Listitem item, Object data, int itemIndex)
			throws Exception {
		SelectOption<?> option = (SelectOption<?>) data;
		item.setValue(option.getValue());
		item.setLabel(option.getLabel());

	}
}
