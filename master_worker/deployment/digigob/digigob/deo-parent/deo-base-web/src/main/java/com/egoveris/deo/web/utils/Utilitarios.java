package com.egoveris.deo.web.utils;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;

public class Utilitarios {

  private static final Logger logger = LoggerFactory.getLogger(Utilitarios.class);

  /**
   * Convertir fecha a String con el formato dado.
   * 
   * @param Fecha
   * @return
   */
  public static String fechaToString(Date fecha) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(fecha);
  }

  /**
   * Convertir fecha a String con el formato dado.
   * 
   * @param Fecha
   * @param formato
   *          : Correspondiente al formato en el que se quiere mostrar la fecha.
   * @return
   */
  public static String fechaToString(Date Fecha, String formato) {
    SimpleDateFormat sdf = new SimpleDateFormat(formato);
    return sdf.format(Fecha);
  }

  /**
   * Configura el formato de una fecha dada.
   * 
   * @param fecha
   * @return
   */
  public static Date formatFecha(Date fecha) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date fechaFormat = null;
    try {
      fechaFormat = sdf.parse(sdf.format(fecha));
    } catch (ParseException e) {
      logger.error("Error al pasear", e);
    }
    return fechaFormat;

  }

  /**
   * Valida el tamaño del string, para visualización, cortandolo en el caso de
   * que sobrepase un limite de tamaño.
   * 
   * @param motivo
   * @param cantidadCaracteres
   *          : Límite de tamaño
   * @return
   */
  public static String motivoParseado(String motivo, int cantidadCaracteres) {
    String substringMotivo;
    if (motivo != null) {
      if (motivo.length() > cantidadCaracteres) {
        substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
      } else {
        substringMotivo = motivo;
      }
    } else {
      substringMotivo = "sin motivo";
    }
    return substringMotivo;
  }

  public static void doLogout(String urlLogOut) {
    Executions.getCurrent().getSession().invalidate();
    Executions.sendRedirect(urlLogOut);
  }

  public static void doLogout() {
    // SPRING SECURITY:
    String urlLogOutCAS = "/logout";
    Executions.getCurrent().getSession().invalidate();
    Executions.sendRedirect(urlLogOutCAS);
  }

  /**
   * Reemplaza los retornos de linea por espacios en blanco.
   * 
   * @param cadena
   *          String con retornos de linea.
   * @return cadena sin retornos de linea.
   */
  public static String reemplazarSaltosLinea(String cadena) {
    return cadena.replace('\n', ' ');
  }

  public static Usuario obtenerUsuarioActual() {
    return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
	
    private static SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes(StandardCharsets.UTF_8);
         
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
         
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
         
        return new SecretKeySpec(claveEncriptacion, "AES");
    }
 
	public static String encriptar(String datos) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = crearClave("egov");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] datosEncriptar = datos.getBytes(StandardCharsets.UTF_8);
		byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
		return Base64.getEncoder().encodeToString(bytesEncriptados);
	}
    
	public static String desencriptar(String datosEncriptados) throws UnsupportedEncodingException, NoSuchAlgorithmException,
			InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = crearClave("egov");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
		byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
		return new String(datosDesencriptados);
	}
    
}
