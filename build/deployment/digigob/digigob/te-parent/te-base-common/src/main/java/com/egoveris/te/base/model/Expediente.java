package com.egoveris.te.base.model;

import com.egoveris.te.base.service.iface.IExpediente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contexto comun de documentos relacionados entre si por un mismo tramite.
 * 
 * @author rgalloci
 *
 */
public abstract class Expediente implements IExpediente, Serializable {
  private static final Logger logger = LoggerFactory.getLogger(Expediente.class);

  private static final long serialVersionUID = 2179910455645325661L;
  private List<DocumentoDTO> documentos = new ArrayList<>();
  private SolicitudExpedienteDTO solicitudIniciadora = new SolicitudExpedienteDTO();

  public SolicitudExpedienteDTO getSolicitudIniciadora() {
    return solicitudIniciadora;
  }

  public void setSolicitudIniciadora(SolicitudExpedienteDTO solicitudIniciadora) {
    this.solicitudIniciadora = solicitudIniciadora;
  }

  public void agregarDocumento(DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarDocumento(documento={}) - start", documento);
    }

    documentos.add(documento);

    if (logger.isDebugEnabled()) {
      logger.debug("agregarDocumento(Documento) - end");
    }
  }

  public void desagregarDocumento(DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("desagregarDocumento(documento={}) - start", documento);
    }

    getDocumentos().remove(documento);

    if (logger.isDebugEnabled()) {
      logger.debug("desagregarDocumento(Documento) - end");
    }
  }

  public List<DocumentoDTO> getDocumentos() {
    return documentos;
  }

  public void setDocumentos(List<DocumentoDTO> documentos) {
    this.documentos = documentos;
  }

  /**
   * METODOS QUE SE TUVIERON QUE AGREGAR PARA PODER REUTILIZAR UN MONTON DE
   * CODIGO EN EL COMPOSER DE CONSULTA DE EXPEDIENTES ELECTRONICOS DE ESTA
   * MANERA PODER UTILIZAR LA CONSULTA TANTO PARA EXPEDIENTES ELECTRONICOS COMO
   * LOS EXPEDIENTES DE TRACK
   */

  public String getLetraTrack() {
    return null;
  }

  public String getNumeroDocumentoTrack() {
    return null;
  }

  public String getTipoDocumentoTrack() {
    return null;
  }

  public String getLetraIniciaTrack() {
    return null;
  }

  public Integer getAnioIniciaTrack() {
    return null;
  }

  public Integer getNumeroActuacionIniciaTrack() {
    return null;
  }

  public String getReparticionActuacionIniciaTrack() {
    return null;
  }

  public String getSecuenciaIniciaTrack() {
    return null;
  }

  public String getCalleTrack() {
    return null;
  }

  public String getApellidonombrerazonTrack() {
    return null;
  }

  public String getDominioTrack() {
    return null;
  }

  public String getIssibTrack() {
    return null;
  }

  public String getDenunciaTrack() {
    return null;
  }

  public Integer getNumerocalleTrack() {
    return null;
  }

  public String getPartidaablTrack() {
    return null;
  }

  public String getCatastralTrack() {
    return null;
  }

  public String getCodigoTrataTrack() {
    return null;
  }

  public String getReparticionOrigenTrack() {
    return null;
  }

  public String getSectorOrigenTrack() {
    return null;
  }

  public Integer getFojasTrack() {
    return null;
  }

  public String getOrigenTrack() {
    return null;
  }

  public String getObservacionesTrack() {
    return null;
  }

}
