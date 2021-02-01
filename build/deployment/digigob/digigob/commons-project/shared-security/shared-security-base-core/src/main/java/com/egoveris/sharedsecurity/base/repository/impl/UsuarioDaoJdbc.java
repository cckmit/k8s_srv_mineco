package com.egoveris.sharedsecurity.base.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.repository.IUsuarioDao;
import com.egoveris.sharedsecurity.base.repository.IUsuarioLdapDao;

public class UsuarioDaoJdbc extends JdbcDaoSupport implements IUsuarioDao {

  @Autowired
  protected IUsuarioLdapDao ldapAccessor;

  private static final String QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1 = "SELECT "
      + "COALESCE(REPASEL.CODIGO_REPARTICION, RTRIM(LTRIM(REPASEL.CODIGO_REPARTICION)), RTRIM(LTRIM(R.CODIGO_REPARTICION))) CODIGO_REPARTICION, "
      + "RTRIM(LTRIM(R.CODIGO_REPARTICION)) CODIGO_REPARTICION_ORIGINAL, "
      + "R.NOMBRE_REPARTICION NOMBRE_REPARTICION, "
      + "COALESCE(REPASEL.CODIGO_SECTOR_INTERNO, RTRIM(LTRIM(REPASEL.CODIGO_SECTOR_INTERNO)), RTRIM(LTRIM(SI.CODIGO_SECTOR_INTERNO))) CODIGO_SECTOR_INTERNO, "
      + "RTRIM(LTRIM(SI.CODIGO_SECTOR_INTERNO)) CODIGO_SECTOR_INTERNO_ORIGINAL, "
      + "DU.USER_SUPERIOR, DU.USUARIO_ASESOR USUARIO_REVISOR,"
      + "DU.OCUPACION, DU.USUARIO, DU.MAIL, PE.APODERADO, CA.CARGO, DU.ACEPTACION_TYC, RTRIM(LTRIM(DU.CODIGO_SECTOR_INTERNO)) "
      + "EXTERNALIZAR_FIRMA_EN_GEDO, EXTERNALIZAR_FIRMA_EN_SIGA ,EXTERNALIZAR_FIRMA_EN_CCOO, EXTERNALIZAR_FIRMA_EN_LOYS, "
      + "NUMERO_CUIT, APELLIDO_NOMBRE, COALESCE(MR.NOMBRE_USUARIO, 1) AS IS_MULTIREPARTICION, NOTIFICAR_SOLICITUD_PF "
      + "FROM ";

  private static final String TABLA_DATOS_USUARIO = " EDT_DATOS_USUARIO";

  private static final String TABLA_DATOS_USUARIO_REVISION = " EDT_DATOS_USUARIO_HIST ";

  private static final String QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2 = " DU INNER JOIN EDT_SADE_SECTOR_USUARIO SU ON DU.USUARIO = SU.NOMBRE_USUARIO "
      + "INNER JOIN EDT_SADE_SECTOR_INTERNO SI ON SI.ID_SECTOR_INTERNO=SU.ID_SECTOR_INTERNO "
      + "INNER JOIN EDT_SADE_REPARTICION R ON SI.CODIGO_REPARTICION=R.ID_REPARTICION "
      + "LEFT JOIN EDT_CARGOS CA  ON CA.ID = DU.CARGO " + "LEFT JOIN (SELECT USUARIO, APODERADO "
      + "FROM EDT_PERIODO_LICENCIA PE " + "WHERE PE.FECHA_HORA_DESDE <= SYSDATE() "
      + "AND PE.FECHA_HORA_HASTA >= SYSDATE() " + "AND PE.CONDICION_PERIODO <> 'TERMINADO' "
      + "AND PE.FECHA_CANCELACION IS NULL) PE ON  DU.USUARIO = PE.USUARIO " + "LEFT JOIN ( "
      + "SELECT NOMBRE_USUARIO FROM EDT_SADE_USR_REPA_HABILITADA " + "GROUP BY NOMBRE_USUARIO "
      + ") MR ON MR.NOMBRE_USUARIO = DU.USUARIO " + "	 LEFT JOIN ("
      + "    SELECT NOMBRE_USUARIO, SR.CODIGO_REPARTICION, NOMBRE_REPARTICION, CODIGO_SECTOR_INTERNO FROM EDT_SADE_REPARTICION_SELECCIONADA RS "
      + "    INNER JOIN EDT_SADE_REPARTICION SR ON RS.ID_REPARTICION = SR.ID_REPARTICION "
      + "	INNER JOIN EDT_SADE_SECTOR_INTERNO SSI ON RS.ID_SECTOR_INTERNO = SSI.ID_SECTOR_INTERNO "
      + "  ) REPASEL ON REPASEL.NOMBRE_USUARIO = DU.USUARIO " + "WHERE SU.ESTADO_REGISTRO = 1 "
      + " AND SI.ESTADO_REGISTRO = 1 AND R.ESTADO_REGISTRO = 1 AND R.VIGENCIA_DESDE<=? AND R.VIGENCIA_HASTA >=? AND SI.VIGENCIA_DESDE<=? "
      + "AND SI.VIGENCIA_HASTA>=? ";

