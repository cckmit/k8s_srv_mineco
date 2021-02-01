package com.egoveris.sharedsecurity.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Service;
import org.zkoss.zk.ui.Executions;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UserConverter;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.model.UsuarioSolr;
import com.egoveris.sharedsecurity.base.repository.IUsuarioDao;
import com.egoveris.sharedsecurity.base.repository.IUsuarioLdapDao;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.sharedsecurity.base.service.IUsuarioSolrService;
import com.egoveris.sharedsecurity.base.util.Constantes;

@Service
public final class UsuarioServiceImpl implements IUsuarioService {

  private static Logger logger = LoggerFactory.getLogger(IUsuarioService.class);
  private static final String SOLR_FIELD_SUPERVISOR = "supervisor:";

  @Autowired
  protected IUsuarioDao usuarioDAO;

  @Autowired
  protected IUsuarioSolrService usuarioSolrService;

  @Autowired
  protected IUsuarioLdapDao ldapAccessor;

  @Autowired
  private LdapUserSearch userSearch;

  @Autowired
  private LdapAuthoritiesPopulator authoritiesPopulator;

  @Autowired
  private UserConverter userConverter;

  protected Map<String, Usuario> listUsuarios = new HashMap<String, Usuario>();

  private UserDetailsContextMapper userDetailsMapper = new LdapUserDetailsMapper();

  private Date ultimaActualizacion;

  @Autowired
  private String tiempoRefresco;

  private void cachearListaUsuarios() {

      if (ultimaActualizacion == null) {
        synchronized (this) {
          listUsuarios = usuarioSolrService.obtenerTodos();
          ultimaActualizacion = new Date();
        }
        return;
      }
      // PASADOS N MINUTOS DE LA ULTIMA ACTUALIZACION REFRESCO LA LISTA.
      Integer minutos = Integer.parseInt(tiempoRefresco);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.MINUTE, -minutos);

      Boolean actualizar = (calendar.getTime()).after(ultimaActualizacion);
      if (!actualizar)
        return;

