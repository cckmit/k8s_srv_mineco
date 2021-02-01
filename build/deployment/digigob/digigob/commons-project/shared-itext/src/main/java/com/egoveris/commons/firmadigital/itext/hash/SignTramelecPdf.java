package com.egoveris.commons.firmadigital.itext.hash;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmaNoExisteException;
import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmadoException;
import com.egoveris.commons.firmadigital.itext.excepciones.SinCamposFirmaException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

public class SignTramelecPdf {

	private static final Logger logger = LoggerFactory.getLogger(SignTramelecPdf.class);
	private static final int ESTIMATED_SIZE = 8192 + 4192;
	private static final String LOCATION_CABA = "Ciudad Autónoma de Buenos Aires";
	private static final String TSA_USERNAME = "";
	private static final String TSA_PASSWORD = "";
	private final PdfReader reader;
	private final Calendar cal;
	private PdfSignatureAppearance sap;
	private byte[] hash;
	private final ByteArrayOutputStream fout;
	private PdfPKCS7 sgn;
	private static final String USUARIO_ = "usuario_";
	private static final String CARGO_ = "cargo_";
	private static final String REPARTICION_ = "reparticion_";
	private Boolean ambienteDesa = false;
	private final String tsa_url;

	public SignTramelecPdf(final InputStream data, final Boolean ambienteDesa, final String tsa_url)
			throws IOException {
		this.reader = new PdfReader(data);
		this.fout = new ByteArrayOutputStream();
		this.cal = Calendar.getInstance();
		this.ambienteDesa = ambienteDesa;
		this.tsa_url = tsa_url;
	}

	public SignTramelecPdf(final byte[] data, final Boolean ambienteDesa, final String tsa_url) throws IOException {
		this.reader = new PdfReader(data);
		this.fout = new ByteArrayOutputStream();
		this.cal = Calendar.getInstance();
		this.ambienteDesa = ambienteDesa;
		this.tsa_url = tsa_url;
	}

	/**
	 * Prepara el pdf para firmar
	 *
	 * @param msgDigAlg
	 *            SHA1, SHA256
	 * @param fieldname
	 * @return hash para firmar
	 * @throws IOException
	 * @throws DocumentException
	 * @throws CampoFirmaNoExisteException
	 * @throws CampoFirmadoException
	 * @throws SinCamposFirmaException
	 * @throws GeneralSecurityException
	 * @throws Exception
	 */
	public byte[] prepararPdf(final String msgDigAlg, final String fieldname, final Certificate[] certs)
			throws DocumentException, IOException, SinCamposFirmaException, CampoFirmadoException,
			CampoFirmaNoExisteException, GeneralSecurityException {
		final PdfStamper stp = PdfStamper.createSignature(this.reader, this.fout, '\0', null, true);
		this.sap = buildTramelecSignature(stp, fieldname, certs);
		final HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
		exc.put(PdfName.CONTENTS, new Integer(ESTIMATED_SIZE * 2 + 2));
		this.sap.preClose(exc);
		final ExternalDigest digest = new BouncyCastleDigest();
		this.sgn = new PdfPKCS7(null, certs, msgDigAlg, null, digest, false);
		final InputStream data = this.sap.getRangeStream();
		this.hash = DigestAlgorithms.digest(data, digest.getMessageDigest(msgDigAlg));
		return this.sgn.getAuthenticatedAttributeBytes(this.hash, this.cal, null, null, CryptoStandard.CMS);
	}

	private PdfSignatureAppearance buildTramelecSignature(final PdfStamper stp, final String fieldName,
			final Certificate[] certs)
			throws SinCamposFirmaException, CampoFirmadoException, CampoFirmaNoExisteException {
		final String validFieldName = campoValidoFirma(stp, fieldName);
		final PdfSignatureAppearance sap = stp.getSignatureAppearance();
		sap.setLocation(LOCATION_CABA);
		sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
		sap.setVisibleSignature(validFieldName);
		sap.setCertificate(certs[0]);
		final PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
		dic.setReason(sap.getReason());
		dic.setLocation(sap.getLocation());
		dic.setSignatureCreator(sap.getSignatureCreator());
		dic.setContact(sap.getContact());
		dic.setDate(new PdfDate(sap.getSignDate()));
		sap.setCryptoDictionary(dic);
		return sap;
	}

