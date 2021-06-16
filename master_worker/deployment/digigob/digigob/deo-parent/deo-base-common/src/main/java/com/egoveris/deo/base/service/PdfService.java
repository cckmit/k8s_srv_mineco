package com.egoveris.deo.base.service;

import com.egoveris.deo.base.exception.ModificacionPDFException;
import com.egoveris.deo.base.exception.PerfilConversionException;
import com.egoveris.deo.base.exception.ValidacionCampoFirmaException;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * @author mprieto
 * 
 */
public interface PdfService {

	
	/**
	 * Variables de identificación de los campos de firma y sello.
	 */
	public static final String USUARIO_ = "usuario_";
	public static final String CARGO_ = "cargo_";
	public static final String REPARTICION_ = "reparticion_";
	public static final String SECTOR_ = "sector_";
	public static final String SIGNATURE_ = "signature_";
	public static final String SIGNATURE_CIERRE = "signature_cierre";
	public static final String SELLO_USUARIO = "sello_usuario";
	public static final String FECHA = "fecha";
//	public static final String FECHA2 = "fecha2";
	public static final String NUMERO_DOCUMENTO = "numero_documento";
	
	public byte[] generarPDF(String contenido,
			String referencia, TipoDocumentoDTO tipoDocumento) throws ApplicationException, IOException;
	
	public byte[] generateEncabezado(String contenido,
			String referencia, TipoDocumentoDTO tipoDocumento,byte[] cont) throws ApplicationException;
	
	public byte[] generateLogo() throws ApplicationException;
	
	public byte[] generarPDFCCOO(String contenido,
			String referencia, TipoDocumentoDTO tipoDocumento,RequestGenerarDocumento request) throws ApplicationException;
	
	 public FormularioDTO obtenerFormularioControlado(String nombreForm);

	/**
	 * Transforma un archivo pdf en archivos png.
	 * @param contenidoPdf archivo pdf.
	 * @return Lista de imágenes, tantas como páginas tenga el archivo pdf.
	 * @throws Exception
	 */
	public List<String> transformPDFToPNG(byte[] contenidoPdf) throws ApplicationException;
		
	public boolean validacionTipoArchivo(String nombreArchivo);
	
	/**
	 * Adiciona los campos correspondientes al sello del firmante:
	 *  - Nombre del firmante.
	 *  - Cargo.
	 *  - Repartición.
	 * Adiciona el campo para firma.
	 * La cantidad de sellos y firmas que se crean, dependera de la cantidad de firmantes
	 * definidos para el documento.
	 * @param contenidoArchivo
	 * @param numeroFirmas: Cantidad de usuarios firmantes.
	 * @return El contenido del archivo pdf,  con los campos creados.
	 * @throws Exception
	 */
	public byte[] quitarCamposSelloEncabezado(byte[] contenidoArchivo);
	
	public byte[] adicionarCampos(byte[] contenidoArchivo, Integer numeroFirmas,boolean encabezado)  throws ApplicationException;
	
	/**
	 * Adiciona los campos correspondientes a sello y firma, en una nueva página.
	 * @param contenidoArchivo
	 * @param numeroFirmas: Cantidad de usuarios firmante 
	 * @param acronimoTipoDocumento tipo de documento.
	 * @motivo Motivo del documento a importar.
	 * @return El contenido del archivo pdf,  con los campos creados.
	 * @throws Exception
	 */
	public byte[] adicionarCamposNuevaPagina(byte[] contenidoArchivo, Integer numeroFirmas, String acronimoTipoDocumento,
			String motivo) throws ApplicationException;
	
	public byte[] adicionarCamposNuevaPagina(byte[] contenidoArchivo,
			Integer numeroFirmas, String acronimo, String motivo ,RequestGenerarDocumento request)throws ApplicationException;
	
	/**
	 * Adiciona los campos correspondientes a sello y firma, en una nueva página.
	 * @param contenidoArchivo
	 * @param numeroFirmas: Cantidad de usuarios firmante 
	 * @param acronimoTipoDocumento tipo de documento.
	 * @motivo Motivo del documento a importar.
	 * @param contenidoTemplate: es el contenido template del tipo de documento.
	 * @return El contenido del archivo pdf,  con los campos creados.
	 * @throws Exception
	 */
	public byte[] adicionarCamposNuevaPaginaImpTemp(byte[] contenidoArchivo, Integer numeroFirmas, String acronimoTipoDocumento,
			String motivo, String contenidoTemplate) throws ApplicationException;
	
	public byte[] adicionarCamposNuevaPaginaImpTemp(byte[] contenidoArchivo, Integer numeroFirmas, String acronimoTipoDocumento,
			String motivo, String contenidoTemplate,RequestGenerarDocumento request) throws ApplicationException;
	
	/**
	 * Se borra la ultima pagina del pdf como accion reguladora de los Documentos Adjuntos que no se encuentran 
	 * en el nuevo repositorio de WebDav (Documentos_Adjuntos) 
	 * @param contenidoArchivo
	 * @return
	 * @throws Exception
	 */
	public byte[] quitarUltimaPagina(byte[] contenidoArchivo) throws ApplicationException;
	
