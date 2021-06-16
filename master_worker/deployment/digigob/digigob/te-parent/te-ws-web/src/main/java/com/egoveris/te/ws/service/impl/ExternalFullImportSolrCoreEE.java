package com.egoveris.te.ws.service.impl;

import com.egoveris.te.ws.service.IFullImportSolr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalFullImportSolrCoreEE implements IFullImportSolr {

  @Autowired
  private com.egoveris.te.base.service.iface.IFullImportSolr baseService;

  @Override
  public void realizarFullImportSolrCoreEE() {
    baseService.realizarFullImportSolrCoreEE();
  }
}