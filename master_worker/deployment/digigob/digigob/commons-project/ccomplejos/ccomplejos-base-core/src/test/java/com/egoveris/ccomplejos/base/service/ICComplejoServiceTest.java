package com.egoveris.ccomplejos.base.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.ccomplejos.base.model.MercanciaDTO;
import com.egoveris.ccomplejos.base.model.ParticipantesDTO;
import com.egoveris.ccomplejos.base.model.ReservaDTO;
import com.egoveris.ccomplejos.base.model.TransportDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {		
		"classpath:/spring/integration-core-config.xml"
		})
public class ICComplejoServiceTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ICComplejoServiceTest.class);

	@Autowired
	private ICComplejosService iCComplejosService;

//	@Test
//	@Transactional
//	public void guardarDatosCommonEDATest() {
//		DocumentDTO docDTO = new DocumentDTO();
//		docDTO.setCodigoDocumento("aasdsad");
//		List<DocumentDTO> listaDocumentoComercial = new ArrayList<DocumentDTO>();
//		listaDocumentoComercial.add(docDTO);
//		
//		InstalacionDTO inst = new InstalacionDTO();
//		inst.setCodigoInstalacionDestino("sadsadsad");
//		List<InstalacionDTO> listaInstalacion = new ArrayList<InstalacionDTO>();
//		listaInstalacion.add(inst);
//		
//		LoteDTO lote = new LoteDTO();
//		lote.setNombre("sadsadsad");
//		List<LoteDTO> listaLote = new ArrayList<LoteDTO>();
//		listaInstalacion.add(inst);
//
//		
//		ParticipantesDTO participante = new ParticipantesDTO();
//		participante.setNombre("sadsadsad");
//		List<ParticipantesDTO> listaTransportista = new ArrayList<ParticipantesDTO>();
//		listaInstalacion.add(inst);
//		
//		DocumentoApoyoDTO docApoyo = new DocumentoApoyoDTO();
//		docApoyo.setNombre("sadsadsad");
//		List<DocumentoApoyoDTO> listDocumentoApoyo = new ArrayList<DocumentoApoyoDTO>();
//		listaInstalacion.add(inst);
//		
//		ObservacionDTO observacion = new ObservacionDTO();
//		observacion.setNombre("sadsadsad");
//		List<ObservacionDTO> listObservacionDTOs = new ArrayList<ObservacionDTO>();
//		listaInstalacion.add(inst);
//		
//		
//		ItemDTO dto = new ItemDTO();
//		dto.setIdOperacion(1);
//		dto.setNombre("abc");
//		dto.setAcuerdoComercial("Aasd");
//		dto.setListaDocumentoComercial(listaDocumentoComercial);
//		dto.setListaInstalacion(listaInstalacion);
//		dto.setListaLote(listaLote);
//		dto.setListaTransportista(listaTransportista);
//		dto.setListDocumentoApoyo(listDocumentoApoyo);
//		dto.setListObservacionDTOs(listObservacionDTOs);
//		
//		iCComplejosService.guardarDatosComponentes(Arrays.asList(dto));
//		LOGGER.info("dto: " + dto);
//
//		List lista = iCComplejosService.buscarDatosComponente(dto);
//		LOGGER.info("lista: " + lista);
//	}

	@Test
	@Transactional
	public void guardarDatosDetailEDATest() {
		MercanciaDTO mer = new MercanciaDTO();
		mer.setNombre("dsfdsf");
		mer.setIdOperacion(1);
		ReservaDTO reser = new ReservaDTO();
		reser.setNombre("dsfdf");
		reser.setIdOperacion(1);
		ParticipantesDTO aprti = new ParticipantesDTO();
		aprti.setNombre("sdfdf");
		aprti.setIdOperacion(1);
		TransportDTO dto = new TransportDTO();
//		dto.setId(1);
		dto.setIdOperacion(1);
		dto.setNombre("abc");
		dto.setIdOperacion(1);
		dto.setMercancia(mer);
		dto.setReserva(reser);
		dto.setTransportista(aprti);

		iCComplejosService.guardarDatosComponentes(Arrays.asList(dto));
		LOGGER.info("dto: " + dto);

		List lista = iCComplejosService.buscarDatosComponente(dto);
		LOGGER.info("lista: " + lista);
	}
	
	
//	@Test
//	@Transactional
//	public void guardarDatosDetailEDATest() {
//		ParticipanteSecundarioDTO asd = new ParticipanteSecundarioDTO();
//		List<ObservacionDTO> listObservaciones = new ArrayList<ObservacionDTO>();
//		asd.setId(1);
//		asd.setIdOperacion(1);
//		asd.setNombre("asdsad");
//		
//		ViewResumenDTO dto = new ViewResumenDTO();
//		dto.setIdOperacion(1);
//		dto.setNombre("abc");
//		dto.setId(1);
//		dto.setFechaCreacion(new Date());
//		dto.setItemTotal(1);
//
//		iCComplejosService.guardarDatosComponentes(Arrays.asList(dto));
//		LOGGER.info("dto: " + dto);
//
//		List lista = iCComplejosService.buscarDatosComponente(dto);
//		LOGGER.info("lista: " + lista);
//	}

}
