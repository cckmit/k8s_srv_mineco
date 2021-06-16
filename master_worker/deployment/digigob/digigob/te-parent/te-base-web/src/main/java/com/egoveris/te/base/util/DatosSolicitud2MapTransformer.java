package com.egoveris.te.base.util;

import com.egoveris.te.base.composer.CaratularExternoComposer;
import com.egoveris.te.base.composer.CaratularInternoComposer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.zkoss.zk.ui.Executions;

public class DatosSolicitud2MapTransformer implements Transformer {

  /**
   * Transforma
   * 
   * @param <code>com.egoveris.te.base.composer.CaratularExternoComposer</code>
   *          ó
   *          <code>com.egoveris.te.base.composer.CaratularInternoComposer</code>
   *          input,
   * @return La estructura que devuelve es
   *         <code>Map<String, Object></code>inputMap
   *         <ul>
   *         <li>ATTR_USER_NAME</li>
   *         <li>ATTR_APELLIDO_SOLICITANTE</li>
   *         ATTR_SEGUNDO_APELLIDO_SOLICITANTE ATTR_TERCER_APELLIDO_SOLICITANTE
   *         <li>ATTR_NOMBRE_SOLICITANTE</li> ATTR_SEGUNDO_NOMBRE_SOLICITANTE
   *         ATTR_TERCERO_NOMBRE_SOLICITANTE
   *         <li>ATTR_RAZON_SOCIAL</li>
   *         <li>ATTR_EMAIL</li>
   *         <li>ATTR_TELEFONO</li>
   *         <li>ATTR_CUIT_CUIL</li>
   *         <li>ATTR_TIPO_DOC</li>
   *         <li>ATTR_NUM_DOC</li>
   *         <li>ATTR_MOTIVO_INTERNO</li>
   *         <li>ATTR_MOTIVO_EXTERNO</li> ATTR_DOMICILIO ATTR_PISO
   *         ATTR_DEPARTAMENTO ATTR_CODIGO_POSTAL ATTR_BARRIO ATTR_COMUNA
   *         </ul>
   */
  @Override
  public Object transform(Object input) {

    Map<String, Object> solicitudDatosMap = new HashMap<String, Object>();

    if (input instanceof CaratularExternoComposer) {
      CaratularExternoComposer caratularExternoComposer = (CaratularExternoComposer) input;
      setterCaratulacionExternoMap(solicitudDatosMap, caratularExternoComposer);
    } else if (input instanceof CaratularInternoComposer) {
      CaratularInternoComposer caratularInternoComposer = (CaratularInternoComposer) input;
      setterCaratulacionInternoMap(solicitudDatosMap, caratularInternoComposer);
    }
    return solicitudDatosMap;
  }

  private void setterCaratulacionExternoMap(Map<String, Object> solicitudDatosMap,
      CaratularExternoComposer caratularExternoComposer) {
    solicitudDatosMap.put("ATTR_USER_NAME",
        (String) Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME));

    solicitudDatosMap.put("ATTR_APELLIDO_SOLICITANTE",
        caratularExternoComposer.getApellido().getValue());

    solicitudDatosMap.put("ATTR_SEGUNDO_APELLIDO_SOLICITANTE",
        caratularExternoComposer.getSegundoApellido().getValue());
    solicitudDatosMap.put("ATTR_TERCER_APELLIDO_SOLICITANTE",
        caratularExternoComposer.getTercerApellido().getValue());

    solicitudDatosMap.put("ATTR_NOMBRE_SOLICITANTE",
        caratularExternoComposer.getNombre().getValue());
    solicitudDatosMap.put("ATTR_SEGUNDO_NOMBRE_SOLICITANTE",
        caratularExternoComposer.getSegundoNombre().getValue());
    solicitudDatosMap.put("ATTR_TERCER_NOMBRE_SOLICITANTE",
        caratularExternoComposer.getTercerNombre().getValue());

    solicitudDatosMap.put("ATTR_RAZON_SOCIAL",
        caratularExternoComposer.getRazonSocial().getValue());
    solicitudDatosMap.put("ATTR_EMAIL", caratularExternoComposer.getEmail().getValue());
    solicitudDatosMap.put("ATTR_TELEFONO", caratularExternoComposer.getTelefono().getValue());

    /*
    if (!caratularExternoComposer.getNoDeclaraNoPosee().isChecked()) {
      solicitudDatosMap.put("ATTR_CUIT_CUIL", caratularExternoComposer.getCuitCuil());
    } else {
      solicitudDatosMap.put("ATTR_CUIT_CUIL", null);
    }
    */
    
    solicitudDatosMap.put("ATTR_CUIT_CUIL", null);
    
    /*
    if ((caratularExternoComposer.getTipoDocumento().getValue() != null)
        && !caratularExternoComposer.getTipoDocumento().getValue().equals("")
        && (caratularExternoComposer.getNumeroDocumento().getValue() != null
            && !caratularExternoComposer.getNumeroDocumento().getValue().equals(""))) {
      solicitudDatosMap.put("ATTR_TIPO_DOC",
          caratularExternoComposer.getTipoDocumento().getValue().substring(0, 2));
      solicitudDatosMap.put("ATTR_NUM_DOC",
          (caratularExternoComposer.getNumeroDocumento().getValue().toString()));
    } else {
      solicitudDatosMap.put("ATTR_TIPO_DOC", null);
      solicitudDatosMap.put("ATTR_NUM_DOC", null);
    }
    */
    solicitudDatosMap.put("ATTR_TIPO_DOC", null);
    solicitudDatosMap.put("ATTR_NUM_DOC", caratularExternoComposer.getNumeroDocumento().getValue().toString());

    solicitudDatosMap.put("ATTR_MOTIVO_INTERNO",
        caratularExternoComposer.getMotivoInternoExpediente().getValue());
    solicitudDatosMap.put("ATTR_MOTIVO_EXTERNO",
        caratularExternoComposer.getMotivoExternoExpediente().getValue());

    solicitudDatosMap.put("ATTR_DOMICILIO", caratularExternoComposer.getDireccion().getValue());
    solicitudDatosMap.put("ATTR_PISO", caratularExternoComposer.getPiso().getValue());
    solicitudDatosMap.put("ATTR_DEPARTAMENTO",
        caratularExternoComposer.getDepartamento().getValue());
    solicitudDatosMap.put("ATTR_CODIGO_POSTAL",
        caratularExternoComposer.getCodigoPostal().getValue());

    return;
  }

  private void setterCaratulacionInternoMap(Map<String, Object> solicitudDatosMap,
      CaratularInternoComposer caratularInternoComposer) {

    solicitudDatosMap.put("ATTR_USER_NAME",
        (String) Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME));
    solicitudDatosMap.put("ATTR_EMAIL", caratularInternoComposer.getEmail().getValue());
    solicitudDatosMap.put("ATTR_TELEFONO", caratularInternoComposer.getTelefono().getValue());

    solicitudDatosMap.put("ATTR_MOTIVO_INTERNO",
        caratularInternoComposer.getMotivoInternoExpediente().getValue());
    solicitudDatosMap.put("ATTR_MOTIVO_EXTERNO",
        caratularInternoComposer.getMotivoExternoExpediente().getValue());

    return;
  }

}
