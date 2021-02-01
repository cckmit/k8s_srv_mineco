package com.egoveris.tica.base.util.sign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egoveris.tica.base.exception.TicaSignPdfException;
import com.egoveris.tica.base.model.FirmaInput;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfFormField;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.CertificateInfo.X500Name;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

public class TicaSignPdf {
	
	
	public static final String USUARIO_ = "usuario_";
	public static final String CARGO_ = "cargo_";
	public static final String REPARTICION_ = "reparticion_";
	public static final String SECTOR_ = "sector_";
	
	private static final Logger logger = LoggerFactory.getLogger(TicaSignPdf.class);

	/**
	 * Logger for this class
	 */
	
		
	private static final String FIRMADO_DIGITAL_DEL_DOCUMENTO = "Firmado digital del documento";
	
	private TicaSignPdf() {

	}

	/**
	 * Metodo que firma un pdf a partir de un arreglo de byte y retorna pdf
	 * firmado como arreglo de byte.
	 *
	 * @param firmaInput
	 * @return
	 * @throws TicaSignPdfException
	 */
	public static byte[] signPdfWithCertificate(final FirmaInput firmaInput, boolean importado) throws TicaSignPdfException {
		if (logger.isDebugEnabled()) {
			logger.debug("signPdfWithCertificate(firmaInput={}) - start", firmaInput);
		}

		PdfStamper stp = null;
		PdfReader reader = null;
		final ByteArrayOutputStream fout = new ByteArrayOutputStream();

		try (FileInputStream fileInput = new FileInputStream(firmaInput.getKeyStore())) {

			final byte[] contenido = firmaInput.getData();
			final String campoFirma = firmaInput.getSignatureFieldName();
			final BouncyCastleProvider provider = new BouncyCastleProvider();
			Security.addProvider(provider);
			final KeyStore ks = KeyStore.getInstance("jks");
			ks.load(fileInput, firmaInput.getPassword().toCharArray());

			final PrivateKey pk = (PrivateKey) ks.getKey(firmaInput.getAliasCert(),
					firmaInput.getPassword().toCharArray());
			final Certificate[] certs = ks.getCertificateChain(firmaInput.getAliasCert());
			reader = new PdfReader(new ByteArrayInputStream(contenido));
			stp = PdfStamper.createSignature(reader, fout, '\0', null, true);
			
			final PdfSignatureAppearance sap = stp.getSignatureAppearance();
			if (campoFirma.contains("cierre")) {
				sap.setVisibleSignature(campoFirma);
			} else {
				
				String firma = campoFirma.split("_")[1];
				cargarDatosSello(stp, USUARIO_,Integer.valueOf(firma),
						firmaInput.getUsuario());
				
				cargarDatosSello(stp, CARGO_,Integer.valueOf(firma),
						firmaInput.getCargo());
				
				cargarDatosSello(stp, SECTOR_,Integer.valueOf(firma),
						firmaInput.getSector());

				cargarDatosSello(stp, REPARTICION_,Integer.valueOf(firma),
						firmaInput.getOrganismo());
				
				sap.setVisibleSignature(campoFirma);
				sap.setRenderingMode(RenderingMode.DESCRIPTION);
				sap.setReason(FIRMADO_DIGITAL_DEL_DOCUMENTO);
				sap.setLocation(firmaInput.getLocation());

				Calendar cal = new GregorianCalendar();
				sap.setSignDate(cal);
			}
			sap.setLayer2Text(createLayer2(sap, certs[0], firmaInput.getLabelsFirma()));
			final ExternalSignature pks = new PrivateKeySignature(pk, firmaInput.getAlgoritmoHash(),
					provider.getName());
			final ExternalDigest digest = new BouncyCastleDigest();
			MakeSignature.signDetached(sap, digest, pks, certs, null, null, null, 0, CryptoStandard.CMS);
			byte[] returnbyteArray = fout.toByteArray();
			if (logger.isDebugEnabled()) {
				logger.debug("signPdfWithCertificate(FirmaInput) - end - return value={}", returnbyteArray);
			}
			return returnbyteArray;
		} catch (final KeyStoreException e) {
			logger.error("signPdfWithCertificate(FirmaInput)", e);

			throw new TicaSignPdfException("KeyStoreException ", e);
		} catch (final IOException e) {
			logger.error("signPdfWithCertificate(FirmaInput)", e);

			throw new TicaSignPdfException("IOException ", e);
		} catch (final DocumentException e) {
			logger.error("signPdfWithCertificate(FirmaInput)", e);

			throw new TicaSignPdfException("DocumentException ", e);
		} catch (final GeneralSecurityException e) {
			logger.error("signPdfWithCertificate(FirmaInput)", e);

			throw new TicaSignPdfException("GeneralSecurityException ", e);
		} catch (final Exception e) {
			logger.error("signPdfWithCertificate(FirmaInput)", e);

			throw new TicaSignPdfException("Exception ", e);
		}

	}
	
		private static String createLayer2(PdfSignatureAppearance sap, Certificate cert, Map<String, String> labelsFirma) {
		String ret;
		
		
        StringBuilder buf = new StringBuilder();
        buf.append(labelsFirma.get("tica.firma.FirmadoDigitalmentePor"));
        String name = null;
        X500Name x500name = CertificateInfo.getSubjectFields((X509Certificate)cert);
        if (x500name != null) {
        	name = x500name.getField("CN");
        	if (name == null)
        		name = x500name.getField("E");
        }
        if (name == null)
            name = "";
        buf.append(name).append('\n');
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");
        buf.append(labelsFirma.get("tica.firma.Fecha")).append(sd.format(sap.getSignDate().getTime()));
        if (sap.getReason() != null)
            buf.append('\n').append(labelsFirma.get("tica.firma.Razon")).append(sap.getReason());
        if (sap.getLocation() != null)
            buf.append('\n').append(labelsFirma.get("tica.firma.Localidad")).append(sap.getLocation());
        ret = buf.toString();
		
		return ret;
		
	}
	
	private static void cargarDatosSello(final PdfStamper pdfStamper,String sufijo ,int firma, String selloTexto) throws IOException, DocumentException {
		
    AcroFields acroFields = pdfStamper.getAcroFields();
    Set<String> acroKeys = acroFields.getFields().keySet();
    String campoSello = sufijo + firma;
    for (String acroCampo : acroKeys) {
        // Adicionar comentario explicativo
        if (acroCampo.equals(campoSello) ) {
          acroFields.setField(acroCampo, selloTexto);
          acroFields.setFieldProperty(acroCampo, "setfflags", PdfFormField.FF_READ_ONLY, null);
          break;
        }
      }
    
	}
	
}