package com.egoveris.deo.util;

import java.util.HashSet;
import java.util.Set;

public class Constantes {

  public static final String ID_FIRMANTE = "idFirmante";

  public static final String NOMBRE_APLICACION = "GEDO";

  public static final String CONTENIDO_GENERACION_MANUAL_TXT = "txt";

  public static final String CONTENIDO_GENERACION_MANUAL_HTML = "html";

  public static final String CONTENIDO_GENEARCION_FIRMA_EXTERNA = "pdf";

  public static final String TIPO_CONTENIDO_ARCHIVO_FIRMA = "application/pdf";

  public static final String SUJETO_AVISO_MAIL = "Vencimiento de tareas de confección de documentos que no se encuentran firmados";

  public static final String SIGNATURE_RECTIFICACION = "signature_rectificacion";

  public static final String TEXTO_RECTIFICACION = "texto_rectificacion";

  public static final String TEXTO_MARGINACION = "texto_marginacion";

  public static final String TEXTO_TITULO = "texto_titulo";

  public static final String TITULO_MARGINACION = "REGISTRO DEL ESTADO CIVIL Y CAPACIDAD DE LAS PERSONAS";

  public static final String LOGO_MARGINACION = "logo_marginacion";

  public static final String NOMBRE_DOCUMENTO_1_TEMPORAL_CAMPO_REC = "nombreTemporal_documento1";

  public static final String NUMERO_SADE_DOCUMENTO_1_ORIGINAL = "numeroSADE_documento1_original";

  public static final Integer LONGITUD_MAXIMA_TEXTO_RECTIFICACION = 400; // 250;

  public static final String ANULACION = "/anulacion.png";

  public static final String RECTIFICACION_RC = "/registroCivil.png";

  public static final String PREVISUALIZACION = "/wm_gedo_preview.jpg";

  public static final String TASK = "tarea_actual";

  public static final String USER_ACTUAL = "usuario_actual";

  public static final String RECIBO_METRO = "recibo-metro.xdp";

  public static final String RECIBO_SUELDO = "recibo-sueldo.xdp";

  public static final String RECIBO_MEGC = "recibo-megc.xdp";

  public static final String PATH_RECIBOS_TEMP_LIVECYCLE = "D:\\temp\\recibo.pdf";

  public static final String PATH_TEMPLATES_RECIBOS = "D:\\Adobe\\Templates Recibo Sueldo";

  public static final String NUMERO_SADE_RECIBO = "numero_documento";

  public static final String MIGRACION_TAREA_USUARIO = "Continua el Supervisor";

  public static final String MIGRACION_BAJA_USUARIO_PF = "CDS -La tarea de firma se rechaza por baja del usuario ";

  public static final String ID_FILENET = "idFilenet";
  
  public static final String NAM_TE = "TE";

  public static final String GESTOR_DOCUMENTAL_WEBDAV = "WEBDAV";

  public static final String GESTOR_DOCUMENTAL_FILENET = "FILENET";

  public static final String ECOSISTEMA = "app.ecosistema";

  public static final String MAX_PREVIEW = "app.max.preview";

  public static final String GEDO_LIMPIEZA_TEMPORALES_TRIGGER = "GedoLimpiezaTemporalesTrigger";

  public static final String GEDO_LIMPIEZA_TEMPORALES_JOB_DETAIL = "GedoLimpiezaTemporalesJobDetail";

  public static final String GEDO_AVISO_MAIL_TRIGGER = "GedoAvisoMailTrigger";

  public static final String GEDO_AVISO_MAIL_JOB_DETAIL = "GedoAvisoMailJobDetail";

  public static final String GEDO = "GEDO";

  public static final String EJECUTA_GEDO_LIMPIEZA_TEMPORALES_JOB_DETAIL = "Ejecuta GedoLimpiezaTemporalesJobDetail";

  public static final String EJECUTA_GEDO_AVISO_MAIL_JOB_DETAIL = "Ejecuta GedoAvisoMailJobDetail";

  /*
   * Verificar tipos de archivos soportados.
   * http://www.adobe.com/products/livecycle/pdfgenerator/formats.html
   */
  public static final Set<String> extensionesPermitidas = new HashSet<String>();

