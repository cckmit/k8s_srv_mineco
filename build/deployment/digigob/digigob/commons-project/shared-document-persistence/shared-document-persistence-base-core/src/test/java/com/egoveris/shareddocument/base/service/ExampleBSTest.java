package com.egoveris.shareddocument.base.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.egoveris.shareddocument.base.model.WebDAVResourceBean;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {		
		"classpath:/spring/integration-core-config.xml"
		})
public class ExampleBSTest {
	
	@Autowired
	private IWebDavService webDavService;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExampleBSTest.class);
		
	@Test
	public void testlistSpace(){
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("this is only an example test to show how to load ARQ components for the test.");
			}
			
		if(webDavService == null){
			LOGGER.debug("Nulo!!!");
		}else{
			LOGGER.debug("No nulo");
			
			List<WebDAVResourceBean> listado = webDavService.listSpace("/", null, null);
			
			for (WebDAVResourceBean webDavRes : listado) {
				//LOGGER.debug(" Recurso : " + webDavRes.getNombre());
				LOGGER.debug(" Recursos : " + webDavRes.getNombre() +", "+webDavRes.getReferencia()+", "+webDavRes.getArchivo()+", "+webDavRes.getMimeType()+", "+webDavRes.isDirectorio()+", "+webDavRes.getSize()+", "+webDavRes.getFechaCreacion()+", "+webDavRes.getFechaModificacion()+", "+webDavRes.getHref());
			}
			
			Assert.assertTrue(!listado.isEmpty());

		}
	}	
	
	@Test
	public void testMoveFile() throws UnsupportedEncodingException {
		boolean existe = webDavService.existFile("test-mover-archivo.txt", null, null);
		if(existe){
			webDavService.deleteFile("/","test-mover-archivo.txt", null, null);
		}
		
		existe = webDavService.existFile("Compartido/test-mover-archivo.txt", null, null);
		if(existe){
			webDavService.deleteFile("Compartido","test-mover-archivo.txt", null, null);
		}
		
		String content = "Hola mundo!";
		byte[] contentBytes = content.getBytes("UTF-8");
		InputStream stream = new ByteArrayInputStream(contentBytes);

		webDavService.createFile("/", "test-mover-archivo.txt", null, null, stream);
		
		
		//muevo el archivo desde la raiz al destino
		webDavService.moveFile("", "Compartido", "test-mover-archivo.txt", "test-mover-archivo.txt", null, null);
		boolean movido = webDavService.existFile("Compartido/test-mover-archivo.txt", null, null);
		LOGGER.debug(" Movido test-mover-archivo?  : "+ movido);
		
		Assert.assertTrue(movido);
	}
	
	@Test
	public void testCopyFile() throws UnsupportedEncodingException {
		
		boolean existe = webDavService.existFile("test-copiar-documento.txt", null, null);
		if(existe){
			webDavService.deleteFile("/","test-copiar-documento.txt", null, null);
		}
		
		String content = "Hola mundo!";

		byte[] contentBytes = content.getBytes("UTF-8");
		InputStream stream = new ByteArrayInputStream(contentBytes);

		webDavService.createFile("/", "test-copiar-documento.txt", null, null, stream);
		
		webDavService.copyFile("/", "Compartido", "test-copiar-documento.txt", "test-copiar-documento.txt", null, null);
		
		existe = webDavService.existFile("Compartido/test-copiar-documento.txt", null, null);
		Assert.assertTrue(existe);
		
		LOGGER.debug(" Copiado test-copiar-documento.txt?  : "+existe);
		if(existe){
			webDavService.deleteFile("Compartido","test-copiar-documento.txt", null, null);
		}
		
		
	}
	
	@Test
	public void testExistFile(){
		boolean existe = webDavService.existFile("eerrores-te-web.txt", null, null);
		LOGGER.debug(" Existe archivo eerrores-te-web.txt?  : "+existe);
		
		Assert.assertTrue(!existe);
	}
	
	@Test
	public void testDeleteFile() throws UnsupportedEncodingException {
		
		boolean existe = webDavService.existFile("Compartido/test-delete-documento.txt", null, null);
		if(!existe){
			// prepare content - a simple text file
			String content = "Hola mundo!";

			byte[] contentBytes = content.getBytes("UTF-8");
			InputStream stream = new ByteArrayInputStream(contentBytes);
			
			webDavService.createFile("Compartido", "test-delete-documento.txt", null, null, stream);
		}

		webDavService.deleteFile("Compartido","test-delete-documento.txt", null, null);
		existe = webDavService.existFile("Compartido/test-delete-documento.txt", null, null);
		
		Assert.assertTrue(!existe);
		LOGGER.debug(" Existe archivo eliminado?  : "+existe);
	}
	
	@Test
	public void testMoveFolder(){
		//Existe directorio a mover
		boolean existe = webDavService.existFile("directorio-test-mover/", null, null);
		if(existe){
			webDavService.deleteSpace("/", "directorio-test-mover", null, null);
		}
		//Existe directorio destino
		existe = webDavService.existFile("Compartido/directorio-test-mover/", null, null);
		if(existe){
			webDavService.deleteSpace("Compartido", "directorio-test-mover", null, null);
		}
		
		webDavService.createSpace("/", "directorio-test-mover", null, null);
		
		//webdav
		//webDavService.moveFolder("directorio-test-mover", "Compartido/directorio-test-mover", null, null);
		
		//alfresco
		webDavService.moveFolder("directorio-test-mover", "Compartido", null, null);
		
		existe = webDavService.existFile("Compartido/directorio-test-mover/", null, null);
		
		Assert.assertTrue(existe);
		
		LOGGER.debug(" Existe directorio movido ?  : "+existe);
		if(existe){
			webDavService.deleteSpace("Compartido", "directorio-test-mover", null, null);
		}

	}
	
	@Test
	public void testCreateSpace(){

		boolean existe = webDavService.existFile("directorio-test-crea/", null, null);
		if(existe){

			webDavService.deleteSpace("/", "directorio-test-crea", null, null);
		}
		
		webDavService.createSpace("/", "directorio-test-crea", null, null);

		existe = webDavService.existFile("directorio-test-crea/", null, null);
		
		Assert.assertTrue(existe);
		LOGGER.debug(" Existe directorio directorio-test-crea?  : "+existe);
	}
	
	@Test
	public void testCreateFile() throws UnsupportedEncodingException{

		String content = "Hola mundo!";

		byte[] contentBytes = content.getBytes("UTF-8");
		InputStream stream = new ByteArrayInputStream(contentBytes);
		
		webDavService.createFile("Compartido", "test-crear-documento.txt", null, null, stream);
		Boolean existe = webDavService.existFile("Compartido/test-crear-documento.txt", null, null);
		
		LOGGER.debug(" Documento creado ?  : "+existe);
		Assert.assertTrue(existe);
	}
	
	@Test
	public void testDeleteSpace(){

		boolean existe = webDavService.existFile("directorio-test-borrar/", null, null);
		LOGGER.debug(" Existe directorio directorio-test-borrar?  : "+existe);
		if(existe){
			webDavService.deleteSpace("", "directorio-test-borrar", null, null);
		}
		webDavService.createSpace("/","directorio-test-borrar", null, null);
		
		webDavService.deleteSpace("", "directorio-test-borrar", null, null);
		
		existe = webDavService.existFile("directorio-test-borrar", null, null);
		Assert.assertTrue(!existe);
		LOGGER.debug(" Existe directorio directorio-test-borrar?  : "+existe);
	}
	
	@Test
	public void testGetFile() throws UnsupportedEncodingException{
		
		boolean existe = webDavService.existFile("Compartido/test-recuperar-documento.txt", null, null);
		if(existe){
			webDavService.deleteFile("Compartido", "test-recuperar-documento.txt", null, null);
		}
		
		// prepare content - a simple text file
		String content = "Hola mundo!";

		byte[] contentBytes = content.getBytes("UTF-8");
		InputStream stream = new ByteArrayInputStream(contentBytes);
		
		webDavService.createFile("Compartido", "test-recuperar-documento.txt", null, null, stream);
		existe = webDavService.existFile("Compartido/test-recuperar-documento.txt", null, null);
		
		if(existe){
			WebDAVResourceBean webDavRes = webDavService.getFile("/Compartido/test-recuperar-documento.txt", null, null);
			LOGGER.debug(" Recurso : " + webDavRes.getNombre() +", "+webDavRes.getReferencia()+", "+webDavRes.getArchivo()+", "+webDavRes.getMimeType()+", "+webDavRes.isDirectorio()+", "+webDavRes.getSize()+", "+webDavRes.getFechaCreacion()+", "+webDavRes.getFechaModificacion()+", "+webDavRes.getHref());
		}
		Assert.assertTrue(existe);
	}
	
	@Test
	public void testGetFileSize() throws UnsupportedEncodingException {
		boolean existe = webDavService.existFile("Compartido/test-peso-documento.txt", null, null);
		if(existe){
			webDavService.deleteFile("Compartido", "test-peso-documento.txt", null, null);
		}
		
		String content = "Hola mundo!";

		byte[] contentBytes = content.getBytes("UTF-8");
		InputStream stream = new ByteArrayInputStream(contentBytes);
		
		webDavService.createFile("Compartido", "test-peso-documento.txt", null, null, stream);
		
		//webdav
		int size = webDavService.getFileSize("/Compartido/test-peso-documento.txt", null, null);
		
		LOGGER.debug(" Tamaño archivo : "+size);
		
		Assert.assertTrue(size > 0);
	}
	
}
