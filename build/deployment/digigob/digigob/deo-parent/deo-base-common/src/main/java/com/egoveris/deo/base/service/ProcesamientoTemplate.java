package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.TemplateMalArmadoException;
import com.egoveris.deo.base.exception.ValoresIncorrectosException;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.FormularioDTO;

import java.util.Map;

import org.terasoluna.plus.common.exception.ApplicationException;

public interface ProcesamientoTemplate {

  public byte[] armarDocumentoTemplate(TipoDocumentoDTO tipoDocumento,
      Map<String, Object> camposTemplate) throws ApplicationException;

  public byte[] armarDocumentoTemplate(TipoDocumentoTemplateDTO tipoDocumentoTemplate,
      Map<String, Object> camposTemplate) throws ApplicationException;

  public byte[] procesarTemplate(byte[] template, Map<String, Object> valores)
      throws ApplicationException;

  public byte[] procesarTemplate(byte[] template, Map<String, Object> valores, FormularioDTO form)
      throws ApplicationException;

  public TipoDocumentoTemplateDTO obtenerUltimoTemplatePorTipoDocumento(
      TipoDocumentoDTO tipoDocumento);

  public void validarCamposTemplatePorTipoDocumentoYMap(TipoDocumentoDTO tipoDocumento,
      Map<String, Object> camposFormulario) throws TemplateMalArmadoException, ValoresIncorrectosException;

  public void validarCamposTemplatePorArrayByteYMap(byte[] template,
      Map<String, Object> camposFormulario) throws TemplateMalArmadoException, ValoresIncorrectosException ;

  /**
   * Obtiene los campos de la transacción del formulario FC en un mapa
   * 
   * @param idTransaccion
   * @return
   * @throws DynFormException
   */
  public Map<String, Object> obtenerCamposTemplate(Integer idTransaccion) throws DynFormException;

}
