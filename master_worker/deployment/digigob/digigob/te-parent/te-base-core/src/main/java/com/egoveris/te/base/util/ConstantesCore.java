package com.egoveris.te.base.util;

public class ConstantesCore {
  
	//Motivos del documento
	public static final String MOTIVO_CARATULA = "Caratula";
	public static final String MOTIVO_MODIFICACION_CARATULA = "Modificación Caratula";
	public static final String MOTIVO_PASE = "Pase";
	public static final String MOTIVO_VINCULACION_TRAMITACION_CONJUNTA = "Vinculación Tramitacion Conjunta";
	public static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación Tramitacion Conjunta";
	public static final String MOTIVO_VINCULACION_FUSION = "Caratula Fusión";
	public static final String PASE_BLOQUEO = "Bloqueo";
	public static final String PASE_DESBLOQUEO = "Desbloqueo";
	public static final String PASE_SIN_CAMBIOS = "SinCambios";	
	public static final String PASE_DE_PASE="motivoDePase" ;
	public static final String ESTADO_TRAMITACION_PARA_REHABILITACION = "Tramitacion";
	public static final String MOTIVO_PASE_REASIGNACION = "Reasignación";
		
	public static final boolean HABILITAR_TAB_TC = true;
	public static final boolean HABILITAR_TAB_FUSION = true;
	
	//VARIABLE TRATA REPARTICION
	public static final String TODAS_REPARTICIONES_HABILITADAS = "--TODAS--";
	public static final String CREACION_TRATA_REPARTICION = "CREACIÓN";
	public static final String MODIF_TRATA_REPARTICION = "MODIFICACIÓN";
	public static final String SELECCIONAR_TODOS = "--TODOS--" ;
	
	//Tipos documento generados
	public static final Long TIPO_DOCUMENTO_PASE = 1L;
	public static final Long TIPO_DOCUMENTO_SUBSANACION = 2L;
	
	public static final String SIGLA_MODULO_ORIGEN = "TE";
	
	//Estados de un expediente
	public static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
	public static final String ESTADO_SUBSANACION = "Subsanacion";
	public static final String ESTADO_PARALELO = "Paralelo"; 
	public static final String ESTADO_TRAMITACION = "Tramitacion";
	public static final String ESTADO_EJECUCION = "Ejecucion";
	public static final String ESTADO_INICIACION = "Iniciacion";
	public static final String ESTADO_ARCHIVO = "Archivo";
	public static final String ESTADO_COMUNICACION = "Comunicacion";
	public static final String ESTADO_CERRADO = "CERRADO";
	public static final String ESTADO_ABIERTA = "ABIERTA";
	public static final String ESTADO_PENDIENTE = "PEDIENTE";
	
	//Constantes para 
	public static final String REPARTICION_USUARIO = "reparticionUsuario";
	public static final String SECTOR_USUARIO = "sectorUsuario";
	public static final String DESTINATARIO = "destinatario";
	public static final String USUARIO_PRODUCTOR = "usuarioProductor";
	public static final String USUARIO_ANTERIOR = "usuarioAnterior";
	public static final String SISTEMA_APODERADO = "sistemaApoderado";
	public static final String REPARTICION_DESTINO = "reparticionDestino";
	public static final String REPARTICION_MESA_DESTINO = "reparticionMesaDestino";
	public static final String SECTOR_DESTINO = "sectorDestino";
	public static final String USUARIO_DESTINO = "usuarioDestino";
	public static final String USUARIO_ORIGEN = "usuarioOrigen";
	public static final String ESTADO_SELECCIONADO = "estadoSeleccionado";
	public static final String ES_REPARTICION_DESTINO = "esReparticionDestino";
	public static final String ES_MESA_DESTINO = "esMesaDestino";
	public static final String ES_SECTOR_DESTINO = "esSectorDestino";
	public static final String ES_USUARIO_DESTINO = "esUsuarioDestino";
	public static final String ID_EXPEDIENTE_ELECTRONICO = "idExpedienteElectronico";
	public static final String GRUPO_SELECCIONADO = "grupoSeleccionado";
	public static final String USUARIO_SELECCIONADO = "usuarioSeleccionado";
	public static final String USUARIO_SOLICITANTE=	"usuarioSolicitante";
	public static final String TAREA_GRUPAL = "tareaGrupal";
	public static final String ESTADO = "estado";
	public static final String MOTIVO = "motivo";
	public static final String ACLARACION = "aclaracion";
	public static final String LOGGED_USERNAME = "loggedUsername";
	public static final String ESTADO_ANTERIOR_PARALELO = "estadoAnteriorParalelo";
	public static final String ESTADO_ANTERIOR = "estadoAnterior";
	public static final String SUFIJO_BLOQUEADO = ".bloqueado";
	public static final String SUFIJO_CONJUNTA = ".conjunta";
	public static final String ULTIMA_MODIFICACION = "utlimaModificacion";
	public static final String CODIGO_TRATA="codigoTrata";
	public static final String DESCRIPCION="descripcion";
	public static final String USUARIO_CANDIDATO="usuarioCandidato";
	public static final String CODIGO_EXPEDIENTE="codigoExpediente";
	public static final String PENDIENTE="Pendiente";
	public static final String ID_SOLUCITUD="idSolicitud";
	public static final String INICIO="inicio";
	public static final String TIPO_DOCUMENTO="tipoDocumento";
	public static final String EE_BLOQUEADO = "expBloqueado";
	
