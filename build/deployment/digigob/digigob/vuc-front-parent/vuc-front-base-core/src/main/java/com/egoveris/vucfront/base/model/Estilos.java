package com.egoveris.vucfront.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_ESTILOS")
public class Estilos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigo;
	private String descripcion;
	private String logo;
	
	@Column(name="COLOR_HEADER")
	private String colorHeader;
	
	@Column(name="COLOR_BOTONES_1")
	private String colorBoton1;
	@Column(name="COLOR_BOTONES_2")
	private String colorBoton2;
	@Column(name="COLOR_IND_BORRADOR")
	private String colorIndBorrador;
	@Column(name="COLOR_IND_PROCESO")
	private String colorIndProceso;
	@Column(name="COLOR_IND_TAREAS")
	private String colorIndTareas;
	@Column(name="COLOR_IND_NOTIFICACIONES")
	private String colorIndNotificaciones;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getColorHeader() {
		return colorHeader;
	}
	public void setColorHeader(String colorHeader) {
		this.colorHeader = colorHeader;
	}
	public String getColorBoton1() {
		return colorBoton1;
	}
	public void setColorBoton1(String colorBoton1) {
		this.colorBoton1 = colorBoton1;
	}
	public String getColorBoton2() {
		return colorBoton2;
	}
	public void setColorBoton2(String colorBoton2) {
		this.colorBoton2 = colorBoton2;
	}
	public String getColorIndBorrador() {
		return colorIndBorrador;
	}
	public void setColorIndBorrador(String colorIndBorrador) {
		this.colorIndBorrador = colorIndBorrador;
	}
	public String getColorIndProceso() {
		return colorIndProceso;
	}
	public void setColorIndProceso(String colorIndProceso) {
		this.colorIndProceso = colorIndProceso;
	}
	public String getColorIndTareas() {
		return colorIndTareas;
	}
	public void setColorIndTareas(String colorIndTareas) {
		this.colorIndTareas = colorIndTareas;
	}
	public String getColorIndNotificaciones() {
		return colorIndNotificaciones;
	}
	public void setColorIndNotificaciones(String colorIndNotificaciones) {
		this.colorIndNotificaciones = colorIndNotificaciones;
	}
	

}
