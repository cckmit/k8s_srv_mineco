package com.egoveris.edt.web.common;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.edt.base.service.admin.IAdministracionSistemasService;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.TipoUsuarioEnum;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Messagebox;

public class BaseComposer extends GenericForwardComposer {

  protected static final Logger logger = LoggerFactory.getLogger(BaseComposer.class);

  /**
   * Composer base para implementar funcionalidades comunes de manejo de usuario
   * y session para EU
   */
  private static final long serialVersionUID = 1L;

  private IAdministracionSistemasService administracionSistemasService;

  private String username;
  private TipoUsuarioEnum tipoUsuario;
  private Session session;
  private Usuario currentUser;
  private String urlEscritorioUnico;
  private List<String> aplicaionesAdministradas;
  private String ministerio_es_jurisdiccion;
  private String nombres_prohibidos;
  protected HashMap<String, String> nombresMap;
  private String alsLimitado;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    session = Executions.getCurrent().getDesktop().getSession();
    this.currentUser = Utilitarios.obtenerUsuarioActual();
    setUsername(this.currentUser.getUsername());
    this.setTipoUsuario(Utilitarios.obtenerTipoUsuario());
    urlEscritorioUnico = (String) SpringUtil.getBean("urlEU");

    administracionSistemasService = (IAdministracionSistemasService) SpringUtil
        .getBean("administracionSistemasService");
    aplicaionesAdministradas = administracionSistemasService
        .obtenerSistemasPorUsuario(this.currentUser.getUsername());
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    this.ministerio_es_jurisdiccion = appProperty.getString("eu.estructura.ministerio");
    this.nombres_prohibidos = appProperty.getString("eu.usu.nombres_prohibidos");
    this.alsLimitado = appProperty.getString("eu.mig.als.limitado");
    generarNomresProhibidos(this.nombres_prohibidos);

  }

  protected void generarNomresProhibidos(String nombres) {
    String d = "[,]";
    this.nombresMap = new HashMap<String, String>();
    String[] vector = nombres.split(d);
    for (int i = 0; i < vector.length; i++) {

      this.nombresMap.put(vector[i], vector[i]);
    }

  }

  protected void mostrarError(String mensaje) {
    Messagebox.show(mensaje, Labels.getLabel("eu.header.titulo"), Messagebox.OK, Messagebox.ERROR);
  }

  protected void mostrarWarning(String mensaje) {
    Messagebox.show(mensaje, Labels.getLabel("eu.header.titulo"), Messagebox.OK,
        Messagebox.EXCLAMATION);
  }

	public ReparticionDTO getReparticionUsadaEnSesion() {
		return (ReparticionDTO) getSession().getAttribute(ConstantesSesion.REPARTICION_USADA_LOGIN);
	}

  protected void reloadPagina() {
    Executions.sendRedirect(urlEscritorioUnico);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public TipoUsuarioEnum getTipoUsuario() {
    return tipoUsuario;
  }

  public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
    this.tipoUsuario = tipoUsuario;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public Usuario getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(Usuario currentUser) {
    this.currentUser = currentUser;
  }

  public List<String> getAplicaionesAdministradas() {
    if (aplicaionesAdministradas == null) {
      aplicaionesAdministradas = new ArrayList<String>();
    }
    return aplicaionesAdministradas;
  }

  public void setAplicaionesAdministradas(List<String> aplicaionesAdministradas) {
    this.aplicaionesAdministradas = aplicaionesAdministradas;
  }

  public void setMinisterio_es_jurisdiccion(String ministerio_es_jurisdiccion) {
    this.ministerio_es_jurisdiccion = ministerio_es_jurisdiccion;
  }

  public String getMinisterio_es_jurisdiccion() {
    return ministerio_es_jurisdiccion;
  }

  public void setNombres_prohibidos(String nombres_prohibidos) {
    this.nombres_prohibidos = nombres_prohibidos;
  }

  public String getNombres_prohibidos() {
    return nombres_prohibidos;
  }

  public void setNombresMap(HashMap<String, String> nombresMap) {
    this.nombresMap = nombresMap;
  }

  public HashMap<String, String> getNombresMap() {
    return nombresMap;
  }

  public void setAlsLimitado(String alsLimitado) {
    this.alsLimitado = alsLimitado;
  }

  public String getAlsLimitado() {
    return alsLimitado;
  }

}
