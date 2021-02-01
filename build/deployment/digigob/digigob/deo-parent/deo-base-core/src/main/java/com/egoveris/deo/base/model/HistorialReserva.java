package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GEDO_HIST_VISUALIZACION")
public class HistorialReserva {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="documento")
	private String documento;
	
	@Column(name="id_documento")
	private Integer idDocumento;
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="sector")
	private String sector;
	
	@Column(name="reparticion")
	private String reparticion;
	
	@Column(name="reparticion_rectora")
	private String reparticionRectora;
	
	@Column(name="fecha_alta")
	private Date fechaAlta;
	
	public HistorialReserva(Integer id,String documento, String usuario, String sector,
			String reparticion, String reparticionRectora, Integer idDocumento, Date fechaAlta) {
		super();
		this.id=id;
		this.documento = documento;
		this.usuario = usuario;
		this.sector = sector;
		this.reparticion = reparticion;
		this.reparticionRectora = reparticionRectora;
		this.idDocumento = idDocumento;
		this.fechaAlta = fechaAlta;
	}
	
	public HistorialReserva(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getReparticionRectora() {
		return reparticionRectora;
	}

	public void setReparticionRectora(String reparticionRectora) {
		this.reparticionRectora = reparticionRectora;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HistorialReserva) {
			HistorialReserva hist = (HistorialReserva) obj;
			if (hist.getDocumento().compareTo(this.documento) == 0
					&& ((hist.getReparticionRectora() == null && this.reparticionRectora == null)
							|| hist.getReparticionRectora() != null
									&& hist.getReparticionRectora().equals(this.reparticionRectora))) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		 return super.hashCode();
	}
	
}
