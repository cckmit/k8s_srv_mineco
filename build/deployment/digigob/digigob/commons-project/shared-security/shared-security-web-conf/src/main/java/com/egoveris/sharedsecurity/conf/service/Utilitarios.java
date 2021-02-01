package com.egoveris.sharedsecurity.conf.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.TipoUsuarioEnum;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioBaseDTO;

/**
 * The Class Utilitarios.
 */
public class Utilitarios {

	/** The Constant EMAIL_PATTERN. */
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Do logout.
	 */
	public static void doLogout() {
		String urlLogOutCas = "/logout";
		Executions.getCurrent().getSession().invalidate();
		Executions.sendRedirect(urlLogOutCas);
	}

	/**
	 * Obtener usuario actual.
	 *
	 * @return the usuario
	 */
	public static Usuario obtenerUsuarioActual() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public static boolean isAdministradorCentral() {
		return SecurityUtil.isAllGranted(ConstantesSesion.ROL_ADMIN_CENTRAL);
	}

	/**
	 * Checks if is administrador central.
	 *
	 * @param user the user
	 * @return true, if is administrador central
	 */
	public static boolean isAdministradorCentral(UsuarioBaseDTO user) {

		if (CollectionUtils.exists(user.getPermisos(), new RoleMatchPredicate(ConstantesSesion.ROL_ADMIN_CENTRAL))) {
			return true;
		}
		return false;
	}

	public static boolean isSindicatura() {
		return SecurityUtil.isAllGranted(ConstantesSesion.ROL_SINDICATURA);

	}

	public static boolean isAdministradorLocalReparticion() {
		return (SecurityUtil.isAllGranted(ConstantesSesion.getRolAdminLocal()[0]))
				&& (SecurityUtil.isAllGranted(ConstantesSesion.getRolAdminLocal()[1]));
	}

	/**
	 * Checks if is administrador local reparticion.
	 *
	 * @param usuarioSeleccionado the usuario seleccionado
	 * @return true, if is administrador local reparticion
	 */
	public static boolean isAdministradorLocalReparticion(UsuarioBaseDTO usuarioSeleccionado) {
		return CollectionUtils.exists(usuarioSeleccionado.getPermisos(),
				new RoleMatchPredicate(ConstantesSesion.getRolAdminLocal()[0]))
				&& CollectionUtils.exists(usuarioSeleccionado.getPermisos(),
						new RoleMatchPredicate(ConstantesSesion.getRolAdminLocal()[1]));
	}

	public static boolean isProjectLeader() {
		return SecurityUtil.isAllGranted(ConstantesSesion.ROL_PROYECT_LIDER);
	}

	/**
	 * Obtener tipo usuario.
	 *
	 * @return the tipo usuario enum
	 */
	public static TipoUsuarioEnum obtenerTipoUsuario() {
		if (isAdministradorCentral()) {
			return TipoUsuarioEnum.AC;
		} else if (isAdministradorLocalReparticion()) {
			return TipoUsuarioEnum.AL;
		} else if (isProjectLeader()) {
			return TipoUsuarioEnum.PL;
		}
		return null;
	}

	/**
	 * Obtener tipo usuario.
	 *
	 * @param usuario the usuario
	 * @return the tipo usuario enum
	 */
	public static TipoUsuarioEnum obtenerTipoUsuario(UsuarioBaseDTO usuario) {

		if (CollectionUtils.exists(usuario.getPermisos(), new RoleMatchPredicate(ConstantesSesion.ROL_ADMIN_CENTRAL))) {
			return TipoUsuarioEnum.AC;
		} else if (CollectionUtils.exists(usuario.getPermisos(),
				new RoleMatchPredicate(ConstantesSesion.getRolAdminLocal()[0]))
				&& CollectionUtils.exists(usuario.getPermisos(),
						new RoleMatchPredicate(ConstantesSesion.getRolAdminLocal()[1]))) {
			return TipoUsuarioEnum.AL;
		}
		return null;
	}

	/**
	 * Valida el digito verificador de un CUIL o CUIT determinado.
	 *
	 * @param numeroCuit the numero cuit
	 * @return true si el CUIL o CUIT es valido
	 */

