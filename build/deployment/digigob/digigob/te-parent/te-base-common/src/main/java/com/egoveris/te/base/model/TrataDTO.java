package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.te.model.model.TipoReservaDTO;

/**
 * Motivo al que hace referencia uno o mas expedientes.
 *
 * @author rgalloci
 *
 */
public class TrataDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(TrataDTO.class);

	private static final long serialVersionUID = 7358136193913860265L;

	/**
	 * Indica que pueden seguirse creando instancias de este tipo de documento.
	 */
	public static final String ACTIVO = "ALTA";
	public static final String ESTADO_INACTIVO = "BAJA";
	public static final Boolean MANUAL = true;

	private Long id;
	private String codigoTrata;

	public void setAcronimoDocumento(String acronimoDocumento) {
		this.acronimoDocumento = acronimoDocumento;
	}

	private String estado;
	private TipoReservaDTO tipoReserva;
	private List<TrataReparticionDTO> reparticionesTrata = new ArrayList<TrataReparticionDTO>();
	private Set<TrataTipoDocumentoDTO> listaTrataTipoDocumento = new TreeSet<TrataTipoDocumentoDTO>();
	private Boolean esAutomatica;
	private Boolean esManual;
	private Boolean habilitado = true;
	private Boolean esInterno;
	private Boolean esExterno;
	private String claveTad;
	private Boolean esNotificableTad;
	private Boolean notificableJMS;
	private Boolean esEnvioAutomaticoGT;
	private List<MetadataDTO> datoVariable = new ArrayList<MetadataDTO>();
	private String descripcion;
	private String acronimoDocumento;
	private Integer tiempoResolucion;
	private String workflow;

	private String tipoActuacion;
	private Boolean integracionSisExt;
	private Boolean integracionAFJG;

	private String tipoTramite;

	private List<TrataTipoResultadoDTO> tipoResultadosTrata;
	private Integer tipoCarga;

	/**
	 * @return the tipoCarga
	 */
	public Integer getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga
	 *            the tipoCarga to set
	 */
	public void setTipoCarga(Integer tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<MetadataDTO> getDatoVariable() {
		return datoVariable;
	}

	public void setDatoVariable(List<MetadataDTO> datoVariable) {
		this.datoVariable = datoVariable;
	}

	public Boolean isNotificableJMS() {
		return notificableJMS;
	}

	public void setNotificableJMS(Boolean notificableJMS) {
		this.notificableJMS = notificableJMS;
	}

	public List<TrataReparticionDTO> getReparticionesTrata() {
		return reparticionesTrata;
	}

	public void setReparticionesTrata(List<TrataReparticionDTO> reparticionesTrata) {
		this.reparticionesTrata = reparticionesTrata;
	}

	public Boolean getEsAutomatica() {
		return esAutomatica;
	}

	public void setEsAutomatica(Boolean esAutomatica) {
		this.esAutomatica = esAutomatica;
	}

	public Boolean getEsManual() {
		return esManual;
	}

	public void setEsManual(Boolean esManual) {
		this.esManual = esManual;
	}

	public Boolean getEsExterno() {
		return esExterno;
	}

	public void setEsExterno(Boolean esExterno) {
		this.esExterno = esExterno;
	}

	public Boolean getEsInterno() {
		return esInterno;
	}

	public void setEsInterno(Boolean esInterno) {
		this.esInterno = esInterno;
	}

	public Set<TrataTipoDocumentoDTO> getListaTrataTipoDocumento() {
		return listaTrataTipoDocumento;
	}

	public void setListaTrataTipoDocumento(Set<TrataTipoDocumentoDTO> listaTrataTipoDocumento) {
		this.listaTrataTipoDocumento = listaTrataTipoDocumento;
	}

	public Boolean getHabilitado() {
		if (logger.isDebugEnabled()) {
			logger.debug("getHabilitado() - start");
		}

		if (getEstado().equals(ACTIVO)) {
			setHabilitado(true);
		} else {
			setHabilitado(false);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getHabilitado() - end - return value={}", habilitado);
		}
		return habilitado;
	}

	public void setHabilitado(Boolean Habilitado) {
		this.habilitado = Habilitado;
	}

	public TipoReservaDTO getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(TipoReservaDTO tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAcronimoDocumento() {
		return this.acronimoDocumento;
	}

	public Integer getTiempoResolucion() {
		return this.tiempoResolucion;
	}

	public void setTiempoResolucion(Integer tiempoResolucion) {
		this.tiempoResolucion = tiempoResolucion;
	}

	public String getClaveTad() {
		return claveTad;
	}

	public void setClaveTad(String claveTad) {
		this.claveTad = claveTad;
	}

	public Boolean getEsNotificableTad() {
		return esNotificableTad;
	}

	public void setEsNotificableTad(Boolean esNotificableTad) {
		this.esNotificableTad = esNotificableTad;
	}

	public Boolean getEsEnvioAutomaticoGT() {
		return esEnvioAutomaticoGT;
	}

	public void setEsEnvioAutomaticoGT(Boolean esEnvioAutomaticoGT) {
		this.esEnvioAutomaticoGT = esEnvioAutomaticoGT;
	}

	public Boolean getIntegracionSisExt() {
		return integracionSisExt;
	}

	public void setIntegracionSisExt(Boolean integracionSisExt) {
		this.integracionSisExt = integracionSisExt;
	}

	public Boolean getIntegracionAFJG() {
		return integracionAFJG;
	}

	public void setIntegracionAFJG(Boolean integracionAFJG) {
		this.integracionAFJG = integracionAFJG;
	}

	// HAY CASOS EN QUE EL FOR TE DA VALORES NULL ... OCURRE AL EJECUTAR LAS
	// MIGRACIONES
	public List<TrataReparticionDTO> getReparticionesRectoras() {
		if (logger.isDebugEnabled()) {
			logger.debug("getReparticionesRectoras() - start");
		}

		List<TrataReparticionDTO> reparticionesRectoras = new ArrayList<TrataReparticionDTO>();

		for (TrataReparticionDTO trata : reparticionesTrata) {
			if (trata != null) {
				if (trata.getReserva())
					reparticionesRectoras.add(trata);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getReparticionesRectoras() - end - return value={}", reparticionesRectoras);
		}
		return reparticionesRectoras;
	}

	public TrataReparticionDTO obtenerTrataRep(String codigoTrata, List<TrataReparticionDTO> reparticiones) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTrataRep(codigoTrata={}, reparticiones={}) - start", codigoTrata, reparticiones);
		}

		for (TrataReparticionDTO t : reparticiones) {
			if (t.getCodigoReparticion().equals(codigoTrata)) {
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerTrataRep(String, List<TrataReparticionDTO>) - end - return value={}", t);
				}
				return t;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTrataRep(String, List<TrataReparticionDTO>) - end - return value={null}");
		}
		return null;
	}

	public void fusionarTratas(TrataReparticionDTO t, TrataReparticionDTO tNueva) {
		if (logger.isDebugEnabled()) {
			logger.debug("fusionarTratas(t={}, tNueva={}) - start", t, tNueva);
		}

		if (!tNueva.getReserva() && t.getReserva()) {
			tNueva.setReserva(true);
		}

		if (!tNueva.getHabilitacion() && t.getHabilitacion()) {
			tNueva.setHabilitacion(true);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("fusionarTratas(TrataReparticionDTO, TrataReparticionDTO) - end");
		}
	}

	public boolean reemplazarReparticion(String codRepViejo, String codRepNuevo) {
		if (logger.isDebugEnabled()) {
			logger.debug("reemplazarReparticion(codRepViejo={}, codRepNuevo={}) - start", codRepViejo, codRepNuevo);
		}

		for (TrataReparticionDTO t : getReparticionesTrata()) {
			if (t != null) {
				if (t.getCodigoReparticion() != null && t.getCodigoReparticion().equals(codRepViejo)
						&& contieneReparticion(codRepNuevo)) {
					TrataReparticionDTO t2 = obtenerTrataRep(codRepNuevo, getReparticionesTrata());
					fusionarTratas(t, t2);
					getReparticionesTrata().remove(t);

					if (logger.isDebugEnabled()) {
						logger.debug("reemplazarReparticion(String, String) - end - return value={}", true);
					}
					return true;
				}
				if (t.getCodigoReparticion() != null && t.getCodigoReparticion().equals(codRepViejo)) {
					t.setCodigoReparticion(codRepNuevo);

					if (logger.isDebugEnabled()) {
						logger.debug("reemplazarReparticion(String, String) - end - return value={}", true);
					}
					return true;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reemplazarReparticion(String, String) - end - return value={}", false);
		}
		return false;
	}

	public boolean reemplazarReparticionRectora(String codRepViejo, String codRepNuevo) {
		if (logger.isDebugEnabled()) {
			logger.debug("reemplazarReparticionRectora(codRepViejo={}, codRepNuevo={}) - start", codRepViejo,
					codRepNuevo);
		}

		for (TrataReparticionDTO t : getReparticionesRectoras()) {
			if (t != null) {
				if (t.getCodigoReparticion().equals(codRepViejo) && contieneReparticionRectora(codRepNuevo)) {
					TrataReparticionDTO t2 = obtenerTrataRep(codRepNuevo, getReparticionesTrata());
					fusionarTratas(t, t2);
					getReparticionesRectoras().remove(t);

					if (logger.isDebugEnabled()) {
						logger.debug("reemplazarReparticionRectora(String, String) - end - return value={}", true);
					}
					return true;
				}
				if (t.getCodigoReparticion() != null && t.getCodigoReparticion().equals(codRepViejo)) {
					t.setCodigoReparticion(codRepNuevo);

					if (logger.isDebugEnabled()) {
						logger.debug("reemplazarReparticionRectora(String, String) - end - return value={}", true);
					}
					return true;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("reemplazarReparticionRectora(String, String) - end - return value={}", false);
		}
		return false;
	}

	public boolean contieneReparticionRectora(String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("contieneReparticionRectora(codigoReparticion={}) - start", codigoReparticion);
		}

		for (TrataReparticionDTO t : getReparticionesRectoras()) {
			if (t != null) {
				if (t.getCodigoReparticion() != null && t.getCodigoReparticion().equals(codigoReparticion)) {
					if (logger.isDebugEnabled()) {
						logger.debug("contieneReparticionRectora(String) - end - return value={}", true);
					}
					return true;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("contieneReparticionRectora(String) - end - return value={}", false);
		}
		return false;
	}

	public boolean contieneReparticion(String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("contieneReparticion(codigoReparticion={}) - start", codigoReparticion);
		}

		for (TrataReparticionDTO t : getReparticionesTrata()) {
			if (t != null) {
				if (t.getCodigoReparticion() != null && t.getCodigoReparticion().equals(codigoReparticion)) {
					if (logger.isDebugEnabled()) {
						logger.debug("contieneReparticion(String) - end - return value={}", true);
					}
					return true;
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("contieneReparticion(String) - end - return value={}", false);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Trata [").append(((id != null) ? ("id=" + id + ", \n") : ""))
				.append("workflow=" + getWorkflow() + ", \n")
				.append(((codigoTrata != null) ? ("codigoTrata=" + codigoTrata + ", \n") : ""))
				.append(((estado != null) ? ("estado=" + estado + ", \n") : ""))
				.append(((tipoReserva != null) ? ("tipoReserva=" + tipoReserva + ", \n") : ""))
				.append(((reparticionesTrata != null) ? ("reparticionesTrata=" + reparticionesTrata + ", \n") : ""))
				.append(((listaTrataTipoDocumento != null)
						? ("listaTrataTipoDocumento=" + listaTrataTipoDocumento + ", \n") : ""))
				.append(((esAutomatica != null) ? ("esAutomatica=" + esAutomatica + ", \n") : ""))
				.append(((esManual != null) ? ("esManual=" + esManual + ", \n") : ""))
				.append(((esInterno != null) ? ("esInterna=" + esInterno + ", \n") : ""))
				.append(((esExterno != null) ? ("esExterna=" + esExterno + ", \n") : ""))
				.append(((habilitado != null) ? ("habilitado=" + habilitado + ", \n") : ""))
				.append(((datoVariable != null) ? ("datoVariable=" + datoVariable + ", \n") : ""))
				.append(((descripcion != null) ? ("descripcion=" + descripcion) : ""))
				.append(((tiempoResolucion != null) ? ("tiempoResolucion (d�as habiles)=" + tiempoResolucion) : ""))
				.append(((esNotificableTad != null) ? ("esNotificableTad=" + esNotificableTad + ", \n") : ""))
				.append(((esEnvioAutomaticoGT != null) ? ("esEnvioAutomaticoGT=" + esEnvioAutomaticoGT + ", \n") : ""))
				.append(((claveTad != null) ? ("claveTad=" + claveTad + ", \n") : ""))
				.append(((integracionSisExt != null) ? ("integracionSisExt=" + integracionSisExt + ", \n") : ""))
				.append(((integracionAFJG != null) ? ("integracionAFJG=" + integracionAFJG + ", \n") : "")).append("]");

		return sb.toString();
	}

	public boolean codigoReparticionTienePermisoDeReserva(String codigoReparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("codigoReparticionTienePermisoDeReserva(codigoReparticion={}) - start", codigoReparticion);
		}

		for (TrataReparticionDTO t : this.getReparticionesTrata()) {
			if (t != null && t.getCodigoReparticion().equalsIgnoreCase(codigoReparticion)) {
				if (logger.isDebugEnabled()) {
					logger.debug("codigoReparticionTienePermisoDeReserva(String) - end - return value={}", true);
				}
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("codigoReparticionTienePermisoDeReserva(String) - end - return value={}", false);
		}
		return false;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public List<TrataTipoResultadoDTO> getTipoResultadosTrata() {
		if (tipoResultadosTrata == null) {
			tipoResultadosTrata = new ArrayList<>();
		}

		return tipoResultadosTrata;
	}

	public void setTipoResultadosTrata(List<TrataTipoResultadoDTO> tipoResultadosTrata) {
		this.tipoResultadosTrata = tipoResultadosTrata;
	}
}
