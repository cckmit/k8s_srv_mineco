package com.egoveris.shareddocument.base.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.client.util.FileUtils;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;

import com.egoveris.shareddocument.base.exception.AlfrescoException;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;

public class AlfrescoCMISServiceImpl extends WebDavServiceImpl {
	
	protected String user = "admin";
	protected String pass = "admin";
	
	protected String url = "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom";
	protected String repositoryId = "-default-";
	
	protected static final String ERROR_PARAM_NOT_VALID = "Parametros no validos";
	protected static final String ERROR_OBJECT_NOT_FOUND = "Objeto no encontrado";
	protected static final String ERROR_OBJECT_NOT_FILE = "El objeto recuperado no es un archivo";
	protected static final String ERROR_OBJECT_NOT_FOLDER = "El objeto recuperado no es un directorio";
	protected static final String ERROR_MIME_TYPE = "Error al obtener el tipo mime";
	protected static final String ERROR_GET_FILE = "Error al obtener al archivo";

	/**
	 * Obtiene el listado de documentos/directorios de un determinado repositorio. La operacion no es recursiva.
	 */
	@Override
	public List<WebDAVResourceBean> listSpace(final String relativeUri, final String username, final String pwd) {
		
		final List<WebDAVResourceBean> documentosAlfrescoList = new ArrayList<>();
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUri)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Crea sesion de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Obtengo la ruta relativa del directorio a revisar.
		String relativePath = obtieneDirectorioRelativo(relativeUri);
		
		
		try{
			//Obtengo el objeto CMIS
			CmisObject relativeObject = FileUtils.getObject(relativePath, session);
			
			WebDAVResourceBean resource;
			
			//verificar si es directorio o documento
			if(relativeObject instanceof Folder){
				
				resource = cmisFolderToWDResource(relativeObject);
				//Añade el directorio actual
				documentosAlfrescoList.add(resource);
				
				Folder relativeFolder = (Folder) relativeObject;
				 //Verificar si tiene hijos
				for (CmisObject child: relativeFolder.getChildren()) {
				    final WebDAVResourceBean cmisChild = cmisObjectToWDResource(child);
				    
				    //Añade los hijos (directorios y documentos)
				    documentosAlfrescoList.add(cmisChild);
				}
			}else{
				resource = cmisDocumentToWDResource(relativeObject);
				//Añade el documento actual
				documentosAlfrescoList.add(resource);
			}
		}catch(CmisObjectNotFoundException notFoudEx){
			logger.error(ERROR_OBJECT_NOT_FOUND);
			throw new AlfrescoException(ERROR_OBJECT_NOT_FOUND, notFoudEx);
		}

		//Limpiamos la session de alfresco
		session.clear();
		
