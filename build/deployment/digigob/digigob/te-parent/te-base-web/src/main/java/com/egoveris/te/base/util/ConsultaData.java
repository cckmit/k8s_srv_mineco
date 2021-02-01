package com.egoveris.te.base.util;

import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;

import java.util.Date;
import java.util.List;

public class ConsultaData {

  private String tipoBusqueda;
  private String username;
  private TipoDocumentoDTO tipoDocumento;
  private List<MetadataDTO> documentoMetaDataList;
  private Date desde;
  private Date hasta;

  public String getTipoBusqueda() {
    return tipoBusqueda;
  }

  public void setTipoBusqueda(String tipoBusqueda) {
    this.tipoBusqueda = tipoBusqueda;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public List<MetadataDTO> getDocumentoMetaDataList() {
    return documentoMetaDataList;
  }

  public void setDocumentoMetaDataList(List<MetadataDTO> documentoMetaDataList) {
    this.documentoMetaDataList = documentoMetaDataList;
  }

  public Date getDesde() {
    return desde;
  }

  public void setDesde(Date desde) {
    this.desde = desde;
  }

  public Date getHasta() {
    return hasta;
  }

  public void setHasta(Date hasta) {
    this.hasta = hasta;
  }

}
