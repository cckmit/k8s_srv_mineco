package com.egoveris.te.base.rendered;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;

@SuppressWarnings("rawtypes")
public class OrgaismosRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
  	ReparticionBean reparticionBean = (ReparticionBean) data;

    Listcell codigo = new Listcell(reparticionBean.getCodigo());
    codigo.setParent(item);
    
    Listcell nombre = new Listcell(reparticionBean.getNombre());
    nombre.setParent(item);
    item.addForward("onClick","./bandboxOrganismo","onElegirOrganismo", reparticionBean);
  }

}
	