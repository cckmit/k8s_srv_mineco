package com.egoveris.te.base.model;

import java.util.Date;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.base.exception.RemoveDocException;
import com.egoveris.te.base.service.iface.IExpediente;
import com.egoveris.te.base.util.BusinessFormatHelper;

/**
 * Clase que representa un expediente papel
 * 
 * @author joflores
 *
 */
public class ExpedienteTrack implements IExpediente {
	private static final Logger logger = LoggerFactory.getLogger(ExpedienteTrack.class);

	private String reparorigen;
	private String sectororigen;
	private String origen; // Interno / Externo
	private Date fecha;
	private Integer fojas;
	private Integer anio;
	private String letra;
	private String reparticionActuacion;
	private String reparticionUsuario;
	private String descripcion;
	private Integer numeroactuacion;
	private String secuencia;
	private String tipodocumento;
	private String nrodocumento;
	private String letrainicia;
	private Integer anioinicia;
	private Integer numeroactuacioninicia;
	private String reparticionactuacioninicia;
	private String secuenciainicia;
	private String calle;
	private String apellidonombrerazon;
	private String dominio;
	private String issib;
	private String denuncia;
	private Integer numerocalle;
	private String partidaabl;
	private String catastral;
	private String codigoTrata;
	private Long id;
	private String observaciones;

	public ExpedienteTrack(SolrDocument document) {
    if (document.get("descripcion") != null) {
      this.setDescripcion(((String) document.get("descripcion")).trim());
    }
    if (document.get("numeroactuacion") != null) {
      this.setNumeroactuacion(Integer.valueOf(document.get("numeroactuacion").toString().trim()));
    }
    if (document.get("secuencia") != null) {
      this.setSecuencia(((String) document.get("secuencia")).trim());
    }
    if (document.get("tipodocumento") != null) {
      this.setTipoDocumento(((String) document.get("tipodocumento")).trim());
    }
    if (document.get("nrodocumento") != null) {
      this.setNrodocumento(((String) document.get("nrodocumento")).trim());
    }
    if (document.get("letra") != null) {
      this.setLetra(((String) document.get("letra")).trim());
    }
    if (document.get("anio") != null) {
      this.setAnio((Integer) document.get("anio"));
    }
    if (document.get("reparticionusuario") != null) {
      this.setReparticionUsuario(((String) document.get("reparticionusuario")).trim());
    }
    if (document.get("reparticionactuacion") != null) {
      this.setReparticionActuacion(((String) document.get("reparticionactuacion")).trim());
    }
    if (document.get("letrainicia") != null) {
      this.setLetrainicia(((String) document.get("letrainicia")).trim());
    }
    if (document.get("anioinicia") != null) {
      this.setAnioinicia((Integer) document.get("anioinicia"));
    }
    if (document.get("numeroactuacioninicia") != null) {
      this.setNumeroactuacioninicia((Integer) document.get("numeroactuacioninicia"));
    }
    if (document.get("reparticionactuacioninicia") != null) {
      this.setReparticionactuacioninicia(((String) document.get("reparticionactuacioninicia")).trim());
    }
    if (document.get("secuenciainicia") != null) {
      this.setSecuenciainicia(((String) document.get("secuenciainicia")).trim());
    }
    if (document.get("calle") != null) {
      this.setCalle(((String) document.get("calle")).trim());
    }
    if (document.get("apellidonombrerazon") != null) {
      this.setApellidonombrerazon(((String) document.get("apellidonombrerazon")).trim());
    }
    if (document.get("dominio") != null) {
      this.setDominio(((String) document.get("dominio")).trim());
    }
    if (document.get("issib") != null) {
      this.setIssib(((String) document.get("issib")).trim());
    }
    if (document.get("denuncia") != null) {
      this.setDenuncia(((String) document.get("denuncia")).trim());
    }

    if (document.get("numerocalle") != null) {
      this.setNumerocalle(Integer.valueOf(((String) document.get("numerocalle")).trim()));
    }
    if (document.get("partidaabl") != null) {
      this.setPartidaabl(((String) document.get("partidaabl")).trim());
    }
    if (document.get("catastral") != null) {
      this.setCatastral(((String) document.get("catastral")).trim());
    }

    if (document.get("reparticionorigen") != null) {
      this.setReparorigenTrack(document.get("reparticionorigen").toString().trim());
    }

    if (document.get("sectororigen") != null) {
      this.setSectororigenTrack(document.get("sectororigen").toString().trim());
    }

    if (document.get("fecha") != null) {
      this.setFechaCreacion((Date) document.get("fecha"));
    }

    if (document.get("fojas") != null) {
      this.setFojasTrack((Integer) document.get("fojas"));
    }
    if (document.get("origen") != null) {
      this.setOrigenTrack(((String) document.get("origen")).trim());
    }

    if (document.get("extracto") != null) {
      this.setCodigoTrata(((String) document.get("extracto")).trim());
    }

    if (document.get("ID") != null) {
      this.setId((Long) document.get("ID"));
    }

    if (document.get("observaciones") != null) {
      this.setObservaciones(((String) document.get("observaciones")).trim());
    }
  }
	
