package com.egoveris.sharedsecurity.base.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;

/**
 * Provee informacion basica del usuario
 * 
 * @author javsolis
 */
public interface IUsuarioService {

	/**
	 * Obtiene los datos del usuario por userName.
	 * 
	 * @param nombreUsuario
	 * @return
	 * @throws SecurityNegocioException
	 */
	public Usuario obtenerUsuario(String nombreUsuario) throws SecurityNegocioException;

	public Usuario obtenerUsuarioSinFiltro(String nombreUsuario) throws SecurityNegocioException;
	
	/**
	 * Obtiene los datos del usuario por userName tipo like.
	 * 
	 * @param nombreUsuario
	 * @return lista de usuarios por encontrados
	 * @throws SecurityNegocioException
	 */
	public List<Usuario> obtenerUsuarios(String criterio) throws SecurityNegocioException;

	/**
	 * Devuelve una lista de usuarios cuyo supervisor sea el indicado por
	 * parametro.
	 * 
	 * @param nombreUsuario
	 * @return lista de usuarios supervisados
	 * @throws SecurityNegocioException
	 */
	public List<Usuario> obtenerUsuariosPorSupervisor(String nombreUsuario) throws SecurityNegocioException;

	/**
	 * Devuelve una lista con los codigos de reparticion habilitadas para operar
	 * por el usuario indicado por parametro
	 * 
	 * @param nombreUsuario
	 * @return codigos de reparticion
	 * @throws SecurityNegocioException
	 */
	public List<String> obtenerReparticionesHabilitadasPorUsuario(String nombreUsuario) throws SecurityNegocioException;


	/**
	 * Devuelve una lista de todos los usuarios
	 * @return
	 * @throws SecurityNegocioException 
	 */
	public Collection<Usuario> obtenerUsuarios() throws SecurityNegocioException;

	/**
	 * Realiza un full-import de los usuarios a SOLR
	 */
	void fullImportUsuarios();

	/**
	 * Determina si un usuario dado se encuentra de licencia en la fecha
	 * indicada
	 * 
	 * @param usuario
	 *            Usuario a validar
	 * @param fecha
	 *            Fecha de licencia
	 * @return true si esta de licencia, false si no
	 * @throws SecurityNegocioException
	 */
	public Boolean licenciaActiva(String usuario, Date fecha) throws SecurityNegocioException;

	/**
	 * Determina si un usuario tiene el rol indicado
	 * 
	 * @param userName
	 * @param rol
	 * @return
	 * @throws SecurityNegocioException
	 */
	boolean usuarioTieneRol(String userName, String rol) throws SecurityNegocioException;

	/**
	 * Devuelve los username que tienen determinado rol
	 * 
	 * @param rol
	 *            por el cual se buscan los usuarios
	 * @return
	 */
	List<UsuarioReducido> obtenerUsuariosPorRol(String rol);

	/**
	 * Busca los usuarios por los campos username y nombre_apellido
	 * 
	 * @param nombre
	 *            username o nombre y apellido por el que se desea buscar
	 * @return
	 * @throws SecurityNegocioException
	 */
	List<Usuario> obtenerUsuariosPorNombre(String nombre) throws SecurityNegocioException;

	/**
	 * Busca los usuarios dado un codigo de reparticion
	 * 
	 * @param codigoReparticion
	 * @return
	 * @throws SecurityNegocioException
	 */
	List<Usuario> obtenerUsuariosPorReparticion(String codigoReparticion) throws SecurityNegocioException;

	/**
	 * Busca los usuarios dado un codigo de reparticion y un sector
	 * 
	 * @param codigoReparticion
	 * @return
	 * @throws SecurityNegocioException
	 */
	List<Usuario> obtenerUsuariosPorGrupo(String codigoReparticion, String sectorInterno)
			throws SecurityNegocioException;

	/**
	 * Busca los usuarios dado un codigo de reparticion y un sector mesa
	 * 
	 * @param codigoReparticion
	 * @return
	 * @throws SecurityNegocioException
	 */
	List<Usuario> obtenerUsuariosPorMesa(String codigoReparticion, String sectorMesa) throws SecurityNegocioException;

	Map<String, Usuario> obtenerMapaUsuarios() throws SecurityNegocioException;
	/**
	 * Devuelve una lista de todos los usuarios que estan en Solr
	 * @return Lista de usuariosReducidos
	 * @throws SecurityNegocioException 
	 */
	public List<UsuarioReducido> obtenerUsuariosDeSolr() throws SecurityNegocioException;
	
	/**
	 * Devuelve una lista de todos los usuarios que estan en Solr Supervisados por el usuario del parametro
	 * @param userNaem
	 * @return  Lista de usuariosReducidos
	 * @throws SecurityNegocioException 
	 */
	public List<UsuarioReducido> obtenerUsuariosDeSolrSupervisados(String userName) throws SecurityNegocioException;
	
	/**
	 *  Permite cambiar el password del {@link Usuario} 
	 * @param username El username del {@link Usuario}
	 * @param pwd el password nuevo a modificar
	 * @throws SecurityNegocioException
	 */
	void cambiarPasswordUsuario(String username, String pwd) throws SecurityNegocioException;
	
	/**
	 * Indica true si el password corresponde al {@link Usuario} identificado por username
	 * 
	 * @param username el nombre del {@link Usuario}
	 * @param password el password a verificar
	 * @return true si hay correspondecia, de lo contrario false
	 */
	
	Boolean validarPasswordUsuario (String username, String password) throws SecurityNegocioException;
	
	/**
	 * Obtiene los datos del usuario por userName dado de baja.
	 * 
	 * @param nombreUsuario
	 * @return
	 * @throws SecurityNegocioException
	 */
	public Usuario obtenerUsuarioMigracion(String nombreUsuario) throws SecurityNegocioException;
	
	/**
	 * Obtiene los datos del usuario por userName por revisor.
	 * 
	 * @param nombreUsuario
	 * @return
	 * @throws SecurityNegocioException
	 */
	public List<Usuario> obtenerUsuariosPorRevisor(String nombreUsuario) throws SecurityNegocioException;
	
	/**
	 * Metodo de indexacion de un usuario partilar en Solr.
	 * SOLO debe usarse por EU en el contexto de active directory!!!!!!
	 * @param username
	 * @throws Exception
	 */
	public void indexarUsuario(String username) throws Exception;
	
	/**
	 * Obtiene los datos del usuario por userName por apoderado.
	 * 
	 * @param nombreUsuario
	 * @return
	 * @throws SecurityNegocioException
	 */
	public List<Usuario> obtenerUsuariosPorApoderado(String nombreUsuario) throws SecurityNegocioException;
	
	/**
	 * Obtiene los datos del usuario por sector.
	 * 
	 * @param sector
	 * @return
	 * @throws SecurityNegocioException
	 */
	public List<Usuario> obtenerUsuariosPorSector(String sector) throws SecurityNegocioException;
	
	/**
	 * Check misma reparticion.
	 *
	 * @param usuarioLogueado the usuario logueado
	 * @param candidate the candidate
	 * @return true, if successful
	 */
	public boolean checkMismaReparticion(Usuario usuarioLogueado, Usuario candidate);
	
}