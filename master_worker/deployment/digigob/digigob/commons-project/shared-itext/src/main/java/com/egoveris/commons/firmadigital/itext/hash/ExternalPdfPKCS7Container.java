package com.egoveris.commons.firmadigital.itext.hash;

import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalSignatureContainer;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

public class ExternalPdfPKCS7Container extends PdfPKCS7 implements ExternalSignatureContainer {

	private static final Logger logger = LoggerFactory.getLogger(ExternalPdfPKCS7Container.class);

	private byte[] sig;

	public ExternalPdfPKCS7Container(Certificate[] certChain, String digAlg, String hashAlgorithm, byte[] extSignature,
			byte[] hash, Calendar signDate, String tsa_url, String tsa_username, String tsa_password)
			throws InvalidKeyException, NoSuchProviderException, NoSuchAlgorithmException {
		super(null, certChain, hashAlgorithm, null, new BouncyCastleDigest(), false);
		super.setExternalDigest(extSignature, null, digAlg);
		// TSA CLIENT
		TSAClient tsaClient = buildTsaClient(tsa_url, tsa_username, tsa_password);

		sig = super.getEncodedPKCS7(hash, signDate, tsaClient, null, null, CryptoStandard.CMS);
	}

	private TSAClient buildTsaClient(String tsa_url, String tsa_username, String tsa_password) {
		
		TSAClient tsaClient = null;

		if (!StringUtils.isEmpty(tsa_url)) {
			tsaClient = new TSAClientBouncyCastle(tsa_url, tsa_username, tsa_password);
		} else {
			logger.warn("No tiene url de TSA configurada. Se firma sin TSA");
		}
		return tsaClient;
	}

	public byte[] sign(InputStream is) {
		return sig;
	}

	public void modifySigningDictionary(PdfDictionary signDic) {
	}
}