	public String getReparticionOrigenTrack() {
		return reparorigen;
	}

	public String getSectorOrigenTrack() {
		return sectororigen;
	}

	public Integer getFojasTrack() {
		return fojas;
	}

	public String getOrigenTrack() {
		return origen;
	}

	public void setReparorigenTrack(String reparorigenid) {
		this.reparorigen = reparorigenid;
	}

	public void setSectororigenTrack(String sectororigenid) {
		this.sectororigen = sectororigenid;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public void setReparticionActuacion(String reparticionActuacion) {
		this.reparticionActuacion = reparticionActuacion;
	}

	public void setReparticionUsuario(String reparticionUsuario) {
		this.reparticionUsuario = reparticionUsuario;
	}

	public void setNumeroactuacion(Integer numeroactuacion) {
		this.numeroactuacion = numeroactuacion;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public void setLetrainicia(String letrainicia) {
		this.letrainicia = letrainicia;
	}

	public void setAnioinicia(Integer anioinicia) {
		this.anioinicia = anioinicia;
	}

	public void setNumeroactuacioninicia(Integer numeroactuacioninicia) {
		this.numeroactuacioninicia = numeroactuacioninicia;
	}

	public void setReparticionactuacioninicia(String reparticionactuacioninicia) {
		this.reparticionactuacioninicia = reparticionactuacioninicia;
	}

	public void setSecuenciainicia(String secuenciainicia) {
		this.secuenciainicia = secuenciainicia;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setApellidonombrerazon(String apellidonombrerazon) {
		this.apellidonombrerazon = apellidonombrerazon;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public void setIssib(String issib) {
		this.issib = issib;
	}

	public void setDenuncia(String denuncia) {
		this.denuncia = denuncia;
	}

	public void setNumerocalle(Integer numerocalle) {
		this.numerocalle = numerocalle;
	}

	public void setPartidaabl(String partidaabl) {
		this.partidaabl = partidaabl;
	}

	public void setCatastral(String catastral) {
		this.catastral = catastral;
	}

	private void setOrigenTrack(String obj) {
		this.origen = obj;
	}

	public void setFojasTrack(Integer integer) {
		this.fojas = integer;
	}

	@Override
	public void setSolicitudIniciadora(SolicitudExpedienteDTO solicitudIniciadora) {
	  //
	}

	@Override
	public void agregarDocumento(DocumentoDTO documento) {
	  //
	}

	@Override
	public void desagregarDocumento(DocumentoDTO documento) {
	  //
	}

	@Override
	public List<DocumentoDTO> getDocumentos() {
		return null;
	}

	@Override
	public void setDocumentos(List<DocumentoDTO> documentos) {
	  //
	}

	@Override
	public String getSistemaCreador() {
		return null;
	}

	@Override
	public void setSistemaCreador(String sistemaCreador) {
	  //
	}

	@Override
	public String getSistemaApoderado() {
		return null;
	}

	@Override
	public void setSistemaApoderado(String sistemaApoderado) {
	  //
	}

	@Override
	public boolean getBloqueado() {
		return false;
	}

	@Override
	public void setBloqueado(boolean bloqueado) {
	  //
	}

	@Override
	public Boolean getTramitacionLibre() {
		return false;
	}

	@Override
	public void setTramitacionLibre(Boolean tramitacionLibre) {
	  //
	}

	@Override
	public String getIdWorkflow() {
		return null;
	}

	@Override
	public void setIdWorkflow(String idWorkflow) {
	  //
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public List<ArchivoDeTrabajoDTO> getArchivosDeTrabajo() {
		return null;
	}

	@Override
	public List<ArchivoDeTrabajoDTO> obtenerArchivosDeTrabajoAcumulado(String reparticionUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerArchivosDeTrabajoAcumulado(reparticionUsuario={}) - start", reparticionUsuario);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerArchivosDeTrabajoAcumulado(String) - end - return value={null}");
		}
		
		return null;
	}

	@Override
	public void setArchivosDeTrabajo(List<ArchivoDeTrabajoDTO> archivosDeTrabajo) {
	  //
	}

	@Override
	public List<ExpedienteAsociadoEntDTO> getListaExpedientesAsociados() {
		return null;
	}

	@Override
	public void setListaExpedientesAsociados(List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados) {
	  //
	}

	@Override
	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public TrataDTO getTrata() {
		return null;
	}

	@Override
	public void setTrata(TrataDTO trata) {
	  //
	}

	@Override
	public String getUsuarioCreador() {
		return null;
	}

	@Override
	public void setUsuarioCreador(String usuarioCreador) {
	  //
	}

	@Override
	public Date getFechaCreacion() {
		return fecha;
	}

	@Override
	public void setFechaCreacion(Date fechaCreacion) {
		this.fecha = fechaCreacion;
	}

	@Override
	public String getUsuarioModificacion() {
		return null;
	}

	@Override
	public void setUsuarioModificacion(String usuarioModificacion) {
	  //
	}

	@Override
	public Date getFechaModificacion() {
		return null;
	}

	@Override
	public void setFechaModificacion(Date fechaModificacion) {
	  //
	}

	@Override
	public String getTipoDocumento() {
		return tipodocumento;
	}

	@Override
	public void setTipoDocumento(String tipoDocumento) {
		this.tipodocumento = tipoDocumento;
	}

	@Override
	public Integer getAnio() {
		return anio;
	}

	@Override
	public void setAnio(Integer anio) {
		this.anio = anio;

	}

	@Override
	public Integer getNumero() {
		return numeroactuacion;
	}

	@Override
	public void setNumero(Integer numero) {
	  //
	}

	@Override
	public String getSecuencia() {
		return secuencia;
	}

	@Override
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	@Override
	public Boolean getDefinitivo() {
		return false;
	}

	@Override
	public void setDefinitivo(Boolean definitivo) {
	  //
	}

	@Override
	public String getCodigoReparticionUsuario() {
		return reparticionUsuario;
	}

	@Override
	public void setCodigoReparticionUsuario(String codigoReparticionUsuario) {
	  //
	}

	@Override
	public String getCodigoReparticionActuacion() {
		return reparticionActuacion;
	}

	@Override
	public void setCodigoReparticionActuacion(String codigoReparticionActuacion) {
	  //
	}

	@Override
	public Boolean getEsElectronico() {
		return false;
	}

	@Override
	public void setEsElectronico(Boolean esElectronico) {
	  //
	}

	@Override
	public List<ExpedienteMetadataDTO> getMetadatosDeTrata() {
		return null;
	}

	@Override
	public void setMetadatosDeTrata(List<ExpedienteMetadataDTO> metadatosDeTrata) {
	  //
	}

	@Override
	public void agregarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoJob) {
	  //
	}

	@Override
	public void agregarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado) {
	  //
	}

	@Override
	public void eliminarDocumento(DocumentoDTO documento) {
	  //
	}

	@Override
	public void agregarMetadatosDeTrata(ExpedienteMetadataDTO espedienteMetadata) {
	  //
	}

	@Override
	public String getEstado() {
		return null;
	}

	@Override
	public void setEstado(String estado) {
	  //
	}

	@Override
	public void setEsCabeceraTC(Boolean esCabeceraTC) {
	  //
	}

	@Override
	public Boolean getEsReservado() {
		return false;
	}

	@Override
	public void setEsReservado(Boolean esReservado) {
	}

	@Override
	public String getUsuarioReserva() {
		return null;
	}

	@Override
	public void setUsuarioReserva(String usuarioReserva) {
	  //
	}

	@Override
	public Date getFechaReserva() {
		return null;
	}

	@Override
	public void setFechaReserva(Date fechaReserva) {
	  //
	}

	@Override
	public void desagregarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoTrabajo) {
	  //
	}

