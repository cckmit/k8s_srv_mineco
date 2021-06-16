package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.DatosUsuarioBean;

/**
 * The Interface UsuariosSADEService.
 */
public interface UsuariosSADEService {
	/**
	 * Obtiene todos los datos de los usuarios que pertenecen al sistema SADE.
	 * 
	 * @return Una lista con los usuarios que cumplen los criterios listados
	 *         arriba o una lista vacía si ninguno cumple con dicho criterio.
	 */
	public List<Usuario> getTodosLosUsuarios() throws SecurityNegocioException;
	
	/**
	 * Licencia activa.
	 *
	 * @param usuario the usuario
	 * @return true, if successful
	 */
	public boolean licenciaActiva(String usuario);
	
	/**
	 * Obtiene todos los datos de los usuarios que pertenecen a la repartición
	 * pasada como argumento.
	 *
	 * @param reparticion            Código de la Repartición a la cual deben pertenecer los
	 *            usuarios a devolver.
	 * @return Una lista con los usuarios que cumplen los criterios listados
	 *         arriba o una lista vacía si ninguno cumple con dicho criterio.
	 * @throws SecurityNegocioException the security negocio exception
	 */
	public List<Usuario> getUsuarios(String reparticion) throws SecurityNegocioException;

	/**
	 * Obtener usuario por criterio.
	 *
	 * @param criterio the criterio
	 * @return the list
	 */
	public List<Usuario>obtenerUsuarioPorCriterio(String criterio);
	
	/**
	 * Obtener usuarios supervisados.
	 *
	 * @param username the username
	 * @return the list
	 */
	public List<Usuario>obtenerUsuariosSupervisados(String username);
	
	/**
	 * Obtener usuarios por sector.
	 *
	 * @param sector the sector
	 * @return the list
	 */
	public List<Usuario>obtenerUsuariosPorSector(String sector);
	
	/**
	 * Obtiene todos los datos de los usuarios que pertenecen a la repartición
	 * pasada como argumento y que tienen permisos en el módulo de referencia.
	 * 
	 * @param reparticion
	 *            Código de la Repartición a la cual deben pertenecer los
	 *            usuarios a devolver.
	 * @param modulo
	 *            Módulo sobre el cual deben tener permisos los usuarios a
	 *            devolver
	 * @return Una lista con los usuarios que cumplen los criterios listados
	 *         arriba o una lista vacía si ninguno cumple con dicho criterio.
	 */
	public List<Usuario> getUsuarios(String reparticion,
			String modulo);
	
	/**
	 * Trae el bean DatosUsuarioBean que representa el perfil del usuario cuyo username se pasa como parámetro.
	 * @param username Nombre de usuario del perfil a buscar
	 * @return El perfil del usuario o <b>null</b> si no se ha encontrado el username.
	 */
	public Usuario getDatosUsuario(String username);
	
	/**
	 * METODO DEPRECADO SOLO DEBE USARSE EN EL CONTEXTO DE NOMBRAMIENTOS.
	 *
	 * @param username the username
	 * @return the datos
	 */
	@Deprecated
	public DatosUsuarioBean getDatos(String username);
	
	/**
	 * Trae la lista de todos los usuarios (DatosUsuarioBean)con permiso de caratulador según que corresponda.
	 * @return lista de todos los usuarios caratuladores internos/externos.
	 */
	@Deprecated
	public List<DatosUsuarioBean> getTodosLosUsuariosCaratuladoresInternos();
	
	@Deprecated
	public List<DatosUsuarioBean> getTodosLosUsuariosCaratuladoresExternos();
	
	/**
	 * Checks for usuarios caratuladores internos X reparticion Y sector.
	 *
	 * @param reparticion the reparticion
	 * @param sectorInterno the sector interno
	 * @return true, if successful
	 */
	public boolean hasUsuariosCaratuladoresInternosXReparticionYSector(String reparticion, String sectorInterno);
	
	/**
	 * Checks for usuarios caratuladores externos X reparticion Y sector.
	 *
	 * @param reparticion the reparticion
	 * @param sectorInterno the sector interno
	 * @return true, if successful
	 */
	public boolean hasUsuariosCaratuladoresExternosXReparticionYSector(String reparticion, String sectorInterno);
	
	/**
	 * Retorna si el usuario dado tiene el rol.
	 *
	 * @param username the username
	 * @param rol the rol
	 * @return true, if successful
	 */
	public boolean usuarioTieneRol(String username,String rol);
	
	/**
	 * METODO DEPRECADO SOLO DEBE USARSE EN EL CONTEXTO DE PLUGINES.
	 *
	 * @param reparticion the reparticion
	 * @param sectorInterno the sector interno
	 * @return the todos los usuarios X reparticion Y sector
	 */
	@Deprecated
	public List<DatosUsuarioBean> getTodosLosUsuariosXReparticionYSector(String reparticion, String sectorInterno);

	/**
	 * To datos usuario bean.
	 *
	 * @param u the u
	 * @return the datos usuario bean
	 */
	public DatosUsuarioBean toDatosUsuarioBean(Usuario u);
	
	/**
	 * Pasando la repartición y sector busco todos los usuarios que petenezcan a ellas.
	 *
	 * @param rol the rol
	 * @return Lista de DatosUsuarioBean con todos los usuarios pertenecientes a la reparticion y sector
	 */
	public List<Usuario> getUsuariosSegunRol(String rol);
	
	/**
	 * Gets the todos los usuarios X reparticion Y sector EE.
	 *
	 * @param reparticion the reparticion
	 * @param sectorInterno the sector interno
	 * @return the todos los usuarios X reparticion Y sector EE
	 */
	public List<Usuario> getTodosLosUsuariosXReparticionYSectorEE(String reparticion, String sectorInterno);
	
	/**
	 * Retorna el usuario logeado.
	 *
	 * @return the usuario
	 */
	public Usuario obtenerUsuarioActual();

	/**
	 * Obtener usuario migracion.
	 *
	 * @param username the username
	 * @return the usuario
	 */
	public Usuario obtenerUsuarioMigracion(String username);
	
	/**
	 * Busca asignadores del sector dado, ademas que sea distinto  los usuarios que se les pasa por parametro
	 * (la lista pasada por parametro, es una lista de posibles asignadores dentro de la reparticion-sector).
	 *
	 * @param codigoReparticionOriginal the codigo reparticion original
	 * @param codigoSectorInterno the codigo sector interno
	 * @param usuariosQueNoQuiero the usuarios que no quiero
	 * @return the string
	 */
	public String buscarAsignadorDeSectorDesestimandoUsuarios(String codigoReparticionOriginal,
			String codigoSectorInterno,List<String>usuariosQueNoQuiero);
	
}