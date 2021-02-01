package com.egoveris.te.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.EEWorkflowUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TETest {

	@Mock
	ExternalTransaccionService transaccionService;
	
	@Mock
	TipoDocumentoService tipoDocumentoService;
	
	@Mock
	IExternalGenerarDocumentoService generarDocumentoService;

	
	public void setUp() {
		
	}
	
	@Test
	public void test() {

		final String numeroDocumento = "IF-2020-00003303-   -REGCIVIL";
		when(transaccionService.grabarTransaccion(any(TransaccionDTO.class))).thenReturn(1);
		TipoDocumentoDTO tipoDocumento = new TipoDocumentoDTO();
		tipoDocumento.setAcronimo("FFCC");
		tipoDocumento.setTipoProduccion("LIBRE");
		when(tipoDocumentoService.consultarTipoDocumentoPorAcronimo("FFCC")).thenReturn(tipoDocumento);
		ResponseExternalGenerarDocumento documento = new ResponseExternalGenerarDocumento();
		documento.setNumero(numeroDocumento);
		when(generarDocumentoService.generarDocumentoGEDO(any(RequestExternalGenerarDocumento.class))).thenReturn(documento);
		EEWorkflowUtils workflowUtils = new EEWorkflowUtils(
												transaccionService, 
												tipoDocumentoService,
												generarDocumentoService
											);
		ExpedienteElectronicoDTO ee = new ExpedienteElectronicoDTO();
		ee.setUsuarioCreador("ADMINEGOV");
		String json = "{ \"dato\" : \"dato\"}";
		String documentoGenerado = workflowUtils.generarDocumentoRespaldo("FFCC", "Esto es una referencia", json, ee);
		Assert.assertNotNull("El documento no se generó correctamente.", documentoGenerado);
		Assert.assertTrue("El número de documento generado no es el esperado", documentoGenerado.contentEquals(numeroDocumento));
	}
	
}