	public static final String TIPO_ACTUACION ="EX";
	public static final String SECUENCIA_GENERICA ="   ";
	//public static final String REPARTICION_ACTUACION ="MGEYA" ;
	public static final int CANTIDAD_DIAS= 20 ;
	
	/**
	 * Carpeta raíz en WebDav dentro de la cual se crea la estructura de
	 * espacios que almacenarán los documentos definitivos.
	 */
	public static final String CARPETA_RAIZ_DOCUMENTOS = "SADE";
	
	// Reserva de expedientes
	public static final String SIN_RESERVA = "SIN RESERVA";
	public static final String RESERVA_PARCIAL = "PARCIAL";
	public static final String RESERVA_TOTAL = "TOTAL";
	public static final String SESSION_USERNAME = "userName";
	public static final String SESSION_USER="user";
	
	public static final int ID_ESTRUCTURA_JUSTICIA_CABA = 14;
	public static final int ID_ESTRUCTURA_JUSTICIA_NACIONAL_Y_PROVINCIAL = 15;
	public static final String JUSTICIA_CABA = "Justicia CABA";
	
	public static final String CONFECCION_DOCUMENTO = "Confeccionar Documento";
	
	//Respuesta de gedo
	public static final String APROBADA= "APROBADA";
	public static final String RECHAZADA="RECHAZADA";
	
	public static final String VAR_ARCHIVO_TEMPORAL_FIRMA = "archivoTemporalFirma";
	public static final String VAR_SISTEMA_INICIADOR = "sistemaIniciador";
	public static final String VAR_USUARIO_PRODUCTOR = "usuarioProductor";
	public static final String VAR_USUARIO_CREADOR = "usuarioCreador";
	public static final String VAR_MOTIVO = "motivo";
	public static final String VAR_TIPO_DOCUMENTO = "tipoDocumento";
	public static final String VAR_MENSAJE_PRODUCTOR = "mensajeProductor";
	public static final String VAR_DOCUMENTO_DATA = "documentoMetadata";
	public static final String VAR_SOLICITUD_ENVIO_MAIL = "solicitudEnvioCorreo";
	public static final String VAR_RECEPTORES_AVISO_FIRMA = "receptoresAvisoFirma";
	public static final String NOMBRE_APLICACION = "EE";
	public static final String NOMBRE_APLICACION_GEDO = "GEDO";
	public static final String REPARTICION_PERMISO_INICIAR = "permisoIniciar";
	public static final String EVENTO_ARCHIVO_TRABAJO = "eventoArchivoTrabajo";
	public static final String EVENTO_USUARIOS_FIRMANTES = "eventoUsuariosFirmantes";
	public static final String EVENTO_DOCUMENTO_SADE = "incorporarDocumentoSade";
	public static final String EVENTO_ADICION_REVISOR = "eventoAdicionRevisor";
	public static final String EVENTO_TOMA_VISTA_RECHAZO = "rechazarTomaVista";
	
