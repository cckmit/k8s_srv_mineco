package com.egoveris.vucfront.base.service;

import com.egoveris.vucfront.model.model.DocumentoDTO;

import java.util.List;
import java.util.Map;

public interface WebDavService {

  /**
   * Upload a list of documents to WebDav VUC/currentYear/currentMonth/. The
   * document to be saved must be a recently uploaded document (has an array of
   * bytes).
   * 
   * @param documentList
   * @param idExpediente
   *          to distinguish the files in the system.
   * @return
   */
  Map<String, String> uploadDocuments(List<DocumentoDTO> documentList);

  /**
   * Delete a document from WebDav.
   * 
   * @param documento
   */
  void deleteDocument(DocumentoDTO documento);

  /**
   * Get's the array of bytes of the Document saved in WebDav.
   * 
   * @param fullFilePath
   *          E.G.: /VUC/2017/5/424234archivo.pdf
   * @return
   */
  byte[] getDocumentoByteArray(String fullFilePath);
}