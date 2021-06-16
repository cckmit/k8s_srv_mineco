package com.egoveris.te.base.model;

import java.io.Serializable;

public class DocumentoArchivoDeTrabajo  implements Serializable{

 private static final long serialVersionUID = 8254393623468018763L;
 private Integer orden;
 private String archivo;
 private String pathRelativo;
public Integer getOrden() {
	return orden;
}
public void setOrden(Integer orden) {
	this.orden = orden;
}
public String getArchivo() {
	return archivo;
}
public void setArchivo(String archivo) {
	this.archivo = archivo;
}
public String getPathRelativo() {
	return pathRelativo;
}
public void setPathRelativo(String pathRelativo) {
	this.pathRelativo = pathRelativo;
}
}
