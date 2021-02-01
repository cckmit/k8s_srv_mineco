package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.CodigoDocumentoNuloOVacioExcepcion;
import com.egoveris.deo.base.exception.DocumentoDigitalExistenteException;
import com.egoveris.deo.base.exception.DocumentoExistenteGEDOCaratulaExcepcion;
import com.egoveris.deo.base.exception.NoEsDocumentoSadePapelException;
import com.egoveris.deo.base.exception.NoExisteDocumentoSadeExcepcion;
import com.egoveris.deo.base.repository.DocumentoRepository;
import com.egoveris.deo.base.service.BuscarDocumentosSadeService;

import org.apache.commons.lang.NotImplementedException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BuscarDocumentosSadeServiceImpl implements BuscarDocumentosSadeService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(BuscarDocumentosSadeServiceImpl.class);


  @Autowired
  protected DocumentoRepository documentoRepo;

  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;
//
//  public CaratulaBeanSade existeDocumento(String codigo)
//      throws DocumentoDigitalExistenteException {
//    if (logger.isDebugEnabled()) {
//      logger.debug("existeDocumento(String) - start"); //$NON-NLS-1$
//    }
//
//    List<CaratulaBeanSade> list = caratulaRepo.findByNumeroDocumentoLike(codigo);
//    if ((list != null) && (!list.isEmpty())) {
//      CaratulaBeanSade returnCaratulaBeanSade = list.get(0);
//      if (logger.isDebugEnabled()) {
//        logger.debug("existeDocumento(String) - end"); //$NON-NLS-1$
//      }
//      return returnCaratulaBeanSade;
//    }
//
//    if (logger.isDebugEnabled()) {
//      logger.debug("existeDocumento(String) - end"); //$NON-NLS-1$
//    }
//    return null;
//  }

  public String consultaCaratulaSadeExiste(String numeroDocumento, String codigoSADE)
      throws NoExisteDocumentoSadeExcepcion, DocumentoExistenteGEDOCaratulaExcepcion,
      DocumentoDigitalExistenteException, CodigoDocumentoNuloOVacioExcepcion,
      NoEsDocumentoSadePapelException {
    throw new NotImplementedException("Funcionalidad eliminada por eliminar sade-commons-services. Revisar.");
//    if (logger.isDebugEnabled()) {
//      logger.debug("consultaCaratulaSadeExiste(String, String) - start"); //$NON-NLS-1$
//    }
//
//    if (StringUtils.isEmpty(codigoSADE)) {
//      throw new CodigoDocumentoNuloOVacioExcepcion("No puede ser el codigo nulo o vacio");
//    }
//    DocumentoDTO documentoEncontrado = this.mapper
//        .map(this.documentoRepo.findByNumero(numeroDocumento), DocumentoDTO.class);
//    if (documentoEncontrado != null) {
//      throw new DocumentoExistenteGEDOCaratulaExcepcion(
//          "Existen documentos para la caratula" + numeroDocumento);
//    }
//    CaratulaBeanSade caratula = this.existeDocumento(codigoSADE);
//    if (caratula == null) {
//      throw new NoExisteDocumentoSadeExcepcion("No Existe el documento en sade: " + codigoSADE);
//    } else if (!caratula.getModuloOrigen().trim().toUpperCase().equals("SADE")) {
//      throw new NoEsDocumentoSadePapelException("No es un documento Sade papel: " + codigoSADE);
//    }
//    String returnString = caratula.getDescripcionTrata();
//    if (logger.isDebugEnabled()) {
//      logger.debug("consultaCaratulaSadeExiste(String, String) - end"); //$NON-NLS-1$
//    }
//    return returnString;
  }
}
