package com.egoveris.te.ws.service;

import java.util.List;

import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.ArchivoDeTrabajoResponse;
import com.egoveris.te.model.model.DocumentoTrabajo;
import com.egoveris.te.ws.exception.ExpedienteInexistenteException;

/**
 * La presente interface de servicio, expone los métodos necesarios para la
 * administración de documentos de trabajo adjuntos a un expediente electrónico.
 * 
 * @author cearagon
 * 
 */
public interface IAdministracionDocumentosDeTrabajoService {

	/**
	 * Adjunta los documentos de trabajo contenidos en una lista otorgada por
	 * parámetro, al expediente de código dado.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita adjuntar.
	 * @param usuario
	 *            : Nombre del usuario que solicita adjuntar.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            adjuntarán los documentos de trabajo. Ej.
	 *            "EX-2012-00001234-   -MGEYA-MGEYA"
	 * 
	 * @param listaDocumentos
	 *            : lista conteniendo los documentos de trabajo que se desean
	 *            asociar al expediente electrónico.
	 * 
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void adjuntarDocumentosTrabajo(String sistemaUsuario,
			 String usuario, String codigoEE,
			List<DocumentoTrabajo> listaDocumentos)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;

	/**
	 * Des-adjunta los documentos de trabajo, cuyos nombres se consignan en la
	 * lista otorgada por parámetro, del expediente de código dado.
	 * 
	 * @param sistemaUsuario
	 *            : Nombre del Sistema al que pertenece el usuario; desde el
	 *            cual solicita desadjuntar.
	 * @param usuario
	 *            : Nombre del usuario que solicita desadjuntar.
	 * @param codigoEE
	 *            : código SADE del expediente electrónico al cual se le
	 *            desadjuntarán los documentos de trabajo. Ej. "EX-2012-00001234-   -MGEYA-MGEYA"
	 * 
	 * @param listaDocumentos
	 *            : lista conteniendo la totalidad de los nombres de los
	 *            documentos de trabajo, que se desean desadjuntar del
	 *            expediente electrónico.
	 * 
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public void desadjuntarDocumentosDeTrabajo(String sistemaUsuario,
			String usuario, String codigoEE, List<String> listaDocumentos)
			throws ProcesoFallidoException, 
			ParametroIncorrectoException, ExpedienteNoDisponibleException;
	
	/**
	 * Servicio que retoma un array de bytes a partir del codigoEE y nombre del archivo de trabajo.
	 * @param codigoEE
	 * @param nombre
	 * @return
	 * @throws ProcesoFallidoException
	 * @throws ParametroIncorrectoException 
	 */
	public ArchivoDeTrabajoResponse obtenerArchivoDeTrabajo(String codigoEE, String usuario, String nombre) throws ProcesoFallidoException, ParametroIncorrectoException;		

}