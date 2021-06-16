package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacion;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.repository.HistorialOperacionRepository;
import com.egoveris.te.base.service.expediente.constants.ConstantesPase;
import com.egoveris.te.base.util.BusinessFormatHelper;

@Service
@Transactional
public class HistorialOperacionServiceImpl implements HistorialOperacionService {
  private static final Logger logger = LoggerFactory
      .getLogger(HistorialOperacionServiceImpl.class);
  @Autowired
  private HistorialOperacionRepository historialOperacionRepository;

  @Autowired
  private UsuariosSADEService usuariosSADEService;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  public void guardarOperacion(HistorialOperacionDTO operacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarOperacion(operacion={}) - start", operacion);
    }

    HistorialOperacion entity = mapper.map(operacion, HistorialOperacion.class);
    historialOperacionRepository.save(entity);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarOperacion(HistorialOperacion) - end");
    }
  }

  public List<HistorialOperacionDTO> buscarHistorialporExpediente(Long idExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarHistorialporExpediente(idExpediente={}) - start", idExpediente);
    }

    List<HistorialOperacion> HistorialOperacionList = historialOperacionRepository
        .findByIdExpediente(idExpediente);
    //INI - Historial de pases sin suplicados de Destino vs Estado
    List<HistorialOperacion> historialLimpio = new ArrayList();
    for(HistorialOperacion aux: HistorialOperacionList){
    	if(historialLimpio.isEmpty()){
    	    historialLimpio.add(aux);
    	 }
    	boolean valida = false;
    	for(HistorialOperacion aux2: historialLimpio){
    		if(aux.getDestinatarioDetalle().toString().equals(aux2.getDestinatarioDetalle().toString()) && aux.getEstado().toString().equals(aux2.getEstado().toString())){
    			valida= true;
    			break;
    		}
    	}
		if(!valida){
			historialLimpio.add(aux);
		}
    }
    HistorialOperacionList = historialLimpio;
    //FIN - Historial de pases sin suplicados de Destino vs Estado
    List<HistorialOperacionDTO> HistorialOperacionDtoList = ListMapper
        .mapList(HistorialOperacionList, mapper, HistorialOperacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarHistorialporExpediente(Integer) - end - return value={}",
          HistorialOperacionDtoList);
    }
    return HistorialOperacionDtoList;
  }

  public void guardarDatosEnHistorialOperacion(ExpedienteElectronicoDTO expedienteElectronico,
      Usuario usuario, Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDatosEnHistorialOperacion(expedienteElectronico={}, usuario={}, detalles={}) - start",
          expedienteElectronico, usuario, detalles);
    }

    detalles.put(ConstantesPase.REPARTICION_USUARIO, usuario.getCodigoReparticion());
    detalles.put(ConstantesPase.SECTOR_USUARIO, usuario.getCodigoSectorInterno());
    HistorialOperacionDTO historialOperacion = new HistorialOperacionDTO();
    historialOperacion.setDetalleOperacion(detalles);

    String tipoOperacion = detalles.get(ConstantesPase.TIPO_OPERACION);
    
    if (tipoOperacion != null) {
      historialOperacion.setTipoOperacion(tipoOperacion);
    } else {
      historialOperacion.setTipoOperacion(ConstantesPase.MOTIVO_PASE);
    }
    
    // Result
    if (expedienteElectronico.getPropertyResultado() != null) {
      historialOperacion.setResultado(expedienteElectronico.getPropertyResultado().getValor());
    }
    historialOperacion.setLatitude(detalles.get(ConstantesPase.LATITUDE));
    historialOperacion.setLongitude(detalles.get(ConstantesPase.LONGITUDE));
    historialOperacion.setFechaOperacion(new Date());
    historialOperacion
        .setExpediente(expedienteElectronico.getTipoDocumento() + expedienteElectronico.getAnio()
            + BusinessFormatHelper.quitaCerosNumeroActuacion(expedienteElectronico.getNumero())
            + expedienteElectronico.getCodigoReparticionActuacion().trim() + "-"
            + expedienteElectronico.getCodigoReparticionUsuario().trim());
    historialOperacion.setUsuario(usuario.getUsername());
    historialOperacion.setIdExpediente(expedienteElectronico.getId());
    
    guardarOperacion(historialOperacion);

    if (expedienteElectronico.getListaExpedientesAsociados() != null
        && expedienteElectronico.getListaExpedientesAsociados().size() > 0) {
      for (ExpedienteAsociadoEntDTO expedienteAsociado : expedienteElectronico
          .getListaExpedientesAsociados()) {
        if (expedienteAsociado.getEsExpedienteAsociadoTC() != null
            && expedienteAsociado.getEsExpedienteAsociadoTC()) {

          HistorialOperacionDTO historialOperacionExpedienteEnTC = new HistorialOperacionDTO();
          historialOperacionExpedienteEnTC.setDetalleOperacion(detalles);
          historialOperacionExpedienteEnTC.setTipoOperacion(ConstantesPase.MOTIVO_PASE);
          historialOperacionExpedienteEnTC.setFechaOperacion(new Date());

          historialOperacionExpedienteEnTC
              .setExpediente(expedienteAsociado.getTipoDocumento() + expedienteAsociado.getAnio()
                  + BusinessFormatHelper.quitaCerosNumeroActuacion(expedienteAsociado.getNumero())
                  + expedienteAsociado.getCodigoReparticionActuacion().trim() + "-"
                  + expedienteAsociado.getCodigoReparticionUsuario().trim());
          historialOperacionExpedienteEnTC.setUsuario(usuario.getUsername());
          historialOperacionExpedienteEnTC
              .setIdExpediente(expedienteAsociado.getIdCodigoCaratula());

          guardarOperacion(historialOperacionExpedienteEnTC);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDatosEnHistorialOperacion(ExpedienteElectronico, Usuario, Map<String,String>) - end");
    }
  }

  public void guardarDatosEnHistorialOperacionSinUsuarioObject(
      ExpedienteElectronicoDTO expedienteElectronico, String usuario,
      Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDatosEnHistorialOperacionSinUsuarioObject(expedienteElectronico={}, usuario={}, detalles={}) - start",
          expedienteElectronico, usuario, detalles);
    }

    Usuario usuarioObject = usuariosSADEService.getDatosUsuario(usuario);
    guardarDatosEnHistorialOperacion(expedienteElectronico, usuarioObject, detalles);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "guardarDatosEnHistorialOperacionSinUsuarioObject(ExpedienteElectronico, String, Map<String,String>) - end");
    }
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }
}