  static {
    extensionesPermitidas.add("txt");
    extensionesPermitidas.add("rtf");
    extensionesPermitidas.add("doc");
    extensionesPermitidas.add("docx");
    extensionesPermitidas.add("xls");
    extensionesPermitidas.add("xlsx");
    extensionesPermitidas.add("ppt");
    extensionesPermitidas.add("pptx");
    extensionesPermitidas.add("odt");
    extensionesPermitidas.add("ods");
    extensionesPermitidas.add("odp");
    extensionesPermitidas.add("jpg");
    extensionesPermitidas.add("jpeg");
    extensionesPermitidas.add("pdf");
    extensionesPermitidas.add("png");
    extensionesPermitidas.add("tiff");
    extensionesPermitidas.add("tif");
    extensionesPermitidas.add("bmp");
    extensionesPermitidas.add("gif");
    extensionesPermitidas.add("vsd");
    extensionesPermitidas.add("html");

  }

  /**
   * Carpeta en webDav, en donde se almacenan los archivos temporales pendientes
   * de firma.
   */
  public static final String CARPETA_TEMPORAL_PDFS_FIRMA = "Proyectos_temporales/GEDO/SADE";
  /**
   * Carpeta en alfresco, en donde se almacenaran los archivos de trabajo
   * temporales
   */
  public static final String CARPETA_TEMPORAL_DOCUMENTO_DE_TRABAJO = "Proyectos_temporales/GEDO/Documentos_De_Trabajo";
  /**
   * Carpeta en alfresco, en donde se almacenaran los archivos embebidos
   * temporales
   */
  public static final String CARPETA_TEMPORAL_ARCHIVOS_EMBEBIDOS = "Proyectos_temporales/GEDO/Archivos_Embebidos";
  /**
   * Carpeta en alfresco, en donde se almacenaran los archivos embebidos
   */
  public static final String CARPETA_TEMPORAL_ARCHIVO_EMBEBIDO = "Proyectos_temporales/GEDO/Archivos_Embebidos";

  /**
   * Carpeta de WebDav, en donde se almacenaran los documento adjuntos
   * temporales
   */
  public static final String CARPETA_TEMPORAL_DOCUMENTO_ADJUNTO = "Proyectos_temporales/GEDO/Documentos_Adjuntos";

  /**
   * Carpeta raíz en WebDav dentro de la cual se guardan los archivos de
   * limpieza de temporales
   */
  public static final String CARPETA_RAIZ_LIMPIEZA_TEMPORALES = "Limpieza_Temporales";

  /**
   * Carpeta raíz en WebDav dentro de la cual se crea la estructura de espacios
   * que almacenarán los documentos definitivos.
   */
  public static final String CARPETA_RAIZ_DOCUMENTOS = "SADE";

  public static final String CARPETA_RAIZ_DOCUMENTOS_ORIGINAL = "SADE/ORIGINAL";

  /**
   * Carpeta raíz en WebDav dentro de la cual se crea la estructura de espacios
   * que almacenarán los documento de trabajo definitivos.
   */
  public static final String CARPETA_RAIZ_ARCHIVOS_TRABAJO = "Documento_De_Trabajo";

  /**
   * Valores por defecto campos creados desde itext en el documento pdf
   */
  /**
   * Transiciones de WorkFlow
   */
  public static final String TRANSICION_USO_PORTAFIRMA = "to Uso PortaFirma";
  public static final String TRANSICION_REVISAR = "to Revisar Documento";
  public static final String TRANSICION_ENVIAR_PORTAFIRMA = "to Enviar a Portafirma";
  public static final String TRANSICION_FIRMA_GEDO = "to Firmar Documento";
  public static final String TRANSICION_FIRMA_PENDIENTE = "to Firma Pendiente";
  public static final String TRANSICION_RECHAZADO = "to Rechazado";
  public static final String TRANSICION_CERRAR = "to Cerrar Documento";
  public static final String TRANSICION_CANCEL = "to cancel1";
  public static final String TRANSICION_ERROR_CIERRE = "to Error Cierre";
  public static final String TRANSICION_IMPORTAR_DOCUMENTO = "to Importar Documento";
  public static final String TRANSICION_CONFECCIONAR_DOCUMENTO = "to Confeccionar Documento";
  public static final String TRANSICION_ESPERA_REINTENTO_CIERRE = "to Espera Reintento Cierre";
  public static final String TRANSICION_REINTENTAR_CIERRE = "to Reintentar Cierre";
  public static final String TRANSICION_END = "to end1";
  public static final String TRANSICION_FORK = "to fork1";
  public static final String TRANSICION_RECHAZAR_DOCUMENTO = "to Rechazar Documento";
  public static final String TRANSICION_RECHAZADO_IMPORTACION = "to Rechazado Importado";
  public static final String TRANSICION_RECHAZO_TEMPLATE = "to Rechazo Template";
  public static final String TRANSICION_REVISAR_DOCUMENTO_FIRMA_CONJUNTA = "to Revisar Documento con Firma Conjunta";
  public static final String TRANSICION_REVISION_FIRMA_CONJUNTA = "to Revision Firma Conjunta";

