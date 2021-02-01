package com.egoveris.shareddocument.base.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.DavException;
import org.apache.jackrabbit.webdav.MultiStatus;
import org.apache.jackrabbit.webdav.MultiStatusResponse;
import org.apache.jackrabbit.webdav.client.methods.CheckoutMethod;
import org.apache.jackrabbit.webdav.client.methods.CopyMethod;
import org.apache.jackrabbit.webdav.client.methods.DavMethod;
import org.apache.jackrabbit.webdav.client.methods.DeleteMethod;
import org.apache.jackrabbit.webdav.client.methods.LockMethod;
import org.apache.jackrabbit.webdav.client.methods.MkColMethod;
import org.apache.jackrabbit.webdav.client.methods.MoveMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;
import org.apache.jackrabbit.webdav.client.methods.UnLockMethod;
import org.apache.jackrabbit.webdav.client.methods.VersionControlMethod;
import org.apache.jackrabbit.webdav.lock.Scope;
import org.apache.jackrabbit.webdav.lock.Type;
import org.apache.jackrabbit.webdav.property.DavProperty;
import org.apache.jackrabbit.webdav.property.DavPropertySet;

import com.egoveris.shareddocument.base.exception.AlfrescoException;
import com.egoveris.shareddocument.base.exception.AlreadyLockedException;
import com.egoveris.shareddocument.base.exception.FalloPrecondicionException;
import com.egoveris.shareddocument.base.exception.WebDAVConnectionException;
import com.egoveris.shareddocument.base.exception.WebDAVEmptyFilenameException;
import com.egoveris.shareddocument.base.exception.WebDAVEmptyRelativeUriException;
import com.egoveris.shareddocument.base.exception.WebDAVEmptySpacenameException;
import com.egoveris.shareddocument.base.exception.WebDAVNotImplementedMethodException;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.ILockResource;

public final class WebDavAlfrescoServiceImpl extends WebDavServiceImpl {

	// /**
	// * Se asegura que al pasar el GC se liberan todas las instancias de
	// * conexiones HTTP tomadas por este servicio.
	// *
	// * @throws Throwable
	// * en caso de resultar problemas al liberar el
	// * ConnectionManager.
	// */
	// protected void finalize() throws Throwable {
	// try {
	// ((MultiThreadedHttpConnectionManager) this.clientConnection
	// .getHttpConnectionManager()).shutdown();
	// } catch (Exception e) {
	// throw new WebDAVConnectionException("Couldn't connect to "
	// + this.getURI(""), e);
	// }
	// }

