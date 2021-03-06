package com.egoveris.deo.web.satra.monitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class SelectedItemsConverter implements TypeConverter, java.io.Serializable {

		public Object coerceToUi(Object val, Component comp) { //load
			Listbox lbx = (Listbox) comp;
			lbx.clearSelection();
			if (val instanceof Collection) {
				Iterator valuesIterator = ((Collection) val).iterator();
				Set items = new HashSet();

				final ListModel xmodel = lbx.getModel();
				if (xmodel instanceof BindingListModel) {
					final BindingListModel model = (BindingListModel) xmodel;
					Listitem item = null;
					while (valuesIterator.hasNext()) {
						int index = model.indexOf(valuesIterator.next());
						if (index >= 0) {
							item = (Listitem) lbx.getItemAtIndex(index);
							if (item != null)
								items.add(item);
						}
					}	
					//We need this to support load-when:onSelect when first load 
					//the page in (so it is called only once).
					if (items.size() > 0 && items.size() != lbx.getSelectedCount()) { // bug 1647817, avoid endless-loop
						//bug #2140491
						Executions.getCurrent().setAttribute("zkoss.zkplus.databind.ON_SELECT"+lbx.getUuid(), Boolean.TRUE);
						Events.postEvent(new SelectEvent("onSelect", lbx, items, item));
					}    
					lbx.setSelectedItems(items);
					return TypeConverter.IGNORE;

				} else if (xmodel == null) { //no model case, assume Listitem.value to be used with selectedItem
					//iterate to find the selected item assume the value (select mold)
					while (valuesIterator.hasNext()) {
						Object value = valuesIterator.next();
						for (Iterator it = lbx.getItems().iterator(); it.hasNext();) {
							Listitem item = (Listitem) it.next();
							if (value.equals(item.getValue())) {
								items.add(item);
							}
						}
					}
					lbx.setSelectedItems(items);
					return TypeConverter.IGNORE;
				} else {
					throw new UiException("model of the databind listbox "+lbx+" must be an instanceof of org.zkoss.zkplus.databind.BindingListModel." + xmodel);
				}
			}
		  	return null;
		}
	  
		public Object coerceToBean(Object val, Component comp) { //save
		  	final Listbox lbx = (Listbox) comp;
			if (Executions.getCurrent().getAttribute("zkoss.zkplus.databind.ON_SELECT"+lbx.getUuid()) != null) {
				//bug #2140491
				//triggered by coerceToUi(), ignore this
				Executions.getCurrent().removeAttribute("zkoss.zkplus.databind.ON_SELECT"+lbx.getUuid());
				return TypeConverter.IGNORE;
			}
		  	if (val != null) {
		  		final ListModel model = lbx.getModel();
		  		Iterator itemsIterator = ((Set) lbx.getSelectedItems()).iterator();
		  		HashSet selectedValues = new HashSet();
		  		
		  		while (itemsIterator.hasNext()) {
					Listitem listitem = (Listitem) itemsIterator.next();
					if (model != null) { // model case
						selectedValues.add(model.getElementAt(listitem.getIndex()));
					} else { // no model case --> Value
						selectedValues.add(listitem.getValue());
					}
		  		}	
		  		return selectedValues;
		  	}
		 	return null;
		}
}
