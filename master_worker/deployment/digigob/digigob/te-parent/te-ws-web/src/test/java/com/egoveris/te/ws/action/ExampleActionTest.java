package com.egoveris.te.ws.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.trade.common.model.RequestMessageDTO;
import com.egoveris.trade.common.model.ResponseMessageDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/integration-core-config.xml" })
public class ExampleActionTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleActionTest.class);
	
	
	@Test
	public void exampleServiceTest() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			String uri = new String("http://7.212.8.112:9600/message/send");
			RequestMessageDTO request = new RequestMessageDTO();
			FormularioWDDTO form = new FormularioWDDTO();
			request.setCode("COD001");
			request.setIdMessage("Message");
			request.setIdTransaction("TRS-0000001");
			request.setFormValues(null);
			request.setForm(form);
			request.setTypeCall("SYNC");
			ResponseEntity<ResponseMessageDTO> response = restTemplate.postForEntity(uri, request, ResponseMessageDTO.class);
			LOGGER.info("response: " + response);
		} catch (Exception e) {
			LOGGER.error("error", e);
		}
	}
}
