/**
 *
 */
package com.egoveris.plugins.manager.deployers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.plugins.manager.model.FileMetadata;
import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.shareddocument.base.service.impl.WebDavApacheServiceImpl;

/**
 * @author difarias
 */
public class WebDavDeployer extends GenericDeployer {
  private final static Logger logger = LoggerFactory.getLogger(WebDavDeployer.class);

  private URL tempDir;

  private String directoryDestiny;
  private HttpConnectionManagerParams httpConnectionManagerParams;
  private String hostname;
  private int port;
  private String documentRoot;
  private String defaultUsername;
  private String defaultPassword;
  private String defaultRealm;
  private Integer defaultConnectionTimeout = new Integer(5000); // 5 seconds
  // by
  // default
  private HostConfiguration hostConfiguration;

  private List<String> lastDeployed;

  private IWebDavService webDavService = null;

  /**
   * @return the httpConnectionManagerParams
   */
  public HttpConnectionManagerParams getHttpConnectionManagerParams() {
    if (httpConnectionManagerParams == null) {
      httpConnectionManagerParams = new org.apache.commons.httpclient.params.HttpConnectionManagerParams();
    }
    return httpConnectionManagerParams;
  }

  /**
   * @param httpConnectionManagerParams
   *          the httpConnectionManagerParams to set
   */
  public void setHttpConnectionManagerParams(
      final HttpConnectionManagerParams httpConnectionManagerParams) {
    this.httpConnectionManagerParams = httpConnectionManagerParams;
  }

  /**
   * @return the hostConfiguration
   */
  public HostConfiguration getHostConfiguration() {
    if (hostConfiguration == null) {
      hostConfiguration = new org.apache.commons.httpclient.HostConfiguration();
    }
    return hostConfiguration;
  }

  /**
   * @param hostConfiguration
   *          the hostConfiguration to set
   */
  public void setHostConfiguration(final HostConfiguration hostConfiguration) {
    this.hostConfiguration = hostConfiguration;
  }

  /**
   * @return the hostname
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * @param hostname
   *          the hostname to set
   */
  public void setHostname(final String hostname) {
    this.hostname = hostname;
  }

  /**
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * @param port
   *          the port to set
   */
  public void setPort(final int port) {
    this.port = port;
  }

  /**
   * @return the documentRoot
   */
  public String getDocumentRoot() {
    return documentRoot;
  }

  /**
   * @param documentRoot
   *          the documentRoot to set
   */
  public void setDocumentRoot(final String documentRoot) {
    this.documentRoot = documentRoot;
  }

  /**
   * @return the defaultUsername
   */
  public String getDefaultUsername() {
    return defaultUsername;
  }

  /**
   * @param defaultUsername
   *          the defaultUsername to set
   */
  public void setDefaultUsername(final String defaultUsername) {
    this.defaultUsername = defaultUsername;
  }

  /**
   * @return the defaultPassword
   */
  public String getDefaultPassword() {
    return defaultPassword;
  }

  /**
   * @param defaultPassword
   *          the defaultPassword to set
   */
  public void setDefaultPassword(final String defaultPassword) {
    this.defaultPassword = defaultPassword;
  }

  /**
   * @return the defaultRealm
   */
  public String getDefaultRealm() {
    return defaultRealm;
  }

  /**
   * @param defaultRealm
   *          the defaultRealm to set
   */
  public void setDefaultRealm(final String defaultRealm) {
    this.defaultRealm = defaultRealm;
  }

  /**
   * @return the defaultConnectionTimeout
   */
  public Integer getDefaultConnectionTimeout() {
    return defaultConnectionTimeout;
  }

  /**
   * @param defaultConnectionTimeout
   *          the defaultConnectionTimeout to set
   */
  public void setDefaultConnectionTimeout(final Integer defaultConnectionTimeout) {
    this.defaultConnectionTimeout = defaultConnectionTimeout;
  }

  /**
   * @return the lastDeployed
   */
  public List<String> getLastDeployed() {
    if (lastDeployed == null) {
      lastDeployed = new ArrayList<>();
    }
    return lastDeployed;
  }

  /**
   * @param lastDeployed
   *          the lastDeployed to set
   */
  public void setLastDeployed(final List<String> lastDeployed) {
    this.lastDeployed = lastDeployed;
  }

  /**
   * @return the tempDir
   */
  public URL getTempDir() {
    if (tempDir == null) {
      try {
        final File aux = new File(
            "/" + System.getProperty("java.io.tmpdir") + File.separator + "webdav");
        // if (tempDir==null) tempDir=new URL("/tmp/webdav");
        tempDir = aux.toURI().toURL();
      } catch (final MalformedURLException e) {
        logger.error("Malfofrmer URL " + "/" + System.getProperty("java.io.tmpdir")
            + File.separator + "webdav", e);
      }

      if (tempDir == null) {
        try {
          tempDir = new URL("file:///tmp/webdav");
        } catch (final MalformedURLException e) {
          logger.error("error in file:///tmp/webdav", e);
        }
      }
    }
    return tempDir;
  }

  private void removeTempDir() {
    try {
      FileUtils.forceDelete(new File(getTempDir().getPath()));
    } catch (final IOException e) {
      logger.error("Error removing dir", e);
    }
  }