	public static final String IMG_ES_FIRMA_CONJUNTA = "/imagenes/IconoFirmaConjunta.png";
	public static final String IMG_TIENE_TOKEN = "/imagenes/IconoToken.png";
	public static final String IMG_ES_CONFIDENCIAL = "/imagenes/IconoConfidencial.png";
	public static final String IMG_ES_FIRMA_EXTERNA = "/imagenes/IconoFirmaExterna.png";
	public static final String IMG_ES_ESPECIAL = "/imagenes/IconoEspecial.png";
	public static final String IMG_TIENE_LIBRE = "/imagenes/IconoLibre.png";
	public static final String IMG_TIENE_TEMPLATE = "/imagenes/IconoTemplate.png";
	public static final String IMG_TIENE_IMPORTADO_TEMPLATE = "/imagenes/IconoImportadoTemplate.png";
	public static final String IMG_TIENE_IMPORTADO = "/imagenes/IconoImportado.png";
	public static final String IMG_COPIAR_OBLIGATORIO = "/imagenes/copiarObligatorio.png";
	public static final String IMG_COPIAR_NO_OBLIGATORIO = "/imagenes/copiarNoObligatorio.png";
	
	public static final String ESTADO_ALTA = "ALTA";
	public static final String PETICION_PENDIENTE_GEDO = "PETICION_PENDIENTE_GEDO";
	
	/**
	 * Tipo de produccion
	*/
	public static final String TIPO_PRODUCCION_LIBRE = "LIBRE";
	public static final String TIPO_PRODUCCION_IMPORTADO = "IMPORTADO";
	public static final String TIPO_PRODUCCION_TEMPLATE = "TEMPLATE";
	public static final String TIPO_PRODUCCION_IMPORTADO_TEMPLATE = "IMPORTADO-TEMPLATE";
	
	public static final String CU_VALOR = "CU";
	public static final String CU_DESCRIPCION = "Cuit"; 
	
	public static final String PATH_TAREAS_WORKFLOW = "/u/tareasworkflow/";
	
	public static final String TIPO_ARCHIVO_DEFECTO = "Otros";
	public static final String TIPO_ARCHIVO_IMAGEN = "Imagen";
	public static final String ESTADO_SOLICITUD_ARCHIVO = "Solicitud Archivo";
	
	public static final String DESTINO_INTERVINIENTE = "INTERVINIENTE";
	
	public static final String TASK_NOMBRE_PARALELO = "Paralelo";
	
	public static final String TIPO_OPERACION = "tipoOperacion";
	
	public static final String ROL_ASIGNADOR = "EE.ASIGNADOR";

	public static final String GUARDA_DOCUMENTAL_FILENET= "FILENET";

	public static final String GUARDA_DOCUMENTAL_WEBDAV = "WEBDAV";
	
	// Ultimo EE GUARDADO
	public static final String ID_EE_GUARDADO = "ID_EE_GUARDADO";
	
	/**PLSQL: Actualizar Tareas de GT 
	 * @smuzychu
	 * Por no encontrarse solucion al no poder reproducir el caso 
	 * Se genero el PlSQL en caso de presentarse tareas de Buzon en GT 
	 *  
	 * 
	 * **/	
	public static final String PLSQL = " DECLARE " +
	"   CURSOR estados " +
	"    IS" +
	"       SELECT e.id_workflow," +
	"              e.estado," +
	"              e.id," +
	"             t.execution_, " +
	"             t.name_, " +
	"             t.form_ " +
	"        FROM EE_EXPEDIENTE_ELECTRONICO e, JBPM4_TASK t " +
	"       WHERE     e.ID_WORKFLOW = t.EXECUTION_ID_ " +
	"             AND e.estado != t.name_" +
	"             AND e.estado != 'Solicitud Archivo'" +
	"             AND e.estado != 'Archivo'" +
	"             AND t.name_ = 'Guarda Temporal';" +
	"   v_cuenta   NUMBER (10); " +
	" BEGIN " +
	"   v_cuenta := 0;" +
	"   FOR c1 IN estados" +
	"   LOOP" +
	"      UPDATE JBPm4_EXECUTION" +
	"         SET ACTIVITYNAME_ = c1.estado" +
	"       WHERE dbid_ = c1.execution_;" +
	"      UPDATE JBPM4_TASK" +
	"         SET TASKDEFNAME_ = c1.estado," +
	"             NAME_ = c1.estado," +
	"             ACTIVITY_NAME_ = c1.estado," +
	"             form_ = 'expediente/tramitacion.zul'" +
	"       WHERE eXECUTION_ = c1.execution_;" +
	"      v_cuenta := v_cuenta + 1;" +
	"      COMMIT;" +
	"      NULL;" +
	"   END LOOP;" +
	" END; ";
	
