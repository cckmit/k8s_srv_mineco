package com.egoveris.shareddocument.base.service;

import com.egoveris.shareddocument.base.model.FileAsStreamConnectionWebDav;
import com.egoveris.shareddocument.base.model.WebDAVResourceBean;

import java.io.InputStream;
import java.util.List;

public interface IWebDavService {

  List<WebDAVResourceBean> listSpace(String relativeUri, String username, String pwd);

  void copyFile(String relativeUriOri, String relativeUriDest, String filenameOri,
      String filenameDest, String username, String pwd);

  void moveFile(String relativeUriOri, String relativeUriDest, String filenameOri,
      String filenameDest, String username, String pwd);

  void moveFolder(String relativeUriOri, String relativeUriDest, String username, String pwd);

  void createSpace(String relativeUri, String nombreSpace, String username, String pwd);

  void deleteFile(String relativeUri, String spaceName, String username, String pwd);

  void deleteSpace(String relativeUri, String spaceName, String username, String pwd);

  public void createFile(String relativeUri, String filename, String username, String pwd,
      InputStream content);

  public WebDAVResourceBean getFile(String relativeUri, String username, String pwd);

  public void checkoutResource(String relativeURI, String resource, String username,
      String password);

  /**
   * Consulta el tama�o de un archivo
   * 
   * @param relativeURI
   * @param username
   * @param password
   * @return
   */
  public int getFileSize(String relativeURI, String username, String password);

  /**
   * Consulta si existe una archivo o un directorio
   * 
   * @param relativeURI
   * @param username
   * @param password
   * @return
   */
  public boolean existFile(String relativeURI, String username, String password);

  public FileAsStreamConnectionWebDav getFileAsStream(String relativeUri, String username,
      String pwd);
  //
  // boolean versionControl(String keyRepoOrigen, String codigoExpedienteSADE,
  // String username, String pwd);
  //
  // // TODO Este metodo no esta funcionando porque no esta terminado,
  // // cuando este lista la implementacion sera actualizado
  // String lockFile(String fileName, String codigoExpedienteSADE, String key,
  // String user, String password);
  //
  //
  // // TODO Este metodo no esta funcionando porque no esta terminado,
  // // cuando este lista la implementacion sera actualizado
  // boolean unLockFile(String fileName, String codigoExpedienteSADE, String
  // token,String key, String user, String password);
  //
  // DocumentoAlfrescoBean obtenerDocumentoTrabajo(String uri, String
  // username, String pwd);
  //
  // DocumentoAlfrescoBean obtenerActuacion(String uri, String username,
  // String pwd);

  // public DocumentoAlfrescoBean obtenerDocumentoAlfresco(String key,String
  // subDirectory,String fileName, String username, String pwd);
  //
  // public void anotherCopyFile(String repositorioOrigen,String
  // repositorioDestino,String subdirectorioOrigen,String subdirectorioDestino
  // ,String nombreArchivo);
}
