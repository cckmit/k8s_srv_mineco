package com.egoveris.te.base.util;

public class ConstantesWeb {

  // CONSTANTES QUE VIAJAN EN SESSION
  public static final String SESSION_USERNAME = "userName";
  public static final String SESSION_USER_NOMBRE_APELLIDO = "usernameNyA";
  public static final String SESSION_USER_REPARTICION = "usernameReparticion";
  public static final String SESSION_FUSIONADOR = "esFusionador";
  public static final String SESSION_USER_CCOO = "esUsuarioEnCCOO";
  public static final String SESSION_ADMIN_CENTRAL = "esAdministrador";
  public static final String SESSION_DESARCHIVADOR = "esDesarchivador";
  public static final String SESSION_PARAMETRO_SISTEMA_EXTERNO = "parametroSistemaExterno";
  public static final String SESSION_PARAMETRO_SISTEMA_AFJG = "parametroSistemaAFJG";
  public static final String SESSION_PERMISO_INTEGRACION_SIS_EXT = "permisoIntegracion";

  public static final String MOTIVO_CARATULA = "Caratula";
  public static final String MOTIVO_MODIFICACION_CARATULA = "Modificación Caratula";
  public static final String MOTIVO_PASE = "Pase";
  public static final String MOTIVO_VINCULACION_TRAMITACION_CONJUNTA = "Vinculación Tramitacion Conjunta";
  public static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación Tramitacion Conjunta";
  public static final String MOTIVO_VINCULACION_FUSION = "Caratula Fusión";
  public static final String PASE_BLOQUEO = "Bloqueo";
  public static final String PASE_DESBLOQUEO = "Desbloqueo";
  public static final String PASE_SIN_CAMBIOS = "SinCambios";
  public static final String PASE_DE_PASE = "motivoDePase";
  public static final String ESTADO_TRAMITACION_PARA_REHABILITACION = "Tramitacion";
  public static final String MOTIVO_PASE_REASIGNACION = "Reasignación";

  public static final boolean HABILITAR_TAB_TC = true;
  public static final boolean HABILITAR_TAB_FUSION = true;
  // VARIABLE TRATA REPARTICION

  public static final String CREACION_TRATA_REPARTICION = "CREACIÓN";
  public static final String MODIF_TRATA_REPARTICION = "MODIFICACIÓN";

  // Tipos documento generados
  public static final Long TIPO_DOCUMENTO_PASE = 1L;
  public static final Long TIPO_DOCUMENTO_SUBSANACION = 2L;

  public static final String SIGLA_MODULO_ORIGEN = "EE";

  // Constantes para
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
  public static final String USUARIO_SOLICITANTE = "usuarioSolicitante";
  public static final String TAREA_GRUPAL = "tareaGrupal";
  public static final int CANTIDAD_DIAS = 20;

  public static final String CARPETA_RAIZ_DOCUMENTOS = "SADE";

  // Reserva de expedientes
  public static final String SESSION_USER = "user";

  public static final int ID_ESTRUCTURA_JUSTICIA_CABA = 14;
  public static final int ID_ESTRUCTURA_JUSTICIA_NACIONAL_Y_PROVINCIAL = 15;
  public static final String JUSTICIA_CABA = "Justicia CABA";

  public static final String CONFECCION_DOCUMENTO = "Confeccionar Documento";

  public static final String ACLARACION = "aclaracion";

  // Respuesta de gedo
  public static final String APROBADA = "APROBADA";
  public static final String RECHAZADA = "RECHAZADA";

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

  public static final String EVENTO_DOCUMENTO_SADE = "incorporarDocumentoSade";
  public static final String EVENTO_ADICION_REVISOR = "eventoAdicionRevisor";
  public static final String EVENTO_TOMA_VISTA_RECHAZO = "rechazarTomaVista";

  public static final String IMG_TIENE_LIBRE = "/imagenes/IconoLibre.png";

  public static final String IMG_TIENE_IMPORTADO_TEMPLATE = "/imagenes/IconoImportadoTemplate.png";
  public static final String IMG_TIENE_IMPORTADO = "/imagenes/IconoImportado.png";
  public static final String IMG_COPIAR_OBLIGATORIO = "/imagenes/copiarObligatorio.png";
  public static final String IMG_COPIAR_NO_OBLIGATORIO = "/imagenes/copiarNoObligatorio.png";

  public static final String ESTADO_ALTA = "ALTA";

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
  public static final String TASK_NOMBRE_PARALELO = "Paralelo";
  public static final String GUARDA_DOCUMENTAL_FILENET = "FILENET";
  public static final String GUARDA_DOCUMENTAL_WEBDAV = "WEBDAV";

