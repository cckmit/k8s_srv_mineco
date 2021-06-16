package com.egoveris.te.base.util;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.SolicitudSubs;
import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentoGedoTemplate {

  final static Logger logger = LoggerFactory.getLogger(DocumentoGedoTemplate.class);

  private static final String LI = "<li>";
  private static final String LI_END = "</li>";
  private static final String UL = "<ul>";
  private static final String UL_END = "</ul>";
  private static final String BR = "<br />";

  /**
   * Crea un Array de Bytes con el formato de la caratula.
   * 
   * @param caratula
   * @return
   */

  public byte[] generarDocumentoComoByteArray(DocumentoDTO documento) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoComoByteArray(documento={}) - start", documento);
    }

    StringBuilder buffer = new StringBuilder();
    SimpleDateFormat fechaCaratulacion = new SimpleDateFormat();
    fechaCaratulacion.applyLocalizedPattern("dd/MM/yyyy");

    String motivo = documento.getMotivo();
    String aclaracion = documento.getAclaracion();

    String motivoFormateado = motivo.replace("\n", BR);

    buffer.append("Motivo: ").append(motivoFormateado);

    // Se elimina la palabra "Aclaracion" (mantis 63056)
    if (aclaracion != null) {
      buffer.append(BR);
      buffer.append(BR);
      String aclaracionFormateado = aclaracion.replace("\n", BR);
      buffer.append(aclaracionFormateado);
    }

    try {
      byte[] returnbyteArray = buffer.toString().getBytes("UTF-8");
      if (logger.isDebugEnabled()) {
        logger.debug("generarDocumentoComoByteArray(Documento) - end - return value={}",
            returnbyteArray);
      }
      return returnbyteArray;
    } catch (UnsupportedEncodingException e) {
      logger.error("No es soportado el formato UTF-8 ", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarDocumentoComoByteArray(Documento) - end - return value={null}");
    }
    return null;
  }

  public static byte[] generarCuerpoDocSubsanacion(SolicitudSubs solicitudSubs) {
    if (logger.isDebugEnabled()) {
      logger.debug("generarCuerpoDocSubsanacion(solicitudSubs={}) - start", solicitudSubs);
    }

    StringBuilder buffer = new StringBuilder();
    buffer.append("Motivo: ");
    buffer.append(solicitudSubs.getMotivo());
    buffer.append(BR);

    buffer.append("Enviado a VUC para ");
    buffer.append(solicitudSubs.getTipo());
    buffer.append(" de: ");
    buffer.append(BR);
    buffer.append(UL);
    if (solicitudSubs.isFormulario()) {
      buffer.append(LI);
      buffer.append("Subsanación del formulario inicial del trámite");
      buffer.append(LI_END);
    }
    if (solicitudSubs.isSubsDoc()) {
      buffer.append(LI);
      buffer.append("Subsanación de documentación:");
      buffer.append(BR);
      buffer.append(UL);
      for (ExternalDocumentoVucDTO doc : solicitudSubs.getListaSubsDocConNombres()) {
        buffer.append(LI);
        buffer.append(doc.getTipoDocumento().getAcronimoTad());
        buffer.append(" - ");
        buffer.append(doc.getTipoDocumento().getDescripcion());
        buffer.append(LI_END);
      }
      buffer.append(UL_END);
      buffer.append(LI_END);
    }
    if (solicitudSubs.isPedidoDoc()) {
      buffer.append(LI);
      buffer.append("Agregar documentación:");
      buffer.append(BR);
      buffer.append(UL);
      for (String doc : solicitudSubs.getListaPedidoDocs()) {
        buffer.append(LI);
        buffer.append(doc);
        buffer.append(LI_END);
      }
      buffer.append(UL_END);
      buffer.append(LI_END);
    }
    buffer.append(UL_END);
    buffer.append(BR);
    try {
      byte[] returnbyteArray = buffer.toString().getBytes("UTF-8");
      if (logger.isDebugEnabled()) {
        logger.debug("generarCuerpoDocSubsanacion(SolicitudSubs) - end - return value={}",
            returnbyteArray);
      }
      return returnbyteArray;
    } catch (UnsupportedEncodingException e) {
      logger.error("No es soportado el formato UTF-8 ", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("generarCuerpoDocSubsanacion(SolicitudSubs) - end - return value={null}");
    }
    return null;
  }

  public static byte[] generarCuerpoComunicacionOficial(DocumentoDTO documento,
      String motivoExpediente, String pieDePaginaDeDestinatarios) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarCuerpoComunicacionOficial(documento={}, motivoExpediente={}, pieDePaginaDeDestinatarios={}) - start",
          documento, motivoExpediente, pieDePaginaDeDestinatarios);
    }

    StringBuilder buffer = new StringBuilder();
    SimpleDateFormat fechaCaratulacion = new SimpleDateFormat();
    fechaCaratulacion.applyLocalizedPattern("dd/MM/yyyy");

    buffer.append("Motivo: ").append(motivoExpediente);

    buffer.append(BR);
    buffer.append(BR);

    buffer.append(LI);
    buffer.append(pieDePaginaDeDestinatarios);
    buffer.append(LI_END);

    buffer.append(BR);

    try {
      byte[] returnbyteArray = buffer.toString().getBytes("UTF-8");
      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarCuerpoComunicacionOficial(Documento, String, String) - end - return value={}",
            returnbyteArray);
      }
      return returnbyteArray;
    } catch (UnsupportedEncodingException e) {
      logger.error("No es soportado el formato UTF-8 ", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarCuerpoComunicacionOficial(Documento, String, String) - end - return value={null}");
    }
    return null;
  }

  // public byte[]generarCuerpoDocumentoTomaVista(String motivo,Date
  // fecha,List<DocumentoRequest>numDocPermitidosList){
  // if (logger.isDebugEnabled()) {
  // logger.debug("generarCuerpoDocumentoTomaVista(motivo={}, fecha={},
  // numDocPermitidosList={}) - start", motivo, fecha, numDocPermitidosList);
  // }
  //
  // StringBuffer buffer = new StringBuffer();
  //
  // buffer.append("Motivo: ").append(motivo);
  // buffer.append(BR);
  //
  // if (fecha != null){
  // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
  // String f = format.format(fecha);
  // buffer.append("Fecha de fin de suspensión: ").append(f);
  // buffer.append(BR);
  // }
  //
  // if(fecha!=null){
  // buffer.append("Se ha concedido la toma de vista solicitada sobre los
  // siguientes documentos GEDO: ");
  // buffer.append(BR);
  // }
  //
  // if(numDocPermitidosList != null && !numDocPermitidosList.isEmpty()){
  // buffer.append(UL);
  // for (DocumentoRequest string : numDocPermitidosList) {
  // buffer.append(LI);
  // buffer.append(string);
  // buffer.append(LI_END);
  // }
  // buffer.append(UL_END);
  //
  // buffer.append("Los mismos podrán ser visualizados desde el buzón Mis
  // Trámites de TAD. ");
  //
  // buffer.append(BR);
  // }

  // try {
  // byte[] returnbyteArray = buffer.toString().getBytes("UTF-8");
  // if (logger.isDebugEnabled()) {
  // logger.debug("generarCuerpoDocumentoTomaVista(String, Date,
  // List<DocumentoRequest>) - end - return value={}", returnbyteArray);
  // }
  // return returnbyteArray;
  // } catch (UnsupportedEncodingException e) {
  // logger.error(e.getMessage());
  // logger.debug("No es soportado el formato UTF-8");
  // }
  //
  // if (logger.isDebugEnabled()) {
  // logger.debug("generarCuerpoDocumentoTomaVista(String, Date,
  // List<DocumentoRequest>) - end - return value={null}");
  // }
  // return null;
  // }
}
