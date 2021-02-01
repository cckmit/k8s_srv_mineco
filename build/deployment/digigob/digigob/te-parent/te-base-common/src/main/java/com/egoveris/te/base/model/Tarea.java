package com.egoveris.te.base.model;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Tarea implements Comparable<Object> {
	private static final Logger logger = LoggerFactory.getLogger(Tarea.class);

	public transient static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private String nombreTarea;	
	private String codigoExpediente;	
	private	Long idSolicitud;
	private String estado;
	private String usuarioAnterior;
	private String motivo;
	private Task task;	
	private String tareaGrupal;
	private String codigoTrata;
	private String descripcionTrata;
	private String fechaCreacion;
	private String fechaModificacion;
	private String sistemaExterno;
	
	/* constructor por default */
	public Tarea(){}

	public Tarea(String codigoExpediente,
			String estado,
			String usuarioAnterior,
			String motivo,
			String codigoTrata,
			String descTrata, 			
			String fechaModificacion) {
		this.codigoExpediente = codigoExpediente;  
		this.estado = estado; 
		this.usuarioAnterior = usuarioAnterior; 
		this.motivo = motivo; 
		this.codigoTrata = codigoTrata;
		this.descripcionTrata = descTrata; 
		this.fechaModificacion = fechaModificacion;

	}
	/* constructor con accessor */
	public Tarea(String nombreTarea, 
						String codigoExpediente, 
						Long idSolicitud, 
						String estado, 
						String usuarioAnterior, 
						String motivo, 
						Task task, 
						String tareaGrupal, 
						String codigoTrata,
						String descTrata, 
						String fechaCreacion, 
						String fechaModificacion) {
		
		this.nombreTarea = nombreTarea;
		this.codigoExpediente = codigoExpediente;  
		this.idSolicitud = idSolicitud; 
		this.estado = estado; 
		this.usuarioAnterior = usuarioAnterior; 
		this.motivo = motivo; 
		this.task = task; 
		this.tareaGrupal = tareaGrupal; 
		this.codigoTrata = codigoTrata;
		this.descripcionTrata = descTrata; 
		this.fechaCreacion = fechaCreacion; 
		this.fechaModificacion = fechaModificacion;
		
	}

	public String getCodigoTrata() {
		return codigoTrata;
	}

	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}

	public String getTareaGrupal() {
		return tareaGrupal;
	}

	public void setTareaGrupal(String tareaGrupal) {
		this.tareaGrupal = tareaGrupal;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public String getCodigoExpediente() {
		return codigoExpediente;
	}

	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}


	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUsuarioAnterior() {
		return usuarioAnterior;
	}

	public void setUsuarioAnterior(String usuarioAnterior) {
		this.usuarioAnterior = usuarioAnterior;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	@Override
    public int compareTo(Object o) {
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(o={}) - start", o);
		}

		int returnint = this.compareToObjects(this.fechaModificacion, ((Tarea) o).fechaModificacion);
		if (logger.isDebugEnabled()) {
			logger.debug("compareTo(Object) - end - return value={}", returnint);
		}
        return returnint;
    }

	private int compareToObjects(Object firstObject, Object secondObject) {
		if (logger.isDebugEnabled()) {
			logger.debug("compareToObjects(firstObject={}, secondObject={}) - start", firstObject, secondObject);
		}

		if ((firstObject != null && secondObject != null) && firstObject instanceof Comparable) {
			int returnint = ((Comparable) firstObject).compareTo(secondObject);
			if (logger.isDebugEnabled()) {
				logger.debug("compareToObjects(Object, Object) - end - return value={}", returnint);
			}
			return returnint;
		} else if ((secondObject != null && firstObject != null) && secondObject instanceof Comparable) {
			int returnint = -((Comparable) secondObject).compareTo(firstObject);
			if (logger.isDebugEnabled()) {
				logger.debug("compareToObjects(Object, Object) - end - return value={}", returnint);
			}
			return returnint;
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("compareToObjects(Object, Object) - end - return value={}", 0);
			}
			return 0;
		}		
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoExpediente == null || "null".equalsIgnoreCase(codigoExpediente)) ? 0 : codigoExpediente.hashCode());
		result = prime * result + ((codigoTrata == null || "null".equalsIgnoreCase(codigoTrata)) ? 0 : codigoTrata.hashCode());
		result = prime * result + ((descripcionTrata == null || "null".equalsIgnoreCase(descripcionTrata)) ? 0 : descripcionTrata.hashCode());
		result = prime * result + ((estado == null || "null".equalsIgnoreCase(estado)) ? 0 : estado.hashCode());
		result = prime * result + ((fechaCreacion == null || "null".equalsIgnoreCase(fechaCreacion)) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((fechaModificacion == null || "null".equalsIgnoreCase(fechaModificacion)) ? 0 : fechaModificacion.hashCode());
		result = prime * result + ((idSolicitud == null) ? 0 : idSolicitud.hashCode());
		result = prime * result + ((motivo == null || "null".equalsIgnoreCase(motivo)) ? 0 : motivo.hashCode());
		result = prime * result + ((nombreTarea == null || "null".equalsIgnoreCase(nombreTarea)) ? 0 : nombreTarea.hashCode());
		result = prime * result + ((tareaGrupal == null || "null".equalsIgnoreCase(tareaGrupal) ) ? 0 : tareaGrupal.hashCode());
		result = prime * result + ((usuarioAnterior == null || "null".equalsIgnoreCase(usuarioAnterior)) ? 0 : usuarioAnterior.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		if (codigoExpediente == null || "null".equalsIgnoreCase(codigoExpediente)) {
			if (other.codigoExpediente != null)
				return false;
		} else if (!codigoExpediente.equals(other.codigoExpediente))
			return false;
		if (codigoTrata == null || "null".equalsIgnoreCase(codigoTrata)) {
			if (other.codigoTrata != null)
				return false;
		} else if (!codigoTrata.equals(other.codigoTrata))
			return false;
		if (descripcionTrata == null || "null".equalsIgnoreCase(descripcionTrata)) {
			if (other.descripcionTrata != null)
				return false;
		} else if (!descripcionTrata.equals(other.descripcionTrata))
			return false;
		if (estado == null || "null".equalsIgnoreCase(estado)) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (fechaCreacion == null || "null".equalsIgnoreCase(fechaCreacion)) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (fechaModificacion == null || "null".equalsIgnoreCase(fechaModificacion)) {
			if (other.fechaModificacion != null)
				return false;
		} else if (!fechaModificacion.equals(other.fechaModificacion))
			return false;
		if (idSolicitud == null) {
			if (other.idSolicitud != null)
				return false;
		} else if (!idSolicitud.equals(other.idSolicitud))
			return false;
		if (motivo == null || "null".equalsIgnoreCase(motivo)) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (nombreTarea == null || "null".equalsIgnoreCase(nombreTarea)) {
			if (other.nombreTarea != null)
				return false;
		} else if (!nombreTarea.equals(other.nombreTarea))
			return false;
		if (tareaGrupal == null || "null".equalsIgnoreCase(tareaGrupal)) {
			if (other.tareaGrupal != null)
				return false;
		} else if (!tareaGrupal.equals(other.tareaGrupal))
			return false;
		if (usuarioAnterior == null || "null".equalsIgnoreCase(usuarioAnterior)) {
			if (other.usuarioAnterior != null)
				return false;
		} else if (!usuarioAnterior.equals(other.usuarioAnterior))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Tarea [")
		.append((nombreTarea != null ? "nombreTarea=" + nombreTarea + ", " : ""))
		.append((codigoExpediente != null ? "codigoExpediente=" + codigoExpediente + ", ": ""))
		.append((idSolicitud != null ? "idSolicitud= " + idSolicitud + ", " : ""))
		.append( (estado != null ? "estado=" + estado + ", " : "") )
		.append((usuarioAnterior != null ? "usuarioAnterior=" + usuarioAnterior + ", " : ""))
		.append((motivo != null ? "motivo=" + motivo + ", " : ""))
		.append((tareaGrupal != null ? "tareaGrupal=" + tareaGrupal + ", " : "") )
		.append((codigoTrata != null ? "codigoTrata=" + codigoTrata + ", " : "") )
		.append((fechaCreacion != null ? "fechaCreacion=" + fechaCreacion + ", " : ""))
		.append((fechaModificacion != null ? "fechaModificacion=" + fechaModificacion + ", " : ""))
		.append("]");
		
		return sb.toString();
	}

	public String getSistemaExterno() {
		return sistemaExterno;
	}

	public void setSistemaExterno(String sistemaExterno) {
		this.sistemaExterno = sistemaExterno;
	}
}
