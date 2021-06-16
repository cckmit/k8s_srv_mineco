package com.egoveris.deo.web.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egoveris.deo.base.service.DocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.model.model.DocumentoDTO;

@RestController
public class DescargaDocumentoPublicableController {

	private static final int ARBITARY_SIZE = 4096;
	
	@Autowired
	private DocumentoService documentoService;

	@Autowired
	@Qualifier("gestionArchivosWebDavServiceImpl")
	private GestionArchivosWebDavService gestionArchivosWebDavService;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello() {
		return "Hello world!!!";
	}

	@RequestMapping(value = "/documento-info", method = RequestMethod.GET)
	public ResponseEntity<?> getDocumentoInfo(@RequestParam(name="id", required = true) String id) {
		ResponseEntity<?> response = null;
		
		Map<String, String> responseData = new HashMap<>();
		DocumentoDTO documento = this.documentoService.findByIdPublicable(id);
		if (documento != null) {
			responseData.put("documento", documento.getNumeroEspecial());		
			response = ResponseEntity.ok(responseData);
		} else {
			responseData.put("message", "El documento no existe");
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
		}
		return response;
	}
	@RequestMapping(value = "/descargar-documento", method = RequestMethod.GET)
	public void descargarDocumento(
				@RequestParam(name="id", required = true) String id,
				HttpServletResponse resp
			) throws IOException {
		DocumentoDTO documento = this.documentoService.findByIdPublicable(id);
		if (documento != null) {
			resp.setContentType("application/pdf");
			resp.setHeader("Content-disposition", "attachment; filename=" + documento.getNumero() + ".pdf");

			try (BufferedInputStream in = this.gestionArchivosWebDavService.obtenerDocumento(documento.getNumero())
					.getFileAsStream(); OutputStream out = resp.getOutputStream()) {

				byte[] buffer = new byte[ARBITARY_SIZE];

				int numBytesRead;
				while ((numBytesRead = in.read(buffer)) > 0) {
					out.write(buffer, 0, numBytesRead);
				}
			} catch (Exception e) {
				
			}			
		} else {
			resp.setContentType("application/json");
			resp.getOutputStream().write(("{ \"message\" : \"El documento no existe.\"}").getBytes());
		}

	}

}
