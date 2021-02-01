package com.egoveris.ffdd.base.service.impl;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.exception.DynFormAnioConstraintException;
import com.egoveris.ffdd.base.exception.DynFormRegularExpression;
import com.egoveris.ffdd.base.exception.DynFormTextBox;
import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.model.FormularioComponente;
import com.egoveris.ffdd.base.model.Transaccion;
import com.egoveris.ffdd.base.model.complex.ComplexComponentDTO;
import com.egoveris.ffdd.base.repository.FormularioRepository;
import com.egoveris.ffdd.base.repository.TransaccionRepository;
import com.egoveris.ffdd.base.repository.ValorFormCompRepository;
import com.egoveris.ffdd.base.service.IFileService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.service.ITransaccionService;
import com.egoveris.ffdd.base.util.ConstantesValidarFormulario;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.exception.DynFormValidarTransaccionException;
import com.egoveris.ffdd.model.exception.DynFormValorComponente;
import com.egoveris.ffdd.model.model.AtributoComponenteDTO;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TransaccionDTO;
import com.egoveris.ffdd.model.model.TransaccionValidaDTO;
import com.egoveris.ffdd.model.model.ValorFormCompDTO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("transaccionService")
@Transactional
public class TransaccionServiceImpl implements ITransaccionService {
  private final Logger logger = LoggerFactory.getLogger(TransaccionServiceImpl.class);

  @Autowired
  private TransaccionRepository transaccionRepository;
  @Autowired
  private ValorFormCompRepository valorFormCompRepository;
  @Autowired
  @Qualifier("mapperCore")
  private Mapper mapper;

  @Autowired
  protected FormularioRepository formularioRepository;

  @Autowired
  protected IFormularioService formularioService;

  @Autowired
  private IFileService fileService;

