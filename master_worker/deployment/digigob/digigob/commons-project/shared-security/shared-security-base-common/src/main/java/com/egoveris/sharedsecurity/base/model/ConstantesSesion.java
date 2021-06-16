package com.egoveris.sharedsecurity.base.model;

/**
 * The Class Constantes.
 */
public final class ConstantesSesion {

  /** The Constant SESSION_USERNAME. */
  public static final String SESSION_USERNAME = "userName";
  
  /** The Constant SESSION_USER_SECTOR. */
  public static final String SESSION_USER_SECTOR = "sector";
  
  /** The Constant SESSION_USER_REPARTICION. */
  public static final String SESSION_USER_REPARTICION = "usernameReparticion";

  /** The Constant SESSION_USER_NOMBRE_APELLIDO. */
  public static final String SESSION_USER_NOMBRE_APELLIDO = "usernameNyA";

  /** The Constant SESSION_USUARIO_FRECUENCIA. */
  public static final String SESSION_USUARIO_FRECUENCIA = "usuarioFrecuencia";


  /** The Constant REPARTICION_USADA_LOGIN. */
  public static final String REPARTICION_USADA_LOGIN = "reparticionUsadaLogin";
  
  /** The Constant SESSION_LISTA_USUARIO_MIS_TAREAS. */
  public static final String SESSION_LISTA_USUARIO_MIS_TAREAS = "listaUsuariosMisTareas";

  /** The Constant SESSION_LISTA_USUARIO_MIS_SISTEMAS. */
  public static final String SESSION_LISTA_USUARIO_MIS_SISTEMAS = "listaUsuariosMisSistemas";

  /** The Constant SESSION_LISTA_USUARIO_MIS_SUPERVISADOS. */
  public static final String SESSION_LISTA_USUARIO_MIS_SUPERVISADOS = "listaUsuariosMisSupervisados";

  /** The Constant SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS. */
  public static final String SESSION_LISTA_USUARIO_BUZONGRUPAL_TAREAS = "listaBuzonTareas";

  /** The Constant SESSION_LISTA_SUPERVISADOS. */
  public static final String SESSION_LISTA_SUPERVISADOS = "listaSupervisados";

  /** The Constant TIEMPO_REFRESCO. */
  public static final String TIEMPO_REFRESCO = "tiempoRefresco";

  /** The Constant TIEMPO_REFRESCO_DEFAULT. */
  public static final String TIEMPO_REFRESCO_DEFAULT = "600000";

  /** The Constant APLICACION_TODA_LA_REPARTICION. */
  public static final String APLICACION_TODA_LA_REPARTICION = "EE";

  /** The Constant ROL_ASIGNADOR_REPARTICION. */
  public static final String ROL_ASIGNADOR_REPARTICION = "EU.ASIGNADOR.JEFE";

  /** The Constant ROL_SINDICATURA. */
  public static final String ROL_SINDICATURA = "SADE.SINDICATURA";

  /** The Constant ROL_ADMIN_CENTRAL. */
  public static final String ROL_ADMIN_CENTRAL = "ADMIN.CENTRAL";

  /** The Constant ROL_PROYECT_LIDER. */
  public static final String ROL_PROYECT_LIDER = "LIDER.PROYECTOS";

  /** The Constant LBL_NACION_GDE_MINISTERIO. */
  public static final String LBL_NACION_GDE_MINISTERIO = "Ministerio";

  /** The Constant LBL_GCBA_GDE_JURISDICCION. */
  public static final String LBL_GCBA_GDE_JURISDICCION = "* Jurisdicción";

  /** The Constant listaRefrescarEstructura. */
  public static final String LISTAREFRESCARESTRUCTURA = "listaRefrescarEstructura";

  /** The Constant listaRefrescarActuacion. */
  public static final String LISTAREFRESCARACTUACION = "listaRefrescarActuacion";

  /** The Constant KEY_ESTRUCTURA. */
  public static final String KEY_ESTRUCTURA = "estructura";

  /** The Constant KEY_ACTUACION. */
  public static final String KEY_ACTUACION = "actuacion";

  /** The Constant KEY_ESTRUCTURA_MODIFICAR. */
  public static final String KEY_ESTRUCTURA_MODIFICAR = "estructura_modificar";

