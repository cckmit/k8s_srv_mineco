package com.egoveris.te.base.repository;

import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TipoDocumentoGedoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;

import java.io.Serializable;
import java.util.List;

public interface TipoDocumentoRepository {
  public TipoDocumentoDTO buscarTipoDocumentoByUso(String uso);
  
  
  

	public List<TipoDocumentoDTO> buscarTipoDocumentoByEstado(String estado);
	public TipoDocumentoDTO buscarTipoDocumentoByAcronimo(String acronimo);
	public List<TipoDocumentoDTO> buscarTipoDocumentoByTieneTemplate(Boolean tieneTemplate);
	public List<TipoDocumentoDTO> buscarTipoDocumentoEspecialByEstado(Boolean esEspecial);
	public List<TipoDocumentoDTO> buscarTipoDocumento();
	public List<TipoDocumentoDTO> buscarTipoDocumentoByTieneTemplateEstado(Boolean tieneTemplate, String estado);
	
	
	public void guardar(Serializable obj);
	public String buscarTipoDocumentoGenerador(Integer tipoDocGenerado);
	public void eliminar(TrataTipoDocumentoDTO trataTipoDocumento);
	public List<TrataTipoDocumentoDTO> buscarTrataTipoDocumento(TrataDTO trata);
	public void actualizarTrataTipoDocumentos(List<TrataTipoDocumentoDTO> ttdModificados, String usuario);
	public void guardarAuditoria(Serializable obj);
	public List<TipoDocumentoGedoDTO> obtenerTiposDocumentoGEDOEspecial();
	public List<TipoDocumentoGedoDTO> obtenerTiposDocumentoGEDOHabilitados();
	public List<TipoDocumentoGedoDTO> obtenerTiposDocumentoGEDO();
	public List<TipoDocumentoDTO> obtenerTiposDocumentoGEDOPorFamilia(String nombreFamilia);

		
}
