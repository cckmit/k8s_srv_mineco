package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.Componente;
import com.egoveris.ffdd.base.model.ComponenteMultilinea;
import com.egoveris.ffdd.base.model.Formulario;
import com.egoveris.ffdd.base.model.FormularioComponente;
import com.egoveris.ffdd.base.model.FormularioSinRelaciones;
import com.egoveris.ffdd.base.model.Grupo;
import com.egoveris.ffdd.base.repository.ComponenteMultilineaRepository;
import com.egoveris.ffdd.base.repository.ComponenteRepository;
import com.egoveris.ffdd.base.repository.FormularioComponenteRepository;
import com.egoveris.ffdd.base.repository.FormularioRepository;
import com.egoveris.ffdd.base.repository.FormularioSinRelacionesRepository;
import com.egoveris.ffdd.base.repository.GrupoRepository;
import com.egoveris.ffdd.base.service.IConstraintService;
import com.egoveris.ffdd.base.service.IFormularioService;
import com.egoveris.ffdd.base.util.ConstantesBase;
import com.egoveris.ffdd.base.util.ConstantesConstraint;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.ConstraintDTO;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.ffdd.model.model.FormularioComponenteDTO;
import com.egoveris.ffdd.model.model.FormularioDTO;
import com.egoveris.ffdd.model.model.FormularioWDDTO;
import com.egoveris.ffdd.model.model.GrupoDTO;
import com.egoveris.ffdd.model.model.VisibilidadComponenteDTO;
import com.egoveris.shared.map.ListMapper;

@Service("formularioService")
@Transactional
public class FormularioServiceImpl implements IFormularioService {
  private static final Logger logger = LoggerFactory.getLogger(FormularioServiceImpl.class);

  @Autowired
  protected FormularioRepository formularioRepository;
  @Autowired
  private ComponenteRepository componenteRepository;
  @Autowired
  private ComponenteMultilineaRepository componenteMultilineaRepository;
  @Autowired
  private GrupoRepository grupoRepository;
  @Autowired
  private FormularioSinRelacionesRepository formularioSinRelacionesRepository;
  @Autowired
  private FormularioComponenteRepository formularioComponenteRepository;
  @Autowired
  protected ConverterServiceImpl converter;
  @Autowired
  private IConstraintService constraintService;
  @Autowired
  @Qualifier("mapperCore")
  private Mapper mapper;

