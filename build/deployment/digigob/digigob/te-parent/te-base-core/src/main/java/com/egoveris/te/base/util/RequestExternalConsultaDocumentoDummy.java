package com.egoveris.te.base.util;

import com.egoveris.sharedsecurity.base.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RequestExternalConsultaDocumentoDummy {
	
	
	private Usuario usuario;
	private List<String> numerosDocumento = new ArrayList<String>();
	private List<String> rectoras = new ArrayList<String>();
	private String numeroEspecial;

	public String getNumeroEspecial() {
		return this.numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}

	public void setNumerosDocumento(List<String> numerosDocumento) {
		this.numerosDocumento = numerosDocumento;
	}

	public List<String> getNumerosDocumento() {
		return numerosDocumento;
	}

	public void setRectoras(List<String> rectoras) {
		this.rectoras = rectoras;
	}

	public List<String> getRectoras() {
		return rectoras;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}
}
