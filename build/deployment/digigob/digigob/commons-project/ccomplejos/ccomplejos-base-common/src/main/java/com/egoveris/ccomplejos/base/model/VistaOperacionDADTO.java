package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.List;

public class VistaOperacionDADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5077974711965231004L;

	protected VistaFinancieroDADTO antecedentesFinancieros;
	protected List<VistaBultoDADTO> bulto;
	protected String codigoAgenciaAduana;
	protected String codigoOficinaAduana;
	protected VistaParticipantesDaDTO despachador;
	protected VistaParticipantesDaDTO destinatario;
	protected VistaDetallesPuertoDADTO destino;
	protected VistaDUSDADTO documentoUnicoSalida;
	protected VistaDetallesPuertoDADTO embarque;
//	protected VistaMercanciaDADTO mercancia;
	protected String nombreOficinaAduana;
	protected String observacionesDeclaracion;
	protected VistaDetallesPuertoDADTO origen;
	protected VistaTotalesDeclaracionDTO totalesDeclaracionFOB;
	protected String valorSeguroDeclaracion;


	

}
