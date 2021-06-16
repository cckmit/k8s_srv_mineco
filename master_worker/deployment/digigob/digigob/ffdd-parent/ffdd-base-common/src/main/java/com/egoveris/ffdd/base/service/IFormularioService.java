package com.egoveris.ffdd.base.service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;

import java.util.List;

public interface IFormularioService {

  public FormularioDTO buscarFormularioPorNombre(String nombre) throws DynFormException;
  
  public FormularioWDDTO buscarFormularioPorNombreWD(String nombre) throws DynFormException;

  public List<FormularioDTO> obtenerTodosLosFormularios() throws DynFormException;

  /**
   * Busca y elimina el formulario
   * 
   * @param nombre
   *          name del formulario
   * @throws DynFormException
   */
  public void eliminarFormulario(String nombre) throws DynFormException;

  public List<ComponenteDTO> obtenerTodosLosComponentes() throws DynFormException;

  public ComponenteDTO buscarComponentePorNombre(String nombre) throws DynFormException;

  public ComponenteDTO buscarComponenteById(int idComponente) throws DynFormException;

  public String buscarMultilinea(Integer idComponente) throws DynFormException;

  public void eliminarMultilinea(int idComponente) throws DynFormException;

  public void guardarFormulario(FormularioDTO nombre) throws DynFormException;

  public void eliminarFormulariosTemporales() throws DynFormException;

  public void guardarGrupo(GrupoDTO grupo) throws DynFormException;

  public List<GrupoDTO> obtenerTodosLosGrupos() throws DynFormException;

  public void eliminarCaja(String nombre) throws DynFormException;

  public GrupoDTO buscarGrupoPorNombre(String nombre) throws DynFormException;

  public void modificarGrupo(GrupoDTO grupo) throws DynFormException;

  public boolean verificarUsoComponente(String id) throws DynFormException;

  public List<FormularioComponenteDTO> buscarFormComponentPorFormulario(int idFormulario)
      throws DynFormException;

  public List<FormularioDTO> obtenerTodosLosFormulariosSinRelaciones() throws DynFormException;

  public void guardarComponenteMultilinea(FormularioDTO form) throws DynFormException;

}
