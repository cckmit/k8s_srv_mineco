package com.egoveris.ffdd.base.service.impl;

import java.io.ByteArrayInputStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.egoveris.shareddocument.base.model.WebDAVResourceBean;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.ffdd.base.service.IFileService;
import com.egoveris.ffdd.model.model.ArchivoDTO;

@Service
@Transactional
public class FileService implements IFileService {

 public static final String FFDD_FILES = "FFDD_Files/2016/";
	@Autowired
	private IWebDavService webDavService;

	@Override
	public ArchivoDTO getFile(final Integer transactionId, final String fileName) {
		//Refactor la ruta en la que se graba en el webdav
		final WebDAVResourceBean file = webDavService.getFile(FFDD_FILES + transactionId + "/" + fileName, "",
				"");
		final ArchivoDTO dto = new ArchivoDTO();
		dto.setNombre(fileName);
		dto.setData(file.getArchivo());
		return dto;
	}

	@Override
	public String saveFile(final Integer transactionId, final byte[] data, final String fileName) {
		webDavService.createSpace(null, "FFDD_Files", "", "");
		webDavService.createSpace("FFDD_Files", "2016", "", "");
		webDavService.createSpace(FFDD_FILES, transactionId.toString(), "", "");
		webDavService.createFile(FFDD_FILES + transactionId + "/", fileName, "", "",
				new ByteArrayInputStream(data));
		return null;
	}

}
