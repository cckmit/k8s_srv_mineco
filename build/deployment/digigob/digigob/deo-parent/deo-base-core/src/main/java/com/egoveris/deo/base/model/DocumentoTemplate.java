package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_DOCUMENTO_TEMPLATE")
public class DocumentoTemplate {
  @EmbeddedId
  private DocumentoTemplatePK documentoTemplatePK;

  @Column(name = "ID_TRANSACCION")
  private Integer idTransaccion;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumns({ @JoinColumn(name = "ID_TIPODOCUMENTO", insertable = false, updatable = false),
      @JoinColumn(name = "VERSION", insertable = false, updatable = false) })
  private TipoDocumentoTemplate tipoDocumentoTemplate;

  public DocumentoTemplatePK getDocumentoTemplatePK() {
    return documentoTemplatePK;
  }

  public void setDocumentoTemplatePK(DocumentoTemplatePK documentoTemplatePK) {
    this.documentoTemplatePK = documentoTemplatePK;
  }

  public Integer getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(Integer idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

  public TipoDocumentoTemplate getTipoDocumentoTemplate() {
    return tipoDocumentoTemplate;
  }

  public void setTipoDocumentoTemplate(TipoDocumentoTemplate tipoDocumentoTemplate) {
    this.tipoDocumentoTemplate = tipoDocumentoTemplate;
  }
}