	/**
	 * Actualiza campos del tipo AcroField, en un archivo pdf.
	 * @param campos
	 * @param fuente ubicacion del archivo con los campos a actualizar, vacíos.
	 * @param destino ubicacion del nuevo archivo con campos actualizados.
	 * @return
	 * @throws Exception 
	 */
	public byte[] actualizarCampoPdf(Map<String,String> campos, byte[] contenido) throws ApplicationException;
	
	/**
	 * Hace uso de la opción Flattening, para convertir un archivo interactivo en no interactivo y de esta
	 * manera no permitir la edición de los campos.
	 * @param name
	 * @throws Exception
	 * Se 
	 */
	@Deprecated
	public void pdfNoInteractivo(String name) throws ApplicationException;
	
	/**
	 * Identifica el siguiente campo de firma en blanco acorde a los campos interactivos creados
	 * en el archivo .pdf.
	 * @param contenido: Contenido del archivo.
	 * @return numero del campo.
	 */
	public String obtenerCampoPdf(byte[] contenido);
		
	/**
	 * TODO
	 * Documentación.
	 * @param contenido
	 * @param numeroSade
	 * @throws Exception
	 */
	public byte[] agregarNumeroPaginaNumeroSADE(byte[] contenido,String numeroSade) throws ApplicationException ;
	
	/**
	 * Valida si un flujo de bytes de contenido pdf, tiene al menos un campo de firma, lleno.
	 * @param data
	 * @throws ValidacionCampoFirmaException
	 */
	public void validarCamposFirma(byte[] data) throws ValidacionCampoFirmaException;
	
	/**
	 * Crea un archivo pdf a partir de un flujo de datos, del tipo indicado.
	 * @param nombreArchivo
	 * @param datos
	 * @param tipoDocumento 
	 * @return
	 * @throws Exception
	 */
	public byte[] crearDocumentoPDFPrevisualizacion(String nombreArchivo, byte[] datos, TipoDocumentoDTO tipoDocumento) throws ApplicationException;
	
	public byte[] crearDocumentoPDFPrevisualizacion(byte[] datos, TipoDocumentoDTO tipoDocumento) throws ApplicationException;

	/**
	 * Borra recursivamente un directorio y su contenido.
	 * @param directorio
	 * @return
	 */
	public boolean borrarImagenesTemporales(String directorio);
	

	
	/**
	 * Valida si un pdf esta firmado. No valida si la firma es valida.
	 * @param data  (byte[])
	 * @return true si esta firmado / false si no esta firmado.
	 * @throws ValidacionCampoFirmaException
	 */
	public boolean estaFirmado(byte[] data) throws ValidacionCampoFirmaException ;
	
	
	/**
	 * Valida si un pdf esta firmado o posee campos de firma. No valida si la firma es valida.
	 * @param data  (byte[])
	 * @return true si esta firmado o posee campos para firmar / false si no esta firmado o no posee campos de firma para firmar.
	 * @throws ValidacionCampoFirmaException
	 */
	public boolean estaFirmadoOConEspaciosDeFirma(byte[] data) throws ValidacionCampoFirmaException ;
	
	
	
	/**
	 * Firmar un documento con certificado del servidor, este documento ya tiene
	 * creado el campo de firma.
	 * @param archivoFirma: Archivo a firmar.
	 * @param campoFirma: Nombre del campo de firma.
	 * @return El archivo firmado.
	 */
	public byte[] firmarConCertificadoServidor(RequestGenerarDocumento requestGenerarDocumento, 
	    byte[] contenido, String campoFirma, boolean importado) 
           throws ApplicationException;
	
	/**
	 * @deprecated
	 * Permite crear el campo de firma para el cierre, si éste no existe.
	 * @param contenido
	 * @return
	 * @throws ModificacionPDFException
	 * 
	 * 
	 */
	public byte[] crearCampoFirmaCierre(byte[] contenido) throws ModificacionPDFException;
	
	
	public Map<String, Object> crearDocumentoParaAutoFirma(byte[] contenido,Map<String, String> map , boolean importado) throws ModificacionPDFException;
	
	public ByteArrayOutputStream generarPDFImportadoTemplate(InputStream contenidoImportado, 
	    InputStream contenidoTemplate, String motivo, TipoDocumentoDTO tipoDocumento) 
	        throws ApplicationException;

	public byte[] adicionarNuevaPaginaVisualizacionImpTemp(byte[] contenidoImportado, byte[] contenidoTemplate, 
	    String tipoArchivoContenidoImp, TipoDocumentoDTO tipoDocumento, String motivo) 
	        throws ApplicationException;
	
	public byte[] adicionarNuevaPaginaVisualizacionImpTemp(byte[] contenidoImportado, byte[] contenidoTemplate, 
	    String tipoArchivoContenidoImp, TipoDocumentoDTO tipoDocumento, String motivo,RequestGenerarDocumento request) 
	        throws ApplicationException;

	public String getLeyendaConmemorativa() ;

	public void setLeyendaConmemorativa(String leyendaConmemorativa) ;
}