  /** The Constant KEY_ACTUACION_MODIFICAR. */
  public static final String KEY_ACTUACION_MODIFICAR = "actuacion_modificar";

  /** The Constant COMPONENTE. */
  public static final String COMPONENTE = "componente";

  /** The Constant componente_actuacion. */
  public static final String COMPONENTE_ACTUACION = "componente_actuacion";
  
  /** The Constant ROL_ADMIN_LOCAL. */
  private static final String[] ROL_ADMIN_LOCAL = { "ADMIN.SECTOR", "SADE.ADMIN", "ADMIN.REPARTICION" };

  /** The Constant PRINCIPAL_PRIVILEGE. */
	private static final String[] PRINCIPAL_PRIVILEGE = { ROL_ADMIN_CENTRAL, ROL_PROYECT_LIDER, ROL_ADMIN_LOCAL[0],
			ROL_ADMIN_LOCAL[1], ROL_ADMIN_LOCAL[2] };
  
  /** The Constant KEY_BORRAR. */
  public static final String KEY_BORRAR = "borrar";

  /** The Constant KEY_BORRAR_ACTUACION. */
  public static final String KEY_BORRAR_ACTUACION = "borrar_actuacion";

  /** The Constant KEY_ESTRUCTURA_BORRAR. */
  public static final String KEY_ESTRUCTURA_BORRAR = "estructura_borrar";

  /** The Constant KEY_ACTUACION_BORRAR. */
  public static final String KEY_ACTUACION_BORRAR = "actuacion_borrar";

  /** The Constant TODOS_SISTEMAS. */
  public static final String TODOS_SISTEMAS = "Todos";

  /** The Constant SISTEMAS_SADE. */
  public static final String SISTEMAS_SADE = "SADE";

  /** The Constant DIAS_ANIO. */
  public static final int DIAS_ANIO = 366;

  /** The Constant REPARTICION_SELECCIONADA. */
  public static final String REPARTICION_SELECCIONADA = "reparticionSeleccionada";

  /** The Constant SECTOR_SELECCIONADO. */
  public static final String SECTOR_SELECCIONADO = "sectorSeleccionado";

  /** The Constant CARGO_SELECCIONADO. */
  public static final String CARGO_SELECCIONADO = "cargoSeleccionado";

  /** The Constant KEY_REPARTICION. */
  public static final String KEY_REPARTICION = "reparticion";

  /** The Constant KEY_MODIFICAR. */
  public static final String KEY_MODIFICAR = "modificar";

  /** The Constant KEY_MODIFICAR_ACTUACION. */
  public static final String KEY_MODIFICAR_ACTUACION = "modificar_actuacion";

  /** The Constant KEY_VISUALIZAR. */
  public static final String KEY_VISUALIZAR = "visualizar";

  /** The Constant KEY_VISUALIZAR_ACTUACION. */
  public static final String KEY_VISUALIZAR_ACTUACION = "visualizar_actuacion";

  /** The Constant KEY_ALTA. */
  public static final String KEY_ALTA = "alta";

  /** The Constant KEY_REPARTICION_MODIFICAR. */
  public static final String KEY_REPARTICION_MODIFICAR = "reparticionModificar";

  /** The Constant KEY_USUARIO_MODIFICAR. */
  public static final String KEY_USUARIO_MODIFICAR = "usuarioModificar";

  /** The Constant KEY_IS_OK. */
  public static final String KEY_IS_OK = "isOk";

  /** The Constant KEY_SECTOR. */
  public static final String KEY_SECTOR = "sector";

  /** The Constant PANEL_REPARTICIONES_HABILITADAS. */
  public static final String PANEL_REPARTICIONES_HABILITADAS = "/administrator/tabReparticionesHabilitadas.zul";

  /** The Constant PANEL_REPARTICIONES_ADMINISTRADAS. */
  public static final String PANEL_REPARTICIONES_ADMINISTRADAS = "/administrator/tabReparticionesAdministradas.zul";

  /** The Constant PANEL_REPARTICION_SECTOR_SELECTOR. */
  public static final String PANEL_REPARTICION_SECTOR_SELECTOR = "/administrator/reparticionSectorSelector.zul";