  private static final String QUERY_TIENE_REPARTICIONES_HABILITADAS_USUARIO = "SELECT COUNT(*) "
      + "FROM EDT_SADE_USR_REPA_HABILITADA " + "WHERE NOMBRE_USUARIO = ?";

  private static final String QUERY_REPARTICIONES_HABILITADAS_USUARIO = "SELECT CODIGO_REPARTICION "
      + "FROM EDT_SADE_USR_REPA_HABILITADA RH INNER JOIN EDT_SADE_REPARTICION R "
      + "ON RH.ID_REPARTICION = R.ID_REPARTICION " + "WHERE NOMBRE_USUARIO = ?";

  private static final String FILTRO_USERNAME_SUPERVISADO = " AND DU.USER_SUPERIOR = ?";
  
  private static final String FILTRO_USERNAME_SECTOR = " AND DU.CODIGO_SECTOR_INTERNO = ?";

  private static final String FILTRO_USERNAME_REVISOR = " AND DU.USUARIO_ASESOR = ?";

  private static final String FILTRO_USERNAME_APODERADO = " AND PE.APODERADO = ?";

  private static final String FILTRO_USERNAME = " AND SU.NOMBRE_USUARIO = ?";

  private static final String QUERY_TIENE_LICENCIA = "SELECT COUNT(*) " 
      + "FROM EDT_PERIODO_LICENCIA "
      + "WHERE EDT_PERIODO_LICENCIA.USUARIO = ? AND DATE_FORMAT(EDT_PERIODO_LICENCIA.FECHA_HORA_DESDE, '%Y-%m-%D %H:%i:%s') <= DATE_FORMAT(?, '%Y-%m-%D %H:%i:%s') "
      + "AND DATE_FORMAT(EDT_PERIODO_LICENCIA.FECHA_HORA_HASTA, '%Y-%m-%D %H:%i:%s') >= DATE_FORMAT(?, '%Y-%m-%D %H:%i:%s') AND CONDICION_PERIODO <> ? "
      + "AND FECHA_CANCELACION IS NULL";

  private static final String FILTRO_ULTIMA_REVISION = " AND DU.REVISION = (SELECT MAX(REVISION) FROM DATOS_USUARIO_HIST D WHERE D.USUARIO LIKE ? ) ";

