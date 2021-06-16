package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.EjecucionSiglaException;
import com.egoveris.deo.model.model.HistorialReservaDTO;

import java.util.List;

public interface HistorialReservaService {

  public void guardarHistorialReserva(HistorialReservaDTO historialReserva);

  public List<HistorialReservaDTO> obtenerHistorialReservaPorUsuario(String usuario, String rectora,
      String documento);

  public List<HistorialReservaDTO> buscarDocumentoPorRectora(String documento, String reparticion);

  public List<HistorialReservaDTO> buscarDocumentoPorUsuario(String username, String documento);

  public List<HistorialReservaDTO> buscarDocumentoPorReparticion(String reparticion,
      String documento);

  public List<HistorialReservaDTO> buscarDocumentoPorSector(String sector, String documento,
      String reparticion);

  public List<HistorialReservaDTO> buscarTodas(String documento);

  public void actualizarReparticionHistorialVisualizacion(String reparticionOrigen,
      String reparticionDestino) throws EjecucionSiglaException;

  public void actualizarReparticionRectoraHistorialVisualizacion(String reparticionRectoraOrigen,
      String reparticionRectoraDestino) throws EjecucionSiglaException;

  public void actualizarSectorHistorialVisualizacion(String sectorOrigen, String sectorDestino,
      String reparticionOrigen, String reparticionDestino) throws EjecucionSiglaException;

}
