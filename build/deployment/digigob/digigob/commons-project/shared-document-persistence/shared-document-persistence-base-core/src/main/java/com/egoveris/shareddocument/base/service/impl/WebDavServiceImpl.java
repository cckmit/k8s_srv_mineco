package com.egoveris.shareddocument.base.service.impl;

import com.egoveris.shareddocument.base.exception.AlreadyLockedException;
import com.egoveris.shareddocument.base.exception.UnknownWebDAVException;
import com.egoveris.shareddocument.base.exception.WebDAVConflictException;
import com.egoveris.shareddocument.base.exception.WebDAVInternalServerErrorException;
import com.egoveris.shareddocument.base.exception.WebDAVNotImplementedMethodException;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;
import com.egoveris.shareddocument.base.exception.WebDAVUnauthorizedException;
import com.egoveris.shareddocument.base.exception.WebDAVWrongRequestException;
import com.egoveris.shareddocument.base.exception.WevDAVMovedPermanentlyException;
import com.egoveris.shareddocument.base.service.IWebDavService;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.webdav.client.methods.PutMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WebDavServiceImpl implements IWebDavService {

  static Logger logger = LoggerFactory.getLogger(WebDavApacheServiceImpl.class);
  private HttpConnectionManagerParams httpConnectionManagerParams;
  protected String hostname;
  protected int port;
  protected String documentRoot;
  protected String defaultUsername;
  protected String defaultPassword;
  protected String defaultRealm;
  // Workaround:
  // http://blog.scriptkiddie.org/2010/09/06/why-i-hate-java-httpclient-maxconnectionsperhost/
  protected Integer maxHostConnections = 10000;
  protected Integer maxPoolConnections = 100;
  protected Integer defaultConnectionTimeout = new Integer(5000);
  private HostConfiguration hostConfiguration;

  /**
   * Se obtiene la conexión con el servidor
   * 
   * @param key
   *          : referencia al archivo de configuracion webdav-config.properties
   * @param username
   *          : si es nulo o vacio son usados los valores del archivo de
   *          configuracion.
   * @param pwd
   *          : si es nulo o vacio son usados los valores del archivo de
   *          configuracion.
   */
  protected HttpClient initConnection(final String username, final String password) {
    String actualUsername = username;
    String actualPassword = password;
    if (StringUtils.isEmpty(username)) {
      actualUsername = this.getDefaultUsername();
    }

    if (StringUtils.isEmpty(password)) {
      actualPassword = this.getDefaultPassword();
    }
    Credentials creds = this.getCredentials(actualUsername, actualPassword);
    AuthScope authScope = new AuthScope(this.hostname, this.port, this.defaultRealm);
    hostConfiguration = this.getHostConfiguration();
    hostConfiguration.setHost(this.hostname, Integer.valueOf(port));
    HttpConnectionManagerParams params = new HttpConnectionManagerParams();
    params.setLinger(-1);
    params.setSoTimeout(10000);
    params.setStaleCheckingEnabled(true);
    params.setConnectionTimeout(5000);
    SimpleHttpConnectionManager simpleCon = new SimpleHttpConnectionManager();
    simpleCon.setParams(params);
    HttpClient client = new HttpClient(simpleCon);
    client.setHostConfiguration(hostConfiguration);
    client.getState().setCredentials(authScope, creds);
    client.getParams().setAuthenticationPreemptive(false);
    logger.debug("Conectando a " + client.getHost() + ":" + client.getPort());
    return client;
  }

  /**
   * Se asegura que al pasar el GC se liberan todas las instancias de conexiones
   * HTTP tomadas por este servicio.
   * 
   * @throws Throwable
   *           en caso de resultar problemas al liberar el ConnectionManager.
   */
  protected void finalize() throws Throwable {
    // try {
    // ((MultiThreadedHttpConnectionManager) this.clientConnection
    // .getHttpConnectionManager()).shutdown();
    // } catch (Exception e) {
    // throw new WebDAVConnectionException("Couldn't connect to "
    // + this.getURI(""), e);
    // }
  }

  protected void processStatusCode(int statusCode, String location) {
    System.out.println("ERRRROR ----> " + statusCode);
    switch (statusCode) {
    case HttpStatus.SC_BAD_REQUEST:
      throw new WebDAVWrongRequestException(location);
    case HttpStatus.SC_UNAUTHORIZED:
      throw new WebDAVUnauthorizedException(location);
    case HttpStatus.SC_LOCKED:
      throw new AlreadyLockedException(location);
    case HttpStatus.SC_NOT_FOUND:
      throw new WebDAVResourceNotFoundException(location);
    case HttpStatus.SC_CONFLICT:
      throw new WebDAVConflictException(location);
    case HttpStatus.SC_NOT_IMPLEMENTED:
      throw new WebDAVNotImplementedMethodException(location);
    case HttpStatus.SC_INTERNAL_SERVER_ERROR:
      throw new WebDAVInternalServerErrorException(location);
    case HttpStatus.SC_MOVED_PERMANENTLY:
      throw new WevDAVMovedPermanentlyException(location);
    default:
      throw new UnknownWebDAVException(statusCode, location);

    }
  }

  protected String getURI(final String relativeUri) throws URIException {
    return "http://" + this.hostname + ":" + this.port
        + URIUtil.encodePath("/" + this.documentRoot + "/" + relativeUri, "UTF-8");
  }

  protected final PutMethod prepateForChunkedUpload(final PutMethod method,
      final RequestEntity content) {
    Header header = new Header();
    // Permite que el upload se realice por chunks y por tanto permite
    // un retry cuando da error de autenticacion ademas de evitar enviar
    // todo un archivo grande en un solo POST HTTP
    // http://en.wikipedia.org/wiki/Chunked_transfer_encoding
    method.setContentChunked(true);
    // El metodo de arriba no setea el header correcto en la versión 3.x
    // de HttpClient entonces lo seteamos a mano
    header.setName("Transfer-Encoding");
    header.setValue("chunked");
    method.setRequestHeader(header);
    return method;
  }

  /**
   * Obtiene las credencials con las cuales autenticarse al WebDAV. Si el
   * username es vacío o nulo entonces usa las credenciales por defecto.
   * 
   * @param username
   *          Nombre de usuario con el cual autenticarse con el WebDAV
   * @param password
   *          Password correspondiente al username pasado como parámetro.
   * @return Una instancia de Credentials conteniendo la información de intento
   *         de autenticación.
   */
  private Credentials getCredentials(String username, String password) {
    Credentials credentials = null;
    if (StringUtils.isEmpty(username)) {
      credentials = new UsernamePasswordCredentials(this.defaultUsername, this.defaultPassword);
    } else {
      credentials = new UsernamePasswordCredentials(username, password);
    }
    return credentials;
  }

  public final HostConfiguration getHostConfiguration() {
    return hostConfiguration;
  }

  public final void setHostConfiguration(HostConfiguration hostConfiguration) {
    this.hostConfiguration = hostConfiguration;
  }

  public final HttpConnectionManagerParams getHttpConnectionManagerParams() {
    return httpConnectionManagerParams;
  }

  public final void setHttpConnectionManagerParams(
      HttpConnectionManagerParams httpConnectionManagerParams) {
    this.httpConnectionManagerParams = httpConnectionManagerParams;
  }

  public final String getHostname() {
    return hostname;
  }

  public final void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public final int getPort() {
    return port;
  }

  public final void setPort(int port) {
    this.port = port;
  }

  public final String getDocumentRoot() {
    return documentRoot;
  }

  public final void setDocumentRoot(String documentRoot) {
    this.documentRoot = documentRoot;
  }

  public final String getDefaultUsername() {
    return defaultUsername;
  }

  public final void setDefaultUsername(String username) {
    this.defaultUsername = username;
  }

  public final String getDefaultPassword() {
    return defaultPassword;
  }

  public final void setDefaultPassword(String pwd) {
    this.defaultPassword = pwd;
  }

  public final Integer getDefaultConnectionTimeout() {
    return this.defaultConnectionTimeout;
  }

  public final void setDefaultConnectionTimeout(Integer cto) {
    this.defaultConnectionTimeout = cto;
  }

}