  private Usuario mapUsuarioBean(Map<String, Object> row) {
    Usuario usuario = new Usuario();

    usuario.setUsername((String) row.get("usuario"));
    usuario.setEmail((String) row.get("mail"));
    usuario.setNombreApellido((String) row.get("apellido_nombre"));
    usuario.setSupervisor((String) row.get("user_superior"));
    usuario.setCodigoReparticion((String) row.get("codigo_reparticion"));
    usuario.setCodigoReparticionOriginal((String) row.get("codigo_reparticion_original"));
    usuario.setCuit((String) row.get("numero_cuit"));
    usuario.setApoderado((String) row.get("apoderado"));
    usuario.setCodigoSectorInterno((String) row.get("codigo_sector_interno"));
    usuario.setSectorMesa((String) row.get("sector_mesa"));
    usuario.setOcupacion((String) row.get("ocupacion"));
    usuario.setNombreReparticionOriginal((String) row.get("nombre_reparticion"));
    usuario.setUsuarioRevisor((String) row.get("usuario_revisor"));
    usuario.setCargo((String) row.get("cargo"));
    usuario.setCodigoSectorInternoOriginal((String) row.get("codigo_sector_interno_original"));

    String notificar = (String) row.get("NOTIFICAR_SOLICITUD_PF");
    String big1 = new String("1");
    Integer int1 = new Integer(1);
    
    usuario.setNotificarSolicitudPF(false);
    
    if (notificar != null && notificar.equals(big1)) {
      usuario.setNotificarSolicitudPF(true);
    }

    if (StringUtils.isBlank(usuario.getNombreApellido())) {
      List<UsuarioReducido> usuarios = ldapAccessor.buscarUsuarioPorCn(usuario.getUsername());
      
      if (!usuarios.isEmpty()) {
        usuario.setNombreApellido(usuarios.get(0).getNombreApellido());
      }
    }

    boolean gedo = false;
    
    if (row.get("externalizar_firma_en_gedo") != null) {
      if (((String) row.get("externalizar_firma_en_gedo")).equals(big1)) {
        gedo = true;
      }
    }
    
    usuario.setExternalizarFirmaGEDO(gedo);
    boolean ccoo = false;
    
    if (row.get("externalizar_firma_en_ccoo") != null) {
      if (((Integer) row.get("externalizar_firma_en_ccoo")).equals(int1)) {
        ccoo = true;
      }
    }
    
    usuario.setExternalizarFirmaCCOO(ccoo);
    boolean siga = false;
    
    if (row.get("externalizar_firma_en_siga") != null) {
      if (((Integer) row.get("externalizar_firma_en_siga")).equals(int1)) {
        siga = true;
      }
    }
    
    usuario.setExternalizarFirmaSIGA(siga);
    boolean loys = false;
    
    if (row.get("externalizar_firma_en_loys") != null) {
      if (((Integer) row.get("externalizar_firma_en_loys")).equals(int1)) {
        loys = true;
      }
    }
    
    usuario.setExternalizarFirmaLOYS(loys);
    boolean tyc = false;
    
    if (row.get("aceptacion_tyc") != null) {
      if (((Integer) row.get("aceptacion_tyc")).equals(int1)) {
        tyc = true;
      }
    }
    
    usuario.setAceptacionTYC(tyc);
    boolean mr = false;
    
    if (row.get("IS_MULTIREPARTICION") != null) {
      if (((String) row.get("IS_MULTIREPARTICION")).equals(big1)) {
        mr = true;
      }
    }
    
    usuario.setIsMultireparticion(mr);
    return usuario;
  }

  public Usuario obtenerUsuario(String username) {
    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }
    