  public static final String USUARIO_DADO_DE_BAJA_EN_TRACK_LDAP_O_CCOO = "Usuario Dado de Baja ";

  /**
   * Variables de WorkFlow
   */

  public static final String VAR_USUARIO_CREADOR = "usuarioCreador";
  public static final String VAR_MOTIVO = "motivo";
  public static final String VAR_USUARIO_ACTUAL = "usuarioActual";
  public static final String VAR_USUARIO_RECEPTOR_TAREA = "usuarioReceptorTarea";
  public static final String VAR_DOCUMENTO_DATA = "documentoMetadata";
  public static final String VAR_RECEPTORES_AVISO_FIRMA = "receptoresAvisoFirma";
  public static final String VAR_TIPO_DOCUMENTO = "tipoDocumento";
  public static final String VAR_DATA_FILE = "dataFile";
  public static final String VAR_NUMERO_FIRMAS = "numeroFirmas";
  public static final String VAR_ARCHIVO_TEMPORAL_FIRMA = "archivoTemporalFirma";
  public static final String VAR_MENSAJE_USUARIO = "mensajeUsuario";
  public static final String VAR_CONTENIDO = "contenido";
  public static final String VAR_USUARIO_PRODUCTOR = "usuarioProductor";
  public static final String VAR_MENSAJE_PRODUCTOR = "mensajeProductor";
  public static final String VAR_USUARIO_APODERADOR = "usuarioApoderador";
  public static final String VAR_USUARIOS_RESERVADOS = "usuariosReservados";
  public static final String VAR_COMUNICABLE = "tipoDocumentoComunicable";
  public static final String VAR_MENSAJE_DESTINATARIO = "mensajeDestinatario";
  public static final String VAR_USUARIOS_DESTINATARIOS = "usuariosDestinatarios";
  public static final String VAR_USUARIOS_DESTINATARIOS_COPIA = "usuariosDestinatariosCopia";
  public static final String VAR_USUARIOS_DESTINATARIOS_COPIA_OCULTA = "usuariosDestinatariosCopiaOculta";
  public static final String VAR_USUARIOS_DESTINATARIOS_EXTERNOS = "usuariosDestinatariosExternos";
  public static final String VAR_COMUNICACION = "comunicacion";
  public static final String VAR_RESPONDE_COMUNICACION = "respondeComunicacion";
  public static final String VAR_ID_COMUNICACION_RESPONDIDA = "idComunicacionRespondida";
  public static final String VAR_USUARIO_SUPERVISADO = "usuarioSupervisado";
  public static final String VAR_USUARIO_DESTINATARIO_LICENCIA = "usuarioDestinatarioLicnecia";
  public static final String VAR_ID_DESTINATARIO_COMUNICACION = "id_destinatario";
  public static final String VAR_ID_GUARDA_DOCUMENTAL = "idGuardaDocumental";
  public static final String VAR_ID_GUARDA_DOCUMENTAL_ORIGINAL = "idGuardaDocumentalOriginal";

