package com.egoveris.commons.databaseconfiguration.propiedades;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Properties;

import com.egoveris.commons.databaseconfiguration.exceptions.DatabaseConfigurationException;
import com.egoveris.commons.databaseconfiguration.exceptions.ParametroInvalidoException;

public interface AppProperty {

	public BigDecimal getBigDecimal(String key);

	public BigInteger getBigInteger(String key);

	public boolean getBoolean(String key);

	public double getDouble(String key);

	public float getFloat(String key);

	public int getInt(String key);

	public long getLong(String key);

	public String getString(String key);

	public Date getDate(String key);

	public void setBigDecimal(String key, BigDecimal value, String nombreConfiguracion) 
		throws ParametroInvalidoException;

	public void setBigInteger(String key, BigInteger value, String nombreConfiguracion)
		throws ParametroInvalidoException;

	public void setBoolean(String key, Boolean value, String nombreConfiguracion)
		throws ParametroInvalidoException;

	public void setDouble(String key, Double value, String nombreConfiguracion)
		throws ParametroInvalidoException;

	public void setFloat(String key, Float value, String nombreConfiguracion)
		throws ParametroInvalidoException;
	
	public void setInt(String key, int value, String nombreConfiguracion)
		throws ParametroInvalidoException;
		
	public void setLong(String key, Long value, String nombreConfiguracion)
		throws ParametroInvalidoException;

	public void setString(String key, String value, String nombreConfiguracion)
		throws ParametroInvalidoException;

	public void setDate(String key, Date date, String nombreConfiguracion)
		throws ParametroInvalidoException;

	/**
	 * Almacena las claves encriptadas.
	 * @param key: Nombre de propiedad.
	 * @param password: Clave
	 * @param nombreConfiguracion: tipo de configuración.
	 * @throws ParametroInvalidoException
	 * @throws DatabaseConfigurationException 
	 */
	public void setPassword(String key, String password, String nombreConfiguracion)
		throws ParametroInvalidoException, DatabaseConfigurationException;
	
	/**
	 * Obtiene las propiedades cargadas desde la base de datos, en un objeto de tipo java.util.Properties
	 * @return
	 */
	public Properties getProperties();

	/**
	 * Registra en el log las propiedades cargadas desde la base de datos.
	 */
	public void showAllProperties();

	/**
	 * Almacena un grupo de propiedades en la base de datos.
	 * @param propiedades
	 * @param nombreConfiguracion
	 * @throws ParametroInvalidoException
	 * @throws DatabaseConfigurationException 
	 */
	public void guardarPropiedades(Properties propiedades, String nombreConfiguracion)
			throws DatabaseConfigurationException, ParametroInvalidoException;

	

	
}