  @Override
  public TransaccionDTO buscarTransaccionPorUUID(final Integer uuid) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTransaccionPorUUID(uuid={}) - start", uuid);
    }

    TransaccionDTO transaccionDTO = null;
    try {
      final Transaccion tran = transaccionRepository.findByUuid(uuid);

      if (tran == null) {
        return null;
      }

      transaccionDTO = getMapper().map(tran, TransaccionDTO.class);

      final Formulario f = formularioRepository.findByNombre(tran.getNombreFormulario());

      // se agregan etiqueta y relevancia busqueda al ValorFormCompDTO
      for (final FormularioComponente formularioComponente : f.getFormularioComponentes()) {
        for (final ValorFormCompDTO valorFormComp : transaccionDTO.getValorFormComps()) {
          if (valorFormComp.getIdFormComp().compareTo(formularioComponente.getId()) == 0) {
            valorFormComp.setEtiqueta(formularioComponente.getEtiqueta());
            valorFormComp.setRelevanciaBusqueda(formularioComponente.getRelevanciaBusqueda());
          }
        }
      }
      for (final ValorFormCompDTO valorFormComp : transaccionDTO.getValorFormComps()) {
        if (valorFormComp.getValorArchivo() != null) {
          valorFormComp.setArchivo(fileService.getFile(uuid, valorFormComp.getValorArchivo()));
        }
      }

    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching transaction by UUID.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTransaccionPorUUID(Integer) - end - return value={}", transaccionDTO);
    }
    return transaccionDTO;
  }

  @Override
  public void deleteFormWebExpt(TransaccionDTO trans) throws DynFormException {
    trans = this.buscarTransaccionPorUUID(trans.getUuid());
    final Transaccion tranDB = getMapper().map(trans, Transaccion.class);
    this.transaccionRepository.delete(tranDB);
  }

  @Override
  public boolean existeTransaccionParaFormulario(final String formulario) throws DynFormException {
    try {
      List<Transaccion> tran = transaccionRepository.findByNombreFormulario(formulario);
      return CollectionUtils.isNotEmpty(tran);
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR evaluating transaction for form existence.", e);
    }
  }

  @Override
  public List<ComplexComponentDTO> getComplexComponents(final Integer idTransaccion)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("getTradeComponents(idTransaccion={}) - start", idTransaccion);
    }

    TransaccionDTO transaction;
    final List<ComplexComponentDTO> returnValue = new ArrayList<>();
    transaction = this.buscarTransaccionPorUUID(idTransaccion);
    if (transaction == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("getTradeComponents(Integer) - end - return value={}", returnValue);
      }
      return returnValue;
    }
    throw new DynFormException(
        "Se revisará la funcionalidad de componentes complejos para trade.");

    // throw new NotImplementedException("Se revisará la funcionalidad de
    // componentes complejos para trade.");
    //
    // for (final FormularioComponente formEntry :
    // form.getFormularioComponentes()) {
    //
    // final ComplexComponent componentDefinition = complexService
    // .getComponent(formEntry.getComponente().getNombreXml());
    // // ifther is no complexDefinition asume normal component.
    // if (componentDefinition != null) {
    // try {
    // final Class<? extends ComplexComponentDTO> componentDTOClass =
    // (Class<? extends ComplexComponentDTO>) Class
    // .forName(componentDefinition.getMappingDTO());
    // returnValue.addAll(ComplexComponentToDTOFactory.createComponent(transaction,
    // componentDTOClass,
    // formEntry.getNombre()));
    // } catch (final ClassNotFoundException e) {
    // logger.error("getTradeComponents(Integer)", e);
    // }
    // }
    // }

    // if (logger.isDebugEnabled()) {
    // logger.debug("getTradeComponents(Integer) - end - return value={}",
    // returnValue);
    // }
    // return returnValue;
  }

  public Mapper getMapper() {
    return mapper;
  }

  @Override
  public Integer grabarTransaccion(final TransaccionDTO trans) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarTransaccion(trans={}) - start", trans);
    }

    try {
      final Transaccion tranDB = getMapper().map(trans, Transaccion.class);
      final Integer transaction = transaccionRepository.save(tranDB).getUuid();
      for (final ValorFormCompDTO valor : trans.getValorFormComps()) {
        if (valor.getArchivo() != null) {
          fileService.saveFile(transaction, valor.getArchivo().getData(),
              valor.getArchivo().getNombre());
        }
      }

      if (logger.isDebugEnabled()) {
        logger.debug("grabarTransaccion(TransaccionDTO) - end - return value={}", transaction);
      }
      return transaction;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR saving transaction.", e);
    }
  }

  public void setMapper(final Mapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void updateFormWebExpt(TransaccionDTO trans) throws DynFormException {
    try {
      if (trans.getUuid() != null) {
        Transaccion transEntity = transaccionRepository.findByUuid(trans.getUuid());
        valorFormCompRepository.delete(transEntity.getValorFormComps());
      }
      final Transaccion tranDB = getMapper().map(trans, Transaccion.class);
      // trans = this.buscarTransaccionPorUUID(trans.getUuid());
      // final Transaccion deleteDB = getMapper().map(trans, Transaccion.class);
      // transaccionRepository.delete(deleteDB);
      // logger.debug("Eliminacion exitosa");

      // persisto lo nuevo
      transaccionRepository.save(tranDB);
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR updating FormWebExept.", e);
    }
  }

  private boolean anioValidate(final Object valor) {
    if (logger.isDebugEnabled()) {
      logger.debug("anioValidate(valor={}) - start", valor);
    }

    final Date fecha = (Date) valor;
    final GregorianCalendar gregCalendar = new GregorianCalendar();
    gregCalendar.setTime(fecha);
    if (gregCalendar.get(GregorianCalendar.YEAR) < 1900
        || gregCalendar.get(GregorianCalendar.YEAR) > 2999) {
      if (logger.isDebugEnabled()) {
        logger.debug("anioValidate(Object) - end - return value={}", false);
      }
      return false;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("anioValidate(Object) - end - return value={}", true);
      }
      return true;
    }
  }

  private Object castearPorTipoComp(final Object valorMapa, final String nombreComponente,
      final String tipoComponente) throws DynFormValorComponente {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "castearPorTipoComp(valorMapa={}, nombreComponente={}, tipoComponente={}) - start",
          valorMapa, nombreComponente, tipoComponente);
    }

    try {
      if (valorMapa != null) {
        if (valorMapa instanceof String) {
          final String valorMapaStr = (String) valorMapa;

          if (tipoComponente.equals(ConstantesValidarFormulario.TEXTBOX)
              || tipoComponente.equals(ConstantesValidarFormulario.COMBOBOX)
              || tipoComponente.equals(ConstantesValidarFormulario.COMPLEXBANDBOX)
              || tipoComponente.equals(ConstantesValidarFormulario.USIGBOX)
              || tipoComponente.equals(ConstantesValidarFormulario.NROSADEBOX)
              || tipoComponente.equals(ConstantesValidarFormulario.TIMEBOX)) {
            if (logger.isDebugEnabled()) {
              logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                  valorMapaStr);
            }
            return valorMapaStr;

          }
          if (tipoComponente.equals(ConstantesValidarFormulario.DATEBOX)) {
            // formatear
            final Object returnObject = formatearDate(valorMapaStr);
            if (logger.isDebugEnabled()) {
              logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                  returnObject);
            }
            return returnObject;
          }
          if (tipoComponente.equals(ConstantesValidarFormulario.LONGBOX)) {
            final Object returnObject = Long.valueOf(valorMapaStr);
            if (logger.isDebugEnabled()) {
              logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                  returnObject);
            }
            return returnObject;
          }
          if (tipoComponente.equals(ConstantesValidarFormulario.DOUBLEBOX)) {
            final Object returnObject = Double.valueOf(valorMapaStr);
            if (logger.isDebugEnabled()) {
              logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                  returnObject);
            }
            return returnObject;
          }
          if (tipoComponente.equals(ConstantesValidarFormulario.INTBOX)) {
            final Object returnObject = Integer.valueOf(valorMapaStr);
            if (logger.isDebugEnabled()) {
              logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                  returnObject);
            }
            return returnObject;
          }
          if (tipoComponente.equals(ConstantesValidarFormulario.CHECKBOX)) {
            if ("true".equalsIgnoreCase(valorMapaStr)) {
              if (logger.isDebugEnabled()) {
                logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                    true);
              }
              return true;
            } else if ("false".equalsIgnoreCase(valorMapaStr)) {
              if (logger.isDebugEnabled()) {
                logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
                    false);
              }
              return false;
            } else {
              throw new ClassCastException("El valor no se corresponde con un boolean");
            }
          }
        }
        if (!validarTipoComponente(valorMapa, tipoComponente)) {
          throw new DynFormValorComponente("El valor: " + nombreComponente
              + " no se correponde con el componente " + tipoComponente);
        }

        if (logger.isDebugEnabled()) {
          logger.debug("castearPorTipoComp(Object, String, String) - end - return value={}",
              valorMapa);
        }
        return valorMapa;
      } else {
        throw new DynFormValorComponente("El valor no puede ser nulo");
      }

    } catch (final ClassCastException e) {
      logger.error("castearPorTipoComp(Object, String, String)", e);

      throw new DynFormValorComponente("El valor: " + nombreComponente
          + " no se correponde con el componente " + tipoComponente, e);
    }
  }

  private Object convertirArrByte(final byte[] arrByte)
      throws IOException, ClassNotFoundException {
    if (logger.isDebugEnabled()) {
      logger.debug("convertirArrByte(arrByte={}) - start", arrByte);
    }

    Object obj = null;
    ByteArrayInputStream bis = null;
    ObjectInputStream ois = null;
    try {
      bis = new ByteArrayInputStream(arrByte);
      ois = new ObjectInputStream(bis);
      obj = ois.readObject();
    } finally {
      if (bis != null) {
        bis.close();
      }
      if (ois != null) {
        ois.close();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("convertirArrByte(byte[]) - end - return value={}", obj);
    }
    return obj;
  }

  private Object convertirObjecto(final String cadena) throws IOException, ClassNotFoundException {
    if (logger.isDebugEnabled()) {
      logger.debug("convertirObjecto(cadena={}) - start", cadena);
    }

    final Object returnObject = convertirArrByte(cadena.getBytes("ISO-8859-1"));
    if (logger.isDebugEnabled()) {
      logger.debug("convertirObjecto(String) - end - return value={}", returnObject);
    }
    return returnObject;
  }

  private String expresionesRegulares(final String expresionRegular) {
    if (logger.isDebugEnabled()) {
      logger.debug("expresionesRegulares(expresionRegular={}) - start", expresionRegular);
    }

    // reemplaza la expresion '\d' por 0-9
    String retorno = expresionRegular.split("\\/")[1];
    retorno = retorno.replace("\\d", "0-9");

    if (logger.isDebugEnabled()) {
      logger.debug("expresionesRegulares(String) - end - return value={}", retorno);
    }
    return retorno;
  }

  private Date formatearDate(final String fecha) throws DynFormValorComponente {
    if (logger.isDebugEnabled()) {
      logger.debug("formatearDate(fecha={}) - start", fecha);
    }

    final SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    try {
      final Date returnDate = dt.parse(fecha);
      if (logger.isDebugEnabled()) {
        logger.debug("formatearDate(String) - end - return value={}", returnDate);
      }
      return returnDate;
    } catch (final ParseException e) {
      logger.error("formatearDate(String)", e);

      throw new DynFormValorComponente("La fecha es incorrecta");
    }
  }

  @Override
  public Integer grabarTransaccionValida(final TransaccionValidaDTO validarDTO)
      throws DynFormValidarTransaccionException {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarTransaccionValida(validarDTO={}) - start", validarDTO);
    }

    try {
      if (validarDTO.getMapaValores() != null && !validarDTO.getMapaValores().isEmpty()) {
        boolean flagExiste;
        final FormularioDTO formularioDto = formularioService
            .buscarFormularioPorNombre(validarDTO.getNombreFormulario());
        if (formularioDto != null) {
          final List<FormularioComponenteDTO> lstFormCompDTO = formularioService
              .buscarFormComponentPorFormulario(formularioDto.getId());
          final List<String> camposFormulario = new ArrayList<>();
          final List<ValorFormCompDTO> lstValorFormCompDto = new ArrayList<>();
          for (final Map.Entry<String, String> mapa : validarDTO.getMapaValores().entrySet()) {
            flagExiste = false;
            final Object valorCast = convertirObjecto(mapa.getValue());
            for (final FormularioComponenteDTO formComp : lstFormCompDTO) {
              final ComponenteDTO compDto = formComp.getComponente();
              if (compDto.getTipoComponente().equals(ConstantesValidarFormulario.SEPARATOR)
                  && mapa.getKey().equals(formComp.getNombre())) {
                flagExiste = true;
                break;
              }
              if (formComp.getNombre().equals(mapa.getKey())) {
                flagExiste = true;
                final ValorFormCompDTO valorFormCompDto = new ValorFormCompDTO();
                valorFormCompDto.setValor(
                    castearPorTipoComp(valorCast, mapa.getKey(), compDto.getTipoComponente()));
                valorFormCompDto.setInputName(mapa.getKey());
                valorFormCompDto.setIdFormComp(formComp.getId());
                camposFormulario.add(mapa.getKey());
                lstValorFormCompDto.add(valorFormCompDto);
                break;
              }
            }
            if (!flagExiste) {
              throw new DynFormException("No existe el componente con el name: " + mapa.getKey());
            }
          }
          validarObligatoriedad(lstFormCompDTO, camposFormulario);
          validarValoresFormulario(lstFormCompDTO, lstValorFormCompDto);
          // grabar transaccion
          final Integer returnInteger = this.grabarTransaccion(prepararTransaccion(
              lstValorFormCompDto, formularioDto, lstFormCompDTO, validarDTO.getSistemaOrigen()));
          if (logger.isDebugEnabled()) {
            logger.debug("grabarTransaccionValida(TransaccionValidaDTO) - end - return value={}",
                returnInteger);
          }
          return returnInteger;
        } else {
          throw new DynFormException(
              "El formulario " + validarDTO.getNombreFormulario() + " no es un formualrio valido");
        }
      } else {
        throw new DynFormException("No se puede generar transaccion sin valores");
      }
    } catch (final DynFormException | IOException | ClassNotFoundException e) {
      logger.error("grabarTransaccionValida(TransaccionValidaDTO): " + validarDTO, e);
      throw new DynFormValidarTransaccionException("ERROR persisting valid transaction", e);
    }
  }

  private TransaccionDTO prepararTransaccion(final List<ValorFormCompDTO> lstValores,
      final FormularioDTO form, final List<FormularioComponenteDTO> lstFormComp,
      final String sistOrigen) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "prepararTransaccion(lstValores={}, form={}, lstFormComp={}, sistOrigen={}) - start",
          lstValores, form, lstFormComp, sistOrigen);
    }

    final TransaccionDTO trans = new TransaccionDTO();
    final Set<ValorFormCompDTO> valorFormComps = new HashSet<>(lstValores);
    trans.setFechaCreacion(new Date());
    trans.setNombreFormulario(form.getNombre());
    trans.setValorFormComps(valorFormComps);
    trans.setSistOrigen(sistOrigen);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "prepararTransaccion(List<ValorFormCompDTO>, FormularioDTO, List<FormularioComponenteDTO>, String) - end - return value={}",
          trans);
    }
    return trans;
  }

  private void validarExistenciaMultivalue(final ValorFormCompDTO valorComp,
      final ComponenteDTO compDTO) throws DynFormValorComponente {
    if (logger.isDebugEnabled()) {
      logger.debug("validarExistenciaMultivalue(valorComp={}, compDTO={}) - start", valorComp,
          compDTO);
    }

    boolean existe = false;
    for (final ItemDTO itemDto : compDTO.getItems()) {
      if (valorComp.getValorStr().equals(itemDto.getValor())) {
        existe = true;
        break;
      }
    }
    if (!existe) {
      throw new DynFormValorComponente(
          "El valor: " + valorComp.getValorStr() + " no esta dentro de las opciones");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarExistenciaMultivalue(ValorFormCompDTO, ComponenteDTO) - end");
    }
  }

  private void validarObligatoriedad(final List<FormularioComponenteDTO> lstFormCompDTO,
      final List<String> camposFormulario) throws DynFormValidarTransaccionException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarObligatoriedad(lstFormCompDTO={}, camposFormulario={}) - start",
          lstFormCompDTO, camposFormulario);
    }

    final List<String> lstNameCompObligatorio = new ArrayList<>();
    for (final FormularioComponenteDTO formCompDTO : lstFormCompDTO) {
      if (formCompDTO.getObligatorio()) {
        lstNameCompObligatorio.add(formCompDTO.getNombre());
      }
    }
    boolean existeObligatorio;
    for (final String compObligatorio : lstNameCompObligatorio) {
      existeObligatorio = false;
      for (final String compFormulario : camposFormulario) {
        if (compFormulario.equals(compObligatorio)) {
          existeObligatorio = true;
          break;
        }
      }
      if (!existeObligatorio) {
        throw new DynFormValidarTransaccionException(
            "El campo: " + compObligatorio + " es obligatorio");

      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarObligatoriedad(List<FormularioComponenteDTO>, List<String>) - end");
    }
  }

  private void validarRestriccionesComponent(final ValorFormCompDTO valorComp,
      final ComponenteDTO compDTO) throws DynFormValorComponente {
    if (logger.isDebugEnabled()) {
      logger.debug("validarRestriccionesComponent(valorComp={}, compDTO={}) - start", valorComp,
          compDTO);
    }

    for (final Map.Entry<String, AtributoComponenteDTO> atributoComp : compDTO.getAtributos()
        .entrySet()) {
      if (atributoComp.getKey().equals(ConstantesValidarFormulario.MAX_LENGTH)) {
        if (valorComp.getValorStr().length() > Integer.valueOf(atributoComp.getValue().getId())) {
          throw new DynFormTextBox(
              "La cantidad de caracteres ingresados excede el maximo del componente");
        }
      }
      if (atributoComp.getKey().equals(ConstantesValidarFormulario.CONSTRAINT)) {
        // expresiones regulares
        if (!valorComp.getValorStr()
            .matches(expresionesRegulares(atributoComp.getValue().getValor()))) {
          throw new DynFormRegularExpression("No se cumple la expresion regular");
        }
      }
      if (atributoComp.getKey().equals(ConstantesValidarFormulario.FORMAT)
          || atributoComp.getValue().equals(ConstantesValidarFormulario.ANIO_CONSTRAINT)) {
        if (!anioValidate(valorComp.getValor())) {
          throw new DynFormAnioConstraintException("La fecha ingresada no es valida");
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarRestriccionesComponent(ValorFormCompDTO, ComponenteDTO) - end");
    }
  }

  private boolean validarTipoComponente(final Object valorComp, final String tipoComponente) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarTipoComponente(valorComp={}, tipoComponente={}) - start", valorComp,
          tipoComponente);
    }

    if (tipoComponente.equals(ConstantesValidarFormulario.DATEBOX)) {
      if (valorComp instanceof Date) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarTipoComponente(Object, String) - end - return value={}", true);
        }
        return true;
      }
    }
    if (tipoComponente.equals(ConstantesValidarFormulario.LONGBOX)) {
      if (valorComp instanceof Long) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarTipoComponente(Object, String) - end - return value={}", true);
        }
        return true;
      }
    }
    if (tipoComponente.equals(ConstantesValidarFormulario.DOUBLEBOX)) {
      if (valorComp instanceof Double) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarTipoComponente(Object, String) - end - return value={}", true);
        }
        return true;
      }
    }
    if (tipoComponente.equals(ConstantesValidarFormulario.INTBOX)) {
      if (valorComp instanceof Integer) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarTipoComponente(Object, String) - end - return value={}", true);
        }
        return true;
      }
    }
    if (tipoComponente.equals(ConstantesValidarFormulario.CHECKBOX)) {
      if (valorComp instanceof Boolean) {
        if (logger.isDebugEnabled()) {
          logger.debug("validarTipoComponente(Object, String) - end - return value={}", true);
        }
        return true;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarTipoComponente(Object, String) - end - return value={}", false);
    }
    return false;
  }

  private void validarValoresFormulario(final List<FormularioComponenteDTO> lstFormCompDTO,
      final List<ValorFormCompDTO> valoresMapa) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("validarValoresFormulario(lstFormCompDTO={}, valoresMapa={}) - start",
          lstFormCompDTO, valoresMapa);
    }

    for (final ValorFormCompDTO valor : valoresMapa) {
      for (final FormularioComponenteDTO formCompDTO : lstFormCompDTO) {
        if (formCompDTO.getNombre().equals(valor.getInputName())) {
          final ComponenteDTO compDTO = formCompDTO.getComponente();
          // si valida el tipo de valor, valida las restricciones
          if (verificarTipoComponenteSimple(compDTO) && compDTO.getAtributos() != null
              && !compDTO.getAtributos().isEmpty()) {
            validarRestriccionesComponent(valor, compDTO);
          }
          if (compDTO.getItems() != null && !compDTO.getItems().isEmpty()) {
            validarExistenciaMultivalue(valor, compDTO);
          }

          break;
        }
      }

    }
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarValoresFormulario(List<FormularioComponenteDTO>, List<ValorFormCompDTO>) - end");
    }
  }

  private boolean verificarTipoComponenteSimple(final ComponenteDTO compForm) {
    if (logger.isDebugEnabled()) {
      logger.debug("verificarTipoComponenteSimple(compForm={}) - start", compForm);
    }

    final String tipoComponente = compForm.getTipoComponente();
    if (!tipoComponente.equals(ConstantesValidarFormulario.COMBOBOX)
        && !tipoComponente.equals(ConstantesValidarFormulario.COMPLEXBANDBOX)
        && !tipoComponente.equals(ConstantesValidarFormulario.USIGBOX)
        && !tipoComponente.equals(ConstantesValidarFormulario.NROSADEBOX)) {
      if (logger.isDebugEnabled()) {
        logger.debug("verificarTipoComponenteSimple(ComponenteDTO) - end - return value={}", true);
      }
      return true;
    } else {
      if (logger.isDebugEnabled()) {
        logger.debug("verificarTipoComponenteSimple(ComponenteDTO) - end - return value={}",
            false);
      }
      return false;
    }
  }

}