	/**
	 * 
	 * usuario Actividades Arch
	 * @smuzychu
	 * 
	 */
	public static final String ACTUALIZAR_RESERVA_REPARTICION_RECTORA="UPDATE EE_ARCH_TRABAJO_VISUALIZACION SET RECTORA= ? WHERE RECTORA= ?";
	
	public static final String ACTUALIZAR_RESERVA_SECTORES = "UPDATE EE_ARCH_TRABAJO_VISUALIZACION SET REPARTICION= ?, SECTOR= ? WHERE REPARTICION = ? AND SECTOR= ?";
	
	
	public static final String SQL_ACTIVIDAD_REGULARIZADOR = 
			" DECLARE " +
			   " v_auxgedo   GEDO_DOCUMENTO.NUMERO%TYPE; "+
			   " v_aux_numero   VARCHAR2 (4000); "+


			   " CURSOR caratulados_no_avisados " +
			   " IS "+
			      " SELECT '' GEDO_ASIGNADO,"+
			      		 " e.id EEID, "+	
			             " a.id, "+
			             " t.numero AS NUMERO_SADE_GEDO,"+
			             " 'FINALIZADO'," +
			             "   E.TIPO_DOCUMENTO "+
			             " || '-' "+
			             " || e.anio "+
			             " || '-' "+
			             " || e.numero "+
			             " || '-' "+
			             " || E.CODIGO_REPARTICION_ACTUACION "+
			             " || '-' "+
			             " || E.CODIGO_REPARTICION_USUARIO "+
			                " AS codigo_ee "+
			             " FROM GEDO_DOCUMENTO t JOIN GEDO_PROCESO_LOG logg ON t.workfloworigen = logg.workflowid, "+
			             " EE_EXPEDIENTE_ELECTRONICO e," +
			             " ACTIVIDAD a,"+
			             " ACTIVIDAD_PARAM ap "+
			       " WHERE     t.WORKFLOWORIGEN = ap.valor "+
			             " AND a.id_objetivo = e.ID_WORKFLOW "+
			             " AND a.id = ap.id_actividad "+
			             " AND ap.campo = 'PEDIDO_GEDO' "+
			             " AND a.USUARIO_ALTA = 'GUARDA_TEMP_TAD' "+
			             " AND a.fecha_cierre IS NULL "+
			             " AND logg.estado='OK'; "+

			   " CURSOR actividades_total "+
			   " IS "+
			      " SELECT a.id, ap.valor "+
			        " FROM ACTIVIDAD a, ACTIVIDAD_PARAM ap "+
			       " WHERE     a.USUARIO_ALTA = 'GUARDA_TEMP_TAD' "+
			             " AND a.fecha_cierre IS NULL "+
			             " AND a.id = ap.id_actividad "+
			             " AND ap.campo = 'PEDIDO_GEDO' "+
			             " AND a.USUARIO_ALTA = 'GUARDA_TEMP_TAD' "+
			             " AND a.fecha_cierre IS NULL; " +

			" BEGIN "+
			   " FOR c1 IN caratulados_no_avisados "+
			   " LOOP "+ 
			    " UPDATE ACTIVIDAD "+
			         " SET estado = 'CERRADA',"+
			             " fecha_cierre = SYSDATE(), "+
			             " usuario_cierre = 'MASIVOMANUAL' "+
			       " WHERE id = c1.id; "+
			      " COMMIT; "+
			   
			      
  				" BEGIN " + 
  					" SELECT d.numero_sade "+ 
  					" INTO v_aux_numero " +
  					" FROM DOCUMENTO d, " +
  					" EE_EXPEDIENTE_DOCUMENTOS ed, " + 
  					" EE_EXPEDIENTE_ELECTRONICO ee " + 
  					" WHERE     d.id = ed.id_documento "+ 
  					" AND ee.id = ed.id "+ 
  					" AND d.numero_sade = c1.NUMERO_SADE_GEDO "+ 
  					" AND ee.id = c1.EEID; "+ 
  					" EXCEPTION "+
  					" WHEN NO_DATA_FOUND "+
  					" THEN "+
  					" UPDATE ACTIVIDAD "+
  					" SET estado = 'PENDIENTE_VINCULAR', "+ 
  					" fecha_cierre = SYSDATE(), "+ 
  					" usuario_cierre = 'MASIVOMANUAL' "+ 
  					" WHERE id = c1.ID; "+  

