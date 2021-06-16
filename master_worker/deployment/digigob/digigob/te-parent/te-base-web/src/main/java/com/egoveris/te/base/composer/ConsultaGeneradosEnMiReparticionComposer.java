package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.TrataDTO;

import java.util.Date;
import java.util.List;


public class ConsultaGeneradosEnMiReparticionComposer extends ConsultasComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -8455406871320015945L;

  @Override
  protected List<ExpedienteElectronicoDTO> buscar(String username, TrataDTO trata,
      List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos,
      Date desde, Date hasta, String cuitCuil, String estado) {

    return this.getExpedienteElectronicoService()
        .buscarExpedienteElectronicoPorReparticion(username, trata, expedienteMetaDataList,
            metaDatos, desde, hasta, tipoDocumento, this.numeroDocumentotbx.getValue(), cuitCuil,
            this.getEstado());
  }
}
