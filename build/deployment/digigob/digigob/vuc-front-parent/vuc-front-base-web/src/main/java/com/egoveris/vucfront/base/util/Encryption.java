package com.egoveris.vucfront.base.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encryption {
  private static final Logger logger = LoggerFactory.getLogger(Encryption.class);

  private String key = "vwa01112RGL93723";
  private static Encryption instance;

  private Encryption() {

  }

  /**
   * Gets called every time an instance of the class is constructed. The static
   * block only gets called once, when the class itself is initialized, no
   * matter how many objects of that type you create.
   */
  static {
    instance = new Encryption();
  }

  public static Encryption getInstance() {
    return instance;
  }

  public String encryptLong(Long param) {
    return encryptParameter(param.toString());
  }

  public String encryptString(String param) {
    return encryptParameter(param);
  }

  private String encryptParameter(String param) {
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, aesKey);
      byte[] encrypted = cipher.doFinal(param.getBytes());
      return new String(Base64.encodeBase64URLSafeString(encrypted));
    } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException
        | NoSuchPaddingException | NoSuchAlgorithmException e) {
      logger.error("ERROR encrypting paramater.", e);
    }
    return param;
  }

  public String decryptParameter(String param) {
    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, aesKey);
      return new String(cipher.doFinal(Base64.decodeBase64(param)));
    } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException
        | NoSuchPaddingException | NoSuchAlgorithmException e) {
      logger.error("ERROR unencrypting paramater.", e);
    }
    return param;
  }
}