     				" UPDATE ACTIVIDAD_PARAM "+ 
     				" SET valor = c1.NUMERO_SADE_GEDO "+ 
     				" WHERE Id_ACTIVIDAD = c1.ID AND campo = 'PEDIDO_GEDO'; "+ 
     				" WHEN OTHERS "+ 
     				" THEN "+ 
     				" NULL; "+ 
     				" END; " +
     				" END LOOP; "+

			  " FOR c2 IN actividades_total " +
			   " LOOP "+
			      " BEGIN "+
			         " SELECT NUMERO "+
			           " INTO v_auxgedo "+
			           " FROM GEDO_DOCUMENTO "+
			          " WHERE WORKFLOWORIGEN = c2.valor; "+
			      " EXCEPTION "+
			        " WHEN NO_DATA_FOUND"+
			         " THEN "+
			            " BEGIN "+
			               " SELECT ASSIGNEE_ "+
			                 " INTO v_auxgedo " +
			                 " FROM GEDO_JBPM4_TASK "+
			                " WHERE EXECUTION_ID_ = c2.valor; "+
			            " EXCEPTION "+
			               " WHEN NO_DATA_FOUND "+
			               " THEN "+
			                  " BEGIN "+
			                     " SELECT KEY_ "+
			                       " INTO v_auxgedo "+
			                       " FROM GEDO_JBPM4_VARIABLE "+
			                      " WHERE     EXECUTION_ = "+
			                                   " TO_NUMBER ( "+
			                                      " REPLACE (c2.valor, 'procesoGEDO.', '')) "+
			                            " AND ROWNUM = 1; "+
			                  " EXCEPTION "+
			                     " WHEN NO_DATA_FOUND "+
			                     " THEN "+
			                        " update ACTIVIDAD set estado = 'CERRADA', fecha_cierre = sysdate(), usuario_cierre = 'MASIVOMANUAL' where id = c2.id; "+
			                        " commit; "+
			                  " END; "+
			            " END; "+
			      " END; "+
			   " END LOOP; "+
			" END; ";
	
	
	public static final String GENERAR_HISTORIAL_MIGRACION = "DECLARE " 
			+" CURSOR expedientes " 
			+" IS " 
			+	" SELECT e.id AS idExpediente,e.estado AS ESTADOEE, "
	      + " e.TIPO_DOCUMENTO || e.ANIO || e.NUMERO || e.CODIGO_REPARTICION_ACTUACION || '-' "
	      + " || e.CODIGO_REPARTICION_USUARIO AS CODIGO_SADE FROM EE_EXPEDIENTE_ELECTRONICO e JOIN JBPM4_TASK t ON e.ID_WORKFLOW = t.EXECUTION_ID_ "
		  + " LEFT JOIN JBPM4_PARTICIPATION p ON p.TASK_ = t.DBID_ "	
	      +	" WHERE t.NAME_ <> 'Paralelo' AND (ASSIGNEE_ = ? OR ASSIGNEE_ = ? ) AND p.GROUPID_ IS NULL; ; "
		+ " BEGIN "
			+ " FOR e IN EXPEDIENTES "
			+ "	LOOP "
	      + " INSERT "
	+" INTO HISTORIALOPERACION "
	  + " (ID,TIPO_OPERACION,FECHA_OPERACION,MOTIVO,USUARIO,EXPEDIENTE,ID_EXPEDIENTE,SECTOR_USUARIO_ORIGEN,REPARTICION_USUARIO, "
	  + " DESTINATARIO,ESTADO,TAREA_GRUPAL,ESTADO_ANTERIOR,LOGGEDUSERNAME) "
	  + " VALUES "
	  + " (HISTORIALOPERACION_ID_SEQ.NEXTVAL,'Pase',sysdate(),?,?,e.CODIGO_SADE,e.idExpediente,?,?,?,e.ESTADOEE,'noEsTareaGrupal',"
	  + "e.ESTADOEE,?); "
	  + " COMMIT; "
	  +	" END LOOP; "
	  +	" END; ";
	
	
	public static final String MIGRACION_SECTOR_STATEMENT =
			"DECLARE" 
			+" CURSOR expedientes " 
			+" 	IS "
			+"		SELECT e.TIPO_DOCUMENTO || e.ANIO || e.NUMERO || e.CODIGO_REPARTICION_ACTUACION "
			+"			|| '-' ||e.CODIGO_REPARTICION_USUARIO AS CODIGO_SADE, e.id AS IDEXPEDIENTE,e.estado AS ESTADOEE, t.dbid_ AS TASKID, " 
			+"			p.dbid_ AS PARTICIPATIONID,e.ANIO AS ANIO, e.NUMERO AS NUMERO,p.GROUPID_ AS GROUPID_ "
            +"			FROM EE_EXPEDIENTE_ELECTRONICO e JOIN JBPM4_TASK t ON "
			+"			t.EXECUTION_ID_ = e.ID_WORKFLOW JOIN JBPM4_PARTICIPATION p ON p.TASK_ = t.DBID_ "
			+"			WHERE  t.NAME_ <> 'Paralelo' "
			+"			AND p.GROUPID_ = ? "
			+"			OR p.GROUPID_ = ?" 
			+" 			AND t.ASSIGNEE_ IS NULL;"
			+"		BEGIN "
			+"			FOR e IN expedientes "
			+"			LOOP "
			+"			INSERT INTO HISTORIALOPERACION (ID,TIPO_OPERACION,FECHA_OPERACION,MOTIVO,USUARIO,EXPEDIENTE,ID_EXPEDIENTE, "
			+"							SECTOR_USUARIO_ORIGEN,REPARTICION_USUARIO,DESTINATARIO,ESTADO,TAREA_GRUPAL,ESTADO_ANTERIOR,LOGGEDUSERNAME) "						
			+"							VALUES (HISTORIALOPERACION_ID_SEQ.NEXTVAL,'Pase',"						
			+"							sysdate(),"
            +"							?,"
			+"							?,"
			+"							e.CODIGO_SADE,e.IDEXPEDIENTE," 
			+"							?,"
			+"							?,"
   			+"							?,"
			+"							e.ESTADOEE,'esTareaGrupal',e.ESTADOEE,"
			+"							?); "	
 			+"				IF e.GROUPID_ LIKE '%conjunta%' "
			+"					THEN "
			+"						UPDATE JBPM4_PARTICIPATION SET GROUPID_=? "
			+"						WHERE DBID_ = e.PARTICIPATIONID; "
			+"					 ELSE "
			+"						UPDATE JBPM4_PARTICIPATION SET GROUPID_= ? "
			+"						WHERE DBID_ = e.PARTICIPATIONID; "
			+"				END IF; "
			+"				COMMIT; "
			+"			END LOOP; "
			+"		END; ";
	
