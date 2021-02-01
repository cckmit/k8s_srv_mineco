package com.egoveris.tica.base.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.commons.io.IOUtils;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XmlSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;
import com.egoveris.tica.base.service.TicaPdfService;
import com.egoveris.tica.base.util.convert.TicaHtmlToPdf;
import com.egoveris.tica.base.util.sign.TicaSignPdf;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

@Service("ticaPdfService")
public class TicaPdfServiceImpl implements TicaPdfService {

	private static final Logger logger = LoggerFactory.getLogger(TicaPdfServiceImpl.class);

	  @Value("${cas.url}/logo.jpg")
	  private Resource imagenDoc;
	
	  @Value("${tica.pdf.style.header.width1:20}")
	  private int headerWidth1;
	  @Value("${tica.pdf.style.header.width2:23}")
	  private int headerWidth2;
	  @Value("${tica.pdf.style.header.width3:17}")
	  private int headerWidth3;
	  @Value("${tica.pdf.style.header.width4:20}")
	  private int headerWidth4;
	  @Value("${tica.pdf.style.header.width5:20}")
	  private int headerWidth5;
	  
	public byte[] firmarDocumento(FirmaInput firmaInput, boolean importado) throws TicaSignPdfException {
		byte[] pdf;
		logger.info("invocando metodo signPdfWithCertificate(FirmaInput firmaInput) >> TicaLib");
		pdf = TicaSignPdf.signPdfWithCertificate(firmaInput, importado);
		logger.info("return PDF firmado");
		return pdf;
	}

	public byte[] htmlToPdf(byte[] data) throws TicaHtmlToPdfException {
		try {
			byte[] pdf;
			logger.info("invocando metodo htmlFileToPDF(byte[] data) >> TicaLib");
			
			pdf = TicaHtmlToPdf.htmlFileToPDFv2(validarHTML(data));
			//			pdf = TicaHtmlToPdf.htmlFileToPDF(data);
			logger.info("return PDF generado");
			return agregarImagenEncabezado(pdf);
		} catch (DocumentException e) {
			throw new TicaHtmlToPdfException("DocumentException " + e);
		} catch (IOException e) {
			throw new TicaHtmlToPdfException("IOException " + e);
		}
	}

	
	  private byte[] agregarImagenEncabezado(byte[] contenidoPDF) throws IOException, DocumentException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfReader pdfReader = new PdfReader(contenidoPDF);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
			PdfContentByte contentByte = pdfStamper.getOverContent(1);
		    PdfPTable header = new PdfPTable(5);
		    try {
		        // set defaults
		    	
//		    	header.setWidths(new int[]{20,23,17,20,20});
		    	header.setWidths(new int[]{headerWidth1,headerWidth2,headerWidth3,headerWidth4,headerWidth5});
		        header.setTotalWidth(525);
		        header.setLockedWidth(true);
		        header.getDefaultCell().setFixedHeight(80);
		        header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		        // add image
		        InputStream input=imagenDoc.getInputStream();
				byte[] bytes=IOUtils.toByteArray(input);
				Image pdfImg=Image.getInstance(bytes);
				pdfImg.setAlignment(Image.MIDDLE);
				header.addCell("");
				header.addCell("");
		        header.addCell(pdfImg);
		        header.addCell("");
		        header.addCell("");

		        // write content
		        header.writeSelectedRows(0, -1, 34, 823, contentByte);
		    } catch(DocumentException de) {
		    	throw new TicaHtmlToPdfException("Error validar HTML ", de);
		    } catch (MalformedURLException e) {
		    	throw new TicaHtmlToPdfException("Error validar HTML ", e);
		    } catch (IOException e) {
		    	throw new TicaHtmlToPdfException("Error validar HTML ", e);
		    }

			pdfStamper.close();
			pdfReader.close();
			return baos.toByteArray();
	  }
	
	@SuppressWarnings("deprecation")
	private byte[] validarHTML(byte[] data) throws TicaHtmlToPdfException {
		try {
			InputStream myInputStream = new ByteArrayInputStream(data);
			HtmlCleaner htmlCleaner = new HtmlCleaner();
			CleanerProperties cleanerProperties = htmlCleaner.getProperties();
			cleanerProperties.setOmitComments(true);
			cleanerProperties.setTranslateSpecialEntities(false);
			cleanerProperties.setRecognizeUnicodeChars(false);
			cleanerProperties.setOmitUnknownTags(true);
			cleanerProperties.setOmitDoctypeDeclaration(false);
			cleanerProperties.setOmitXmlDeclaration(false);
			cleanerProperties.setUseCdataForScriptAndStyle(true);

			TagNode tagNode = htmlCleaner.clean(myInputStream);
			tagNode.removeAttribute("xmlns:xml");
			XmlSerializer xmlSerializer = new CompactXmlSerializer(cleanerProperties);
			String cleanedPage = xmlSerializer.getXmlAsString(tagNode, "UTF-8");
			return cleanedPage.getBytes();
		} catch (Exception e) {
			logger.error("Error validar HTML ");
			throw new TicaHtmlToPdfException("Error validar HTML ", e);
		}
	}
}
