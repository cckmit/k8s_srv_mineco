package com.egoveris.te.base.util;

public class ConstantesSatra {
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

  // CONSTANTES DE ESTADOS DE UNA TAREA DE UN EXPEDIENTE ELECTR�NICO
  public static final String ESTADO_PARALELO = "Paralelo";
  public static final String ESTADO_ADQUIRIDA = "Adquirida";
  public static final String ESTADO_INICIACION = "Iniciacion";
  public static final String ESTADO_TRAMITACION = "Tramitacion";
  public static final String ESTADO_SUBSANACION = "Subsanacion";
  public static final String ESTADO_COMUNICACION = "Comunicacion";
  public static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  public static final String ESTADO_SOLICITUD_ARCHIVO ="Solicitud Archivo";
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
  
  // CONSTANTES DE VISUALIZACI�N
  // Color que adquiriran las filas que han sido modificadas en una
  // tramitacion paralela y no han sido dadas definitivas.
  // MANTIS 689: Adquisicion de tarea post pase multiple. 
  // Identificacion de modificaciones realizadas
  // Identificar con un color o de alguna manera aquellas
  // modificaciones a un EX que haya sido tramitado en paralelo y advocado por
  // su emisor.
  public static final String COLOR_ILUMINACION_FILA = "#FBDB76";

  //TODO estructurar como enum o similar
  public static final String ROL_ADMIN_CENTRAL = "ADMIN.CENTRAL";
  public static final String ROL_DESARCHIVADOR = "EE.DESARCHIVAR";
  public static final String ROL_ADMINISTRADOR_PASES = "EE.ADMINISTRADOR.PASES";
  public static final String ROL_FUSIONADOR = "EE.FUSIONADOR"; 
  public static final String ROL_ASIGNADOR = "EE.ASIGNADOR";
  public static final String ROL_CARAT_SADE_INTERNO = "SADE.INTERNOS";
  public static final String ROL_CARAT_SADE_EXTERNO = "SADE.EXTERNOS";
  public static final String ROL_EE_CONFIDENCIAL = "EE.CONFIDENCIAL.RESERVA";
  public static final String ROL_HERRAMIENTAS = "EE.HERRAMIENTAS";
  
  //VARIABLE TRATA REPARTICION
  public static final String TODAS_REPARTICIONES_HABILITADAS = "--TODAS--";
  public static final String NOMBRE_REPARTICION_TODAS = "TODAS";
  public static final String ESTADO_INICIAR_EXPEDIENTE = "Iniciar Expediente";
  public static final String ESTADO_ANULAR_MODIFICAR_EXPEDIENTE ="Anular/Modificar Solicitud";
  public static final String TIPO_DOCUMENTO_GENERADO_PASE = "PASE";
  
  //Redireccion
  public static final String KEYWORD = "keyWord";
  public static final String PATHMAP = "PathMap";
  public static final String KEYWORD_TAREAS = "tareas";
  public static final String KEYWORD_EXPEDIENTES = "expedientes";
  public static final int CANTIDAD_ID_PERMITIDOS = 1;
  
  //VARIABLES DE TIPO DOCUMENTO GEDO
  public static final String SELECCIONAR_TODOS = "--TODOS--" ;
  public static final String REDIRECT = "REDIRECT";
  
  //Constantes para identificacion de operaciones, en envio de eventos.
  public static final String CONFIRMACION_EJECUCION_TAREA = "confirmacionEjecucionTarea";
  public static final String HABILITADO_MODIFICACION ="habilitado modificar";
  
  //Documento
  public static final String DOCUMENTOS_SIN_PASE = "Sin Pase";
  public static final String DOCUMENTOS_CON_PASE = "Con Pase";
  public static final String DOCUMENTOS_FILTRO = "Filtro";
  public static final String PERMISO_MODIFICADOR = "sade.modificacion";
  public static final String PERMISO_SIGA_MESA = "SIGA.MESA";
  public static final String PERMISO_ARCHIVADOR = "SA.ARCHIVADOR";
  
  // Reserva de expedientes
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
  
  public static final String MOTIVO_PASE_REASIGNACION = "Reasignación";
  public static final String MOTIVO_PASE_AVOCACION = "Avocación";
  public static final String MOTIVO_PASE_ASIGNACION = "Asignación";
  
  public static final String SISTEMA_BAC = "BAC";
  public static final String SISTEMA_EE = "EE";
  public static final String SISTEMA_AFJG = "AFJG";

  private ConstantesSatra() {
    // Private constructor
  }
}