	/**
	 * Obtiene un listado de los documentos en el repositorio
	 * 
	 * @param key
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final List<WebDAVResourceBean> listSpace(final String relativeUri, final String username, final String pwd) {
		final HttpClient client = initConnection(username, pwd);
		final List<WebDAVResourceBean> documentosAlfrescoList = new ArrayList<WebDAVResourceBean>();
		PropFindMethod method = null;
		try {
			method = new PropFindMethod(this.getURI(relativeUri), DavConstants.PROPFIND_ALL_PROP, DavConstants.DEPTH_1);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, this.getURI(relativeUri));
			}
			final MultiStatus multiStatus = method.getResponseBodyAsMultiStatus();
			final MultiStatusResponse[] responses = multiStatus.getResponses();
			for (final MultiStatusResponse currResponse : responses) {
				final String relativePath = currResponse.getHref().replaceAll(this.documentRoot, "");
				final DavPropertySet set = currResponse.getProperties(HttpStatus.SC_OK);
				final WebDAVResourceBean doc = this.fillWebDAVResource(set, relativePath, currResponse.getHref());
				if (!doc.getReferencia().equals("//")) {
					documentosAlfrescoList.add(doc);
				}
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.consulta.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.consulta.io", ioe);
		} catch (final DavException dave) {
			throw new AlfrescoException("error.documentos.consulta.dav", dave);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return documentosAlfrescoList;
	}

	private WebDAVResourceBean fillWebDAVResource(final DavPropertySet set, String relativePath, final String href)
			throws UnsupportedEncodingException {
		final WebDAVResourceBean doc = new WebDAVResourceBean();

		doc.setHref(href);

		final DavProperty contentType = set.get("getcontenttype");
		if (contentType != null) {
			doc.setMimeType((String) contentType.getValue());
		}

		final int size = Integer.valueOf((String) set.get("getcontentlength").getValue());
		if (size == 0) {
			doc.setDirectorio(true);
		} else {
			doc.setSize(size);
		}
		doc.setNombre(this.obtenerNombreSinRuta(href));
		relativePath = URLDecoder.decode(relativePath, "UTF-8");
		doc.setReferencia(relativePath);

		final String creationDateStr = (String) set.get("creationdate").getValue();
		if (StringUtils.isNotEmpty(creationDateStr)) {
			final DateFormat df = DavConstants.creationDateFormat;
			Date creationDate = null;
			try {
				creationDate = df.parse(creationDateStr);
			} catch (final ParseException e) {
				logger.warn("creationDate couldn't be parsed", e);
			}
			if (creationDate != null) {
				doc.setFechaCreacion(creationDate);
			}
		}
		final String modificationDateStr = (String) set.get("getlastmodified").getValue();
		if (StringUtils.isNotEmpty(creationDateStr)) {
			final DateFormat df = DavConstants.modificationDateFormat;
			Date modificationDate = null;
			try {
				modificationDate = df.parse(modificationDateStr);
			} catch (final ParseException e) {
				logger.warn("modificationDate couldn't be parsed", e);
			}
			if (modificationDate != null) {
				doc.setFechaModificacion(modificationDate);
			}
		}
		return doc;
	}

	/**
	 * @param href
	 *            TODO si es un directorio, devuelve el nombre del directorio.
	 *            Tener en cuenta que si es un directorio, puede o no terminar
	 *            con una barra. Si es un archivo, devuelve el nombre del
	 *            archivo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String obtenerNombreSinRuta(final String href) throws UnsupportedEncodingException {
		final String[] carpetas = href.split("/");
		String nombreDirectorio = null;
		final List<String> wordList = Arrays.asList(carpetas);
		nombreDirectorio = wordList.get(wordList.size() - 1);
		return URLDecoder.decode(nombreDirectorio, "UTF-8");
	}

	/**
	 * Mueve un archivo a través del método WebDAV.
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param keyRepoDestino
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param fileName
	 *            : nombre de archivo a que se quiere copiar.
	 * @param fileNameDestino
	 *            : Nombre de archivo a pegar
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final void moveFile(final String relativeUriOri, final String relativeUriDest, final String sourceFilename,
			final String filenameDest, final String username, final String pwd) {

		final HttpClient client = initConnection(username, pwd);
		DavMethod method = null;
		try {
			final String escapedUriOrigen = this.getURI(relativeUriOri + "/" + sourceFilename);
			final String escapedUriDestino = this.getURI(relativeUriDest + "/" + filenameDest);
			method = new MoveMethod(escapedUriOrigen, escapedUriDestino, true);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, escapedUriOrigen + " --> " + escapedUriDestino);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.copyFile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.copyFile.io", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	// TODO: Agregar comprobacion de not null de origen y destino tanto de URI
	// como de filename
	@Override
	public final void copyFile(final String relativeUriOri, final String relativeUriDest, final String sourceFilename,
			final String filenameDest, final String username, final String pwd) {

		final HttpClient client = initConnection(username, pwd);
		DavMethod method = null;
		try {
			final String escapedUriOrigen = this.getURI(relativeUriOri + "/" + sourceFilename);
			final String escapedUriDestino = this.getURI(relativeUriDest + "/" + filenameDest);
			method = new CopyMethod(escapedUriOrigen, escapedUriDestino, true);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, escapedUriOrigen + " --> " + escapedUriDestino);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.copyFile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.copyFile.io", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	/**
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties.
	 * @param uri
	 *            : ubicación del archivo incluido el nombre del mismo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final WebDAVResourceBean getFile(final String relativeUri, final String usuario, final String password) {
		WebDAVResourceBean resourceBean = null;
		GetMethod method = null;
		PropFindMethod propMethod = null;
		try {
			final HttpClient client = this.initConnection(usuario, password);
			propMethod = new PropFindMethod(this.getURI(relativeUri), DavConstants.PROPFIND_ALL_PROP,
					DavConstants.DEPTH_0);
			int statusCode = client.executeMethod(propMethod);
			if (!propMethod.succeeded()) {
				this.processStatusCode(statusCode, this.getURI(relativeUri));
			}
			final MultiStatus multiStatus = propMethod.getResponseBodyAsMultiStatus();
			final MultiStatusResponse[] responses = multiStatus.getResponses();
			final MultiStatusResponse currResponse = responses[0];
			final String relativePath = currResponse.getHref().replaceAll(this.documentRoot, "");
			final DavPropertySet set = currResponse.getProperties(200);
			resourceBean = new WebDAVResourceBean();
			resourceBean = this.fillWebDAVResource(set, relativePath, currResponse.getHref());
			method = new GetMethod(this.getURI(relativeUri));
			statusCode = client.executeMethod(method);
			if (statusCode < 200 || statusCode >= 300) {
				this.processStatusCode(statusCode, this.getURI(relativeUri));
			}
			final ByteArrayOutputStream bis = new ByteArrayOutputStream();
			final BufferedInputStream content = new BufferedInputStream(method.getResponseBodyAsStream());
			int value = content.read();
			while (value != -1) {
				bis.write(value);
				value = content.read();
			}
			resourceBean.setArchivo(bis.toByteArray());
		} catch (final HttpException httpe) {
			throw new WebDAVConnectionException(httpe.getMessage());
		} catch (final IOException ioe) {
			throw new WebDAVConnectionException(ioe.getMessage());
		} catch (final DavException dave) {
			throw new WebDAVConnectionException(dave.getMessage());
		}

		finally {
			if (method != null) {
				method.releaseConnection();
			}
			if (propMethod != null) {
				propMethod.releaseConnection();
			}
		}
		return resourceBean;
	}

	public byte[] getFileByHref(final String href) {
		final byte[] archivo = null;

		return archivo;
	}

	/**
	 * Crea un workspace en Alfresco
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param uri
	 *            : ubicación del archivo incluido el nombre del mismo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final void createSpace(String relativeUri, final String nombreSpace, final String username,
			final String pwd) {

		final HttpClient client = this.initConnection(username, pwd);
		MkColMethod method = null;
		try {
			if (StringUtils.isEmpty(nombreSpace)) {
				throw new WebDAVEmptySpacenameException();
			}
			// Puede venir null entonces la transformamos a empty para evitar
			// errores de nulls
			if (relativeUri == null) {
				relativeUri = "";
			}
			final String uriOrigen = this.getURI(relativeUri + "/" + nombreSpace);
			method = new MkColMethod(uriOrigen);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uriOrigen);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.createspace.http", httpe);
		} catch (final IOException ioe) {
			ioe.printStackTrace();
			throw new AlfrescoException("error.documentos.createspace.io", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	/**
	 * Borra un archivo en Alfresco
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param fileName
	 *            : nombre de archivo a que se quiere borrar.
	 * @param uri
	 *            : ubicación del archivo incluido el nombre del mismo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final void deleteFile(final String relativeUri, final String filename, final String username,
			final String pwd) {

		final HttpClient client = this.initConnection(username, pwd);
		DeleteMethod method = null;
		try {
			if (StringUtils.isEmpty(filename)) {
				throw new AlfrescoException("error.documentos.deletefile.emptyfilename");
			}
			final String uri = this.getURI(relativeUri + "/" + filename);
			method = new DeleteMethod(uri);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uri);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.deletefile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.deletefile.ioe", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	@Override
	public final void deleteSpace(final String relativeUri, final String spaceName, final String username,
			final String pwd) {

		final HttpClient client = this.initConnection(username, pwd);
		DeleteMethod method = null;
		try {
			if (StringUtils.isEmpty(spaceName)) {
				throw new AlfrescoException("error.documentos.deletefile.emptyfilename");
			}
			final String uri = this.getURI(relativeUri + "/" + spaceName);
			method = new DeleteMethod(uri);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uri);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.deletefile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.deletefile.ioe", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	/**
	 * Control de versiones para los archivos de Alfresco.
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param fileName
	 *            : nombre de archivo a que se quiere borrar.
	 * @param uri
	 *            : ubicación del archivo incluido el nombre del mismo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	// TODO: Implementar y probar
	public final boolean versionControl(final String keyRepoOrigen, final String codigoExpedienteSADE,
			final String username, final String pwd) {
		final HttpClient client = this.initConnection(username, pwd);
		boolean result = false;
		VersionControlMethod method = null;
		try {
			final String uri = this.getURI(codigoExpedienteSADE);
			method = new VersionControlMethod(uri);
			client.executeMethod(method);
			result = method.succeeded();
		} catch (final Exception ioe) {
			throw new AlfrescoException("error.documentos.versioncontrol.io", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return result;
	}

	@Override
	public final void checkoutResource(final String relativeURI, final String resource, final String username,
			final String password) {
		final HttpClient client = this.initConnection(username, password);
		CheckoutMethod method = null;
		try {
			final String uri = this.getURI(relativeURI + "/" + resource);
			method = new CheckoutMethod(uri);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uri);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.deletefile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.deletefile.ioe", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}

	}

	/**
	 * Crea un archivo en Alfresco
	 * 
	 * @param keyRepoOrigen
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param fileName
	 *            : nombre de archivo a que se quiere borrar.
	 * @param uri
	 *            : ubicación del archivo incluido el nombre del mismo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 */
	@Override
	public final void createFile(final String relativeUri, final String filename, final String username,
			final String pwd, final InputStream content) {
		final HttpClient client = this.initConnection(username, pwd);
		PutMethod method = null;
		try {
			if (StringUtils.isEmpty(relativeUri)) {
				throw new WebDAVEmptyRelativeUriException();
			}
			if (StringUtils.isEmpty(filename)) {
				throw new WebDAVEmptyFilenameException();
			}
			final String uri = this.getURI(relativeUri + "/" + filename);
			method = new PutMethod(uri);
			final RequestEntity requestEntity = new InputStreamRequestEntity(content);
			method = this.prepateForChunkedUpload(method, requestEntity);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uri);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.createfile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.createfile.ioe", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	public final void createFileAndSpaces(final String relativeUri, final String filename, final String username,
			final String pwd, final InputStream content) {
		this.createFile(relativeUri, filename, username, pwd, content);
	}

