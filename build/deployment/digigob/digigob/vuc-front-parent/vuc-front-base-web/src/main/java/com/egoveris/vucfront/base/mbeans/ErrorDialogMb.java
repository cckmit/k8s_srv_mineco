package com.egoveris.vucfront.base.mbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class ErrorDialogMb implements Serializable {

  private static final long serialVersionUID = 1616252184704127083L;

  public void closeDialog() {
    RequestContext.getCurrentInstance().closeDialog(null);
  }
}