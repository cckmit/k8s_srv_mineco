package com.egoveris.te.base.composer;

import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.util.ConstantesServicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow; 

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SearchFormComposer extends EEGenericForwardComposer {

  private static final long serialVersionUID = 5712501002999884499L;
  
  @Autowired
  private Bandbox tipoFormBandbox;

  @WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
  private IExternalFormsService externalFormsService;

  @Autowired
  private Textbox textTypeForm = new Textbox();
  @Autowired
  private Tree formTypeTree;
  @Autowired
  private AnnotateDataBinder binderForm;

  private List<FormularioDTO> listFormComplete;
  private List<FormularioDTO> listFormFilter;
  private TreeModel treeModel;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp); 
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    this.listFormComplete = externalFormsService.buscarFormulariosFFDD();
    
    if (listFormComplete != null && !listFormComplete.isEmpty()) {
      this.filter(listFormComplete, formTypeTree);
      this.rechargeComponent(formTypeTree, binderForm);
    }
  }

  @SuppressWarnings("unchecked")
  public void onSelect$formTypeTree() throws InterruptedException {
    List<Component> treeSelectedItem = (List<Component>) formTypeTree.getSelectedItem()
        .getTreerow().getChildren();
    if (tipoFormBandbox != null) {
      String valueBandbox = SelectedItem(treeSelectedItem);
      tipoFormBandbox.setValue(valueBandbox);
      InputEvent e = new InputEvent("onChanging", textTypeForm, "", tipoFormBandbox.getValue());
      rechargeComponent(textTypeForm, binderForm);
      rechargeComponent(tipoFormBandbox, binderForm);
      this.tipoFormBandbox.close();
    }
  }

  public void onChanging$tipoFormBandbox(InputEvent e) {
    InputEvent ev = new InputEvent("onChanging", textTypeForm, e.getValue(),
        textTypeForm.getValue());
    this.textTypeForm.setValue(e.getValue());
    onChanging$textTypeForm(ev);
  }

  public void onChanging$textTypeForm(InputEvent e) {
    this.textTypeForm.clearErrorMessage();
    String matchingText = e.getValue().trim();
    if (!matchingText.equals("") && (matchingText.length() >= 2)) {
      this.listFormFilter = this.chargeFilterFormList(this.listFormComplete,
          matchingText.toLowerCase());
      if (!this.listFormFilter.isEmpty()) {
        filter(this.listFormFilter, formTypeTree);
        rechargeComponent(formTypeTree, binderForm);
      } else {
        throw new WrongValueException(this.textTypeForm,
            Labels.getLabel("ee.busquedaTipoDocumento.textoInexistente"));
      }
    } else if (matchingText.equals("")) {
      filter(this.listFormComplete, formTypeTree);
      rechargeComponent(formTypeTree, binderForm);
    }
  }

  public void filter(List<FormularioDTO> typeForm, Tree arbol) {
    List<DefaultTreeNode<FormularioDTO>> listTree = new ArrayList<DefaultTreeNode<FormularioDTO>>();
    List<DefaultTreeNode<FormularioDTO>> listFormFilterAux = new ArrayList<DefaultTreeNode<FormularioDTO>>();
    if (typeForm != null) {
      listTree.clear();
      FormularioDTO typeFormItem = null;
      String codigoSadeAux;

      int index = 0;
      int end = typeForm.size();

      while (index < end) {
        listFormFilterAux.clear();
        codigoSadeAux = typeForm.get(index).getNombre();
        while (index < end && codigoSadeAux.equals(typeForm.get(index).getNombre())) {
          typeFormItem = typeForm.get(index);
          listFormFilterAux.add(new DefaultTreeNode(typeFormItem));
          index++;
        }
        listTree.add(new DefaultTreeNode(codigoSadeAux, listFormFilterAux));
      }
      this.treeModel = new DefaultTreeModel(new DefaultTreeNode("ROOT", listTree));
    }
    arbol.setTreeitemRenderer(new TreeitemRenderer() {
      public void render(Treeitem item, Object data, int arg1) throws Exception {
        if (data instanceof DefaultTreeNode) {
          DefaultTreeNode nodo = (DefaultTreeNode) data;

          if (nodo.getData() instanceof String) {
            String fi = (String) nodo.getData();
            Treerow tr = new Treerow();
            tr.setParent(item);
            tr.setId(null);
            tr.appendChild(new Treecell(fi));
          } else if (nodo.getData() instanceof FormularioDTO) {
            treeRowFormularioDTO(item, (FormularioDTO) nodo.getData());
          }
        } else if (data instanceof FormularioDTO) {
          treeRowFormularioDTO(item, (FormularioDTO) data);
        }
      }
    });
    arbol.setModel(this.treeModel);
  }

  private void treeRowFormularioDTO(Treeitem treeItem, FormularioDTO data) {
    Treerow treerow = new Treerow();
    treerow.setParent(treeItem);

    if (data.getId() != null) {
      treerow.setId(data.getId().toString());
    }
  }

  public void rechargeComponent(Component comp, AnnotateDataBinder db) {
    db = new AnnotateDataBinder(comp);
    db.loadComponent(comp);
  }

  public String SelectedItem(List<Component> selectedItem) {
    String value = "";
    for (Component tc : selectedItem) {
      if (value.equals(""))
        value = ((Treecell) tc).getLabel();
    }
    return value;
  }

  public List<FormularioDTO> chargeFilterFormList(List<FormularioDTO> listTypeFormComplete,
      String criterion) {
    List<FormularioDTO> listFilter = new ArrayList<FormularioDTO>();
    String codig;
    for (FormularioDTO td : listTypeFormComplete) {
      codig = "";
      if (td.getNombre() != null)
        codig = td.getNombre().toLowerCase();
      if (codig.contains(criterion))
        listFilter.add(td);
    }
    return listFilter;
  }
}
