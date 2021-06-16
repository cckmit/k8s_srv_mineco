package com.egoveris.commons.databaseconfiguration.encription.impl;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.commons.databaseconfiguration.encription.IPasswordBasedEncription;
import com.egoveris.commons.databaseconfiguration.exceptions.EncripcionException;

public class PasswordBasedEncriptionImpl implements IPasswordBasedEncription {

	private Logger logger = LoggerFactory.getLogger(PasswordBasedEncriptionImpl.class);
	
	private String masterKey = null;
	
	private SecretKey secret = null;
	
	private final static byte[] salt = {
        (byte)0x23, (byte)0x7C, (byte)0x04, (byte)0xF9,
        (byte)0x4F, (byte)0x8D, (byte)0x54, (byte)0x51
    };
	
	private final static Integer CONTADOR = Integer.valueOf(100);
	
	private final static Integer LONGITUD_CLAVE = Integer.valueOf(128);
	
	private final static Integer TAMANIO_BLOQUE = Integer.valueOf(16);
	
	private Cipher encryptCipher;
	
	private Cipher decryptCipher;
	
	public void initCiphers() throws EncripcionException
	{
		KeySpec keySpec = null;
		SecretKeyFactory keyFactory = null;
		try {
			keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			keySpec = new PBEKeySpec(this.masterKey.toCharArray(),salt, CONTADOR, LONGITUD_CLAVE);
			SecretKey tmp = keyFactory.generateSecret(keySpec);
			secret = new SecretKeySpec(tmp.getEncoded(), "AES");
			encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (Exception e) {
			logger.error("Error inicializando ciphers", e);
			throw new EncripcionException("Error inicializando cifradores", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see ar.gob.gcaba.encription.IPasswordBasedEncription#encriptarPassword(java.lang.String)
	 */
	@Override
	public String encriptarPassword(String password) throws EncripcionException
	{
		byte [] textoEncriptado = null;
		byte [] vectorInit = null;
		String passwordEncriptado = null;
		SecureRandom secureRandom = new SecureRandom();
		IvParameterSpec iv = new IvParameterSpec(secureRandom.generateSeed(TAMANIO_BLOQUE));
		vectorInit = iv.getIV();
		try {
			encryptCipher.init(Cipher.ENCRYPT_MODE, secret,iv);
			textoEncriptado = encryptCipher.doFinal(password.getBytes("UTF-8"));
			byte [] textoAlmacenar = new byte[vectorInit.length + textoEncriptado.length];
			System.arraycopy(vectorInit, 0, textoAlmacenar, 0,TAMANIO_BLOQUE);
			System.arraycopy(textoEncriptado, 0, textoAlmacenar, TAMANIO_BLOQUE,textoEncriptado.length);
//			System.out.println("Size textoEncriptado " + textoEncriptado.length);
			passwordEncriptado = Base64.encodeBase64String(textoAlmacenar);
//			System.out.println("Size textoEncriptadoCod64 " + passwordEncriptado.length());
		} catch (Exception e) {
			logger.error("Error al encriptar el password",e);
			throw new EncripcionException("Error al encriptar el password", e);
		}
		return passwordEncriptado;
	}
		
	
	/* (non-Javadoc)
	 * @see ar.gob.gcaba.encription.IPasswordBasedEncription#desencriptarPassword(byte[])
	 */
	@Override
	public String desencriptarPassword(String passwordEncriptado) throws EncripcionException
	{
		byte[] textoPlano = null;
		byte[] textoAlmacenado = null;
		byte[] vectorInit = new byte[TAMANIO_BLOQUE];
		
		String password = null;
		try {
			textoAlmacenado = Base64.decodeBase64(passwordEncriptado);
			System.arraycopy(textoAlmacenado, 0, vectorInit, 0,TAMANIO_BLOQUE);
			byte[] textoSinIv = new byte[textoAlmacenado.length-TAMANIO_BLOQUE];
			System.arraycopy(textoAlmacenado,vectorInit.length,textoSinIv,0,textoSinIv.length);
			IvParameterSpec iv = new IvParameterSpec(vectorInit);
			decryptCipher.init(Cipher.DECRYPT_MODE, secret,iv);
			textoPlano = decryptCipher.doFinal(textoSinIv);
			password = new String(textoPlano, "UTF-8");
		} catch (Exception e) {
			logger.error("Error al desencriptar el password",e);
			throw new EncripcionException("Error al desencriptar el password", e);
		}
		return password;
	}	
	
	/* (non-Javadoc)
	 * @see ar.gob.gcaba.encription.IPasswordBasedEncription#generarSALT()
	 */
	@Override
	public byte[] generarSALT()
	{
		Random r = new SecureRandom();
		byte[] salt = new byte[8];
		r.nextBytes(salt);
		return salt;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
}
