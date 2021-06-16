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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
//import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERUTCTime;

import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmaNoExisteException;
import com.egoveris.commons.firmadigital.itext.excepciones.CampoFirmadoException;
import com.egoveris.commons.firmadigital.itext.excepciones.SinCamposFirmaException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalBlankSignatureContainer;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.SecurityIDs;

public abstract class SignTramelecPdfv2 {

	private static final int ESTIMATED_SIZE = 8192 + 4192;

	public static PrepararFirmaOutDTO prepararFirma(PrepararFirmaDTO signDTO) throws DocumentException, IOException,
			SinCamposFirmaException, CampoFirmadoException, CampoFirmaNoExisteException, GeneralSecurityException {
		PdfReader reader = new PdfReader(signDTO.pdfDatas);
		ByteArrayOutputStream fout = new ByteArrayOutputStream();
		PdfStamper stamper = PdfStamper.createSignature(reader, fout, '\0');
		// actualiza los campos de sello
		actualizarCamposPdf(stamper, signDTO.mapCamposPdf);
		// arma la firma
		PdfSignatureAppearance psa = buildSignature(stamper, signDTO.fieldname, signDTO.certs, signDTO.location);
		ExternalSignatureContainer external = new ExternalBlankSignatureContainer(PdfName.ADOBE_PPKLITE,
				PdfName.ADBE_PKCS7_DETACHED);
		MakeSignature.signExternalContainer(psa, external, ESTIMATED_SIZE);

		BouncyCastleDigest digest = new BouncyCastleDigest();
		byte[] hash = DigestAlgorithms.digest(psa.getRangeStream(), digest.getMessageDigest(signDTO.msgDigAlg));

		return new PrepararFirmaOutDTO(hash, getAuthenticatedAttributeBytes(hash, psa.getSignDate()),
				fout.toByteArray(), psa.getSignDate());
	}