  /** The Constant KEY_USUARIO. */
  public static final String KEY_USUARIO = "usuario";

  /** The Constant KEY_CARGO. */
  public static final String KEY_CARGO = "cargo";

  /** The Constant KEY_TODAS_LAS_REPARTICIONES. */
  public static final String KEY_TODAS_LAS_REPARTICIONES = "todasLasReparticiones";

  /** The Constant PROCESO_SECTOR_USUARIO. */
  public static final String PROCESO_SECTOR_USUARIO = "SUME";


  /** The Constant KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO. */
  public static final String KEY_WINDOW_REPARTICIONES_ADMINISTRADAS_USUARIO = "windowReparticionesAdministradasDelUsuarioSeleccionado";

  /** The Constant ROL_SINCRONIZADOR_SOLR. */
  public static final String ROL_SINCRONIZADOR_SOLR = "EU.SINCRONIZADOR";

  /** The Constant KEY_REPARTICIONES_FILTRADAS. */
  public static final String KEY_REPARTICIONES_FILTRADAS = "reparticionesFiltradas";

  /** The Constant KEY_VISIBILIDAD_COMBO_SECTOR. */
  public static final String KEY_VISIBILIDAD_COMBO_SECTOR = "visibilidadComboSector";

  /** The Constant KEY_CARGAR_COMBO_REPARTICION. */
  public static final String KEY_CARGAR_COMBO_REPARTICION = "cargarComboReparticion";

  /** The Constant KEY_CARGAR_COMBO_SECTOR. */
  public static final String KEY_CARGAR_COMBO_SECTOR = "cargarComboReparticion";

  /** The Constant KEY_CARGAR_COMBO_CARGO. */
  public static final String KEY_CARGAR_COMBO_CARGO = "cargarComboCargo";

  /** The Constant KEY_HABILITAR_COMBO_REPARTICION. */
  public static final String KEY_HABILITAR_COMBO_REPARTICION = "habilitarComboReparticion";

  /** The Constant KEY_VISIBILIDAD_COMBO_CARGO. */
  public static final String KEY_VISIBILIDAD_COMBO_CARGO = "visibilidadComboCargo";

  /** The Constant KEY_HABILITAR_BANDBOXS. */
  public static final String KEY_HABILITAR_BANDBOXS = "habilitarBandBoxs";

  /** The Constant KEY_SECTOR_USUARIO. */
  public static final String KEY_SECTOR_USUARIO = "sectorUsuario";

  /** The Constant KEY_LISTA_TODOS_LOS_USUARIOS. */
  public static final String KEY_LISTA_TODOS_LOS_USUARIOS = "listaTodosLosUsuarios";

  /** The Constant KEY_EXISTE_DATOS_USUARIO. */
  public static final String KEY_EXISTE_DATOS_USUARIO = "existeDatosDeUsuario";

  /** The Constant KEY_MESA_ACTUALIZADA. */
  public static final String KEY_MESA_ACTUALIZADA = "__mesa_actualizada____";

  /** The Constant KEY_ALERTA_AVISO_DETALLE. */
  public static final String KEY_ALERTA_AVISO_DETALLE = "alertaAvisoDetalle";

  /** The Constant ALERTA_AVISO_COMBO_FILTRO_REFERENCIA. */
  public static final String ALERTA_AVISO_COMBO_FILTRO_REFERENCIA = "Referencia";

  /** The Constant ALERTA_AVISO_COMBO_FILTRO_FECHA. */
  public static final String ALERTA_AVISO_COMBO_FILTRO_FECHA = "Fecha";

  /** The Constant ALERTA_AVISO_COMBO_FILTRO_MOTIVO. */
  public static final String ALERTA_AVISO_COMBO_FILTRO_MOTIVO = "Motivo";

  /** The Constant ALERTA_AVISO_SELECCIONADA. */
  public static final String ALERTA_AVISO_SELECCIONADA = "alertaAvisoSeleccionada";

  /** The Constant KEY_REPARTICIONES_SOLO_LECTURA. */
  public static final String KEY_REPARTICIONES_SOLO_LECTURA = "soloLectura";