  public static final String VAR_USUARIO_REVISOR = "usuarioRevisor";
  public static final String VAR_USUARIO_FIRMANTE = "usuarioFirmante";
  public static final String VAR_USUARIO_VERIFICADOR = "usuarioVerificador";
  public static final String VAR_USUARIO_VERIFICADOR_ORIGINAL = "usuarioVerificadorOriginal";
  public static final String VAR_TIENE_TEMPLATE = "tieneTemplate";
  public static final String VAR_USUARIO_DERIVADOR = "usuarioDerivador";
  public static final String VAR_MENSAJE_A_REVISOR = "mensajeARevisor";
  public static final String VAR_NUMERO_REINTENTOS_CIERRE = "reintentosCierre";
  public static final String VAR_TIMEOUT_REINTENTOS_CIERRE = "timeoutReintentosCierre";
  public static final String VAR_NUMERO_SADE = "numeroSADE";
  public static final String VAR_REINTENTO_HABILITADO = "reintentoHabilitado";
  public static final String VAR_MOTIVO_RECHAZO = "motivoRechazo";
  public static final String VAR_TAREA_RECHAZO_DOCUMENTO = "tareaRechazoDocumento";
  public static final String VAR_SOLICITUD_ENVIO_MAIL = "solicitudEnvioCorreo";
  public static final String VAR_SOLICITUD_ENVIO_MAIL_FAIL = "solicitudEnvioCorreoFail";
  public static final String VAR_NUMERO_SADE_PAPEL = "numeroSadePapel";
  public static final String VAR_SISTEMA_INICIADOR = "sistemaIniciador";
  public static final String VAR_NUMERO_SA = "numeroSade";
  
  public static final String VAR_DOCUMENTO_PUBLICABLE_ID = "documentoPublicableId";

  /**
   * Actividades workflow
   */

  public static final String ACT_ENVIAR_PORTAFIRMA = "Enviar a Portafirma";
  public static final String ACT_ESPERAR_REINTENTO = "Espera Reintento Cierre";
  public static final String ACT_CANCEL = "cancel1";
  public static final String TASK_FIRMAR_DOCUMENTO = "Firmar Documento";
  public static final String TASK_REVISAR_DOCUMENTO = "Revisar Documento";
  public static final String TASK_RECHAZADO = "Rechazado";
  public static final String ENVIAR_PORTA_FIRMA = "Enviar a Portafirma";
  public static final String ENVIO_PORTA_FIRMA = "Firmar Documento (Portafirma)";
  public static final String INICIAR_DOCUMENTO = "Iniciar Documento";
  public static final String IMPORTAR_DOCUMENTO = "Importar Documento";

  /**
   * Nombres de workflow
   */
  public static final String ACT_CONFECCIONAR = "Confeccionar Documento";
  public static final String ACT_REVISAR = "Revisar Documento";
  public static final String ACT_REVISAR_FIRMA_CONJUNTA = "Revisar Documento con Firma Conjunta";
  public static final String ACT_IMPORTAR = "Importar Documento";
  public static final String ACT_FIRMAR = "Firmar Documento";
  public static final String ACT_FIRMAR_PORTA_FIRMA = "Firmar Documento (Portafirma)";
  public static final String ACT_REASIGNAR_DOCUMENTO = "Reasignado";
  /**
   * Forms para tarea de rechazo
   */

  public static final String FORM_RECHAZADO_TEMPLATE = "taskViews/revisarDocumento.zul";
  public static final String FORM_RECHAZADO_NO_TEMPLATE = "taskViews/revisarDocumentoImportacion.zul";
  public static final String FORM_TAREA_FIRMA = "taskViews/firmarDocumento.zul";
  public static final String FORM_TAREA_VERIFICACION = "taskViews/verificarDocumento.zul";
  /**
   * Motivo de rechazo:
   */
  public static final String MOTIVO_RECHAZADO = "RECHAZADO: ";

  /**
   * Cancelacion:
   */
  public static final String MOTIVO_CANCELACION = "CANCELADO: ";

  /**
   * Corresponden al código de la repartición, que va a permitir idenfiticar que
   * todas las reparticiones están habilidatas para un tipo de documento
   * determinado.
   */
  public static final String TODAS_REPARTICIONES_HABILITADAS = "--TODAS--";
  public static final String NOMBRE_REPARTICION_TODAS = "TODAS";
  public static final String SESSION_USER_REPARTICION = "usernameReparticion";

  /**
   * Permisos de reparticiones habilitadas
   */

  /**
   * Constantes que indican que permiso se requiere validar, en las validaciones
   * de reparticiones habilitadas
   */
  public static final String REPARTICION_PERMISO_FIRMAR = "permisoFirmar";
  public static final String REPARTICION_PERMISO_INICIAR = "permisoIniciar";

