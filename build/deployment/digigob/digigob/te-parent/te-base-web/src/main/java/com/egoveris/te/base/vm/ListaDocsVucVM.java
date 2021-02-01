package com.egoveris.te.base.vm;

import com.egoveris.vucfront.ws.model.ExternalDocumentoVucDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

public class ListaDocsVucVM {

  private List<ExternalDocumentoVucDTO> vucDocList;
  private Set<ExternalDocumentoVucDTO> selectedVucDocs;
  @Wire("#documentosVucWindow")
  private Window window;

  @Init
  public void init(@ContextParam(ContextType.VIEW) Component view,
      @ExecutionArgParam("documentosUsuarioVuc") List<ExternalDocumentoVucDTO> docsUsuarioVucList,
      @ExecutionArgParam("documentosSeleccionados") List<ExternalDocumentoVucDTO> tiposDocAsubsanar) {
    Selectors.wireComponents(view, this, false);

    if (vucDocList == null) {
      vucDocList = new ArrayList<>();
    } else {
      vucDocList.clear();
    }
    if (selectedVucDocs == null) {
      selectedVucDocs = new HashSet<>();
    } else {
      selectedVucDocs.clear();
    }

    if (docsUsuarioVucList != null && !docsUsuarioVucList.isEmpty()) {
      vucDocList = docsUsuarioVucList;
    }
    if (!vucDocList.isEmpty() && tiposDocAsubsanar != null && !tiposDocAsubsanar.isEmpty()) {
      fillSelectedDocs(tiposDocAsubsanar);
    }

  }

  private void fillSelectedDocs(List<ExternalDocumentoVucDTO> docsAsubsanar) {
    for (ExternalDocumentoVucDTO vucDoc : vucDocList) {
      for (ExternalDocumentoVucDTO doc : docsAsubsanar) {
        if (vucDoc.getTipoDocumento().getAcronimoGedo()
            .equals(doc.getTipoDocumento().getAcronimoGedo())) {
          int indexDoc = vucDocList.indexOf(vucDoc);
          selectedVucDocs.add(vucDocList.get(indexDoc));
          break;
        }
      }
    }
  }

  @Command
  public void onSalir() {
    window.detach();
  }

  public Window getDocumentosVucWindow() {
    return window;
  }

  public void setDocumentosVucWindow(Window window) {
    this.window = window;
  }

  public List<ExternalDocumentoVucDTO> getVucDocList() {
    return vucDocList;
  }

  public Set<ExternalDocumentoVucDTO> getSelectedVucDocs() {
    return selectedVucDocs;
  }

}