package com.egoveris.sharedsecurity.base.service.ldap;

import java.util.List;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

public interface ILdapService {
	/**
	 * * Este metodo accede a los servicios del Ldap para dar de alta un
	 * usuario, * generandole la password aleatoria de 8 digitos. * * @param
	 * usuario * que se desea dar de alta * @param diasParaLogin * es la
	 * cantidad de dias que el usuario tiene para realizar su * primer login sin
	 * ser dado de baja * @throws NegocioException * en caso de ocurrir un error
	 * al mapear los atributos del usuario a * la estructura del Ldap o durante
	 * el envio del mail al usuario
	 */
	void darAltaUsuario(UsuarioBaseDTO usuario, Integer diasParaLogin)
			throws SecurityNegocioException, EmailNoEnviadoException;

	/**
	 * * Este metodo accede a los servicios del Ldap para modificar un usuario.
	 * * * @param usuario * que se desea modificar con los datos cambiados
	 * * @throws NegocioException * en caso de ocurrir un error al mapear los
	 * atributos del usuario a * la estructura del Ldap
	 */
	void modificarUsuario(UsuarioBaseDTO usuario) throws SecurityNegocioException;

	/**
	 * * Este metodo accede a los servicios del Ldap para buscar todos los
	 * usuarios. * * @return Una <b>List de UsuarioSadeDTO</b> que contiene los
	 * datos de todos * los usuarios encontrados * @throws NegocioException *
	 * the negocio exception
	 */
	List<UsuarioBaseDTO> obtenerTodosLosUsuarios();

	/**
	 * * Este metodo accede a los servicios del Ldap para buscar el usuario que
	 * * concuerde con el criterio de busqueda. * * @param uId * del usuario que
	 * se esta buscando * @return Un <b>UsuarioSadeDTO</b> que contiene los
	 * datos del usuario * encontrado ó <b>null</b> en caso de no encontrarlo
	 * * @throws NegocioException * the negocio exception
	 */
	UsuarioBaseDTO obtenerUsuarioPorUid(String uId);

	/**
	 * * Este metodo accede a los servicios del Ldap para buscar el/los usuarios
	 * que * concuerden con el criterio de busqueda. * * @param nombre * del
	 * usuario que se esta buscando * @return Una <b>List de UsuarioSadeDTO</b>
	 * que contiene los datos de los * usuarios encontrados * @throws
	 * NegocioException * the negocio exception
	 */
	List<UsuarioBaseDTO> obtenerUsuarioPorNombre(String nombre);

	/**
	 * * Metodo que devuelve una lista de usuarios que concuerden en nombre y/o
	 * uid * con el criterio de busqueda. * * @param busqueda * the busqueda
	 * * @return Una <b>List de UsuarioSadeDTO</b> que contiene los datos de los
	 * * usuarios encontrados * @throws NegocioException * the negocio exception
	 */
	List<UsuarioBaseDTO> obtenerUsuarioPorNombreYUid(String busqueda);

	/**
	 * * Verifica que la password pasada por parametro sea la correspondiente al
	 * * usuario pasado por parametro. * * @param usuario * the usuario * @param
	 * password * the password * @return true si la password enviada corresponde
	 * al usuario enviado. * @throws NegocioException * the negocio exception
	 */
	boolean verificarPassword(String usuario, String password);
}
