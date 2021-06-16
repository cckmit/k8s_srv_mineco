package com.egoveris.te.base.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ParametrosSistemaExternoDTO {
	private static final Logger logger = LoggerFactory.getLogger(ParametrosSistemaExternoDTO.class);

	private Long id;
	private Long idTrata;
	private String codigo;
	private String url;
	private String parametros;
	private Boolean esactivo;
	private Set<ReparticionDTO> reparticiones = new HashSet<>();
	private List<TrataIntegracionReparticionDTO>reparticionesIntegracion;
	
	private boolean externo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
			
	public Boolean getEsactivo() {
		return this.esactivo;
	}

	public void setEsactivo(Boolean esactivo) {
		this.esactivo = esactivo;
	}
	
	public String getParametros() {
		return this.parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}
	
	public void removerPorCodigo(String codigo){
	}
	
	public String getURLFull() {
		if (logger.isDebugEnabled()) {
			logger.debug("getURLFull() - start");
		}

		StringBuilder urlFull = new StringBuilder();
		if(!getUrl().contains("http://") && !getUrl().contains("https://")){
            urlFull.append("http://");
		}	
		urlFull.append(getUrl());
		urlFull.append(getParametros());
		String returnString = urlFull.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("getURLFull() - end - return value={}", returnString);
		}
		return returnString ;
	}

	@Override
	public String toString() {
		return "ParametrosSistemaExterno [id=" + id + ", codigo=" + codigo
				+ ", url=" + url + ", parametros=" + parametros + ", esactivo="
				+ esactivo + "]";
	}

	public Set<ReparticionDTO> getReparticiones() {
		return reparticiones;
	}

	public void setReparticiones(Set<ReparticionDTO> reparticiones) {
		this.reparticiones = reparticiones;
	}

	public Long getIdTrata() {
		return idTrata;
	}

	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}

	public boolean isExterno() {
		return externo;
	}

	public void setExterno(boolean externo) {
		this.externo = externo;
	}

	
	public List<TrataIntegracionReparticionDTO> getReparticionesIntegracion() {
		return reparticionesIntegracion;
	}

	public void setReparticionesIntegracion(
			List<TrataIntegracionReparticionDTO> reparticionesIntegracion) {
		this.reparticionesIntegracion = reparticionesIntegracion;
	}

	public void agregarTrataIntegracion(TrataIntegracionReparticionDTO repa) {
		if (logger.isDebugEnabled()) {
			logger.debug("agregarTrataIntegracion(repa={}) - start", repa);
		}

		this.reparticionesIntegracion.add(repa);
		
		if (logger.isDebugEnabled()) {
			logger.debug("agregarTrataIntegracion(TrataIntegracionReparticion) - end");
		}
	}
	
	
	
	
		
}
