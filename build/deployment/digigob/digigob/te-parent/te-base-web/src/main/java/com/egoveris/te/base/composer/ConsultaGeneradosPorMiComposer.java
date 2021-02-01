package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;

import java.util.Date;
import java.util.List;

public class ConsultaGeneradosPorMiComposer extends ConsultasComposer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1569138975299328259L;

	@Override
	protected List<ExpedienteElectronicoDTO> buscar(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList,
			List<DatosDeBusqueda> metaDatos, Date desde,
			Date hasta, String cuitcuil,String estado) {
		
		super.buscar();
		
	 return  this.getExpedienteElectronicoService().buscarExpedienteElectronicoPorUsuario(username,
				trata, expedienteMetaDataList, metaDatos,desde, hasta,tipoDocumento,this.numeroDocumentotbx.getValue(), cuitcuil,estado);
		

	}
	
}
