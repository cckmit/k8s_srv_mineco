package com.egoveris.commons.databaseconfiguration.encription;

import com.egoveris.commons.databaseconfiguration.exceptions.EncripcionException;

public interface IPasswordBasedEncription {

	public abstract String encriptarPassword(String password) throws EncripcionException;

	public abstract String desencriptarPassword(String passwordEncriptado) throws EncripcionException;

	public abstract byte[] generarSALT();

}