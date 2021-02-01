package com.egoveris.te.base.model.tipo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.egoveris.te.base.model.Operacion;

@Entity
@Table(name = "TE_TIPO_OPERACION")
public class TipoOperacion implements Serializable {

  private static final long serialVersionUID = -5514896734289249519L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "DBID_")
  private Long workflow;

  @Column(name = "CODIGO")
  private String codigo;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "ESTADO")
  private Boolean estado;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoOperacion")
  private List<Operacion> operacion;

  @OneToMany(mappedBy = "tipoOperacion", fetch = FetchType.EAGER)
  @Fetch(FetchMode.SELECT)
  private List<TipoOperacionDocumento> tiposOpDocumento;

  @OneToMany(mappedBy = "tipoOperacion", fetch = FetchType.EAGER)
  @Fetch(FetchMode.SELECT)
  private List<TipoOperacionOrganismo> organismos;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  /**
   * @return the workflow
   */
  public Long getWorkflow() {
    return workflow;
  }

  /**
   * @param workflow
   *          the workflow to set
   */
  public void setWorkflow(Long workflow) {
    this.workflow = workflow;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public List<Operacion> getOperacion() {
    return operacion;
  }

  public void setOperacion(List<Operacion> operacion) {
    this.operacion = operacion;
  }

  public List<TipoOperacionDocumento> getTiposOpDocumento() {
    return tiposOpDocumento;
  }

  public void setTiposOpDocumento(List<TipoOperacionDocumento> tiposOpDocumento) {
    this.tiposOpDocumento = tiposOpDocumento;
  }

  public List<TipoOperacionOrganismo> getOrganismos() {
    return organismos;
  }

  public void setOrganismos(List<TipoOperacionOrganismo> organismos) {
    this.organismos = organismos;
  }

}
