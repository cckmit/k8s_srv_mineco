package com.egoveris.te.base.service.iface;

import java.util.List;

import javax.jws.WebParam;

import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.CaratulaVariableResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponse;
import com.egoveris.te.model.model.ConsultaExpedienteResponseDetallado;
import com.egoveris.te.model.model.ExpedienteElectronicoResponse;
import com.egoveris.te.model.model.HistorialDePasesResponse;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEERequest;
import com.egoveris.te.model.model.ObtenerCaratulaPorCodigoEEResponse;

/**
 * La presente interfaz de servicio expone los métodos necesarios para la
 * consulta de expedientes electrónicos.
 * 
 * @author cearagon
 * 
 */
public interface IConsultaExpedienteService {

	public boolean validarEE(@WebParam(name="codigoEE") String codigoEE);
	
	/**
	 * Servicio que busca el expediente electrónico que se corresponde al código
	 * de expediente dado como parámetro.
	 * 
	 * @param codigoEE
	 *            : código SADE del expediente buscado.
	 * @return un objeto tipo ConsultaExpedienteResponse.
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public ConsultaExpedienteResponse consultaEE(@WebParam(name="codigoEE") String codigoEE)
	throws ProcesoFallidoException, 	ParametroIncorrectoException;
	
	
	
	/**
	 * Servicio que busca el expediente electrónico y detalla( Datos propios del expediente,Destino actual (repartición, sector o usuario)
	 *  según figure en el historial. Discrimina en distintos parámetros de salida si es usuario, sector o repartición , Fecha de caratulación,
	 *   Usuario caratulador,Descripción adicional del trámite ) que se corresponde al código de expediente dado como parámetro.
	 * 
	 * @param codigoEE
	 *            : código SADE del expediente buscado.
	 * @return un objeto tipo ConsultaExpedienteResponse.
	 * @throws ProcesoFallidoException
	 * @throws ExpedienteInexistenteException
	 * @throws ParametroIncorrectoException
	 */
	public ConsultaExpedienteResponseDetallado consultaEEDetallado(@WebParam(name="codigoEE") String codigoEE)
	throws ProcesoFallidoException, 	ParametroIncorrectoException;

	
    // EE-REFACTORME (Martes 15-Marzo-2014) grinberg. 
	// JIRA: https://quark.everis.com/jira/browse/BISADE-3132, 
	//       https://quark.everis.com/jira/browse/BISADE-3624
	/**
	 * Listado de List<DatosTareaBean> de los <code>ExpedienteElectronico</code>
	 * @param <code>java.util.List<String></code>listaDeCodigosTrata
	 * @param <code>java.lang.String</code>expedienteUsuarioAsignado
	 * @param <code>java.lang.String</code>expedienteEstado
	 * @return <code>List<DatosTareaBean></code>listaDatosTareaBean
	 * @throws ProcesoFallidoException
	 */
	public ConsultaExpedienteResponse consultarDatosExpedientePorCodigosDeTrata(@WebParam(name = "listaDeCodigosTrata") List<String> listaDeCodigosTrata, @WebParam(name="expedienteEstado") String expedienteEstado, @WebParam(name="expedienteUsuarioAsignado") String expedienteUsuarioAsignado) throws ProcesoFallidoException;
	
	/**
	 * servicio que retorna todos los expedientes que estan en estado guarda temporal con fecha de modificacion mayor a 24 meses
	 * a partir del dia actual
	 * @return
	 */
	
    public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalMayorA24Meses(@WebParam(name="cantidadDeDias")Integer cantidadDeDias);
    
    
    public List<ExpedienteElectronicoResponse> obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(@WebParam(name="codigoExpediente")String codExpediente);
	
	/**
	 * Obtiene el último codigo de la modificación de caratula.
	 * @param <code>java.lang.String</code>expedienteCodigo
	 * @return <code>javal.lang.String</code>codigoDocCaratula
	 * @throws ProcesoFallidoException
	 */
	public ConsultaExpedienteResponse obtenerCaratulaPorNumeroExpediente(@WebParam(name="expedienteCodigo") String expedienteCodigo) throws ProcesoFallidoException;
	
	/**
	 * @param <code><ObtenerCaratulaPorCodigoEERequest>
	 * 				<codigoEE> </codigoEE>
	 * 				<ObtenerCaratulaPorCodigoEERequest></code> request
	 * 
	 *    @return <code><ObtenerCaratulaPorCodigoEEResponse>
     *          	<DTODatosCaratula>      
     *     			<NombreSolicitante>PEPE</NombreSolicitante>
     *     			<ApellidoSolicitante>PEPE</ ApellidoSolicitante>
     *     			<RazonSocialSolicitante>PEPE</RazonSocialSolicitante>
     *     			<TipoDocumento>PEPE</TipoDocumento>
     *     			<NumeroDocumento>PEPE</NumeroDocumento>
     *     			<NumeroSadeDocumentoCaratula>PEPE</ NumeroSadeDocumentoCaratula >
     *     			</DTODatosCaratula>      
     *     		<ObtenerCaratulaPorCodigoEEResponse></code> response
	 */
    public ObtenerCaratulaPorCodigoEEResponse obtenerCaratulaPorCodigoEE( @WebParam(name = "request") ObtenerCaratulaPorCodigoEERequest request) throws ProcesoFallidoException;


    /**
     *  recibe un numeroSADE de un expediente y retorna su historial de pases
     * @param codigoEE
     * @return
     * @throws ParametroIncorrectoException
     */
    public HistorialDePasesResponse obtenerHistorialDePasesDeExpediente (@WebParam(name="codigoEE") String codigoEE)
 			throws ParametroIncorrectoException;
    /**
     * Servicio que retorna todos los expedientes del sistema origen dado para la reparticion indicada.
     * @param sistemaOrigen
     * @param reparticion
     * @return List<String>
     */
    public List<String> consultaExpedientesPorSistemaOrigenReparticion(
    		@WebParam(name="sistemaOrigen")String sistemaOrigen, @WebParam(name="reparticion")String reparticion) throws ParametroIncorrectoException;

    /**
     * Servicio que retorna todos los expedientes del sistema origen dado para el usuario ingresado.
     * @param sistemaOrigen
     * @param usuario
     * @return List<String>
     */
    public List<String> consultaExpedientesPorSistemaOrigenUsuario(
    		@WebParam(name="sistemaOrigen")String sistemaOrigen, @WebParam(name="usuario")String usuario) throws ParametroIncorrectoException;
    
    public Long consultaIdFCPorExpediente(@WebParam(name="codigoEE")String codigoEE) throws ParametroIncorrectoException;
    
    /**
     * Servicio que retorna los datos de caratula variable segun el codigoEE para el usuario ingresado.
     * @param codigoEE
     * @param usuario
     * @return List<String>
     */
    public CaratulaVariableResponse obtenerDatosCaratulaVariable(@WebParam(name="codigoEE")String codigoEE, 
    		@WebParam(name="usuario")String usuario) throws ParametroIncorrectoException;

    /**
     * Servicio que retorna pdf codificado Base64 segun codigo expediente para el usuario ingresado.
     * @param codigoEE
     * @param usuario
     * @return List<String>
     */
    public byte[] obtenerDocumentoCaratulaVariable(@WebParam(name="codigoEE")String codigoEE, 
    		@WebParam(name="usuario")String usuario) throws ParametroIncorrectoException;

}