  private void createTempDir() {
    try {
      removeTempDir();
      FileUtils.forceMkdir(new File(getTempDir().getPath()));
    } catch (final IOException e) {
      logger.error("Error creating dir", e);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see com.egoveris.plugins.manager.deployers.GenericDeployer#stop()
   */
  @Override
  public void stop() {
    removeTempDir();
    super.stop();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.egoveris.plugins.manager.deployers.GenericDeployer#start()
   */
  @Override
  public void start() {
    createTempDir();
    super.start();
  }

  /**
   * @return the webDavService
   */
  public IWebDavService getWebDavService() {
    if (webDavService == null) {
      final WebDavApacheServiceImpl webDavApacheService = new WebDavApacheServiceImpl();

      webDavApacheService.setHostConfiguration(getHostConfiguration());
      webDavApacheService.setHttpConnectionManagerParams(getHttpConnectionManagerParams());
      webDavApacheService.setHostname(getHostname());
      webDavApacheService.setPort(getPort());
      webDavApacheService.setDocumentRoot(getDocumentRoot());
      webDavApacheService.setDefaultUsername(getDefaultPassword());
      webDavApacheService.setDefaultPassword(getDefaultPassword());
      webDavApacheService.setDefaultConnectionTimeout(getDefaultConnectionTimeout());
      webDavService = webDavApacheService;
    }
    return webDavService;
  }

  // ----------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   *
   * @see com.egoveris.plugins.manager.deployers.GenericDeployer#explore()
   */
  @Override
  public void explore() throws IOException {
    try {
      fileExplore();
    } catch (final Exception e) {
      logger.error("error explore", e);
      String msg = e.getMessage();
      if (e.getCause() != null) {
        msg = e.getCause().toString();
      }
      logger.error(String.format("ERROR : %s", msg));
    }
  }

  /**
   * @return the directoryDestiny
   */
  public String getDirectoryDestiny() {
    return directoryDestiny;
  }

  /**
   * @param directoryDestiny
   *          the directoryDestiny to set
   */
  public void setDirectoryDestiny(final String directoryDestiny) {
    this.directoryDestiny = directoryDestiny;
  }

  public boolean isNewFileOrModified(final String filename){
    boolean result;
    FileMetadata file;
    try{
	    if (getFilesMap().containsKey(filename)) {
	      file = getFilesMap().get(filename);
	      final FileMetadata toProve = new FileMetadata(new File(filename));
	      result = !file.isEqual(toProve); // si es diferente
	      if (result) {
	        getFilesMap().put(filename, toProve);
	      }
	    } else { // not in cache
	    	file = new FileMetadata(filename);
	        getFilesMap().put(filename, file);
	        result = true;
	    }
      } catch (IOException e ) {
        logger.debug("error isNewFileOrModified - file", e);
        result = true;
      }
    
    return result;
  }

  private List<String> checkRemoved(final List<String> lastDeployed,
      final List<String> pluginJar) {
    final List<String> result = new ArrayList<>();
    // --- normalize names ---
    if (pluginJar != null && !pluginJar.isEmpty()) {
      for (String aux : lastDeployed) {
        boolean toRemove = true;
        aux = FilenameUtils.getName(aux);
        for (String newDeploy : pluginJar) {
          newDeploy = FilenameUtils.getName(newDeploy);
          if (aux.equals(newDeploy)) {
            toRemove = false;
          }
        }

        if (toRemove) {
          result.add(aux);
        }
      }
    } else {
      result.addAll(pluginJar);
    }

    return result;
  }

  /**
   * Exploración del directorio WEBDAV
   *
   * @throws IOException
   */
  public void fileExplore() throws IOException {
    createTempDir();

    final List<WebDAVResourceBean> lstFiles = getWebDavService().listSpace(getUrlToMonitoring(),
        getDefaultUsername(), getDefaultPassword());
    final URL urlDestino = new URL(getDirectoryDestiny());

    final List<File> toCopyLst = new ArrayList<>();
    final List<String> filesAvailables = new ArrayList<>();

    if (lstFiles != null && !lstFiles.isEmpty()) {
      for (final WebDAVResourceBean resource : lstFiles) {
        if (!resource.isDirectorio()) {
          logger.debug("-> " + resource.getNombre() + " - " + resource.getFechaModificacion());
          logger.debug("---> " + getTempDir().getPath());

          final String filename = getTempDir().getPath() + "/" + resource.getNombre();
          filesAvailables.add(filename);

          if (isNewFileOrModified(filename)) {
            final File outputFile = new File(filename);

            final FileOutputStream fos = new FileOutputStream(outputFile);
            FileAsStreamConnectionWebDav fascw = null;
            final String toDownload = getUrlToMonitoring() + "/" + resource.getNombre();
            try {
              fascw = getWebDavService().getFileAsStream(toDownload, getDefaultUsername(),
                  getDefaultPassword());
              IOUtils.copy(fascw.getFileAsStream(), fos);
              fos.close();

              outputFile.setLastModified(resource.getFechaModificacion().getTime());
              toCopyLst.add(outputFile);
            } catch (final Exception e) {
              logger.info(String.format("Error accessing resource: %s", toDownload), e);
            } finally {
              closeFile(fos);
            }
          }
        }
      }
      final File dirDest = new File(urlDestino.getPath());

      final List<String> lstToRemove = checkRemoved(getLastDeployed(), filesAvailables);
      File fileToDelete = null;
      for (final String fileToRemove : lstToRemove) {
        fileToDelete = new File(dirDest + File.separator + fileToRemove);
        logger.info("--> removing: " + fileToDelete.getName());
        FileUtils.forceDelete(fileToDelete);
      }

      getLastDeployed().clear();
      for (final File fileToCopy : toCopyLst) {
        getLastDeployed().add(FilenameUtils.getName(fileToCopy.getName()));
        logger.debug("## ---> " + fileToCopy.getAbsolutePath());
        FileUtils.copyFileToDirectory(fileToCopy, dirDest, true);
      }
    }
  }

  private void closeFile(FileOutputStream fos) {
    try {
      fos.close();
    } catch (Exception e) {
      logger.error("error al cerrar el archivo", e);
    }
  }

}
