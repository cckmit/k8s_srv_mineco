package com.egoveris.ws.test;

import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.trade.ws.fusedemoservice.ExternalRequest;
import com.egoveris.trade.ws.fusedemoservice.FuseDemoServiceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/integration-core-config.xml"})
public class FuseTest {

		@Test
		public void test(){
			try {
				ExternalRequest ex = new ExternalRequest();
				ex.setIdMessage("DEMO001");
				ex.setIdTransaction("12312");
				ex.setTypeForm("FORMLUIS");
				URL url = new URL("http://localhost:9090/ws/FuseDemoService?wsdl"); 
				FuseDemoServiceService demo1 = new FuseDemoServiceService(url);
				demo1.getFuseDemoServicePort().fuseDemoCallback(ex);

			} catch (Exception e){
				System.out.println("error " + e.getMessage());
			}
			
			
		}
}
