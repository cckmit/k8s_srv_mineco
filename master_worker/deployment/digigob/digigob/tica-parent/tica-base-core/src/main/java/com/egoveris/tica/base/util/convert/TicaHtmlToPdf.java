package com.egoveris.tica.base.util.convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.tica.base.exception.TicaHtmlToPdfException;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.DefaultTagProcessorFactory;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * @author acampos
 * @version 1.0.0
 * @since
 */
public class TicaHtmlToPdf {
	

	private static XMLParser xmlParser;
	private static PdfWriterPipeline pdfPipeline;
	private static Document document;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TicaHtmlToPdf.class);
	
	private TicaHtmlToPdf(){
		
	}

	/**
	 * Inicializacion de variables
	 */
	private static void initHtmlToPdf() {
		// CSS
		CSSResolver cssResolver = new StyleAttrCSSResolver();
		// HTML
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		TagProcessorFactory tagProcessorFactory = (DefaultTagProcessorFactory) Tags.getHtmlTagProcessorFactory();
		tagProcessorFactory.addProcessor(new TextBoxTagProcessor(), "textbox");
		htmlContext.setTagFactory(tagProcessorFactory);
		pdfPipeline = new PdfWriterPipeline();
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdfPipeline);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		XMLWorker worker = new XMLWorker(css, true);
		xmlParser = new XMLParser(worker);
	}

	/**
	 * Metodo que a partir de un html, como arreglo de byte, retorna un pdf como
	 * arreglo de byte.
	 * 
	 * @param data
	 * @return
	 * @throws TicaHtmlToPdfException
	 */
	public static byte[] htmlFileToPDF(byte[] data) throws TicaHtmlToPdfException {
		initHtmlToPdf();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		document = new Document();
		PdfWriter writer;
		try {
			writer = PdfWriter.getInstance(document, baos);
			writer.setInitialLeading(12.5f);
			pdfPipeline.setDocument(document);
			pdfPipeline.setWriter(writer);
			document.open();
			xmlParser.parse(new ByteArrayInputStream(data));
			
			document.close();
		} catch (DocumentException e) {
			throw new TicaHtmlToPdfException("DocumentException " + e);
		} catch (IOException e) {
			throw new TicaHtmlToPdfException("IOException " + e);
		}
		return baos.toByteArray();
	}
	
	public static byte[] htmlFileToPDFv2(byte[] data) throws TicaHtmlToPdfException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
		
      ConverterProperties properties = new ConverterProperties();
      properties.setCreateAcroForm(true);
      HtmlConverter.convertToPdf(new ByteArrayInputStream(data), baos , properties);

			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			throw new TicaHtmlToPdfException("Exception " + e);
		} 
		return baos.toByteArray();
	}

}
