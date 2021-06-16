package com.egoveris.te.base.util;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Toolbarbutton;

/**
 * 
 * @author lfishkel
 *
 */
public class FiltroEE {

  static TipoDocumentoService tipoDocumentoService = (TipoDocumentoService) SpringUtil.getBean(ConstantesServicios.TIPO_DOCUMENTO_SERVICE);
  
  private static final long A_DAY = 1000 * 60 * 60 * 24;
  public static final Pattern DIACRITICS_AND_FRIENDS = Pattern
      .compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

  public static List<DocumentoDTO> filtrarSinPase(List<DocumentoDTO> documentosFiltrados) {
    List<DocumentoDTO> documentosSinPase = new ArrayList<DocumentoDTO>();
    for (DocumentoDTO doc : documentosFiltrados) {
      if (!esDocumentoPase(doc)) {
        documentosSinPase.add(doc);
      }
    }
    return documentosSinPase;
  }

  private static boolean esDocumentoPase(DocumentoDTO doc) {
    boolean result = false;
    if (doc.getTipoDocGenerado() != null) { 
      String tipoDocumentoGenerador = tipoDocumentoService
          .tipoDocumentoGeneracion(doc.getTipoDocGenerado());
      result = tipoDocumentoGenerador.toUpperCase()
          .equals(ConstantesWeb.TIPO_DOCUMENTO_GENERADO_PASE);
    }
    return result;
  }

  public static List<DocumentoDTO> filtrarConUltimoPase(List<DocumentoDTO> list) {
    DocumentoDTO ultimoPase = buscarUltimoPase(list);
    List<DocumentoDTO> result = new ArrayList<DocumentoDTO>();
    if (ultimoPase != null)
      result.add(ultimoPase);
    result.addAll(filtrarSinPase(list));
    return result;
  }

  public static DocumentoDTO buscarUltimoPase(List<DocumentoDTO> list) {
    DocumentoDTO ultimoPase = null;
    for (DocumentoDTO doc : list) {
      if (esDocumentoPase(doc)) {
        ultimoPase = doc;
        break;
      }
    }
    return ultimoPase;
  }

  public static List<DocumentoDTO> filtrarConCamposDeBusqueda(
      List<DocumentoDTO> documentosFiltrados, String reparticion, Integer anio, Date desde,
      Date hasta, String tipoDocumento, String referencia) {

    List<DocumentoDTO> resp = new ArrayList<DocumentoDTO>();
    Date hastaMasUnDia = null;
    if (hasta != null)
      hastaMasUnDia = new Date(hasta.getTime() + A_DAY);

    for (DocumentoDTO doc : documentosFiltrados) {
      if ((desde == null || (desde.compareTo(doc.getFechaAsociacion()) <= 0
          && (hastaMasUnDia == null || hastaMasUnDia.compareTo(doc.getFechaAsociacion()) >= 0)))
          && (anio == null || doc.getNumeroSade().contains(anio.toString()))
          && (reparticion == null || reparticion.isEmpty()
              || doc.getNumeroSade().contains(reparticion))
          && (referencia == null || referencia.trim().isEmpty()
              || normalizarString(doc.getMotivo()).contains(referencia.trim().toUpperCase()))
          && (tipoDocumento == null || tipoDocumento.isEmpty()
              || doc.getTipoDocAcronimo().equalsIgnoreCase(tipoDocumento))) {

        resp.add(doc);
      }
    }

    return resp;
  }

  public static String normalizarString(String str) {
    if (str == null) {
      return null;
    }
    str = str.trim().toUpperCase();
    str = Normalizer.normalize(str, Normalizer.Form.NFD);
    str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
    return str;
  }

  public static void blockedChildren(List<Component> children, boolean status) {
	   
	  for (Component c : children) {
	        if (c instanceof Toolbarbutton) {
	          Toolbarbutton t = (Toolbarbutton) c;
	          t.setDisabled(true);
	        } else if (c instanceof Button) {
	          Button t = (Button) c;
	          t.setDisabled(status);
	        }

	        if (c.getChildren() != null && !c.getChildren().isEmpty()) {
	          blockedChildren(c.getChildren(), status);
	        }
	      }
	  			
	    }
}
