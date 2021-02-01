package com.egoveris.sharedsecurity.base.service.impl.ldap;

import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.exception.EmailNoEnviadoException;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.exception.UsernameRepetidoException;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;
import com.egoveris.sharedsecurity.base.service.IPasswordService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapGenericService;
import com.egoveris.sharedsecurity.base.service.ldap.ILdapService;
import com.egoveris.sharedsecurity.base.util.ConstanstesAdminSade;
import com.egoveris.sharedsecurity.base.util.UsuarioBaseDTOMapper;

@Service
public class LdapServiceImpl implements ILdapService {

	private static Logger logger = LoggerFactory.getLogger(LdapServiceImpl.class);
	private DozerBeanMapper mapper = new DozerBeanMapper();
	private final String sPerson = "person";

	@Autowired
	private IPasswordService iPasswordService;
	@Autowired
	private ILdapGenericService iLdapGenericService;

	@Override
	public void darAltaUsuario(UsuarioBaseDTO usuario, Integer diasParaLogin)
			throws SecurityNegocioException, EmailNoEnviadoException {
		final String sErrorUserExist = "Ya existe un usuario con este nombre de usuario.";

		try {
			DistinguishedName newContactDN = new DistinguishedName("ou=funcionarios");
			newContactDN.add("cn", usuario.getUid());
			if (!iLdapGenericService.existeUsuario(newContactDN)) {
				if (StringUtils.isEmpty(usuario.getPassword())) {
					usuario.setPassword(this.iPasswordService
							.generarPasswordAleatoria(ConstanstesAdminSade.CANTIDAD_CARACTERES_PASSWORD_ALEATORIA));
				}
				this.iLdapGenericService.guardarUsuario(newContactDN, null,
						this.mapearUserAttributes(mapper.map(usuario, UsuarioBaseDTO.class)));
			} else {
				throw new UsernameRepetidoException(sErrorUserExist);
			}
		} catch (UsernameRepetidoException ex) {
			logger.error(sErrorUserExist, ex);
			throw new SecurityNegocioException(sErrorUserExist);
		}
	}

	@Override
	public void modificarUsuario(UsuarioBaseDTO usuario) throws SecurityNegocioException {
		DistinguishedName newContactDN = new DistinguishedName("ou=funcionarios");
		newContactDN.add("cn", usuario.getUid());

		ModificationItem[] modificaciones = null;
		try {
			Attributes atributos = this.mapearUserAttributes(mapper.map(usuario, UsuarioBaseDTO.class));
			NamingEnumeration<String> atributosEnum = atributos.getIDs();

			modificaciones = new ModificationItem[atributos.size()];
			int cont = 0;
			while (atributosEnum.hasMore()) {
				modificaciones[cont] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						atributos.get(atributosEnum.next()));
				cont++;
			}
			this.iLdapGenericService.modificarUsuario(newContactDN, modificaciones);
		} catch (NamingException ex) {
			logger.error("Error al recorrer la lista de atributos del usuario: " + usuario.getUid(), ex);
			throw new SecurityNegocioException(
					"Error al recorrer la lista de atributos del usuario: " + usuario.getUid(), ex);
		} catch (Exception ex) {
			logger.error("Error al guardar en LDAP: " + ex.getMessage(), ex);
			throw new SecurityNegocioException("Error al guardar en LDAP", ex);
		}
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	@SuppressWarnings("unchecked")
	public List<UsuarioBaseDTO> obtenerTodosLosUsuarios() throws SecurityNegocioException {
		return ListMapper.mapList((List<UsuarioBaseDTO>) this.iLdapGenericService.obtenerUsuarios("",
				"(objectclass=person)", new UsuarioBaseDTOMapper()), mapper, UsuarioBaseDTO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public UsuarioBaseDTO obtenerUsuarioPorUid(String uId) throws SecurityNegocioException {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass", sPerson));
		andFilter.and(new EqualsFilter("cn", uId));
		List<UsuarioBaseDTO> listaUsuarios = (List<UsuarioBaseDTO>) this.iLdapGenericService.obtenerUsuarios("",
				andFilter.encode(), new UsuarioBaseDTOMapper());
		if (listaUsuarios.isEmpty()) {
			return null;
		} else {
			return mapper.map(listaUsuarios.get(0), UsuarioBaseDTO.class);
		}
	}

	/**
	 * @deprecated
	 */
	@Override
	@Deprecated
	@SuppressWarnings("unchecked")
	public List<UsuarioBaseDTO> obtenerUsuarioPorNombre(String nombre) {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("objectClass", sPerson));
		andFilter.and(new WhitespaceWildcardsFilter("uid", nombre));
		return ListMapper.mapList((List<UsuarioBaseDTO>) this.iLdapGenericService.obtenerUsuarios("",
				andFilter.encode(), new UsuarioBaseDTOMapper()), mapper, UsuarioBaseDTO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioBaseDTO> obtenerUsuarioPorNombreYUid(String busqueda) {

		OrFilter orFilter = new OrFilter();
		orFilter.or(new WhitespaceWildcardsFilter("cn", busqueda));
		orFilter.or(new WhitespaceWildcardsFilter("uid", busqueda));

		return ListMapper.mapList((List<UsuarioBaseDTO>) this.iLdapGenericService.obtenerUsuarios("", orFilter.encode(),
				new UsuarioBaseDTOMapper()), mapper, UsuarioBaseDTO.class);
	}

	@Override
	public boolean verificarPassword(String usuario, String password) throws SecurityNegocioException {
		AndFilter andFilter = new AndFilter();
		andFilter.and(new EqualsFilter("cn", usuario));
		return this.iLdapGenericService.verificarPassword("", andFilter.encode(), password);
	}

	/**
	 * Metodo que mapea los atributos del objeto UsuarioBaseDTO al objeto
	 * Attributes
	 * 
	 * @param user
	 *            con los que se desean mapear
	 * @return un objecto <b>Attributes</b> con los datos mapeados a la
	 *         estructura del Ldap
	 * @throws NegocioException
	 *             en caso de ocurrir un error durante la codificacion del
	 *             password, si tuviera.
	 */
	private Attributes mapearUserAttributes(UsuarioBaseDTO user) {
		Attributes personAttributes = new BasicAttributes();
		BasicAttribute personBasicAttribute = new BasicAttribute("objectclass");
		personBasicAttribute.add(sPerson);
		personBasicAttribute.add("inetOrgPerson");
		personBasicAttribute.add("organizationalPerson");
		personAttributes.put(personBasicAttribute);
		personAttributes.put("cn", user.getUid());
		personAttributes.put("sn", user.getUid());
		personAttributes.put("uid", user.getNombre());
		personAttributes.put("mail", user.getMail());

		if (user.getLegajo() != null && !user.getLegajo().isEmpty()) {
			personAttributes.put("employeeNumber", user.getLegajo());
		}
		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			personAttributes.put("userPassword", this.iPasswordService.codificarSHA(user.getPassword()));
		}

		BasicAttribute employeeType = new BasicAttribute("employeeType");
		employeeType.add("public");
		if (user.getPermisos() != null && !user.getPermisos().isEmpty()) {
			for (String per : user.getPermisos()) {
				employeeType.add(per);
			}
		}
		personAttributes.put(employeeType);

		return personAttributes;
	}
}