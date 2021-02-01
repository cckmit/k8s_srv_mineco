package com.egoveris.te.base.model;

import java.util.ArrayList;
import java.util.List;

public class EjecucionDeTarea {
	TareaMigracionDTO  tareaDeEjecucion;
	List<String> errores;
	
	public TareaMigracionDTO getTareaDeEjecucion() {
		return tareaDeEjecucion;
	}

	public void setTareaDeEjecucion(TareaMigracionDTO tareaDeEjecucion) {
		this.tareaDeEjecucion = tareaDeEjecucion;
	}
	
	
	
	
	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public EjecucionDeTarea(){
		this.errores = new ArrayList<String>();
	}
	
}
