package com.egoveris.te.base.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.DatoPropio;
import com.egoveris.te.base.model.DatosVariablesComboGruposDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.TipoDatoPropioDTO;
import com.egoveris.te.base.model.TrataDTO;

/**
 * Operaciones asociadas a una trata del expediente.
 * 
 * @author rgalloci
 *
 */
public interface TrataService {
	
	/**
	 * Buscar tratas by estado.
	 *
	 * @param activo the activo
	 * @return the list
	 */
	public List<TrataDTO> buscarTratasByEstado(String estado);

	/**
	 * Buscar totalidad tratas EE.
	 *
	 * @return the list
	 */
	public List<TrataDTO> buscarTotalidadTratasEE();
	
	/**
	 * Eliminar trata.
	 *
	 * @param trata the trata
	 * @param userName the user name
	 */
	public void eliminarTrata(TrataDTO trata, String userName);
	
	/**
	 * Modificar trata.
	 *
	 * @param trata the trata
	 * @param userName the user name
	 */
	public void modificarTrata(TrataDTO trata,String userName);
	
	/**
	 * Buscar datos variables por trata.
	 *
	 * @param trata the trata
	 * @return the list
	 */
	public List<MetadataDTO> buscarDatosVariablesPorTrata(TrataDTO trata);
	
	/**
	 * Guardar datos variables de trata.
	 *
	 * @param trata the trata
	 */
	public void guardarDatosVariablesDeTrata(TrataDTO trata);
	
	/**
	 * Buscar trata by codigo.
	 *
	 * @param codigoTrata the codigo trata
	 * @return the trata
	 */
	public TrataDTO buscarTrataByCodigo(String codigoTrata);

	/**
	 * Dar alta trata.
	 *
	 * @param trataSade the trata sade
	 * @param userName the user name
	 */
	public void darAltaTrata(TrataDTO trataSade, String userName);
	
	/**
	 * Es trata utilizada en EE.
	 *
	 * @param trataSade the trata sade
	 * @return true, if successful
	 */
	public Boolean esTrataUtilizadaEnEE(String trataSade);
	
	/**
	 * Obtener lista todas las tratas.
	 *
	 * @return the list
	 */
	public List<TrataDTO> obtenerListaTodasLasTratas();
	
	/**
	 * Obtener trata by id trata sugerida.
	 *
	 * @param idTrataSugerida the id trata sugerida
	 * @return the trata
	 */
	public TrataDTO obtenerTrataByIdTrataSugerida(Long idTrataSugerida);
	
	/**
	 * Obtener descripcion trata by codigo.
	 *
	 * @param codigoTrata the codigo trata
	 * @return the string
	 */
	public String obtenerDescripcionTrataByCodigo(String codigoTrata);
	
	/**
	 * Formato to string trata.
	 *
	 * @param codigo the codigo
	 * @param descrip the descrip
	 * @return the string
	 */
	public String formatoToStringTrata(String codigo, String descrip);
	
	
	/**
	 * Buscar tratas manuales.
	 *
	 * @param manual the manual
	 * @return the list
	 */
	public List<TrataDTO> buscarTratasManuales(Boolean manual);
	
	/**
	 * Buscar tipos datos propios.
	 *
	 * @return the list
	 */
	public List<TipoDatoPropioDTO> buscarTiposDatosPropios();
	 
	
	/**
	 * Buscar datos combo.
	 *
	 * @param nombre the nombre
	 * @return the list
	 */
	public List<DatoPropio> buscarDatosCombo(String nombre);
	
	/**
	 * Buscar combo grupos por tipo.
	 *
	 * @param trata the trata
	 * @return the list
	 */
	public List<DatosVariablesComboGruposDTO> buscarComboGruposPorTipo(TrataDTO trata);

	/**
	 * Actualizar reparticion caratuladora Y rectora.
	 *
	 * @param codigoReparticionViejo the codigo reparticion viejo
	 * @param codigoReparticion the codigo reparticion
	 */
	@Transactional
	public void actualizarReparticionCaratuladoraYRectora(String codigoReparticionViejo,String codigoReparticion);
	

	/**
	 * Obtener todas las tratas con descripcion.
	 *
	 * @return the list
	 */
	public List<TrataDTO> obtenerTodasLasTratasConDescripcion();
	
	/**
	 * Buscar tratas by tipo caratulacion.
	 *
	 * @param esInterno the es interno
	 * @param esExterno the es externo
	 * @return the list
	 */
	public List<TrataDTO> buscarTratasByTipoCaratulacion(Boolean esInterno, Boolean esExterno);
	
	/**
	 * Buscar tratas manuales Y tipo caratulacion.
	 *
	 * @param manual the manual
	 * @param esInterno the es interno
	 * @return the list
	 */
	public List<TrataDTO> buscarTratasManualesYTipoCaratulacion(Boolean manual, Boolean esInterno);

	/**
	 * Buscar tratas by estado Y tipo caratulacion.
	 *
	 * @param estado the estado
	 * @param esInterno the es interno
	 * @return the list
	 */
	public List<TrataDTO> buscarTratasByEstadoYTipoCaratulacion(String estado, Boolean esInterno);

	/**
	 * Buscar tratapor id.
	 *
	 * @param idTrata the id trata
	 * @return the trata
	 */
	public TrataDTO buscarTrataporId(Long idTrata);

	/**
	 * Reparticion usuario tiene permiso de reserva.
	 *
	 * @param codigoReparticion the codigo reparticion
	 * @return true, if successful
	 */
	public boolean reparticionUsuarioTienePermisoDeReserva(
			String codigoReparticion);

	/**
	 * Busca flag para crear tramites 1 es manual(new) y 0 es desde base de datos(old).
	 *
	 * @return the int
	 */
	public int buscarFlagCreacionTramite();
	
	/**
	 * Busca codigo extrato.
	 *
	 * @return String
	 */
	public String obtenerDescripcionTrataSADE(String codigoExtracto);
	
	/**
	 * Verifica si un tipo de resultado esta en uso
	 * por una Trata
	 * 
	 * @param propertyTipoResultado
	 * @return
	 */
	public boolean isTipoResultadoEnUso(String propertyTipoResultado);
	
	/**
   * Searchs all Trata with tramitation type "Subprocess" or "Both"
   *
   * @return the list
   */
  public List<TrataDTO> buscarTratasEEByTipoSubproceso();
  
  /**
   * Searchs all Trata with tramitation type "Expedient" or "Both".
   *
   * @return the list
   */
  public List<TrataDTO> buscarTratasByTipoExpediente(boolean manual);
}
