package com.egoveris.deo.model.model;

/**
 * Establece los parámetros de entrada requeridos para generar un documento con
 * rectificación Esta clase extiende de RequestExternalGenerarTarea para no
 * realizar la misma lógica.
 * 
 * - nroSadeRectificacion: Número de Sade que hace referencia al documento 1. -
 * textoRectificacion: Texto de rectificación que se va agregar al documento 1.
 * - pagContexto: Establece en que página se va agregar el texto de
 * rectificación. "primera" / "ultima" / "todas" - marcaAgua: Establece si se
 * agrega la marca de agua en el documento 1 - nombreImagen: Establece el nombre
 * de la imagen que se va a stampar en el documento 1. - //NOTA por ahora solo
 * tiene asociada una sola imagen "registroCivil"
 */

public class RequestExternalGenerarTareaConRectificacion extends RequestExternalGenerarTarea {

  private static final long serialVersionUID = -4214407125619941551L;
  private String nroSadeRectificacion;
  private String textoRectificacion;
  private String textoMarginacion;
  private String textoTitulo;
  private PagContexto pagContexto;
  private boolean marcaAgua;
  private NombreImagen nombreImagen;

  public enum NombreImagen {
    PREVISUALIZACION, RECTIFICACION_RC, ANULACION
  };

  public enum PagContexto {
    TODAS, PRIMERA, ULTIMA
  };

  public String getNroSadeRectificacion() {
    return nroSadeRectificacion;
  }

  public void setNroSadeRectificacion(String nroSadeRectificacion) {
    this.nroSadeRectificacion = nroSadeRectificacion;
  }

  public String getTextoRectificacion() {
    return textoRectificacion;
  }

  public void setTextoRectificacion(String textoRectificacion) {
    this.textoRectificacion = textoRectificacion;
  }

  public boolean getMarcaAgua() {
    return marcaAgua;
  }

  public void setMarcaAgua(boolean marcaAgua) {
    this.marcaAgua = marcaAgua;
  }

  public NombreImagen getNombreImagen() {
    return nombreImagen;
  }

  public void setNombreImagen(NombreImagen nombreImagen) {
    this.nombreImagen = nombreImagen;
  }

  public PagContexto getPagContexto() {
    return pagContexto;
  }

  public void setPagContexto(PagContexto pagContexto) {
    this.pagContexto = pagContexto;
  }

  public String getTextoMarginacion() {
    return textoMarginacion;
  }

  public void setTextoMarginacion(String textoMarginacion) {
    this.textoMarginacion = textoMarginacion;
  }

  public String getTextoTitulo() {
    return textoTitulo;
  }

  public void setTextoTitulo(String textoTitulo) {
    this.textoTitulo = textoTitulo;
  }

}