	/**
	 * Método de servicio para bloquear un recurso que este en uso. Tambien se
	 * obtiene el lockToken para poder hacer el unLock.
	 * 
	 * @param fileName
	 *            : nombre de archivo a bloquear
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear
	 * @param key
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @throws AlreadyLockedException
	 * @throws StatusCodeException
	 */
	public final ILockResource lockFile(final String relativeUri, final String filename, final String user,
			final String password) throws AlreadyLockedException {
		String token = null;
		final WebDavAlfrescoLockResource lockToken = new WebDavAlfrescoLockResource();
		GetMethod davMethod = null;
		LockMethod method = null;
		int statusCode = 0;
		final HttpClient clientConnection = this.initConnection(user, password);
		try {
			final String uri = this.getURI(relativeUri + "/" + filename);
			final Scope lockScope = Scope.EXCLUSIVE;
			final Type lockType = Type.WRITE;
			final String owner = user;
			final long timeout = 100000;
			final boolean isDeep = false;
			davMethod = new GetMethod(uri);
			clientConnection.executeMethod(davMethod);
			final String[] tokens = StringUtils.split(davMethod.getResponseHeader("ETag").getValue().replace("\"", ""),
					"_");
			token = tokens[0].concat(":" + owner);
			method = new LockMethod(uri, lockScope, lockType, owner, timeout, isDeep);
			statusCode = clientConnection.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, uri);
			}
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.lockfile.ioe", ioe);
		} finally {
			if (davMethod != null) {
				davMethod.releaseConnection();
			}
			if (method != null) {
				method.releaseConnection();
			}
			lockToken.setToken(token);
		}
		return lockToken;
	}

	/**
	 * Método de servicio para desbloquear un recurso que ya se dejó de usar.
	 * para poder desbloquear el recurso si o si tiene que estar el lockToken
	 * obtenido cuando se realizó el lock.
	 * 
	 * @param fileName
	 *            : nombre de archivo a bloquear.
	 * @param codigoExpedienteSADE
	 *            : directorio donde va a estar guardado el recurso a bloquear.
	 * @param key
	 *            : referencia al archivo de configuracion
	 *            webdav-config.properties.
	 * @param lockToken
	 *            : se necesita si o si para desbloquear el archivo, se genera
	 *            en el momento del bloqueo.
	 * @param username
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @param pwd
	 *            : si es nulo o vacio son usados los valores del archivo de
	 *            configuracion.
	 * @throws FalloPrecondicionException
	 */
	public final boolean unLockFile(final String relativeUri, final String filename, final String key,
			final ILockResource lockToken, final String user, final String password) throws FalloPrecondicionException {

		UnLockMethod method = null;
		String token = null;
		final HttpClient clientConnection = this.initConnection(user, password);
		int statusCode = 0;
		try {
			final String uri = this.getURI(relativeUri + "/" + filename);
			if (lockToken instanceof WebDavAlfrescoLockResource) {
				token = ((WebDavAlfrescoLockResource) lockToken).getToken();
			} else {
				throw new IllegalArgumentException(
						lockToken.getClass().toString() + " is not of type WebDavAlfrescoLockResource");
			}
			method = new UnLockMethod(uri, token);
			statusCode = clientConnection.executeMethod(method);
			System.out.println("STATUS: " + statusCode);
			if (statusCode == 412) {
				throw new FalloPrecondicionException("Fallo precondicion");
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.unlockfile.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.unlockfile.io", ioe);
		} catch (final FalloPrecondicionException e) {
			throw new FalloPrecondicionException("error.documentos.unlockfile.falloprecondicion", e);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return true;
	}

	@Override
	public int getFileSize(final String relativeURI, final String username, final String password) {
		throw new WebDAVNotImplementedMethodException("Metodo no implementado");
	}

	@Override
	public boolean existFile(final String relativeURI, final String username, final String password) {
		throw new WebDAVNotImplementedMethodException("Metodo no implementado");
	}

	@Override
	public FileAsStreamConnectionWebDav getFileAsStream(final String relativeUri, final String username,
			final String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @author apereziz Taken from version 1.7.4-SNAPSHOT
	 * @see com.egoveris.commons.guardadocumental.services.IWebDavService#moveFolder(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void moveFolder(final String relativeUriOri, final String relativeUriDest, final String username,
			final String pwd) {
		final HttpClient client = initConnection(username, pwd);
		DavMethod method = null;
		try {
			final String escapedUriOrigen = this.getURI(relativeUriOri + "/");
			final String escapedUriDestino = this.getURI(relativeUriDest + "/");
			method = new MoveMethod(escapedUriOrigen, escapedUriDestino, true);
			final int statusCode = client.executeMethod(method);
			if (!method.succeeded()) {
				this.processStatusCode(statusCode, escapedUriOrigen + " --> " + escapedUriDestino);
			}
		} catch (final HttpException httpe) {
			throw new AlfrescoException("error.documentos.copyFolder.http", httpe);
		} catch (final IOException ioe) {
			throw new AlfrescoException("error.documentos.copyFolder.io", ioe);
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
	}
}