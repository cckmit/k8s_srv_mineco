package com.egoveris.deo.base.service;

import com.egoveris.deo.model.model.FamiliaTipoDocumentoDTO;

import java.util.List;

public interface FamiliaTipoDocumentoService {

  public void guardarFamilia(List<FamiliaTipoDocumentoDTO> listaFamiliaAgregar,
      List<FamiliaTipoDocumentoDTO> listaFamiliasEliminar, FamiliaTipoDocumentoDTO familiaGenerica,
      String usuario);

  public void updateFamilia(List<FamiliaTipoDocumentoDTO> listaFamiliaModificar,
      FamiliaTipoDocumentoDTO familiaGenerica, String usuario);

  public void updateFamilia(FamiliaTipoDocumentoDTO familiaTipoDocumento, String usuario);

  public List<FamiliaTipoDocumentoDTO> traerfamilias();

  public List<String> traerNombresFamilias();

  public List<String> traerNombresFamiliasByIdTipoDocumento(Integer idTipoDocumento);

  public FamiliaTipoDocumentoDTO traerFamiliaTipoDocumentoByNombre(String nombreFamilia);
  
  public FamiliaTipoDocumentoDTO traerFamiliaTipoDocumentoById(String idTiopoDoc);

}