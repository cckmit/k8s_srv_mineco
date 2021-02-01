package com.egoveris.edt.web.pl.consulta;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;

public class FindReparticionesImportar extends GenericForwardComposer {

  /**
  * 
  */
  private static final long serialVersionUID = -8826295810008568404L;

  // private ObtenerReparticionServices obtenerReparticionService;

  @Autowired
  protected Listbox reparticionesImportarDocumentoSADEListbox;
  @Autowired
  protected Textbox textoReparticionImportarDocumentoSADE;
  @Autowired
  protected Bandbox reparticionImportarDocumentoSADE;
  protected AnnotateDataBinder binder;

  protected ReparticionDTO reparticionSeleccionada;
  protected List<ReparticionDTO> listaReparticionSADESeleccionada;
  protected List<ReparticionDTO> listaReparticionesSADECompleta;
  @Autowired
  private Combobox comboUAI;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.binder = new AnnotateDataBinder(reparticionesImportarDocumentoSADEListbox);
    this.listaReparticionSADESeleccionada = new ArrayList<ReparticionDTO>();
    // this.obtenerReparticionService =
    // (ObtenerReparticionServices)SpringUtil.getBean("obtenerReparticionService");

    // usar las del service

    binder.bindBean("listaReparticionSADESeleccionada", this.listaReparticionSADESeleccionada);
    binder.bindBean("reparticionSeleccionada", this.reparticionSeleccionada);
    binder.loadAll();
  }
  //
  // public void onChanging$textoReparticionImportarDocumentoSADE(InputEvent e)
  // {
  // this.cargarReparticiones(e);
  // }
  ////
  // public void cargarReparticiones(InputEvent e){
  // String matchingText = e.getValue();
  // if (!matchingText.equals("") && (matchingText.length() >= 3)) {
  // this.listaReparticionSADESeleccionada.clear();
  // if (listaReparticionesSADECompleta != null) {
  // matchingText = matchingText.toUpperCase();
  // Iterator<ReparticionDTO> iterator =
  // listaReparticionesSADECompleta.iterator();
  // ReparticionDTO reparticion = null;
  // while ((iterator.hasNext())) {
  // reparticion = iterator.next();
  // if ((reparticion != null) && (reparticion.getNombre() != null)) {
  // if ((reparticion.getNombre().contains(matchingText)) ||
  // (reparticion.getCodigo().contains(matchingText))) {
  // listaReparticionSADESeleccionada.add(reparticion);
  // }
  // }
  // }
  // }
  // this.binder.loadComponent(reparticionesImportarDocumentoSADEListbox);
  // }
  // else if(matchingText.equals("")){
  // this.listaReparticionSADESeleccionada.clear();
  // this.binder.loadComponent(reparticionesImportarDocumentoSADEListbox);
  // }
  // }
  //
  // public void onBlur$reparticionImportarDocumentoSADE() {
  // String value = this.reparticionImportarDocumentoSADE.getValue();
  // if (StringUtils.isNotEmpty(value)) {
  //// if (!(obtenerReparticionService.validarCodigoReparticion(value.trim())))
  // {
  // this.reparticionImportarDocumentoSADE.focus();
  //// throw new WrongValueException(this.reparticionImportarDocumentoSADE,
  // Labels.getLabel("ee.general.adm.reparticionInvalida"));
  //// }
  // this.reparticionImportarDocumentoSADE.setValue(value.toUpperCase());
  // }
  // }
  //
  // public void onSelect$reparticionesImportarDocumentoSADEListbox() {
  // ReparticionDTO rsb =
  // this.listaReparticionSADESeleccionada.get(reparticionesImportarDocumentoSADEListbox.getSelectedIndex());
  // this.reparticionImportarDocumentoSADE.setValue(rsb.getCodigo());
  // this.textoReparticionImportarDocumentoSADE.setValue(null);
  // this.listaReparticionSADESeleccionada.clear();
  // this.binder.loadComponent(this.reparticionImportarDocumentoSADE);
  // this.textoReparticionImportarDocumentoSADE.setValue(null);
  // reparticionImportarDocumentoSADE.close();
  // }
}
