package com.egoveris.te.base.repository.expediente;

import com.egoveris.te.base.model.expediente.ExpedienteAsociado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteAsociadoRepository extends JpaRepository<ExpedienteAsociado, Long> {

  ExpedienteAsociado findByNumeroAndAnioAndEsExpedienteAsociadoTC(Integer numero,
      Integer anio, boolean esExpedienteAsociadoTC);

  ExpedienteAsociado findByNumeroAndAnioAndEsExpedienteAsociadoFusionTrue(Integer stNumeroSADE,
      Integer stAnioSADE, boolean asociadoFusion);
    
  ExpedienteAsociado findByNumeroAndAnioAndEsExpedienteAsociadoFusion(Integer stNumeroSADE,
      Integer stAnioSADE, boolean asociadoFusion);

  ExpedienteAsociado findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndEsExpedienteAsociadoFusion(
      String tipoDocumento, Integer anio, Integer numero, String reparticion,
      Boolean esExpedienteAsociadoFusion);

  ExpedienteAsociado findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuarioAndIdExpCabeceraTCIsNotNull(
      String tipoActaucion, Integer anio, Integer numero, String reparticion);

  ExpedienteAsociado findByNumeroAndEsExpedienteAsociadoFusionTrue(Integer numero);
  
  

}
