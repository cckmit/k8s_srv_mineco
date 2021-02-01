package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TrataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class TrataComboBoxItemRenderer implements ComboitemRenderer {

	@Autowired
	private TrataService trataService;

	@Override
	public void render(Comboitem item, Object data, int arg1) throws Exception {

		TrataDTO trata = (TrataDTO) data;
		String codigo = trata.getCodigoTrata();
		String desc = trataService.obtenerDescripcionTrataByCodigo(codigo);
		item.setLabel(trataService.formatoToStringTrata(codigo, desc));
		item.setValue(trata);
	}

}
