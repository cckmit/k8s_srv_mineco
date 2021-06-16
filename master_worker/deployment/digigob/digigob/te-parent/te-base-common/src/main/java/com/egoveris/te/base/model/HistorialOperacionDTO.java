
package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HistorialOperacionDTO implements Serializable, Comparable<HistorialOperacionDTO> {
	private static final Logger logger = LoggerFactory.getLogger(HistorialOperacionDTO.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -3762107384789954953L;

	private Integer id;

	private String tipoOperacion;

	private Date fechaOperacion;

	private String usuario;

	/**
	 * Desnormalizacion de detalles atributos nuevos
	 * 
	 */
	private String expediente;
	private Long idSolicitud;
	private Long idExpedienteDetalle;
	private Date fechaDetalle;
	private String grupoSeleccionado;
	private String esSectorDestino;
	private String usuarioDestino;
	private String idListDestinatarios;
	private String destinatarioDetalle;
	private String usuarioAnterior;
	private String estadoSeleccionado;
	private String esUsuarioDestino;
	private String grupoAnterior;
	private String usuarioProductorInfo;
	private String sectorDestino;
	private String usuarioOrigen;
	private String sistemaApoderado;
	private String reparticionUsuario;
	private String tipoOperacionDetalle;
	private String destino;
	private String motivo;
	private String estadoAnterior;
	private String tareaGrupal;
	private String loggedUsername;
	private String esReparticionDestino;
	private String estado;
	private String usuarioSeleccionado;
	private String reparticionDestino;
	private String descripcion;
	private String sectorUsuario;
	private String resultado;
	private String latitude;
	private String longitude;

	/**
	 * 
	 * Fin Atributos desnormalizacion de detalles.
	 */

	private Long idExpediente;

	public String getDestinatario() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDestinatario() - start");
		}

		String returnString = this.getDestinatarioDetalle();
		if (logger.isDebugEnabled()) {
			logger.debug("getDestinatario() - end - return value={}", returnString);
		}
		return returnString;
	}

	private Map<String, String> detalleOperacion;

	// public String getMotivo(){
	// return detalleOperacion.get("motivo");
	// }
	//
	// public String getDestinatario(){
	// return detalleOperacion.get("destinatario");
	// }
	//
	// public String getEstado(){
	// return detalleOperacion.get("estado");
	// }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Map<String, String> getDetalleOperacion() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDetalleOperacion() - start");
		}

		ConvertirHistorialDetalle c = new ConvertirHistorialDetalle(null, this);
		Map<String, String> returnMap = c.getDetalleOperacion();
		if (logger.isDebugEnabled()) {
			logger.debug("getDetalleOperacion() - end - return value={}", returnMap);
		}
		return returnMap;

	}

	public void setDetalleOperacion(Map<String, String> detalleOperacion) {
		if (logger.isDebugEnabled()) {
			logger.debug("setDetalleOperacion(detalleOperacion={}) - start", detalleOperacion);
		}

		ConvertirHistorialDetalle c = new ConvertirHistorialDetalle(detalleOperacion, this);
		this.detalleOperacion = c.getDetalleOperacion();

		if (logger.isDebugEnabled()) {
			logger.debug("setDetalleOperacion(Map<String,String>) - end");
		}
	}

	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("HistorialOperacion [").append((id != null ? "id=" + id + ", " : ""))
				.append((tipoOperacion != null ? "tipoOperacion=" + tipoOperacion + ", " : ""))
				.append((fechaOperacion != null ? "fechaOperacion=" + fechaOperacion + ", " : ""))
				.append((usuario != null ? "usuario=" + usuario + ", " : ""))
				.append((idExpediente != null ? "idExpediente=" + idExpediente + ", " : ""))
				.append((detalleOperacion != null ? "detalleOperacion=" + detalleOperacion : "")).append("]");

		return sb.toString();
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public Long getIdExpedienteDetalle() {
		return idExpedienteDetalle;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaDetalle() {
		return fechaDetalle;
	}

	public String getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public String getUsuarioDestino() {
		return usuarioDestino;
	}

	public String getIdListDestinatarios() {
		return idListDestinatarios;
	}

	public String getUsuarioAnterior() {
		return usuarioAnterior;
	}

	public String getEstadoSeleccionado() {
		return estadoSeleccionado;
	}

	public String getEsUsuarioDestino() {
		return esUsuarioDestino;
	}

	public String getGrupoAnterior() {
		return grupoAnterior;
	}

	public String getUsuarioProductorInfo() {
		return usuarioProductorInfo;
	}

	public String getSectorDestino() {
		return sectorDestino;
	}

	public String getUsuarioOrigen() {
		return usuarioOrigen;
	}

	public String getSistemaApoderado() {
		return sistemaApoderado;
	}

	public String getReparticionUsuario() {
		return reparticionUsuario;
	}

	public String getTipoOperacionDetalle() {
		return tipoOperacionDetalle;
	}

	public String getDestino() {
		return destino;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public String getTareaGrupal() {
		return tareaGrupal;
	}

	public String getLoggedUsername() {
		return loggedUsername;
	}

	public String getEsReparticionDestino() {
		return esReparticionDestino;
	}

	public String getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public void setIdExpedienteDetalle(Long idExpedienteDetalle) {
		this.idExpedienteDetalle = idExpedienteDetalle;
	}

	public void setFechaDetalle(Date fechaDetalle) {
		this.fechaDetalle = fechaDetalle;
	}

	public void setGrupoSeleccionado(String grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
	}

	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}

	public void setIdListDestinatarios(String idListDestinatarios) {
		this.idListDestinatarios = idListDestinatarios;
	}

	public void setUsuarioAnterior(String usuarioAnterior) {
		this.usuarioAnterior = usuarioAnterior;
	}

	public void setEstadoSeleccionado(String estadoSeleccionado) {
		this.estadoSeleccionado = estadoSeleccionado;
	}

	public void setEsUsuarioDestino(String esUsuarioDestino) {
		this.esUsuarioDestino = esUsuarioDestino;
	}

	public void setGrupoAnterior(String grupoAnterior) {
		this.grupoAnterior = grupoAnterior;
	}

	public void setUsuarioProductorInfo(String usuarioProductorInfo) {
		this.usuarioProductorInfo = usuarioProductorInfo;
	}

	public void setSectorDestino(String sectorDestino) {
		this.sectorDestino = sectorDestino;
	}

	public void setUsuarioOrigen(String usuarioOrigen) {
		this.usuarioOrigen = usuarioOrigen;
	}

	public void setSistemaApoderado(String sistemaApoderado) {
		this.sistemaApoderado = sistemaApoderado;
	}

	public void setReparticionUsuario(String reparticionUsuario) {
		this.reparticionUsuario = reparticionUsuario;
	}

	public void setTipoOperacionDetalle(String tipoOperacionDetalle) {
		this.tipoOperacionDetalle = tipoOperacionDetalle;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public void setTareaGrupal(String tareaGrupal) {
		this.tareaGrupal = tareaGrupal;
	}

	public void setLoggedUsername(String loggedUsername) {
		this.loggedUsername = loggedUsername;
	}

	public void setEsReparticionDestino(String esReparticionDestino) {
		this.esReparticionDestino = esReparticionDestino;
	}

	public void setUsuarioSeleccionado(String usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public void setDestinatarioDetalle(String destinatarioDetalle) {
		this.destinatarioDetalle = destinatarioDetalle;
	}

	public String getDestinatarioDetalle() {
		return destinatarioDetalle;
	}

	public String getEsSectorDestino() {
		return esSectorDestino;
	}

	public void setEsSectorDestino(String esSectorDestino) {
		this.esSectorDestino = esSectorDestino;
	}

	public String getReparticionDestino() {
		return reparticionDestino;
	}

	public void setReparticionDestino(String reparticionDestino) {
		this.reparticionDestino = reparticionDestino;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setSectorUsuario(String sectorUsuario) {
		this.sectorUsuario = sectorUsuario;
	}

	public String getSectorUsuario() {
		return sectorUsuario;
	}
	
	public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

	final class ConvertirHistorialDetalle {
		private Map<String, String> detalleOperacion;
		private HistorialOperacionDTO h;

		public ConvertirHistorialDetalle(Map<String, String> detalleOperacion, HistorialOperacionDTO histin) {
			this.detalleOperacion = detalleOperacion;
			this.h = histin;
			if (this.detalleOperacion != null && this.detalleOperacion.size() > 0) {
				this.rellenarDetalle(this.detalleOperacion, this.h);
				this.rellenarVariables(this.h);

			} else {
				this.detalleOperacion = new HashMap<String, String>();
				this.rellenarDetalle(this.detalleOperacion, this.h);

			}

		}

		private void rellenarVariables(HistorialOperacionDTO h2) {
			if (logger.isDebugEnabled()) {
				logger.debug("rellenarVariables(h2={}) - start", h2);
			}

			this.detalleOperacion.put("grupoSeleccionado", h2.getGrupoSeleccionado());
			this.detalleOperacion.put("esSectorDestino", h2.getEsSectorDestino());
			this.detalleOperacion.put("usuarioDestino", h2.getUsuarioDestino());
			this.detalleOperacion.put("idListDestinatarios", h2.getIdListDestinatarios());
			this.detalleOperacion.put("destinatario", h2.getDestinatarioDetalle());
			if (h2.getIdExpedienteDetalle() != null) {
				this.detalleOperacion.put("idExpedienteElectronico", h2.getIdExpedienteDetalle().toString());
			}
			if (h2.getIdSolicitud() != null) {
				this.detalleOperacion.put("idSolicitud", h2.getIdSolicitud().toString());
			}
			this.detalleOperacion.put("reparticionDestino", h2.getReparticionDestino());
			this.detalleOperacion.put("usuarioAnterior", h2.getUsuarioAnterior());
			this.detalleOperacion.put("esUsuarioDestino", h2.getUsuarioDestino());
			this.detalleOperacion.put("grupoAnterior", h2.getGrupoAnterior());
			this.detalleOperacion.put("usuarioProductorInfo", h2.getUsuarioProductorInfo());
			this.detalleOperacion.put("estadoSeleccionado", h2.getEstadoSeleccionado());
			this.detalleOperacion.put("sectorDestino", h2.getSectorDestino());
			this.detalleOperacion.put("usuarioOrigen", h2.getUsuarioOrigen());
			this.detalleOperacion.put("descripcion", h2.getDescripcion());
			this.detalleOperacion.put("loggedUsername", h2.getLoggedUsername());
			this.detalleOperacion.put("reparticionUsuario", h2.getReparticionUsuario());

			this.detalleOperacion.put("tipoOperacion", h2.getTipoOperacionDetalle());
			this.detalleOperacion.put("tipoOperacion", h2.getTipoOperacion());
			this.detalleOperacion.put("destino", h2.getDestino());
			this.detalleOperacion.put("motivo", h2.getMotivo());
			this.detalleOperacion.put("estadoAnterior", h2.getEstadoAnterior());
			this.detalleOperacion.put("tareaGrupal", h2.getTareaGrupal());
			this.detalleOperacion.put("esReparticionDestino", h2.getEsReparticionDestino());
			if (h2.getFechaDetalle() != null) {
				this.detalleOperacion.put("fecha", h2.getFechaOperacion().toString());
			}
			this.detalleOperacion.put("estado", h2.getEstado());
			this.detalleOperacion.put("usuarioSeleccionado", h2.getUsuarioSeleccionado());
			this.detalleOperacion.put("sectorUsuario", h2.getSectorUsuario());

			if (logger.isDebugEnabled()) {
				logger.debug("rellenarVariables(HistorialOperacion) - end");
			}
		}

		private void rellenarDetalle(Map<String, String> d, HistorialOperacionDTO h2) {
			if (logger.isDebugEnabled()) {
				logger.debug("rellenarDetalle(d={}, h2={}) - start", d, h2);
			}

			/*
			 * this.h.setDescripcion(d.get("descripcion"));
			 * this.h.setDestinatarioDetalle(d.get("destinatario"));
			 */

			this.h.setGrupoSeleccionado(d.get("grupoSeleccionado"));
			this.h.setEsSectorDestino(d.get("esSectorDestino"));
			this.h.setUsuarioDestino(d.get("usuarioDestino"));
			this.h.setIdListDestinatarios(d.get("idListDestinatarios"));
			if (d.get("idExpedienteElectronico") != null) {
				this.h.setIdExpedienteDetalle(Long.valueOf(d.get("idExpedienteElectronico")));
			}
			this.h.setDestinatarioDetalle(d.get("destinatario"));
			if (d.get("idSolicitud") != null) {
				this.h.setIdSolicitud(Long.valueOf(d.get("idSolicitud")));
			}
			this.h.setReparticionDestino(d.get("reparticionDestino"));
			this.h.setUsuarioAnterior(d.get("usuarioAnterior"));
			this.h.setEstadoSeleccionado(d.get("estadoSeleccionado"));
			this.h.setSectorDestino(d.get("sectorDestino"));
			this.h.setUsuarioOrigen(d.get("usuarioOrigen"));
			this.h.setDescripcion(d.get("descripcion"));
			this.h.setLoggedUsername(d.get("loggedUsername"));
			this.h.setReparticionUsuario(d.get("reparticionUsuario"));
			this.h.setTipoOperacion(d.get("tipoOperacion"));
			this.h.setTipoOperacionDetalle(d.get("tipoOperacion"));
			this.h.setDestino(d.get("destino"));
			this.h.setMotivo(d.get("motivo"));
			this.h.setEstadoAnterior(d.get("estadoAnterior"));
			this.h.setTareaGrupal(d.get("tareaGrupal"));
			this.h.setEsReparticionDestino(d.get("esReparticionDestino"));
			this.h.setFechaDetalle(h.getFechaOperacion());
			this.h.setEstado(d.get("estado"));
			this.h.setUsuarioSeleccionado(d.get("usuarioSeleccionado"));
			this.h.setSectorUsuario(d.get("sectorUsuario"));

			if (logger.isDebugEnabled()) {
				logger.debug("rellenarDetalle(Map<String,String>, HistorialOperacion) - end");
			}
		}

		public void setDetalleOperacion(Map<String, String> detalleOperacion) {
			this.detalleOperacion = detalleOperacion;
		}

		public Map<String, String> getDetalleOperacion() {
			return detalleOperacion;
		}

		public void setH(HistorialOperacionDTO h) {
			this.h = h;
		}

		public HistorialOperacionDTO getH() {
			return h;
		}

	}

	@Override
	public int compareTo(HistorialOperacionDTO o) {
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(o={}) - start", o);
		}

		int returnint = o.getFechaOperacion().compareTo(this.getFechaOperacion());
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(HistorialOperacion) - end - return value={}", returnint);
		}
		return returnint;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
