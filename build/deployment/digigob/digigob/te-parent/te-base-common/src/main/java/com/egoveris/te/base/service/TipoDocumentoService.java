package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;


/**
 * The Interface TipoDocumentoService.
 */
public interface TipoDocumentoService {
	
	/**
	 * Obtiene todos los tipos de documento electronicos que 
	 * pueden usarse en esta aplicacion.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumento();
	
	/**
	 * Obtiene de una cache (map) el tipo de documento por el acronimo del mismo.
	 *
	 * @param acronimo the acronimo
	 * @return the tipo documento
	 */
	public TipoDocumentoDTO obtenerTipoDocumento(String acronimo);
	
	/**
	 * Obtener tipos documento gedo.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedo();
	
	/**
	 * Guardar.
	 *
	 * @param tipoDocumento the tipo documento
	 */
	public void guardar(TipoDocumentoDTO tipoDocumento);
	
	/**
	 * Buscar tipo documento by uso.
	 *
	 * @param uso the uso
	 * @return the tipo documento
	 */
	public TipoDocumentoDTO buscarTipoDocumentoByUso(String uso);
	
	/**
	 * Obtener tipos documento GEDO.
	 *
	 * @param tipoDocGedo the tipo doc gedo
	 * @return the tipo documento
	 */
	public TipoDocumentoDTO obtenerTiposDocumentoGEDO(Long tipoDocGedo);
	
	/**
	 * Obtener tipos documento GEDO especial.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOEspecial();

	/**
	 * Tipo documento generacion.
	 *
	 * @param tipoDocGenerado the tipo doc generado
	 * @return the string
	 */
	public String tipoDocumentoGeneracion(Long tipoDocGenerado);
	
	/**
	 * Obtener tipos documento GEDO habilitados.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOHabilitados();
	
	/**
	 * Cargar auditoria.
	 *
	 * @param trataTipoDocumento the trata tipo documento
	 * @param estado the estado
	 * @param usuario the usuario
	 */
	public void cargarAuditoria(TrataTipoDocumentoDTO trataTipoDocumento ,String estado,String  usuario);
	
	/**
	 * Obtener tipos documento GEDO por familia.
	 *
	 * @param nombreFamilia the nombre familia
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOPorFamilia(String nombreFamilia);
	
	
	/**
	 * Obtener tipos documento gedo.
	 *
	 * @param estado the estado
	 * @param esManual the es manual
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedo(String estado, boolean esManual);
	
	/**
	 * Obtener notificables.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerNotificables();
	
	/**
	 * Obtener todos documentos gedo.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTodosDocumentosGedo();
	
	/**
	 * Buscar tipo documento by estado filtrados manual.
	 *
	 * @param estado the estado
	 * @param esManual the es manual
	 * @return the list
	 */
	public List<TipoDocumentoDTO> buscarTipoDocumentoByEstadoFiltradosManual(String estado, boolean esManual);
	
	/**
	 * Consultar tipo documento por acronimo.
	 *
	 * @param acronimo the acronimo
	 * @return the tipo documento
	 */
	public TipoDocumentoDTO consultarTipoDocumentoPorAcronimo(String acronimo);
	
	/**
	 * Obtiene todos los documentos de tipo template activos en DEO.
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTemplates();
	
	/**
	 * Buscar trata tipo documento por trata.
	 *
	 * @param trata the trata
	 * @return the list
	 */
	public List<TrataTipoDocumentoDTO> buscarTrataTipoDocumentoPorTrata(TrataDTO trata);

	/**
	 * Actualizar trata tipo documentos.
	 *
	 * @param ttdModificados the ttd modificados
	 * @param usuario the usuario
	 */
	public void actualizarTrataTipoDocumentos(List<TrataTipoDocumentoDTO> ttdModificados, String usuario);

	
	public List<TipoDocumentoDTO> obtnerTiposDocumentoGEDOHabilitadosFamilia();
	
	/**
	 * Obtener tipos documento gedo (Importado)
	 *
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGedoImportados();
	
}