  /**
   * Operaciones para registro de auditoria.
   */
  public static final String AUDITORIA_OP_BAJA = "BAJA";
  public static final String AUDITORIA_OP_MODIFICACION = "MODI";
  public static final String AUDITORIA_OP_ALTA = "ALTA";

  /**
   * Constantes para validación de tamaños de archivo para importación o
   * previsualización.
   */
  public static final int TAMANO_HOJA_FIRMA = 64719;
  public static final int FACTOR_CONVERSION = 1024;
  public static final int FACTOR_CONVERSION_MB = 1024 * 1024;

  public static final String ARCHIVO_CON_FIRMA_EXTERNA = "archivoConFirmaExterna";

  // tipos de settings de perfiles de livecycle
  public static final String ADOBESETTING = "AdobePdfSetting";
  public static final String FILESETTING = "FileTypeSetting";
  public static final String DEFAULTSETTING = "Standard";

  /**
   * Tareas.
   */
  public static final String PRODUCCION = "PRODUCIR";
  public static final String IMPORTACION = "IMPORTAR";
  public static final String REVISION = "REVISAR";
  public static final String FIRMA = "FIRMAR";

  /**
   * Suscripciones
   */
  public static final String ORIGEN_PORTAFIRMA = "PORTAFIRMA";
  public static final String ESTADO_OK = "OK";
  public static final String ESTADO_PENDIENTE = "PENDIENTE";
  public static final String NOMBRE_PROCESO = "DestinatariosAGedo";

  /**
   * Doble Factor
   */
  public static final String ESTADO_USADO = "USADO";
  public static final String MAIL_DOBLEFACTOR_SUBJECT = "Verificación de código de doble factor";
  
  /**
   * Tipo de produccion
   */
  public static final int TIPO_PRODUCCION_LIBRE = 1;
  public static final int TIPO_PRODUCCION_IMPORTADO = 2;
  public static final int TIPO_PRODUCCION_TEMPLATE = 3;
  public static final int TIPO_PRODUCCION_IMPORTADO_TEMPLATE = 4;

  public static final String MODO_RENDERIZADO_FIRMACONJUNTA = "Firma Conjunta";
  public static final String MODO_RENDERIZADO_USUARIOSRESERVADOS = "Usuarios Reservados";

  /**
   * Roles de EE para reserva de pases
   */

  public static final String CONFIDENCIAL_RECTORA = "EE.CONFIDENCIAL.RECTORA";
  public static final String CONFIDENCIAL_REPARTICION = "EE.CONFIDENCIAL.REPARTICION";
  public static final String CONFIDENCIAL_SECTOR = "EE.CONFIDENCIAL.SECTOR";

  /**
   * Truncated Result
   */
  public static final int MAX_REPARTICION_RESULTS = 100;

  // VARIABLES QUE VIAJAN EN SESSION
  public static final String SESSION_USERNAME = "userName";
  public static final String SESSION_USER_NOMBRE_APELLIDO = "usernameNyA";
  public static final String ROL_ADMIN_CENTRAL = "ADMIN.CENTRAL";
  public static final String ROL_DOCUMENTACION_CONFIDENCIAL = "GEDO.CONFIDENCIAL";
  public static final String IMAGEN_SIN_ARCHIVO_DE_TRABAJO = "/imagenes/NoArchivosDeTrabajo.png";
  public static final String IMAGEN_CON_ARCHIVO_DE_TRABAJO = "/imagenes/ANIMADO_ArchivosDeTrabajo.gif";
  public static final String PATHMAP = "PathMap";
  public static final String IMAGEN_CON_ARCHIVO_EMBEBIDO = "/imagenes/Archivos_Embebidos.gif";
  public static final String IMAGEN_SIN_ARCHIVO_EMBEBIDO = "/imagenes/SinArchivosEmbebidos.png";

  // VARIABLES DE REDIRECCION
  public static final String KEYWORD = "keyWord";
  public static final String KEYWORD_DOCUMENTOS = "documentos";
  public static final String KEYWORD_TAREAS = "tareas";
  public static final String KEYWORD_TAREAS_WORFLOW = "tareasworkflow";
  public static final int CANTIDAD_ID_PERMITIDOS = 1;

  // Time out Applet Firma
  public static final Integer TIMEOUT_APPLET_FIRMA = 120000;

