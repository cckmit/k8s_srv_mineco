package com.egoveris.te.base.model.tipo;

import com.egoveris.te.base.model.trata.Trata;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_RESERVA")
public class TipoReserva {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "TIPO_RESERVA")
	private String tipoReserva;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@OneToMany(mappedBy = "tipoReserva", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Trata> listTrata;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the tipoReserva
	 */
	public String getTipoReserva() {
		return tipoReserva;
	}

	/**
	 * @param tipoReserva
	 *            the tipoReserva to set
	 */
	public void setTipoReserva(String tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the listTrata
	 */
	public Set<Trata> getListTrata() {
		return listTrata;
	}

	/**
	 * @param listTrata
	 *            the listTrata to set
	 */
	public void setListTrata(Set<Trata> listTrata) {
		this.listTrata = listTrata;
	}

}
