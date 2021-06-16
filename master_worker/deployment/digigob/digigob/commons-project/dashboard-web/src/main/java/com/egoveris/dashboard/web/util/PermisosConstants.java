package com.egoveris.dashboard.web.util;

import org.apache.commons.lang.StringUtils;

public class PermisosConstants {
	
	public static final String EDT = "EDT";
	public static final String OPERACIONES = "TE.OPERACIONES";
	public static final String MIS_TAREAS = "TE.TAREAS.MISTAREAS";
	public static final String BUZON_GRUPAL = "TE.TAREAS.BUZONGRUPAL";
	public static final String MIS_SUPERVISADOS = "TE.TAREAS.SUPERVISADOS";
	public static final String CONSULTAS = "TE.CONSULTAS";
	
	public static final String CONSULTA_CONSOLIDACION = "TE.CONSULTA.CONSOLIDACION";
	
	public static final String REHABILITAR = "TE.REHABILITAR";
	public static final String HERRAMIENTAS = "TE.HERRAMIENTAS";
	public static final String TRAMITES = "TE.ADM.TRAMITES";
	public static final String ADM_DOCUMENTOS= "DEO.ADMIN.DOCUMENTOS";
	public static final String FFDD = "FFDD";
	public static final String TIPO_OPERACIONES = "TE.ADM.TIPOOPER";
	public static final String WD_STATEFLOW = "WD.STATEFLOW";
	public static final String WD_TASKFLOW = "WD.TASKFLOW";
	public static final String DEO_MIS_TAREAS = "DEO.TAREAS.MISTAREAS";
	public static final String DEO_TAREAS_SUPERVISADAS = "DEO.TAREAS.SUPERVISADAS";
	public static final String DEO_CONSULTAS = "DEO.CONSULTAS";
	public static final String DEO_PLANTILLAS = "DEO.MIS.PLANTILLAS";
	public static final String EDT_USUARIO = "EDT.ADMINUSUARIOPERFIL";
	public static final String EDT_ORGANISMO = "EDT.ADMINISTRACIONORGANIZACION";
	public static final String EDT_MANTENEDORES = "EDT.ADMINISTRACIONMANTENEDORES";
	public static final String EDT_FERIADOS = "EDT.ADMINISTRACIONFERIADOS";
	public static final String EDT_NOVEDADES = "EDT.ADMINISTRACIONNOVEDADES";
	
	public static final String GRUPO_TE = StringUtils.join(new String[] {  OPERACIONES, CONSULTAS, REHABILITAR}, ",");
	public static final String GRUPO_TAREAS = StringUtils.join(new String[] { MIS_TAREAS, BUZON_GRUPAL, MIS_SUPERVISADOS }, ",");
	public static final String GRUPO_DEO = StringUtils.join(new String[] {  DEO_MIS_TAREAS, DEO_TAREAS_SUPERVISADAS, DEO_CONSULTAS, DEO_PLANTILLAS}, ",");
	public static final String GRUPO_ADMINISTRACION = StringUtils.join(new String[] { TRAMITES, ADM_DOCUMENTOS, TIPO_OPERACIONES, EDT_NOVEDADES, EDT_FERIADOS, EDT_MANTENEDORES, EDT_ORGANISMO, EDT_USUARIO}, ",");
	public static final String GRUPO_HERRAMIENTAS = StringUtils.join(new String[] { FFDD, WD_STATEFLOW, WD_TASKFLOW, HERRAMIENTAS}, ",");
	
	private PermisosConstants() {
		// Constructor
	}
	
}
