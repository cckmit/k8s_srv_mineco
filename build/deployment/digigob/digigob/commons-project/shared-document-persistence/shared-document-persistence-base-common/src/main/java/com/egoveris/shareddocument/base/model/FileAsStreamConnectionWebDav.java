package com.egoveris.shareddocument.base.model;

import java.io.BufferedInputStream;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.jackrabbit.webdav.client.methods.PropFindMethod;
import org.terasoluna.plus.common.exception.ApplicationException;

public class FileAsStreamConnectionWebDav extends FileAsStreamConnection {

  public FileAsStreamConnectionWebDav(BufferedInputStream fileAsStream, GetMethod method,
      PropFindMethod propMethod) {
    super(fileAsStream);
    this.method = method;
    this.propMethod = propMethod;
  }

  GetMethod method = null;
  PropFindMethod propMethod = null;

  public FileAsStreamConnectionWebDav(BufferedInputStream fileAsStream) {
    super(fileAsStream);
  }

  @Override
  public void closeConnection() {
    if (this.getFileAsStream() != null) {
      try {
        this.getFileAsStream().close();
      } catch (Exception e) {
        throw new ApplicationException(e.getMessage()) {
        };
      }
    }
    if (method != null) {
      method.releaseConnection();
    }
    if (propMethod != null) {
      propMethod.releaseConnection();
    }

  }

}
