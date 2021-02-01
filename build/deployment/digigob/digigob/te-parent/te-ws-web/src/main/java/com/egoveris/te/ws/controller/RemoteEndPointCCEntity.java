package com.egoveris.te.ws.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;
import com.egoveris.ffdd.ws.service.ExternalCComplejosService;
import com.egoveris.te.base.util.ReflectionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/ccEntity")
public class RemoteEndPointCCEntity {

  private Log logger = LogFactory.getLog(RemoteEndPointCCEntity.class);

  protected List<AbstractCComplejoDTO> entityDTOClassList;
  
  @Autowired
  private ExternalCComplejosService cComplejosService;
  
  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public ResponseEntity<String> getCCEntityOperation(@RequestParam(value = "entity", required = true) String entity,
      @RequestParam(value = "idOperation", required = true) Integer operation) {
    
    HttpStatus httpStatus = HttpStatus.OK;
    String json = "[]";
    
    if (entityDTOClassList == null) {
      entityDTOClassList = ReflectionUtil.searchClasses(AbstractCComplejoDTO.class);
    }
    
    AbstractCComplejoDTO entityDTO = null;
    
    try {
      for (Object entityDTOClass : entityDTOClassList) {
        if (entityDTOClass instanceof AbstractCComplejoDTO
            && entityDTOClass.getClass().getSimpleName().toLowerCase().contains(entity.concat("DTO").toLowerCase())) {
          entityDTO = (AbstractCComplejoDTO) entityDTOClass.getClass().newInstance();
          break;
        }
      }
    }
    catch (Exception e) {
      logger.debug("Error while searching for entity: ", e);
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
    if (entityDTO != null) {
      entityDTO.setIdOperacion(operation);
      
      try {
        List<AbstractCComplejoDTO> datosComponente = cComplejosService.buscarDatosComponente(entityDTO);
        
        if (!datosComponente.isEmpty()) {
          ObjectMapper mapper = new ObjectMapper();
          json = mapper.writeValueAsString(datosComponente);
        }
      }
      catch (Exception e) {
        logger.debug("Error getting entity data [Entity: " + entityDTO.getClass().getSimpleName() + " | Operation: "
            + operation + "] => ", e);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      }
    }
    
    return ResponseEntity.status(httpStatus).body(json);
  }
  
}
