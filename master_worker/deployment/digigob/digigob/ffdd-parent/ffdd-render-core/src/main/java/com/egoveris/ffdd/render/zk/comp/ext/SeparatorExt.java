package com.egoveris.ffdd.render.zk.comp.ext;

import com.egoveris.ffdd.render.model.ComponentZkExt;

import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Auxheader;

public class SeparatorExt extends Auxhead implements ComponentZkExt {

	private static final long serialVersionUID = 5378339918930005318L;

	private Integer idComponentForm;
	private String label;
	private String name;
	private Integer repetidos = 0;
	private boolean sepInterno;
	private boolean sepRepetitivo;

	public static final String IMAGENES_MAS_PNG = "~./mas.png";
	public static final String IMAGENES_MENOS_PNG = "~./menos.png";

	public SeparatorExt() {
	}

	public SeparatorExt(Integer idCompForm, String nombre, String label, boolean sepRepetitivo, boolean sepInterno) {
		setIdComponentForm(idCompForm);
		this.name = nombre;
		this.sepInterno = sepInterno;
		this.sepRepetitivo = sepRepetitivo;
		this.setLabel(label);
		Auxheader header = new Auxheader();
		header.setLabel(label);
		header.setColspan(2);
		header.setParent(this);
		if (sepInterno) {
			header.setRowspan(2);
		}
	}

	public SeparatorExt(Integer idCompForm, String nombre, String label) {
		 this(idCompForm, nombre, label, false, false);
	}

	@Override
	public Integer getIdComponentForm() {
		return idComponentForm;
	}

	@Override
	public void setIdComponentForm(Integer idComponentForm) {
		this.idComponentForm = idComponentForm;
	}

	public Integer getRepetidos() {
		return repetidos;
	}

	public void setRepetidos(Integer repetidos) {
		this.repetidos = repetidos;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSepInterno() {
		return sepInterno;
	}

	public boolean isSepRepetitivo() {
		return sepRepetitivo;
	}

}