    try {
    	Date date = new Date();
      StringBuilder consulta = new StringBuilder();

      consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
      consulta.append(TABLA_DATOS_USUARIO);
      consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
      consulta.append(FILTRO_USERNAME);

      Map<String, Object> row = getJdbcTemplate().queryForMap(consulta.toString(),
          new Object[] { date, date, date, date, username });
      return mapUsuarioBean(row);
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.error(e.getMessage(), e);
      return null;
    }
  }

  public List<Usuario> obtenerUsuariosPorSupervisor(String username) {
    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }

    Date date = new Date();
    List<Usuario> listUsuarios = new ArrayList<>();
    StringBuilder consulta = new StringBuilder();

    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
    consulta.append(TABLA_DATOS_USUARIO);
    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
    consulta.append(FILTRO_USERNAME_SUPERVISADO);

    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(consulta.toString(),
        new Object[] { date, date, date, date, username });
    
    for (Map<String, Object> row : rows) {
      Usuario usuarioBean = mapUsuarioBean(row);
      listUsuarios.add(usuarioBean);
    }

    return listUsuarios;
  }

  @Override
  public int obtenerCantidadDeReparticionHabilitadaPorNombre(String username) {
    int cantidadDeReparticionesEncontradas = 0;
    
    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }
    
    cantidadDeReparticionesEncontradas = getJdbcTemplate().queryForObject(
          QUERY_TIENE_REPARTICIONES_HABILITADAS_USUARIO, new Object[] { username }, Integer.class);

    return cantidadDeReparticionesEncontradas;
  }

  @Override
  public List<String> obtenerReparticionesHabilitadasPorNombreUsuario(String username) {
    List<String> listRepas = new ArrayList<String>();
    
    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }

    try {
      List<Map<String, Object>> rows = getJdbcTemplate()
          .queryForList(QUERY_REPARTICIONES_HABILITADAS_USUARIO, new Object[] { username });
      for (Map<String, Object> row : rows) {
        if (row.get("codigo_reparticion") != null) {
          listRepas.add((String) row.get("codigo_reparticion"));
        }
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage(), e);
    }

    return listRepas;
  }

  @Override
  public List<Usuario> obtenerUsuarios() {
    List<Usuario> listUsuarios = new ArrayList<>();

    Date date = new Date();
    StringBuilder consulta = new StringBuilder();

    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
    consulta.append(TABLA_DATOS_USUARIO);
    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
    
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(consulta.toString(),
				new Object[] { date, date, date, date });
    
    for (Map<String, Object> row : rows) {
      Usuario usuarioBean = mapUsuarioBean(row);
      listUsuarios.add(usuarioBean);
    }

    return listUsuarios;
  }

  @Override
  public boolean licenciaActiva(String username, Date fecha) {
    int cantidad = 0;
    
    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }
    
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fechaQuery = dt.format(fecha);
    
    try {
      cantidad = getJdbcTemplate().queryForObject(QUERY_TIENE_LICENCIA,
          new Object[] { username, fechaQuery, fechaQuery, "terminado" }, Integer.class);
    } catch (DataAccessException e) {
      logger.error(e.getMessage(), e);
    }
    
    return cantidad > 0;
  }
  
  @Override
  public Usuario obtenerUsuarioMigracion(String username) {

    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }
    try {

    	Date date = new Date();
      StringBuilder consulta = new StringBuilder();

      consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
      consulta.append(TABLA_DATOS_USUARIO_REVISION);
      consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
      consulta.append(FILTRO_ULTIMA_REVISION);
      consulta.append(FILTRO_USERNAME);

      Map<String, Object> row = getJdbcTemplate().queryForMap(consulta.toString(),
          new Object[] { date, date, date, date, username, username });
      
      return mapUsuarioBean(row);
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.error(e.getMessage(), e);
      return null;
    }
  }

  public List<Usuario> obtenerUsuariosPorRevisor(String username) {

    if (username == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }

    List<Usuario> listUsuarios = new ArrayList<Usuario>();
    Date date = new Date();
    StringBuilder consulta = new StringBuilder();

    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
    consulta.append(TABLA_DATOS_USUARIO);
    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
    consulta.append(FILTRO_USERNAME_REVISOR);

    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(consulta.toString(),
				new Object[] { date, date, date, date, username  });
    
    for (Map<String, Object> row : rows) {
      Usuario usuarioBean = mapUsuarioBean(row);
      listUsuarios.add(usuarioBean);
    }

    return listUsuarios;
  }

  public List<Usuario> obtenerUsuariosPorApoderado(String nombreUsuario) {
    if (nombreUsuario == null) {
      throw new IllegalArgumentException("Username no puede ser nulo");
    }

    List<Usuario> listUsuarios = new ArrayList<>();
    Date date = new Date();
    StringBuilder consulta = new StringBuilder();

    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
    consulta.append(TABLA_DATOS_USUARIO);
    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
    consulta.append(FILTRO_USERNAME_APODERADO);

    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(consulta.toString(),
				new Object[] { date, date, date, date, nombreUsuario  });
    
    for (Map<String, Object> row : rows) {
      Usuario usuarioBean = mapUsuarioBean(row);
      listUsuarios.add(usuarioBean);
    }

    return listUsuarios;
  }
  
  public List<Usuario> obtenerUsuariosPorSector(String sector) {
	    if (sector == null) {
	      throw new IllegalArgumentException("Sector no puede ser nulo");
	    }

	    List<Usuario> listUsuarios = new ArrayList<Usuario>();
	    Date date = new Date();
	    StringBuffer consulta = new StringBuffer();

	    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE1);
	    consulta.append(TABLA_DATOS_USUARIO);
	    consulta.append(QUERY_DATOSUSUARIO_REPARTICION_SECTOR_PARTE2);
	    consulta.append(FILTRO_USERNAME_SECTOR);

	    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(consulta.toString(),
					new Object[] { date, date, date, date, sector  });
	    
	    for (Map<String, Object> row : rows) {
	      Usuario usuarioBean = mapUsuarioBean(row);
	      listUsuarios.add(usuarioBean);
	    }

	    return listUsuarios;
	  }
  
}
