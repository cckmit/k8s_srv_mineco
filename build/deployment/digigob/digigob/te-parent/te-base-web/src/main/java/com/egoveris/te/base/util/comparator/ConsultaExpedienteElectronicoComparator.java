package com.egoveris.te.base.util.comparator;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import java.io.Serializable;
import java.util.Comparator; 
@SuppressWarnings( { "serial", "rawtypes" })
public class ConsultaExpedienteElectronicoComparator implements Comparator, Serializable {
	private boolean asc = true;
	private int type = 0;
	
	public ConsultaExpedienteElectronicoComparator(boolean asc, int type) {
		this.asc = asc;
		this.type = type;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int compare(Object o1, Object o2) {
		int result = 0;
		
		ExpedienteElectronicoDTO exp1 = (ExpedienteElectronicoDTO) o1;
		ExpedienteElectronicoDTO exp2 = (ExpedienteElectronicoDTO) o2;
		
		switch (type) {
		case 1:
			if (exp1 == null || exp1.toString() == null) {
				result = -1;
			} else if (exp2 == null || exp2.toString() == null) {
				result = 1;
			} else {
				result = exp1.toString().compareTo(
						exp2.toString())
						* (asc ? 1 : -1);
			}
			
			break;
			
		case 2:
			if (exp1 == null || exp1.getCodigoReparticionUsuario() == null) {
				result = -1;
			} else if (exp2 == null || exp2.getCodigoReparticionUsuario() == null) {
				result = 1;
			} else {
				result = exp1.getCodigoReparticionUsuario().compareTo(
						exp2.getCodigoReparticionUsuario())
						* (asc ? 1 : -1);
			}
			
			break;
		case 3:
			if (exp1 == null || exp1.getFechaCreacion() == null) {
				result = -1;
			} else if (exp2 == null || exp2.getFechaCreacion() == null) {
				result = 1;
			} else {
				result = exp1.getFechaCreacion().compareTo(exp2.getFechaCreacion())
				* (asc ? 1 : -1);
			}
			
			break;
		case 4:
			if (exp1 == null || exp1.getUsuarioCreador() == null) {
				result = -1;
			} else if (exp2 == null || exp2.getUsuarioCreador() == null) {
				result = 1;
			} else {
				result = exp1.getUsuarioCreador().compareTo(
						exp2.getUsuarioCreador())
						* (asc ? 1 : -1);
			}
			
			break;
		case 5:
			if (exp1 == null || exp1.getSolicitudIniciadora().getMotivo() == null) {
				result = -1;
			} else if (exp2 == null || exp2.getSolicitudIniciadora().getMotivo() == null) {
				result = 1;
			} else {
				result = exp1.getSolicitudIniciadora().getMotivo().compareTo(
						exp2.getSolicitudIniciadora().getMotivo())
						* (asc ? 1 : -1);
			}
			
			break;
		
		default: // Full Name
			// return
			// contributor1.getFullName().compareTo(contributor2.getFullName())
			// * (asc ? 1 : -1);
			result = 0;
		}
		return result;
	}
}