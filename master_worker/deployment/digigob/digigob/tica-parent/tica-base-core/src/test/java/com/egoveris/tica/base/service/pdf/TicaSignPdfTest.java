package com.egoveris.tica.base.service.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;
import com.egoveris.tica.base.util.sign.TicaSignPdf;
import com.itextpdf.text.pdf.security.DigestAlgorithms;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/ApplicationContextTest.xml")
public class TicaSignPdfTest {
	
	@Test
	public void firmarConCertificado() throws TicaSignPdfException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File pdf = new File(classLoader.getResource("firmar.pdf").getFile());
		File keyStore = new File(classLoader.getResource("keystoreTest").getFile());
		FirmaInput firmaInput = new FirmaInput();
		byte[] data = FileUtils.readFileToByteArray(pdf);
		firmaInput.setAlgoritmoHash(DigestAlgorithms.SHA256);
		firmaInput.setData(data);
		firmaInput.setAliasCert("egoveris");
		firmaInput.setSignatureFieldName("signature_0");
		firmaInput.setKeyStore(keyStore);
		firmaInput.setPassword("egoveris");
		byte[] firmado = TicaSignPdf.signPdfWithCertificate(firmaInput, false);
		Assert.assertNotNull(firmado);
	}
}