  /** The Constant KEY_REPARTICIONES_SOLO_LECTURA_SECTOR. */
  public static final String KEY_REPARTICIONES_SOLO_LECTURA_SECTOR = "soloLecturaSectorCombo";

  /** The Constant KEY_REPARTICIONES_SOLO_LECTURA_CARGO. */
  public static final String KEY_REPARTICIONES_SOLO_LECTURA_CARGO = "soloLecturaSectorCargo";

  /** The Constant PANEL_BAJA_REPARTICION. */
  // pantallas de migracion
  public static final String PANEL_BAJA_REPARTICION = "/administrator/tabsMigraciones/tabEliminarReparticion.zul";

  /** The Constant PANEL_BAJA_SECTOR. */
  public static final String PANEL_BAJA_SECTOR = "/administrator/tabsMigraciones/tabEliminarSector.zul";

  /** The Constant PANEL_BAJA_USUARIO. */
  public static final String PANEL_BAJA_USUARIO = "/administrator/tabsMigraciones/tabEliminarUsuario.zul";

  /** The Constant PANEL_MIGRAR_REPARTICION. */
  public static final String PANEL_MIGRAR_REPARTICION = "/administrator/tabsMigraciones/tabMigrarReparticion.zul";

  /** The Constant PANEL_MIGRAR_SECTOR. */
  public static final String PANEL_MIGRAR_SECTOR = "/administrator/tabsMigraciones/tabMigrarSector.zul";

  /** The Constant PANEL_MIGRAR_USUARIO. */
  public static final String PANEL_MIGRAR_USUARIO = "/administrator/tabsMigraciones/tabMigrarUsuario.zul";

  /** The Constant PANEL_CAMBIO_DE_SIGLA_REPARTICION. */
  public static final String PANEL_CAMBIO_DE_SIGLA_REPARTICION = "/administrator/tabsMigraciones/tabCambioDeSiglaReparticion.zul";

  /** The Constant PANEL_CAMBIO_DE_SIGLA_SECTOR. */
  public static final String PANEL_CAMBIO_DE_SIGLA_SECTOR = "/administrator/tabsMigraciones/tabCambioDeSiglaSector.zul";

  private static final String[] ESTADOS_NOVEDAD = { "Pendiente", "Activo", "Finalizado",
      "Eliminado" };

  /** The Constant ESTRUCTURA_SOLO_DESTINO. */
  public static final String ESTRUCTURA_SOLO_DESTINO = "SOLO DESTINO%";

  /** The Constant ESTRUCTURA_JUSTICIA_CABA. */
  public static final String ESTRUCTURA_JUSTICIA_CABA = "JUSTICIA CABA%";

  /** The Constant ESTRUCTURA_JUSTICIA_NAC_Y_PROV. */
  public static final String ESTRUCTURA_JUSTICIA_NAC_Y_PROV = "JUST. NAC. Y PROV.%";

  /** The Constant SOLO_DESTINO. */
  public static final String SOLO_DESTINO = "SOLO DESTINO";

  /** The Constant CARGO_TIPO_ALTO. */
  public static final String CARGO_TIPO_ALTO = "Alta";

  /** The Constant CARGO_TIPO_BAJA. */
  public static final String CARGO_TIPO_BAJA = "Baja";

  /** The Constant KEY_MOSTRAR_NOVEDADES. */
  public static final String KEY_MOSTRAR_NOVEDADES = "mostrarNovedades";

  /** The Constant ASIGNAR_PERMISO. */
  public static final int ASIGNAR_PERMISO = 586;

  /** The Constant LISTA_REPARTICIONES_UAI_PARAM. */
  // Constantes from the old edt-core-api
  public static final String LISTA_REPARTICIONES_UAI_PARAM = "listaRepartcionesUAI";

  /** The Constant FECHA_DESDE_PARAM. */
  public static final String FECHA_DESDE_PARAM = "fechaDesde";

  /** The Constant FECHA_HASTA_PARAM. */
  public static final String FECHA_HASTA_PARAM = "fechaHasta";

  /** The Constant LETRA_ACTUACION_PARAM. */
  public static final String LETRA_ACTUACION_PARAM = "letraActuacion";

