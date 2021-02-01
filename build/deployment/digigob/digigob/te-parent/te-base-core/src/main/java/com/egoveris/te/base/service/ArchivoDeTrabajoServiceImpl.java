package com.egoveris.te.base.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.ArchivoDeTrabajo;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.TipoArchivoTrabajoDTO;
import com.egoveris.te.base.model.tipo.TipoArchivoTrabajo;
import com.egoveris.te.base.repository.ArchivoDeTrabajoRepository;
import com.egoveris.te.base.repository.tipo.ITipoArchivoTrabajoRepository;
import com.egoveris.te.base.util.BusinessFormatHelper;

/**
 * @author eduavega Implementacion de métodos para acceder a la implementacion
 *         del ArchivoDeTrabajoDao, se crea el espacio en Apache WebDav (ex
 *         Alfresco), se suben y eliminan archivos en Apache WebDav (ex
 *         Alfresco)
 */
@Transactional
@Service
public class ArchivoDeTrabajoServiceImpl implements ArchivoDeTrabajoService {
  private static final Logger logger = LoggerFactory.getLogger(ArchivoDeTrabajoServiceImpl.class);

  @Autowired
  private ArchivoDeTrabajoRepository archivoDeTrabajoRepository;

  @Autowired
  private ITipoArchivoTrabajoRepository tipoArchivoDeTrabajoRepository;

  @Autowired
  private IWebDavService webDavService;

  private Mapper mapper_ = new DozerBeanMapper();
  
  @Autowired
  private Mapper mapper;
  
  @Autowired
  private String fileNETNombreModulo;
  
  @Autowired
  private AppProperty appProperty;

  @Override
  public TipoArchivoTrabajoDTO buscarTipoArchivoTrabajoPorNombre(final String nombreTipoArchivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoArchivoTrabajoPorNombre(nombreTipoArchivo={}) - start",
          nombreTipoArchivo);
    }

    TipoArchivoTrabajo returnTipoArchivoTrabajo = tipoArchivoDeTrabajoRepository
        .findByNombre(nombreTipoArchivo);
    TipoArchivoTrabajoDTO tipArchivoTrabajoDto;

    tipArchivoTrabajoDto = mapper_.map(returnTipoArchivoTrabajo, TipoArchivoTrabajoDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoArchivoTrabajoPorNombre(String) - end - return value={}",
          returnTipoArchivoTrabajo);
    }
    return tipArchivoTrabajoDto;
  }

  /**
   * @author eduavega Se recibe un archivo para ser elimnado de la base de
   *         datos.
   * @param archivo
   */
  @Override
  public void eliminarAchivoDeTrabajo(final ArchivoDeTrabajoDTO archivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarAchivoDeTrabajo(archivo={}) - start", archivo);
    }
    ArchivoDeTrabajo entidad;

    entidad = mapper_.map(archivo, ArchivoDeTrabajo.class);
    archivoDeTrabajoRepository.delete(entidad);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarAchivoDeTrabajo(ArchivoDeTrabajo) - end");
    }
  }

  /**
   * @author eduavega Metodo que permite eliminar un archivo dentro de un
   *         espacio en Alfresco
   * @param nombreArchivo
   *          nombre del archivo a eliminar dentro de un espacio en Alfresco
   * @param nombreSpace
   *          nombre del espacio en alfresco
   */

  @Override
  public void eliminarArchivoDeTrabajoPorWebDav(final String nombreArchivo,
      final String nombreSpace) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarArchivoDeTrabajoPorWebDav(nombreArchivo={}, nombreSpace={}) - start",
          nombreArchivo, nombreSpace);
    }

    try {
      webDavService.deleteFile(
          "Documentos_De_Trabajo/" + BusinessFormatHelper.formarPathWebDavApache(nombreSpace),
          nombreArchivo, "", "");
    } catch (final Exception e) {
      logger.error("eliminarArchivoDeTrabajoPorWebDav(String, String)", e);

      throw new TeRuntimeException("No se puede desasociar el archivo: " + nombreArchivo, e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarArchivoDeTrabajoPorWebDav(String, String) - end");
    }
  }

  public AppProperty getAppProperty() {
    return appProperty;
  }

  public ArchivoDeTrabajoRepository getArchivoDeTrabajoDao() {
    return archivoDeTrabajoRepository;
  }

  public String getFileNETNombreModulo() {
    return fileNETNombreModulo;
  }

  public ITipoArchivoTrabajoRepository getTipoArchivoDeTrabajoDao() {
    return tipoArchivoDeTrabajoRepository;
  }

  public IWebDavService getWebDavService() {
    return webDavService;
  }

  /**
   * @author eduavega Se recibe un archivo para ser persistido en la base de
   *         datos.
   * @param archivo
   */
  @Override
  public void grabarArchivoDeTrabajo(final ArchivoDeTrabajoDTO archivo) {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarArchivoDeTrabajo(archivo={}) - start", archivo);
    }

    ArchivoDeTrabajo entidad;
    entidad = mapper_.map(archivo, ArchivoDeTrabajo.class);
    archivoDeTrabajoRepository.save(entidad);

    if (logger.isDebugEnabled()) {
      logger.debug("grabarArchivoDeTrabajo(ArchivoDeTrabajo) - end");
    }
  }

  @SuppressWarnings("unchecked")
