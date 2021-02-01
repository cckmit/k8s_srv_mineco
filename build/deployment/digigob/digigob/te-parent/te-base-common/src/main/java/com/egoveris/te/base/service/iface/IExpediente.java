package com.egoveris.te.base.service.iface;

import java.util.Date;
import java.util.List;

import com.egoveris.te.base.exception.RemoveDocException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;




/**
 * Intefaz creada para unir metodos de un Expediente Electronico con un ExpedienteTrack
 * @author joflores
 *
 */
public interface IExpediente {

	public void setSolicitudIniciadora(SolicitudExpedienteDTO solicitudIniciadora);
	public SolicitudExpedienteDTO getSolicitudIniciadora();
	public void agregarDocumento(DocumentoDTO documento);
	public void desagregarDocumento(DocumentoDTO documento);
	public List<DocumentoDTO> getDocumentos();
	public void setDocumentos(List<DocumentoDTO> documentos);
	public String getSistemaCreador();
	public void setSistemaCreador(String sistemaCreador);
	public String getSistemaApoderado();
	public void setSistemaApoderado(String sistemaApoderado);
	public boolean getBloqueado();
	public void setBloqueado(boolean bloqueado);
	public Boolean getTramitacionLibre();
	public void setTramitacionLibre(Boolean tramitacionLibre);
	public String getIdWorkflow();
	public void setIdWorkflow(String idWorkflow);
	public Long getId();
	public void setId(Long id);
	public List<ArchivoDeTrabajoDTO> getArchivosDeTrabajo();
	public List<ArchivoDeTrabajoDTO> obtenerArchivosDeTrabajoAcumulado(String reparticionUsuario);
	public void setArchivosDeTrabajo(List<ArchivoDeTrabajoDTO> archivosDeTrabajo);
	public List<ExpedienteAsociadoEntDTO> getListaExpedientesAsociados();
	public void setListaExpedientesAsociados(List<ExpedienteAsociadoEntDTO> listaExpedientesAsociados);
	public String getDescripcion();
	public void setDescripcion(String descripcion);
	public TrataDTO getTrata();
	public void setTrata(TrataDTO trata);
	public String getUsuarioCreador();
	public void setUsuarioCreador(String usuarioCreador);
	public Date getFechaCreacion();
	public void setFechaCreacion(Date fechaCreacion);
	public String getUsuarioModificacion();
	public void setUsuarioModificacion(String usuarioModificacion);
	public Date getFechaModificacion();
	public void setFechaModificacion(Date fechaModificacion);
	public String getTipoDocumento();
	public void setTipoDocumento(String tipoDocumento);
	public Integer getAnio();
	public void setAnio(Integer anio);
	public Integer getNumero();
	public void setNumero(Integer numero);
	public String getSecuencia();
	public void setSecuencia(String secuencia);
	public Boolean getDefinitivo();
	public void setDefinitivo(Boolean definitivo);
	public String getCodigoReparticionUsuario();
	public void setCodigoReparticionUsuario(String codigoReparticionUsuario);
	public String getCodigoReparticionActuacion();
	public void setCodigoReparticionActuacion(String codigoReparticionActuacion);
	public Boolean getEsElectronico();
	public void setEsElectronico(Boolean esElectronico);
	public List<ExpedienteMetadataDTO> getMetadatosDeTrata();
	public void setMetadatosDeTrata(List<ExpedienteMetadataDTO> metadatosDeTrata);
	public void agregarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoJob);
	public void agregarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado);
	public void eliminarDocumento(DocumentoDTO documento);
	public void agregarMetadatosDeTrata(ExpedienteMetadataDTO espedienteMetadata);
	public String getEstado();
	public void setEstado(String estado);
	public String getCodigoCaratula();
	public Boolean getEsCabeceraTC();
	public void setEsCabeceraTC(Boolean esCabeceraTC);
	public Boolean getEsReservado();
	public void setEsReservado(Boolean esReservado);	
	public String getUsuarioReserva();
	public void setUsuarioReserva(String usuarioReserva);
	public Date getFechaReserva();
	public void setFechaReserva(Date fechaReserva);
	public void desagregarArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoTrabajo);
	public void desagregarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsoc);
	public boolean existenExpedientesDefinitivos();	
	public boolean existenExpedientesFusionados();	
	public boolean existenExpedientesTramitacion();
	public void removeDoc(DocumentoDTO documentoGEDO) throws RemoveDocException;
	public void removeDocsNoDefinitivos();
	public void desasociarPorNumeroDeDoc(Integer numero) throws Exception;
	public void setFechaArchivo(Date date);
	public void setFechaSolicitudArchivo(Date date);
	public List<String> getCodigosDeDocumentos();
	
	/**
	 * metodos de un expediente Track
	 * @return
	 */
	public String getLetraTrack();
	public String getNumeroDocumentoTrack();
	public String getTipoDocumentoTrack();
	public String getLetraIniciaTrack();
	public Integer getAnioIniciaTrack();
	public Integer getNumeroActuacionIniciaTrack();
	public String getReparticionActuacionIniciaTrack();
	public String getSecuenciaIniciaTrack();
	public String getCalleTrack();
	public String getApellidonombrerazonTrack();
	public String getDominioTrack();
	public String getIssibTrack();
	public String getDenunciaTrack();
	public Integer getNumerocalleTrack();
	public String getPartidaablTrack();
	public String getCatastralTrack();
	public String getCodigoTrataTrack();
	public String getReparticionOrigenTrack();
	public String getSectorOrigenTrack();
	public Integer getFojasTrack();
	public String getOrigenTrack();
	public String getObservacionesTrack();
	


}