  /** The Constant REPARTICION. */
  public static final String REPARTICION = "reparticiones";

  /** The Constant INICIO_CARGA_PARAM. */
  public static final String INICIO_CARGA_PARAM = "inicioCarga";

  /** The Constant TAMANO_PAGINACION_PARAM. */
  public static final String TAMANO_PAGINACION_PARAM = "tamanoPaginacion";

  /** The Constant CRITERIO_PARAM. */
  public static final String CRITERIO_PARAM = "criterio";

  /** The Constant ORDEN_PARAM. */
  public static final String ORDEN_PARAM = "orden";

  /** The Constant NUMERO_SADE_PARAM. */
  public static final String NUMERO_SADE_PARAM = "numeroSADE";

  /** The Constant SECTOR_INTERNO_PARAM. */
  public static final String SECTOR_INTERNO_PARAM = "sectorInterno";

  /** The Constant ON_CLOSE. */
  // Events
  public static final String ON_CLOSE = "onClose";

  /** The Constant ACTUALIZACION_CARGO. */
  // Origins
  public static final String ACTUALIZACION_CARGO = "ActualizacionCargosUsuarioComposer";

  /** The Constant SECTOR_INTERNO_ORIGEN_PARAM. */
  public static final Object SECTOR_INTERNO_ORIGEN_PARAM = "sectorInterno";

  /** The Constant LETRA_ACTUACION_TRACK_PARAM. */
  public static final Object LETRA_ACTUACION_TRACK_PARAM = "letraActuacionTrack";

  /** The Constant NUMERO_SADE_TRACK_PARAM. */
  public static final Object NUMERO_SADE_TRACK_PARAM = "numeroSADETrack";

  /** The Constant LISTA_REPARTICIONES_UAI_TRACK_PARAM. */
  public static final Object LISTA_REPARTICIONES_UAI_TRACK_PARAM = "listaRepartcionesUAI";

  /** The Constant FECHA_HASTA_TRACK_PARAM. */
  public static final Object FECHA_HASTA_TRACK_PARAM = "fechaHastaTrack";

  /** The Constant FECHA_DESDE_TRACK_PARAM. */
  public static final Object FECHA_DESDE_TRACK_PARAM = "fechaDesdeTrack";

  /** The Constant CARPETA_RAIZ_DOCUMENTOS. */
  public static final String CARPETA_RAIZ_DOCUMENTOS = "SADE";

  /** The Constant CODIGO_SECTOR_ARCHIVO. */
  public static final Object CODIGO_SECTOR_ARCHIVO = "ARCHIVO";

  /** The Constant CODIGO_SECTOR_ALS. */
  public static final Object CODIGO_SECTOR_ALS = "ALS";

  /** The Constant ROL_ASIGNADOR. */
  public static final String ROL_ASIGNADOR = "EE.ASIGNADOR";

  /** The Constant SUFIJO_SECTOR. */
  public static final String SUFIJO_SECTOR = "_MIG";

  /** The Constant BAJA. */
  public static final String BAJA = "Baja";

  /** The Constant CAMBIOSIGLA. */
  public static final String CAMBIOSIGLA = "Cambio de Sigla";

  /** The Constant TRASPASO. */
  public static final String TRASPASO = "Traspaso";

  /** The Constant NO. */
  public static final String NO = "N";

  /** The Constant SI. */
  public static final String SI = "S";

  /** The Constant PUNTO_CLASS_PESO. */
  public static final String PUNTO_CLASS_PESO = ".class$";
  
  /** The Constant DATEFORMAT. */
  public static final String DATEFORMAT = "dd/MM/yyyy";
  
  /** The constant KEY_EXISTE_DATO_USUARIO */
  public static final String KEY_EXISTE_DATO_USUARIO = "existeDatoUsuario";

  private ConstantesSesion() {
  }
  
  public static String[] getRolAdminLocal() {
    return ROL_ADMIN_LOCAL.clone();
  }
  
  public static String[] getEstadosNovedad() {
    return ESTADOS_NOVEDAD.clone();
  }

  public static String[] getPrincipales() {
    return PRINCIPAL_PRIVILEGE.clone();
  }
  
}