	public static final String USUARIO_GENERICO_GENERACION_AUDITORIA_ARCH = "CIERRE_ACTIVIDADES_ARCH";
	
	public static final String RECTORA_CONFIDENCIAL= "EE.CONFIDENCIAL.RECTORA";
	
	public static final String REPARTICION_CONFIDENCIAL= "EE.CONFIDENCIAL.REPARTICION";
	
	public static final String SECTOR_CONFIDENCIAL= "EE.CONFIDENCIAL.SECTOR";
	
	public static final Integer ARCHIVO_TRABAJO_RESERVADO = 1;
	public static final String ROL_CARAT_SADE_INTERNO = "SADE.INTERNOS";
	public static final String ROL_CARAT_SADE_EXTERNO = "SADE.EXTERNOS";
	
  public static final String GENERAR_COPIA_ESTADO_PENDIENTE = "PENDIENTE";
  public static final String GENERAR_COPIA_ESTADO_CANCELADA = "CANCELADA";
  public static final String GENERAR_COPIA_ESTADO_RESUELTA = "RESUELTA";

  public static final String MEMORANDUM = "ME";
  public static final String NOTA = "NO";

  public static final String MOTIVO_CANCELACION = "Cancelación de solicitud de subsanación del Expediente ";
  public static final String REFERENCIADECANCELACION = "Cancelación de Actividad.";
  
  private ConstantesCore() {
    // Private constructor
  }
}
