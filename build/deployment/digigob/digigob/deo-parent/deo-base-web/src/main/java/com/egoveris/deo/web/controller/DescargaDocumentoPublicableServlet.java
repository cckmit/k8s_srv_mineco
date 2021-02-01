package com.egoveris.deo.web.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.zkoss.spring.SpringUtil;

import com.egoveris.deo.base.service.DocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.model.model.DocumentoDTO;

public class DescargaDocumentoPublicableServlet extends HttpServlet {

	private static final int ARBITARY_SIZE = 4096;
	/**
	 * 
	 */
	private static final long serialVersionUID = 3727947311464486587L;

	private DocumentoService documentoService;

	private GestionArchivosWebDavService gestionArchivosWebDavService;

	@Override
	public void init() throws ServletException {
		super.init();

		this.documentoService = (DocumentoService) SpringUtil.getApplicationContext().getBean("documentoServiceImpl");
		this.gestionArchivosWebDavService = (GestionArchivosWebDavService) SpringUtil.getApplicationContext()
				.getBean("gestionArchivosWebDavServiceImpl");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");

		DocumentoDTO documento = this.documentoService.findByIdPublicable(id);
		Assert.notNull(documento, "El id de documento publicable no existe no existe");

		resp.setContentType("application/pdf");
		resp.setHeader("Content-disposition", "attachment; filename=" + documento.getNumero() + ".pdf");

		try (BufferedInputStream in = this.gestionArchivosWebDavService.obtenerDocumento(documento.getNumero())
				.getFileAsStream(); OutputStream out = resp.getOutputStream()) {

			byte[] buffer = new byte[ARBITARY_SIZE];

			int numBytesRead;
			while ((numBytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, numBytesRead);
			}
		}
	}

}