      synchronized (this) {
        listUsuarios = usuarioSolrService.obtenerTodos();
        ultimaActualizacion = new Date();
      }
  }

  @Override
  public Collection<Usuario> obtenerUsuarios() {
    cachearListaUsuarios();
    List<Usuario> users = new ArrayList<Usuario>();
    for (Usuario u : listUsuarios.values()) {
      users.add(u);
    }
    return users;
  }

  @Override
  public Map<String, Usuario> obtenerMapaUsuarios() {
    cachearListaUsuarios();
    return listUsuarios;
  }

  public List<Usuario> obtenerUsuariosPorSupervisor(String nombreUsuario) {
      return usuarioDAO.obtenerUsuariosPorSupervisor(nombreUsuario);
  }

  public List<Usuario> obtenerUsuariosPorSector(String sector) {
      return usuarioDAO.obtenerUsuariosPorSector(sector);
  }
  
  @Override
  public List<UsuarioReducido> obtenerUsuariosDeSolr() {
    List<UsuarioReducido> listaUsuariosReducidos;
      listaUsuariosReducidos = usuarioSolrService
          .searchByQueryUsuarioReducido(Constantes.OBTENER_TODOS_SOLR);
      return listaUsuariosReducidos;
  }

  @Override
  public List<UsuarioReducido> obtenerUsuariosDeSolrSupervisados(String userName) {
    List<UsuarioReducido> listaUsuariosReducidos;
      listaUsuariosReducidos = usuarioSolrService
          .searchByQueryUsuarioReducido(SOLR_FIELD_SUPERVISOR + userName);
      return listaUsuariosReducidos;

  }

  /**
   * Este metodo se utiliza como legacy de aplicaciones que siguen utilizandolo.
   * 
   */
  @SuppressWarnings("unchecked")
	@Override
	public Usuario obtenerUsuario(String userName) throws UsernameNotFoundException {
		// Siempre consulta los permisos en Ldap
  	String name = userName.toUpperCase();
		UserDetails userDetails = obtenerUsuarioLdap(name);
		Usuario user = null;

		UsuarioSolr userSolr = usuarioSolrService.searchByUsername(name);
		if (userSolr != null) {
			// SI ESTA EN SOLR PERO AUN NO SE ENCUENTRAN INDEXADOS LOS DATOS DE
			// CCOO LO INDEXO.
			if (userSolr.getCuit() == null) {
				user = usuarioDAO.obtenerUsuario(name);
				if (user != null) {
					user = mapearDatosLdap(user, userDetails);
					userSolr = userConverter.cargarDTO(user);
					usuarioSolrService.addToIndex(userSolr);
				}
			}
			user = userConverter.cargarUsuario(userSolr);
			// SI NO ESTA EN SOLR LO BUSCO EN LDAP Y CCOO Y LO INDEXO.
		} else if (ldapAccessor.existeUsuario(name)) {
			user = usuarioDAO.obtenerUsuario(name);
			if (user == null) {
				user = new Usuario();
				user.setUsername(userDetails.getUsername());
			}
			user = mapearDatosLdap(user, userDetails);

			userSolr = userConverter.cargarDTO(user);
			usuarioSolrService.addToIndex(userSolr);
		}

		if (user != null) {
			user = mapearDatosLdap(user, userDetails);
			user.setAuthorities((Collection<GrantedAuthority>) userDetails.getAuthorities());
			if (!listUsuarios.containsKey(name) && user.getAceptacionTYC() != null && user.getAceptacionTYC()) {
				listUsuarios.put(name, user);
			}
		} else {
			throw new UsernameNotFoundException("No se ha podido cargar el Usuario " + name);
		}
		if(logger.isDebugEnabled()) {			
			logger.debug("Usuario " + name + " cargado Satisfactoriamente");
		}
		return user;
	}

  public Usuario obtenerUsuarioSinFiltro(String nombreUsuario) {
    return this.obtenerUsuario(nombreUsuario);
  }

  @Override
  public List<UsuarioReducido> obtenerUsuariosPorRol(String rol) {
    return ldapAccessor.buscarUsuariosPorRol(rol);
  }

  @Override
  public void indexarUsuario(String username) throws Exception {
    Usuario usuario = usuarioDAO.obtenerUsuario(username);
    UserDetails details = obtenerUsuarioLdap(username);
    if (details != null && usuario != null) {
      Usuario gcabaUser = mapearDatosLdap(usuario, details);
      UsuarioSolr solr = userConverter.cargarDTO(gcabaUser);
      usuarioSolrService.addToIndex(solr);
    }
  }

  @Override
  public void fullImportUsuarios() {
    try {
      List<Usuario> listaUsuariosTmp = usuarioDAO.obtenerUsuarios();
      listaUsuariosTmp = this.depurarListaUsuarios(listaUsuariosTmp);
      List<Usuario> listaUsuarios;
      synchronized (this.listUsuarios) {
        listaUsuarios = listaUsuariosTmp;
      }

      Collection<UsuarioSolr> usuarios = new ArrayList<UsuarioSolr>();
      for (Usuario usuario : listaUsuarios) {
        logger.info("Leyendo usuario " + usuario.getUsername());
        UserDetails userLdap = obtenerUsuarioLdap(usuario.getUsername());
        usuario = mapearDatosLdap(usuario, userLdap);
        UsuarioSolr userSolr = userConverter.cargarDTO(usuario);
        usuarios.add(userSolr);
      }
      usuarioSolrService.limpiarIndice();
      usuarioSolrService.addToIndex(usuarios);
      logger.info(usuarios.size() + " usuarios indexados satisfactoriamente");

    } catch (Exception e) {
      logger.error("Error al cargar usuarios: " + e.getMessage(), e);
    }
  }

  @Override
  public boolean usuarioTieneRol(String userName, String rol) {
    UserDetails user = obtenerUsuarioLdap(userName);

    if (user == null) {
      logger.error("El usuario no existe");
    }

    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
    boolean authorized = authorities.contains(new SimpleGrantedAuthority(rol.toUpperCase()));

    return authorized;
  }

  public void setUserSearch(LdapUserSearch userSearch) {
    this.userSearch = userSearch;
  }

  public void setAuthoritiesPopulator(LdapAuthoritiesPopulator authoritiesPopulator) {
    this.authoritiesPopulator = authoritiesPopulator;
  }

  @Override
  public List<Usuario> obtenerUsuarios(String criterio) {
    List<UsuarioSolr> list = new ArrayList<UsuarioSolr>();
    List<Usuario> rList = new ArrayList<Usuario>();
      list = usuarioSolrService.searchByQuery(criterio);

    for (UsuarioSolr usuarioSolr : list) {
      rList.add(userConverter.cargarUsuario(usuarioSolr));
    }
    return rList;
  }

  @Override
  public List<Usuario> obtenerUsuariosPorNombre(String nombre) {
    List<Usuario> list = new ArrayList<Usuario>();

    String criterio = armarFiltroSOLR(Constantes.SOLR_FIELD_USERNAME, nombre);
    criterio += " OR " + armarFiltroSOLR(Constantes.SOLR_FIELD_NOMBRE_APELLIDO, nombre);
    list = obtenerUsuarios(criterio);

    return list;
  }

  @Override
  public List<Usuario> obtenerUsuariosPorReparticion(String codigoReparticion) {
    List<Usuario> list = new ArrayList<Usuario>();

    String criterio = armarFiltroSOLR(Constantes.SOLR_FIELD_CODIGO_REPARTICION, codigoReparticion);
    list = obtenerUsuarios(criterio);

    return list;
  }

  @Override
  public List<Usuario> obtenerUsuariosPorGrupo(String codigoReparticion, String sectorInterno) {
    List<Usuario> list = new ArrayList<Usuario>();

    String criterio = armarFiltroSOLR(Constantes.SOLR_FIELD_CODIGO_REPARTICION, codigoReparticion);
    criterio += " AND "
        + armarFiltroSOLR(Constantes.SOLR_FIELD_CODIGO_SECTOR_INTERNO, sectorInterno);
    list = obtenerUsuarios(criterio);

    return list;
  }

  @Override
  public List<Usuario> obtenerUsuariosPorMesa(String codigoReparticion, String sectorMesa) {
    List<Usuario> list = new ArrayList<Usuario>();

    String criterio = armarFiltroSOLR(Constantes.SOLR_FIELD_CODIGO_REPARTICION, codigoReparticion);
    criterio += " AND " + armarFiltroSOLR(Constantes.SOLR_FIELD_CODIGO_SECTOR_MESA, sectorMesa);

    list = obtenerUsuarios(criterio);

    return list;
  }

  @Override
  public List<String> obtenerReparticionesHabilitadasPorUsuario(String nombreUsuario) {
      return usuarioDAO.obtenerReparticionesHabilitadasPorNombreUsuario(nombreUsuario);
  }

  @Override
  public Boolean licenciaActiva(String usuario, Date fecha) {
      return usuarioDAO.licenciaActiva(usuario, fecha);
  }

  private String armarFiltroSOLR(String field, String value) {
    StringBuilder criterio = new StringBuilder(field);
    criterio.append(":");
    criterio.append(value);

    return criterio.toString();
  }

  @SuppressWarnings("unchecked")
  private UserDetails obtenerUsuarioLdap(String userName) {
    DirContextOperations dirContextOperations = userSearch.searchForUser(userName);
    Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) authoritiesPopulator
        .getGrantedAuthorities(dirContextOperations, userName);
    UserDetails userDetails = userDetailsMapper.mapUserFromContext(dirContextOperations, userName,
        grantedAuthorities);
    return userDetails;
  }

  private Usuario mapearDatosLdap(Usuario gcabaUser, UserDetails userDetails) {
    gcabaUser.setIsAccountNonExpired(userDetails.isAccountNonExpired());
    gcabaUser.setIsAccountNonLocked(userDetails.isAccountNonLocked());
    gcabaUser.setIsCredentialsNonExpired(userDetails.isCredentialsNonExpired());
    gcabaUser.setIsEnabled(userDetails.isEnabled());
    List<UsuarioReducido> usuarios = ldapAccessor.buscarUsuarioPorCn(gcabaUser.getUsername());
    if (!usuarios.isEmpty()) {
      gcabaUser.setNombreApellido(usuarios.get(0).getNombreApellido());
    }

    return gcabaUser;
  }

  private List<Usuario> depurarListaUsuarios(List<Usuario> listaADepurar) {
    Vector<Usuario> toThrowAway = new Vector<Usuario>();
    if (listaADepurar != null) {

      List<UsuarioReducido> usuariosLdap = ldapAccessor.buscarTodosUsuariosLdap();
      ArrayList<UsuarioReducido> usuariosLdapSADE = new ArrayList<UsuarioReducido>();
      usuariosLdapSADE.addAll(usuariosLdap);

      Comparator<UsuarioReducido> comparator = new Comparator<UsuarioReducido>() {
        @Override
        public int compare(UsuarioReducido o1, UsuarioReducido o2) {
          return o1.compareTo(o2);
        }
      };
      Collections.sort(usuariosLdapSADE, comparator);
      for (Usuario usuario : listaADepurar) {
        int index = Collections.binarySearch(usuariosLdapSADE, usuario, comparator);

        if (index < 0) {
          toThrowAway.add(usuario);
        } else {
          if (StringUtils.isEmpty(usuario.getNombreApellido())) {
            List<UsuarioReducido> lista = ldapAccessor.buscarUsuarioPorCn(usuario.getUsername());
            if (!lista.isEmpty()) {
              usuario.setNombreApellido(lista.get(0).getNombreApellido());
            }
          }
        }
      }
      // Eliminamos los que no están en el LDAP y si en la BBDD
      listaADepurar.removeAll(toThrowAway);
    }
    return listaADepurar;
  }

  @Override
  public void cambiarPasswordUsuario(String username, String pwd) {
      ldapAccessor.cambiarPasswordUsuario(username, pwd);
  }

  @Override
  public Boolean validarPasswordUsuario(String username, String password) {
    AndFilter filter = new AndFilter();
    filter.and(new EqualsFilter("cn", username));
    return ldapAccessor.esPasswordUsuarioValido("", filter.encode(), password);
  }

  public void setTiempoRefresco(String tiempoRefresco) {
    this.tiempoRefresco = tiempoRefresco;
  }

  public String getTiempoRefresco() {
    return tiempoRefresco;
  }

  @Override
  public Usuario obtenerUsuarioMigracion(String nombreUsuario) {
      return usuarioDAO.obtenerUsuarioMigracion(nombreUsuario);
  }

  @Override
  public List<Usuario> obtenerUsuariosPorRevisor(String nombreUsuario) {
      return usuarioDAO.obtenerUsuariosPorRevisor(nombreUsuario);
  }

  @Override
  public List<Usuario> obtenerUsuariosPorApoderado(String nombreUsuario) {
      return usuarioDAO.obtenerUsuariosPorApoderado(nombreUsuario);
  }
  
  /**
   * Verifica si el usuario actual pertenece a la misma repartición del usuario
   * al que se envía la tarea (producción, revisión, firma).
   *
   * @param actualUser the actual user
   * @param candidate the candidate
   * @return true si el usuario al que se envía la tarea pertenece a la misma
   *         repartición, false en caso contrario.
   */
  @Override
	public boolean checkMismaReparticion(Usuario usuarioLogueado, Usuario candidate) {
  	 // Lo voy a buscar en lugar de guardarlo en la Session por las
    // actualizaciones
    String username = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute("userName");
		try {
			// cacheUser
			if (usuarioLogueado.getIsMultireparticion() != null && usuarioLogueado.getIsMultireparticion()) {
				List<String> reparticionesUsuario = this
						.obtenerReparticionesHabilitadasPorUsuario(username);
				for (String codReparticion : reparticionesUsuario) {
					// USO reparticion seleccionada
					if (candidate.getCodigoReparticion().equals(codReparticion)) {
						return true;
					}
				}
			}
			if (candidate.getCodigoReparticion().equals(usuarioLogueado.getCodigoReparticion())) {
				return true;
			}
			return false;
		} catch (SecurityNegocioException e) {
			logger.error("Mensaje de error", e);
		}
		return false;
	}
  
}
