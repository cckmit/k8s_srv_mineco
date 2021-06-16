package com.egoveris.te.base.model.tipo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tipos de Archivo de Trabajo
 *
 * @author everis
 *
 */
@Entity
@Table(name = "EE_TIPOS_ARCHIVO_TRABAJO")
public class TipoArchivoTrabajo {
	
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "DESCRIPCION")
  private String descripcion;

  @Column(name = "REPETIBLE")
  private boolean repetible;

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

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre
   *          the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the descripcion
   */
  public String getDescripcion() {
    return descripcion;
  }

  /**
   * @param descripcion
   *          the descripcion to set
   */
  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  /**
   * @return the repetible
   */
  public boolean isRepetible() {
    return repetible;
  }

  /**
   * @param repetible
   *          the repetible to set
   */
  public void setRepetible(boolean repetible) {
    this.repetible = repetible;
  }
  
}
