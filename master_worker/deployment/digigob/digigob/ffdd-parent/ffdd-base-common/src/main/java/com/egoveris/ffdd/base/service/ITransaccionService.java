package com.egoveris.ffdd.base.service;

import com.egoveris.ffdd.base.model.complex.ComplexComponentDTO;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.DynFormValidarTransaccionException;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.TransaccionValidaDTO;

import java.util.List;

public interface ITransaccionService {

  public TransaccionDTO buscarTransaccionPorUUID(Integer uuid) throws DynFormException;

  public void deleteFormWebExpt(TransaccionDTO trans) throws DynFormException;

  public boolean existeTransaccionParaFormulario(String formulario) throws DynFormException;

  /**
   * Return the TradeComponents in the Transaction identified by idTransaccion.
   * All fields in the transaction that dont' match a trade component are
   * ignored.
   * 
   * @param idTransaccion
   * @return Null if no transaction exists with that id. Otherwise, return a
   *         list of concrete tradecomponents of the given transaction.
   * @throws DynFormException
   */
  public List<ComplexComponentDTO> getComplexComponents(Integer idTransaccion)
      throws DynFormException;

  public Integer grabarTransaccion(TransaccionDTO trans) throws DynFormException;

  public void updateFormWebExpt(TransaccionDTO trans) throws DynFormException;

  /**
   * Checks all form constrains and stores the data in a new transaction.
   * 
   * @param validarDTO
   *          Form values
   * @return Transaction id.
   * @throws DynFormValidarTransaccionException
   *           If constraint is not met.
   */
  Integer grabarTransaccionValida(TransaccionValidaDTO validarDTO)
      throws DynFormValidarTransaccionException;

}
