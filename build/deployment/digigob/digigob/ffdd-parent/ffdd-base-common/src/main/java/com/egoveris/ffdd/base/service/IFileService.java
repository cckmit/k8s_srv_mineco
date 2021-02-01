package com.egoveris.ffdd.base.service;

import com.egoveris.ffdd.model.model.ArchivoDTO;

public interface IFileService {
  /**
   * Gets the file by name for a given transactionID
   * 
   * @param transactionId
   * @param fileName
   * @return
   */
  public ArchivoDTO getFile(Integer transactionId, String fileName);

  /**
   * Stores the file for the transaction. If a file with same name exists for
   * the transattions is overwriten.
   * 
   * @param transactionId
   * @param data
   * @param fileName
   * @return
   */
  public String saveFile(Integer transactionId, byte[] data, String fileName);

}
