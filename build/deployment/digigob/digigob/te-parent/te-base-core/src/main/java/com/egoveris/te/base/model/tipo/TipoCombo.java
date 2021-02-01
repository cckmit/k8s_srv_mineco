
package com.egoveris.te.base.model.tipo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DATOS_VARIABLES_COMBOS")
public class TipoCombo  implements Serializable
{	
	private static final long serialVersionUID = 7358136192913860115L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ID_GRUPO_COMBO")
	private Long grupo;
	
	@Column(name = "VALOR_OPCION_COMBO")
	private String valorOpcion;
	
	@Column(name = "FECHA_BAJA")
	private Date fechaBaja;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGrupo() {
    return grupo;
  }

  public void setGrupo(Long grupo) {
    this.grupo = grupo;
  }

  public String getValorOpcion() {
    return valorOpcion;
  }

  public void setValorOpcion(String valorOpcion) {
    this.valorOpcion = valorOpcion;
  }

  public Date getFechaBaja() {
    return fechaBaja;
  }

  public void setFechaBaja(Date fechaBaja) {
    this.fechaBaja = fechaBaja;
  }


 
}
