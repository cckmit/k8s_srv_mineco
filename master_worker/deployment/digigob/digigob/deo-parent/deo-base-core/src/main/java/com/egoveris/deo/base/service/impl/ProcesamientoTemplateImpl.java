package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.TemplateMalArmadoException;
import com.egoveris.deo.base.exception.ValoresIncorrectosException;
import com.egoveris.deo.base.service.ProcesamientoTemplate;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoTemplateDTO;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;
import com.egoveris.ffdd.ws.service.ExternalFormularioService;
import com.egoveris.ffdd.ws.service.ExternalTransaccionService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
@Transactional
public class ProcesamientoTemplateImpl implements ProcesamientoTemplate {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProcesamientoTemplateImpl.class);
  private static final String DOCUMENTO_TEMPLATE = "DocumentoTemplate";
  private static final String SPACE = "_R";
  private static final String VACIO = "";

  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private ExternalTransaccionService transaccionService;
  @Autowired
  private ExternalFormularioService formularioService;

  /**
   * Permite armar un texto como array de bytes basado en el ultimo template
   * asociado a un tipo de documento y con un mapa determinado de valores para
   * asociarle al mismo.
   * 
   * @param tipoDocumento
   * @param camposTemplate
   * @return
   * @throws Exception
   */
  public byte[] armarDocumentoTemplate(TipoDocumentoDTO tipoDocumento,
      Map<String, Object> camposTemplate) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoTemplate(TipoDocumentoDTO, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    TipoDocumentoTemplateDTO tipoDocumentoTemplate;
    try {
      // Se vuelve a obtener el tipo de Documento por problemas de
      // "Conexión cerrada" al obtener el TipoDocumentoTemplate
      TipoDocumentoDTO tipoDocumentoObject = tipoDocumentoService
          .buscarTipoDocumentoPorId(tipoDocumento.getId());

      tipoDocumentoTemplate = this.obtenerUltimoTemplatePorTipoDocumento(tipoDocumentoObject);
    } catch (Exception e) {
      LOGGER.error(
          "Ha ocurrido un error al armar el documento de tipo Template - " + e.getMessage());
      throw e;
    }
    byte[] returnbyteArray = this.armarDocumentoTemplate(tipoDocumentoTemplate, camposTemplate);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoTemplate(TipoDocumentoDTO, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnbyteArray;
  }

  /**
   * Permite armar un texto como array de bytes basado en un tipo de documento
   * template y un mapa determinado de valores para asociarle al mismo.
   * 
   * @param tipoDocumento
   * @param camposTemplate
   * @return
   * @throws Exception
   */
  public byte[] armarDocumentoTemplate(TipoDocumentoTemplateDTO tipoDocumentoTemplate,
      Map<String, Object> camposTemplate) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoTemplate(TipoDocumentoTemplateDTO, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    byte[] data = null;
    try {
       byte[] bdata = tipoDocumentoTemplate.getTemplate().getBytes();
      
       com.egoveris.ffdd.model.model.FormularioDTO form =
       this.formularioService
       .buscarFormularioPorNombre(tipoDocumentoTemplate.getIdFormulario());
      
       data = this.procesarTemplate(bdata, camposTemplate, form);

    } catch (Exception e) {
      LOGGER.error(
          "Ha ocurrido un error al armar el documento de tipo Template - " + e.getMessage());
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarDocumentoTemplate(TipoDocumentoTemplateDTO, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return data;
  }

  /**
   * Permite armar un texto como array de bytes basado en un texto pasado como
   * array de bytes y un mapa determinado de valores para asociarle al mismo.
   * Recordar que el texto brindado debe poseer campos definidos al estilo
   * freeMarker para poder realizar la transformacion deseada.
   * 
   * @param template
   * @param valores
   * @return
   * @throws ClavesFaltantesException
   * @throws Exception
   */
  public byte[] procesarTemplate(byte[] template, Map<String, Object> valores)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("procesarTemplate(byte[], Map<String,Object>) - start"); //$NON-NLS-1$
    }

    Map<String, Object> valoresTransformados = reemplazarValorDeCheckbox(valores);
    InputStream templateStream = new ByteArrayInputStream(template);
    byte[] documentoTemplateArmado = null;

    try {

      Template t = new Template(DOCUMENTO_TEMPLATE,
          new StringReader(transformarInToString(templateStream)), new Configuration());

      documentoTemplateArmado = this.generarTemplate(t, valoresTransformados);

    } catch (Exception e) {
      LOGGER.error("Error al generar el template", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("procesarTemplate(byte[], Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return documentoTemplateArmado;
  }

  public byte[] procesarTemplate(byte[] template, Map<String, Object> valores,
      com.egoveris.ffdd.model.model.FormularioDTO form) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("procesarTemplate(byte[], Map<String,Object>, FormularioDTO) - start"); //$NON-NLS-1$
    }

    Map<String, Object> mapaValores = obtenerCantidadRepetidos(valores, form);
    String newTemplate = actualizarTemplate(new String(template), valores, form, mapaValores);
    template = (newTemplate.replaceAll("¬R", VACIO)).getBytes();
    Map<String, Object> valoresTransformados = reemplazarValorDeCheckbox(valores);
    try {
      aplicarMascara(form, valoresTransformados);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
    InputStream templateStream = new ByteArrayInputStream(template);
    byte[] documentoTemplateArmado = null;

    try {
      Template t = new Template(DOCUMENTO_TEMPLATE,
          new StringReader(transformarInToString(templateStream)), new Configuration());

      documentoTemplateArmado = this.generarTemplate(t, valoresTransformados);

    } catch (Exception e) {
      LOGGER.error("Error al generar el template", e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("procesarTemplate(byte[], Map<String,Object>, FormularioDTO) - end"); //$NON-NLS-1$
    }
    return documentoTemplateArmado;
  }

  private void aplicarMascara(com.egoveris.ffdd.model.model.FormularioDTO form,
      Map<String, Object> valoresTransformados) throws java.text.ParseException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("aplicarMascara(FormularioDTO, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    for (com.egoveris.ffdd.model.model.FormularioComponenteDTO formularioComponente : form
        .getFormularioComponentes()) {

      if (formularioComponente.getComponente().getMascara() != null && !formularioComponente.getComponente().getMascara().isEmpty()) {
        String numeroConMascara;
        String numeroTel = (String) valoresTransformados.get(formularioComponente.getNombre());
        String mascara = formularioComponente.getComponente().getMascara();
        numeroConMascara = format(mascara, numeroTel);
        valoresTransformados.put(formularioComponente.getNombre(), numeroConMascara);
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("aplicarMascara(FormularioDTO, Map<String,Object>) - end"); //$NON-NLS-1$
    }
  }

  private String format(String pattern, Object value) throws java.text.ParseException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("format(String, Object) - start"); //$NON-NLS-1$
    }

    MaskFormatter mask;
    mask = new MaskFormatter(pattern);
    mask.setValueContainsLiteralCharacters(false);
    String returnString = mask.valueToString(value);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("format(String, Object) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  private Map<String, Object> obtenerCantidadRepetidos(Map<String, Object> valores,
      com.egoveris.ffdd.model.model.FormularioDTO form) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerCantidadRepetidos(Map<String,Object>, FormularioDTO) - start"); //$NON-NLS-1$
    }

    Map<String, Object> gridRepetidos = new HashMap<>();
    List<String> nombresSeparadores = new ArrayList<>();
    Map<String, List<String>> conjuntosRepetitivos = obtenerConjuntosRepetitivos(form);

    Set<String> setKeysConjuntos = conjuntosRepetitivos.keySet();
    Set<String> setKeysValores = valores.keySet();
    for (String itemNombreComponente : setKeysValores) {
      for (String itemSeparador : setKeysConjuntos) {
        if (itemNombreComponente.equals(itemSeparador)) {
          gridRepetidos.put(itemNombreComponente, valores.get(itemNombreComponente));
          nombresSeparadores.add(itemNombreComponente);
          break;
        }
      }
    }

    for (String compABorrar : nombresSeparadores) {
      valores.remove(compABorrar);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerCantidadRepetidos(Map<String,Object>, FormularioDTO) - end"); //$NON-NLS-1$
    }
    return gridRepetidos;
  }

  private String actualizarTemplate(String template, Map<String, Object> valores,
      com.egoveris.ffdd.model.model.FormularioDTO form, Map<String, Object> gridRepetidos)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "actualizarTemplate(String, Map<String,Object>, FormularioDTO, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    Boolean hayRepetido = false;
    String templateModificado = VACIO;
    Boolean esTabla;

    Map<String, List<String>> conjuntosRepetitivos = obtenerConjuntosRepetitivos(form);
    Set<String> setCampos = valores.keySet();
    for (String campo : setCampos) {
      if (!template.contains(campo)) {
        if (template.contains("¬R")) {
          hayRepetido = true;

          String[] lista = template.split("¬R");

          if (lista.length % 2 == 1) {
            for (int i = 0; i < lista.length; i++) {
              esTabla = false;
              String copiaDeTexto = lista[i];

              if (copiaDeTexto.contains("<table")) {
                String duplicado = duplicarFilaTabla(conjuntosRepetitivos, copiaDeTexto, campo,
                    gridRepetidos);
                templateModificado = templateModificado.concat(duplicado);
                esTabla = true;
              } else {
                templateModificado = templateModificado + copiaDeTexto;
              }

              if (i % 2 == 1 && !esTabla) {
                int max = obtenerCantMaximaRepetidos(copiaDeTexto, gridRepetidos,
                    conjuntosRepetitivos);
                for (int j = 0; j < max; j++) {
                  Map<String, List<String>> mapTextoCampos = modificarNombre(conjuntosRepetitivos,
                      copiaDeTexto, campo, j + 1);
                  Set<String> setKeys = mapTextoCampos.keySet();
                  for (String copiaDeTextoModificada : setKeys) {
                    templateModificado = templateModificado + copiaDeTextoModificada;
                  }
                }
              }
            }
          }
        }
        break;
      }
    }
    String returnString = hayRepetido ? templateModificado : template;
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "actualizarTemplate(String, Map<String,Object>, FormularioDTO, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  private String duplicarFilaTabla(Map<String, List<String>> conjuntosRepetitivos,
      String copiaDeTexto, String campo, Map<String, Object> gridRepetidos) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "duplicarFilaTabla(Map<String,List<String>>, String, String, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    Set<String> setKeys;
    String templateModificado = "";
    String[] listaCamposCopia = copiaDeTexto.split("<table");
    int max;

    for (int i = 0; i < listaCamposCopia.length; i++) {
      if (listaCamposCopia[i].contains("<tr>")) {
        templateModificado = templateModificado.concat("<table");
        String[] listaCamposTabla = listaCamposCopia[i].split("tr>");

        for (int j = 0; j < listaCamposTabla.length; j++) {
          templateModificado = templateModificado.concat(listaCamposTabla[j]);
          if (j != listaCamposTabla.length - 1) {
            templateModificado = templateModificado.concat("tr>");
          }

          if ((max = obtenerCantMaximaRepetidos(listaCamposTabla[j], gridRepetidos,
              conjuntosRepetitivos)) > 0) {

            for (int k = 0; k < max; k++) {
              Map<String, List<String>> mapTextoCampos = modificarNombre(conjuntosRepetitivos,
                  listaCamposTabla[j], campo, k + 1);
              setKeys = mapTextoCampos.keySet();
              for (String copiaDeTextoModificada : setKeys) {
                copiaDeTextoModificada = copiaDeTextoModificada.replaceAll("</p>", "");
                copiaDeTextoModificada = copiaDeTextoModificada.replaceAll("<p>", "");
                templateModificado = templateModificado
                    .concat(copiaDeTextoModificada.substring(0, 1) + "<tr>"
                        + copiaDeTextoModificada.substring(2) + "tr>");
              }
            }
          }
        }
      } else {
        templateModificado = templateModificado.concat(listaCamposCopia[i]);

        if ((max = obtenerCantMaximaRepetidos(listaCamposCopia[i], gridRepetidos,
            conjuntosRepetitivos)) > 0) {
          for (int j = 0; j < max; j++) {
            Map<String, List<String>> mapTextoCampos = modificarNombre(conjuntosRepetitivos,
                listaCamposCopia[i], campo, j + 1);
            setKeys = mapTextoCampos.keySet();
            for (String copiaDeTextoModificada : setKeys) {

              if (i == 0) {
                copiaDeTextoModificada = copiaDeTextoModificada.replaceFirst("</p> <", "");
                copiaDeTextoModificada = copiaDeTextoModificada.replaceFirst("</p>", "");
                copiaDeTextoModificada = copiaDeTextoModificada.replaceFirst("<p>", "");
                templateModificado = templateModificado.concat(copiaDeTextoModificada);
              } else {
                copiaDeTextoModificada = copiaDeTextoModificada.replaceFirst("<p>>", "");
                templateModificado = templateModificado.concat(copiaDeTextoModificada);
              }
            }
          }
        }
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "duplicarFilaTabla(Map<String,List<String>>, String, String, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return templateModificado;
  }

  private int obtenerCantMaximaRepetidos(String copiaDeTexto, Map<String, Object> gridRepetidos,
      Map<String, List<String>> conjuntosRepetitivos) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "obtenerCantMaximaRepetidos(String, Map<String,Object>, Map<String,List<String>>) - start"); //$NON-NLS-1$
    }

    List<String> campos = new ArrayList<>();
    List<String> componentes = new ArrayList<>();
    String campo = VACIO;
    int numRepetido = 0;
    int max = 0;

    Map<String, List<String>> mapaNombres = modificarNombre(conjuntosRepetitivos, copiaDeTexto,
        campo, numRepetido);
    Set<String> setKeys = mapaNombres.keySet();
    for (String key : setKeys) {
      campos = mapaNombres.get(key);
    }
    setKeys = conjuntosRepetitivos.keySet();
    for (String nombreCampo : campos) {
      for (String nombreGrid : setKeys) {
        componentes = conjuntosRepetitivos.get(nombreGrid);
        for (String nombreComponente : componentes) {
          if (nombreCampo.equals(nombreComponente)) {
            Set<String> setKeysSeparadores = gridRepetidos.keySet();
            for (String nombreSeparador : setKeysSeparadores) {
              if (nombreSeparador.equals(nombreGrid)) {
                if (max < new Long(gridRepetidos.get(nombreSeparador).toString())) {
                  max = new Integer(gridRepetidos.get(nombreSeparador).toString());
                  break;
                }
              }
            }
          }
        }
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "obtenerCantMaximaRepetidos(String, Map<String,Object>, Map<String,List<String>>) - end"); //$NON-NLS-1$
    }
    return max;
  }

  private Map<String, List<String>> obtenerConjuntosRepetitivos(
      com.egoveris.ffdd.model.model.FormularioDTO form) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerConjuntosRepetitivos(FormularioDTO) - start"); //$NON-NLS-1$
    }

    List<String> campos = new ArrayList<>();
    Map<String, List<String>> conjuntosRepetitivos = new HashMap<String, List<String>>();
    String separadorNombre = VACIO;
    boolean separadorRepetidor = false;
    boolean separadorGenerico = false;
    Set<com.egoveris.ffdd.model.model.FormularioComponenteDTO> setComponentes = form
        .getFormularioComponentes();

    for (com.egoveris.ffdd.model.model.FormularioComponenteDTO componente : setComponentes) {
      if ("Separator - Repetidor".equals(componente.getComponente().getNombre())) {
        if (separadorRepetidor) {
          conjuntosRepetitivos.put(separadorNombre, campos);
        }
        separadorNombre = componente.getNombre();
        separadorRepetidor = true;
        separadorGenerico = false;
        campos = new ArrayList<>();
      }
      if ("Separator - Generico".equals(componente.getComponente().getNombre())) {
        separadorGenerico = true;
      } else {
        if (!separadorNombre.equals(VACIO)
            && !"Separator - Repetidor".equals(componente.getComponente().getNombre())
            && !separadorGenerico) {
          if ("NroSadeBox - Nro Sade con validacion".equals(componente.getComponente().getNombre())) {
            campos.add(componente.getNombre() + "_act");
            campos.add(componente.getNombre() + "_anio");
            campos.add(componente.getNombre() + "_nro");
            campos.add(componente.getNombre() + "_rep");
          } else {
            if ("Usigbox - Domicilio".equals(componente.getComponente().getNombre())) {
              campos.add(componente.getNombre());
              campos.add(componente.getNombre() + "_barrio");
              campos.add(componente.getNombre() + "_comuna");
              campos.add(componente.getNombre() + "_seccion");
              campos.add(componente.getNombre() + "_manzana");
              campos.add(componente.getNombre() + "_parcela");
            } else {
              campos.add(componente.getNombre());
            }
          }

        }
      }
    }
    conjuntosRepetitivos.put(separadorNombre, campos);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerConjuntosRepetitivos(FormularioDTO) - end"); //$NON-NLS-1$
    }
    return conjuntosRepetitivos;
  }

  private Map<String, List<String>> modificarNombre(Map<String, List<String>> conjuntosRepetitivos,
      String copiaTexto, String campo, int num) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("modificarNombre(Map<String,List<String>>, String, String, int) - start"); //$NON-NLS-1$
    }

    String  copiaDeTexto = copiaTexto;
    String numRepetido = String.valueOf(num);
    List<String> nombreCampos = new ArrayList<>();
    Map<String, List<String>> mapTextoCampos = new HashMap<>();

    for (int i = 0; i < (copiaDeTexto.length() - 1); i++) {
      if ("${".equals(copiaDeTexto.substring(i, i + 2))) {
        for (int j = i + 2; j < copiaDeTexto.length(); j++) {
          if ("}".equals(copiaDeTexto.substring(j, j + 1))) {
            if ("!".equals(copiaDeTexto.substring(j - 1, j))) {
              if (")".equals(copiaDeTexto.substring(j - 2, j - 1))) {
                if ("c".equals(copiaDeTexto.substring(j - 3, j - 2))) {
                  if (campo.equals(VACIO)) {
                    nombreCampos.add(copiaDeTexto.substring(i + 3, j - 4));
                  } else {
                    copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                        numRepetido, i + 3, j - 4, j - 4, VACIO);
                    i = j + 2;
                  }
                } else {
                  if ("y".equals(copiaDeTexto.substring(j - 5, j - 4))) {
                    if (campo.equals(VACIO)) {
                      nombreCampos.add(copiaDeTexto.substring(i + 3, j - 23));
                    } else {
                      copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                          numRepetido, i + 3, j - 23, j - 23, VACIO);
                      i = j + 2;
                    }
                  } else {
                    if (campo.equals(VACIO)) {
                      nombreCampos.add(copiaDeTexto.substring(i + 3, j - 20));
                    } else {
                      copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                          numRepetido, i + 3, j - 20, j - 20, VACIO);
                      i = j + 2;
                    }
                  }
                }
              } else {
                if (campo.equals(VACIO)) {
                  nombreCampos.add(copiaDeTexto.substring(i + 2, j - 1));
                } else {
                  copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto, numRepetido,
                      i + 2, j - 1, j - 1, VACIO);
                  i = j + 2;
                }
              }
            } else {
              if (")".equals(copiaDeTexto.substring(j - 1, j))) {
                if ("y".equals(copiaDeTexto.substring(j - 3, j - 2))) {
                  if (campo.equals(VACIO)) {
                    nombreCampos.add(copiaDeTexto.substring(i + 2, j - 21));
                  } else {
                    copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                        numRepetido, i + 2, j - 21, j, "date");
                    i = j + 5;
                  }
                } else {
                  if (campo.equals(VACIO)) {
                    nombreCampos.add(copiaDeTexto.substring(i + 2, j - 18));
                  } else {
                    copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                        numRepetido, i + 2, j - 18, j, "double");
                    i = j + 5;
                  }
                }
              } else {
                if ("c".equals(copiaDeTexto.substring(j - 1, j))) {
                  if (campo.equals(VACIO)) {
                    nombreCampos.add(copiaDeTexto.substring(i + 2, j - 2));
                  } else {
                    copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                        numRepetido, i + 2, j - 2, j, "long");
                    i = j + 5;
                  }
                } else {
                  if (campo.equals(VACIO)) {
                    nombreCampos.add(copiaDeTexto.substring(i + 2, j));
                  } else {
                    copiaDeTexto = agregarSubindice(conjuntosRepetitivos, copiaDeTexto,
                        numRepetido, i + 2, j, j, "comun");
                    i = j + 3;
                  }
                }
              }
            }
            break;
          }
        }
      }
    }
    mapTextoCampos.put("</p> <p>" + copiaDeTexto, nombreCampos);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("modificarNombre(Map<String,List<String>>, String, String, int) - end"); //$NON-NLS-1$
    }
    return mapTextoCampos;
  }

  private String agregarSubindice(Map<String, List<String>> conjuntosRepetitivos,
      String copiaDeTexto, String numRepetido, int inicio, int fin, int resto,
      String obligatorio) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - start"); //$NON-NLS-1$
    }

    String nombreCampo = copiaDeTexto.substring(inicio, fin);
    Set<String> setKeys = conjuntosRepetitivos.keySet();

    for (String key : setKeys) {
      List<String> listComponents = conjuntosRepetitivos.get(key);
      for (String component : listComponents) {
        if (component.equals(nombreCampo)) {
          if (!obligatorio.equals(VACIO)) {
            if ("date".equals(obligatorio)) {
              nombreCampo = "(" + nombreCampo.substring(0) + SPACE + numRepetido;
              String returnString = (copiaDeTexto.substring(0, inicio) + nombreCampo
                  + copiaDeTexto.substring(fin, resto) + ")!" + copiaDeTexto.substring(resto));
              if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(
                    "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
              }
              return returnString;
            } else {
              if ("double".equals(obligatorio)) {
                nombreCampo = "(" + nombreCampo.substring(0) + SPACE
                    + numRepetido;
                String returnString = (copiaDeTexto.substring(0, inicio) + nombreCampo
                    + copiaDeTexto.substring(fin, resto) + ")!" + copiaDeTexto.substring(resto));
                if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug(
                      "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
                }
                return returnString;
              } else {
                if (obligatorio.equals("long")) {
                  nombreCampo = "(" + nombreCampo.substring(0) + SPACE
                      + String.valueOf(numRepetido);
                  String returnString = (copiaDeTexto.substring(0, inicio) + nombreCampo
                      + copiaDeTexto.substring(fin, resto) + ")!" + copiaDeTexto.substring(resto));
                  if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(
                        "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
                  }
                  return returnString;
                } else {
                  nombreCampo = nombreCampo.substring(0) + SPACE + String.valueOf(numRepetido)
                      + "!";
                  String returnString = (copiaDeTexto.substring(0, inicio) + nombreCampo
                      + copiaDeTexto.substring(resto));
                  if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(
                        "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
                  }
                  return returnString;
                }
              }
            }
          } else {
            nombreCampo = nombreCampo.substring(0) + SPACE + String.valueOf(numRepetido);
            String returnString = (copiaDeTexto.substring(0, inicio) + nombreCampo
                + copiaDeTexto.substring(resto));
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug(
                  "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
            }
            return returnString;
          }
        }
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "agregarSubindice(Map<String,List<String>>, String, String, int, int, int, String) - end"); //$NON-NLS-1$
    }
    return copiaDeTexto;
  }

  /**
   * Metodo que identifica valores "true" o "false" y los cambia por valores
   * "Si" o "No" respectivamente.
   * 
   * @param valores
   * @return
   */
  private Map<String, Object> reemplazarValorDeCheckbox(Map<String, Object> valores) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("reemplazarValorDeCheckbox(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    Map<String, Object> valoresTransformados = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : valores.entrySet()) {
      if (entry.getValue().toString().equalsIgnoreCase("true")
          || (entry.getValue().toString().equalsIgnoreCase("false"))) {

        String valorBoolean = (entry.getValue().toString().equalsIgnoreCase("true") ? "Si" : "No");
        valoresTransformados.put(entry.getKey(), valorBoolean);
      } else {
        valoresTransformados.put(entry.getKey(), entry.getValue());
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("reemplazarValorDeCheckbox(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return valoresTransformados;
  }

  /**
   * Metodo que transforma el contenido de un InputStream a una cadena de Texto
   * String.
   * 
   * @param co
   * @return
   */
  private String transformarInToString(InputStream co) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("transformarInToString(InputStream) - start"); //$NON-NLS-1$
    }

    StringBuilder sb = new StringBuilder();
    String line;

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(co, "UTF-8"));
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (UnsupportedEncodingException uee) {
      LOGGER.error("Encoding no soportado", uee);
    } catch (IOException ioe) {
      LOGGER.error("No se pudo cerrar el archivo", ioe);
    } finally {
      try {
        co.close();
      } catch (IOException ioe) {
        LOGGER.error("No se pudo cerrar el archivo", ioe);
      }
    }

    String returnString = sb.toString();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("transformarInToString(InputStream) - end"); //$NON-NLS-1$
    }
    return returnString;
  }

  /**
   * Procesa el template FreeMarker. Reemplazando en la "template" los campos
   * preseteados en "propiedadesTemplate"
   * 
   * @param template
   * @param porpiedadesTemplate
   *          mapa con los nombres
   * @return byte[]
   */
  private byte[] generarTemplate(Template template, Map<String, Object> propiedadesTemplate)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("generarTemplate(Template, Map<String,Object>) - start"); //$NON-NLS-1$
    }
    byte[] returnbyteArray = new byte[0];
    StringWriter processedTemplateWriter = new StringWriter();
    try {
      template.setLocale(new Locale("es", "ES"));
      template.process(propiedadesTemplate, processedTemplateWriter);
      returnbyteArray = String.valueOf(processedTemplateWriter.getBuffer()).getBytes();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("generarTemplate(Template, Map<String,Object>) - end"); //$NON-NLS-1$
      }

    } catch (ApplicationException e) {
      LOGGER.error(
          "Error al generar el template - No se encuentran los campos necesarios para la generacion del Documento - ",
          e);
      throw e;
    } catch (Exception e) {
      LOGGER.error("Error al generar el template", e);
    } finally {
      try {
        processedTemplateWriter.close();
      } catch (IOException ioe) {
      }
    }
    return returnbyteArray;
  }

  /**
   * Obtiene el ultimo TipoDocumentoTemplate asociado a un TipoDocumento
   * determinado.
   * 
   * @param tipoDocumento
   * @return
   */
  public TipoDocumentoTemplateDTO obtenerUltimoTemplatePorTipoDocumento(
      TipoDocumentoDTO tipoDocumento) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUltimoTemplatePorTipoDocumento(TipoDocumentoDTO) - start"); //$NON-NLS-1$
    }

    // Se vuelve a obtener el tipo de Documento por problemas de
    // "Conexión cerrada" al obtener el TipoDocumentoTemplate
    TipoDocumentoDTO tipoDocumentoObject = tipoDocumentoService
        .buscarTipoDocumentoPorId(tipoDocumento.getId());

    double i = 0;
    TipoDocumentoTemplateDTO tipoDocumentoTemplate = null;

    for (TipoDocumentoTemplateDTO tipoDocumentoTemplateAux : tipoDocumentoObject
        .getListaTemplates()) {
      if (tipoDocumentoTemplateAux.getTipoDocumentoTemplatePK().getVersion() > i) {
        tipoDocumentoTemplate = tipoDocumentoTemplateAux;
        i = tipoDocumentoTemplateAux.getTipoDocumentoTemplatePK().getVersion();
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerUltimoTemplatePorTipoDocumento(TipoDocumentoDTO) - end"); //$NON-NLS-1$
    }
    return tipoDocumentoTemplate;
  }

  /**
   * Valida que el mapa de campo valor se corresponda correctamente a un
   * template. Para ello, se puede pasar un TipoDocumento y este metodo se
   * encargara de obtener el Template asociado al ultimo TipoDocumentoTemplate.
   * En caso de que los campos no coincidan, se saldra por la excepcion
   * "ClavesFaltantesException".
   * 
   * @param tipoDocumento
   * @param camposFormulario
   * @throws ClavesFaltantesException
   * @throws TemplateMalArmadoException
   * @throws Exception
   */
  public void validarCamposTemplatePorTipoDocumentoYMap(TipoDocumentoDTO tipoDocumento,
      Map<String, Object> camposFormulario) throws ApplicationException, ValoresIncorrectosException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "validarCamposTemplatePorTipoDocumentoYMap(TipoDocumentoDTO, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    this.armarDocumentoTemplate(tipoDocumento, camposFormulario);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug(
          "validarCamposTemplatePorTipoDocumentoYMap(TipoDocumentoDTO, Map<String,Object>) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Valida que el mapa de campo valor se corresponda correctamente a un
   * template determinado pasado como Array de bytes. En caso de que los campos
   * no coincidan, se saldra por la excepcion "ClavesFaltantesException".
   * 
   * @param tipoDocumento
   * @param camposFormulario
   * @throws ClavesFaltantesException
   * @throws TemplateMalArmadoException
   * @throws Exception
   */
  public void validarCamposTemplatePorArrayByteYMap(byte[] template,
      Map<String, Object> camposFormulario) throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarCamposTemplatePorArrayByteYMap(byte[], Map<String,Object>) - start"); //$NON-NLS-1$
    }

    this.procesarTemplate(template, camposFormulario);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarCamposTemplatePorArrayByteYMap(byte[], Map<String,Object>) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene los campos de la transacción del formulario FC en un mapa
   * 
   * @param idTransaccion
   * @return
   * @throws DynFormException
   */
  public Map<String, Object> obtenerCamposTemplate(Integer idTransaccion)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerCamposTemplate(Integer) - start"); //$NON-NLS-1$
    }

    Map<String, Object> mapso = new HashMap<>();

    TransaccionDTO transaccion = this.transaccionService.buscarTransaccionPorUUID(idTransaccion);

    for (ValorFormCompDTO valor : transaccion.getValorFormComps()) {
      mapso.put(valor.getInputName(), valor.getValor());
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerCamposTemplate(Integer) - end"); //$NON-NLS-1$
    }
    return mapso;
  }

}