@Override
  public List<TipoArchivoTrabajoDTO> mostrarTodosTipoArchivoTrabajo() {
    if (logger.isDebugEnabled()) {
      logger.debug("mostrarTodosTipoArchivoTrabajo() - start");
    }
    
    List<TipoArchivoTrabajo> archivoTrabajoList = tipoArchivoDeTrabajoRepository.findAll();
    List<TipoArchivoTrabajoDTO> returnList = new ArrayList<>();
    
    if (archivoTrabajoList != null) {
    	returnList = ListMapper.mapList(archivoTrabajoList, mapper_, TipoArchivoTrabajoDTO.class);
    }
    
    if (logger.isDebugEnabled()) {
      logger.debug("mostrarTodosTipoArchivoTrabajo() - end - return value={}", returnList);
    }
    
    return returnList;
  }

  public void setAppProperty(final AppProperty appProperty) {
    this.appProperty = appProperty;
  }

  public void setArchivoDeTrabajoDao(final ArchivoDeTrabajoRepository archivoDeTrabajoDao) {
    this.archivoDeTrabajoRepository = archivoDeTrabajoDao;
  }

  public void setFileNETNombreModulo(final String fileNETNombreModulo) {
    this.fileNETNombreModulo = fileNETNombreModulo;
  }

  public void setTipoArchivoDeTrabajoDao(
      final ITipoArchivoTrabajoRepository tipoArchivoDeTrabajoDao) {
    this.tipoArchivoDeTrabajoRepository = tipoArchivoDeTrabajoDao;
  }

  public void setWebDavService(final IWebDavService webDavService) {
    this.webDavService = webDavService;
  }

  /**
   * @author eduavega Se reciben los parametros archivo y nombreSpace, se crea
   *         un directorio temporal para el achivo a ser subido en Alfresco y se
   *         envian los parametros al metedo que subira definitivamente el
   *         archivo en Alfresco.
   * @param archivo
   * @param nombreSpace
   *          nombre del espacio que se creara en Alfresco
   *
   */
  @Override
  public String subirArchivoDeTrabajo(final ArchivoDeTrabajoDTO archivo,
      final String nombreSpace) {
    if (logger.isDebugEnabled()) {
      logger.debug("subirArchivoDeTrabajo(archivo={}, nombreSpace={}) - start", archivo,
          nombreSpace);
    }

    File archivoTemp;
    try {
      archivoTemp = File.createTempFile("archivoDeTrabajo", archivo.getNombreArchivo());
    } catch (final IOException e1) {
      logger.error("subirArchivoDeTrabajo(ArchivoDeTrabajo, String)", e1);

      throw new TeRuntimeException(
          "No se puede adjuntar el archivo: " + archivo.getNombreArchivo(), e1);
    }

    try (final FileOutputStream fos = new FileOutputStream(archivoTemp)) {
      fos.write(archivo.getDataArchivo());
      fos.close();
      this.subirArchivoDeTrabajoPorWebDav(archivo, archivoTemp, nombreSpace);

      if (logger.isDebugEnabled()) {
        logger
            .debug("subirArchivoDeTrabajo(ArchivoDeTrabajo, String) - end - return value={null}");
      }
      return null;

    } catch (final Exception e) {
      logger.error("subirArchivoDeTrabajo(ArchivoDeTrabajo, String)", e);

      throw new TeRuntimeException(
          "No se puede adjuntar el archivo: " + archivo.getNombreArchivo(), e);
    }
  }

  /**
   * @author eduavega Metodo que realiza la subido del archivo al repositorio de
   *         Alfresco
   * @param archivo
   *          se extrae el nombre para el archivo
   * @param file
   *          es archivo a ser enviado al Alfresco
   * @param nombreSpace
   *          es el nombre del espacio donde se subira el archivo
   *
   */

  @Override
  public void subirArchivoDeTrabajoPorWebDav(final ArchivoDeTrabajoDTO archivo, final File file,
      final String nombreSpace) {
    if (logger.isDebugEnabled()) {
      logger.debug("subirArchivoDeTrabajoPorWebDav(archivo={}, file={}, nombreSpace={}) - start",
          archivo, file, nombreSpace);
    }

    try {
      archivo.setDataArchivo(FileUtils.readFileToByteArray(new File(file.getAbsolutePath())));

      final Integer anio = BusinessFormatHelper.obtenerAnio(nombreSpace);
      final String reparticionUsuario = BusinessFormatHelper
          .obtenerReparticionUsuario(nombreSpace);
      final String primerosDosNumeroSade = BusinessFormatHelper
          .completarConCerosNumActuacion(BusinessFormatHelper.obtenerNumeroSade(nombreSpace))
          .substring(0, 2);
      final String segundosTresNumeroSade = BusinessFormatHelper
          .completarConCerosNumActuacion(BusinessFormatHelper.obtenerNumeroSade(nombreSpace))
          .substring(2, 5);

      /**
       * Se comenta la validación de existencia de la estructura de carpetas
       * para armar una validación general en el webdav
       */
      webDavService.createSpace(null, "Documentos_De_Trabajo", "", "");
      webDavService.createSpace("Documentos_De_Trabajo", anio.toString(), "", "");
      webDavService.createSpace("Documentos_De_Trabajo/" + anio.toString(), reparticionUsuario, "",
          "");
      webDavService.createSpace(
          "Documentos_De_Trabajo/" + anio.toString() + "/" + reparticionUsuario + "/",
          primerosDosNumeroSade, "", "");
      webDavService.createSpace("Documentos_De_Trabajo/" + anio.toString() + "/"
          + reparticionUsuario + "/" + primerosDosNumeroSade + "/", segundosTresNumeroSade, "",
          "");
      webDavService
          .createSpace(
              "Documentos_De_Trabajo/" + anio + "/" + reparticionUsuario + "/"
                  + primerosDosNumeroSade + "/" + segundosTresNumeroSade + "/",
              nombreSpace.trim(), "", "");

      webDavService.createFile(
          "Documentos_De_Trabajo/" + BusinessFormatHelper.formarPathWebDavApache(nombreSpace),
          archivo.getNombreArchivo(), "", "", new ByteArrayInputStream(archivo.getDataArchivo()));

    } catch (final IOException ioe) {
      logger.error("subirArchivoDeTrabajoPorWebDav(ArchivoDeTrabajo, File, String)", ioe);

      throw new TeRuntimeException(
          "No se puede adjuntar el archivo: " + archivo.getNombreArchivo(), ioe);
    } catch (final Exception e) {
      logger.error("subirArchivoDeTrabajoPorWebDav(ArchivoDeTrabajo, File, String)", e);

      throw new TeRuntimeException(
          "No se puede adjuntar el archivo: " + archivo.getNombreArchivo(), e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("subirArchivoDeTrabajoPorWebDav(ArchivoDeTrabajo, File, String) - end");
    }
  }

}