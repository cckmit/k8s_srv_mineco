package com.egoveris.sharedsecurity.base.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.service.IPasswordService;

@Service("iPasswordService")
public class PasswordServiceImpl implements IPasswordService {

  private static Logger logger = LoggerFactory.getLogger(PasswordServiceImpl.class);

  private static final String NUMEROS = "0123456789";
  private static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

  @Override
  public String generarPasswordAleatoria(int cantidadDigitos) {
    String caracteres = NUMEROS + MAYUSCULAS + MINUSCULAS;
    String pswd = "";
    for (int i = 0; i < cantidadDigitos; i++) {
      pswd += (caracteres.charAt((int) (Math.random() * caracteres.length())));
    }
    return pswd;
  }

  @Override
  public String codificarSHA(String password){
    String passwordCodificada = null;
    byte[] by = null;
    try {
      by = MessageDigest.getInstance("SHA").digest(password.getBytes());
      passwordCodificada = "{SHA}" + new String(Base64.encodeBase64(by));
    } catch (NoSuchAlgorithmException ex) {
      logger.error("Error al codificar la password en SHA1", ex);
      throw new SecurityNegocioException("Error al codificar la password en SHA1", ex);
    }
    return passwordCodificada;
  }
}