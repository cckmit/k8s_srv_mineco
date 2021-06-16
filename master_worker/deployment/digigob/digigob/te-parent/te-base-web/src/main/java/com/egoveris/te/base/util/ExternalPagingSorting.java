package com.egoveris.te.base.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.ext.Paginal;

public class ExternalPagingSorting {

  private static Logger logger = LoggerFactory.getLogger(ExternalPagingSorting.class);

  @Autowired
  private ProcessEngine processEngine;
  private Listbox listBox;
  private Paginal paginador;
  private List<?> datos;
  private String criterio;
  private String orden;
  private ConsultaData consultaData;
  // Nombre del método que carga los datos en la implementación del servicio.
  private String nombreMetodo;
  // Instancia del servicio utilizado para interactuar con la capa de negocio.
  private Object objetoModelo;
  // Maps que contiene los parámetros de consulta.
  private Map<String, Object> parametrosConsulta;
  private String username;

  public ExternalPagingSorting(Listbox listBox, Paginal paginador) {

    this.listBox = listBox;
    this.paginador = paginador;
    this.adicionarListeners();
  }

  /**
   * Adiciona listeners para escuchar eventos de paginación y de ordenamiento.
   */
  private void adicionarListeners() {
    Listhead listhead = listBox.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if (lheader.getSortAscending() != null || lheader.getSortDescending() != null)
          lheader.addEventListener("onSort", new ExternalPagingListener(this));
      }
    }
    paginador.addEventListener("onPaging", new ExternalPagingListener(this));
  }

  public List<?> cargarDatos() {
    Integer inicioCarga = paginador.getActivePage() * paginador.getPageSize();
    Integer tamanoPaginacion = paginador.getPageSize();
    Integer iTotalSize = paginador.getTotalSize();

    try {
      Class<?> clase = objetoModelo.getClass();

      Class<?>[] parametros = new Class[2];
      parametros[0] = Map.class;
      parametros[1] = Map.class;

      Map<String, String> parametrosOrden = new HashMap<String, String>();
      parametrosOrden.put("inicioCarga", String.valueOf(inicioCarga));
      parametrosOrden.put("seleccionNumPagina", String.valueOf(paginador.getActivePage()));
      parametrosOrden.put("tamanoPaginacion", String.valueOf(tamanoPaginacion));
      parametrosOrden.put("criterio", criterio);
      parametrosOrden.put("orden", orden);
      parametrosOrden.put("iTotalSize", String.valueOf(iTotalSize));
      Object[] arglist = new Object[] { this.parametrosConsulta, parametrosOrden };
      Method metodo = clase.getMethod(nombreMetodo, parametros);
      datos = (List<?>) metodo.invoke(objetoModelo, arglist);

    } catch (Exception e) {
      logger.error("error al cargar datos", e);
    }
    return datos;
  }

  public void limpiarIndicadoresOrden(Listheader listHeaderActual) {
    Listhead listhead = listBox.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if (lheader != listHeaderActual)
          lheader.setSortDirection("natural");
      }
    }
  }

  public String getCriterio() {
    return criterio;
  }

  public void setCriterio(String criterio) {
    this.criterio = criterio;
  }

  public Listbox getListBox() {
    return listBox;
  }

  public void setListBox(Listbox listBox) {
    this.listBox = listBox;
  }

  public Paginal getPaginador() {
    return paginador;
  }

  public void setPaginador(Paginal paginador) {
    this.paginador = paginador;
  }

  public Map<String, Object> getParametrosConsulta() {
    return parametrosConsulta;
  }

  public void setParametrosConsulta(Map<String, Object> parametrosConsulta) {
    this.parametrosConsulta = parametrosConsulta;
  }

  public String getOrden() {
    return orden;
  }

  public void setOrden(String orden) {
    this.orden = orden;
  }

  public ConsultaData getConsultaData() {
    return consultaData;
  }

  public void setConsultaData(ConsultaData consultaData) {
    this.consultaData = consultaData;
  }

  public List<?> getDatos() {
    return datos;
  }

  public void setDatos(List<?> datos) {
    this.datos = datos;
  }

  public String getNombreMetodo() {
    return nombreMetodo;
  }

  public void setNombreMetodo(String nombreMetodo) {
    this.nombreMetodo = nombreMetodo;
  }

  public Object getObjetoModelo() {
    return objetoModelo;
  }

  public void setObjetoModelo(Object objetoModelo) {
    this.objetoModelo = objetoModelo;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

}

/**
 * Listener que escucha los eventos de paginación y ordenamiento.
 * 
 * 
 * 
 */

final class ExternalPagingListener implements EventListener {
  private ExternalPagingSorting extPag;

  public ExternalPagingListener(ExternalPagingSorting extPag) {
    this.extPag = extPag;
  }

  public void onEvent(Event event) throws Exception {

    event.stopPropagation();
    if (event.getName().equals(Events.ON_SORT)) {

      Listheader lh = (Listheader) event.getTarget();
      if (lh.getSortDirection().compareTo("ascending") == 0) {
        lh.setSortDirection("descending");

      } else {
        lh.setSortDirection("ascending");
      }

      extPag.setCriterio(lh.getId());
      extPag.setOrden(lh.getSortDirection());
      extPag.limpiarIndicadoresOrden(lh);
      extPag.cargarDatos();

    }
    if (event.getName().equals(ZulEvents.ON_PAGING)) {
      extPag.setDatos(extPag.cargarDatos());
    }
    Events.sendEvent(extPag.getListBox(), new Event(Events.ON_NOTIFY));
  }
}
