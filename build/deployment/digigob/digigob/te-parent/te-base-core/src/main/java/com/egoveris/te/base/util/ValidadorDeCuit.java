/**
 * 
 */
package com.egoveris.te.base.util;

import com.egoveris.te.base.exception.NegocioException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Clase encargada de validar si un n�mero de Cuit es v�lido.
 * Realiza validaciones de formato y de negocio.

 * @author caieta 
 */
public class ValidadorDeCuit {
	private static final Logger logger = LoggerFactory.getLogger(ValidadorDeCuit.class);


	/**************************************************************************************************
	 * M�todo que se encarga de llamar a todas las validaciones que se deben aplicar a un n�mero de Cuit
	 * 
	 * @param numeroCuit
	 * @throws NegocioException 
	 * 
	 */
	public void validarNumeroDeCuit(String numeroCuit) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarNumeroDeCuit(numeroCuit={}) - start", numeroCuit);
		}

		this.validarContenidoVacio(numeroCuit);
		this.validarFormatoCorrectoDeCaracteres(numeroCuit);
		this.validarCantidadCorrectaDeCaracteres(numeroCuit);
		this.validarDigitoVerificadorCorrecto(numeroCuit);

		if (logger.isDebugEnabled()) {
			logger.debug("validarNumeroDeCuit(String) - end");
		}
	}

	/**************************************************************************************************
	 * @param numeroCuit: El n�mero de CUIT que se quiere validar 
	 * Verifica que el n�mero de CUIT ingresado no est� vac�o
	 * @throws NegocioException 
	 */
	private void validarContenidoVacio(String numeroCuit) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarContenidoVacio(numeroCuit={}) - start", numeroCuit);
		}

		if (numeroCuit==null || StringUtils.isEmpty(numeroCuit))
			throw new NegocioException("Su número de CUIT es requerido, por favor ingrese los datos en dicho campo.", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarContenidoVacio(String) - end");
		}
	}
	
	/**************************************************************************************************
	 * 
	 * @param numeroCuit: El n�mero de CUIT que se quiere validar 
	 * Verifica que el n�mero de CUIT ingresado contenga s�lo n�meros
	 * @throws NegocioException 
	 */
	private void validarFormatoCorrectoDeCaracteres(String numeroCuit) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarFormatoCorrectoDeCaracteres(numeroCuit={}) - start", numeroCuit);
		}

		// Utilizo una expresion regular para validar que el n�mero de CUIT
		// contenga s�lo n�meros
		if (!numeroCuit.matches("[0-9]*"))
			throw new NegocioException("Su número de CUIT sólo debe contener números, sin guiones ni caracteres especiales", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarFormatoCorrectoDeCaracteres(String) - end");
		}
	}
	
	/**************************************************************************************************
	 * @param numeroCuit: El n�mero de CUIT que se quiere validar 
	 * Verifica que el n�mero de CUIT ingresado contenga once n�meros
	 * @throws NegocioException 
	 */
	private void validarCantidadCorrectaDeCaracteres(String numeroCuit) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarCantidadCorrectaDeCaracteres(numeroCuit={}) - start", numeroCuit);
		}

		// Utilizo una expresion regular para validar que el n�mero de CUIT
		// contenga la cantidad correcta de caracteres
		String patternNumeroDeCaracteresCuit = "[0-9]{" + this.getCantidadDeCaracteresCuit() + "}";
		if (!numeroCuit.trim().matches(patternNumeroDeCaracteresCuit))
			throw new NegocioException(	"Su número de CUIT debe contener 11 (once) números", null);

		if (logger.isDebugEnabled()) {
			logger.debug("validarCantidadCorrectaDeCaracteres(String) - end");
		}
	}
	
	/**************************************************************************************************
	 * @param numeroCuit: El n�mero de CUIT que se quiere validar 
	 * @throws NegocioException 
	 */
	public void validarDigitoVerificadorCorrecto(String numeroCuit) throws NegocioException {
		if (logger.isDebugEnabled()) {
			logger.debug("validarDigitoVerificadorCorrecto(numeroCuit={}) - start", numeroCuit);
		}
		
		// la secuencia de valores de factor es 5, 4, 3, 2, 7, 6, 5, 4, 3, 2
		int factor = 5;
		
		int[] c = new int[11];
		int resultado = 0;
		
		for (int i = 0; i < 10; i++) {
			// se toma el valor de cada cifra
			c[i] = Integer.valueOf(Character.toString(numeroCuit.charAt(i))).intValue();
			
			// se suma al resultado el producto de la cifra por el factor que
			// corresponde
			resultado = resultado + c[i] * factor;
			// se actualiza el valor del factor
			factor = (factor == 2) ? 7 : factor - 1;
		}
		
		c[10] = Integer.valueOf(Character.toString(numeroCuit.charAt(10))).intValue();
		
		// se obtiene el valor calculado a comparar
		int control = (11 - (resultado % 11)) % 11;
		
		// Si la cifra de control es distinta del valor calculado
		if (control != c[10]) {
			throw new NegocioException("Su número de CUIT/CUIL posee un dígito verificador incorrecto.", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarDigitoVerificadorCorrecto(String) - end");
		}
	}
	
	/**************************************************************************************************
	 * VARIABLES DE CLASE
	 **************************************************************************************************/
	private static int cantidadDeCaracteresCuit = 11;
	
	/**************************************************************************************************
	 * GETTERS y SETTERS
	 **************************************************************************************************/

	private int getCantidadDeCaracteresCuit() {
		return cantidadDeCaracteresCuit;
	}

}
