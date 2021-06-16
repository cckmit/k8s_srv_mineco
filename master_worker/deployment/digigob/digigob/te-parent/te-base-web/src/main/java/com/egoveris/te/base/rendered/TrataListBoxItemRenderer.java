package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.util.ConstantesServicios;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TrataListBoxItemRenderer implements ListitemRenderer {

	private TrataService trataService;

	@Override
	public void render(Listitem item, Object data, int arg1) throws Exception {
		
		this.trataService = (TrataService) SpringUtil.getBean(ConstantesServicios.TRATA_SERVICE);
		TrataDTO trata = (TrataDTO) data;
		String codigo = trata.getCodigoTrata();
		String Desc = trataService.obtenerDescripcionTrataByCodigo(codigo);
		item.setLabel(trataService.formatoToStringTrata(codigo, Desc));
		item.setValue(trata);
	}

}
