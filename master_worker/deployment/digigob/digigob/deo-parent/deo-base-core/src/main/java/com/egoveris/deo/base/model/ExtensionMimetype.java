package com.egoveris.deo.base.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_EXTENSION_MIMETYPE")
public class ExtensionMimetype {

  @EmbeddedId
  private ExtensionMimetypePK extensionMimetypePK;

  public ExtensionMimetypePK getExtensionMimetypePK() {
    return extensionMimetypePK;
  }

  public void setExtensionMimetypePK(ExtensionMimetypePK extensionMimetypePK) {
    this.extensionMimetypePK = extensionMimetypePK;
  }
}