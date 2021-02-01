package com.egoveris.ffdd.web.adm;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class IndexTypeConverter implements TypeConverter {

	@Override
	public Object coerceToUi(Object val, Component comp) {
		return val != null && ((Integer) val) != 0;
	}

	@Override
	public Object coerceToBean(Object val, Component comp) {
		return ((Boolean) val)? 1 : 0;
	}
}
