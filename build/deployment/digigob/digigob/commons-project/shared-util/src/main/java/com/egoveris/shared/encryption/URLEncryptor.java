package com.egoveris.shared.encryption;

import com.egoveris.shared.constants.Constants;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An easy to use class to encrypt and decrypt a string. Just call the simplest
 * constructor and the needed methods.
 * 
 */
public class URLEncryptor {
  private Cipher encryptCipher;
  private Cipher decryptCipher;
  private static URLEncryptor instance = null;
  private final SessionIdentifierGenerator paddingGenerator = new SessionIdentifierGenerator();
  private final String charset = "UTF-8";
  private final String defaultEncryptionPassword = Constants.DEFAULT_ECRYPTION_PASS;
  private static final Logger LOGGER = LoggerFactory.getLogger(URLEncryptor.class);

  private final byte[] defaultSalt = {

      (byte) 0xa3, (byte) 0x21, (byte) 0x24, (byte) 0x2c,

      (byte) 0xf2, (byte) 0xd2, (byte) 0x3e, (byte) 0x19 };

  private final char[] hexas = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
      'D', 'E', 'F' };

  /**
   * Gets the single instance of URLEncryptor.
   *
   * @return single instance of URLEncryptor
   */
  public static final URLEncryptor getInstance() {
    if (URLEncryptor.instance == null) {
      URLEncryptor.instance = new URLEncryptor();
    }
    return URLEncryptor.instance;
  }

  /**
   * The simplest constructor which will use a default password and salt to
   * encode the string.
   *
   * @throws SecurityException
   *           the security exception
   */
  private URLEncryptor() throws SecurityException {
    setupEncryptor(defaultEncryptionPassword, defaultSalt);
  }

  /**
   * Dynamic constructor to give own key and salt to it which going to be used
   * to encrypt and then decrypt the given string.
   *
   * @param encryptionPassword
   *          the encryption password
   * @param salt
   *          the salt
   */
  public URLEncryptor(String encryptionPassword, byte[] salt) {
    setupEncryptor(encryptionPassword, salt);
  }

  /**
   * Inits the.
   *
   * @param pass
   *          the pass
   * @param salt
   *          the salt
   * @param iterations
   *          the iterations
   * @throws SecurityException
   *           the security exception
   */
  public void init(char[] pass, byte[] salt) throws SecurityException {
    try {
      PBEParameterSpec ps = new javax.crypto.spec.PBEParameterSpec(salt, 20);

      SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

      SecretKey key = kf.generateSecret(new javax.crypto.spec.PBEKeySpec(pass));

      encryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");

      encryptCipher.init(Cipher.ENCRYPT_MODE, key, ps);

      decryptCipher = Cipher.getInstance("PBEWithMD5AndDES/CBC/PKCS5Padding");

      decryptCipher.init(Cipher.DECRYPT_MODE, key, ps);
    } catch (Exception ex) {
      throw new SecurityException("Could not initialize CryptoLibrary: " + ex);
    }
  }

  /**
   * method to encrypt a string.
   * 
   * @param str
   *          Description of the Parameter
   * 
   * @return String the encrypted string.
   * 
   * @exception SecurityException
   *              Description of the Exception
   */

  public synchronized String encrypt(final String str) throws SecurityException {
    if (str.contains("|")) {
      throw new IllegalArgumentException("To be encrypted string cannot contain '|' character");
    }
    try {
      String tobeEncoded = str + "|" + this.paddingGenerator.nextSessionId();
      byte[] utf8 = tobeEncoded.getBytes(this.charset);

      byte[] enc = this.encryptCipher.doFinal(utf8);
      return this.encodeAsHex(enc);
    } catch (Exception ex) {
      throw new SecurityException(ex);
    }
  }

  private final String encodeAsHex(byte[] enc) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < enc.length; i++) {
      builder.append(this.toHexString(enc[i]));
    }
    return builder.toString().toUpperCase();
  }

  private char[] toHexString(byte byt) {
    char[] number = new char[2];
    byte high = (byte) ((byt >> 4) & 0x0F);
    byte low = (byte) (byt & 0XF);
    number[1] = this.hexas[low];
    number[0] = this.hexas[high];
    return number;
  }

  private final byte[] decodeAsHex(String dec) {
    char[] hex = dec.toCharArray();
    int length = hex.length / 2;
    byte[] raw = new byte[length];
    for (int i = 0; i < length; i++) {
      int high = Character.digit(hex[i * 2], 16);
      int low = Character.digit(hex[i * 2 + 1], 16);
      int value = (high << 4) | low;
      if (value > 127) {
        value -= 256;
      }
      raw[i] = (byte) value;
    }
    return raw;
  }

  /**
   * method to encrypting a string.
   * 
   * @param str
   *          Description of the Parameter
   * 
   * @return String the encrypted string.
   * 
   * @exception SecurityException
   *              Description of the Exception
   */

  public synchronized String decrypt(final String str) throws SecurityException {
    try {
      byte[] dec = this.decodeAsHex(str);
      byte[] utf8 = decryptCipher.doFinal(dec);
      String decryptedAndPadded = new String(utf8, charset);
      return decryptedAndPadded.split("\\|")[0];
    } catch (Exception ex) {
      throw new SecurityException(ex);
    }
  }

  private void setupEncryptor(String defaultEncryptionPassword, byte[] salt) {
    Cipher cipher = null;
    try {

      cipher = Cipher.getInstance(Constants.S_DES, "SunJCE");
    } catch (Exception ex) {
      LOGGER.error("Error en setupEncryptor", ex);
      try {
        cipher = Cipher.getInstance(Constants.S_DES, "IBMJCE");
      } catch (NoSuchAlgorithmException e1) {
        LOGGER.error(ex.getMessage(), e1);
      } catch (NoSuchProviderException e1) {
        LOGGER.error(ex.getMessage(), e1);
      } catch (NoSuchPaddingException e1) {
        LOGGER.error(ex.getMessage(), e1);
      }

    }
    if (null != cipher) {
      java.security.Security.addProvider(cipher.getProvider());
    }

    char[] pass = defaultEncryptionPassword.toCharArray();

    init(pass, salt);
  }

}

final class SessionIdentifierGenerator {

  private SecureRandom random = new SecureRandom();

  public String nextSessionId() {
    return new BigInteger(130, random).toString(64);
  }
}
