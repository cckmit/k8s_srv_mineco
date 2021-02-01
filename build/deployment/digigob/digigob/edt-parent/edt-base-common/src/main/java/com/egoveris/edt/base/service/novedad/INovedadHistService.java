package com.egoveris.edt.base.service.novedad;

import com.egoveris.edt.base.model.eu.novedad.NovedadDTO;
import com.egoveris.edt.base.model.eu.novedad.NovedadHistDTO;

import java.util.List;

/**
 * The Interface INovedadHistService.
 */
public interface INovedadHistService {

  /**
   * Gets the historial.
   *
   * @param id
   *          the id
   * @return the historial
   */
  List<NovedadHistDTO> getHistorial(Integer id);

  /**
   * Save.
   *
   * @param novedadHist
   *          the novedad hist
   */
  void save(NovedadHistDTO novedadHist);

  /**
   * Guardar historico.
   *
   * @param novedad
   *          the novedad
   * @param tipo
   *          the tipo
   */
  void guardarHistorico(NovedadDTO novedad, int tipo);
}
