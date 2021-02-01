package com.egoveris.te.base.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.sharedsecurity.base.model.Usuario;

public class TareasUsuario implements Comparable<TareasUsuario> {
	private static final Logger logger = LoggerFactory.getLogger(TareasUsuario.class);

	private Usuario datosUsuario;
	private int tareasPendientes;

	public TareasUsuario(Usuario datosUsuario, int cantTareas) {
		this.datosUsuario=datosUsuario;
		this.tareasPendientes=cantTareas;
	}
	
	public Usuario getDatosUsuario() {
		return datosUsuario;
	}

	public void setDatosUsuario(Usuario datosUsuario) {
		this.datosUsuario = datosUsuario;
	}

	public int getTareasPendientes() {
		return tareasPendientes;
	}

	public void setTareasPendientes(int tareasPendientes) {
		this.tareasPendientes = tareasPendientes;
	}


	@Override
    public int compareTo(TareasUsuario o) {
		
		return new Integer(this.getTareasPendientes()).compareTo(new Integer(o.getTareasPendientes()));
		
//		
//		if (logger.isDebugEnabled()) {
//			logger.debug("compareTo(o={}) - start", o);
//		}
//
//		int returnint = this.compareToObjects(this.getDatosUsuario().getNombreApellido(), ((TareasUsuario) o).getDatosUsuario().getNombreApellido());
//		if (logger.isDebugEnabled()) {
//			logger.debug("compareTo(Object) - end - return value={}", returnint);
//		}
//        return returnint;
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
		result = prime * result + ((datosUsuario == null || datosUsuario.getNombreApellido() == null || "null".equalsIgnoreCase(datosUsuario.getNombreApellido())) ? 0 : datosUsuario.getNombreApellido().hashCode());
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
		TareasUsuario other = (TareasUsuario) obj;
		if (datosUsuario == null || datosUsuario.getNombreApellido() == null || "null".equalsIgnoreCase(datosUsuario.getNombreApellido())) {
			if (other.datosUsuario != null)
				return false;
		} else if (!datosUsuario.equals(other.datosUsuario))
		if(tareasPendientes != other.tareasPendientes)
			return false;
		return true;
	}

}