	public static boolean validarDigitoVerificadorCorrecto(String numeroCuit) {

		// la secuencia de valores de factor es 5, 4, 3, 2, 7, 6, 5, 4, 3, 2
		int factor = 5;

		int[] intArr = new int[11];
		int resultado = 0;

		for (int i = 0; i < 10; i++) {
			// se toma el valor de cada cifra
			intArr[i] = Integer.parseInt(Character.toString(numeroCuit.charAt(i)));

			// se suma al resultado el producto de la cifra por el factor que
			// corresponde
			resultado = resultado + intArr[i] * factor;
			// se actualiza el valor del factor
			factor = (factor == 2) ? 7 : factor - 1;
		}

		intArr[10] = Integer.parseInt(Character.toString(numeroCuit.charAt(10)));

		// se obtiene el valor calculado a comparar
		int control = (11 - (resultado % 11)) % 11;

		// Si la cifra de control es distinta del valor calculado
		if (control != intArr[10]) {
			return false;
		}

		return true;
	}

	/**
	 * Validacion de Enteros.
	 *
	 * @param numero the numero
	 * @return boolean si esta OK.
	 */
	public static boolean esNumero(String numero) {
		Pattern pat;
		Matcher mat;
		pat = Pattern.compile("^[0-9]+$");
		mat = pat.matcher(numero);
		return mat.find();
	}

	/**
	 * Validar numeros.
	 *
	 * @param txt the txt
	 * @throws WrongValueException the wrong value exception
	 */
	public static void validarNumeros(Textbox txt) throws WrongValueException {

		final String enteredValue = ((String) txt.getValue()).trim();
		// check if not allowed signs
		if (!enteredValue.isEmpty() && !enteredValue.matches("(([0-9]+)?)+")) {
			throw new WrongValueException(txt, Labels.getLabel("eu.adminSade.validacion.reparticion.numInvalido"));
		}
	}

	/**
	 * Validar numeros telefono.
	 *
	 * @param txt the txt
	 * @throws WrongValueException the wrong value exception
	 */
	public static void validarNumerosTelefono(Textbox txt) throws WrongValueException {

		final String enteredValue = ((String) txt.getValue()).trim();
		if (!enteredValue.isEmpty()) {
			Pattern pat = Pattern.compile("[0-9]*");
			Matcher mat = pat.matcher(enteredValue);
			if (!mat.matches()) {
				throw new WrongValueException(txt, Labels.getLabel("eu.adminSade.validacion.reparticion.numTelefonoInvalido"));
			}
		}
	}

	/**
	 * Validar mail.
	 *
	 * @param txt the txt
	 * @throws WrongValueException the wrong value exception
	 */
	public static void validarMail(Textbox txt) throws WrongValueException {
		final String enteredValue = ((String) txt.getValue()).trim();
		if (!enteredValue.isEmpty() && !validarMail(enteredValue)) {
			throw new WrongValueException(txt, Labels.getLabel("eu.validacion.correo.error"));
		}
	}

	/**
	 * Validar mail.
	 *
	 * @param hex the hex
	 * @return true, if successful
	 */
	public static boolean validarMail(final String hex) {
		EmailValidator emailValidator = new EmailValidator();
		return emailValidator.isValid(hex, null);
	}

	/**
	 * Close pop ups.
	 *
	 * @param popStr the pop str
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void closePopUps(String popStr) {
		List<?> comps = new CopyOnWriteArrayList(Executions.getCurrent().getDesktop().getComponents());
		for (Object obj : comps) {
			if (obj instanceof Window) {
				String str = ((Window) obj).getId();

				if (popStr.equals(str)) {
					((Window) obj).detach();
				}
			} else if (obj instanceof Popup) {
				String strPop = ((Popup) obj).getId();

				if (popStr.equals(strPop)) {
					((Popup) obj).detach();
				}
			}
		}
	}
	
}

/**
 * The Class RoleMatchPredicate.
 */
class RoleMatchPredicate implements Predicate {

	String role;

	public RoleMatchPredicate(String role) {
		this.role = role;
	}

	@Override
	public boolean evaluate(Object object) {
		return ((String) object).toUpperCase().contains(role);
	}
}