  public static final String ESTADO_ANTERIOR_PARALELO = "estadoAnteriorParalelo";
  public static final String ESTADO_ANTERIOR = "estadoAnterior";
  public static final String SUFIJO_BLOQUEADO = ".bloqueado";
  public static final String SUFIJO_CONJUNTA = ".conjunta";
  public static final String ULTIMA_MODIFICACION = "utlimaModificacion";
  public static final String CODIGO_TRATA = "codigoTrata";
  public static final String DESCRIPCION = "descripcion";
  public static final String USUARIO_CANDIDATO = "usuarioCandidato";
  public static final String CODIGO_EXPEDIENTE = "codigoExpediente";
  public static final String PENDIENTE = "Pendiente";
  public static final String ID_SOLUCITUD = "idSolicitud";
  public static final String INICIO = "inicio";
  public static final String TIPO_DOCUMENTO = "tipoDocumento";
  public static final String EE_BLOQUEADO = "expBloqueado";

  public static final String TIPO_ACTUACION = "EX";
  public static final String SECUENCIA_GENERICA = "   ";

  // CONSTANTES CLASE CONSTACTIVIDAD PACKETE com.egoveris.te.web.tramelec.common
  public static final String ACTIVIDAD = "popupActividad";
  public static final String PARAMETROS_VISUALIZACION = "popupParametrosVisualizacion";
  public static final String VISTA_ACTIVIDAD = "popupVistaActividad";

  public enum VISTA {
    EDICION, LECTURA
  }

  // CONSTANTE INTERFAZ TRUNCATED RESULT PACKETE com.egoveris.te.web.ee.satra.pl
  public static final int MAX_REPARTICION_RESULTS = 100;

  // CONSTANTES DE ESTADOS DE UNA TAREA DE UN EXPEDIENTE ELECTR�NICO
  public static final String ESTADO_PARALELO = "Paralelo";
  public static final String ESTADO_ADQUIRIDA = "Adquirida";
  public static final String ESTADO_INICIACION = "Iniciacion";
  public static final String ESTADO_TRAMITACION = "Tramitacion";
  public static final String ESTADO_SUBSANACION = "Subsanacion";
  public static final String ESTADO_COMUNICACION = "Comunicacion";
  public static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  public static final String ESTADO_SOLICITUD_ARCHIVO = "Solicitud Archivo";
  public static final String ESTADO_ARCHIVO = "Archivo";
  public static final String COMPOSER = "FormularioControladoComposer";
  public static final String CARATULAR_INTERNO_COMPOSER = "CaratularInternoComposer";
  public static final String ESTADO = "estado";
  public static final String MOTIVO = "motivo";
  public static final String DESTINATARIO = "destinatario";
  public static final String REPARTICION_USUARIO = "reparticionUsuario";
  public static final String SECTOR_USUARIO = "sectorUsuario";
  public static final String TIPO_OPERACION = "tipoOperacion";
  public static final String LOGGED_USERNAME = "loggedUsername";

  // CONSTANTES DE VISUALIZACION
  // Color que adquiriran las filas que han sido modificadas en una
  // tramitacion paralela y no han sido dadas definitivas.
  // MANTIS 689: Adquisicion de tarea post pase multiple.
  // Identificacion de modificaciones realizadas
  // Identificar con un color o de alguna manera aquellas
  // modificaciones a un EX que haya sido tramitado en paralelo y advocado por
  // su emisor.
  public static final String COLOR_ILUMINACION_FILA = "#FBDB76";

  // TODO estructurar como enum o similar
  public static final String ROL_ADMIN_CENTRAL = "ADMIN.CENTRAL";
  public static final String ROL_DESARCHIVADOR = "EE.DESARCHIVAR";
  public static final String ROL_ADMINISTRADOR_PASES = "EE.ADMINISTRADOR.PASES";
  public static final String ROL_FUSIONADOR = "EE.FUSIONADOR";
  public static final String ROL_ASIGNADOR = "EE.ASIGNADOR";
  public static final String ROL_CARAT_SADE_INTERNO = "SADE.INTERNOS";
  public static final String ROL_CARAT_SADE_EXTERNO = "SADE.EXTERNOS";

  public static final String ROL_EE_CONFIDENCIAL = "EE.CONFIDENCIAL.RESERVA";
  public static final String ROL_HERRAMIENTAS = "EE.HERRAMIENTAS";

  // VARIABLE TRATA REPARTICION
  public static final String TODAS_REPARTICIONES_HABILITADAS = "--TODAS--";
  public static final String NOMBRE_REPARTICION_TODAS = "TODAS";
  public static final String ESTADO_INICIAR_EXPEDIENTE = "Iniciar Expediente";
  public static final String ESTADO_ANULAR_MODIFICAR_EXPEDIENTE = "Anular/Modificar Solicitud";
  public static final String TIPO_DOCUMENTO_GENERADO_PASE = "PASE";