  @Override
  public String buscarMultilinea(final Integer idComponente) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarMultilinea(idComponente={}) - start", idComponente);
    }
    try {
      final ComponenteMultilinea cm = this.componenteMultilineaRepository
          .findOneByIdFormComp(idComponente);
      if (logger.isDebugEnabled()) {
        logger.debug("buscarMultilinea(Integer) - end - return value={}", cm.getTexto());
      }
      return cm.getTexto();
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching multi line", e);
    }
  }

  @Override
  public void eliminarMultilinea(final int idComponente) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarMultilinea(idComponente={}) - start", idComponente);
    }
    try {
      this.componenteMultilineaRepository.deleteByIdFormComp(idComponente);
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR deleting multi line", e);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarMultilinea(int) - end");
    }
  }

  @Override
  public boolean verificarUsoComponente(final String id) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("verificarUsoComponente(id={}) - start", id);
    }
    try {
      Componente componente = new Componente();
      componente.setId(Integer.parseInt(id));
      FormularioComponente formComponente = this.formularioComponenteRepository
          .findByComponente(componente);
      boolean returnboolean = false;
      if (formComponente != null) {
        returnboolean = true;
      }
      if (logger.isDebugEnabled()) {
        logger.debug("verificarUsoComponente(String) - end - return value={}", returnboolean);
      }
      return returnboolean;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR verifying use component", e);
    }
  }

  @Override
  public FormularioDTO buscarFormularioPorNombre(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("findByNombre(nombre={}) - start", nombre);
    }

    try {
      final Formulario form = formularioRepository.findByNombre(nombre);
      final FormularioDTO formularioDTO = converter.formToDTO(form);
      if (form != null) {
        agregarLogicaDesencadenante(formularioDTO);
      }
      final FormularioDTO returnFormularioDTO = form == null ? null
          : agregarTextoMultilinea(formularioDTO);
      if (logger.isDebugEnabled()) {
        logger.debug("findByNombre(String) - end - return value={}", returnFormularioDTO);
      }
      return returnFormularioDTO;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching form by name", e);
    }
  }
  
  @Override
  public FormularioWDDTO buscarFormularioPorNombreWD(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("findByNombre(nombre={}) - start", nombre);
    }

    try {
      final Formulario form = formularioRepository.findByNombre(nombre);
      final FormularioDTO formularioDTO = converter.formToDTO(form);
      if (form != null) {
        agregarLogicaDesencadenante(formularioDTO);
      }
      final FormularioDTO returnFormularioDTO = form == null ? null
          : agregarTextoMultilinea(formularioDTO);
      if (logger.isDebugEnabled()) {
        logger.debug("findByNombre(String) - end - return value={}", returnFormularioDTO);
      }
      
      FormularioWDDTO formWd = mapper.map(returnFormularioDTO, FormularioWDDTO.class);
      return formWd;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching form by name", e);
    }
  }

  private FormularioDTO agregarTextoMultilinea(final FormularioDTO form) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarTextoMultilinea(form={}) - start", form);
    }

    for (final FormularioComponenteDTO componente : form.getFormularioComponentes()) {
      if (componente.isMultilinea()) {
        componente.setTextoMultilinea(buscarMultilinea(componente.getId()));
        componente.setObligatorio(false);
        componente.setRelevanciaBusqueda(0);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("agregarTextoMultilinea(FormularioDTO) - end - return value={}", form);
    }
    return form;
  }

  @Override
  public GrupoDTO buscarGrupoPorNombre(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("findOneByNombre(nombre={}) - start", nombre);
    }

    final Grupo grupo = grupoRepository.findOneByNombre(nombre);
    final GrupoDTO returnGrupoDTO = converter.grupoToDTO(grupo);
    if (logger.isDebugEnabled()) {
      logger.debug("findOneByNombre(String) - end - return value={}", returnGrupoDTO);
    }
    return returnGrupoDTO;
  }

  @Override
  public List<FormularioDTO> obtenerTodosLosFormularios() {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosFormularios() - start");
    }

    final List<Formulario> formsAux = new ArrayList<>();
    final List<Formulario> forms = (List<Formulario>) formularioRepository.findAll();
    for (final Formulario form : forms) {
			// form.getFormularioComponentes().size();
      if (!form.getNombre().startsWith(ConstantesBase.TEMP)) {
        formsAux.add(form);
      }
    }
    final List<FormularioDTO> returnList = converter.formListToDTO(formsAux);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosFormularios() - end - return value={}", returnList);
    }
    return returnList;
  }

  @Override
  public List<FormularioDTO> obtenerTodosLosFormulariosSinRelaciones() throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosFormulariosSinRelaciones() - start");
    }

    try {
      final List<FormularioSinRelaciones> forms = (List<FormularioSinRelaciones>) formularioSinRelacionesRepository
          .findAll();
      final List<FormularioSinRelaciones> formsAux = new ArrayList<>();
      for (final FormularioSinRelaciones form : forms) {
        if (!form.getNombre().startsWith(ConstantesBase.TEMP)) {
          formsAux.add(form);
        }
      }
      final List<FormularioDTO> returnList = converter.formReducidoListToDTO(formsAux);
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerTodosLosFormulariosSinRelaciones() - end - return value={}",
            returnList);
      }
      return returnList;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR retrieving all forms without relationships.", e);
    }
  }

  @Override
  public void eliminarFormulario(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulario(nombre={}) - start", nombre);
    }

    try {
      this.formularioRepository.delete(formularioRepository.findByNombre(nombre));
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR deleting form.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulario(String) - end");
    }
  }

  @Override
  public void eliminarCaja(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarCaja(nombre={}) - start", nombre);
    }

    try {
      this.grupoRepository.delete(grupoRepository.findOneByNombre(nombre));
    } catch (final AccesoDatoException e) {
      logger.error("Error al eliminar la caja de datos: " + e.getMessage(), e);
      throw new DynFormException("ERROR deleting box.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarCaja(String) - end");
    }
  }

  @Override
  public void eliminarFormulariosTemporales() {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulariosTemporales() - start");
    }

    this.formularioSinRelacionesRepository.deleteByNombreContains(ConstantesBase.TEMP);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulariosTemporales() - end");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ComponenteDTO> obtenerTodosLosComponentes() throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosComponentes() - start");
    }
    List<ComponenteDTO> returnList = ListMapper.mapList(componenteRepository.findAll(), mapper,
        ComponenteDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosComponentes() - end - return value={}", returnList);
    }
    return returnList;
  }

  @Override
  public ComponenteDTO buscarComponentePorNombre(final String nombre) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComponentePorNombre(nombre={}) - start", nombre);
    }

    try {
      final Componente com = this.componenteRepository.findOneByNombre(nombre);
      if (com == null) {
        if (logger.isDebugEnabled()) {
          logger.debug("buscarComponentePorNombre(String) - end - return value={null}");
        }
        return null;
      }
      com.getItems().size();

      final ComponenteDTO returnComponenteDTO = mapper.map(com, ComponenteDTO.class);
      if (logger.isDebugEnabled()) {
        logger.debug("buscarComponentePorNombre(String) - end - return value={}",
            returnComponenteDTO);
      }
      return returnComponenteDTO;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching component by name.", e);
    }
  }

  @Override
  public ComponenteDTO buscarComponenteById(final int idComponente) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComponenteById(idComponente={}) - start", idComponente);
    }

    try {
      final ComponenteDTO returnComponenteDTO = mapper.map(
          this.componenteRepository
              .findOne(this.formularioComponenteRepository.findOne(idComponente).getId()),
          ComponenteDTO.class);
      if (logger.isDebugEnabled()) {
        logger.debug("buscarComponenteById(int) - end - return value={}", returnComponenteDTO);
      }
      return returnComponenteDTO;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching component by id.", e);
    }
  }

  @Override
  public void guardarFormulario(final FormularioDTO form) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarFormulario(form={}) - start", form);
    }

    try {
      formularioRepository.save(converter.dTOToForm(form));
      guardarComponenteMultilinea(form);
      guardarConstraintDTO(form);
      guardarRestriccionDTO(form);
    } catch (AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR saving form.", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarFormulario(FormularioDTO) - end");
    }
  }

  /**
   * Permite agregar las constraints o restricciones que posea el formulario...
   *
   * @param formulario
   * @throws AccesoDatoException
   */
  private void agregarLogicaDesencadenante(final FormularioDTO formulario)
      throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug("agregarLogicaDesencadenante(formulario={}) - start", formulario);
    }

    final List<FormTriggerDTO> listDynamicConstraintComponent = constraintService
        .obtenerConstraintsPorComponente(formulario.getId());
    formulario.setListaComponentesOcultos(new ArrayList<VisibilidadComponenteDTO>());
    final List<ConstraintDTO> listaConstraintAux = new ArrayList<>();
    for (final FormTriggerDTO dynamicConstraintComp : listDynamicConstraintComponent) {
      final Object objectDTO = constraintService.crearObjectDTO(dynamicConstraintComp);
      if (objectDTO instanceof ConstraintDTO) {
        ConstraintDTO constraintDto = (ConstraintDTO) objectDTO;
        listaConstraintAux.add(constraintDto);
      }
      if (objectDTO instanceof VisibilidadComponenteDTO) {
        VisibilidadComponenteDTO restriccionDto = (VisibilidadComponenteDTO) objectDTO;
        formulario.getListaComponentesOcultos().add(restriccionDto);
      }
    }
    for (final FormularioComponenteDTO componente : formulario.getFormularioComponentes()) {
      componente.setConstraintList(new ArrayList<ConstraintDTO>());
      for (final ConstraintDTO constraint : listaConstraintAux) {
        if (constraint.getNombreComponente().equals(componente.getNombre())) {
          componente.getConstraintList().add(constraint);
        }
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("agregarLogicaDesencadenante(FormularioDTO) - end");
    }
  }

  /**
   * Permite guardar las constraints del formulario, por componente , tabla
   * DF_DYNAMIC_CONSTRAINT
   *
   * @param formulario
   * @throws AccesoDatoException
   */
  private void guardarConstraintDTO(final FormularioDTO formulario) throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarConstraintDTO(formulario={}) - start", formulario);
    }

    final Formulario formularioPersistido = formularioRepository
        .findByNombre(formulario.getNombre());
    final List<FormTriggerDTO> listaTrigger = new ArrayList<>();
    for (final FormularioComponenteDTO formComp : formulario.getFormularioComponentes()) {
      listaTrigger.addAll(cargarTriggers(formComp, formularioPersistido.getId()));
    }
    constraintService.modificarConstraintList(listaTrigger, formularioPersistido.getId());

    if (logger.isDebugEnabled()) {
      logger.debug("guardarConstraintDTO(FormularioDTO) - end");
    }
  }

  private List<FormTriggerDTO> cargarTriggers(final FormularioComponenteDTO formComp,
      final int idFormulario) throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarTriggers(formComp={}, idFormulario={}) - start", formComp, idFormulario);
    }

    final List<FormTriggerDTO> triggers = new ArrayList<>();
    if (formComp.getConstraintList() != null) {
      for (final ConstraintDTO constraintDto : formComp.getConstraintList()) {
        final FormTriggerDTO formTrigger = constraintService
            .createDynamicConstraintComponent(idFormulario, constraintDto);
        formTrigger.setType(ConstantesConstraint.TYPE_CONSTRAINTDTO);
        triggers.add(formTrigger);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarTriggers(FormularioComponenteDTO, int) - end - return value={}",
          triggers);
    }
    return triggers;
  }

  private void guardarRestriccionDTO(final FormularioDTO formulario) throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarRestriccionDTO(formulario={}) - start", formulario);
    }

    final Formulario formularioPersistido = formularioRepository
        .findByNombre(formulario.getNombre());
    if (formulario.getId() == null) {
      constraintService.guardarListConstraints(crearListaDynamicRestriccion(
          formularioPersistido.getId(), formulario.getListaComponentesOcultos()));
    } else {
      constraintService.modificarRestriccionList(formulario,
          ConstantesConstraint.TYPE_RESTRICCIONDTO);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarRestriccionDTO(FormularioDTO) - end");
    }
  }

  public List<FormTriggerDTO> crearListaDynamicConstraint(final FormularioComponente componente,
      final List<ConstraintDTO> listConstraintDTO, final int idFormulario)
      throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearListaDynamicConstraint(componente={}, listConstraintDTO={}, idFormulario={}) - start",
          componente, listConstraintDTO, idFormulario);
    }

    final List<FormTriggerDTO> listDynamicConstraintComponent = new ArrayList<>();
    FormTriggerDTO dynamicConstraintComponent;
    for (final ConstraintDTO constraintDto : listConstraintDTO) {
      final FormularioComponente formComp = new FormularioComponente();
      formComp.setId(componente.getId());
      dynamicConstraintComponent = constraintService.createDynamicConstraintComponent(idFormulario,
          constraintDto);
      dynamicConstraintComponent.setType(ConstantesConstraint.TYPE_CONSTRAINTDTO);
      listDynamicConstraintComponent.add(dynamicConstraintComponent);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearListaDynamicConstraint(FormularioComponente, List<ConstraintDTO>, int) - end - return value={}",
          listDynamicConstraintComponent);
    }
    return listDynamicConstraintComponent;
  }

  public List<FormTriggerDTO> crearListaDynamicRestriccion(final int idFormulario,
      final List<VisibilidadComponenteDTO> listRestriccionDTO) throws AccesoDatoException {
    if (logger.isDebugEnabled()) {
      logger.debug("crearListaDynamicRestriccion(idFormulario={}, listRestriccionDTO={}) - start",
          idFormulario, listRestriccionDTO);
    }

    final List<FormTriggerDTO> listDynamicConstraintComponent = new ArrayList<>();
    FormTriggerDTO dynamicConstraintComponent;
    if (listRestriccionDTO != null) {
      for (final VisibilidadComponenteDTO restricciontDto : listRestriccionDTO) {
        dynamicConstraintComponent = constraintService
            .createDynamicConstraintComponent(idFormulario, restricciontDto);
        dynamicConstraintComponent.setType(ConstantesConstraint.TYPE_RESTRICCIONDTO);
        listDynamicConstraintComponent.add(dynamicConstraintComponent);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearListaDynamicRestriccion(int, List<VisibilidadComponenteDTO>) - end - return value={}",
          listDynamicConstraintComponent);
    }
    return listDynamicConstraintComponent;
  }

  public void guardarComponenteMultilinea(final FormularioDTO form) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarComponenteMultilinea(form={}) - start", form);
    }

    final Formulario formularioGuardado = formularioRepository.findByNombre(form.getNombre());
    final Set<FormularioComponente> setFormComponents = formularioGuardado
        .getFormularioComponentes();
    List<ComponenteMultilinea> listMultilinea = new ArrayList<>();
    for (final FormularioComponenteDTO componente : form.getFormularioComponentes()) {
      if (componente.isMultilinea()) {
    	  listMultilinea.add(validateGuardarComponenteMultilinea(formularioGuardado, componente, setFormComponents));
      }
    }
    if (!listMultilinea.isEmpty()) {
    	this.componenteMultilineaRepository.save(listMultilinea);
    }
    guardaComponenteMultilineaLogger();
  }

  public ComponenteMultilinea validateGuardarComponenteMultilinea(Formulario formularioGuardado,
      FormularioComponenteDTO componente, Set<FormularioComponente> setFormComponents) {
	  ComponenteMultilinea multilineaComp = new ComponenteMultilinea();
    for (final FormularioComponente formComponent : setFormComponents) {
      if (componente.getNombre().equals(formComponent.getNombre())) {
        if (componente.getId() != null) {
          multilineaComp = this.componenteMultilineaRepository
              .findOneByIdFormComp(componente.getId());
          multilineaComp.setTexto(componente.getTextoMultilinea());
        } else {
          multilineaComp.setIdFormulario(formularioGuardado.getId());
          multilineaComp.setIdFormComp(formComponent.getId());
          multilineaComp.setTexto(componente.getTextoMultilinea());
        }
        return multilineaComp;
      }
    }
    return null;
  }

  public void guardaComponenteMultilineaLogger() {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarComponenteMultilinea(FormularioDTO) - end");
    }
  }

  @Override
  public void guardarGrupo(final GrupoDTO grupo) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarGrupo(grupo={}) - start", grupo);
    }

    grupoRepository.save(converter.dTOToGrupo(grupo));

    if (logger.isDebugEnabled()) {
      logger.debug("guardarGrupo(GrupoDTO) - end");
    }
  }

  @Override
  public List<GrupoDTO> obtenerTodosLosGrupos() throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosGrupos() - start");
    }

    final List<Grupo> grupos = (List<Grupo>) this.grupoRepository.findAll();
    final List<Grupo> lstAux = new ArrayList<>();
    for (final Grupo grupo : grupos) {
      grupo.getGrupoComponentes().size();
      lstAux.add(grupo);
    }
    final List<GrupoDTO> returnList = converter.grupoListToDTO(lstAux);
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerTodosLosGrupos() - end - return value={}", returnList);
    }
    return returnList;
  }

  @Override
  public void modificarGrupo(final GrupoDTO grupo) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("modificarGrupo(grupo={}) - start", grupo);
    }

    grupoRepository.save(converter.dTOToGrupo(grupo));

    if (logger.isDebugEnabled()) {
      logger.debug("modificarGrupo(GrupoDTO) - end");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<FormularioComponenteDTO> buscarFormComponentPorFormulario(final int idFormulario)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarFormComponentPorFormulario(idFormulario={}) - start", idFormulario);
    }

    try {
      Formulario formulario = new Formulario();
      formulario.setId(idFormulario);
      final List<FormularioComponenteDTO> returnList = ListMapper.mapList(
          formularioComponenteRepository.findByFormulario(formulario), mapper,
          FormularioComponenteDTO.class);
      if (logger.isDebugEnabled()) {
        logger.debug("buscarFormComponentPorFormulario(int) - end - return value={}", returnList);
      }
      return returnList;
    } catch (final AccesoDatoException e) {
      logger.error(e.getMessage(), e);
      throw new DynFormException("ERROR searching form component by form.", e);
    }
  }
}