
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TipoDatoPropioDTO;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;



public class DatosPropiosTrataComboBoxItemRenderer implements ComboitemRenderer
{
	@Override
	public void render(Comboitem item, Object data,int arg1) throws Exception
	{
		TipoDatoPropioDTO tmp=(TipoDatoPropioDTO) data;
		
		item.setLabel(tmp.getDescripcion());
		item.setValue(tmp.getId());
	}
}
