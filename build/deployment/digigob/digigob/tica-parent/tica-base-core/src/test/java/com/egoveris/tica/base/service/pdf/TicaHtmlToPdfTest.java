package com.egoveris.tica.base.service.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.egoveris.tica.base.util.convert.TicaHtmlToPdf;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/ApplicationContextTest.xml")
public class TicaHtmlToPdfTest {
	
	@Test
	public void htmlFileToPDF() throws TicaHtmlToPdfException, IOException{
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("template.html").getFile());
		byte[] data = FileUtils.readFileToByteArray(file);
		byte[] htmlPDF = TicaHtmlToPdf.htmlFileToPDF(data);
		Assert.assertNotNull(htmlPDF);
	}
	

}