  // redireccion
  public static final String KEYWORD = "keyWord";
  public static final String PATHMAP = "PathMap";
  public static final String KEYWORD_TAREAS = "tareas";
  public static final String KEYWORD_EXPEDIENTES = "expedientes";
  public static final int CANTIDAD_ID_PERMITIDOS = 1;
  // VARIABLAS DE TIPO DOCUMENTO GEDO
  public static final String SELECCIONAR_TODOS = "--TODOS--";
  public static final String REDIRECT = "REDIRECT";

  // Constantes para identificacion de operaciones, en envio de eventos.
  public static final String CONFIRMACION_EJECUCION_TAREA = "confirmacionEjecucionTarea";
  public static final String HABILITADO_MODIFICACION = "habilitado modificar";
  // Documento
  public static final String DOCUMENTOS_SIN_PASE = "Sin Pase";
  public static final String DOCUMENTOS_CON_PASE = "Con Pase";
  public static final String DOCUMENTOS_FILTRO = "Filtro";
  public static final String PERMISO_MODIFICADOR = "sade.modificacion";
  public static final String PERMISO_SIGA_MESA = "SIGA.MESA";
  public static final String PERMISO_ARCHIVADOR = "SA.ARCHIVADOR";

  // Reservade expedientes
  public static final String RESERVA_PARCIAL = "PARCIAL";
  public static final String RESERVA_TOTAL = "TOTAL";
  public static final String SIN_RESERVA = "SIN RESERVA";
  public static final String RESERVA_EN_TRAMITACION = "RESERVA EN TRAMITACION";

  public static final String EVENTO_USUARIOS_FIRMANTES = "eventoUsuariosFirmantes";
  public static final String EVENTO_ARCHIVO_TRABAJO = "eventoArchivoTrabajo";

  public static final String IMG_ES_FIRMA_CONJUNTA = "/imagenes/IconoFirmaConjunta.png";
  public static final String IMG_TIENE_TOKEN = "/imagenes/IconoToken.png";
  public static final String IMG_ES_CONFIDENCIAL = "/imagenes/IconoConfidencial.png";
  public static final String IMG_ES_FIRMA_EXTERNA = "/imagenes/IconoFirmaExterna.png";
  public static final String IMG_ES_ESPECIAL = "/imagenes/IconoEspecial.png";
  public static final String IMG_TIENE_TEMPLATE = "/imagenes/IconoTemplate.png";

  public static final String PETICION_PENDIENTE_GEDO = "PETICION_PENDIENTE_GEDO";
  public static final String TIPO_ACTIVIDIDAD_RESULTADO_TAD = "Resultado de subsanación a TAD";
  public static final String TIPO_ACTIVIDAD_APROBACION_SUBSANACION_TAD = "Aprobación de Subsanacion";

  public static final String MEMORANDUM = "ME";
  public static final String NOTA = "NO";

  public static final String ESTADO_PENDIENTE = "PENDIENTE";
  public static final String ESTADO_PENDIENTE_INICIACION = "Pendiente Iniciacion";
  public static final String ESTADO_ABIERTA = "ABIERTA";

  public static final String ACTUACION_EX = "EX";
  public static final String ACTUACION_LE = "LE";
  public static final String ACTUACION_LP = "LP";

  public static final String MODULO_EE = "ee";

  public static final String DESTINO_TITULAR = "TITULAR";
  public static final String DESTINO_INTERVINIENTE = "INTERVINIENTE";

  public static final String MOTIVO_PASE_AVOCACION = "Avocación";
  public static final String MOTIVO_PASE_ASIGNACION = "Asignación";

  public static final String SISTEMA_BAC = "BAC";
  public static final String SISTEMA_EE = "EE";
  public static final String SISTEMA_AFJG = "AFJG";

  public static final String USUARIO_GENERICO_GENERACION_AUDITORIA_ARCH = "CIERRE_ACTIVIDADES_ARCH";

  public static final String RECTORA_CONFIDENCIAL = "EE.CONFIDENCIAL.RECTORA";

  public static final String REPARTICION_CONFIDENCIAL = "EE.CONFIDENCIAL.REPARTICION";

  public static final String SECTOR_CONFIDENCIAL = "EE.CONFIDENCIAL.SECTOR";

  public static final Integer ARCHIVO_TRABAJO_RESERVADO = 1;

  public static final String GENERAR_COPIA_ESTADO_PENDIENTE = "PENDIENTE";
  public static final String GENERAR_COPIA_ESTADO_CANCELADA = "CANCELADA";
  public static final String GENERAR_COPIA_ESTADO_RESUELTA = "RESUELTA";
  public static final String RESULT = "RESULT";

}
