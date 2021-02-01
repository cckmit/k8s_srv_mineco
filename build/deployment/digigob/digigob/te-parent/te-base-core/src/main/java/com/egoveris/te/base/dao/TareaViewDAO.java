package com.egoveris.te.base.dao;

import com.egoveris.te.base.model.Tarea;

import java.sql.SQLDataException;
import java.util.List;

public interface TareaViewDAO {
	public int numeroTotalTareaDistPorCriterio(String assignee, String estado) throws SQLDataException;
	
	public int numeroTotalTareaDistPorGrupo(String candidate, String estado) throws SQLDataException;
	
	public int numeroTotalTareasSupervisadas(String assignee)
    throws SQLDataException;
	
	public List<Tarea> buscarTareaDistPorTrata(final String expedienteEstado, final String expedienteUsuarioAsignado, final List<String> codigosTrataList) throws SQLDataException;
	public Boolean verificarNoExisteTarea(final String idWorkflow) throws SQLDataException;
}
