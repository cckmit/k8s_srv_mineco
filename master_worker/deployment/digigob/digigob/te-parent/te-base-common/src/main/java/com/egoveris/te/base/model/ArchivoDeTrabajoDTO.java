package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Archivo de trabajo que se asocia a un expediente en tramite. No se almacena
 * en un repositorio, no se caratula y no se firma.
 * 
 * @author rgalloci
 * 
 */

public class ArchivoDeTrabajoDTO implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ArchivoDeTrabajoDTO.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 3005946162657159558L;
	private Long id;
	private String nombreArchivo;
	private byte[] dataArchivo;
	private boolean definitivo = false;
	private String usuarioAsociador;
	private Date fechaAsociacion;
	private String idTask;
	private Long idExpCabeceraTC;
	private Integer tipoReserva;
	private TipoArchivoTrabajoDTO tipoArchivoTrabajo = new TipoArchivoTrabajoDTO();
	private List<ReparticionParticipanteDTO> reparticionesParticipantes = new ArrayList<ReparticionParticipanteDTO>();
	private Set<ArchivoDeTrabajoVisualizacionDTO> permisosVisualizacion = new HashSet<ArchivoDeTrabajoVisualizacionDTO>(0);
	private String idGuardaDocumental;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public byte[] getDataArchivo() {
		return dataArchivo;
	}
	public void setDataArchivo(byte[] dataArchivo) {
		this.dataArchivo = dataArchivo;
	}
	public boolean isDefinitivo() {
		return definitivo;
	}
	public void setDefinitivo(boolean definitivo) {
		this.definitivo = definitivo;
	}
	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}
	public void setUsuarioAsociador(String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}
	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}
	public Long getIdExpCabeceraTC() {
		return idExpCabeceraTC;
	}
	public void setIdExpCabeceraTC(Long idExpCabeceraTC) {
		this.idExpCabeceraTC = idExpCabeceraTC;
	}
	public List<ReparticionParticipanteDTO> getReparticionesParticipantes() {
		return this.reparticionesParticipantes;
	}
	public void setReparticionesParticipantes(
			List<ReparticionParticipanteDTO> reparticionesParticipantes) {
		this.reparticionesParticipantes = reparticionesParticipantes;
	}
	
	public void addReparticionesParticipantes(
			List<ReparticionParticipanteDTO> reparticionesParticipantes) {
		if (logger.isDebugEnabled()) {
			logger.debug("addReparticionesParticipantes(reparticionesParticipantes={}) - start", reparticionesParticipantes);
		}

		for (int i = 0; i < reparticionesParticipantes.size(); i++) {
			this.reparticionesParticipantes.add(reparticionesParticipantes.get(i));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("addReparticionesParticipantes(List<ReparticionParticipante>) - end");
		}
	}
	public void setTipoArchivoTrabajo(TipoArchivoTrabajoDTO tipoArchivoTrabajo) {
		this.tipoArchivoTrabajo = tipoArchivoTrabajo;
	}
	public TipoArchivoTrabajoDTO getTipoArchivoTrabajo() {
		return tipoArchivoTrabajo;
	}
	public void setPermisosVisualizacion(Set<ArchivoDeTrabajoVisualizacionDTO> permisosVisualizacion) {
		this.permisosVisualizacion = permisosVisualizacion;
	}
	public Set<ArchivoDeTrabajoVisualizacionDTO> getPermisosVisualizacion() {
		return permisosVisualizacion;
	}
	public void setTipoReserva(Integer tipoReserva) {
		this.tipoReserva = tipoReserva;
	}
	public Integer getTipoReserva() {
		return tipoReserva;
	}
	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}
	public void setIdGuardaDocumental(String idFileNET) {
		this.idGuardaDocumental = idFileNET;
	}
	
	
	
	
	public void copiarAtributosParaTC(ArchivoDeTrabajoDTO at) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiarAtributosParaTC(at={}) - start", at);
		}

		this.dataArchivo = at.getDataArchivo();
		this.fechaAsociacion= new Date();
		this.definitivo = at.isDefinitivo();
		this.usuarioAsociador = at.getUsuarioAsociador();
		this.tipoArchivoTrabajo = at.getTipoArchivoTrabajo();
		this.tipoReserva = at.getTipoReserva();
		this.reparticionesParticipantes = copiarReparticionesParticipantes(at.getReparticionesParticipantes());
		this.permisosVisualizacion = copiarPermisosDeVisualizacion(at.getPermisosVisualizacion());
		this.nombreArchivo = at.getNombreArchivo();

		if (logger.isDebugEnabled()) {
			logger.debug("copiarAtributosParaTC(ArchivoDeTrabajo) - end");
		}
	}
	private Set<ArchivoDeTrabajoVisualizacionDTO> copiarPermisosDeVisualizacion(
			Set<ArchivoDeTrabajoVisualizacionDTO> permisos) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiarPermisosDeVisualizacion(permisos={}) - start", permisos);
		}

		Set<ArchivoDeTrabajoVisualizacionDTO> set = new HashSet<ArchivoDeTrabajoVisualizacionDTO>();
		
		for(ArchivoDeTrabajoVisualizacionDTO at : permisos){
			ArchivoDeTrabajoVisualizacionDTO a = new ArchivoDeTrabajoVisualizacionDTO(at,this);
			set.add(a);
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("copiarPermisosDeVisualizacion(Set<ArchivoDeTrabajoVisualizacion>) - end - return value={}", set);
		}
		return set;
	}
	private List<ReparticionParticipanteDTO> copiarReparticionesParticipantes(
			List<ReparticionParticipanteDTO> rp) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiarReparticionesParticipantes(rp={}) - start", rp);
		}

		List<ReparticionParticipanteDTO>rps = new ArrayList<ReparticionParticipanteDTO>();
		for(ReparticionParticipanteDTO r : rp){
			ReparticionParticipanteDTO c = new ReparticionParticipanteDTO(r);
			rps.add(c);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("copiarReparticionesParticipantes(List<ReparticionParticipante>) - end - return value={}", rps);
		}
		return rps;
	}
	private TipoArchivoTrabajoDTO copiarTipoArchivoTrabajo(
			TipoArchivoTrabajoDTO tt2) {
		if (logger.isDebugEnabled()) {
			logger.debug("copiarTipoArchivoTrabajo(tt2={}) - start", tt2);
		}

		TipoArchivoTrabajoDTO tt = new TipoArchivoTrabajoDTO();
		tt.setNombre(tt2.getNombre());
		tt.setRepetible(tt2.isRepetible());
		tt.setDescripcion(tt2.getDescripcion());

		if (logger.isDebugEnabled()) {
			logger.debug("copiarTipoArchivoTrabajo(TipoArchivoTrabajo) - end - return value={}", tt);
		}
		return tt;
	}
	
	
	
	
}
