package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;

public class ResponseExternalBuscarDocumentos implements Serializable {

  private static final long serialVersionUID = -2889151521957160606L;
  private int totalSize;
  private boolean equals;
  private int indexSortColumn;
  private List<DocumentoSolrResponse> content;

  public void setIndexSortColumn(int indexSortColumn) {
    this.indexSortColumn = indexSortColumn;
  }

  public void setEquals(boolean equals) {
    this.equals = equals;
  }

  public void setFoot(int totalSize) {
    this.totalSize = totalSize;
  }

  public void setDocumentos(List<DocumentoSolrResponse> content) {
    this.content = content;
  }

  public boolean getEquals() {
    return equals;
  }

  public int getIndexSortColumn() {
    return indexSortColumn;
  }

  public List<DocumentoSolrResponse> getContent() {
    return content;
  }

  public void setTotalSize(int totalSize) {
    this.totalSize = totalSize;
  }

  public int getTotalSize() {
    return totalSize;
  }

}
