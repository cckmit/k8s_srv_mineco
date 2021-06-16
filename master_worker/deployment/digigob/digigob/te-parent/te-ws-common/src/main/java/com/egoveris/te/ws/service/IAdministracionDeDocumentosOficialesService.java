package com.egoveris.te.ws.service;

import java.util.List;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RequestRelacionDocumentoOficialEE;
import com.egoveris.te.model.model.VinculacionDefinitivaDeDocsRequest;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

/**
 * La presente interfaz de servicio, expone los métodos necesarios para la
 * administración de documentos oficiales vinculados a un expediente
 * electrónico.
 * 
 * @author cearagon
 * 
 */
public interface IAdministracionDeDocumentosOficialesService {

	/**
	 * Vincula los documentos oficiales, cuyos números SADE se encuentran en la
	 * lista otorgada por parámetro, al expediente electrónico cuyo código es
	 * también un parámetro otorgado.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita la vinculación.
	 * @param usuario
	 *            : Nombre del usuario que solicita la vinculación.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            vincularán los documentos oficiales. Ej
	 *            "EX-2012-00001478-   -MGEYA-MGEYA"
	 * @param listaDocumentos
	 *            : lista conteniendo los números SADE de los documentos que se
	 *            desean vincular al expediente electrónico.
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void vincularDocumentosOficiales(String sistemaUsuario,
			String usuario, String codigoEE, List<String> listaDocumentos)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	/**
	 * Desvincula los documentos oficiales, cuyos números SADE se encuentran en
	 * la lista otorgada como parámetro, del expediente electrónico cuyo código
	 * es también un parámetro otorgado.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita la desvinculación.
	 * @param usuario
	 *            : Nombre del usuario que solicita la desvinculación.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            desvincularán los documentos oficiales. Ej
	 *            "EX-2012-00001478-   -MGEYA-MGEYA"
	 * @param listaDocumentos
	 *            : lista conteniendo los números SADE de los documentos que se
	 *            desean desvincular del expediente electrónico.
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void desvincularDocumentosOficiales(String sistemaUsuario,
			String usuario, String codigoEE, List<String> listaDocumentos)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	/**
	 * Método accesorio para limpiar un expediente de cualquier documento oficial que se encuentre vinculado de manera no definitiva
	 * de modo que un sistema origen tenga la facilidad de poder "resetear" todas las vinculaciones que se hayan realizado.
	 * @param sistemaUsuario
	 * @param usuario
	 * @param codigoEE
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public void eliminarDocumentosNoDefinitivos(String sistemaUsuario,
			String usuario, String codigoEE)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	
    /**
     *
     * Se realiza la vinculacion de un <code>DocumentoOficial</code> que no esta difinitivo, al <code>ExpedienteElectronico</code>.
     * Las acciones de la operacion son,
     * @param <code>VincularDocumentoOficialRequest</code> request, los parametros son,
     * @throws <code>ProcesoFallidoException</code>, <code>ExpedienteInexistenteException</code>, <code>ParametroIncorrectoException</code>, <code>ExpedienteNoDisponibleException</code>
     */
	public void vincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE request)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;	


   /**
    *
    * Se realiza la desvinculacion de un <code>DocumentoOficial</code> que no esta difinitivo, al <code>ExpedienteElectronico</code>.
    * Los parametros que se usan son,<p>
    * Las acciones de la operacion que se realiza es
    * @param <code>VincularDocumentoOficialRequest</code> request
    * @throws <code>ProcesoFallidoException</code>, <code>ExpedienteInexistenteException</code>, <code>ParametroIncorrectoException</code>, <code>ExpedienteNoDisponibleException</code>
    */
	public void desvincularDocumentosOficialesNumeroEspecial(RequestRelacionDocumentoOficialEE request)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;
	
	/**
	 * Vincula los documentos oficiales, cuyos números SADE se encuentran en la
	 * lista otorgada por parámetro, al expediente electrónico cuyo código es
	 * también un parámetro otorgado.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita la vinculación.
	 * @param usuario
	 *            : Nombre del usuario que solicita la vinculación.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            vincularán los documentos oficiales. Ej
	 *            "EX-2012-00001478-   -MGEYA-MGEYA"
	 * @param documento
	 *            : número SADE del documento que se
	 *            desean vincular al expediente electrónico.
	 * 
	 * @param idTransaccion
	 *            : id de transaccion para ubicar el formulario controlado que se utilizo  
	 * 
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void vincularDocumentosOficialesConTransaccionFC(String sistemaUsuario,
			String usuario, String codigoEE, String documento, Long idTransaccionFC)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;
	
	/**
	 * vincula documentos a un Expediente con estado guarda temporal o con estado solicitud archivo
	 * @param sistemaUsuario
	 * @param usuario
	 * @param codigoEE
	 * @param listaDocumentos
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	public void vincularDocumentosOficialesAEEGuardaTemporalOSolicitudArchivo(String sistemaUsuario,
			String usuario, String codigoEE, List<String> listaDocumentos)
	   		 throws ProcesoFallidoException, ParametroIncorrectoException, ExpedienteNoDisponibleException;



	/**
	 * Metodo que hace definitivos todos los documentos existentes del expediente 
	 * @param request
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 * @throws ExpedienteNoDisponibleException
	 */
	
	public void hacerDefinitivosDocsDeEE(VinculacionDefinitivaDeDocsRequest request) throws ProcesoFallidoException, ParametroIncorrectoException, ExpedienteNoDisponibleException;
}
