package com.egoveris.ffdd.base.service;

import java.util.List;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;

public interface IComponenteService {

  /**
   * Save a component and return it persisted.
   * 
   * @param Component
   * @return Component
   * @throws DynFormException
   */
  public ComponenteDTO guardarComponente(ComponenteDTO componente) throws DynFormException;

  /**
   * Find all components by type combobox.
   * 
   * @return list
   * @throws DynFormException
   */
  public List<ComponenteDTO> obtenerTodosLosCombobox() throws DynFormException;

  /**
   * Find all components by type bandbox.
   * 
   * @return list
   * @throws DynFormException
   */
  public List<ComponenteDTO> obtenerTodosLosBandBox() throws DynFormException;

  /**
   * Find all components that have multiple items.
   * 
   * @return list
   * @throws DynFormException
   */
  public List<ComponenteDTO> obtenerTodosLosComponentesMultivalores() throws DynFormException;

  /**
   * Find a component by name.
   * 
   * @param name
   * @return Component
   * @throws DynFormException
   */
  public ComponenteDTO buscarComponentePorNombre(String nombre) throws DynFormException;

  /**
   * Delete an specific component
   * 
   * @param Component
   * @throws DynFormException
   */
  public void eliminarComponente(ComponenteDTO comp) throws DynFormException;

  /**
   * Find all component that are of type textbox and longbox.
   * 
   * @return list
   * @throws DynFormException
   */
  public List<ComponenteDTO> obtenerComponentesABMLongBoxYTextBox() throws DynFormException;

	/**
	 * Save an specific component.
	 * 
	 * @param componente
	 * @throws DynFormException
	 */
  public void guardarComponenteDTO(ComponenteDTO componente) throws DynFormException;

  /**
   * Delete a component by name.
   * 
   * @param nombre
   * @throws DynFormException
   */
  public void eliminarComponente(String nombre) throws DynFormException;

	/**
	 * 
	 * @return
	 */
	public List<String> findDistinctNombreXml();

	/**
	 * 
	 * @return
	 */
	public List<ComponenteDTO> findComplexComponents();

	/**
	 * 
	 * @param xmlFile
	 * @return
	 */
	public List<String> findComponentsByXml(String xmlFile);

	/**
	 * 
	 * @param nombre
	 */
	public Boolean findIfComponentIsUsed(String nombre);

	/**
	 * 
	 * @param factory
	 * @return
	 */
	public List<String> findComponentsByFactory(String factory);

}