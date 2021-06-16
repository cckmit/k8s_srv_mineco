package com.egoveris.ccomplejos.base.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "view_mercanciada")
public class VistaMercanciaDA extends AbstractViewCComplejoJPA {
	

	@Column(name = "ID_PRODUCTO")
	Long producto;

//	@OneToMany(mappedBy = "vistaMercanciaDA")
//	private List<ItemJPA> items;
}