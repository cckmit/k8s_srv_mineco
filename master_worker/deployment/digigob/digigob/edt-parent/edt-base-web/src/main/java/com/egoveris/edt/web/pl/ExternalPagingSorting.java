package com.egoveris.edt.web.pl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.sharedsecurity.base.model.ConstantesSesion;

/**
 * Clase helper que implementa la paginación y ordenamiento a través de métodos
 * de negocio.
 * 
 * Esta clase a través de los servicios, invoca un método de la capa de negocio
 * el cual debe devolver una lista de objetos de acuerdo a la paginación y
 * ordenamiento definidos en la vista.
 * 
 * Se requieren diferentes instancias de esta clase, para paginar y ordenar
 * externamente listbox definidas en la misma ventana.
 * 
 * Se deben definir los parámetros de consulta, y los parámetros de orden en un
 * HashMap, de esta manera se podrá invocar dinámicamente métodos con diferentes
 * parámetros de consulta.
 * 
 * Para ejemplo de utilización, ver implementación visualización de avisos,
 * inboxComposer.java.
 * 
 * @author kmarroqu
 * 
 */

public class ExternalPagingSorting {

	
  private static Logger logger = LoggerFactory.getLogger(ExternalPagingSorting.class);
  private Listbox listBox;
  private Paginal paginador;
  private List<?> datos;
  private String criterio;
  private String orden;
  // private ConsultaData consultaData;

  // Nombre del método que carga los datos en la implementación del servicio.
  private String nombreMetodo;
  // Instancia del servicio utilizado para interactuar con la capa de negocio.
  private Object objetoModelo;
  // Maps que contiene los parámetros de consulta.
  private Map<String, Object> parametrosConsulta;

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

  /**
   * Restablece los íconos de ordenamiento de los encabezados, para que solo se
   * indique el ordenamiento de la última columna por la cual se ordenó.
   * 
   * @param listHeaderActual
   *          ListHeader actual, por el cual se realizó el último ordenamiento.
   */
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

  /**
   * Invoca dinámicamente el método del negocio que carga la información de la
   * base de datos.
   * 
   * El método del servicio que carga los datos debe tener exactamente los
   * siguientes parámetros, respetando el orden, almacenados en un HashMap. -
   * String inicioCarga. - String tamañoPaginacion. - String criterio. - String
   * orden. Los parámetros de búsqueda se almacenan en un HashMap<String,?>.
   */
  public List<?> cargarDatos() {
    Integer inicioCarga = paginador.getActivePage() * paginador.getPageSize();
    Integer tamanoPaginacion = paginador.getPageSize();

    try {
      Class<?> clase = objetoModelo.getClass();
      Class<?>[] parametros = new Class[2];
      parametros[0] = Map.class;
      parametros[1] = Map.class;

      Map<String, String> parametrosOrden = new HashMap<String, String>();
      parametrosOrden.put(ConstantesSesion.INICIO_CARGA_PARAM, String.valueOf(inicioCarga));
      parametrosOrden.put(ConstantesSesion.TAMANO_PAGINACION_PARAM, String.valueOf(tamanoPaginacion));
      parametrosOrden.put(ConstantesSesion.CRITERIO_PARAM, criterio);
      parametrosOrden.put(ConstantesSesion.ORDEN_PARAM, orden);
      Object[] arglist = new Object[] { this.parametrosConsulta, parametrosOrden };
      Method metodo = clase.getMethod(nombreMetodo, parametros);
      datos = (List<?>) metodo.invoke(objetoModelo, arglist);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return datos;
  }

  public String getNombreMetodo() {
    return nombreMetodo;
  }

  public void setNombreMetodo(String nombreMetodo) {
    this.nombreMetodo = nombreMetodo;
  }

  public String getCriterio() {
    return criterio;
  }

  public void setCriterio(String criterio) {
    this.criterio = criterio;
  }

  public String getOrden() {
    return orden;
  }

  public void setOrden(String orden) {
    this.orden = orden;
  }

  public Object getObjetoModelo() {
    return objetoModelo;
  }

  public void setObjetoModelo(Object objetoModelo) {
    this.objetoModelo = objetoModelo;
  }

  public Listbox getListBox() {
    return listBox;
  }

  public void setListBox(Listbox listBox) {
    this.listBox = listBox;
  }

  public List<?> getDatos() {
    return datos;
  }

  public void setDatos(List<?> datos) {
    this.datos = datos;
  }

  public Map<String, Object> getParametrosConsulta() {
    return parametrosConsulta;
  }

  public void setParametrosConsulta(Map<String, Object> parametrosConsulta) {
    this.parametrosConsulta = parametrosConsulta;
  }
}

/**
 * Listener que escucha los eventos de paginación y ordenamiento.
 * 
 * @author kmarroqu
 * 
 */

final class ExternalPagingListener implements EventListener {
  private ExternalPagingSorting extPag;

  public ExternalPagingListener(ExternalPagingSorting extPag) {
    this.extPag = extPag;
  }

  @Override
  public void onEvent(Event event) throws Exception {

    event.stopPropagation();
    if (event.getName().equals(Events.ON_SORT)) {

      Listheader lh = (Listheader) event.getTarget();
      if (lh.getSortDirection().compareTo("ascending") == 0)
        lh.setSortDirection("descending");
      else
        lh.setSortDirection("ascending");
      extPag.setCriterio(lh.getId());
      extPag.setOrden(lh.getSortDirection());
      extPag.cargarDatos();
      extPag.limpiarIndicadoresOrden(lh);
    }
    if (event.getName().equals(ZulEvents.ON_PAGING)) {
      extPag.cargarDatos();
    }
    Events.sendEvent(extPag.getListBox(), new Event(Events.ON_NOTIFY));
  }
}