  public static final String FORMATO_FECHA_SIN_SEC = "yyyy-MM-dd HH:mm";
  public static final String REDIRECT = "REDIR";

  public static final String EVENTO_ARCHIVO_TRABAJO = "eventoArchivoTrabajo";
  public static final String EVENTO_ARCHIVO_EMBEBIDO = "eventoArchivoEmbebido";
  public static final String EVENTO_TIPO_DOC_EMBEBIDOS = "eventoTipoDocumentoEmbebido";
  public static final String EVENTO_OBLIGATORIEDAD_EXTENSIONES = "eventoObligatoriedadExtensiones";
  public static final String EVENTO_USUARIOS_FIRMANTES = "eventoUsuariosFirmantes";
  public static final String EVENTO_USUARIOS_RESERVADOS = "eventoUsuariosReservados";
  public static final String EVENTO_DOCUMENTO_SADE = "incorporarDocumentoSade";
  public static final String EVENTO_ADICION_REVISOR = "eventoAdicionRevisor";
  public static final String EVENTO_ABM_DOCUMENTO_TEMPLATE = "abmDocumentoTemplate";
  public static final String EVENTO_ENVIAR_A_REVISAR = "eventoEnviarARevisar";
  public static final String EVENTO_ENVIAR_A_FIRMAR = "eventoEnviarAFirmar";
  public static final String EVENTO_INCORPORAR_SADE = "eventoIncorporarSade";
  public static final String EVENTO_DEFINIR_DESTINATARIOS = "eventoDefinirDestinatarios";

  public static final String IMG_ES_FIRMA_CONJUNTA = "/imagenes/IconoFirmaConjunta.png";
  public static final String IMG_TIENE_TOKEN = "/imagenes/IconoToken.png";
  public static final String IMG_ES_CONFIDENCIAL = "/imagenes/IconoConfidencial.png";
  public static final String IMG_ES_FIRMA_EXTERNA = "/imagenes/IconoFirmaExterna.png";
  public static final String IMG_ES_ESPECIAL = "/imagenes/IconoEspecial.png";
  public static final String IMG_ES_NOTIFICABLE = "/imagenes/iconoNotificable.png";
  public static final String IMG_TIENE_LIBRE = "/imagenes/IconoLibre.png";
  public static final String IMG_TIENE_TEMPLATE = "/imagenes/IconoTemplate.png";
  public static final String IMG_TIENE_IMPORTADO_TEMPLATE = "/imagenes/IconoImportadoTemplate.png";
  public static final String IMG_TIENE_IMPORTADO = "/imagenes/IconoImportado.png";
  public static final String IMG_COPIAR_OBLIGATORIO = "/imagenes/copiarObligatorio.png";
  public static final String IMG_COPIAR_NO_OBLIGATORIO = "/imagenes/copiarNoObligatorio.png";

  public static final String ACRONIMO_WORKFLOW = "procesoGEDO";
  public static final String CODIGO_ECOSISTEMA_SADE = "EGOV";
  
  public static final String PROCESO_LOG_ESTADO_ERROR = "ERROR";
  public static final String PROCESO_LOG_ESTADO_OK = "OK";
  public static final String PROCESO_LOG_ESTADO_EN_EJECUCION = "EN_EJECUCION";
  public static final String PROCESO_LOG_MENSAJE_OK = "Notificacion OK";
  
  public static final String SUSCRIPCION_ESTADO_REINTENTO = "REINTENTAR";
  public static final String SUSCRIPCION_ESTADO_OK = "OK";
  public static final String SUSCRIPCION_ESTADO_ERROR = "ERROR";
  public static final String SUSCRIPCION_ESTADO_PENDIENTE = "PENDIENTE";
  
  public static final String SISTEMA_ORIGEN_CAMPO_WORKFLOWID = "WORKFLOWID";
  public static final String SISTEMA_ORIGEN_CAMPO_MESSAGEID = "MESSAGEID";
  
  
  public static final String REVISAR_DOCUMENTO_CON_FIRMA_CONJUNTA = "Revisar Documento con Firma Conjunta";
  public static final String REVISAR_FIRMA = "Revisar Firma";
  public static final String REVISAR_DOCUMENTO_CON_CERTIFICADO = "Revisar Documento con certificado";
}
