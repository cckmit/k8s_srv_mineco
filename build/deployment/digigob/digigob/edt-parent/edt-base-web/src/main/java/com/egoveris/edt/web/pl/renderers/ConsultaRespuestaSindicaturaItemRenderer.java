package com.egoveris.edt.web.pl.renderers;

import com.egoveris.edt.base.model.SindicaturaBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ConsultaRespuestaSindicaturaItemRenderer implements ListitemRenderer {

  private SimpleDateFormat SDF_ = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SindicaturaBean sindicatura = (SindicaturaBean) data;

    Listcell sistema = new Listcell(sindicatura.getSistema());
    sistema.setParent(item);

    Listcell usuario = new Listcell(sindicatura.getUsuario());
    usuario.setParent(item);

    Listcell codigo = new Listcell(sindicatura.getCodigo());
    codigo.setParent(item);

    Listcell anio = new Listcell(Integer.toString(sindicatura.getAnio()));
    anio.setParent(item);

    Listcell numero = new Listcell(Integer.toString(sindicatura.getNumero()));
    numero.setParent(item);

    Listcell secuencia = new Listcell(sindicatura.getSecuencia());
    secuencia.setParent(item);

    Listcell reparticionActuacion = new Listcell(sindicatura.getReparticionActuacion());
    reparticionActuacion.setParent(item);

    Listcell reparticionUsuario = new Listcell(sindicatura.getReparticionCodigo());
    reparticionUsuario.setParent(item);

    Listcell fecha = new Listcell(fechaToString(sindicatura.getFecha()));
    fecha.setParent(item);

    Listcell estado = new Listcell(sindicatura.getEstado());
    estado.setParent(item);

    Listcell sectorInterno = new Listcell(sindicatura.getSectorUsuario());
    sectorInterno.setParent(item);

  }

  public String fechaToString(Date fecha) {
    SimpleDateFormat sdf = (SimpleDateFormat) SDF_.clone();
    String fechaString = sdf.format(fecha);
    return fechaString;

  }
}