	@Override
	public void desagregarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsoc) {
	  //
	}

	@Override
	public boolean existenExpedientesDefinitivos() {
		return false;
	}

	@Override
	public boolean existenExpedientesFusionados() {
		return false;
	}

	@Override
	public boolean existenExpedientesTramitacion() {
		return false;
	}

	@Override
	public void removeDoc(DocumentoDTO documentoGEDO) throws RemoveDocException {
	  //
	}

	@Override
	public void removeDocsNoDefinitivos() {
	  //
	}

	@Override
	public void desasociarPorNumeroDeDoc(Integer numero) throws Exception {
	  //
	}

	@Override
	public void setFechaArchivo(Date date) {
	  //
	}

	@Override
	public void setFechaSolicitudArchivo(Date date) {
	  //
	}

	@Override
	public List<String> getCodigosDeDocumentos() {
		return null;
	}

	@Override
	public String getLetraTrack() {
		return letra;
	}

	@Override
	public String getNumeroDocumentoTrack() {
		return nrodocumento;
	}

	@Override
	public String getTipoDocumentoTrack() {
		return tipodocumento;
	}

	@Override
	public String getLetraIniciaTrack() {
		return letrainicia;
	}

	@Override
	public Integer getAnioIniciaTrack() {
		return anioinicia;
	}

	@Override
	public Integer getNumeroActuacionIniciaTrack() {
		return numeroactuacioninicia;
	}

	@Override
	public String getReparticionActuacionIniciaTrack() {
		return reparticionactuacioninicia;
	}

	@Override
	public String getSecuenciaIniciaTrack() {
		return secuenciainicia;
	}

	@Override
	public String getCalleTrack() {
		return calle;
	}

	@Override
	public String getApellidonombrerazonTrack() {
		return apellidonombrerazon;
	}

	@Override
	public String getDominioTrack() {
		return dominio;
	}

	@Override
	public String getIssibTrack() {
		return issib;
	}

	@Override
	public String getDenunciaTrack() {
		return denuncia;
	}

	@Override
	public Integer getNumerocalleTrack() {
		return numerocalle;
	}

	@Override
	public String getPartidaablTrack() {
		return partidaabl;
	}

	@Override
	public String getCatastralTrack() {
		return catastral;
	}

	public String getCodigoTrataTrack() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	@Override
	public SolicitudExpedienteDTO getSolicitudIniciadora() {
		return null;
	}

	public String getCodigoCaratula() {
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoCaratula() - start");
		}

		StringBuilder codigoCaratulaBuilder = new StringBuilder();

		if (this.letra != null) {
			codigoCaratulaBuilder.append(this.letra.toUpperCase());
		}

		if (this.secuencia == null) {
			this.secuencia = "   ";
		}

		codigoCaratulaBuilder.append("-");
		codigoCaratulaBuilder.append(this.anio);
		codigoCaratulaBuilder.append("-");
		codigoCaratulaBuilder.append(BusinessFormatHelper.completarConCerosNumActuacion(this.numeroactuacion));
		codigoCaratulaBuilder.append("-");
		codigoCaratulaBuilder.append(this.secuencia);
		codigoCaratulaBuilder.append("-");
		
		if (this.getCodigoReparticionActuacion() != null) {
			codigoCaratulaBuilder.append(this.getCodigoReparticionActuacion().toUpperCase());
		}

		codigoCaratulaBuilder.append("-");
		
		if (this.getCodigoReparticionUsuario() != null) {
			codigoCaratulaBuilder.append(this.getCodigoReparticionUsuario().toUpperCase());
		}
		
		String returnString = codigoCaratulaBuilder.toString();
		
		if (logger.isDebugEnabled()) {
			logger.debug("getCodigoCaratula() - end - return value={}", returnString);
		}
		
		return returnString;
	}

	public Boolean getEsCabeceraTC() {
		return false;
	}

	public String getObservacionesTrack() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