	/**
	 * @param signAlg
	 *            encryption algorithm RSA, DSA
	 * @param extSignature
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public byte[] firmarPdf(final String signAlg, final byte[] extSignature)
			throws IOException, DocumentException, ExceptionConverter {
		this.sgn.setExternalDigest(extSignature, null, signAlg);
		TSAClientBouncyCastle tsaClient = null;
		if (!ambienteDesa) {
			tsaClient = new TSAClientBouncyCastle(tsa_url, TSA_USERNAME, TSA_PASSWORD);
		}
		final byte[] encodedSig = this.sgn.getEncodedPKCS7(this.hash, this.cal, tsaClient, null, null,
				CryptoStandard.CMS);
		if (ESTIMATED_SIZE < encodedSig.length) {
			throw new IOException("Not enough space");
		}
		final byte[] paddedSig = new byte[ESTIMATED_SIZE];
		System.arraycopy(encodedSig, 0, paddedSig, 0, encodedSig.length);
		final PdfDictionary dic2 = new PdfDictionary();
		dic2.put(PdfName.CONTENTS, new PdfString(paddedSig).setHexWriting(true));
		this.sap.close(dic2);
		return this.fout.toByteArray();
	}

	private String campoValidoFirma(final PdfStamper stp, String fieldname)
			throws SinCamposFirmaException, CampoFirmadoException, CampoFirmaNoExisteException {
		final AcroFields formFields = stp.getAcroFields();
		if (fieldname == null) {
			fieldname = this.obtenerCampoDisponibleFirma(formFields);
		} else {
			validacionCampoFirma(formFields, fieldname);
		}
		return fieldname;
	}

	/**
	 * Valida si el campo indicado existe y está en blanco, disponible para
	 * firmar.
	 *
	 * @param formFields
	 *            : Listado de AcroFields del documento.
	 * @param fieldName
	 *            : Campo de Firma.
	 * @throws CampoFirmadoException
	 */
	private void validacionCampoFirma(final AcroFields formFields, final String fieldName)
			throws CampoFirmadoException, CampoFirmaNoExisteException {
		if (formFields.getField(fieldName) == null) {
			throw new CampoFirmaNoExisteException(fieldName);
		}
		if (formFields.getSignatureNames().contains(fieldName)) {
			throw new CampoFirmadoException(fieldName);
		}
	}

	/**
	 * Obtiene el próximo campo de firma disponible para firmar.
	 *
	 * @param formFields
	 *            : Listado de AcroFields del documento.
	 * @return String: nombre del campo.
	 * @throws SinCamposFirmaException
	 */
	private String obtenerCampoDisponibleFirma(final AcroFields formFields) throws SinCamposFirmaException {
		String campoFirma = null;
		final List<String> blankSignatures = formFields.getBlankSignatureNames();
		if (blankSignatures.size() < 1) {
			throw new SinCamposFirmaException("blankSignatures.size() < 1");
		}
		Collections.sort(blankSignatures);
		campoFirma = blankSignatures.get(0);
		return campoFirma;
	}

	/**
	 * Permite crear un Certificado para firmar el documento. Recibe un array de
	 * bytes
	 */
	public static Certificate[] crearCertificado(final byte[] certificadosEnBytes)
			throws CertificateException, IOException {
		final CertificateFactory cf = CertificateFactory.getInstance("X.509");
		final InputStream in = new ByteArrayInputStream(certificadosEnBytes);
		final Collection<? extends Certificate> certs = cf.generateCertificates(in);
		in.close();
		return certs.toArray(new Certificate[certs.size()]);
	}

	/**
	 * Actualiza los campos del PDF, esta lógica se encontraba en el applet
	 */
	public static ByteArrayInputStream actualizarCamposDelDocumento(final List<String> camposUsuario,
			final byte[] documento) throws SinCamposFirmaException, Exception {
		byte[] documentoCamposActualizado = null;
		ByteArrayInputStream documentoAFirmar = null;
		final ByteArrayInputStream documentoRecibido = new ByteArrayInputStream(documento);
		if (camposUsuario.size() != 0) {
			documentoCamposActualizado = actualizarCampoPdf(documentoRecibido, camposUsuario).toByteArray();
			documentoAFirmar = new ByteArrayInputStream(documentoCamposActualizado);
		} else {
			documentoAFirmar = documentoRecibido;
		}
		return documentoAFirmar;
	}

	/**
	 * Permite actualizar los campos correspondientes a Nombre, Cargo y
	 * Repartición que representan el sello del usuario
	 *
	 * @param fuente
	 *            : Archivo a actualizar.
	 * @return Flujo con los campos actualizados.
	 */
	public static ByteArrayOutputStream actualizarCampoPdf(final ByteArrayInputStream fuente,
			final List<String> camposUsuario) throws SinCamposFirmaException, Exception {
		PdfReader pdfReader;
		final ByteArrayOutputStream destino = new ByteArrayOutputStream();
		try {
			pdfReader = new PdfReader(fuente);
			final PdfStamper pdfStamper = new PdfStamper(pdfReader, destino, '\0', true);
			final AcroFields acroFields = pdfStamper.getAcroFields();
			final List<String> camposFirma = acroFields.getBlankSignatureNames();
			Collections.sort(camposFirma);
			final String campo = camposFirma.get(0);
			final String numeroFirma = campo.substring(campo.lastIndexOf("_") + 1, campo.length());
			final Map<String, String> campos = prepararCampos(numeroFirma, camposUsuario);
			final Set<String> acroKeys = acroFields.getFields().keySet();
			final Set<String> keys = campos.keySet();
			for (final String nombreCampo : keys) {
				for (final String acroCampo : acroKeys) {
					if (acroCampo.contains(nombreCampo)) {
						acroFields.setField(acroCampo, campos.get(nombreCampo));
						acroFields.setFieldProperty(acroCampo, "setfflags", PdfFormField.FF_READ_ONLY, null);
						break;
					}
				}
			}
			pdfStamper.close();
		} catch (final Exception ex) {
			throw new SinCamposFirmaException(
					"Error al actualizar los campos del Pdf" + " Descripción: " + camposUsuario, ex);
		}
		return destino;
	}

