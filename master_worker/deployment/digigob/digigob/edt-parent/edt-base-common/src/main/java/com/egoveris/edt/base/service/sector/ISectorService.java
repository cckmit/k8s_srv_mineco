package com.egoveris.edt.base.service.sector;

import com.egoveris.sharedsecurity.base.model.EstructuraDTO;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;

import java.util.List;

/**
 * The Interface ISectorService.
 */
public interface ISectorService {

  /**
   * Buscar sectores por reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  List<SectorDTO> buscarSectoresPorReparticion(ReparticionDTO reparticion);

  /**
   * Buscar sectores mesa por reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  List<SectorDTO> buscarSectoresMesaPorReparticion(ReparticionDTO reparticion);

  /**
   * Buscar sectores mesa virtual por reparticion.
   *
   * @param reparticion
   *          the reparticion
   * @return the list
   */
  List<SectorDTO> buscarSectoresMesaVirtualPorReparticion(ReparticionDTO reparticion);

  /**
   * Buscar sectores por codigo de sector.
   *
   * @param value
   *          the value
   * @return the list
   */
  List<SectorDTO> buscarSectoresPorCodigoDeSector(String value);

  /**
   * Buscar sector por repa Y sector.
   *
   * @param sectorCodigo
   *          the sector codigo
   * @param reparticion
   *          the reparticion
   * @return the sector DTO
   */
  SectorDTO buscarSectorPorRepaYSector(String sectorCodigo, ReparticionDTO reparticion);

  /**
   * Buscar sectores por descripcion.
   *
   * @param sectorDescripcion
   *          the sector descripcion
   * @return the list
   */
  List<SectorDTO> buscarSectoresPorDescripcion(String sectorDescripcion);

  /**
   * Lista sectores.
   *
   * @return the list
   */
  List<SectorDTO> listaSectores();

  /**
   * Gets the sectorby id.
   *
   * @param id
   *          the id
   * @return the sectorby id
   */
  SectorDTO getSectorbyId(Integer id);

  /**
   * Guardar sector.
   *
   * @param sector
   *          the sector
   */
  void guardarSector(SectorDTO sector);

  /**
   * Modificar sector.
   *
   * @param sector
   *          the sector
   */
  void modificarSector(SectorDTO sector);

  /**
   * Es sector activo.
   *
   * @param sector
   *          the sector
   * @return true, if successful
   */
  boolean esSectorActivo(SectorDTO sector);

  /**
   * Crear sector interno.
   *
   * @param reparticion
   *          the reparticion
   * @param estructuras
   *          the estructuras
   */
  void crearSectorInterno(ReparticionDTO reparticion, List<EstructuraDTO> estructuras);

  /**
   * Buscar sectores por reparticiones.
   *
   * @param reparticiones
   *          the reparticiones
   * @return the list
   */
  public List<SectorDTO> buscarSectoresPorReparticiones(List<ReparticionDTO> reparticiones);

  /**
   * Buscar sectores por codigo de sector comodin.
   *
   * @param codigoSector
   *          the codigo sector
   * @return the list
   */
  public List<SectorDTO> buscarSectoresPorCodigoDeSectorComodin(String codigoSector);

}