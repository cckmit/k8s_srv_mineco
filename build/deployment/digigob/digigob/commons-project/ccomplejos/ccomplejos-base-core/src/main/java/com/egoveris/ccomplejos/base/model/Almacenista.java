package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_ALMACENISTA")
public class Almacenista extends AbstractCComplejoJPA {

	@Column(name = "ALMACENISTA")
	protected String almacenista;

	@Column(name = "FECHA_RECEPCION_MERCANCIAS")
	protected Date fechaRecepcionMercancias;

	@Column(name = "FECHA_RETIRO_MERCANCIAS")
	protected Date fechaRetiroMercancias;

	@Column(name = "NUMERO_REGISTRO_RECONOCIMIENTO")
	protected String numeroRegistroReconocimiento;

	@Column(name = "ANO_REGISTRO_RECONOCIMIENTO")
	protected String anoRegistroReconocimiento;

	@Column(name = "CODIGO_REGLA_UNO_PROCEDIMIENTO_AFORO")
	protected String codigoReglaUnoProcedimientoAforo;

	@Column(name = "NUMERO_RESOLUCION_REGLA_UNO")
	protected String numeroResolucionReglaUno;

	@Column(name = "ANO_RESOLUCION_REGLA_UNO")
	protected String anoResolucionReglaUno;

	@Column(name = "CODIGO_ULTIMA_RESOLUCION_REGLA_UNO")
	protected String codigoUltimaResolucionReglaUno;

	public String getAlmacenista() {
		return almacenista;
	}

	public void setAlmacenista(String almacenista) {
		this.almacenista = almacenista;
	}

	public Date getFechaRecepcionMercancias() {
		return fechaRecepcionMercancias;
	}

	public void setFechaRecepcionMercancias(Date fechaRecepcionMercancias) {
		this.fechaRecepcionMercancias = fechaRecepcionMercancias;
	}

	public Date getFechaRetiroMercancias() {
		return fechaRetiroMercancias;
	}

	public void setFechaRetiroMercancias(Date fechaRetiroMercancias) {
		this.fechaRetiroMercancias = fechaRetiroMercancias;
	}

	public String getNumeroRegistroReconocimiento() {
		return numeroRegistroReconocimiento;
	}

	public void setNumeroRegistroReconocimiento(String numeroRegistroReconocimiento) {
		this.numeroRegistroReconocimiento = numeroRegistroReconocimiento;
	}

	public String getAnoRegistroReconocimiento() {
		return anoRegistroReconocimiento;
	}

	public void setAnoRegistroReconocimiento(String anoRegistroReconocimiento) {
		this.anoRegistroReconocimiento = anoRegistroReconocimiento;
	}

	public String getCodigoReglaUnoProcedimientoAforo() {
		return codigoReglaUnoProcedimientoAforo;
	}

	public void setCodigoReglaUnoProcedimientoAforo(String codigoReglaUnoProcedimientoAforo) {
		this.codigoReglaUnoProcedimientoAforo = codigoReglaUnoProcedimientoAforo;
	}

	public String getNumeroResolucionReglaUno() {
		return numeroResolucionReglaUno;
	}

	public void setNumeroResolucionReglaUno(String numeroResolucionReglaUno) {
		this.numeroResolucionReglaUno = numeroResolucionReglaUno;
	}

	public String getAnoResolucionReglaUno() {
		return anoResolucionReglaUno;
	}

	public void setAnoResolucionReglaUno(String anoResolucionReglaUno) {
		this.anoResolucionReglaUno = anoResolucionReglaUno;
	}

	public String getCodigoUltimaResolucionReglaUno() {
		return codigoUltimaResolucionReglaUno;
	}

	public void setCodigoUltimaResolucionReglaUno(String codigoUltimaResolucionReglaUno) {
		this.codigoUltimaResolucionReglaUno = codigoUltimaResolucionReglaUno;
	}

}
