package com.egoveris.commons.databaseconfiguration.propiedades.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.configuration.ConfigurationRuntimeException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.apache.commons.configuration.PropertyConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.commons.databaseconfiguration.encription.IPasswordBasedEncription;
import com.egoveris.commons.databaseconfiguration.exceptions.DatabaseConfigurationException;
import com.egoveris.commons.databaseconfiguration.exceptions.EncripcionException;
import com.egoveris.commons.databaseconfiguration.exceptions.ParametroInvalidoException;
import com.egoveris.commons.databaseconfiguration.exceptions.ParseDateException;
import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class DBAppPropertyImpl implements AppProperty{

	private static final String KEY_COLUMN = "clave";
	private static final String VALUE_COLUMN = "valor";
	private static final String NAME_CONFIGURACION = "configuracion";
	private static final String JNDI_NOMBRE_NODO = "java:comp/env/nombreNodo";
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	private static final String PREFIJO_PASSWORD = "<<passwd>>";
	
	private Properties properties;
	private DataSource dataSource;
	private String nombreTabla;
	private String[] configuracionesArray;
		
	private Logger logger = LoggerFactory.getLogger(DBAppPropertyImpl.class);
	
	private IPasswordBasedEncription  passwordBasedEncriptionService;
	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.commons.databaseconfiguration.propiedades.AppProperty#getProperties()
	 */
	@Override
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * Constructor, carga las propiedades que se encuentran configuradas en la base de datos.
	 * @param dataSource: Base de datos desde donde se obtienen las propiedades.
	 * @param nombreTabla: Nombre de la tabla de propiedades.
	 * @param configuraciones: Los nombres de las distinas configuraciones a cargar.
	 * @param passwordBasedEncriptionService: Instancia del servicio para encripción de propiedades.
	 * @throws DatabaseConfigurationException 
	 */
	public DBAppPropertyImpl(DataSource dataSource, String nombreTabla, 
			String configuraciones, IPasswordBasedEncription  passwordBasedEncriptionService) throws DatabaseConfigurationException
	{
		this.dataSource = dataSource;
		this.nombreTabla = nombreTabla;
		/**
		 * Configuraciones definidas en el contexto spring desde la aplicación cliente.
		 */
		String [] configuracionesContexto = configuraciones.split(",");
		this.properties = new Properties();
		this.passwordBasedEncriptionService = passwordBasedEncriptionService;
		
		/**
		 * Si se ha definido un nombre del nodo para la aplicación, se crean los nombres de las configuraciones
		 * acorde al nombre del nodo, teniendo en cuenta las configuraciones inyectadas de manera declarativa.
		 */
		String nombreNodo = cargarNombreNodo();
		if(!StringUtils.isEmpty(nombreNodo)){
			this.configuracionesArray = new String[(configuracionesContexto.length)*2];
			System.arraycopy(configuracionesContexto, 0, configuracionesArray, 0, configuracionesContexto.length);
			for (int i = 0; i<configuracionesContexto.length; i++) {
				configuracionesArray[i+configuracionesContexto.length] = configuracionesContexto[i] + "." + nombreNodo;
			}
		}
		else{
			this.configuracionesArray = configuracionesContexto;
		}
		/**
		 * Es necesario crear un objeto de tipo DatabaseConfiguration, para cada tipo de configuración que se 
		 * requiera cargar, ya que la librería utilizada (Apache), en sus consultas y actualizaciones solo contempla
		 * un tipo de configuración 
		 */
		for (int i = 0; i < configuracionesArray.length; i++) {
			DatabaseConfiguration dataBaseConfiguration = new DatabaseConfiguration(dataSource, nombreTabla, 
					NAME_CONFIGURACION, KEY_COLUMN, VALUE_COLUMN, configuracionesArray[i]);
			dataBaseConfiguration.setDelimiterParsingDisabled(true);
			ConfigurationUtils.enableRuntimeExceptions(dataBaseConfiguration);
			this.properties.putAll(obtenerProperties(dataBaseConfiguration));
		}
	}
	
	/**
	 * Constructor, carga las propiedades que se encuentran configuradas en la base de datos.
	 * @param dataSource: Base de datos desde donde se obtienen las propiedades.
	 * @param nombreTabla: Nombre de la tabla de propiedades.
	 * @param passwordBasedEncriptionService: Instancia del servicio para encripción de propiedades.
	 */
	public DBAppPropertyImpl(DataSource dataSource, String nombreTabla, IPasswordBasedEncription passwordBasedEncriptionService)
	{
		this.dataSource = dataSource;
		this.nombreTabla = nombreTabla;
		this.passwordBasedEncriptionService = passwordBasedEncriptionService;
		this.properties = new Properties();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.egoveris.commons.databaseconfiguration.propiedades.AppProperty#showAllProperties()
	 */
	@Override
	public void showAllProperties()
	{
		logger.info("Cantidad de propiedades cargadas desde Base de Datos: " , properties.size());
	}
	
	/**
	 * Instancia objeto de tipo DatabaseConfiguration
	 * @param nombreConfiguracion
	 * @return
	 * @throws ParametroInvalidoException
	 */
	private DatabaseConfiguration getDatabaseConfiguration(String nombreConfiguracion) throws ParametroInvalidoException
	{
		DatabaseConfiguration configuration = null;
		if(StringUtils.isEmpty(nombreConfiguracion))
			throw new ParametroInvalidoException
			("El parámetro nombreConfiguración es requerido, para la actualización de propiedades");
		else
			configuration = new DatabaseConfiguration(dataSource, nombreTabla, NAME_CONFIGURACION,
					KEY_COLUMN, VALUE_COLUMN, nombreConfiguracion);
			configuration.setDelimiterParsingDisabled(false);
			ConfigurationUtils.enableRuntimeExceptions(configuration);
		return configuration;
	}
	
	@Override
	public BigDecimal getBigDecimal(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toBigDecimal(value);
	}
	
	@Override
	public BigInteger getBigInteger(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toBigInteger(value);
	}

	@Override
	public boolean getBoolean(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toBoolean(value);
	}

	@Override
	public double getDouble(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toDouble(value);
	}

	@Override
	public float getFloat(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toFloat(value);
	}

	@Override
	public int getInt(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toInteger(value);
	}

	@Override
	public long getLong(String key) {
		String value = this.properties.getProperty(key);
		return PropertyConverter.toLong(value);
	}

	@Override
	public String getString(String key) {
		return this.properties.getProperty(key);
	}
	
	@Override
	public Date getDate(String key){
		String value = this.properties.getProperty(key);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			logger.error("Error al dar formato a fecha ", e);
			throw new ParseDateException();
		}
	}
	
	private String desencriptarPassword(String valorCampo) throws DatabaseConfigurationException
	{
		String passwordEncriptado = StringUtils.removeStart(valorCampo,PREFIJO_PASSWORD);
		String textoClaro;
		try {
			textoClaro = passwordBasedEncriptionService.desencriptarPassword(passwordEncriptado);
		} catch (EncripcionException e) {
			throw new DatabaseConfigurationException(e);
		}
		return textoClaro;
	}
	
	/**
	 * Guarda las propiedades en la base de datos, permite hacer manejo de excepciones.
	 * @param configuracion
	 * @param key
	 * @param value
	 * @throws ParametroInvalidoException
	 */
	private void guardarPropiedad(DatabaseConfiguration configuracion, String key, Object value) throws ParametroInvalidoException
	{
		try{
			configuracion.setProperty(key, value);
		}
		catch(ConfigurationRuntimeException me)
		{
			logger.error("Error almacenando propiedades",me);
			if(me.getCause() instanceof MySQLIntegrityConstraintViolationException){
				throw new ParametroInvalidoException("Error actualizando propiedades, el par propiedad-configuración deber ser único. " +
					"para configuraciones diferentes");
			}
			else if(me.getCause() instanceof MysqlDataTruncation){
				throw new ParametroInvalidoException("Error actualizando propiedades, el valor de la propiedad excede el máximo tamaño permitido");
			}
			else{
				throw new ParametroInvalidoException("Error insertando o actualizando propiedades: " + me.getMessage());
			}
		}
		catch(Exception e)
		{
			throw new ParametroInvalidoException("Error insertando o actualizando propiedades: " + e.getMessage());
		}
	}
	
	@Override
	public void setBigDecimal(String key,BigDecimal value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}

	@Override
	public void setBigInteger(String key,BigInteger value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}

	@Override
	public void setBoolean(String key,Boolean value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}
	
	@Override
	public void setDouble(String key,Double value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}
	
	@Override
	public void setFloat(String key,Float value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}

	@Override
	public void setInt(String key,int value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}

	@Override
	public void setLong(String key,Long value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, String.valueOf(value));
	}

	@Override
	public void setString(String key,String value, String nombreConfiguracion) 
		throws ParametroInvalidoException{
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,value);
		this.properties.put(key, value);
	}
	
	@Override
	public void setDate(String key,Date date, String nombreConfiguracion)
		throws ParametroInvalidoException{
		String dateStr = sdf.format(date);
		guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,dateStr);
		this.properties.put(key, dateStr);
	}
	
	@Override
	public void setPassword(String key, String password, String nombreConfiguracion)
		throws ParametroInvalidoException, DatabaseConfigurationException
	{
		String passwordEncriptado=null;
		try {
			passwordEncriptado = passwordBasedEncriptionService.encriptarPassword(password);
			guardarPropiedad(getDatabaseConfiguration(nombreConfiguracion),key,new String(PREFIJO_PASSWORD + passwordEncriptado)); 
			this.properties.put(key, password);
		} catch (Exception e) {
			throw new DatabaseConfigurationException(e);
		}
	}
	
	/**
	 * Almacena claves encriptados.
	 * @param key
	 * @param password
	 * @param configuration
	 * @throws DatabaseConfigurationException 
	 */
	private String encriptarPassword(String password)
		throws DatabaseConfigurationException
	{
		String passwordEncriptado;
		try {
			passwordEncriptado = passwordBasedEncriptionService.encriptarPassword(StringUtils.removeStart(password,PREFIJO_PASSWORD));
		} catch (EncripcionException e) {
			throw new DatabaseConfigurationException(e);
		}
		return new String(PREFIJO_PASSWORD + passwordEncriptado);
	}
	
	/*
	 * Crea un objeto de tipo java.util.Properties, con las propiedades almacenadas en la base de datos.
	 */
	private Properties obtenerProperties(DatabaseConfiguration configuration) throws DatabaseConfigurationException {
		Properties properties = null;
		if(configuration.getKeys()== null){
			logger.info("no hay propiedades cargadas - ");
		}
		else{
			properties = new Properties();
			Iterator<String> it = (Iterator<String>)configuration.getKeys();
			while(it.hasNext())
			{
				String key =it.next();
				String valorPropiedad = (String)configuration.getProperty(key);
				properties.put(key , valorPropiedad);
			}
		}
		return properties;
	}
	
	@Override
	public void guardarPropiedades(Properties propiedades, String nombreConfiguracion) 
		throws DatabaseConfigurationException, ParametroInvalidoException
	{
		Set<Entry<Object, Object>> listaPropiedades = propiedades.entrySet();
		DatabaseConfiguration configuration = getDatabaseConfiguration(nombreConfiguracion);
		try{
			for (Entry<Object, Object> entry : listaPropiedades) {
				Object value = entry.getValue().toString();
				if(StringUtils.startsWith((String)value, PREFIJO_PASSWORD)){
					value = encriptarPassword((String)value);
				}
				configuration.setProperty(entry.getKey().toString(), value.toString());
			}
		}
		catch(ConfigurationRuntimeException me)
		{
			logger.error("Error almacenando propiedades",me);
			if(me.getCause() instanceof MySQLIntegrityConstraintViolationException){
				throw new DatabaseConfigurationException("Error actualizando propiedades, el par propiedad-configuración deber ser único. " +
					"para configuraciones diferentes");
			}
			else if(me.getCause() instanceof MysqlDataTruncation){
				throw new ParametroInvalidoException("Error actualizando propiedades, el valor de la propiedad excede el máximo tamaño permitido");
			}
			else{
				throw new ParametroInvalidoException("Error insertando o actualizando propiedades: " + me.getMessage());
			}
		}
		catch(Exception e)
		{
			throw new DatabaseConfigurationException("Error insertando o actualizando propiedades: " + e.getMessage());
		}
	}
	
	/**
	 * Carga el nombre del nodo de una variable de ambiente
	 * @return
	 */
	private String cargarNombreNodo()
	{
		Context ctx = null;
		String nombreNodo = null;
		try{
			ctx = new InitialContext();
			if(ctx!=null){
				nombreNodo = (String) ctx.lookup(JNDI_NOMBRE_NODO);
			}
			
		} catch (NamingException e) {
			logger.warn("Error no se encontro variable de ambiente para la configuración por nodos: " + e.getMessage());
		}
		return nombreNodo;
	}
}