	/**
	 * Carga en un Mapa los datos del usuario,cargo y repartición del usuario.
	 * Sirve para firmaConjunta.
	 */
	private static Map<String, String> prepararCampos(final String numeroFirma, final List<String> camposUsuario) {
		final Map<String, String> camposSello = new HashMap<String, String>();
		camposSello.put("usuario_" + numeroFirma, camposUsuario.get(0));
		camposSello.put("cargo_" + numeroFirma, camposUsuario.get(1));
		camposSello.put("reparticion_" + numeroFirma, camposUsuario.get(2));
		return camposSello;
	}

	/**
	 * Carga en un Mapa los datos del usuario,cargo y repartición del usuario
	 * que rectifica.
	 */
	private static Map<String, String> prepararCampos(final List<String> camposUsuario) {
		final Map<String, String> camposSello = new HashMap<String, String>();
		camposSello.put(USUARIO_, camposUsuario.get(0));
		camposSello.put(CARGO_, camposUsuario.get(1));
		camposSello.put(REPARTICION_, camposUsuario.get(2));
		return camposSello;
	}

	/**
	 * Permite agregar los campos correspondientes a Nombre, Cargo y Repartición
	 * que representan el sello del usuario que rectifica el pdf.
	 *
	 * @param fuente
	 *            : Archivo a actualizar.
	 * @return Flujo con los campos actualizados.
	 */
	public static ByteArrayInputStream agregarDatosRectificante(final byte[] fuente, final List<String> camposUsuario)
			throws SinCamposFirmaException, Exception {
		PdfReader pdfReader;
		final ByteArrayOutputStream destino = new ByteArrayOutputStream();
		try {
			pdfReader = new PdfReader(fuente);
			final PdfStamper pdfStamper = new PdfStamper(pdfReader, destino, '\0', true);
			final AcroFields acroFields = pdfStamper.getAcroFields();
			final Map<String, String> campos = prepararCampos(camposUsuario);
			acroFields.setField(USUARIO_, campos.get(USUARIO_));
			acroFields.setFieldProperty(USUARIO_, "setfflags", PdfFormField.FF_READ_ONLY, null);
			acroFields.setField(CARGO_, campos.get(CARGO_));
			acroFields.setFieldProperty(CARGO_, "setfflags", PdfFormField.FF_READ_ONLY, null);
			acroFields.setField(REPARTICION_, campos.get(REPARTICION_));
			acroFields.setFieldProperty(REPARTICION_, "setfflags", PdfFormField.FF_READ_ONLY, null);
			pdfStamper.close();
		} catch (final Exception ex) {
			logger.error("Error desconocida." + " Descripción: " + ex);
			throw new SinCamposFirmaException("Error al insertar los datos del rectificante." + camposUsuario, ex);
		}
		return new ByteArrayInputStream(destino.toByteArray());
	}

	/**
	 * Permite obtener el nro de campo de firma. Se utiliza para firma conjunta.
	 */
	public static String obtenerSiguienteFirma(final byte[] fuente) {
		PdfReader reader = null;
		String result = null;
		try {
			reader = new PdfReader(fuente);
			final AcroFields acroFields = reader.getAcroFields();
			final List<String> camposFirma = acroFields.getBlankSignatureNames();
			int numeroMenor = Integer.MAX_VALUE;
			for (final String campo : camposFirma) {
				final String numeroCampo = campo.substring(campo.lastIndexOf("_") + 1, campo.length());
				if (esEntero(numeroCampo)) {
					final int numeroFirma = Integer.valueOf(numeroCampo);
					if (numeroFirma < numeroMenor) {
						numeroMenor = numeroFirma;
					}
				}
			}
			if (numeroMenor <= Integer.MAX_VALUE) {
				result = String.valueOf(numeroMenor);
			}
		} catch (final IOException e) {
			logger.error("Error al obtener el nombre del siguiente campo de firma");
		} finally {
			reader.close();
		}
		return result;
	}

	private static boolean esEntero(final String texto) {
		try {
			Integer.valueOf(texto);
		} catch (final NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
