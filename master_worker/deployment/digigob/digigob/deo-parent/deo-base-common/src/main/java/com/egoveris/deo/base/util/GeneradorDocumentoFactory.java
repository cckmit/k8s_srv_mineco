package com.egoveris.deo.base.util;

import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;

import java.util.Map;

public class GeneradorDocumentoFactory {

  private Map<String, GenerarDocumentoService> generadorDocumentoBeans;

  /**
   * Crea un objeto de tipo GenerarDocumentoService, dependiendo del tipo de
   * documento que se requiere generar.
   * 
   * @param tipo:
   *          Firma externa, manual(con template), importado(sin template).
   * @return Objeto dependiendo del tipo.
   */
  public GenerarDocumentoService obtenerGeneradorDocumento(TipoDocumentoDTO tipo) {
    GenerarDocumentoService generadorDocumento = null;
    if (tipo.getEsFirmaExterna())
      generadorDocumento = generadorDocumentoBeans.get("firmaExterna");
    else {
      if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO) {
        generadorDocumento = generadorDocumentoBeans.get("importado");
      }
      if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_IMPORTADO_TEMPLATE) {
        generadorDocumento = generadorDocumentoBeans.get("importadoTemplate");
      } else if (tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_LIBRE
          || tipo.getTipoProduccion() == Constantes.TIPO_PRODUCCION_TEMPLATE) {
        generadorDocumento = generadorDocumentoBeans.get("manual");
      }
    }
    return generadorDocumento;
  }

  public Map<String, GenerarDocumentoService> getGeneradorDocumentoBeans() {
    return generadorDocumentoBeans;
  }

  public void setGeneradorDocumentoBeans(
      Map<String, GenerarDocumentoService> generadorDocumentoBeans) {
    this.generadorDocumentoBeans = generadorDocumentoBeans;
  }
}
