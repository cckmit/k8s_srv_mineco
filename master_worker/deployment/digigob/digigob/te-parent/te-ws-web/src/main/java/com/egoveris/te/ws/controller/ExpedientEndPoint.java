package com.egoveris.te.ws.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egoveris.te.base.service.iface.IGenerarExpedienteService;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.egoveris.te.ws.model.RemoteResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/expedient")
public class ExpedientEndPoint {
  
  private Log logger =  LogFactory.getLog(RemoteEndPoint.class);
  
  @Autowired
  IGenerarExpedienteService generarExpedienteServiceImpl;
  
  /**
   * Generates a new Expedient, according to given data
   * 
   * @param username Username
   * @param trataCod Tramitation Type
   * @param motive Motive
   * @param description Description
   * @return
   */
  @ApiOperation(value = "Generates a new expedient", response = String.class)
  @RequestMapping(value="/generate", method = RequestMethod.POST)
  public ResponseEntity<RemoteResponse> generateEE(
      @ApiParam(name = "username", value = "Logged username") @RequestParam("username") String username,
      @ApiParam(name = "trataCod", value = "Tramitation type") @RequestParam("trataCod") String trataCod,
      @ApiParam(name = "motive", value = "Motive of expedient") @RequestParam("motive") String motive,
      @ApiParam(name = "description", value = "Description of expedient") @RequestParam("description") String description) {
	  
	  ResponseEntity<RemoteResponse> responseEntity = null;
	  RemoteResponse response = new RemoteResponse();
	  
	  CaratulacionExpedienteRequest caratulacionRequest = new CaratulacionExpedienteRequest();
	  caratulacionRequest.setLoggeduser(username);
	  caratulacionRequest.setSistema("TE");
	  caratulacionRequest.setInterno(true);
	  caratulacionRequest.setSelectTrataCod(trataCod);
	  caratulacionRequest.setDescripcion(description);
	  caratulacionRequest.setMotivo(motive);
	  caratulacionRequest.setTaskApp(true);
	  
	  try {
	    String generatedEE = generarExpedienteServiceImpl.generarExpedienteElectronicoCaratulacion(caratulacionRequest);
	    
	    response.setHttpStatus(200);
	    response.setStatusApp(1);
	    response.setMessage(generatedEE);
	    responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
	  }
	  catch (Exception e) {
	    logger.error("Error calling generarExpedienteElectronicoCaratulacion(): ", e);
	    
	    response.setHttpStatus(500);
	    response.setStatusApp(0);
	    response.setMessage(e.getMessage());
	    responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	  }
	  
	  return responseEntity;
	}
	
}