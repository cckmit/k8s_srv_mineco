package com.egoveris.te.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import org.jbpm.pvm.internal.identity.impl.GroupImpl;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasoluna.plus.core.util.ApplicationContextUtil;
import org.zkoss.spring.SpringUtil;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.service.UsuariosSADEService;

/**
 * En esta clase se utiliza unicamente el metodo findGroupsByUser()
 * Es utilizado para el Buzon Grupal
 * 
 * @author everis
 */
public class IdentitySessionImpl implements IdentitySession {
	private static final Logger logger = LoggerFactory.getLogger(IdentitySessionImpl.class);
	
	private static final List<String> LISTA_PERMISOS_EE = Arrays
			.asList(new String[] { "SADE.EXTERNOS", "SADE.INTERNOS" });
	
	public IdentitySessionImpl() {
		// Constructor
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#createGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createGroup(String s, String s1, String s2) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#createMembership(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void createMembership(String s, String s1, String s2) {
		//
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#createUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createUser(String s, String s1, String s2, String s3) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#deleteGroup(java.lang.String)
	 */
	public void deleteGroup(String s) {
		//
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#deleteMembership(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteMembership(String s, String s1, String s2) {
		//
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#deleteUser(java.lang.String)
	 */
	public void deleteUser(String s) {
		//
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findGroupById(java.lang.String)
	 */
	public Group findGroupById(String s) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findGroupsByUser(java.lang.String)
	 */
	public List<Group> findGroupsByUser(String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("findGroupsByUser(username={}) - start", username);
		}
		
		String userNameValidacion = username;
		String sufijo = "";
		
		if (username.endsWith(ConstantesWeb.SUFIJO_BLOQUEADO)) {
			userNameValidacion = username.split(ConstantesWeb.SUFIJO_BLOQUEADO)[0];
			sufijo = ConstantesWeb.SUFIJO_BLOQUEADO;
		}
		
		List<Group> listaGrupos = new ArrayList<>();
		
		UsuariosSADEService usuariosSADEService = (UsuariosSADEService) ApplicationContextUtil.getBean(ConstantesServicios.USUARIOS_SADE_SERVICE);
		
		Usuario usuario = usuariosSADEService.getDatosUsuario(userNameValidacion);
		
		String codigoReparticion = usuario.getCodigoReparticion();
		String codSectorInterno = usuario.getCodigoSectorInterno();
		
		listaGrupos.add(this.crearGrupo(codigoReparticion + sufijo));
		listaGrupos.add(this.crearGrupo(codigoReparticion + "-" + codSectorInterno + sufijo));

		for (String permisoEE : LISTA_PERMISOS_EE) {
			if (usuariosSADEService.usuarioTieneRol(usuario.getUsername(), permisoEE)) {
				String reparticionPermiso = codigoReparticion + "-" + permisoEE;
				String reparticionSectorPermiso = codigoReparticion + "-" + codSectorInterno + "-" + permisoEE;
				listaGrupos.add(this.crearGrupo(reparticionPermiso + sufijo));
				listaGrupos.add(this.crearGrupo(reparticionSectorPermiso + sufijo));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("findGroupsByUser(String) - end - return value={}", listaGrupos);
		}
		
		return listaGrupos;
	}
	
	private GroupImpl crearGrupo(String nombreGrupo) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearGrupo(nombreGrupo={}) - start", nombreGrupo);
		}

		GroupImpl grupo = new GroupImpl();
		grupo.setId(nombreGrupo);
		grupo.setName(nombreGrupo);

		if (logger.isDebugEnabled()) {
			logger.debug("crearGrupo(String) - end - return value={}", grupo);
		}
		
		return grupo;
	}
	
	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findGroupsByUserAndGroupType(java.lang.String, java.lang.String)
	 */
	public List<Group> findGroupsByUserAndGroupType(String s, String s1) {
		if (logger.isDebugEnabled()) {
			logger.debug("findGroupsByUserAndGroupType(s={}, s1={}) - start", s, s1);
		}

		List<Group> returnList = findGroupsByUser(s);

		if (logger.isDebugEnabled()) {
			logger.debug("findGroupsByUserAndGroupType(String, String) - end - return value={}", returnList);
		}

		return returnList;
	}
	
	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findUserById(java.lang.String)
	 */
	public User findUserById(String s) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findUsers()
	 */
	public List<User> findUsers() {
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findUsersByGroup(java.lang.String)
	 */
	public List<User> findUsersByGroup(String s) {
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see org.jbpm.pvm.internal.identity.spi.IdentitySession#findUsersById(java.lang.String[])
	 */
	public List<User> findUsersById(String... iUserIds) {
		return new ArrayList<>();
	}

}