	private static byte[] getAuthenticatedAttributeBytes(byte secondDigest[], Calendar signingTime) {
		try {
			ASN1EncodableVector attribute = new ASN1EncodableVector();
			ASN1EncodableVector v = new ASN1EncodableVector();
			v.add(new ASN1ObjectIdentifier(SecurityIDs.ID_CONTENT_TYPE));
			v.add(new DERSet(new ASN1ObjectIdentifier(SecurityIDs.ID_PKCS7_DATA)));
			attribute.add(new DERSequence(v));
			v = new ASN1EncodableVector();
			v.add(new ASN1ObjectIdentifier(SecurityIDs.ID_SIGNING_TIME));
			v.add(new DERSet(new DERUTCTime(signingTime.getTime())));
			attribute.add(new DERSequence(v));
			v = new ASN1EncodableVector();
			v.add(new ASN1ObjectIdentifier(SecurityIDs.ID_MESSAGE_DIGEST));
			v.add(new DERSet(new DEROctetString(secondDigest)));
			attribute.add(new DERSequence(v));
			return new DERSet(attribute).getEncoded(ASN1Encoding.DER);
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	private static PdfSignatureAppearance buildSignature(PdfStamper stp, String fieldName, Certificate[] certs,
			String location) throws SinCamposFirmaException, CampoFirmadoException, CampoFirmaNoExisteException {
		validacionCampoFirma(stp.getAcroFields(), fieldName);
		PdfSignatureAppearance sap = stp.getSignatureAppearance();
		sap.setLocation(location);
		sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
		sap.setVisibleSignature(fieldName);
		sap.setCertificate(certs[0]);
		return sap;
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
	private static void validacionCampoFirma(AcroFields formFields, String fieldName) throws CampoFirmadoException,
			CampoFirmaNoExisteException {
		if (formFields.getField(fieldName) == null) {
			throw new CampoFirmaNoExisteException(fieldName);
		}
		if (formFields.getSignatureNames().contains(fieldName)) {
			throw new CampoFirmadoException(fieldName);
		}
	}

	/**
	 * Permite crear un Certificado para firmar el documento. Recibe un array de
	 * bytes
	 */
	public static Certificate[] crearCertificado(byte[] certificadosEnBytes) throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream in = new ByteArrayInputStream(certificadosEnBytes);
		Collection<? extends Certificate> certs = cf.generateCertificates(in);
		in.close();
		return certs.toArray(new Certificate[certs.size()]);
	}
	
	public static byte[] actualizarCamposPdf(byte[] data, Map<String, String> mapCamposPdf) throws IOException, DocumentException{
		PdfReader reader = new PdfReader(data);
		ByteArrayOutputStream fout = new ByteArrayOutputStream();
		PdfStamper pdfStamper = new PdfStamper(reader, fout, '\0', true);
		actualizarCamposPdf(pdfStamper, mapCamposPdf);
		pdfStamper.close();
		return fout.toByteArray();
		
	}

	private static void actualizarCamposPdf(PdfStamper pdfStamper, Map<String, String> mapCamposPdf)
			throws IOException, DocumentException {
		AcroFields acroFields = pdfStamper.getAcroFields();
		Set<Entry<String, String>> setEntry = mapCamposPdf.entrySet();
		for (Entry<String, String> entry : setEntry) {
			if (acroFields.setField(entry.getKey(), entry.getValue())) {
				//throw new DocumentException("No encontro el campo " + entry.getKey());
			}
			acroFields.setFieldProperty(entry.getKey(), "setfflags", PdfFormField.FF_READ_ONLY, null);
		}
	}

	public static class PrepararFirmaOutDTO {
		private byte[] attributeBytesToSign;
		private byte[] pdfWithEmptySignature;
		private byte[] hash;
		private Calendar signDate;

		public PrepararFirmaOutDTO() {
			super();
		}

		public PrepararFirmaOutDTO(byte[] hash, byte[] attributeBytesToSign, byte[] pdfWithEmptySignature,
				Calendar signDate) {
			this.hash = hash;
			this.signDate = signDate;
			this.attributeBytesToSign = attributeBytesToSign;
			this.pdfWithEmptySignature = pdfWithEmptySignature;
		}

    public byte[] getAttributeBytesToSign() {
      return attributeBytesToSign;
    }

    public void setAttributeBytesToSign(byte[] attributeBytesToSign) {
      this.attributeBytesToSign = attributeBytesToSign;
    }

    public byte[] getPdfWithEmptySignature() {
      return pdfWithEmptySignature;
    }

    public void setPdfWithEmptySignature(byte[] pdfWithEmptySignature) {
      this.pdfWithEmptySignature = pdfWithEmptySignature;
    }

    public byte[] getHash() {
      return hash;
    }

    public void setHash(byte[] hash) {
      this.hash = hash;
    }

    public Calendar getSignDate() {
      return signDate;
    }

    public void setSignDate(Calendar signDate) {
      this.signDate = signDate;
    }
		
		
	}

	public static class PrepararFirmaDTO {
		private byte[] pdfDatas;
		private String msgDigAlg;
		private String fieldname;
		private Certificate[] certs;
		private String location;
		private Map<String, String> mapCamposPdf;

		public PrepararFirmaDTO(byte[] pdfDatas, String msgDigAlg, String fieldname, Certificate[] certs,
				String location, Map<String, String> mapCamposPdf) {
			this.pdfDatas = pdfDatas;
			this.msgDigAlg = msgDigAlg;
			this.fieldname = fieldname;
			this.certs = certs;
			this.location = location;
			this.mapCamposPdf = mapCamposPdf;
		}

    public byte[] getPdfDatas() {
      return pdfDatas;
    }

    public void setPdfDatas(byte[] pdfDatas) {
      this.pdfDatas = pdfDatas;
    }

    public String getMsgDigAlg() {
      return msgDigAlg;
    }

    public void setMsgDigAlg(String msgDigAlg) {
      this.msgDigAlg = msgDigAlg;
    }

    public String getFieldname() {
      return fieldname;
    }

    public void setFieldname(String fieldname) {
      this.fieldname = fieldname;
    }

    public Certificate[] getCerts() {
      return certs;
    }

    public void setCerts(Certificate[] certs) {
      this.certs = certs;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public Map<String, String> getMapCamposPdf() {
      return mapCamposPdf;
    }

    public void setMapCamposPdf(Map<String, String> mapCamposPdf) {
      this.mapCamposPdf = mapCamposPdf;
    }
		
		
	}

	public static class FirmaDTO {
	  private byte[] pdfDatas;
	  private byte[] hashFirmado;
		private String hashAlg;
		private String sigAlg;
		private String fieldname;
		private Certificate[] certs;
		private byte[] hash;
		private Calendar signDate;
		private String tsa_url;
		private String tsa_username;
		private String tsa_password;

		public FirmaDTO(byte[] pdfDatas, byte[] hashFirmado, String sigAlg, String hashAlg, String fieldname,
				Certificate[] certs, byte[] hash, Calendar signDate, String tsa_url, String tsa_username,
				String tsa_password) {
			this.pdfDatas = pdfDatas;
			this.hashFirmado = hashFirmado;
			this.hash = hash;
			this.signDate = signDate;
			this.hashAlg = hashAlg;
			this.sigAlg = sigAlg;
			this.fieldname = fieldname;
			this.certs = certs;
			this.tsa_url = tsa_url;
			this.tsa_username = tsa_username;
			this.tsa_password = tsa_password;
		}

    public byte[] getPdfDatas() {
      return pdfDatas;
    }

    public void setPdfDatas(byte[] pdfDatas) {
      this.pdfDatas = pdfDatas;
    }

    public byte[] getHashFirmado() {
      return hashFirmado;
    }

    public void setHashFirmado(byte[] hashFirmado) {
      this.hashFirmado = hashFirmado;
    }

    public String getHashAlg() {
      return hashAlg;
    }

    public void setHashAlg(String hashAlg) {
      this.hashAlg = hashAlg;
    }

    public String getSigAlg() {
      return sigAlg;
    }

    public void setSigAlg(String sigAlg) {
      this.sigAlg = sigAlg;
    }

    public String getFieldname() {
      return fieldname;
    }

    public void setFieldname(String fieldname) {
      this.fieldname = fieldname;
    }

    public Certificate[] getCerts() {
      return certs;
    }

    public void setCerts(Certificate[] certs) {
      this.certs = certs;
    }

    public byte[] getHash() {
      return hash;
    }

    public void setHash(byte[] hash) {
      this.hash = hash;
    }

    public Calendar getSignDate() {
      return signDate;
    }

    public void setSignDate(Calendar signDate) {
      this.signDate = signDate;
    }

    public String getTsa_url() {
      return tsa_url;
    }

    public void setTsa_url(String tsa_url) {
      this.tsa_url = tsa_url;
    }

    public String getTsa_username() {
      return tsa_username;
    }

    public void setTsa_username(String tsa_username) {
      this.tsa_username = tsa_username;
    }

    public String getTsa_password() {
      return tsa_password;
    }

    public void setTsa_password(String tsa_password) {
      this.tsa_password = tsa_password;
    }
		
		
	}
}
