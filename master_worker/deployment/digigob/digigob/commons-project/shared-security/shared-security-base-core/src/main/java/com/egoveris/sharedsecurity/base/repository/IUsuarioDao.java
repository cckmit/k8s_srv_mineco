package com.egoveris.sharedsecurity.base.repository;

import java.util.Date;
import java.util.List;

import com.egoveris.sharedsecurity.base.model.Usuario;

/**
 * Interfaz para obtener usuarios de CCOO
 * 
 * @author psettino
 */
public interface IUsuarioDao {

  /**
   * Devuelve un usuario de CCOO
   * 
   * @param userName
   * @exception AccesoDatosException
   */
  public Usuario obtenerUsuario(String username);

  public List<Usuario> obtenerUsuariosPorSupervisor(String username);

  public List<Usuario> obtenerUsuarios();

  public int obtenerCantidadDeReparticionHabilitadaPorNombre(String username);

  public List<String> obtenerReparticionesHabilitadasPorNombreUsuario(String username);

  public boolean licenciaActiva(String username, Date fecha);

  public Usuario obtenerUsuarioMigracion(String username);

  public List<Usuario> obtenerUsuariosPorRevisor(String username);

  public List<Usuario> obtenerUsuariosPorApoderado(String nombreUsuario);
  
  public List<Usuario> obtenerUsuariosPorSector(String sector);
}
