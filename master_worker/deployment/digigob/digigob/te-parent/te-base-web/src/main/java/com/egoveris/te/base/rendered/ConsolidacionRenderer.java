package com.egoveris.te.base.rendered;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.vucfront.ws.model.ConsolidacionDTO;

public class ConsolidacionRenderer implements ListitemRenderer<ConsolidacionDTO> {

	@Override
	public void render(Listitem item, ConsolidacionDTO consolidacionDTO, int index) throws Exception {
		
		DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaPago = " ";
		
		if(consolidacionDTO.getFechaPago()!=null) {
			fechaPago = dateformat.format(consolidacionDTO.getFechaPago());
		}
		
		item.appendChild(new Listcell(consolidacionDTO.getCodigoTrata()));
		
		item.appendChild(new Listcell(consolidacionDTO.getDescripcionTrata()));

		item.appendChild(new Listcell(consolidacionDTO.getNumeroExpediente()));
		
		String transaccionTad = " ";
		if(consolidacionDTO.getNumeroTransaccionTad()!=null) {
			transaccionTad = consolidacionDTO.getNumeroTransaccionTad().toString();
		}
		item.appendChild(new Listcell(transaccionTad));
		
		item.appendChild(new Listcell(fechaPago));

		item.appendChild(new Listcell(consolidacionDTO.getNumeroAutorizacion()));

		item.appendChild(new Listcell(consolidacionDTO.getTransaccionPago()));
		
		item.appendChild(new Listcell(consolidacionDTO.getApiKey()));


		item.appendChild(new Listcell(consolidacionDTO.getMonto()));

		item.appendChild(new Listcell(consolidacionDTO.getTitularTarjeta()));

		item.appendChild(new Listcell(consolidacionDTO.getNumeroDUI()));		
		
		item.appendChild(new Listcell(consolidacionDTO.getOrganismoIniciador()));		

	}

}
