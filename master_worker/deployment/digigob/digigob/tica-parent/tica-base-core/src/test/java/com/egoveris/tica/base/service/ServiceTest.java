package com.egoveris.tica.base.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.egoveris.tica.base.exception.TicaServiceException;
import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;
import com.itextpdf.text.pdf.security.DigestAlgorithms;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/ApplicationContextTest.xml")
public class ServiceTest {

	@Autowired
	TicaPdfService ticaPdfService;
	
	@Test(expected=TicaHtmlToPdfException.class)	
	public void htmlToPdfNullTest() throws  TicaHtmlToPdfException{
		ticaPdfService.htmlToPdf(null);
	}
	
	@Test
	public void htmlToPdfTest() throws  IOException, TicaHtmlToPdfException {
		byte[] pdf = ticaPdfService.htmlToPdf(getFileArray());
		Assert.assertNotNull(pdf);
		
	}
	
	@Test
	public void firmarDocumentoTest() throws  IOException, TicaSignPdfException, TicaServiceException, TicaHtmlToPdfException{
		FirmaInput firmaInput = new FirmaInput();
		firmaInput.setAlgoritmoHash(DigestAlgorithms.SHA256);
		firmaInput.setData(getFileArray());
		firmaInput.setAliasCert("egoveris");
		firmaInput.setSignatureFieldName("signature_0");
		firmaInput.setPassword("egoveris");
		firmaInput.setLocation("SANTIAGO");
		firmaInput.setSello(getFile("sello.png"));
		firmaInput.setKeyStore(getFile("keyStore"));
		byte[] pdf = ticaPdfService.firmarDocumento(firmaInput, false);
		
		FirmaInput firmaInput3 = new FirmaInput();
		firmaInput3.setAlgoritmoHash(DigestAlgorithms.SHA256);
		firmaInput3.setData(pdf);
		firmaInput3.setAliasCert("egoveris");
		firmaInput3.setSignatureFieldName("signature_1");
		firmaInput3.setPassword("egoveris");
		firmaInput3.setLocation("PUERTO MONTT");
		firmaInput3.setSello(getFile("sello.png"));
		firmaInput3.setKeyStore(getFile("keyStore"));
		pdf = ticaPdfService.firmarDocumento(firmaInput3, false);
		Assert.assertNotNull(pdf);	
	}
	
	private byte[] getFileArray() throws  TicaServiceException, TicaHtmlToPdfException, IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("files/page.html").getFile());
		return ticaPdfService.htmlToPdf(Files.readAllBytes(file.toPath()));
	}
	
	private File getFile(String fileName) throws IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("files/"  + fileName).getFile());
	}
}
