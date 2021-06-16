package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestGenerarDocumento implements Serializable {

  private static final long serialVersionUID = 6154939233007929139L;

  private String contenido;
  private String numero;
  private String codigoReparticion;
  private String reparticion;
  private String sector;
  private String usuario;
  private String nombreYApellido;
  private String nombreAplicacion;
  private String nombreAplicacionIniciadora;
  private TipoDocumentoDTO tipoDocumentoGedo;
  private String motivo;
  private String mensaje;
  private String cargo;
  private String nombreArchivo;
  private String tipoArchivo;
  private ComunicacionDTO comunicacion;
  private Integer idComunicacionRespondida;
  private boolean tieneToken;
  private byte[] data;
  private byte[] dataTemplateTranformada;
  private NumeracionEspecialDTO numeroEspecial;
  private List<DocumentoMetadataDTO> documentoMetadata;
  private String usuarioApoderador;
  private String numeroSadePapel;
  private String usuarioIniciador;
  private String usuarioFirmante;
  private String usuarioSupervisado;
  private HashMap<String, Object> camposTemplate;
  private List<ArchivoEmbebidoDTO> listaArchivosEmbebidos;
  private Integer idTransaccion;
  private List<String> listaUsuariosReservados;
  private List<String> listaUsuariosDestinatarios;
  private List<String> listaUsuariosDestinatariosCopia;
  private List<String> listaUsuariosDestinatariosCopiaOculta;
  private List<UsuarioExternoDTO> listaUsuariosDestinatariosExternos;
  private String mensajeDestinatario;
  private ComunicacionDTO comunicacionRespondida;
  private String idGuardaDocumental;
  private DocumentoDTO documento;
  private String numeroEspecialCompleto;
  private Integer idDestinatario;
  private String textoQR;

  private String idPublicable;
  /**
   * Identificador del workflow usado para generar el documento o null si no
   * aplica o no se usÃ³ ninguno
   */
  private String workflowId = null;
  private String usuarioActual;

  /**
   * Usuario productor, revisor o firmante.
   */
  private String usuarioReceptorTarea;
  private List<String> receptoresAvisoFirma;
  /**
   * Mensaje al usuario productor o al usuario revisor.
   */
  private String mensajeUsuario;

  /**
   * Número de usuarios firmantes.
   */
  private String numeroFirmas;

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  public boolean isTieneToken() {
    return tieneToken;
  }

  public void setTieneToken(boolean tieneToken) {
    this.tieneToken = tieneToken;
  }

  public String getCodigoReparticion() {
    return codigoReparticion;
  }

  public void setCodigoReparticion(String codigoReparticion) {
    this.codigoReparticion = codigoReparticion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getNombreAplicacion() {
    return nombreAplicacion;
  }

  public void setNombreAplicacion(String nombreAplicacion) {
    this.nombreAplicacion = nombreAplicacion;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public TipoDocumentoDTO getTipoDocumentoGedo() {
    return tipoDocumentoGedo;
  }

  public void setTipoDocumentoGedo(TipoDocumentoDTO tipoDocumentoGedo) {
    this.tipoDocumentoGedo = tipoDocumentoGedo;
  }

  public NumeracionEspecialDTO getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(NumeracionEspecialDTO numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public List<DocumentoMetadataDTO> getDocumentoMetadata() {
    return documentoMetadata;
  }

  public void setDocumentoMetadata(List<DocumentoMetadataDTO> documentoMetadata) {
    this.documentoMetadata = documentoMetadata;
  }

  public String getWorkflowId() {
    return workflowId;
  }

  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }

  public String getUsuarioActual() {
    return usuarioActual;
  }

  public void setUsuarioActual(String usuarioActual) {
    this.usuarioActual = usuarioActual;
  }

  public String getUsuarioReceptorTarea() {
    return usuarioReceptorTarea;
  }

  public void setUsuarioReceptorTarea(String usuarioReceptorTarea) {
    this.usuarioReceptorTarea = usuarioReceptorTarea;
  }

  public List<String> getReceptoresAvisoFirma() {
    return receptoresAvisoFirma;
  }

  public void setReceptoresAvisoFirma(List<String> receptoresAvisoFirma) {
    this.receptoresAvisoFirma = receptoresAvisoFirma;
  }

  public String getMensajeUsuario() {
    return mensajeUsuario;
  }

  public void setMensajeUsuario(String mensajeUsuario) {
    this.mensajeUsuario = mensajeUsuario;
  }

  public String getNumeroFirmas() {
    return numeroFirmas;
  }

  public void setNumeroFirmas(String numeroFirmas) {
    this.numeroFirmas = numeroFirmas;
  }

  public String getUsuarioApoderador() {
    return usuarioApoderador;
  }

  public void setUsuarioApoderador(String usuarioApoderador) {
    this.usuarioApoderador = usuarioApoderador;
  }

  public void setTipoArchivo(String tipoArchivo) {
    this.tipoArchivo = tipoArchivo;
  }

  public String getTipoArchivo() {
    return tipoArchivo;
  }

  public String getNumeroSadePapel() {
    return numeroSadePapel;
  }

  public void setNumeroSadePapel(String numeroSadePapel) {
    this.numeroSadePapel = numeroSadePapel;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public String getNombreAplicacionIniciadora() {
    return nombreAplicacionIniciadora;
  }

  public void setNombreAplicacionIniciadora(String nombreAplicacionIniciadora) {
    this.nombreAplicacionIniciadora = nombreAplicacionIniciadora;
  }

  public Map<String, Object> getCamposTemplate() {
    return camposTemplate;
  }

  public void setCamposTemplate(HashMap<String, Object> camposTemplate) {
    this.camposTemplate = camposTemplate;
  }

  public byte[] getDataOriginal() {
    return dataTemplateTranformada;
  }

  public void setDataOriginal(byte[] dataOriginal) {
    this.dataTemplateTranformada = dataOriginal;
  }

  public List<ArchivoEmbebidoDTO> getListaArchivosEmbebidos() {
    return listaArchivosEmbebidos;
  }

  public void setListaArchivosEmbebidos(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) {
    this.listaArchivosEmbebidos = listaArchivosEmbebidos;
  }

  public Integer getIdTransaccion() {
    return idTransaccion;
  }

  public void setIdTransaccion(Integer idTransaccion) {
    this.idTransaccion = idTransaccion;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}

public List<String> getListaUsuariosDestinatarios() {
    return listaUsuariosDestinatarios;
  }

  public void setListaUsuariosDestinatarios(List<String> listaUsuariosDestinatarios) {
    this.listaUsuariosDestinatarios = listaUsuariosDestinatarios;
  }

  public List<String> getListaUsuariosDestinatariosCopia() {
    return listaUsuariosDestinatariosCopia;
  }

  public void setListaUsuariosDestinatariosCopia(List<String> listaUsuariosDestinatariosCopia) {
    this.listaUsuariosDestinatariosCopia = listaUsuariosDestinatariosCopia;
  }

  public List<String> getListaUsuariosDestinatariosCopiaOculta() {
    return listaUsuariosDestinatariosCopiaOculta;
  }

  public void setListaUsuariosDestinatariosCopiaOculta(
      List<String> listaUsuariosDestinatariosCopiaOculta) {
    this.listaUsuariosDestinatariosCopiaOculta = listaUsuariosDestinatariosCopiaOculta;
  }

  public List<UsuarioExternoDTO> getListaUsuariosDestinatariosExternos() {
    return listaUsuariosDestinatariosExternos;
  }

  public void setListaUsuariosDestinatariosExternos(
      List<UsuarioExternoDTO> listaUsuariosDestinatariosExternos) {
    this.listaUsuariosDestinatariosExternos = listaUsuariosDestinatariosExternos;
  }

  public String getMensajeDestinatario() {
    return mensajeDestinatario;
  }

  public void setMensajeDestinatario(String mensajeDestinatario) {
    this.mensajeDestinatario = mensajeDestinatario;
  }

  public ComunicacionDTO getComunicacion() {
    return comunicacion;
  }

  public void setComunicacion(ComunicacionDTO comunicacion) {
    this.comunicacion = comunicacion;
  }

  public ComunicacionDTO getComunicacionRespondida() {
    return comunicacionRespondida;
  }

  public void setComunicacionRespondida(ComunicacionDTO comunicacionRespondida) {
    this.comunicacionRespondida = comunicacionRespondida;
  }

  public String getNombreYApellido() {
    return nombreYApellido;
  }

  public void setNombreYApellido(String nombreYApellido) {
    this.nombreYApellido = nombreYApellido;
  }

  public Integer getIdComunicacionRespondida() {
    return idComunicacionRespondida;
  }

  public void setIdComunicacionRespondida(Integer idComunicacionRespondida) {
    this.idComunicacionRespondida = idComunicacionRespondida;
  }

  public String getUsuarioFirmante() {
    return usuarioFirmante;
  }

  public void setUsuarioFirmante(String usuarioFirmante) {
    this.usuarioFirmante = usuarioFirmante;
  }

  public String getUsuarioSupervisado() {
    return usuarioSupervisado;
  }

  public void setUsuarioSupervisado(String usuarioSupervisado) {
    this.usuarioSupervisado = usuarioSupervisado;
  }

  public List<String> getListaUsuariosReservados() {
    return listaUsuariosReservados;
  }

  public void setListaUsuariosReservados(List<String> listaUsuariosReservados) {
    this.listaUsuariosReservados = listaUsuariosReservados;
  }

  public String getIdGuardaDocumental() {
    return idGuardaDocumental;
  }

  public void setIdGuardaDocumental(String idGuardaDocumental) {
    this.idGuardaDocumental = idGuardaDocumental;
  }

  public String getNumeroEspecialCompleto() {
    return numeroEspecialCompleto;
  }

  public void setNumeroEspecialCompleto(String numeroEspecialCompleto) {
    this.numeroEspecialCompleto = numeroEspecialCompleto;
  }

  public DocumentoDTO getDocumento() {
    return documento;
  }

  public void setDocumento(DocumentoDTO documento) {
    this.documento = documento;
  }

  public Integer getIdDestinatario() {
    return idDestinatario;
  }

  public void setIdDestinatario(Integer idDestinatario) {
    this.idDestinatario = idDestinatario;
  }

	public String getTextoQR() {
		return textoQR;
	}
	
	public void setTextoQR(String textoQR) {
		this.textoQR = textoQR;
	}

	public String getIdPublicable() {
		return idPublicable;
	}

	public void setIdPublicable(String idPublicable) {
		this.idPublicable = idPublicable;
	}


}