		//Retorno el listado de documentos y directorios
		return documentosAlfrescoList;
	}

	
	
	
	@Override
	public void createFile(String relativeUri, String filename, String username, String pwd, InputStream inputStream) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUri, filename) || inputStream == null){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Inicia session de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Arma la ruta del directorio de destino del archivo
		String relativePathDes = obtieneDirectorioRelativo(relativeUri);
		
		// verificar si el nombre de archivo existe en el directorio
		// destino, de ser asi, se debe eliminar y conservar solo el actual.		
		if(existFile(relativeUri + "/" + filename, username, pwd)){
			//si ya existe un archivo con el mismo nombre lo eliminamos antes de crear el nuevo
			deleteFile(relativeUri, filename, username, pwd);
		}
		
		//Directorio destino
		Folder directorioDestino = FileUtils.getFolder(relativePathDes, session);
		
		//Propiedades del documento a crear
		Map<String, Object> properties = new HashMap<>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, filename);
		
		//TODO cpavezba - analizar con el equipo si se requieren mas propiedades para el archivo.
		
		//Es necesario contar con el byte[] del archivo a crear y el tipo mime
		ContentStream contentStream = null;		
		try {
			
			//Obtiene el mime type de un archivo
			Tika tike = new Tika();
			String mimetype = tike.detect(inputStream, filename);
			
			//Se crea el content usando los parametros de entrada
			contentStream = session.getObjectFactory().createContentStream(filename, inputStream.available(), mimetype,
					inputStream);
			
			//Crea el archivo en el directorio de destino
			directorioDestino.createDocument(properties, contentStream, VersioningState.MAJOR);
			
		} catch (IOException ioe) {
			// generar log y relanzar el error
			logger.error(ERROR_MIME_TYPE);
			throw new AlfrescoException(ERROR_MIME_TYPE, ioe);
		}
		
		//Limpiamos la session de alfresco
		session.clear();
		
		// TODO cpavezba - es necesario retornar el Identificador del nuevo
		// archivo creado para poder accederlo de forma rapida.
	}


	

	@Override
	public void copyFile(String relativeUriOri, String relativeUriDest, String filenameOri, String filenameDest,
			String username, String pwd) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUriOri, relativeUriDest,filenameOri)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		// verificar si el nombre de archivo existe en el directorio
		// destino, de ser asi, se debe eliminar y conservar solo el actual.		
		if(existFile(relativeUriDest + "/" + filenameOri, username, pwd)){
			//si ya existe un archivo con el mismo nombre lo eliminamos antes de crear el nuevo
			deleteFile(relativeUriDest, filenameOri, username, pwd);
		}
		
		//Inicia session de alfresco
		Session session = iniciarSessionAlfresco();
		
		String relativePathDes = obtieneDirectorioRelativo(relativeUriDest);
		String documentOri = obtieneDirectorioRelativo(filenameOri);
		
		//Directorio puede no existor o bien la ruta corresponde a un documento 
		Folder directorioDestino = FileUtils.getFolder(relativePathDes, session);
		
		//El objeto puede no existor o bien ser un documento, sino un directorio
		CmisObject object = FileUtils.getObject(documentOri, session);
		
		if(object instanceof Document){
			Document documentToMove = (Document) object;
			//El copiar el documento podria dar un fallo
			//El archivo podria ya existir			
			documentToMove.copy(directorioDestino);
		}
		//Limpiamos la session de alfresco
		session.clear();
	}

	@Override
	public void moveFile(String relativeUriOri, String relativeUriDest, String filenameOri, String filenameDest,
			String username, String pwd) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUriDest,filenameOri)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Inicia sesion de alfresco
		Session session = iniciarSessionAlfresco();
		
		String relativePathOri = obtieneDirectorioRelativo(relativeUriOri);
		String relativePathDes = obtieneDirectorioRelativo(relativeUriDest);
		String documentOri = obtieneDirectorioRelativo(filenameOri);
		
		//Directorio puede no existor o bien la ruta corresponde a un documento 
		Folder directorioOrigen = FileUtils.getFolder(relativePathOri, session);
		
		//Directorio puede no existor o bien la ruta corresponde a un documento 
		Folder directorioDestino = FileUtils.getFolder(relativePathDes, session);
		
		//El objeto puede no existor o bien ser un documento, sino un directorio
		CmisObject object = FileUtils.getObject(documentOri, session);
		
		if(object instanceof Document){
			Document documentToMove = (Document) object;
			
			// verificar si el nombre de archivo existe en el directorio
			// destino, de ser asi, se debe eliminar y conservar solo el actual.		
			if(existFile(relativeUriDest + "/" + filenameOri, username, pwd)){
				//si ya existe un archivo con el mismo nombre lo eliminamos antes de crear el nuevo
				deleteFile(relativeUriDest, filenameOri, username, pwd);
			}
			
			//Mueve el documento al directorio destino.
			documentToMove.move(directorioOrigen, directorioDestino);
		}
		//Limpiamos la session de alfresco
		session.clear();
	}
	
	@Override
	public void deleteFile(String relativeUri, String filename, String username, String pwd) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUri, filename)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Crea una session de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Obtenemos el directorio que contiene el archivo a borrar
		String relativePath = obtieneDirectorioRelativo(relativeUri);
		
		//El objeto puede no existor o bien ser un documento, sino un directorio
		CmisObject object = FileUtils.getObject(relativePath + filename, session);
		
		//Obtengo el documento
		Document document = (Document) object;
		
		//Elimino el documento
		document.delete(true);
		
		//Limpio la sesion de alfresco
		session.clear();
	}

	@Override
	public void moveFolder(String relativeUriOri, String relativeUriDest, String username, String pwd) {
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUriOri, relativeUriDest)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Se crea session de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Ruta origen
		String relativePathOri = obtieneDirectorioRelativo(relativeUriOri);
		//Ruta destino
		String relativePathDes = obtieneDirectorioRelativo(relativeUriDest);
		
		//Folder origen
		Folder directorioOrigen = FileUtils.getFolder(relativePathOri, session);
		
		if (directorioOrigen != null) {
			// Id directorio padre
			String parentIdOri = directorioOrigen.getParentId();
			// Folder padre
			Folder parentFolderOri = FileUtils.getFolder(parentIdOri, session);

			// Folder destino
			Folder directorioDestino = FileUtils.getFolder(relativePathDes, session);
			
			if (directorioDestino != null && parentFolderOri != null) {
				// Movemos el directorio desde el padre al destino
				directorioOrigen.move(parentFolderOri, directorioDestino);
			} else {
				// lanzar excepcion no existe directorio destino
				logger.error("No existe el directorio de destino");
				throw new AlfrescoException("No existe el directorio de destino");
			}
		} else {
			// lanzar excepcion no existe directorio origen
			logger.error("No existe el directorio de origen");
			throw new AlfrescoException("No existe el directorio de origen");
		}
		//Limpiamos la session de alfresco
		session.clear();
	}
	
	@Override
	public void createSpace(String relativeUri, String nombreSpace, String username, String pwd) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUri, nombreSpace)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Sesion de alfresco
		Session session = iniciarSessionAlfresco();
		//Ruta de la carpeta padre donde se creara el directorio
		String relativePath = obtieneDirectorioRelativo(relativeUri);
		//Folder de destino, controlar si se le pasa como parametro un Documento
		Folder directorioDes = FileUtils.getFolder(relativePath, session);
		
		//TODO cpavezba - verificar si es necesario alguna propiedad adicional para los directorios.
		Map<String, Object> newFolderProps = new HashMap<>();
		newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
		newFolderProps.put(PropertyIds.NAME, nombreSpace);
		
		//Se crea el directorio en el repositorio de alfresco.
		directorioDes.createFolder(newFolderProps);
		
		//Limpio la sesion de alfresco
		session.clear();
	}

	@Override
	public void deleteSpace(String relativeUri, String spaceName, String username, String pwd) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(spaceName)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}

		// create session
		Session session = iniciarSessionAlfresco();
		
		//Obtengo la ruta del directorio
		String relativePath = obtieneDirectorioRelativo(relativeUri);
		
		//El objeto puede no existor o bien ser un documento, sino un directorio
		CmisObject object = FileUtils.getObject(relativePath + spaceName, session);
		
		if(!(object instanceof Folder)){
			logger.error(ERROR_OBJECT_NOT_FOLDER);
			throw new AlfrescoException(ERROR_OBJECT_NOT_FOLDER);
		}
		
		//Obtengo el documento
		Folder folderDelete = (Folder) object;
		
		//Elimino el documento
		folderDelete.delete(true);
		
		//Limpio la sesion de alfresco
		session.clear();
	}

	@Override
	public WebDAVResourceBean getFile(String relativeUri, String username, String pwd) {
		WebDAVResourceBean wdRes;
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeUri)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		//Inicio la session de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Obtengo el objeto CMIS
		CmisObject object = FileUtils.getObject(relativeUri, session);
		
		//Verifico que sea un documento y no un directorio
		if(!(object instanceof Document)){
			logger.error(ERROR_OBJECT_NOT_FILE);
			throw new AlfrescoException(ERROR_OBJECT_NOT_FILE);
		}
		
		Document document = (Document) object;
		
		//Creo una nueva instancia para retornar
		wdRes = new WebDAVResourceBean();

		wdRes.setDirectorio(false);
		wdRes.setNombre(document.getName());
		wdRes.setFechaCreacion(document.getCreationDate().getTime());
		wdRes.setMimeType(document.getPropertyValue(PropertyIds.CONTENT_STREAM_MIME_TYPE));
		wdRes.setFechaModificacion(document.getLastModificationDate().getTime());
		
		if(document.getPaths() != null && !document.getPaths().isEmpty()){
			wdRes.setReferencia(document.getPaths().get(0));
    		wdRes.setHref(document.getPaths().get(0));
		}

		//Propiedades adicionales
		
    	//TODO cpavezba - Dependiendo del tamaño de un archivo, tal vez un integer no sea suficiente
    	wdRes.setSize(Integer.valueOf(document.getPropertyValue(PropertyIds.CONTENT_STREAM_LENGTH).toString()));
    	
    	try {
    		//Obtengo el byte[]
    		wdRes.setArchivo(IOUtils.toByteArray(document.getContentStream().getStream()));
		} catch (IOException e) {
			logger.error(ERROR_GET_FILE, e);
		}
    	
    	//Retorna el objeto WebDAVResourceBean
		return wdRes;
	}

	@Override
	public void checkoutResource(String relativeURI, String resource, String username, String password) {
		// TODO no me queda claro la funcionalidad de esta operacion
		logger.error("No implementada");
		throw new AlfrescoException("No implementada");
	}

	@Override
	public int getFileSize(String relativeURI, String username, String password) {
		
		//Validar parametros de entrada
		if(!validarCamposObligatorios(relativeURI)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Creo la sesion de alfresco
		Session session = iniciarSessionAlfresco();
		
		try{
			
			//Obtengo el objeto CMIS
			CmisObject object = FileUtils.getObject(relativeURI, session);
			
			if(!(object instanceof Document)){
				throw new AlfrescoException(ERROR_OBJECT_NOT_FILE);
			}
			
			Document document = (Document) object;
			
			//TODO cpavezba - Dependiendo del tamaño de un archivo, tal vez un integer no sea suficiente
			return Integer.valueOf(document.getPropertyValue(PropertyIds.CONTENT_STREAM_LENGTH).toString());
			
		}catch(CmisObjectNotFoundException notFoundEx){
			//controlo la excepcion y cierro la session de alfresco
			logger.error(ERROR_OBJECT_NOT_FOUND, notFoundEx);
			session.clear();
			return 0;
		}
	}

	@Override
	public boolean existFile(String relativeURI, String username, String pwd) {
		
		if(!validarCamposObligatorios(relativeURI)){
			//Parametros no validos. Lanzar excepcion.
			logger.error(ERROR_PARAM_NOT_VALID);
			throw new AlfrescoException(ERROR_PARAM_NOT_VALID);
		}
		
		//Creamos session de alfresco
		Session session = iniciarSessionAlfresco();
		
		//Obtenemos directorio
		String relativePath= getDirectorioRelativo(relativeURI);
		
		try{
			//Obtenemos el objeto 
			CmisObject object = session.getObjectByPath(relativePath);
			
			if(object != null){
				return true;
			}
		}catch(CmisObjectNotFoundException notFoundEx){
			//Se controla la excepcion y se retorna false
			logger.error(ERROR_OBJECT_NOT_FOUND, notFoundEx);
			return false;
		}
		//Limpiamos la sesion de alfresco
		session.clear();
			
		return false;
	}

	@Override
	public FileAsStreamConnectionWebDav getFileAsStream(String relativeUri, String username, String pwd) {
		// TODO Pendiente recupera el contenido de un archivo
		logger.error("No implementada");
		throw new AlfrescoException("No implementada");
	}

	/**
	 * Verifica el parametro de entrada y retorna la ruta relativa del directorio.
	 * @param relativeUri
	 * @return
	 */
	private String getDirectorioRelativo(String relativeUri) {
		String relativePathDes;
		if(relativeUri == null || relativeUri.length() == 0 || "/".equals(StringUtils.trim(relativeUri))){
			relativePathDes = "/";
		}else{
			relativePathDes = "/".concat(relativeUri);
		}
		return relativePathDes;
	}
	
	/**
	 * Verifica el parametro de entrada y retorna la ruta relativa del directorio.
	 * @param relativeUri
	 * @return
	 */
	private String obtieneDirectorioRelativo(String relativeUri) {
		String relativePathDes;
		if(relativeUri == null || relativeUri.length() == 0 || "/".equals(StringUtils.trim(relativeUri))){
			relativePathDes = "/";
		}else{
			relativePathDes = "/".concat(relativeUri).concat("/");
		}
		return relativePathDes;
	}

	
	/**
	 * Valida una serie de parametros entregados como argumento.
	 * @param params
	 * @return valido o no valido
	 */
	private static boolean validarCamposObligatorios(String... params){
		//Por cada parametro ingresado validamos.
		for (String param : params) {
			// Valida si el parametro es nulo o vacio
			if (param == null || "".equals(StringUtils.trim(param))) {
				return false;
			}
		}
		//Si pasa todas las validaciones retorna correcto
		return true;
	}
	
	/**
	 * Crea una nueva session de opencmis para conectar con el repositorio alfresco.
	 * 
	 * @return Session openCMIS alfresco
	 */
	private Session iniciarSessionAlfresco(){
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<>();

		// user credentials
		parameter.put(SessionParameter.USER, user);
		parameter.put(SessionParameter.PASSWORD, pass);

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, url);
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.REPOSITORY_ID, repositoryId);
		try{
			// create session
			return factory.createSession(parameter);
		}catch(CmisConnectionException conex){
			logger.error("Error al conectarse a alfresco... ", conex);
			throw new AlfrescoException("Error al conectarse a alfresco... ");
		}
	}
	
	/**
	 * Crea una nueva instancia de WebDAVResourceBean a partir de un objeto CMIS. estos pueden ser Document o Folder.
	 * @param cmisObject CmisObject
	 * @return WebDAVResourceBean
	 */
	private WebDAVResourceBean cmisObjectToWDResource(CmisObject cmisObject) {
		final WebDAVResourceBean resource = new WebDAVResourceBean();
		
		if(cmisObject instanceof Folder){
			//Directorio
			Folder folder = (Folder) cmisObject;
			resource.setNombre(folder.getName());
			
			//Refactorizar
			resource.setDirectorio(true);
			resource.setFechaCreacion(folder.getCreationDate().getTime());
			resource.setFechaModificacion(folder.getLastModificationDate().getTime());
			resource.setHref(folder.getPropertyValue(PropertyIds.PATH)+"/");
			resource.setMimeType(folder.getPropertyValue(PropertyIds.BASE_TYPE_ID));
			resource.setNombre(folder.getName());
			resource.setReferencia("/".concat(folder.getPropertyValue(PropertyIds.NAME)).concat("/"));
			resource.setSize(0);
		
		}else if(cmisObject instanceof Document){
			//Documento
			Document document = (Document) cmisObject;
			resource.setDirectorio(false);
			resource.setNombre(document.getName());
			resource.setFechaCreacion(document.getCreationDate().getTime());
			resource.setMimeType(document.getPropertyValue(PropertyIds.CONTENT_STREAM_MIME_TYPE));
			resource.setFechaModificacion(document.getLastModificationDate().getTime());
			resource.setSize(Integer.valueOf(document.getPropertyValue(PropertyIds.CONTENT_STREAM_LENGTH).toString()));
			
			if(document.getPaths() != null && !document.getPaths().isEmpty()){
				resource.setReferencia(document.getPaths().get(0));
				resource.setHref(document.getPaths().get(0));
			}
			
			try {
				resource.setArchivo(IOUtils.toByteArray(document.getContentStream().getStream()));
			} catch (IOException e) {
				logger.error(ERROR_GET_FILE, e);
			}
		}
		return resource;
	}
	
	/**
	 * Crea una nueva instancia de WebDAVResourceBean y asigna los atributos a
	 * partir de un objeto Document de CMIS.
	 * 
	 * @param object
	 *            CmisObject
	 * @return WebDAVResourceBean
	 */
	private WebDAVResourceBean cmisFolderToWDResource(CmisObject object){
		
		WebDAVResourceBean resource = new WebDAVResourceBean();
		
		Folder folder = (Folder) object;
		
		//Refactorizar
		resource.setDirectorio(true);
		resource.setFechaCreacion(folder.getCreationDate().getTime());
		resource.setFechaModificacion(folder.getLastModificationDate().getTime());
		resource.setHref(folder.getPropertyValue(PropertyIds.PATH));
		resource.setMimeType(folder.getPropertyValue(PropertyIds.BASE_TYPE_ID));
		resource.setNombre(folder.getName());
		resource.setReferencia("/".concat(folder.getPropertyValue(PropertyIds.NAME)).concat("/"));
		resource.setSize(0);
		
		return resource;
	}
	
	/**
	 * Crea una nueva instancia de WebDAVResourceBean y asigna los atributos a
	 * partir de un objeto Document de CMIS.
	 * 
	 * @param object
	 *            CmisObject
	 * @return WebDAVResourceBean
	 */
	private WebDAVResourceBean cmisDocumentToWDResource(CmisObject object){
		
		WebDAVResourceBean resource = new WebDAVResourceBean();
		
		Document document = (Document) object;
		
		resource.setDirectorio(false);
		resource.setNombre(document.getName());
		try {
			resource.setArchivo(IOUtils.toByteArray(document.getContentStream().getStream()));
		} catch (IOException e) {
			logger.error(ERROR_GET_FILE, e);
		}
		
		resource.setNombre(document.getName());
		resource.setFechaCreacion(document.getCreationDate().getTime());
		resource.setMimeType(document.getPropertyValue(PropertyIds.CONTENT_STREAM_MIME_TYPE));
		resource.setFechaModificacion(document.getLastModificationDate().getTime());
		
		if(document.getPaths() != null && !document.getPaths().isEmpty()){
			resource.setReferencia(document.getPaths().get(0));
			resource.setHref(document.getPaths().get(0));
		}
		
		return resource;
	}

}
